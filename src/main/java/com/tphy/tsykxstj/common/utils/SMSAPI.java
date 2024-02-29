package com.tphy.tsykxstj.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
// import tphy.uzfb.common.bean.CommonReturn;
// import tphy.uzfb.verify.mapper.VerifyMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.regex.Pattern;

public class SMSAPI {

    // @Autowired
    // VerifyMapper verifyMapper;

    public static String accessKeyId = "KR0Rf0uuUrJCGO57";
    public static String accessKeySecret = "UIsJm9CIdNdpTL1kpnSpKywQiU9Gis";
    public static IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
    public static DefaultAcsClient client = new DefaultAcsClient(profile);
    public static Pattern pattern = Pattern.compile("^[1]\\d{10}$");
    public static Pattern pattern1 = Pattern.compile("^[0-9]{11}$");

    /// <summary>
    /// 验证码短信模板编码
    /// </summary>

    /// <summary>
    /// 发送手机验证码
    /// </summary>
    /// <param name="phoneNum"></param>
    /// <returns></returns>
    public static AppResponse<String> SendVerifyCode(String phoneNum)
    {
        // CommonReturn cr = new CommonReturn();
        AppResponse<String> res = new AppResponse<>();

        // if (verifyMapper.LimitForMinute(phoneNum)>=3 || verifyMapper.LimitForDay(phoneNum)>=10)
        // {
        //     cr.setIsSuccess(false);
        //     cr.setData("超过发送次数限制");
        //     return cr;
        // }
        String signName = "北京天鹏恒宇";

        String verifyCode = CreateVerifyCode();
        String templateCode = "SMS_184826696";
        String verifyCodeJsonFormat = "{{\"code\":\""+verifyCode+"\"}}";
        String json = String.format(verifyCodeJsonFormat, verifyCode);
        res = GetMessage(phoneNum, templateCode, json, signName);

        // if (cr.getIsSuccess())
        // {
        //     if (verifyMapper.SaveVerifyCode(phoneNum, verifyCode))
        //     {
        //         cr.setIsSuccess(true);
        //         return cr;
        //     }
        //     else
        //     {
        //         cr.setIsSuccess(false);
        //         return cr;
        //     }
        // }

        return res;
    }

    /// <summary>
    /// 发送预约短信
    /// </summary>
    /// <param name="phoneNum"></param>
    /// <returns></returns>
    public static AppResponse<String> SendMessage(String templateCode,String phoneNum, String verifyCodeJsonFormat)
    {
        return GetMessage(phoneNum, templateCode, verifyCodeJsonFormat, "天鹏恒宇");
    }

    public static AppResponse<String> GetMessage(String phoneNum, String templateCode, String JsonFormat, String signName)
    {
        AppResponse<String> res = new AppResponse<>();

        if (phoneNum.trim().length() != 11)
        {
            // cr.setIsSuccess(false);
            // cr.setData("手机号位数不合理");
            return res.error("手机号位数不合理",null);
        }
        if (!pattern1.matcher(phoneNum).matches())
        {
            // cr.setIsSuccess(false);
            // cr.setData("手机号非纯数字");
            return res.error("手机号非纯数字",null);
        }
        if (!pattern.matcher(phoneNum).matches())
        {
            // cr.setIsSuccess(false);
            // cr.setData("手机号格式不合理");
            return res.error("手机号格式不合理",null);
        }

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", phoneNum);
        request.putQueryParameter("SignName", signName);
        request.putQueryParameter("TemplateCode", templateCode);
        request.putQueryParameter("TemplateParam", JsonFormat);


        //request.AddQueryParameters("SmsUpExtendCode", "");
        //request.AddQueryParameters("OutId", "");
        try
        {
            CommonResponse response = client.getCommonResponse(request);
            String message = new String(response.getHttpResponse().getHttpContent(), StandardCharsets.UTF_8);

            MsgModel msgModel = JSONObject.parseObject(JSONObject.toJSONString(JSON.parse(message)),MsgModel.class);

            if (msgModel != null && "OK".equals(msgModel.getCode()))
            {
                // cr.setIsSuccess(true);
                return res.success("短信发送成功",null);
            }
            else
            {
                // cr.setIsSuccess(false);
                // cr.setData(msgModel != null ? msgModel.Message : "未知原因");
                return res.error(msgModel != null ? msgModel.Message : "未知原因",null);
            }
        }
        catch (ServerException e)
        {
//            Log.LogWrite("GetMessage", e.Message);
        }
        catch (ClientException e)
        {
//            Log.LogWrite("GetMessage", e.Message);
        }
        catch (Exception e)
        {
//            Log.LogWrite("GetMessage", e.Message);
        }


        return res;
    }

    private static String CreateVerifyCode() {
        Random r = new Random();
        String str = "";
        for (int i = 0; i < 6; i++) {
            str += r.nextInt(10);
        }
        return str;
    }
}
