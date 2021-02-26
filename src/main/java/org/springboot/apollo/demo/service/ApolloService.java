package org.springboot.apollo.demo.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public interface ApolloService {
    JSONArray queryPublicNameSpace();

    void putConfig(String appId, String config);

    JSONArray queryOwnerApps(String owner, int page, int size);

    JSONArray queryEnvClusterList(String appId);

    //    JSONArray queryAppIdEnv(String appId);
    JSONObject queryProjectionInfo(String appId);

    JSONArray queryAppIdEnvClusterNamespaceConfig(String appId,String env,String cluster);

    JSONArray queryAppIdCommitRecord(String appId,String env,String cluster, String namespace, int page, int size);
    /**
     * 查询识别登录身份使用的cookie（jsessionid）
     */
    String queryJsessionid();
}
