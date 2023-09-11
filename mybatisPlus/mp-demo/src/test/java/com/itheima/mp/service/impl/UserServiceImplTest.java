package com.itheima.mp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.mp.domain.po.User;
import com.itheima.mp.enums.UserStatus;
import com.itheima.mp.service.IUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest(args = "--mpw.key=d24f64e99db8cdcf")
class UserServiceImplTest {

    @Autowired
    private IUserService userService;

    @Test
    void testSaveUser() {
        User user = new User();
        user.setUsername("Amy2");
        user.setPassword("123");
        user.setPhone("18688990011");
        user.setBalance(2000000);
        user.setInfo("{\"age\": 18, \"intro\": \"Java老师\", \"gender\": \"female\"}");
//        user.setInfo(UserInfo.of(18, "Java老师", "female"));
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        user.setStatus(UserStatus.NORMAL);
        userService.save(user);
    }

    @Test
    void testGetById() {
        User user = userService.getById(1L);
        System.out.println("user = " + user);
    }

    @Test
    void testSelect() {
        List<User> list = userService.list(new QueryWrapper<User>().like("username", "o"));
        list.forEach(System.out::println);
    }

    @Test
    void testSaveOneByOne() {
        long b = System.currentTimeMillis();
        for (int i = 1; i <= 100000; i++) {
            userService.save(buildUser(i));
        }
        long e = System.currentTimeMillis();
        System.out.println("耗时：" + (e - b));
    }

    private User buildUser(int i) {
        User user = new User();
        user.setUsername("user_" + i);
        user.setPassword("123");
        user.setPhone("" + (18688190000L + i));
        user.setBalance(2000);
        // user.setInfo("{\"age\": 24, \"intro\": \"英文老师\", \"gender\": \"female\"}");
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(user.getCreateTime());
        return user;
    }

    @Test
    void testSaveBatch() {
        List<User> list = new ArrayList<>(1000);
        long b = System.currentTimeMillis();
        for (int i = 1; i <= 100000; i++) {
            list.add(buildUser(i));
            if (i % 1000 == 0) {
                userService.saveBatch(list);
                list.clear();
            }
        }
        long e = System.currentTimeMillis();
        System.out.println("耗时：" + (e - b));
    }

    @Test
    void testLambdaQuery() {

        User user = userService.lambdaQuery()
                .eq(User::getUsername, "Rose")
                .one();

        System.out.println("user = " + user);

        List<User> list = userService.lambdaQuery()
                .like(User::getUsername, "o")
                .list();
        list.forEach(System.out::println);

        Long count = userService.lambdaQuery()
                .like(User::getUsername, "o")
                .count();
        System.out.println("count = " + count);
    }

    @Test
    void testQueryUsers() {
        List<User> list = queryUsers("o", 1, null, null);
        list.forEach(System.out::println);
    }

    public List<User> queryUsers(String username, Integer status, Long min, Long max) {
        return userService.lambdaQuery()
                .like(username != null, User::getUsername, username)
                .eq(status != null, User::getStatus, status)
                .gt(min != null, User::getBalance, min)
                .lt(max != null, User::getBalance, max)
                .list();
    }

    @Test
    void testLambdaUpdate() {
        updateBalance(0L, 1L, null);
    }

    public void updateBalance(Long balance, Long id, String username){
        if (id == null && username == null) {
            // 不符合要求，至少要有一个更新条件
            throw new RuntimeException("更新条件不能为空");
        }
        // update user set balance = ? , status = 2 WHERE id = ? AND username = ?
        userService.lambdaUpdate()
                .set(User::getBalance, balance)
                .set(balance == 0, User::getStatus, 2)
                .eq(id != null, User::getId, id)
                .eq(username != null, User::getUsername, username)
                .update(); // 执行update
    }

    @Test
    void testPageQuery() {
        int pageNo = 1, pageSize = 5;
        // 1.分页条件
        Page<User> p = Page.of(pageNo, pageSize);
        // 2.排序条件
        p.addOrder(new OrderItem("balance", false));

        // 3.查询
        Page<User> page = userService.page(p);

        // 4.分页结果
        long total = page.getTotal();
        System.out.println("total = " + total);
        long pages = page.getPages();
        System.out.println("pages = " + pages);
        List<User> records = page.getRecords();
        for (User record : records) {
            System.out.println("record = " + record);
        }
    }
}