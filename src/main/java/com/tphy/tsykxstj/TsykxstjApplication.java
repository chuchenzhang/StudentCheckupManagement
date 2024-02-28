package com.tphy.tsykxstj;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@MapperScan("com.tphy.tsykxstj.*.dao.mapper")
@SpringBootApplication
public class TsykxstjApplication {

    public static void main(String[] args) {
        SpringApplication.run(TsykxstjApplication.class, args);
        log.info("项目启动成功!");
    }

}
