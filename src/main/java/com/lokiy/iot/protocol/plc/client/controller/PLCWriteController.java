package com.lokiy.iot.protocol.plc.client.controller;

import com.lokiy.iot.protocol.plc.client.common.controller.BaseController;
import com.lokiy.iot.protocol.plc.client.service.PLCWriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Lokiy
 * @Date 2021/8/5 10:33
 * @Description plc写控制类
 */
@RestController
@RequestMapping("/plc-write")
public class PLCWriteController extends BaseController {

    @Autowired
    private PLCWriteService plcWriteService;
}
