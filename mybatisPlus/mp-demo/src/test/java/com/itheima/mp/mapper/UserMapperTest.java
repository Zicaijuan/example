package com.itheima.mp.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.itheima.mp.domain.po.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
class UserMapperTest {

    @Resource
    private UserMapper userMapper;

    @Test
    void testQueryByIds(){
        List<User> users = userMapper.queryUserByIds(List.of(1L,2L,3L));
        users.forEach(System.out::println);
    }


    @Test
    void testInsert() {
        User user = new User();
        user.setId(5L);
        user.setUsername("Lucy");
        user.setPassword("123");
        user.setPhone("18688990011");
        user.setBalance(200);
        user.setInfo("{\"age\": 24, \"intro\": \"英文老师\", \"gender\": \"female\"}");
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        userMapper.insert(user);
    }

    @Test
    void testSelectById(){
        User user = userMapper.selectById(5L);
        System.out.println(user);
    }
    @Test
    void selectByIds(){
        List<User> users = userMapper.selectBatchIds(List.of(1L, 2L, 3L, 4L, 5L));
        users.forEach(System.out::println);
    }

    /**
     * 只修改有值的属性
     */
    @Test
    void testUpdateById() {
        User user = new User();
        user.setId(5L);
        user.setBalance(20000);
        userMapper.updateById(user);
    }

    /**
     * 利用QueryWrapper查询
     */
    @Test
    void testQueryWrapper(){
        //构建查询条件
        QueryWrapper<User> wrapper = new QueryWrapper<User>()
                .select("id","username","info","balance")
                .like("username","o")
                .ge("balance",1000);

        List<User> users = userMapper.selectList(wrapper);
        users.forEach(System.out::println);
    }
    @Test
    void testUpdateQueryWrapper(){
        User user = new User();
        user.setBalance(2000);

        QueryWrapper<User> wrapper = new QueryWrapper<User>()
                .eq("username","jack");

        //user中的非空字段会被作为set条件
        //UPDATE user SET balance=? WHERE (username = ?)

        userMapper.update(user,wrapper);
    }

    /**
     * UpdateWrapper手写sql语句
     */
    @Test
    void testUpdateWrapper(){
        UpdateWrapper<User> wrapper = new UpdateWrapper<User>()
                .setSql("balance = balance - 200")
                        .in("id",List.of(1L,2L,3L));
        userMapper.update(null,wrapper);
    }

}