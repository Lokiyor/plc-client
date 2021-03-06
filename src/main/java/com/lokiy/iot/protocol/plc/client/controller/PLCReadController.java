package com.lokiy.iot.protocol.plc.client.controller;

import com.lokiy.iot.protocol.plc.client.common.controller.BaseController;
import com.lokiy.iot.protocol.plc.client.service.PLCReadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Lokiy
 * @Date 2021/8/5 10:25
 * @Description plc读控制类
 */
@RestController
@RequestMapping("/plc-read")
public class PLCReadController extends BaseController {

    @Autowired
    private PLCReadService plcReadService;
}
