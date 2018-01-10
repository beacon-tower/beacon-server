package com.beacon.api.v1.controller;

import com.beacon.commons.response.ResData;
import com.beacon.commons.utils.AssertUtils;
import com.beacon.entity.Image;
import com.beacon.service.OssService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;

import static com.beacon.commons.code.PublicResCode.PARAMS_EXCEPTION;

/**
 * 图片上传
 *
 * @author luckyhua
 * @version 1.0
 * @since 2018/1/10
 */
@RestController
@RequestMapping("api/v1/image")
@Api(value = "api/v1/image", tags = "图片上传")
public class ImageController {

    @Inject
    private OssService ossService;

    @ApiOperation(value = "图片上传", response = Image.class, notes = "image")
    @PostMapping(value = "upload")
    public ResData<Image> upload(@ApiParam(name = "file", value = "上传的图片") @RequestParam("file") MultipartFile file) {
        AssertUtils.isTrue(PARAMS_EXCEPTION, !file.isEmpty());
        return ResData.success(ossService.upload(file));
    }

}
