package com.charlie.common.utils;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;



public class DbManager {
	private static final Log log=LogFactory.getLog(DbManager.class);
	/**
	 * 获取连接
	 * 
	 * @return
	 */
	public Connection getConnect() throws Exception{
		Connection conn = null;
        String url = "jdbc:mysql://127.0.0.1:3306/ssm?"
                + "user=root&password=123&useUnicode=true&characterEncoding=UTF8";
 
        Class.forName("com.mysql.jdbc.Driver");// 动态加载mysql驱动
        // or:
        // com.mysql.jdbc.Driver driver = new com.mysql.jdbc.Driver();
        // or：
        // new com.mysql.jdbc.Driver();
 
        System.out.println("成功加载MySQL驱动程序");
        conn = DriverManager.getConnection(url);
        return conn ;
	}
	
	/**
	 * 清理resultSet
	 * @param rs
	 */
	public void close(ResultSet rs) {
		try {
		if (rs != null) {
				rs.close();
				rs = null;
		}
			} catch (SQLException e) {
				log.error(e,e.fillInStackTrace()) ;
				e.printStackTrace();
			}
	}
	/**
	 * 清理PreparedStatement
	 * @param prestmt
	 */
	public void close(PreparedStatement prestmt){
		try {
			if (null != prestmt) {
				prestmt.close();
				prestmt = null;
			}
		} catch (SQLException e) {
			log.error(e,e.fillInStackTrace()) ;
			e.printStackTrace();
		}
	}
	/**
	 * 关闭Connection
	 * @param con
	 */
	public void close(Connection con){
		
		try{
			if(null!=con){
				con.close();
				con=null;
				//System.out.println(">>>>>>>>>>>>>>>"+count--);
			}
		}catch(SQLException e){
			log.error(e,e.fillInStackTrace()) ;
			e.printStackTrace();
		}
		
		
	}

	/**
	 * 关闭连接 释放内存
	 */
	public void close(Connection con, ResultSet rs) {
		this.close(rs);
		this.close(con);
	}

	/**
	 * 查询操作
	 * 
	 * @param sql
	 * @param data
	 *            参数都
	 * @return resultset
	 */
	public ResultSet preExecQuery(Connection con, String sql, Object[] data) {
		ResultSet rs = null;
		PreparedStatement prestmt = null;
		try {
			printSQL(sql,data);
			prestmt = null;
			log.info(sql);
			prestmt = (PreparedStatement) con.prepareStatement(sql);
			this.setParams(prestmt, data);
			rs = prestmt.executeQuery();
		} catch (SQLException e) {
			log.error(e,e.fillInStackTrace());
			e.printStackTrace();
		} catch (Exception e) {
			log.error(e,e.fillInStackTrace());
			e.printStackTrace();
		} finally {
			//close(prestmt);
		}
		return rs;
	}
	/**
	 * 插入数据返回主键
	 * @param con
	 * @param sql
	 * @param data
	 * @return 主键
	 */
	public int preExecUpdateKey(Connection con,String sql, Object[] data) {
		PreparedStatement prestmt = null;
		ResultSet rs = null;
		int _backKey = -1;
		try {
			printSQL(sql,data);
			log.info(sql);
			// 1 设置提交方式为程序控制
			con.setAutoCommit(false);
			// 2 获得语句对象
			prestmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			// 3设置SQL语句的参数
			this.setParams(prestmt, data);
			// 4 执行语句
			prestmt.executeUpdate();
			// 5 程序提交
			con.commit();
			con.setAutoCommit(true);
			// 6 返回生成的主键
			rs = prestmt.getGeneratedKeys();
			
			if (rs.next()) {
				_backKey = rs.getInt(1);
			}

		} catch (SQLException e) {
			log.error(e,e.fillInStackTrace()) ;
			e.printStackTrace();
		} catch (Exception e) {
			log.error(e,e.fillInStackTrace()) ;
			e.printStackTrace();
		}finally{
			close(rs);
			close(prestmt);
		}
		return _backKey;
	}

