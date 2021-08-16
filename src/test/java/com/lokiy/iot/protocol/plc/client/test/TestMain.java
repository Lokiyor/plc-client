package com.lokiy.iot.protocol.plc.client.test;

import com.lokiy.iot.protocol.plc.client.common.constant.CommonConstant;
import org.apache.plc4x.java.PlcDriverManager;
import org.apache.plc4x.java.api.PlcConnection;
import org.apache.plc4x.java.api.messages.PlcWriteRequest;
import org.apache.plc4x.java.api.messages.PlcWriteResponse;
import org.apache.plc4x.java.api.types.PlcResponseCode;

/**
 * @Author Lokiy
 * @Date 2021/8/9 10:39
 * @Description
 */
public class TestMain {

    public static void main(String[] args) {
        String connectionIp = CommonConstant.S7_IP_PREFIX.concat("127.0.0.1");
        try (PlcConnection plcConnection = new PlcDriverManager().getConnection(connectionIp)) {
            if (!plcConnection.getMetadata().canWrite()) {
                System.err.println("This connection doesn't support writing.");
                return;
            }
            PlcWriteRequest.Builder builder = plcConnection.writeRequestBuilder();
            builder.addItem("aaa", "%DB.DB1.4:INT[3]", 7, 23, 42 );
            PlcWriteRequest writeRequest = builder.build();
            PlcWriteResponse response = writeRequest.execute().get();
            String fieldName = response.getFieldNames().toArray(new String[0])[0];
            if(response.getResponseCode(fieldName) != PlcResponseCode.OK) {
                System.err.println("error:" + fieldName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
