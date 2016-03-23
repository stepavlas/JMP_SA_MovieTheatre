package com.stepavlas.movieTheatre.services.discount;

import com.stepavlas.movieTheatre.entities.Show;
import com.stepavlas.movieTheatre.entities.User;

/**
 * Created by Степан on 24.03.2016.
 */
public interface DiscountStrategy {
    int countDiscount(User user, Show show);
}
