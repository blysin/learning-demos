package cn.blysin;

import cn.blysin.config.ServiceConstant;
import io.seata.spring.annotation.datasource.EnableAutoDataSourceProxy;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author daishaokun
 * @date 2021/3/10
 */
@SpringBootApplication
@EnableFeignClients
@EnableAutoDataSourceProxy(dataSourceProxyMode = ServiceConstant.type)
public class SeataServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(SeataServiceApplication.class, args);
    }
}
