package com.noob.blog.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice // 拦截所有含有 @Controller 的控制器
public class ControllerExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(Exception.class)
    public ModelAndView exceptionHandler(HttpServletRequest request,
                                         Exception e) throws Exception {
        logger.error("Request URL : {}, Exception {}",
                request.getRequestURL(), e);

        // 若异常的类已自定义，则抛出此异常
        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class)
            != null) {
            // 由 Spring Boot 处理，测试中就是跳转到 404 页面
            throw e;
        }

        ModelAndView mv = new ModelAndView();
        mv.addObject("url", request.getRequestURL());
        mv.addObject("exception", e);
        mv.setViewName("error/error");
        return mv;
    }
}
