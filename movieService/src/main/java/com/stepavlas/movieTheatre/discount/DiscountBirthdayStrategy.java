package com.stepavlas.movieTheatre.discount;

import com.stepavlas.movieTheatre.Show;
import com.stepavlas.movieTheatre.User;
import org.joda.time.DateTimeComparator;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Степан on 23.03.2016.
 */
public class DiscountBirthdayStrategy implements DiscountStrategy {
    public int DISCOUNTVALUE = 5;
    private Calendar calendar;

    public DiscountBirthdayStrategy(int DISCOUNTVALUE, Calendar calendar) {
        this.DISCOUNTVALUE = DISCOUNTVALUE;
        this.calendar = calendar;
    }

    @Override
    public int getDiscountValue() {
        return DISCOUNTVALUE;
    }

    public boolean hasDiscount(User user, Show show){
        Date birthday = user.getBirthDate();

        calendar.setTime(show.getDateTime());

        boolean result = DateTimeComparator.getDateOnlyInstance().compare(birthday, calendar) == 0;
        return result;
    }

    @Override
    public String toString() {
        return "DiscountBirthdayStrategy";
    }
}
