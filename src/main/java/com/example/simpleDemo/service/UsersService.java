package com.example.simpleDemo.service;

import com.example.simpleDemo.entity.Users;
import com.example.simpleDemo.mapper.UsersMapper;
import com.example.simpleDemo.utils.PageInfoResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UsersService {

    @Autowired
    private UsersMapper usersMapper;

    public boolean save(Users user) throws DuplicateKeyException {
        // 设置创建时间和更新时间
        LocalDateTime now = LocalDateTime.now();
        user.setCreatedAt(now);
        user.setUpdatedAt(now);

        // 调用mapper插入数据
        int result = usersMapper.insert(user);

        return result > 0;
    }

    // 实现查询相关方法
    public Users getById(Long id) {
        return usersMapper.selectById(id);
    }

    public List<Users> getAllUsers() {
        return usersMapper.selectAll();
    }

    public List<Users> getByUsername(String username) {
        return usersMapper.selectByUsername(username);
    }

    // 实现分页查询方法
    public PageInfoResult<Users> getAllUsersWithPagination(int pageNum, int pageSize, String userName) {
        PageHelper.startPage(pageNum, pageSize);
        List<Users> usersList = usersMapper.selectAllWithPagination(userName);
        PageInfo<Users> pageInfo = new PageInfo<>(usersList);
        return new PageInfoResult<>(pageInfo);
    }

    // 实现更新方法
    public boolean update(Users user) {
        // 设置更新时间
        user.setUpdatedAt(LocalDateTime.now());

        // 调用mapper更新数据
        int result = usersMapper.updateById(user);

        return result > 0;
    }

    // 实现删除方法
    public boolean deleteById(Long id) {
        int result = usersMapper.deleteById(id);
        return result > 0;
    }
}