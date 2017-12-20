package com.beacon.global.aspect;

import com.beacon.commons.utils.JsonUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

/**
 * Aspect for logging execution of service and dao Spring components.
 *
 * @author luckyhua
 * @version 1.0
 * @since 2017/9/19
 */
@Aspect
@Component
public class LogAspect {

    private static final Logger log = LoggerFactory.getLogger(LogAspect.class);

    /**
     * 开始请求时间
     */
    private ThreadLocal<Long> startTime = new ThreadLocal<>();


    @Pointcut("execution(public * com.beacon.api.*.controller..*.*(..))")
    public void logPointcut() {
    }

    @Before("logPointcut()")
    public void doBefore(JoinPoint joinPoint) {
        if (log.isInfoEnabled()) {
            startTime.set(System.currentTimeMillis());
            // 接收到请求，记录请求内容
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();

            Signature signature = joinPoint.getSignature();
            String builder = "\n**********************************************request*********************************************" +
                    "\n* URL = " + request.getRequestURL() +
                    "\n* METHOD = " + request.getMethod() +
                    "\n* JOIN = " + signature.getDeclaringType().getSimpleName() + "." + signature.getName() +
                    "\n* ARGS = " + Arrays.toString(joinPoint.getArgs());
            log.debug(builder);
        }
    }

    /**
     * 后置返回通知:
     * 如果参数中的第一个参数为JoinPoint，则第二个参数为返回值的信息
     * 如果参数中的第一个参数不为JoinPoint，则第一个参数为returning中对应的参数
     * returning 限定了只有目标方法返回值与通知方法相应参数类型时才能执行后置返回通知，
     * 否则不执行，对于returning对应的通知方法参数为Object类型将匹配任何目标返回值
     */
    @AfterReturning(value = "logPointcut()", returning = "result")
    public void doAfterReturning(JoinPoint joinPoint, Object result) {
        if (log.isDebugEnabled()) {
            // 接收到请求，记录请求内容
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();
            HttpServletResponse response = attributes.getResponse();

            String builder = "\n***********************************************response********************************************" +
                    "\n* URL = " + request.getRequestURL() +
                    "\n* STATUS = " + response.getStatus() +
                    "\n* TIME = " + (System.currentTimeMillis() - startTime.get()) + "ms" +
                    "\n* RESULT = " + JsonUtils.objectToJson(result);
            log.debug(builder);
            startTime.remove();
        }
    }
}

