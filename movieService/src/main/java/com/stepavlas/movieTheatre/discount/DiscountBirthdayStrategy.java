package com.stepavlas.movieTheatre.discount;

import com.stepavlas.movieTheatre.Show;
import com.stepavlas.movieTheatre.User;

import java.util.Calendar;

/**
 * Created by Степан on 23.03.2016.
 */
public class DiscountBirthdayStrategy implements DiscountStrategy {
    public int DISCOUNTVALUE = 5;

    public DiscountBirthdayStrategy(int DISCOUNTVALUE) {
        this.DISCOUNTVALUE = DISCOUNTVALUE;
    }

    @Override
    public int getDiscountValue() {
        return DISCOUNTVALUE;
    }

    public int countDiscount(User user, Show show){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(show.getDateTime());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        if (user.getBirthDate().equals(calendar.getTime())){
            return DISCOUNTVALUE;
        }
        return 0;
    }
}
