package cn.linshenkx.randomimageapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@EnableCaching
@SpringBootApplication
public class RandomImageApiApplication {

    /**
     * dev: https://tunnel.linshenkx.cn/image/random
     * 1. nginx:tunnel.linshenkx.cn->117.50.162.147:22909
     * 2. fasttunnel:117.50.162.147:22909 -> win:22909
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(RandomImageApiApplication.class, args);
    }

}
