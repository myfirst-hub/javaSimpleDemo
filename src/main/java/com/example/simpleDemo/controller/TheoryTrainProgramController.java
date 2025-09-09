package com.example.simpleDemo.controller;

import com.example.simpleDemo.entity.TheoryTrainProgram;
import com.example.simpleDemo.mapper.TheoryTrainProgramMapper;
import com.example.simpleDemo.service.TheoryTrainProgramService;
import com.example.simpleDemo.utils.ApiResponse;
import com.example.simpleDemo.utils.PageInfoResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class TheoryTrainProgramController {

    private static final Logger logger = LoggerFactory.getLogger(TheoryTrainProgramController.class);

    @Autowired
    private TheoryTrainProgramService theoryTrainProgramService;

    @GetMapping("/theoryTrainProgram/list")
    public ResponseEntity<ApiResponse<PageInfoResult<TheoryTrainProgram>>> getTrainProgramsWithRelations(
            @RequestParam(required = true, defaultValue = "1") Integer pageNum,
            @RequestParam(required = true, defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String semester) {
        logger.info(
                "Get train programs with relations endpoint accessed with params: pageNum={}, pageSize={}, name={}, semester={}",
                pageNum, pageSize, name, semester);
        try {
            PageInfoResult<TheoryTrainProgram> result = theoryTrainProgramService.selectTheoryTrainProgramList(
                    pageNum,
                    pageSize,
                    name,
                    semester);
            ApiResponse<PageInfoResult<TheoryTrainProgram>> response = ApiResponse.success(result);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error occurred while fetching train programs with relations", e);
            ApiResponse<PageInfoResult<TheoryTrainProgram>> response = ApiResponse
                    .error("Failed to fetch train programs with relations");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 新增理论培训计划接口
    @PostMapping("/theoryTrainProgram/create")
    public ResponseEntity<ApiResponse<Integer>> addTheoryTrainProgram(
            @RequestBody TheoryTrainProgram theoryTrainProgram) {
        logger.info("Add theory train program endpoint accessed with params: {}", theoryTrainProgram);
        try {
            int result = theoryTrainProgramService.insertTheoryTrainProgram(theoryTrainProgram);
            ApiResponse<Integer> response = ApiResponse.success(result);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error occurred while adding theory train program", e);
            ApiResponse<Integer> response = ApiResponse.error("Failed to add theory train program");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 编辑理论培训计划接口
    @PostMapping("/theoryTrainProgram/update")
    public ResponseEntity<ApiResponse<Integer>> updateTheoryTrainProgram(
            @RequestBody TheoryTrainProgram theoryTrainProgram) {
        logger.info("Update theory train program endpoint accessed with params: {}", theoryTrainProgram);
        try {
            int result = theoryTrainProgramService.updateTheoryTrainProgram(theoryTrainProgram);
            ApiResponse<Integer> response = ApiResponse.success(result);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error occurred while updating theory train program", e);
            ApiResponse<Integer> response = ApiResponse.error("Failed to update theory train program");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 删除理论培训计划接口
    @PostMapping("/theoryTrainProgram/delete")
    public ResponseEntity<ApiResponse<Integer>> deleteTheoryTrainProgram(
            @RequestBody TheoryTrainProgram theoryTrainProgram) {
        logger.info("Delete theory train program endpoint accessed with params: id={}", theoryTrainProgram.getId());
        try {
            int result = theoryTrainProgramService.deleteTheoryTrainProgram(theoryTrainProgram.getId(),
                    theoryTrainProgram.getSubjectId());
            ApiResponse<Integer> response = ApiResponse.success(result);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error occurred while deleting theory train program", e);
            ApiResponse<Integer> response = ApiResponse.error("Failed to delete theory train program");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 根据ID查询理论培训计划详情接口
    @GetMapping("/theoryTrainProgram/detail")
    public ResponseEntity<ApiResponse<TheoryTrainProgram>> getTheoryTrainProgramById(
            @RequestParam(required = true) Long id) {
        logger.info("Get theory train program detail endpoint accessed with id: {}", id);
        try {
            TheoryTrainProgram result = theoryTrainProgramService.selectTheoryTrainProgramById(id);
            if (result == null) {
                ApiResponse<TheoryTrainProgram> response = ApiResponse.error("Theory train program not found");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            ApiResponse<TheoryTrainProgram> response = ApiResponse.success(result);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error occurred while fetching theory train program detail", e);
            ApiResponse<TheoryTrainProgram> response = ApiResponse.error("Failed to fetch theory train program detail");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 新增：上传Excel并解析插入数据接口
    @PostMapping("/theoryTrainProgram/uploadTestResult")
    public ResponseEntity<ApiResponse<String>> uploadTestResultExcel(@RequestParam("file") MultipartFile file,
            @RequestParam("subjectId") Long subjectId, @RequestParam("trainProgramId") Long trainProgramId,
            @RequestParam("studentId") Long studentId) {
        logger.info("Upload test result excel endpoint accessed");
        try {
            if (file.isEmpty()) {
                ApiResponse<String> response = ApiResponse.error("上传文件为空");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            String fileName = file.getOriginalFilename();
            if (!fileName.endsWith(".xls") && !fileName.endsWith(".xlsx")) {
                ApiResponse<String> response = ApiResponse.error("文件格式不正确，请上传Excel文件");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            int result = theoryTrainProgramService.importTestResultFromExcel(file, subjectId, trainProgramId,
                    studentId);
            ApiResponse<String> response = ApiResponse.success("成功导入" + result + "条记录");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error occurred while uploading test result excel", e);
            ApiResponse<String> response = ApiResponse.error("导入失败：" + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}