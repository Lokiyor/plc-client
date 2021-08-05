package com.lokiy.iot.protocol.plc.client.controller;

import com.lokiy.iot.protocol.plc.client.common.controller.BaseController;
import com.lokiy.iot.protocol.plc.client.common.model.resp.R;
import com.lokiy.iot.protocol.plc.client.domain.dto.PLCWriteSingleDTO;
import com.lokiy.iot.protocol.plc.client.service.PLCWriteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Lokiy
 * @Date 2021/8/5 10:33
 * @Description plc写控制类
 */
@RestController
@RequestMapping("/plc-write")
@Api(value = "plc写操作控制类", tags = "plc写操作控制类")
public class PLCWriteController extends BaseController {

    @Autowired
    private PLCWriteService plcWriteService;


    @ApiOperation("plc单独写操作接口")
    @RequestMapping("/single")
    public R singleWrite(@RequestBody @Validated PLCWriteSingleDTO dto){
        plcWriteService.singleWrite(dto);
        return R.success();
    }
}
