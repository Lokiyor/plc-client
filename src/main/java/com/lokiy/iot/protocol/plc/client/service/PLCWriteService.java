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
import java.util.Random;
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


    private PlcWriteRequest writeRequest(PLCWriteSingleDTO dto, PlcConnection plcConnection){
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



    public void fixWrite() {
        PlcConnection plcConnection = PlcConnectionUtil.getConnection("127.0.0.1");
        PlcWriteRequest writeRequest = fixPoint(plcConnection);
        CompletableFuture<? extends PlcWriteResponse> asyncResponse = writeRequest.execute();
        asyncResponse.whenComplete((response, throwable) -> {
            for (String fieldName : response.getFieldNames()) {
                if(response.getResponseCode(fieldName) == PlcResponseCode.OK) {
                    log.info("Value[" + fieldName + "]: updated");
                } else {
                    log.error("Error[" + fieldName + "]: " + response.getResponseCode(fieldName).name());
                }
            }
        });
    }

    private PlcWriteRequest fixPoint( PlcConnection plcConnection){
        PlcWriteRequest.Builder builder = plcConnection.writeRequestBuilder();
        builder.addItem("1", "%DB.DB1.0:INT", 0);
        builder.addItem("2", "%DB.DB1.2:INT", 0);
        builder.addItem("3", "%DB.DB1.4:INT", 288);
        builder.addItem("4", "%DB.DB1.6:INT", 20108);

        builder.addItem("5", "%DB.DB1.16:INT", 1);

        builder.addItem("6", "%DB.DB1.32:INT", new Random().nextInt(2));
        builder.addItem("7", "%DB.DB1.34:INT", new Random().nextInt(4) + 1);
        builder.addItem("8", "%DB.DB1.36:INT", new Random().nextInt(2) + 1);
        builder.addItem("9", "%DB.DB1.38:INT", new Random().nextInt(2));

        builder.addItem("10", "%DB.DB2.0:INT", new Random().nextInt(2));
        builder.addItem("11", "%DB.DB2.2:INT", new Random().nextInt(200) + 100);
        builder.addItem("12", "%DB.DB2.4:INT", new Random().nextInt(20) + 10);


        builder.addItem("13", "%DB.DB2.16:LONG", 490L);
        builder.addItem("14", "%DB.DB2.20:LONG", 760L);
        builder.addItem("15", "%DB.DB2.24:LONG", 6500L);

        builder.addItem("16", "%DB.DB1.48:INT", new Random().nextInt(2));
        builder.addItem("17", "%DB.DB1.50:INT", new Random().nextInt(4) + 1);
        builder.addItem("18", "%DB.DB1.52:INT", new Random().nextInt(2) + 1);
        builder.addItem("19", "%DB.DB1.54:INT", new Random().nextInt(2));
        builder.addItem("20", "%DB.DB1.56:INT", new Random().nextInt(40));


        builder.addItem("21", "%DB.DB3.0:INT", new Random().nextInt(100) + 100);
        builder.addItem("22", "%DB.DB3.2:INT", new Random().nextInt(100) + 300);
        builder.addItem("23", "%DB.DB3.4:INT", new Random().nextInt(100) + 200);
        builder.addItem("24", "%DB.DB3.6:INT", new Random().nextInt(100) + 400);

        return builder.build();
    }
}
