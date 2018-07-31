package com.ht.commonactivity;

import com.ht.commonactivity.controller.ActivitiOutServiceController;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import java.awt.image.DataBuffer;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CommonActivityApplication.class)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DependencyInjectionTestExecutionListener.class})
@Transactional
@Log4j2
public class CommonActivityApplicationTests {

    @Autowired
    ActivitiOutServiceController activitiOutServiceController;

    @Test
    public void contextLoads() {
       log.info( activitiOutServiceController.procIsEnd("c56570ac5fda4372b35ee6e054242682"));
    }

    @Test
    public void asdf(){

    }

}
