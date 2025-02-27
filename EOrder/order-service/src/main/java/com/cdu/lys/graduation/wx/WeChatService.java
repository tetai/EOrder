package com.cdu.lys.graduation.wx;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
public class WeChatService {
    private final OkHttpClient httpClient = new OkHttpClient();
    //...省略获取 access_token 的方法

    public void sendSubscribeMessage(String accessToken, String openId, String templateId) throws IOException {
        String url = String.format("https://api.weixin.qq.com/cgi-bin/message/subscribe/send?access_token=%s", accessToken);
    // 构建消息内容
        SubscribeMessage message = new SubscribeMessage();
        message.setTouser(openId);
        message.setTemplate_id(templateId);
        message.setData(new SubscribeMessage.Data(
                new SubscribeMessage.DataItem("A0001"),
                new SubscribeMessage.DataItem("保利城店"),
                new SubscribeMessage.DataItem("1111145"),
                new SubscribeMessage.DataItem("A01")
        ));

        RequestBody body = RequestBody.create(JSONObject.toJSONString(message), MediaType.get("application/json; charset=utf-8"));
        log.info("参数={}", JSONObject.toJSONString(message));
        Request request = new Request.Builder().url(url).post(body).build();
        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                log.error("推送订阅结果：{}", response.body().string());
                log.error("推送订阅结果：{}", response.toString());
                throw new IOException("Unexpected code " + response);
            }
            log.info("推送订阅结果：{}", response.body().string());
        }
    }

    static class SubscribeMessage {
        private String touser;
        private String template_id;
        private Data data;

        public String getTouser() {
            return touser;
        }

        public void setTouser(String touser) {
            this.touser = touser;
        }

        public String getTemplate_id() {
            return template_id;
        }

        public void setTemplate_id(String template_id) {
            this.template_id = template_id;
        }

        public Data getData() {
            return data;
        }

        public void setData(Data data) {
            this.data = data;
        }



        static class Data {
            private DataItem character_string1;
            private DataItem thing2;
            private DataItem character_string3;
            private DataItem thing11;

            Data(DataItem character_string1, DataItem thing2, DataItem character_string3, DataItem thing11) {
                this.character_string1 = character_string1;
                this.thing2 = thing2;
                this.character_string3 = character_string3;
                this.thing11 = thing11;
            }


            public DataItem getCharacter_string1() {
                return character_string1;
            }

            public void setCharacter_string1(DataItem character_string1) {
                this.character_string1 = character_string1;
            }

            public DataItem getThing2() {
                return thing2;
            }

            public void setThing2(DataItem thing2) {
                this.thing2 = thing2;
            }

            public DataItem getCharacter_string3() {
                return character_string3;
            }

            public void setCharacter_string3(DataItem character_string3) {
                this.character_string3 = character_string3;
            }

            public DataItem getThing11() {
                return thing11;
            }

            public void setThing11(DataItem thing11) {
                this.thing11 = thing11;
            }
        }

        static class DataItem {
            private String value;

            DataItem(String value) {
                this.value = value;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }
        }
    }
}