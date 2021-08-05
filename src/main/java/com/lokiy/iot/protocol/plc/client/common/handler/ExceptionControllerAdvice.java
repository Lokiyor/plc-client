package com.lokiy.iot.protocol.plc.client.common.handler;

import com.lokiy.iot.protocol.plc.client.common.enums.CodeEnum;
import com.lokiy.iot.protocol.plc.client.common.exception.BusinessException;
import com.lokiy.iot.protocol.plc.client.common.model.resp.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Author Lokiy
 * @Date 2021/7/19 16:21
 * @Description 全局异常处理
 */
@RestControllerAdvice
@Slf4j
public class ExceptionControllerAdvice {

    /**
     * 入参校验异常处理
     *
     * @param e 入参校验异常
     * @return 返回信息
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        // 从异常对象中拿到ObjectError对象
        ObjectError objectError = e.getBindingResult().getAllErrors().get(0);
        // 然后提取错误提示信息进行返回
        log.error("全局异常------>{}", e.getMessage(), e);
        return R.error(CodeEnum.VALIDATE_FAILED.getCode(), objectError.getDefaultMessage());
    }

    /**
     * 业务异常
     *
     * @param e 业务异常
     * @return 返回信息
     */
    @ExceptionHandler(BusinessException.class)
    public R businessExceptionHandler(BusinessException e) {
        // 业务级异常返回
        log.error("全局异常------>{}", e.getMessage(), e);
        return R.error(e);
    }


    /**
     * 入参校验异常处理
     *
     * @param e 入参校验异常
     * @return 返回信息
     */
    @ExceptionHandler(Exception.class)
    public R exceptionHandler(Exception e) {
        // 未知异常返回
        log.error("全局异常------>{}", e.getMessage(), e);
        return R.error(CodeEnum.UNKNOWN_ERROR.getCode(), CodeEnum.UNKNOWN_ERROR.getMsg());
    }

}
