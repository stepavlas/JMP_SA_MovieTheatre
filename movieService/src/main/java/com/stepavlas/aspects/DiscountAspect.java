package com.stepavlas.aspects;

import com.stepavlas.movieTheatre.discount.DiscountStrategy;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Степан on 30.03.2016.
 */

@Aspect
public class DiscountAspect {
    private Map<DiscountStrategy, Integer> discountsCount = new HashMap<>();

    @Pointcut("execution(public * com.stepavlas.movieTheatre.discount.DiscountService.getDiscount(com.stepavlas.movieTheatre.User, ..))")
    private void controlDiscount(){}

    @AfterReturning(pointcut = "controlDiscount()", returning = "retDiscStrat")
    public void addDiscountToMap(Object retDiscStrat) {
        DiscountStrategy discountStrategy = (DiscountStrategy) retDiscStrat;
        if (discountStrategy != null) {
            putToUserMap(discountStrategy);
        }

        System.out.println(discountsCount);
    }

    private void putToUserMap(DiscountStrategy discountStrategy) {
        Integer count = discountsCount.get(discountStrategy);
        if (count == null){
            count = 0;
        }
        discountsCount.put(discountStrategy, ++count);
    }
}
