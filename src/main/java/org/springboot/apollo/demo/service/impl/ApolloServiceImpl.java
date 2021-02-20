package org.springboot.apollo.demo.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springboot.apollo.demo.configuration.apollo.MyApolloConf;
import org.springboot.apollo.demo.service.ApolloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@Service("apolloService")
public class ApolloServiceImpl implements ApolloService {

    @Autowired
    private MyApolloConf myApolloConf;

    @Autowired
    private RestTemplate restTemplate;


    @Override
    public JSONArray queryPublicNameSpace() {
        HttpHeaders headers = new HttpHeaders();
        String apolloSessionId = this.queryJsessionid();
        headers.set("Cookie","JSESSIONID=" + apolloSessionId);
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(myApolloConf.getUrl()).append("/appnamespaces/public");
        JSONArray result=restTemplate.exchange(stringBuilder.toString(), HttpMethod.GET, entity, JSONArray.class).getBody();
        return result;
    }

    @Override
    public void putConfig(String appId, String config) {

    }

    @Override
    public JSONArray queryOwnerApps(String owner, int page, int size) {
        return null;
    }

    @Override
    public JSONArray queryEnvClusterList(String appId) {
        return null;
    }

    @Override
    public JSONObject queryProjectionInfo(String appId) {
        return null;
    }

    @Override
    public JSONArray queryAppIdEnvClusterNamespaceConfig(String appId, String env, String cluster) {
        return null;
    }

    @Override
    public JSONArray queryAppIdCommitRecord(String appId, String env, String cluster, String namespace, int page, int size) {
        return null;
    }

    @Override
    public String queryJsessionid() {
        String jsessionid = "";
        Map<String, String> postArgumentMap = new HashMap<>();
        postArgumentMap.put("username", myApolloConf.getUsername());
        postArgumentMap.put("password", myApolloConf.getPassword());
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("username=").append(myApolloConf.getUsername())
                .append("&password=").append(myApolloConf.getPassword());
        // "Set-Cookie"
        URI uri = UriComponentsBuilder.fromUriString(myApolloConf.getUrl().concat("/signin")).build().toUri();
        RequestEntity<String> requestEntity = RequestEntity.post(uri)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(stringBuffer.toString());
        HttpHeaders resultHeaders =  restTemplate.exchange(requestEntity, Map.class).getHeaders();
        System.out.println(resultHeaders.toSingleValueMap());
        String setCookie = resultHeaders.toSingleValueMap().get("Set-Cookie");
        // jsessionid，例如：JSESSIONID=A46EE2C3E757822F12CF120A1663861C; Path=/; HttpOnly
        jsessionid = setCookie.split(";")[0].split("=")[1];

        return jsessionid;
    }
}
