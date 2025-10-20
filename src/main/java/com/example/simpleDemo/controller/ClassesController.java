package com.example.simpleDemo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.simpleDemo.entity.Classes;
import com.example.simpleDemo.utils.ApiResponse;
import com.example.simpleDemo.utils.PageInfoResult;

import com.example.simpleDemo.service.ClassesService;

@RestController
public class ClassesController {

    private static final Logger logger = LoggerFactory.getLogger(ClassesController.class);

    @Autowired
    private ClassesService classesService;

    /**
     * 分页查询班级列表
     * 
     * @param pageNum  页码，默认为1
     * @param pageSize 每页大小，默认为10
     * @param name     班级名称（可选）
     * @return 分页结果
     */
    @GetMapping("/classes/list")
    public ResponseEntity<ApiResponse<PageInfoResult<Classes>>> findClasses(
            @RequestParam(required = true, defaultValue = "1") Integer pageNum,
            @RequestParam(required = true, defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Long teacherId) {

        logger.info("Get classes endpoint accessed with params: pageNum={}, pageSize={}, name={}, teacherId={}",
                pageNum, pageSize, name, teacherId);

        try {
            PageInfoResult<Classes> result = classesService
                    .findClasses(pageNum, pageSize, name, teacherId);

            ApiResponse<PageInfoResult<Classes>> response = ApiResponse.success(result);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error occurred while fetching classes", e);
            ApiResponse<PageInfoResult<Classes>> response = ApiResponse
                    .error("Failed to fetch classes");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/classes/create")
    public ResponseEntity<ApiResponse<Classes>> createClasses(@RequestBody Classes classes) {
        logger.info("Create classes endpoint accessed with classes: {}", classes);
        try {
            // 调用服务创建学生
            int result = classesService.createClasses(classes);

            if (result > 0) {
                // 创建成功
                ApiResponse<Classes> response = ApiResponse.success(classes, "Classes created successfully");
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                // 创建失败
                ApiResponse<Classes> response = ApiResponse.error("Failed to create classes");
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            logger.error("Error occurred while creating classes", e);
            ApiResponse<Classes> response = ApiResponse.error("Failed to create classes: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/classes/update")
    public ResponseEntity<ApiResponse<Classes>> updateClasses(@RequestBody Classes classes) {
        logger.info("Update classes endpoint accessed with id: {} and classes: {}", classes.getId(), classes);
        try {
            // 调用服务更新班级
            int result = classesService.updateClasses(classes);

            if (result > 0) {
                // 更新成功
                ApiResponse<Classes> response = ApiResponse.success(classes, "Classes updated successfully");
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                // 更新失败
                ApiResponse<Classes> response = ApiResponse.error("Failed to update classes");
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            logger.error("Error occurred while updating classes", e);
            ApiResponse<Classes> response = ApiResponse.error("Failed to update classes: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/classes/delete")
    public ResponseEntity<ApiResponse<String>> deleteClasses(@RequestBody Classes classes) {
        logger.info("Delete classes endpoint accessed with id: {}", classes.getId());
        try {
            // 调用服务删除班级
            int result = classesService.deleteClasses(classes.getId());

            if (result > 0) {
                // 删除成功
                ApiResponse<String> response = ApiResponse.success("Classes deleted successfully");
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                // 删除失败
                ApiResponse<String> response = ApiResponse.error("Failed to delete classes");
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            logger.error("Error occurred while deleting classes", e);
            ApiResponse<String> response = ApiResponse.error("Failed to delete classes: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}