	/**
	 * 更新操作
	 * 
	 * @param sql
	 * @param data
	 * @return 影响的行数
	 */
	public int preExecUpdate(Connection con, String sql, Object[] data) {
		PreparedStatement prestmt = null;
		int _count = -1;
		try {
			printSQL(sql,data);
			prestmt = null;
			log.info(sql);
			prestmt = (PreparedStatement) con.prepareStatement(sql);
			this.setParams(prestmt, data);
			_count = prestmt.executeUpdate();
		} catch (SQLException e) {
			log.error(e,e.fillInStackTrace()) ;
			//e.printStackTrace();
		} catch (Exception e) {
			log.error(e,e.fillInStackTrace()) ;
			//e.printStackTrace();
		}finally{
			close(prestmt);
		}
		return _count;
	}

	/**
	 * 取得总数
	 * 
	 * @param sql
	 * @return sql的第一个字段值
	 */
	public int getPreTotal(Connection con, String sql, Object[] data) {
		ResultSet rs = null;
		PreparedStatement prestmt = null;
		int _count = 0;
		try {
			printSQL(sql,data);
			prestmt = null;
			prestmt = (PreparedStatement) con.prepareStatement(sql);
			this.setParams(prestmt, data);
			rs = prestmt.executeQuery();
			if (null != rs && rs.next()) {
				_count = rs.getInt(1);
			}

		} catch (SQLException e) {
			log.error(e,e.fillInStackTrace()) ;
			e.printStackTrace();
		} catch (Exception e) {
			log.error(e,e.fillInStackTrace()) ;
			e.printStackTrace();
		}finally{
			close(rs);
			close(prestmt);
		}
		return _count;
	}

	/**
	 * 打印sql
	 * 
	 * @param sql
	 * @param params
	 * @return 去掉？号的sql
	 */
	private void printSQL(String sql, Object[] params) {

		int _paramNum = 0;
		String _backSql = "";
		if (null != params)
			_paramNum = params.length;
		if (1 > _paramNum) {// 1 如果没有参数，说明是不是动态SQL语句
			_backSql = sql;
		} else {// 2 如果有参数，则是动态SQL语句
			StringBuffer _returnSQL = new StringBuffer();
			String[] _subSQL = sql.split("\\?");
			for (int i = 0; i < _paramNum; i++) {
				if (null != params[i]) {

					if (params[i] instanceof java.util.Date) {
						_returnSQL
								.append(_subSQL[i])
								.append(" '")
								.append(this
										.util2sql((java.util.Date) params[i]))
								.append("' ");
					} else if (params[i] instanceof String) {
						_returnSQL.append(_subSQL[i]).append(" '")
								.append(params[i]).append("' ");
					} else if (params[i] instanceof Integer) {
						_returnSQL.append(_subSQL[i]).append(" ")
								.append(params[i]).append(" ");
					}
				}
			}

			if (_subSQL.length > params.length) {
				_returnSQL.append(_subSQL[_subSQL.length - 1]);
			}
			_backSql = _returnSQL.toString();
		}
		log.debug(_backSql);
	}

	/**
	 * 设置参数
	 * 
	 * @param pstmt
	 * @param params
	 */
	private void setParams(PreparedStatement pstmt, Object[] params) {
		if (null != params) {
			for (int i = 0, j = 1; i < params.length; i++) {
				try {
					if (null != params[i]) {// 有值可写入
						if (params[i] instanceof java.util.Date) {
							pstmt.setDate(j,
									this.util2sql((java.util.Date) params[i]));
						} else {
							pstmt.setObject(j, params[i]);
						}
						j++;
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private java.sql.Date util2sql(java.util.Date fechaUtil) {
		java.sql.Date _SQL = new java.sql.Date(fechaUtil.getTime());
		return _SQL;
	}
	public static void main(String[] args) throws Exception{
		DbManager db = new DbManager();
		Connection conn = db.getConnect() ;
	}
}
