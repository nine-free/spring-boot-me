package cn.soft1010.java.springboot.restruction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by zhangjifu on 2020/12/2.
 */
@SpringBootApplication
@ComponentScan(basePackages = "cn.soft1010.java.springboot.restruction")
public class SpringBootApplicationStarter {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootApplicationStarter.class, args);
    }
}
