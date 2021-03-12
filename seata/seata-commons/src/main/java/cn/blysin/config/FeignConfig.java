package cn.blysin.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import io.seata.core.context.RootContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author daishaokun
 * @date 2021/3/11
 */
@Configuration
public class FeignConfig implements RequestInterceptor {
    @Bean
    @ConditionalOnMissingBean(RestTemplate.class)
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Override
    public void apply(RequestTemplate requestTemplate) {
        String xid = RootContext.getXID();
        if (StringUtils.isNotBlank(xid)) {
            System.out.println("feign xid："+xid);
        }

        requestTemplate.header(RootContext.KEY_XID, xid);
    }
}
