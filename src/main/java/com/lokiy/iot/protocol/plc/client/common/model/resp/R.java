package com.lokiy.iot.protocol.plc.client.common.model.resp;

import com.lokiy.iot.protocol.plc.client.common.enums.CodeEnum;
import com.lokiy.iot.protocol.plc.client.common.exception.BusinessException;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author Lokiy
 * @Date 2021/7/19 16:13
 * @Description 返回结果
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("返回对象")
public class R<T> implements Serializable {

    /**
     * 成功标记
     */
    @ApiModelProperty("成功标识")
    private boolean success;

    /**
     * 编码
     */
    @ApiModelProperty("返回编码")
    private Integer code;

    /**
     * 提示信息
     */
    @ApiModelProperty("提示信息")
    private String msg;

    /**
     * 数据
     */
    @ApiModelProperty("返回数据")
    private T data;


    /**
     * 返回结果
     * @param data 返回数据
     * @param <T> 数据类型
     * @return 返回结果
     */
    public static <T> R result(T data){
        return new RBuilder<>()
                .success(true)
                .code(CodeEnum.SUCCESS.getCode())
                .msg(CodeEnum.SUCCESS.getMsg())
                .data(data)
                .build();
    }


    /**
     * 返回成功
     * @return
     */
    public static R success(){
        return result("sucess");
    }

    /**
     * 返回标识
     * @param codeEnum
     * @return
     */
    public R codeEnum(CodeEnum codeEnum){
        this.code = codeEnum.getCode();
        this.msg = codeEnum.getMsg();
        return this;
    }

    /**
     * 返回错误信息
     * @param errorCode
     * @param msg
     * @return
     */
    public static R error(Integer errorCode, String msg){
        R result = new R();
        result.setCode(errorCode);
        result.setMsg(msg);
        return result;
    }

    /**
     * 返回错误信息
     * @param exception
     * @return
     */
    public static R error(BusinessException exception){
        R result = new R();
        result.setCode(exception.getCode());
        result.setMsg(exception.getMessage());
        return result;
    }
}