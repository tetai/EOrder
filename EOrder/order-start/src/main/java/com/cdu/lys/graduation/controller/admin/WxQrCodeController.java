package com.cdu.lys.graduation.controller.admin;

import com.alibaba.fastjson.JSONObject;
import com.cdu.lys.graduation.commons.result.ActionResult;
import com.cdu.lys.graduation.commons.utils.QRCodeUtil;
import com.cdu.lys.graduation.commons.utils.WeChatUtil;
import com.cdu.lys.graduation.wx.WeChatService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.Base64;

/**
 * @desc
 * @date 2025/2/27
 * @auth zkz
 */
@RestController
@RequestMapping(value = "/eorder/admin/qrcode")
@Api(tags = "微信二维码")
@Slf4j
public class WxQrCodeController {

    @Value("${order.wx.AppID}")
    private String appId;

    @Value(("${order.wx.AppSecret}"))
    public String appSecret;
    @Resource
    private WeChatService weChatService;

    @RequestMapping("/generateQRCode")
    public ActionResult<String> generateQRCode(@RequestParam String path, HttpServletResponse response) throws Exception{
        String accessToken = WeChatUtil.getAccessToken(appId, appSecret);
        String openId = "owS9c5LmbtTCPOZoWBMIwCDsAIwI";
        String tempId = "bn5wi-EzNZPJSL5_NjhwVnM9dcXuuIggWyCLyBu_VsY";

        weChatService.sendSubscribeMessage(accessToken, openId, tempId);

        response.setContentType("image/png");
        byte[] qrCodeBytes = QRCodeUtil.createQRCode(accessToken, path);
        if (qrCodeBytes != null) {
            // 将字节数组转换为Base64编码的字符串
            String base64Image = Base64.getEncoder().encodeToString(qrCodeBytes);
            return ActionResult.getSuccessResult("data:image/png;base64," + base64Image); // 返回Base64编码的图片
        }
        return null;
    }

}
