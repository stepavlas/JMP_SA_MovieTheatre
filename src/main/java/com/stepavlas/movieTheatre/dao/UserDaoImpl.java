package com.stepavlas.movieTheatre.dao;

import com.google.common.annotations.VisibleForTesting;
import com.stepavlas.movieTheatre.entities.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Степан on 17.03.2016.
 */
public class UserDaoImpl implements UserDao {
    private static Map<Long, User> users = new HashMap<>();

    @Override
    public List<User> findUsers(User user) {
        // add exception
        List<User> result = null;
        if (user.getId() != 0){
            User dbUser = getById(user);
            result = createListWithUser(dbUser);
        } else if (user.getEmail() != null){
            User dbUser = findByEmail(user);
            result = createListWithUser(dbUser);
        } else if (user.getFirstName() != null && user.getLastName() != null){
            result = findByName(user);
        }
        // return empty list
        if (result == null || result.isEmpty()){
            return null;
        }
        return result;
    }

    private List<User> createListWithUser(User user) {
        List<User> users = null;
        if (user != null) {
            users = new ArrayList<>();
            users.add(user);
        }
        return users;
    }

    @Override
    public User getById(User user) {
        // add exception
        return users.get(user.getId());
    }

    private User findByEmail(User user) {
        List<User> result = new ArrayList<User>();
        for (User dbUser: users.values()){
            if (user.getEmail().equals(dbUser.getEmail())){
                return dbUser;
            }
        }
        return null;
    }

    private List<User> findByName(User user){
        List<User> result = new ArrayList<User>();
        for (User dbUser: users.values()){
            if (user.getLastName().equals(dbUser.getLastName()) &&
                    user.getFirstName().equals(dbUser.getFirstName())){
                result.add(dbUser);
            }
        }
        return result;
    }

    @Override
    public void add(User user) {
        if (user.getId() != 0 || user.getEmail() == null || user.getFirstName() == null ||
                user.getLastName() == null){
            throw new IllegalArgumentException("User doesn't have mandatory field for adding");
        }
        List<User> dbUsers = findUsers(user);
        if (dbUsers != null && !dbUsers.isEmpty()) {
            throw new IllegalArgumentException("User already exists");
        }
        user.setId(users.size() + 1);
        users.put(user.getId(), user);
    }

    @Override
    public User update(User user){
        if (user.getId() == 0 || user.getEmail() == null || user.getFirstName() == null ||
                user.getLastName() == null){
            throw new IllegalArgumentException("User doesn't have mandatory field or has incorrect id " +
                    "for updating");
        }
        User dbUser = getById(user);
        if (dbUser == null){
            return null;
        }
        user.setId(dbUser.getId());
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User remove(User user){
        if (user.getId() == 0){
            throw new IllegalArgumentException("User doesn't have mandatory field 'id' " +
                    "for operation remove");
        }
        User removedUser = users.remove(user.getId());
        return removedUser;
    }

    @VisibleForTesting
    protected static Map<Long, User> getUsers(){
        return users;
    }

    @VisibleForTesting
    protected void fillDbWithUsers(){
        users = new HashMap<>();
        User user1 = new User(1, "Sergei", "Shnaps", "SergeiShnaps@example.com");
        UserDaoImpl.users.put(user1.getId(), user1);
        User user2 = new User(2, "Pavel", "Velocipedov", "PavelVelosipedov@mail.com");
        UserDaoImpl.users.put(user2.getId(), user2);
        User user3 = new User(3, "Vitaliy", "Prishin", "VitaliyPrishin@gmail.com");
        UserDaoImpl.users.put(user3.getId(), user3);
        User user4 = new User(4, "Pavel", "Velocipedov", "PashaVelociped@yandex.ru");
        UserDaoImpl.users.put(user4.getId(), user4);
    }
}
