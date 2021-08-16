package com.lokiy.iot.protocol.plc.client.test;

import com.lokiy.iot.protocol.plc.client.common.constant.CommonConstant;
import lombok.SneakyThrows;
import org.apache.plc4x.java.PlcDriverManager;
import org.apache.plc4x.java.api.PlcConnection;
import org.apache.plc4x.java.api.messages.PlcReadRequest;
import org.apache.plc4x.java.api.messages.PlcReadResponse;
import org.apache.plc4x.java.api.messages.PlcWriteRequest;
import org.apache.plc4x.java.api.messages.PlcWriteResponse;
import org.apache.plc4x.java.api.types.PlcResponseCode;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @Author Lokiy
 * @Date 2021/8/9 10:39
 * @Description
 */
public class TestMain {

    public static void main(String[] args) {
        String connectionIp = CommonConstant.S7_IP_PREFIX.concat("127.0.0.1");
        try (PlcConnection plcConnection = new PlcDriverManager().getConnection(connectionIp)) {
            writeBody(plcConnection);
//            readBody(plcConnection);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void writeBody(PlcConnection plcConnection) throws ExecutionException, InterruptedException {
        PlcWriteRequest.Builder builder = plcConnection.writeRequestBuilder();
        builder.addItem("aaa", "%DB1.DB0:INT", 0 );
        PlcWriteRequest writeRequest = builder.build();
        PlcWriteResponse response = writeRequest.execute().get();
        String fieldName = response.getFieldNames().toArray(new String[0])[0];
        if(response.getResponseCode(fieldName) != PlcResponseCode.OK) {
            System.err.println("error:" + fieldName);
        }
    }


    public static void readBody(PlcConnection plcConnection) throws ExecutionException, InterruptedException, TimeoutException {
        String fieldName = "value-4";
        PlcReadRequest.Builder builder = plcConnection.readRequestBuilder();
        builder.addItem(fieldName, "%DB1.DB4:INT");
        PlcReadRequest readRequest = builder.build();
        PlcReadResponse response = readRequest.execute().get(1000, TimeUnit.MILLISECONDS);
        if(response.getResponseCode(fieldName) == PlcResponseCode.OK) {
            int numValues = response.getInteger(fieldName);
            System.err.println(numValues);
        }
    }
}
