package com.cheng.mybatis;

import com.cheng.mybatis.domain.User;
import com.cheng.mybatis.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
public class UserMapperTest {
    @Resource
    private UserMapper userMapper;

    @Test
    public void test(){
        List<User> users = userMapper.selectAll();
        System.out.println("users = " + users);
    }
}
