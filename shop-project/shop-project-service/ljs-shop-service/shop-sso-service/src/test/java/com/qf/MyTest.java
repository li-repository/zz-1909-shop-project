package com.qf;

import com.qf.service.SsoService;
import com.qf.utils.ResultBean;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author li
 * @date 2020/3/12 0012 21:31
 */

@SpringBootTest
public class MyTest {

    @Autowired
    private SsoService service;


    @Test
    void test(){


        ResultBean resultBean = service.checkIsLogin("123");
        System.out.println("--------"+resultBean);


    }


}
