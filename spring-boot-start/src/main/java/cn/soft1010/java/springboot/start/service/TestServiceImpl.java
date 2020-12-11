package cn.soft1010.java.springboot.start.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Created by zhangjifu on 2020/12/11.
 */
@Service
public class TestServiceImpl implements TestService {

    Logger logger = LoggerFactory.getLogger(TestServiceImpl.class);

    @Override
    public void test() {
        logger.info("-------test--------");
    }
}
