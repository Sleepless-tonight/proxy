package com.nostyling.proxy.runner;


import com.nostyling.proxy.service.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


/**
 * @author shiliang
 * @Classname Runner
 * @Date 2020/12/22 16:12
 * @Description Runner
 */
@Component
public class Runner implements CommandLineRunner {
    private final static Logger logger = LoggerFactory.getLogger(Runner.class);

    private int port = 9999;


    @Override
    public void run(String... args) throws Exception {
        new Server(port).start();
    }

}
