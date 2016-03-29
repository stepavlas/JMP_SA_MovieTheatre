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

    public int getDiscount(User user, Show show){
        int discount = 0;
        for (DiscountStrategy strategy: discountStrategies){
            int strategyDisc = strategy.countDiscount(user, show);
            if (discount < strategyDisc){
                discount = strategyDisc;
            }
        }
        return discount;
    }
}
