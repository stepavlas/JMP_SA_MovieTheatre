package com.stepavlas.aspects;

import com.stepavlas.movieTheatre.Ticket;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.util.Random;
import java.util.TooManyListenersException;

/**
 * Created by Степан on 30.03.2016.
 */

@Aspect
public class LuckyWinnerAspect {
    @Around("execution(public * *.getTicketPrice(com.stepavlas.movieTheatre.Event, ..))")
    public Integer luckyDiscount(ProceedingJoinPoint joinPoint) throws Throwable {
        Random random = new Random();
        int num = random.nextInt(100);
        Integer price = (Integer)joinPoint.proceed();
        if (num <= 10){
            price = 0;
        }
        return price;
    }

}
