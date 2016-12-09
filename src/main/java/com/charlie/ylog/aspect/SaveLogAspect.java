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
	
	//�е�
	@Pointcut("execution(* com.charlie.*.controller..*.*(..))&&@annotation(log)") 
	public void mark(SaveLog log){}
	
	/**
	 * ����֪ͨ
	 * @param pJoinPoint
	 * @param log @SaveLogע��
	 * @return
	 * @throws Throwable
	 */
	@Around("mark(log)")
	public Object saveLog(ProceedingJoinPoint pJoinPoint,SaveLog log) throws Throwable{
		Object result = null;
//		Class<? extends Object> cla = null;
		StringBuffer stringBuffer = new StringBuffer();

		//����ִ��ǰ��¼
		try {
			//ͨ�������ȡ���صķ���
//			cla = pJoinPoint.getTarget().getClass();

			//����ǩ��
			String signature = pJoinPoint.getSignature().toString();
			//������
//			String methodName = signature.substring(signature.lastIndexOf(".")+1, signature.indexOf("("));

			stringBuffer.append("***********" + signature.toString() + "***********");
			stringBuffer.append(System.getProperty("line.separator"));
			
			//��¼�����������
			if(log.saveRequests()){
				Object[] parames = pJoinPoint.getArgs();  //Ŀ�귽������
				String paramStr = parseParames(parames);
				stringBuffer.append("*******Parames��" + paramStr);
				stringBuffer.append(System.getProperty("line.separator"));
			}
			
			//��¼��ע
			stringBuffer.append("*******Note��" + log.note());
			stringBuffer.append(System.getProperty("line.separator"));
			
		
		} catch (Exception e) {
			stringBuffer.append(e.getStackTrace());
			stringBuffer.append(System.getProperty("line.separator"));
		}finally {
			result = pJoinPoint.proceed();
		}
		
		//����ִ�к��¼
		stringBuffer.append("***********end***********");
		stringBuffer.append(System.getProperty("line.separator"));
		
		logger.info(stringBuffer.toString());
		return result;
	}
	
	/**
	 * ���������ʽת��
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