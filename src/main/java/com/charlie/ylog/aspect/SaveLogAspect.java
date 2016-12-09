package com.charlie.ylog.aspect;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.charlie.ylog.annotion.SaveLog;

@Aspect
@Component
public class SaveLogAspect {
	private final Logger logger = Logger.getLogger(SaveLogAspect.class);
	
	//切点
	@Pointcut("execution(* com.charlie.*.controller..*.*(..))&&@annotation(log)") 
	public void mark(SaveLog log){}
	
	/**
	 * 环绕通知
	 * @param pJoinPoint
	 * @param log @SaveLog注解
	 * @return
	 * @throws Throwable
	 */
	@Around("mark(log)")
	public Object saveLog(ProceedingJoinPoint pJoinPoint,SaveLog log) throws Throwable{
		Object result = null;
//		Class<? extends Object> cla = null;
		StringBuffer stringBuffer = new StringBuffer();

		//方法执行前记录
		try {
			//通过反射获取拦截的方法
//			cla = pJoinPoint.getTarget().getClass();

			//方法签名
			String signature = pJoinPoint.getSignature().toString();
			//方法名
//			String methodName = signature.substring(signature.lastIndexOf(".")+1, signature.indexOf("("));

			stringBuffer.append("***********" + signature.toString() + "***********");
			stringBuffer.append(System.getProperty("line.separator"));
			
			//记录方法请求参数
			if(log.saveRequests()){
				Object[] parames = pJoinPoint.getArgs();  //目标方法参数
				String paramStr = parseParames(parames);
				stringBuffer.append("*******Parames：" + paramStr);
				stringBuffer.append(System.getProperty("line.separator"));
			}
			
			//记录备注
			stringBuffer.append("*******Note：" + log.note());
			stringBuffer.append(System.getProperty("line.separator"));
			
		
		} catch (Exception e) {
			stringBuffer.append(e.getStackTrace());
			stringBuffer.append(System.getProperty("line.separator"));
		}finally {
			result = pJoinPoint.proceed();
		}
		
		//方法执行后记录
		stringBuffer.append("***********end***********");
		stringBuffer.append(System.getProperty("line.separator"));
		
		logger.info(stringBuffer.toString());
		return result;
	}
	
	/**
	 * 请求参数格式转换
	 * @param parames
	 * @return
	 */
	private String parseParames(Object[] parames){
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("[");
		if(parames != null && parames.length > 0){
			for(Object object : parames){
				stringBuffer.append(object.toString() + ",");
			}
			stringBuffer.deleteCharAt(stringBuffer.length() - 1);
		}
		stringBuffer.append("]");
		
		return stringBuffer.toString();
	}
}