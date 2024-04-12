package com.mmm.apiclientsdk.client;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.mmm.apiclientsdk.model.User;
import com.mmm.apiclientsdk.utils.SignUtils;

import java.util.HashMap;
import java.util.Map;

public class MmmApiClient {

    private String accessKey;

    private String secretKey;

    public MmmApiClient(String accessKey, String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

    public String getNameGet(String name){
        HashMap<String ,Object> paramMap = new HashMap<>();
        paramMap.put("name",name);
        String res = HttpUtil.get("http://localhost:8123/api/name/", paramMap);
        System.out.println(res);
        return res;
    }

    public String getNamePost(String name){
        HashMap<String ,Object> paramMap = new HashMap<>();
        paramMap.put("name",name);
        String res = HttpUtil.post("http://localhost:8123/api/name/", paramMap);
        System.out.println(res);
        return res;
    }

    private Map<String,String> getHeaderMap(String body) {
        Map<String ,String > hashMap = new HashMap<>();
        hashMap.put("accessKey",accessKey);
        hashMap.put("nonce", RandomUtil.randomNumbers(4));
        hashMap.put("body",body);
        hashMap.put("timestamp",String.valueOf(System.currentTimeMillis()/1000));
        hashMap.put("sign", SignUtils.genSign(body,secretKey));
        return hashMap;
    }

    public String getUserNameByPost(User user) {
        String json = JSONUtil.toJsonStr(user);
        HttpResponse httpResponse = HttpRequest.post("http://localhost:8123/api/name/user")
            .addHeaders(getHeaderMap(json))
            .body(json)
            .execute();
        System.out.println(httpResponse.getStatus());
        String body = httpResponse.body();
        System.out.println(body);
        return body;
    }
}
