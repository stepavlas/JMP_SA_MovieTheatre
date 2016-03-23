package com.stepavlas.movieTheatre.services.discount;

import com.stepavlas.movieTheatre.entities.Show;
import com.stepavlas.movieTheatre.entities.User;
import com.stepavlas.movieTheatre.services.discount.DiscountStrategy;

import java.util.Calendar;

/**
 * Created by Степан on 23.03.2016.
 */
public class DiscountBirthdayStrategy implements DiscountStrategy {
    public int countDiscount(User user, Show show){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(show.getDateTime());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        if (user.getBirthDate().equals(calendar.getTime())){
            return 5;
        }
        return 0;
    }
}