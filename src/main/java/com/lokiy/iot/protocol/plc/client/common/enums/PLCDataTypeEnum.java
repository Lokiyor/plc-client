package com.lokiy.iot.protocol.plc.client.common.enums;

import com.google.common.collect.Maps;
import com.lokiy.iot.protocol.plc.client.common.exception.BusinessException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;
import java.util.Objects;

/**
 * @Author Lokiy
 * @Date 2021/8/5 11:16
 * @Description plcData类型
 */
@AllArgsConstructor
@Getter
public enum PLCDataTypeEnum {

    /**
     * Bit-Strings
     */
    BOOL("BOOL"),
    BYTE("BYTE"),
    WORD("WORD"),
    DWORD("DWORD"),
    LWORD("LWORD"),

    /**
     * Integer values
     */
    SINT("SINT"),
    USINT("USINT"),
    INT("INT"),
    UINT("UINT"),
    DINT("DINT"),
    UDINT("UDINT"),
    LINT("LINT"),
    ULINT("ULINT"),

    /**
     * Floaring pooint values
     */
    REAL("REAL"),
    LREAL("LREAL"),
    /**
     * Character values
     */
    CHAR("CHAR"),
    WCHAR("WCHAR"),
    STRING("STRING"),
    WSTRING("WSTRING"),

    /**
     * Temporal values
     */
    S5TIME("S5TIME"),
    TIME("TIME"),
    LTIME("LTIME"),
    DATE("DATE"),
    TIME_OF_DAY("TIME_OF_DAY"),
    DATE_AND_TIME("DATE_AND_TIME"),
    ;

    private final String type;

    private static final Map<String, PLCDataTypeEnum> MAP = Maps.newConcurrentMap();
    static {
        for (PLCDataTypeEnum e:values()){MAP.put(e.getType(), e);}
    }

    /**
     * 获取plc数据类型
     * @param type 类型字符串
     * @return 数据类型枚举
     */
    public static PLCDataTypeEnum getPLCDataTypeEnum(String type){
        PLCDataTypeEnum e = MAP.get(type);
        if(Objects.isNull(e)){
            throw new BusinessException("plc数据类型不存在");
        }
        return e;
    }
}
