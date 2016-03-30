package com.stepavlas.movieTheatre.discount;


import com.stepavlas.movieTheatre.Show;
import com.stepavlas.movieTheatre.User;

import java.util.List;

/**
 * Created by Степан on 24.03.2016.
 */
public class DiscountService {
    private List<DiscountStrategy> discountStrategies;

    public DiscountService(List<DiscountStrategy> discountStrategies) {
        this.discountStrategies = discountStrategies;
    }

    public DiscountStrategy getDiscount(User user, Show show){
        DiscountStrategy result = null;
        for (DiscountStrategy strategy: discountStrategies){
            if (strategy.hasDiscount(user, show)){
                if (result == null || strategy.getDiscountValue() > result.getDiscountValue()){
                    result = strategy;
                }
            }
        }
        return result;
    }
}
