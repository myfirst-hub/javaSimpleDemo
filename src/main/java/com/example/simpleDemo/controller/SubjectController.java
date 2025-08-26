package com.example.simpleDemo.controller;

import com.example.simpleDemo.entity.KnowledgeTree;
import com.example.simpleDemo.entity.Subject;
import com.example.simpleDemo.entity.SubjectWithKnowledgesDTO;
import com.example.simpleDemo.service.KnowledgeTreeService;
import com.example.simpleDemo.service.SubjectService;
import com.example.simpleDemo.utils.ApiResponse;
import com.example.simpleDemo.utils.PageInfoResult;
import com.fasterxml.jackson.databind.JsonNode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class SubjectController {

  private static final Logger logger = LoggerFactory.getLogger(SubjectController.class);

  @Autowired
  private SubjectService subjectService;

  @Autowired
  private KnowledgeTreeService knowledgeTreeService;

  /**
   * 分页查询科目列表，包含知识点信息
   * 
   * @param pageNum  页码，默认为1
   * @param pageSize 每页大小，默认为10
   * @param name     科目名称（可选）
   * @param semester 学期（可选）
   * @return 分页结果，包含知识点信息
   */
  @GetMapping("/subjects")
  public ResponseEntity<ApiResponse<PageInfoResult<SubjectService.SubjectWithKnowledges>>> findSubjectsWithKnowledges(
      @RequestParam(required = true, defaultValue = "1") Integer pageNum,
      @RequestParam(required = true, defaultValue = "10") Integer pageSize,
      @RequestParam(required = false) String name,
      @RequestParam(required = false) String semester) {

    logger.info(
        "Get subjects with knowledges endpoint accessed with params: pageNum={}, pageSize={}, name={}, semester={}",
        pageNum, pageSize, name, semester);

    try {
      PageInfoResult<SubjectService.SubjectWithKnowledges> result = subjectService
          .findSubjectsWithKnowledges(pageNum, pageSize, name, semester);

      ApiResponse<PageInfoResult<SubjectService.SubjectWithKnowledges>> response = ApiResponse.success(result);
      return new ResponseEntity<>(response, HttpStatus.OK);
    } catch (Exception e) {
      logger.error("Error occurred while fetching subjects with knowledges", e);
      ApiResponse<PageInfoResult<SubjectService.SubjectWithKnowledges>> response = ApiResponse
          .error("Failed to fetch subjects with knowledges");
      return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  /**
   * 分页查询科目列表
   * 
   * @param pageNum  页码，默认为1
   * @param pageSize 每页大小，默认为10
   * @param name     科目名称（可选）
   * @param semester 学期（可选）
   * @return 分页结果
   */
  @GetMapping("/subjectNew")
  public ResponseEntity<ApiResponse<PageInfoResult<Subject>>> findSubjects(
      @RequestParam(required = true, defaultValue = "1") Integer pageNum,
      @RequestParam(required = true, defaultValue = "10") Integer pageSize,
      @RequestParam(required = false) String name,
      @RequestParam(required = false) String semester) {

    logger.info(
        "Get subjects with knowledges endpoint accessed with params: pageNum={}, pageSize={}, name={}, semester={}",
        pageNum, pageSize, name, semester);

    try {
      PageInfoResult<Subject> result = subjectService
          .findSubjects(pageNum, pageSize, name, semester);

      ApiResponse<PageInfoResult<Subject>> response = ApiResponse.success(result);
      return new ResponseEntity<>(response, HttpStatus.OK);
    } catch (Exception e) {
      logger.error("Error occurred while fetching subjects with knowledges", e);
      ApiResponse<PageInfoResult<Subject>> response = ApiResponse
          .error("Failed to fetch subjects with knowledges");
      return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  /**
   * 新增科目，同时处理知识点和科目知识点映射关系
   * 
   * @param subjectWithKnowledgesDTO 包含科目和知识点信息的DTO对象
   * @return 是否添加成功
   */
  @PostMapping("/subject/create")
  public ResponseEntity<ApiResponse<Boolean>> addSubjectWithKnowledges(
      @RequestBody SubjectWithKnowledgesDTO subjectWithKnowledgesDTO) {

    logger.info("Add subject with knowledges endpoint accessed with params: subjectWithKnowledgesDTO={}",
        subjectWithKnowledgesDTO);

    try {
      boolean result = subjectService.addSubjectWithKnowledges(subjectWithKnowledgesDTO);
      ApiResponse<Boolean> response = ApiResponse.success(result);
      return new ResponseEntity<>(response, HttpStatus.OK);
    } catch (Exception e) {
      logger.error("Error occurred while adding subject with knowledges", e);
      ApiResponse<Boolean> response = ApiResponse.error("Failed to add subject with knowledges");
      return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  /**
   * 编辑科目，同时处理知识点和科目知识点映射关系
   * 
   * @param subjectWithKnowledgesDTO 包含科目和知识点信息的DTO对象
   * @return 是否编辑成功
   */
  @PostMapping("/subject/update")
  public ResponseEntity<ApiResponse<Boolean>> updateSubjectWithKnowledges(
      @RequestBody SubjectWithKnowledgesDTO subjectWithKnowledgesDTO) {

    logger.info("Update subject with knowledges endpoint accessed with params: subjectWithKnowledgesDTO={}",
        subjectWithKnowledgesDTO);

    try {
      boolean result = subjectService.updateSubjectWithKnowledges(subjectWithKnowledgesDTO);
      ApiResponse<Boolean> response = ApiResponse.success(result);
      return new ResponseEntity<>(response, HttpStatus.OK);
    } catch (Exception e) {
      logger.error("Error occurred while updating subject with knowledges", e);
      ApiResponse<Boolean> response = ApiResponse.error("Failed to update subject with knowledges");
      return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  /**
   * 删除科目及其关联的知识点
   * 
   * @param subjectId 科目ID
   * @return 是否删除成功
   */
  @PostMapping("/subject/delete")
  public ResponseEntity<ApiResponse<Boolean>> deleteSubjectByIdWithKnowledges(@RequestBody Subject subject) {
    logger.info("Delete subject with knowledges endpoint accessed with params: subjectId={}", subject.getId());

    try {
      boolean result = subjectService.deleteSubjectWithKnowledges(subject.getId());
      ApiResponse<Boolean> response = ApiResponse.success(result);
      return new ResponseEntity<>(response, HttpStatus.OK);
    } catch (Exception e) {
      logger.error("Error occurred while deleting subject with knowledges", e);
      ApiResponse<Boolean> response = ApiResponse.error("Failed to delete subject with knowledges");
      return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}