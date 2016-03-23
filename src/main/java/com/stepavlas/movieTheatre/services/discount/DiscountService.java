package com.stepavlas.movieTheatre.services.discount;

import com.stepavlas.movieTheatre.entities.Show;
import com.stepavlas.movieTheatre.entities.User;

import java.util.List;

/**
 * Created by Степан on 24.03.2016.
 */
public class DiscountService {
    private List<DiscountStrategy> discountStrategies;

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
