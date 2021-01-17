package org.springboot.apollo.demo.controller;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.fastjson.JSONArray;
import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfig;
import net.sf.jsqlparser.JSQLParserException;
import org.springboot.apollo.demo.component.MysqlComponent;
import org.springboot.apollo.demo.configuration.apollo.MyApolloConf;
import org.springboot.apollo.demo.configuration.mysql.DynamicDataSource;
import org.springboot.apollo.demo.entity.ContrarySqlRecord;
import org.springboot.apollo.demo.entity.Goods;
import org.springboot.apollo.demo.service.ApolloService;
import org.springboot.apollo.demo.service.ContrarySqlRecordService;
import org.springboot.apollo.demo.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("test")
public class ConfigClientController {
    @Value("${time out}")
    private String time;

    @ApolloConfig("application")
    private Config aConfig;

    @Autowired
    private MysqlComponent mysqlComponent;

    @Autowired
    private MyApolloConf myApolloConf;

    @Autowired
    private ApolloService apolloService;

    @Autowired
    GoodsService goodsService;

    @Autowired
    ContrarySqlRecordService contrarySqlRecordService;


    @GetMapping("/getTimeOut")
    public String getTimeOut() {
        return time;
    }

    @GetMapping("/getJdbcUrl")
    public String getJdbcUrl() {
        return mysqlComponent.getUrl();
    }

    @GetMapping("/getMysqlConf")
    public String getMysqlConf() {
        return mysqlComponent.toString();
    }


    @GetMapping("/getMyApolloConf")
    public String getMyApolloConf() {
        return myApolloConf.toString();
    }

    @GetMapping("/getConfig")
    public String getConfig() {
        Set<String> propertyNames = aConfig.getPropertyNames();
        System.out.println(propertyNames);
        propertyNames.forEach(key -> {
            System.out.println(key + "=" + aConfig.getProperty(key, "100"));
        });
        return propertyNames.toString();
    }

    @GetMapping("/getJsessionid")
    public String getJsessionid() {
        return apolloService.queryJsessionid();
    }

    @GetMapping("query/public/namespace")
    public JSONArray queryPublicNameSpace() {
        return apolloService.queryPublicNameSpace();
    }

    @GetMapping("query/goodI")
    public Goods queryGoodI(@RequestParam("id") String id) {
        return goodsService.queryById(id);
    }

    @GetMapping("/selectAll")
    public List<Map> selectAll(@RequestParam("sql") String sql) {
        return goodsService.selectAll(sql);
    }

    @GetMapping("exe/sql")
    public Object exeSql(@RequestParam("applyId") String applyId, @RequestParam("dataBase") String dataBase, @RequestParam("sql") String sql) throws JSQLParserException {
        System.out.println("执行sql");
        int res = 0;
        try {
            DynamicDataSource.setDynamicDataSource(mysqlComponent, dataBase);
            ContrarySqlRecord contrarySqlRecord = goodsService.exeUpdateSql(applyId, dataBase, sql);
            DynamicDataSource.clear();
            contrarySqlRecordService.insert(contrarySqlRecord);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    @GetMapping("/rollbackAllSql")
    public Object rollbackAllSql(@RequestParam("applyId") String rapplyId) {
        ContrarySqlRecord contrarySqlRecord = contrarySqlRecordService.queryLatestRecord(rapplyId);
        String dataBase = contrarySqlRecord.getDataBase();
        String contrarySqlStr = contrarySqlRecord.getContrarySql();
        DruidDataSource druidDataSource = new DruidDataSource();
        String dynamicUrl = mysqlComponent.getUrl().replace("share", dataBase);
        druidDataSource.setUrl(dynamicUrl);
        druidDataSource.setUsername(mysqlComponent.getUsername());
        druidDataSource.setPassword(mysqlComponent.getPassword());
        druidDataSource.setDriverClassName(mysqlComponent.getDriverClass());
        DynamicDataSource.dataSourcesMap.put(dataBase, druidDataSource);
        DynamicDataSource.setDataSource(dataBase);
        String[] contrarySqls = contrarySqlStr.split("#!%");
        for (int i = 0; i < contrarySqls.length; i++) {
            contrarySqlRecordService.rollBackSql(contrarySqls[i]);
        }
        DynamicDataSource.clear();
        return null;
    }
}
