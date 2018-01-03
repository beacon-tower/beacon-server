package com.beacon.commons.exception.handler;

import com.beacon.commons.exception.ResException;
import com.beacon.commons.response.ResData;
import com.beacon.commons.utils.ExceptionUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import static com.beacon.commons.code.PublicResCode.*;

/**
 * 全局异常处理
 *
 * @author luckyhua
 * @version 1.0
 * @since 2017/9/19
 */
@RestController
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    public Object defaultExceptionHandler(HttpServletRequest req, Exception e) throws Exception {
        log.error("ZGH10060: DefaultException Handler---Host {} invokes url {} ERROR: {}",
                req.getRemoteHost(), req.getRequestURL(), e.getMessage());
        log.error("ZGH10210: exception = {} ", ExceptionUtils.getTrace(e));
        return ResData.build(Boolean.FALSE);
    }

    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    public Object missingServletRequestParameterExceptionHandler(HttpServletRequest req, MissingServletRequestParameterException e) throws Exception {
        log.error("ZGH10090: MissingServletRequestParameterException Handler---Host {} invokes url {} ERROR: {}",
                req.getRemoteHost(), req.getRequestURL(), e.getMessage());
        String msg = PARAMS_IS_NULL.getMsg().replace("{0}", e.getParameterName());
        return ResData.error(PARAMS_IS_NULL, msg);
    }

    @ExceptionHandler(value = TypeMismatchException.class)
    public Object typeMismatchExceptionHandler(HttpServletRequest req, TypeMismatchException e) throws Exception {
        log.error("ZGH10090: TypeMismatchException Handler---Host {} invokes url {} ERROR: {}",
                req.getRemoteHost(), req.getRequestURL(), e.getMessage());
        String msg = PARAMS_TYPE_EXCEPTION.getMsg()
                .replace("{0}", e.getPropertyName())
                .replace("{1}", e.getRequiredType().toString());
        return ResData.error(PARAMS_TYPE_EXCEPTION, msg);
    }

    @ExceptionHandler(value = UnauthorizedException.class)
    public Object unauthorizedExceptionHandler(HttpServletRequest req, UnauthorizedException e) throws Exception {
        log.error("ZGH10090: unauthorizedExceptionHandler Handler---Host {} invokes url {} ERROR: {}",
                req.getRemoteHost(), req.getRequestURL(), e.getMessage());
        return ResData.error(NOT_AUTHORIZED);
    }

    @ExceptionHandler(value = ResException.class)
    public Object resExceptionHandler(HttpServletRequest req, ResException e) throws Exception {
        log.error("ZGH10060: ResponseException Handler---Host {} invokes url {} ERROR: {}",
                req.getRemoteHost(), req.getRequestURL(), e.getMessage());
        return ResData.error(e.getResCode(), e.getMessage());
    }
}
