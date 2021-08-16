package com.lokiy.iot.protocol.plc.client.controller;

import com.lokiy.iot.protocol.plc.client.common.controller.BaseController;
import com.lokiy.iot.protocol.plc.client.common.model.resp.R;
import com.lokiy.iot.protocol.plc.client.service.PLCWriteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Lokiy
 * @Date 2021/8/5 13:31
 * @Description 测试接口类
 */
@RestController
@RequestMapping("/test")
@Api(value = "测试接口类", tags = "测试接口类")
public class TestController extends BaseController {

    @Autowired
    private PLCWriteService plcWriteService;

    @ApiOperation("写本地固定地址")
    @GetMapping("/write")
    public R write(){
        plcWriteService.fixWrite();
        return R.success();
    }
}
