package com.lokiy.iot.protocol.plc.client.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author Lokiy
 * @Date 2021/7/19 16:05
 * @Description 错误码枚举
 */
@AllArgsConstructor
@Getter
public enum CodeEnum {

    /**
     * 成功标识
     */
    SUCCESS(200, "SUCCESS"),

    UNKNOWN_ERROR( 9000, "未知错误"),

    BUSINESS_ERROR( 500, "业务异常"),

    /**
     * 入参参数校验错误
     */
    VALIDATE_FAILED(1001,"参数校验错误");

    private Integer code;

    private String msg;


    public static CodeEnum getErrorCodeEnum(Integer code) {
        for (CodeEnum ce : values()) {
            if (ce.code.equals(code)) {
                return ce;
            }
        }
        return UNKNOWN_ERROR;
    }
}
