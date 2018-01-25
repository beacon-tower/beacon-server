package com.beacon.api.v1.controller;

import com.beacon.commons.response.ResData;
import com.beacon.entity.User;
import com.beacon.utils.ShiroUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * @author Ian.Su
 * @version $Id IndexController.java, v 0.1 2018/1/25 10:37 Ian.Su Exp $
 **/
@RestController
@RequestMapping("api/v1/index")
@Api(value = "api/v1/index", tags = "首页")
public class IndexController {

    @ApiOperation(value = "文章列表", notes = "首页的文章列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNumber", value = "页码,默认显示第1页", paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "limit", value = "显示多少条,默认显示10条", paramType = "query", dataType = "int")
    })
    @GetMapping("posts/list")
    public ResData<Boolean> getPostsList(@RequestParam(defaultValue = "1")  Integer pageNumber,
                                         @RequestParam(defaultValue = "10") Integer limit ) {

        User user = ShiroUtils.getUser();
        if(user == null){

        }

        return ResData.success(true);
    }


    @PostMapping("post/test")
    public ResData<Boolean> postTest(@RequestParam(defaultValue = "1")  Integer pageNumber,
                                     @RequestParam(defaultValue = "10") Integer limit ) {

        User user = ShiroUtils.getUser();
        if(user == null){

        }

        return ResData.success(true);
    }


}
