package com.lokiy.iot.protocol.plc.client.service;

import cn.hutool.core.util.StrUtil;
import com.lokiy.iot.protocol.plc.client.common.enums.PLCDataTypeEnum;
import com.lokiy.iot.protocol.plc.client.common.exception.BusinessException;
import com.lokiy.iot.protocol.plc.client.common.util.PlcConnectionUtil;
import com.lokiy.iot.protocol.plc.client.domain.dto.PLCWriteSingleDTO;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.plc4x.java.api.PlcConnection;
import org.apache.plc4x.java.api.messages.PlcWriteRequest;
import org.apache.plc4x.java.api.messages.PlcWriteResponse;
import org.apache.plc4x.java.api.types.PlcResponseCode;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * @Author Lokiy
 * @Date 2021/8/5 10:36
 * @Description plc写业务类
 */
@Service
@Slf4j
public class PLCWriteService {

    /**
     * plc单写操作
     * @param dto 写操作dto
     */
    @SneakyThrows
    public void singleWrite(PLCWriteSingleDTO dto) {
        PLCDataTypeEnum.getPLCDataTypeEnum(dto.getType());
        PlcConnection plcConnection = PlcConnectionUtil.getConnection(dto.getIp());
        PlcWriteRequest writeRequest = writeRequest(dto, plcConnection);
        PlcWriteResponse response = writeRequest.execute().get();
        String fieldName = response.getFieldNames().toArray(new String[0])[0];
        if(response.getResponseCode(fieldName) != PlcResponseCode.OK) {
            log.error("Error[{}]: {}", fieldName, response.getResponseCode(fieldName).name());
            throw new BusinessException("PLC写操作错误");
        }
    }


    private PlcWriteRequest writeRequest(PLCWriteSingleDTO dto,  PlcConnection plcConnection){
        PlcWriteRequest.Builder builder = plcConnection.writeRequestBuilder();
        builder.addItem(getKey(dto), getPos(dto), dto.getValue());
        return builder.build();
    }

    private String getKey(PLCWriteSingleDTO dto){
        return dto.getIp()
                .concat(StrUtil.COLON)
                .concat(String.valueOf(dto.getDb()))
                .concat(StrUtil.COLON)
                .concat(String.valueOf(dto.getAddress()));
    }

    private String getPos(PLCWriteSingleDTO dto){
        return  "%DB.DB".concat(String.valueOf(dto.getDb()))
                .concat(StrUtil.DOT)
                .concat(String.valueOf(dto.getAddress()))
                .concat(StrUtil.COLON)
                .concat(dto.getType());
    }
}
