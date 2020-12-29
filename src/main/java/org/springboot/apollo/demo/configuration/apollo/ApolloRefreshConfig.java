package org.springboot.apollo.demo.configuration.apollo;

import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.scope.refresh.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * @author： WangQian
 * @date： 2020/8/12 下午 4:50
 */
@Component
public class ApolloRefreshConfig {

    @Autowired
    MyApolloConf myApolloConf;

    @Autowired
    RefreshScope refreshScope;

    @ApolloConfigChangeListener("apollo")
    public void onChange(ConfigChangeEvent changeEvent) {
        boolean hopsonConfigChanged = false;
        for (String changedKey : changeEvent.changedKeys()) {
            if (changedKey.startsWith("apollo")) {
                hopsonConfigChanged = true;
                break;
            }
        }
        if (!hopsonConfigChanged) {
            return;
        }

        System.out.println("before refresh {}" + myApolloConf.toString());
        refreshScope.refresh("myApolloConf");
        System.out.println("after refresh {}"+myApolloConf.toString());
    }
}
