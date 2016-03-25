package com.stepavlas.movieTheatre;


import com.stepavlas.movieTheatre.impl.EventDaoImpl;
import com.stepavlas.movieTheatre.impl.ShowDaoImpl;
import com.stepavlas.movieTheatre.impl.UserDaoImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Степан on 25.03.2016.
 */
public class daoTestApp {
    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("daoConfig.xml");
        EventDao eventDao = ctx.getBean("eventDao", EventDaoImpl.class);
        int i = 4;
        int a = i + 2;
    }
}
