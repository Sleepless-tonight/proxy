package com.nostyling.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 * @author shiliang
 * @Classname Test
 * @Date 2021/1/12 15:35
 * @Description ProxyApplication
 */
@SpringBootApplication
public class ProxyApplication {
    private final static Logger logger = LoggerFactory.getLogger(ProxyApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(ProxyApplication.class, args);
        logger.info("ProxyApplication 启动成功。");

    }

}
