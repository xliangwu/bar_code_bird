package com.caveup.barcode;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author xw80329
 */
@Component
@Slf4j
public class StartupRunner implements CommandLineRunner {

    @Override
    public void run(String... args) {
        log.info(">>>>>>>>>>>>>>>服务启动完成<<<<<<<<<<<<<");
    }
}
