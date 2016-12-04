package com.charlie.common.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class DbManagerSQLServer {
	private static final Log log=LogFactory.getLog(DbManagerSQLServer.class);
	private static final String URL = GetProperties.getPro("jdbc.properties").getProperty("jdbc.url") ;
	private static final String DRIVER = GetProperties.getPro("jdbc.properties").getProperty("jdbc.driver") ;
	private static final String USERNAME = GetProperties.getPro("jdbc.properties").getProperty("jdbc.username") ;
	private static final String PASSWORD = GetProperties.getPro("jdbc.properties").getProperty("jdbc.password") ;
	/**
	 * 获取连接
	 * 
	 * @return
	 */
	public static Connection getConnect() throws Exception{
		Connection conn = null;
        Class.forName(DRIVER);// 动态加载mysql驱动
        log.info("成功加载SQL SERVER驱动程序......");
        conn = DriverManager.getConnection(URL,USERNAME,PASSWORD);
        return conn ;
	}
	
	/**
	 * 清理resultSet
	 * @param rs
	 */
	public static void close(ResultSet rs) {
		try {
		if (rs != null) {
				rs.close();
				rs = null;
		}
			} catch (SQLException e) {
				log.error("close rs error!");
				log.error(e, e.fillInStackTrace());
			}
	}
	/**
	 * 清理PreparedStatement
	 * @param prestmt
	 */
	public static void close(PreparedStatement prestmt){
		try {
			if (null != prestmt) {
				prestmt.close();
				prestmt = null;
			}
		} catch (SQLException e) {
			log.error("close prestmt error!");
			log.error(e, e.fillInStackTrace());
		}
	}
	public static void close(Statement stmt){
		try {
			if (null != stmt) {
				stmt.close();
				stmt = null;
			}
		} catch (SQLException e) {
			log.error("close stmt error!");
			log.error(e, e.fillInStackTrace());
		}
	}
	/**
	 * 关闭Connection
	 * @param con
	 */
	public static void close(Connection con){
		
		try{
			if(null!=con){
				con.close();
				con=null;
				//System.out.println(">>>>>>>>>>>>>>>"+count--);
			}
		}catch(SQLException e){
			log.error("close con error!");
			log.error(e, e.fillInStackTrace());
		}
		
		
	}

	/**
	 * 关闭连接 释放内存
	 */
	public static void close(Connection con, ResultSet rs) {
		close(rs);
		close(con);
	}
	
	public static void close(Connection con, PreparedStatement prestmt,ResultSet rs) {
		close(rs);
		close(prestmt) ;
		close(con);
	}
	public static void close(Connection con, Statement stmt,ResultSet rs) {
		close(rs);
		close(stmt) ;
		close(con);
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
			log.error("preExecQuery SQLException: ");
			log.error(e, e.fillInStackTrace());
		} catch (Exception e) {
			log.error("preExecQuery Exception: ");
			log.error(e, e.fillInStackTrace());
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
			log.error("preExecUpdateKey SQLException: ");
			log.error(e, e.fillInStackTrace());
		} catch (Exception e) {
			log.error("preExecUpdateKey Exception: ");
			log.error(e, e.fillInStackTrace());
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
			log.error("preExecUpdate SQLException: ");
			//log.error(e, e.fillInStackTrace());
		} catch (Exception e) {
			log.error("preExecUpdate Exception: ");
			//log.error(e, e.fillInStackTrace());
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
			log.error("getPreTotal SQLException: ");
			log.error(e, e.fillInStackTrace());
		} catch (Exception e) {
			log.error("getPreTotal Exception: ");
			log.error(e, e.fillInStackTrace());
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
					log.error(e, e.fillInStackTrace());
				}
			}
		}
	}

	private java.sql.Date util2sql(java.util.Date fechaUtil) {
		java.sql.Date _SQL = new java.sql.Date(fechaUtil.getTime());
		return _SQL;
	}
}
