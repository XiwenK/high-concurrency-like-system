package com.sean.highconcurrencylikesystem.controller;

import com.sean.highconcurrencylikesystem.common.BaseResponse;
import com.sean.highconcurrencylikesystem.common.ResultUtils;
import com.sean.highconcurrencylikesystem.model.dto.DoThumbRequest;
import com.sean.highconcurrencylikesystem.service.ThumbService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("thumb")
public class ThumbController {

    @Resource
    private ThumbService thumbService;

    @PostMapping("/doThumb")
    public BaseResponse<Boolean> doThumb(@RequestBody DoThumbRequest doThumbRequest,
                                         HttpServletRequest request) {
        Boolean success = thumbService.doThumb(doThumbRequest, request);
        return ResultUtils.success(success);
    }

    @PostMapping("/undoThumb")
    public BaseResponse<Boolean> undoThumb(@RequestBody DoThumbRequest doThumbRequest,
                                           HttpServletRequest request) {
        Boolean success = thumbService.undoThumb(doThumbRequest, request);
        return ResultUtils.success(success);
    }

}
