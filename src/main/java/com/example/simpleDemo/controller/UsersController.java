package com.example.simpleDemo.controller;

import com.example.simpleDemo.entity.Users;
import com.example.simpleDemo.service.UsersService;
import com.example.simpleDemo.mapper.UsersMapper;
import com.example.simpleDemo.utils.ApiResponse;
import com.example.simpleDemo.utils.PageInfoResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@RestController
public class UsersController {

    @Autowired
    private UsersService usersService;

    @Autowired
    private UsersMapper usersMapper;

    // Token配置
    private static final String SECRET_KEY_STRING = "mySecretKeymySecretKeymySecretKeymySecretKeymySecretKey";
    private static final long EXPIRATION_TIME = 86400000; // 24小时 (毫秒)

    // 插入用户数据的接口
    @PostMapping("/users/insert")
    public ResponseEntity<ApiResponse<String>> insertUser(@RequestBody Users user) {
        // 调用service层保存用户数据
        try {
            boolean success = usersService.save(user);

            if (success) {
                String result = "User inserted successfully: " + user.toString();
                ApiResponse<String> response = ApiResponse.success(result);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                ApiResponse<String> response = ApiResponse.error("Failed to insert user");
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (DuplicateKeyException e) {
            ApiResponse<String> response = ApiResponse.error("Email or username already exists");
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }
    }

    // 登录接口
    @PostMapping("/users/login")
    public ResponseEntity<ApiResponse<Object>> login(@RequestBody LoginRequest loginRequest) {
        try {
            // 根据用户名查找用户
            List<Users> users = usersService.getByUsername(loginRequest.getUsername());

            if (users.isEmpty()) {
                ApiResponse<Object> response = ApiResponse.error("用户名不存在");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            Users user = users.get(0);

            // 简单的密码校验（实际项目中应该使用加密后的密码比对）
            if (!user.getPasswordHash().equals(loginRequest.getPassword())) {
                ApiResponse<Object> response = ApiResponse.error("密码错误");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            // 生成简单Token
            String token = generateToken(user);

            // 创建返回结果对象
            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setToken(token);
            loginResponse.setUser(user);

            ApiResponse<Object> response = ApiResponse.success(loginResponse);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse<Object> response = ApiResponse.error("Login failed: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 新增的生成token方法
    private String generateToken(Users user) {
        try {
            // 使用UUID和用户信息生成唯一标识
            String rawToken = UUID.randomUUID().toString() +
                    user.getId() +
                    user.getUsername() +
                    System.currentTimeMillis() +
                    SECRET_KEY_STRING;

            // 使用MD5生成固定长度的哈希值作为token
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(rawToken.getBytes());
            byte[] digest = md.digest();

            // 转换为十六进制字符串
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b & 0xff));
            }

            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            // 如果MD5不可用，则使用UUID作为备选方案
            return UUID.randomUUID().toString().replace("-", "");
        }
    }

    // 登出接口
    @PostMapping("/users/logout")
    public ResponseEntity<ApiResponse<String>> logout() {
        // 在基于JWT的无状态认证中，登出操作通常在前端删除token即可
        // 这里可以添加token黑名单等高级功能
        ApiResponse<String> response = ApiResponse.success("Logout successful");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 查询所有用户接口（分页）
    @GetMapping("/users")
    public ResponseEntity<ApiResponse<PageInfoResult<Users>>> getAllUsers(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) String username) {
        try {
            PageInfoResult<Users> result = usersService.getAllUsersWithPagination(pageNum, pageSize, username);
            ApiResponse<PageInfoResult<Users>> response = ApiResponse.success(result);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse<PageInfoResult<Users>> response = ApiResponse.error("Failed to fetch users");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 查询所有用户接口（分页）
    @GetMapping("/users/teachers")
    public ResponseEntity<ApiResponse<List<Users>>> getAllTeachers() {
        try {
            List<Users> result = usersMapper.selectByRole("teacher");
            ApiResponse<List<Users>> response = ApiResponse.success(result);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse<List<Users>> response = ApiResponse.error("Failed to fetch users");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 更新用户信息接口
    @PostMapping("/users/update")
    public ResponseEntity<ApiResponse<String>> updateUser(@RequestBody Users user) {
        boolean success = usersService.update(user);

        if (success) {
            String result = "User updated successfully: " + user.toString();
            ApiResponse<String> response = ApiResponse.success(result);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ApiResponse<String> response = ApiResponse.error("Failed to update user");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 删除用户接口
    @GetMapping("/users/delete")
    public ResponseEntity<ApiResponse<String>> deleteUser(@RequestParam(required = true) Long id) {
        boolean success = usersService.deleteById(id);

        if (success) {
            String result = "User deleted successfully with ID: " + id;
            ApiResponse<String> response = ApiResponse.success(result);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ApiResponse<String> response = ApiResponse.error("Failed to delete user with ID: " + id);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 登录请求数据类
    public static class LoginRequest {
        private String username;
        private String password;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    // 登录响应数据类
    public static class LoginResponse {
        private String token;
        private Users user;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public Users getUser() {
            return user;
        }

        public void setUser(Users user) {
            this.user = user;
        }
    }
}