package com.example.simpleDemo.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.example.simpleDemo.entity.Users;
import java.util.List;

@Mapper
public interface UsersMapper {

    int insert(Users user);

    // 添加查询相关方法
    Users selectById(Long id);

    List<Users> selectAll();

    int deleteById(Long id);

    // 添加分页查询方法
    List<Users> selectAllWithPagination(String userName);

    // 补充缺失的方法
    List<Users> selectByUsername(String username);

    List<Users> selectByRole(String role);

    int updateById(Users user);
}