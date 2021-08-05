package com.lokiy.iot.protocol.plc.client.domain.dto;

import com.lokiy.iot.protocol.plc.client.common.enums.PLCDataTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author Lokiy
 * @Date 2021/8/5 11:06
 * @Description plc单写对象
 */
@Data
@ApiModel("plc单写对象")
public class PLCWriteSingleDTO {

    @ApiModelProperty("plc-ip")
    private String ip;

    @ApiModelProperty("plc数据库db")
    private Integer db;

    @ApiModelProperty("读取位置")
    private Integer address;

    /**
     * {@link PLCDataTypeEnum}
     */
    @ApiModelProperty("plc数据类型")
    private String type;
}
