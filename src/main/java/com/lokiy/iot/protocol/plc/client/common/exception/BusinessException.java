package com.lokiy.iot.protocol.plc.client.common.exception;

import com.lokiy.iot.protocol.plc.client.common.enums.CodeEnum;
import lombok.Data;

/**
 * @Author Lokiy
 * @Date 2021/7/19 15:34
 * @Description 业务异常
 */
@Data
public class BusinessException extends RuntimeException{

    /**
     * 异常编码
     */
    protected Integer code;

    public BusinessException(String msg) {
        super(msg);
    }

    public BusinessException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }

    public BusinessException(Integer code, String msg){
        super(msg);
        this.code = code;
    }

    public BusinessException(Integer code, String msgFormat, Object... args) {
        super(String.format(msgFormat, args));
        this.code = code;
    }

    public BusinessException(CodeEnum errorCodeEnum, Object... args) {
        super(String.format(errorCodeEnum.getMsg(), args));
        this.code = errorCodeEnum.getCode();
    }
}
