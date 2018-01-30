package com.beacon.api.v1.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;

/**
 * @author Ian.Su
 * @version $Id TokenController.java, v 0.1 2018/1/30 14:18 Ian.Su Exp $
 **/
@RestController
@RequestMapping("api/v1/assist")
@Api(value = "api/v1/assist", tags = "辅助功能")
public class AssistController {

    @ApiOperation(value = "下载token", notes = "将参数中的token值写入文件并下载")
    @ApiImplicitParams({

            @ApiImplicitParam(name = "token", required = true, paramType = "form", dataType = "string"),
    })
    @PostMapping("token/download")
    public void login(@RequestParam String token, HttpServletResponse response) throws Exception {

        response.setContentType("text/plain");
        response.setHeader("Content-disposition","attachment; filename=token.txt");
        BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
        bos.write(token.getBytes());
        bos.close();

    }
}
