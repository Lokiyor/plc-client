package com.lokiy.iot.protocol.plc.client.common.util;

import com.github.benmanes.caffeine.cache.Cache;
import com.lokiy.iot.protocol.plc.client.common.constant.CommonConstant;
import com.lokiy.iot.protocol.plc.client.common.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.plc4x.java.PlcDriverManager;
import org.apache.plc4x.java.api.PlcConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @Author Lokiy
 * @Date 2021/8/5 10:39
 * @Description
 */
@Component
@Slf4j
public class PlcConnectionUtil {

    private static Cache<String,PlcConnection> plcCache;

    @Autowired
    public void setPlcCache(Cache<String, PlcConnection> plcCache) {
        PlcConnectionUtil.plcCache = plcCache;
    }

    /**
     * 获取plc连接
     * @param ip plc ip
     * @return plc连接
     */
    public static PlcConnection getConnection(String ip){
        PlcConnection connection = plcCache.getIfPresent(ip);
        if(Objects.nonNull(connection)){
            return connection;
        }
        String connectionIp = CommonConstant.S7_IP_PREFIX.concat(ip);
        try (PlcConnection plcConnection = new PlcDriverManager().getConnection(connectionIp)) {
            plcCache.put(ip, plcConnection);
            return plcConnection;
        } catch (Exception e) {
            log.error("PLC连接失败------>{}",e.getMessage(), e);
            throw new BusinessException("PLC连接失败!");
        }
    }

}
