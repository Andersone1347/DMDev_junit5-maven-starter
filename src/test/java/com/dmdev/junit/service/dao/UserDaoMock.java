package com.dmdev.junit.service.dao;

import com.dmdev.junit.dao.UserDao;
import org.mockito.stubbing.Answer1;

import java.util.HashMap;
import java.util.Map;

public class UserDaoMock extends UserDao {

    private Map<Integer, Boolean> answers = new HashMap<>();
//    private Answer1<Integer, Boolean> answer1;

    @Override
    public boolean delete(Integer userId) {

        return answers.getOrDefault(userId,false);
    }
}
