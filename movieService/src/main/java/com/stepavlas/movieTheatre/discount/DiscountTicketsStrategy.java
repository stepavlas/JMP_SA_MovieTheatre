package com.stepavlas.movieTheatre.discount;

import com.stepavlas.movieTheatre.Show;
import com.stepavlas.movieTheatre.User;

/**
 * Created by Степан on 24.03.2016.
 */
public class DiscountTicketsStrategy implements DiscountStrategy{
    @Override
    public int countDiscount(User user, Show show){
        if (user.getNumTickets() % 10 == 0){
            return 50;
        }
        return 0;
    }
}
