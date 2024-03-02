package com.tphy.tsykxstj;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@MapperScan("com.tphy.tsykxstj.*.dao.mapper")
@EnableScheduling
@SpringBootApplication
public class TsykxstjApplication {

    public static void main(String[] args) {
        SpringApplication.run(TsykxstjApplication.class, args);
        log.info("项目启动成功!");
    }

    //用于更改tomcat的非法字符限制。去掉一些括号双引号的限制
    @Bean
    public ConfigurableServletWebServerFactory webServerFactory() {
        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
        factory.addConnectorCustomizers((TomcatConnectorCustomizer) connector -> connector.setProperty("relaxedQueryChars", "|{}[]\\"));
        return factory;
    }

}
