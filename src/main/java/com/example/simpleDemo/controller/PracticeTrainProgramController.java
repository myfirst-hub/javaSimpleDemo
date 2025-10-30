package com.example.simpleDemo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.simpleDemo.dto.PracticeTrainProgramListDTO;
import com.example.simpleDemo.entity.PracticeTrainProgram;
import com.example.simpleDemo.entity.PracticeTrainProgram;
import com.example.simpleDemo.service.PracticeTrainProgramService;
import com.example.simpleDemo.utils.ApiResponse;
import com.example.simpleDemo.utils.PageInfoResult;

@RestController
public class PracticeTrainProgramController {
    private static final Logger logger = LoggerFactory.getLogger(PracticeTrainProgramController.class);

    @Autowired
    private PracticeTrainProgramService practiceTrainProgramService;

    @PostMapping("/practiceTrainProgram/list")
    public ResponseEntity<ApiResponse<PageInfoResult<PracticeTrainProgram>>> getTrainProgramsWithRelations(
            @RequestBody(required = false) PracticeTrainProgramListDTO request) {

        if (request == null) {
            request = new PracticeTrainProgramListDTO();
        }

        logger.info(
                "Get train programs with relations endpoint accessed with params: pageNum={}, pageSize={}, name={}, semester={}, subjectIds={}",
                request.getPageNum(), request.getPageSize(), request.getName(), request.getSemester(),
                request.getSubjectIds());
        try {
            PageInfoResult<PracticeTrainProgram> result = practiceTrainProgramService.selectPracticeTrainProgramList(
                    request.getPageNum() != null ? request.getPageNum() : 1,
                    request.getPageSize() != null ? request.getPageSize() : 10,
                    request.getName(),
                    request.getSemester(),
                    request.getSubjectIds());
            ApiResponse<PageInfoResult<PracticeTrainProgram>> response = ApiResponse.success(result);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error occurred while fetching train programs with relations", e);
            ApiResponse<PageInfoResult<PracticeTrainProgram>> response = ApiResponse
                    .error("Failed to fetch train programs with relations");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 新增实操培训计划接口
    @PostMapping("/practiceTrainProgram/create")
    public ResponseEntity<ApiResponse<Integer>> addPracticeTrainProgram(
            @RequestBody PracticeTrainProgram practiceTrainProgram) {
        logger.info("Add practice train program endpoint accessed with params: {}", practiceTrainProgram);
        try {
            int result = practiceTrainProgramService.insertPracticeTrainProgram(practiceTrainProgram);
            ApiResponse<Integer> response = ApiResponse.success(result);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error occurred while adding practice train program", e);
            ApiResponse<Integer> response = ApiResponse.error("Failed to add practice train program");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 编辑实操培训计划接口
    @PostMapping("/practiceTrainProgram/update")
    public ResponseEntity<ApiResponse<Integer>> updatePracticeTrainProgram(
            @RequestBody PracticeTrainProgram practiceTrainProgram) {
        logger.info("Update practice train program endpoint accessed with params: {}", practiceTrainProgram);
        try {
            int result = practiceTrainProgramService.updatePracticeTrainProgram(practiceTrainProgram);
            ApiResponse<Integer> response = ApiResponse.success(result);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error occurred while updating practice train program", e);
            ApiResponse<Integer> response = ApiResponse.error("Failed to update practice train program");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 删除实操培训计划接口
    @PostMapping("/practiceTrainProgram/delete")
    public ResponseEntity<ApiResponse<Integer>> deletePracticeTrainProgram(
            @RequestBody PracticeTrainProgram practiceTrainProgram) {
        logger.info("Delete practice train program endpoint accessed with params: id={}", practiceTrainProgram.getId());
        try {
            int result = practiceTrainProgramService.deletePracticeTrainProgram(practiceTrainProgram.getId(),
                    practiceTrainProgram.getSubjectId());
            ApiResponse<Integer> response = ApiResponse.success(result);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error occurred while deleting practice train program", e);
            ApiResponse<Integer> response = ApiResponse.error("Failed to delete practice train program");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 根据ID查询实操培训计划详情接口
    @GetMapping("/practiceTrainProgram/detail")
    public ResponseEntity<ApiResponse<PracticeTrainProgram>> getPracticeTrainProgramById(
            @RequestParam(required = true) Long id) {
        logger.info("Get practice train program detail endpoint accessed with id: {}", id);
        try {
            PracticeTrainProgram result = practiceTrainProgramService.selectPracticeTrainProgramById(id);
            if (result == null) {
                ApiResponse<PracticeTrainProgram> response = ApiResponse.error("Practice train program not found");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            ApiResponse<PracticeTrainProgram> response = ApiResponse.success(result);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error occurred while fetching practice train program detail", e);
            ApiResponse<PracticeTrainProgram> response = ApiResponse
                    .error("Failed to fetch practice train program detail");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 新增：上传Excel并解析插入数据接口
    @PostMapping("/practiceTrainProgram/uploadTestResult")
    public ResponseEntity<ApiResponse<String>> uploadTestResultExcel(@RequestParam("files") MultipartFile[] files,
            @RequestParam("subjectId") Long subjectId) {
        logger.info("Upload test result excel endpoint accessed");
        try {
            if (files == null || files.length == 0) {
                ApiResponse<String> response = ApiResponse.error("上传文件为空");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            int totalResult = 0;
            for (MultipartFile file : files) {
                if (file.isEmpty()) {
                    continue;
                }

                String fileName = file.getOriginalFilename();
                if (!fileName.endsWith(".xls") && !fileName.endsWith(".xlsx")) {
                    ApiResponse<String> response = ApiResponse.error("文件格式不正确，请上传Excel文件");
                    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
                }

                int result = practiceTrainProgramService.importTestResultFromExcel(file, subjectId);
                totalResult += result;
            }

            ApiResponse<String> response = ApiResponse.success("成功导入" + totalResult + "条记录");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error occurred while uploading test result excel", e);
            ApiResponse<String> response = ApiResponse.error("导入失败：" + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
