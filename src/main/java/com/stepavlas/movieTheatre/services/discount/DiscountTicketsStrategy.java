package com.stepavlas.movieTheatre.services.discount;

import com.stepavlas.movieTheatre.entities.Show;
import com.stepavlas.movieTheatre.entities.User;

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
