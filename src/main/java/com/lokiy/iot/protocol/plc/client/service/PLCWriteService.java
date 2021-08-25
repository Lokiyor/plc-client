package com.lokiy.iot.protocol.plc.client.service;

import cn.hutool.core.util.StrUtil;
import com.lokiy.iot.protocol.plc.client.common.constant.CommonConstant;
import com.lokiy.iot.protocol.plc.client.common.enums.PLCDataTypeEnum;
import com.lokiy.iot.protocol.plc.client.common.exception.BusinessException;
import com.lokiy.iot.protocol.plc.client.common.util.PlcConnectionUtil;
import com.lokiy.iot.protocol.plc.client.domain.dto.PLCWriteSingleDTO;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.plc4x.java.PlcDriverManager;
import org.apache.plc4x.java.api.PlcConnection;
import org.apache.plc4x.java.api.messages.PlcWriteRequest;
import org.apache.plc4x.java.api.messages.PlcWriteResponse;
import org.apache.plc4x.java.api.types.PlcResponseCode;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

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
//        PLCDataTypeEnum.getPLCDataTypeEnum(dto.getType());
        String connectionIp = CommonConstant.S7_IP_PREFIX.concat(dto.getIp());
        try (PlcConnection plcConnection = new PlcDriverManager().getConnection(connectionIp)) {
            writeBody(dto, plcConnection);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void writeBody(PLCWriteSingleDTO dto, PlcConnection plcConnection) throws ExecutionException, InterruptedException {
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
        builder.addItem(getKey(dto), getPos(dto), Integer.valueOf(dto.getValue()));
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
        return  "%DB".concat(String.valueOf(dto.getDb()))
                .concat(StrUtil.DOT)
                .concat("DB")
                .concat(String.valueOf(dto.getAddress()))
                .concat(StrUtil.COLON)
                .concat(caseType(dto.getType()));
    }

    private String caseType(String type){
        switch (type){
            case "VB":
                return "SINT";
            case "VW":
                return "INT";
            case "VD":
                return "DINT";
            default:
                throw new BusinessException("格式不正确!");
        }
    }


    @SneakyThrows
    public void fixWrite() {
        PlcConnection plcConnection = PlcConnectionUtil.getConnection("192.168.1.100");
        PlcWriteRequest writeRequest = fixPoint(plcConnection);
//        CompletableFuture<? extends PlcWriteResponse> asyncResponse = writeRequest.execute();
//        asyncResponse.whenComplete((response, throwable) -> {
//            for (String fieldName : response.getFieldNames()) {
//                if(response.getResponseCode(fieldName) == PlcResponseCode.OK) {
//                    log.info("Value[" + fieldName + "]: updated");
//                } else {
//                    log.error("Error[" + fieldName + "]: " + response.getResponseCode(fieldName).name());
//                }
//            }
//        });
        PlcWriteResponse response = writeRequest.execute().get();
        for(String fieldName : response.getFieldNames()){
            if(response.getResponseCode(fieldName) != PlcResponseCode.OK) {
                log.error("PLC_ERROR [{}]: {}", fieldName, response.getResponseCode(fieldName).name());
            }
        }
    }

    private PlcWriteRequest fixPoint( PlcConnection plcConnection){
        PlcWriteRequest.Builder builder = plcConnection.writeRequestBuilder();
        builder.addItem("110", "%DB14.DB110:INT", 0);
        builder.addItem("112", "%DB14.DB112:INT", 0);
        builder.addItem("114", "%DB14.DB114:INT", 288);
        builder.addItem("116", "%DB14.DB116:INT", 20108);

        builder.addItem("88", "%DB14.DB88:INT", new Random().nextInt(4) + 1);
        builder.addItem("90", "%DB14.DB90:INT", new Random().nextInt(2));
        builder.addItem("92", "%DB14.DB92:INT", new Random().nextInt(4) + 1);
        builder.addItem("94", "%DB14.DB94:INT", new Random().nextInt(2));

        builder.addItem("102", "%DB14.DB102:INT", new Random().nextInt(3) + 1);
        builder.addItem("104", "%DB14.DB104:INT", new Random().nextInt(2));


        builder.addItem("124", "%DB14.DB124:INT", new Random().nextInt(500));
        builder.addItem("126", "%DB14.DB126:INT", new Random().nextInt(500));
        builder.addItem("128", "%DB14.DB128:INT", new Random().nextInt(500));
        builder.addItem("130", "%DB14.DB130:INT", new Random().nextInt(500));
        builder.addItem("132", "%DB14.DB132:INT", new Random().nextInt(100));

        builder.addItem("140", "%DB14.DB140:INT", new Random().nextInt(2));

        builder.addItem("314", "%DB14.DB314:INT", new Random().nextInt(50));
        builder.addItem("316", "%DB14.DB316:INT", new Random().nextInt(40));
        builder.addItem("318", "%DB14.DB318:INT", new Random().nextInt(2));
        builder.addItem("320", "%DB14.DB320:INT", new Random().nextInt(2));

        builder.addItem("350", "%DB14.DB350:INT", new Random().nextInt(400));

        builder.addItem("358", "%DB14.DB358:INT", new Random().nextInt(2) + 1);
        builder.addItem("360", "%DB14.DB360:INT", new Random().nextInt(100));

        builder.addItem("368", "%DB14.DB368:INT", new Random().nextInt(100) + 400);

        builder.addItem("372", "%DB14.DB372:INT", new Random().nextInt(100) + 400);

        builder.addItem("380", "%DB14.DB380:INT", new Random().nextInt(400));

        builder.addItem("386", "%DB14.DB386:INT", new Random().nextInt(2));
        builder.addItem("388", "%DB14.DB388:INT", new Random().nextInt(32));

        builder.addItem("422", "%DB14.DB422:INT", new Random().nextInt(10000));

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime of = LocalDateTime.of(2021, 8, 25, 0, 0, 0, 0);
        long hours = Duration.between(of, now).toHours();

        builder.addItem("442", "%DB14.DB442:DINT", (long) ( hours * 0.3));
        builder.addItem("448", "%DB14.DB448:DINT", (long) ( hours * 0.4));
        builder.addItem("464", "%DB14.DB464:DINT", hours);
        builder.addItem("496", "%DB14.DB496:INT", new Random().nextInt(2));


        builder.addItem("500", "%DB14.DB500:INT", new Random().nextInt(200));
        builder.addItem("502", "%DB14.DB502:INT", new Random().nextInt(1000));
        builder.addItem("508", "%DB14.DB508:INT", new Random().nextInt(1000));
        builder.addItem("510", "%DB14.DB510:INT", new Random().nextInt(1500));

        builder.addItem("530", "%DB14.DB530:INT", new Random().nextInt(2));


        builder.addItem("598", "%DB14.DB598:INT", new Random().nextInt(2)  == 1 ? new Random().nextInt(200) * (-1) : new Random().nextInt(500));
        builder.addItem("600", "%DB14.DB600:INT", new Random().nextInt(2)  == 1 ? new Random().nextInt(200) * (-1) : new Random().nextInt(500));
        builder.addItem("602", "%DB14.DB602:INT", new Random().nextInt(100));
        builder.addItem("604", "%DB14.DB604:INT", new Random().nextInt(2)  == 1 ? new Random().nextInt(200) * (-1) : new Random().nextInt(500));

        builder.addItem("608", "%DB14.DB608:INT", new Random().nextInt(2));
        builder.addItem("610", "%DB14.DB610:INT", new Random().nextInt(250) + 100);


        builder.addItem("620", "%DB14.DB620:INT", new Random().nextInt(2));
        builder.addItem("622", "%DB14.DB622:INT", new Random().nextInt(2));
        builder.addItem("624", "%DB14.DB624:INT", new Random().nextInt(2));
        builder.addItem("626", "%DB14.DB626:INT", new Random().nextInt(2));

        builder.addItem("632", "%DB14.DB632:INT", new Random().nextInt(2));

        return builder.build();
    }

    public void batchWrite(List<PLCWriteSingleDTO> dto) {
        dto.forEach(this::singleWrite);
    }
}
