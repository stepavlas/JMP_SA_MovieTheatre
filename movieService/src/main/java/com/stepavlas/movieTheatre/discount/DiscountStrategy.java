package com.stepavlas.movieTheatre.discount;

import com.stepavlas.movieTheatre.Show;
import com.stepavlas.movieTheatre.User;

/**
 * Created by Степан on 24.03.2016.
 */
public interface DiscountStrategy {
    int countDiscount(User user, Show show);
}
