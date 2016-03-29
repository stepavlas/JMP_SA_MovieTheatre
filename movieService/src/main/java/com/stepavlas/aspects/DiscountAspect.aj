package com.stepavlas.aspects;

import com.stepavlas.movieTheatre.User;
import com.stepavlas.movieTheatre.discount.DiscountStrategy;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Степан on 30.03.2016.
 */

@Aspect
public aspect DiscountAspect {
    private List<DiscountStrategy> discountStrategies;
    private Map<DiscountStrategy, List<User>> map = new HashMap();

    public void setDiscountStrategies(List<DiscountStrategy> discountStrategies) {
        this.discountStrategies = discountStrategies;
    }

    @Pointcut("execution(public int com.stepavlas.movieTheatre.discount.DiscountService.getDiscount(..))")
    private void controlDiscount(){
        // not finished
    }


}
