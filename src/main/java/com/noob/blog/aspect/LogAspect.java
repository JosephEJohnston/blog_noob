package com.noob.blog.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;


@Aspect // AOP 注解
@Component
public class LogAspect {

    // 拿到日志记录器
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    // 以 execution() 来决定拦截哪些类
    // 切面方法应该为空，里面的方法体也不会执行
    @Pointcut("execution(* com.noob.blog.web.*.*(..))") // 成为切面
    public void log() {
    }

    @Before("log()") // 这个方法会在切面方法前执行
    public void doBefore(JoinPoint joinPoint) {
        // 记录 url、ip、类方法、参数
        ServletRequestAttributes attributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String url = request.getRequestURL().toString();
        String ip = request.getRemoteAddr();
        String classMethod = joinPoint.getSignature().getDeclaringTypeName() +
                "." + joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        RequestLog requestLog = new RequestLog(url, ip, classMethod, args);


        logger.info("Request : {}", requestLog);
    }

    @After("log()")
    public void doAfter() {
        //logger.info("------------doAfter------------");
    }

    // returning 为返回内容
    // AfterReturning 在 After 之前
    @AfterReturning(returning = "result", pointcut = "log()")
    public void doAfterReturn(Object result) {
        logger.info("Result : {}", result);
    }

    private class RequestLog {
        private String url;
        private String ip;
        private String classMethod;
        private Object[] args;

        public RequestLog(String url, String ip, String classMethod, Object[] args) {
            this.url = url;
            this.ip = ip;
            this.classMethod = classMethod;
            this.args = args;
        }

        @Override
        public String toString() {
            return "{" +
                    "url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", classMethod='" + classMethod + '\'' +
                    ", args=" + Arrays.toString(args) +
                    '}';
        }
    }
}
