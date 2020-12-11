package cn.soft1010.java.springboot.start;

import cn.soft1010.java.springboot.start.service.TestService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by zhangjifu on 2020/12/2.
 */
@SpringBootApplication
@ComponentScan(basePackages = "cn.soft1010.java.springboot.start")
public class SpringBootApplicationStarter {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootApplicationStarter.class, args);
        TestService testService = (TestService) SpringUtils.getBean(TestService.class);
        testService.test();
    }
}
