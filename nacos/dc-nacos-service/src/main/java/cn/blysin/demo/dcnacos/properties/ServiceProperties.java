package cn.blysin.demo.dcnacos.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * 配置类
 *
 * @author daishaokun
 * @date 2020/11/24
 */
@ConfigurationProperties(prefix = "app.api")
@Component
@Data
@RefreshScope
public class ServiceProperties {
    private String accessKey;
    private String accessToken;
    private String name;
}
