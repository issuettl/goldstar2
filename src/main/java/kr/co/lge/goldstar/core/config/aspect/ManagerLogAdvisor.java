package kr.co.lge.goldstar.core.config.aspect;

import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;

import org.apache.commons.lang.ArrayUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import kr.co.lge.goldstar.mvc.c.request.service.RequestService;
import kr.co.lge.goldstar.mvc.m.manager.service.ManagerLogExecution;
import kr.co.lge.goldstar.mvc.m.manager.service.ManagerService;
import kr.co.lge.goldstar.orm.jpa.entity.YesOrNo;
import kr.co.lge.goldstar.orm.jpa.entity.member.ManagerEntity;
import kr.co.lge.goldstar.orm.jpa.entity.member.ManagerLogEntity;
import kr.co.lge.goldstar.orm.jpa.repository.spring.ManagerLogRepository;
import kr.co.rebel9.core.utils.DateUtils;
import kr.co.rebel9.web.data.DataMap;

@Aspect
@Order(1)
@Component
public class ManagerLogAdvisor {
	
	@Autowired
	private ManagerLogRepository managerLogRepository;
	
	@Autowired
	private ManagerService managerService;
	
	@Autowired
	private RequestService requestService;
	
	@Around("@annotation(kr.co.lge.goldstar.mvc.m.manager.service.ManagerLogExecution)")
	public Object logExecution(ProceedingJoinPoint joinPoint) throws Throwable {
		
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		
		Class<? extends Object> clazz = joinPoint.getTarget().getClass();
		String methodName = joinPoint.getSignature().getName();
		
		Class<? extends Object>[] parameterTypes = signature.getMethod().getParameterTypes();
		Method method = clazz.getDeclaredMethod(methodName, parameterTypes);
		
		Object[] args = joinPoint.getArgs();
		
		ManagerLogExecution logExecution = method.getAnnotation(ManagerLogExecution.class);

		ManagerLogEntity managerLog = new ManagerLogEntity();
		
		ManagerEntity manager = this.managerService.getSigned();
		
		managerLog.setIp(this.requestService.getIp());
		managerLog.setStarted(DateUtils.getToday("yyyyMMddHHmmssSSS"));
		managerLog.setProcessName(logExecution.process());
		managerLog.setMenuName(logExecution.menu());
		managerLog.setButtonName(logExecution.button());
		
		try {
			Object proceed = joinPoint.proceed();
			managerLog.setSuccess(YesOrNo.Y);
			if(!ObjectUtils.isEmpty(manager)) {
				managerLog.setManagerSn(manager.getSn());
				managerLog.setManagerName(manager.getName());
				managerLog.setManagerCompany(manager.getCompany());
			}
			
			if(args.length > 0) {
				StringBuilder message = new StringBuilder();
				for(Object arg : args) {
					if(arg != null) {
						message.append(arg.toString()).append("\n\n");
					}
				}
				
				managerLog.setParams(message.toString());
			}
			
			if(proceed instanceof DataMap) {
				DataMap returnData = (DataMap) proceed;
				managerLog.setResult(returnData.toString());
			}
			
			return proceed;
			
		} catch (Throwable e) {
			managerLog.setSuccess(YesOrNo.N);
			managerLog.setResult(e.getMessage());
			
			throw e;
		} finally {
			if(StringUtils.hasText(managerLog.getResult()) && managerLog.getResult().getBytes(StandardCharsets.UTF_8).length > 4000) {
				byte[] result = managerLog.getResult().getBytes(StandardCharsets.UTF_8);
				result = ArrayUtils.subarray(result, 0, 3998);
				managerLog.setResult(new String(result, StandardCharsets.UTF_8));
			}
			
			managerLog.setFinished(DateUtils.getToday("yyyyMMddHHmmssSSS"));
			managerLog.setCreated(DateUtils.getToday("yyyyMMddHHmmss"));
			this.managerLogRepository.save(managerLog);
		}
	}
}
