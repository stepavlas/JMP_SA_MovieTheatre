package com.stepavlas.movieTheatre.impl;

import com.google.common.annotations.VisibleForTesting;
import com.stepavlas.movieTheatre.User;
import com.stepavlas.movieTheatre.UserDao;
import com.stepavlas.movieTheatre.exceptions.IncorrectUserException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Степан on 17.03.2016.
 */
public class UserDaoImpl implements UserDao {
    private Map<Long, User> users = new HashMap<>();

    @Override
    public List<User> findUsers(User user) throws IncorrectUserException{
        validate(user);
        List<User> result = new ArrayList<>();
        if (user.getId() != 0){
            User dbUser = getById(user);
            if (dbUser != null){
                result.add(dbUser);
            }
        } else if (user.getEmail() != null){
            User dbUser = findByEmail(user);
            if (dbUser != null){
                result.add(dbUser);
            }
        } else if (user.getFirstName() != null && user.getLastName() != null){
            result = findByName(user);
        }
        return result;
    }

    private void validate(User user) throws IncorrectUserException{
        if (user == null){
            throw new IllegalArgumentException("User argument is null");
        }
        if (user.getId() < 0){
            throw new IncorrectUserException("user has negative id");
        }
        if (user.getEmail() != null && !user.getEmail().contains("@")){
            throw new IncorrectUserException("user has incorrect email");
        }
    }

    @Override
    public User getById(User user) throws IncorrectUserException{
        validate(user);
        if (user.getId() == 0){
            throw new IncorrectUserException("User doesn't have id");
        }
        return users.get(user.getId());
    }

    private User findByEmail(User user) throws IncorrectUserException{
        for (User dbUser: users.values()){
            if (user.getEmail().equals(dbUser.getEmail())){
                return dbUser;
            }
        }
        return null;
    }

    private List<User> findByName(User user) throws IncorrectUserException{
        List<User> result = new ArrayList<>();
        for (User dbUser: users.values()){
            if (user.getLastName().equals(dbUser.getLastName()) &&
                    user.getFirstName().equals(dbUser.getFirstName())){
                result.add(dbUser);
            }
        }
        return result;
    }

    @Override
    public void add(User user) throws IncorrectUserException{
        validate(user);
        if (user.getId() != 0 || user.getEmail() == null || user.getFirstName() == null ||
                user.getLastName() == null){
            throw new IncorrectUserException("User doesn't have mandatory field for adding");
        }
        List<User> dbUsers = findUsers(user);
        if (!dbUsers.isEmpty()) {
            throw new IllegalArgumentException("User already exists");
        }
        user.setId(users.size() + 1);
        users.put(user.getId(), user);
    }

    @Override
    public User update(User user) throws IncorrectUserException{
        validate(user);
        if (user.getId() == 0 || user.getEmail() == null || user.getFirstName() == null ||
                user.getLastName() == null){
            throw new IncorrectUserException("User doesn't have mandatory field or has incorrect id " +
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
    public User remove(User user) throws IncorrectUserException{
        validate(user);
        if (user.getId() == 0){
            throw new IncorrectUserException("User doesn't have mandatory field 'id' " +
                    "for operation remove");
        }
        User removedUser = users.remove(user.getId());
        return removedUser;
    }

    public void setUsers(Map<Long, User> users){
        this.users = users;
    }

    public Map<Long, User> getUsers(){
        return users;
    }
}
