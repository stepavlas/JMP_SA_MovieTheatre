package com.stepavlas.movieTheatre.impl;

import com.stepavlas.movieTheatre.User;
import com.stepavlas.movieTheatre.exceptions.IncorrectUserException;
import org.junit.Test;

import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by Viacheslav_Ganiaev on 3/28/2016.
 */
public class MapUserDaoTest {
    @Test
    public void addTest() throws IncorrectUserException{
        Map<Long, User> userMap = mock(Map.class);

        MapUserDaoImpl mapUserDao = new MapUserDaoImpl();
        mapUserDao.setUserMap(userMap);

        User user = new User();
        user.setId(1);
        user.setEmail("test@test.ru");

        mapUserDao.add(user);

        verify(userMap).put(user.getId(), user);
    }
}
