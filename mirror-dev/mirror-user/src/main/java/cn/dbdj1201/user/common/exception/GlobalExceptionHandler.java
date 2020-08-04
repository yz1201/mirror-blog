package cn.dbdj1201.user.common.exception;

import cn.dbdj1201.user.entity.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.ShiroException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

/**
 * @Author: dbdj1201
 * @Date: 2020-08-04 11:08
 * 全局异常处理
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    //捕捉shiro异常
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(ShiroException.class)
    public CommonResult<Object> handle401(ShiroException e) {
        return new CommonResult<>(401, e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public CommonResult<Object> handler(IllegalArgumentException e) throws IOException {
        log.error("Assert异常：------>{}", e.getMessage());
        return new CommonResult<>(e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public CommonResult<Object> handler(MethodArgumentNotValidException e) throws IOException {

        log.error("实体校验异常：------>{}", e.getMessage());

        return new CommonResult<>(e.getBindingResult().getAllErrors()
                .stream().findFirst().get().getDefaultMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = RuntimeException.class)
    public CommonResult<Object> handler(RuntimeException e) {
        log.error("运行时异常：----------------{}", e.getMessage());
        return new CommonResult<>(e.getMessage());
    }

}
