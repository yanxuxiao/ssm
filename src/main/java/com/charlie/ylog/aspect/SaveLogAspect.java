package com.charlie.ylog.aspect;

import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import com.charlie.ylog.annotion.SaveLog;

@Aspect
@Component
public class SaveLogAspect {
	private final Logger logger = Logger.getLogger(SaveLogAspect.class);
	
	//切点
	@Pointcut("@within(com.charlie.ylog.annotion.SaveLog)||@annotation(com.charlie.ylog.annotion.SaveLog)")
	public void mark(){}
	
	/**
	 * 环绕通知
	 * @param pJoinPoint
	 * @return
	 * @throws Throwable
	 */
	@Around(value="mark()")
	public Object saveLog(ProceedingJoinPoint joinPoint) throws Throwable{
		Object result = null;
		SaveLog log = null ;
		Method method = null;
		
		StringBuffer stringBuffer = new StringBuffer();

		//方法执行前记录
		try {
			//通过反射获取拦截的方法
//			cla = pJoinPoint.getTarget().getClass();

			//得到方法上的接口日志打印注解
			Signature sig = joinPoint.getSignature();
			if (sig instanceof MethodSignature) {
				method = ((MethodSignature) sig).getMethod();

				log = ((MethodSignature) sig).getMethod().getAnnotation(SaveLog.class);
				if (log == null) {
					log = ((MethodSignature) sig).getMethod().getDeclaringClass()
							.getAnnotation(SaveLog.class);
				}
			} else {
				logger.info("SaveLog annotation is not used at method");
			}
			//方法名
//			String methodName = signature.substring(signature.lastIndexOf(".")+1, signature.indexOf("("));

			stringBuffer.append("***********" + sig.toString() + "***********");
			stringBuffer.append(System.getProperty("line.separator"));
			
			//记录方法请求参数
			if(log.saveRequests()){
				Object[] parames = joinPoint.getArgs();  //目标方法参数
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
			result = joinPoint.proceed();
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