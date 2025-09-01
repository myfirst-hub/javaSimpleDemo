package com.example.simpleDemo.controller;

import com.example.simpleDemo.entity.KnowledgeTree;
import com.example.simpleDemo.entity.Subject;
import com.example.simpleDemo.entity.Teacher;
import com.example.simpleDemo.entity.Subject;
import com.example.simpleDemo.service.KnowledgeTreeService;
import com.example.simpleDemo.service.SubjectKnowledgeService;
import com.example.simpleDemo.service.UploadService;
import com.example.simpleDemo.service.SubjectService;
import com.example.simpleDemo.utils.ApiResponse;
import com.example.simpleDemo.utils.PageInfoResult;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;

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

  @Autowired
  private SubjectKnowledgeService subjectKnowledgeService;

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
        "Get subjects endpoint accessed with params: pageNum={}, pageSize={}, name={}, semester={}",
        pageNum, pageSize, name, semester);

    try {
      PageInfoResult<Subject> result = subjectService
          .findSubjects(pageNum, pageSize, name, semester);

      ApiResponse<PageInfoResult<Subject>> response = ApiResponse.success(result);
      return new ResponseEntity<>(response, HttpStatus.OK);
    } catch (Exception e) {
      logger.error("Error occurred while fetching subjects", e);
      ApiResponse<PageInfoResult<Subject>> response = ApiResponse
          .error("Failed to fetch subjects");
      return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  /**
   * 通过教师id分页查询科目列表
   * 
   * @param pageNum  页码，默认为1
   * @param pageSize 每页大小，默认为10
   * @param name     科目名称（可选）
   * @param semester 学期（可选）
   * @return 分页结果
   */
  @GetMapping("/subjectList/teacherId")
  public ResponseEntity<ApiResponse<PageInfoResult<Subject>>> findSubjectsByTeacherId(
      @RequestParam(required = true, defaultValue = "1") Integer pageNum,
      @RequestParam(required = true, defaultValue = "10") Integer pageSize,
      @RequestParam(required = true) Long teacherId,
      @RequestParam(required = false) String name,
      @RequestParam(required = false) String semester) {

    logger.info(
        "Get subjects endpoint accessed with params: pageNum={}, pageSize={}, teacherId={}, name={}, semester={}",
        pageNum, pageSize, teacherId, name, semester);

    try {
      PageInfoResult<Subject> result = subjectService
          .findSubjectsByTeacherId(pageNum, pageSize, teacherId, name, semester);

      ApiResponse<PageInfoResult<Subject>> response = ApiResponse.success(result);
      return new ResponseEntity<>(response, HttpStatus.OK);
    } catch (Exception e) {
      logger.error("Error occurred while fetching subjects", e);
      ApiResponse<PageInfoResult<Subject>> response = ApiResponse
          .error("Failed to fetch subjects");
      return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  /**
   * 新增科目，只处理科目信息
   * 
   * @param subject 科目对象
   * @return 是否添加成功
   */
  @PostMapping("/subject/create")
  public ResponseEntity<ApiResponse<Boolean>> addSubject(
      @RequestBody Subject subject) {

    logger.info("Add subject endpoint accessed with params: subject={}",
        subject);

    try {
      boolean result = subjectService.addSubject(subject);
      ApiResponse<Boolean> response = ApiResponse.success(result);
      return new ResponseEntity<>(response, HttpStatus.OK);
    } catch (Exception e) {
      logger.error("Error occurred while adding subject", e);
      ApiResponse<Boolean> response = ApiResponse.error("Failed to add subject");
      return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  /**
   * 编辑科目，只处理科目信息
   * 
   * @param subject 包含科目和知识点信息的DTO对象
   * @return 是否编辑成功
   */
  @PostMapping("/subject/update")
  public ResponseEntity<ApiResponse<Boolean>> updateSubject(
      @RequestBody Subject subject) {

    logger.info("Update subject endpoint accessed with params: subject={}",
        subject);

    try {
      boolean result = subjectService.updateSubject(subject);
      ApiResponse<Boolean> response = ApiResponse.success(result);
      return new ResponseEntity<>(response, HttpStatus.OK);
    } catch (Exception e) {
      logger.error("Error occurred while updating subject", e);
      ApiResponse<Boolean> response = ApiResponse.error("Failed to update subject");
      return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  /**
   * 删除科目
   * 
   * @param subjectId 科目ID
   * @return 是否删除成功
   */
  @PostMapping("/subject/delete")
  public ResponseEntity<ApiResponse<Boolean>> deleteSubjectById(@RequestBody Subject subject) {
    logger.info("Delete subject endpoint accessed with params: subjectId={}", subject.getId());

    try {
      boolean result = subjectService.deleteSubjectById(subject.getId());
      ApiResponse<Boolean> response = ApiResponse.success(result);
      return new ResponseEntity<>(response, HttpStatus.OK);
    } catch (Exception e) {
      logger.error("Error occurred while deleting subject", e);
      ApiResponse<Boolean> response = ApiResponse.error("Failed to delete subject");
      return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  /**
   * 根据ID查询科目详情，包含知识点信息
   * 
   * @param id 科目ID
   * @return 科目详情信息
   */
  @GetMapping("/subject/detail")
  public ResponseEntity<ApiResponse<Object>> findSubjectById(
      @RequestParam(required = true) Long id) {

    logger.info("Get subject detail endpoint accessed with params: id={}", id);

    try {
      List<Long> ids = subjectKnowledgeService.findKnowledgeIdsBySubjectId(id);

      logger.info("Get subject detail endpoint accessed with params: ids={}", ids);

      List<KnowledgeTree> knowledgeTrees;
      int leafCount;

      // 添加对空列表的检查，避免SQL语法错误
      if (ids == null || ids.isEmpty()) {
        knowledgeTrees = List.of(); // 返回空列表而不是null
        leafCount = 0;
      } else {
        knowledgeTrees = knowledgeTreeService.buildKnowledgeTree(ids);
        leafCount = knowledgeTreeService.countLeafNodes(ids);
      }

      // 创建包含知识点树和叶子节点计数的返回对象
      java.util.Map<String, Object> result = new java.util.HashMap<>();
      result.put("knowledgeTrees", knowledgeTrees);
      result.put("leafCount", leafCount);

      ApiResponse<Object> response = ApiResponse.success(result);
      return new ResponseEntity<>(response, HttpStatus.OK);
    } catch (Exception e) {
      logger.error("Error occurred while fetching subject detail", e);
      ApiResponse<Object> response = ApiResponse.error("Failed to fetch subject detail");
      return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}