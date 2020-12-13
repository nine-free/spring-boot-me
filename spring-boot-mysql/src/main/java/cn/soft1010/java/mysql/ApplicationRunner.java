package cn.soft1010.java.mysql;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by zhangjifu on 2020/12/13.
 */
@SpringBootApplication
@ComponentScan(basePackages = "cn.soft1010.java.mysql")
public class ApplicationRunner {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationRunner.class, args);
    }
}
