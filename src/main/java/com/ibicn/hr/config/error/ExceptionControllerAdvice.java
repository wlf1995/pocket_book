package com.ibicn.hr.config.error;

import com.ibicn.hr.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常处理
 */
@Slf4j
@RestControllerAdvice
public class ExceptionControllerAdvice {
    @ExceptionHandler(Exception.class)
    public Result jsonErrorHandler(HttpServletRequest req, Exception e) {
        e.printStackTrace();
        log.error("出现错误。错误信息：" + e.getClass());
        return Result.failure("出现错误，请联系技术。错误信息：" + e.getClass());
    }
}