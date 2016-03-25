package com.stepavlas.movieTheatre.impl;

import com.stepavlas.movieTheatre.User;
import com.stepavlas.movieTheatre.exceptions.IncorrectUserException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Степан on 17.03.2016.
 */
public class UserDaoTest {
    private static UserDaoImpl userDao = new UserDaoImpl();

    @Before
    public void init(){
        Map<Long, User> users = new HashMap<>();
        User user1 = new User(1, "Sergei", "Shnaps", "SergeiShnaps@example.com",null);
        users.put(user1.getId(), user1);
        User user2 = new User(2, "Pavel", "Velocipedov", "PavelVelosipedov@mail.com",null);
        users.put(user2.getId(), user2);
        User user3 = new User(3, "Vitaliy", "Prishin", "VitaliyPrishin@gmail.com",null);
        users.put(user3.getId(), user3);
        User user4 = new User(4, "Pavel", "Velocipedov", "PashaVelociped@yandex.ru",null);
        users.put(user4.getId(), user4);
        userDao.setUsers(users);
    }

    @Test
    public void findUserByIdTest() throws IncorrectUserException{
        User user1 = new User();
        user1.setId(2);
        List<User> users = userDao.findUsers(user1);
        User user2 = new User(2, "Pavel", "Velocipedov", "PavelVelosipedov@mail.com", null);
        Assert.assertEquals(1, users.size());
        user1 = users.get(0);
        Assert.assertTrue(usersAreEqual(user1, user2));
    }

    @Test
    public void findUserByEmailTest() throws IncorrectUserException{
        User user1 = new User();
        user1.setEmail("SergeiShnaps@example.com");
        List<User> users = userDao.findUsers(user1);
        User user2 = new User(1, "Sergei", "Shnaps", "SergeiShnaps@example.com", null);
        Assert.assertEquals(1, users.size());
        user1 = users.get(0);
        Assert.assertTrue(usersAreEqual(user1, user2));
    }

    @Test
    public void findUserByNameTest() throws IncorrectUserException{
        User user1 = new User();
        user1.setFirstName("Pavel");
        user1.setLastName("Velocipedov");
        List<User> users = userDao.findUsers(user1);
        User user2 = new User(2, "Pavel", "Velocipedov", "PavelVelosipedov@mail.com", null);
        User user4 = new User(4, "Pavel", "Velocipedov", "PashaVelociped@yandex.ru", null);
    }

    @Test
    public void addUserTest(){
        User user1 = new User("Leonid", "Antonitov", "LeoAntonitov@example.com");
        try {
            userDao.add(user1);
        } catch (IncorrectUserException e) {
            Assert.fail("add method threw IncorrectUserException with message:" + e.getMessage());
        }
        Map<Long, User> users = userDao.getUsers();
        Assert.assertEquals(5, users.size());
        user1 = users.get((long)5);
        User user2 = new User(5, "Leonid", "Antonitov", "LeoAntonitov@example.com", null);
        Assert.assertTrue(usersAreEqual(user1, user2));
    }

    @Test
    public void updateUserTest(){
        Map<Long, User> users = userDao.getUsers();
        User oldUser = users.get((long)2);
        User user1 = new User(2, "Sergei", "Klimov", "SergKlimov@example.com", null);
        try {
            userDao.update(user1);
        } catch (IncorrectUserException e) {
            Assert.fail("add method threw IncorrectUserException with message:" + e.getMessage());
        }
        User newUser = users.get((long)2);
        Assert.assertEquals(4, users.size());
        Assert.assertFalse(usersAreEqual(oldUser, newUser));
        Assert.assertTrue(usersAreEqual(newUser, user1));
    }

    @Test
    public void removeUserTest(){
        Map<Long, User> oldUsers = userDao.getUsers();
        int oldSize = oldUsers.size();
        User user = new User();
        user.setId(3);

        try {
            userDao.remove(user);
        } catch (IncorrectUserException e) {
            Assert.fail("remove method threw IncorrectUserException with message:" + e.getMessage());
        }

        Map<Long, User> newUsers = userDao.getUsers();
        int newSize = newUsers.size();
        Assert.assertEquals(oldSize - 1, newSize);
        Assert.assertFalse(newUsers.containsKey((long)3));
    }

    private boolean usersAreEqual(User user1, User user2){
        if (user1.getId() != user2.getId()){
            return false;
        }
        if (!(strsAreEqual(user1.getFirstName(), user2.getFirstName()))){
            return false;
        }
        if (!(strsAreEqual(user1.getLastName(), user2.getLastName()))){
            return false;
        }
        if (!(strsAreEqual(user1.getEmail(), user2.getEmail()))){
            return false;
        }
        return true;
    }

    private boolean strsAreEqual(String str1, String str2){
        return (str1 == null ? str2 == null : str1.equals(str2));
    }
}
