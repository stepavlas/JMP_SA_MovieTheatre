package com.stepavlas.movieTheatre;

import com.stepavlas.movieTheatre.entities.User;
import com.stepavlas.movieTheatre.services.UserService;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Степан on 18.03.2016.
 */
public class ViewApp {
    private static ConfigurableApplicationContext ctx = new ClassPathXmlApplicationContext("theatreConfig.xml");

    public static void main(String[] args) {
        UserService userService = ctx.getBean("userService", UserService.class);
        userService.register("Sergei", "Avdochinov", "SergAvdochinov@example.com");
        User user = userService.getUserByEmail("SergAvdochinov@example.com");
        System.out.println(user);
    }
}
