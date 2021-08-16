package com.lokiy.iot.protocol.plc.client.domain.dto;

import com.lokiy.iot.protocol.plc.client.common.enums.PLCDataTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author Lokiy
 * @Date 2021/8/5 11:06
 * @Description plc单写对象
 */
@Data
@ApiModel("plc单写对象")
public class PLCWriteSingleDTO {

    @ApiModelProperty(value = "plc-ip", example = "127.0.0.1", required = true)
    @NotBlank(message = "plc-ip必填")
    private String ip;

    @ApiModelProperty(value = "plc数据库db", example = "1", required = true)
    @NotNull(message = "plc数据库db必填")
    private Integer db;

    @ApiModelProperty(value = "读取位置", example = "0", required = true)
    @NotNull(message = "plc读取位置必填")
    private Integer address;

    /**
     * {@link PLCDataTypeEnum}
     */
    @ApiModelProperty(value = "plc数据类型", example = "VW", required = true, allowableValues = "VB,VW,VD")
    @NotBlank(message = "plc数据类型必填")
    private String type;

    @ApiModelProperty(value = "写入数值", required = true, example = "1")
    @NotBlank(message = "写入数值不能为空")
    private String value;
}
