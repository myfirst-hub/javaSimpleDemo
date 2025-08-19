package com.example.simpleDemo.controller;

import com.example.simpleDemo.entity.KnowledgeTree;
import com.example.simpleDemo.entity.Subject;
import com.example.simpleDemo.entity.SubjectWithKnowledgesDTO;
import com.example.simpleDemo.service.KnowledgeTreeService;
import com.example.simpleDemo.service.SubjectService;
import com.example.simpleDemo.utils.ApiResponse;
import com.example.simpleDemo.utils.PageInfoResult;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

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
            String json = """
                    {
                        "name": "高等数学",
                        "children": [
                            {
                                "name": "第一章 函数与极限",
                                "children": [
                                    {
                                        "name": "1.1 函数",
                                        "children": [
                                            {"name": "函数的定义", "type": "concept"},
                                            {"name": "函数的性质", "type": "concept"},
                                            {"name": "反函数与复合函数", "type": "concept"},
                                            {"name": "初等函数", "type": "concept"}
                                        ]
                                    },
                                    {
                                        "name": "1.2 数列的极限",
                                        "children": [
                                            {"name": "数列极限的定义", "type": "concept"},
                                            {"name": "收敛数列的性质", "type": "concept"},
                                            {"name": "极限存在准则", "type": "concept"},
                                            {"name": "重要极限 lim(1+1/n)^n=e", "type": "formula"}
                                        ]
                                    },
                                    {
                                        "name": "1.3 函数的极限",
                                        "children": [
                                            {"name": "函数极限的定义", "type": "concept"},
                                            {"name": "函数极限的性质", "type": "concept"},
                                            {"name": "无穷小与无穷大", "type": "concept"},
                                            {"name": "极限运算法则", "type": "method"}
                                        ]
                                    },
                                    {
                                        "name": "1.4 连续函数",
                                        "children": [
                                            {"name": "连续性的定义", "type": "concept"},
                                            {"name": "间断点分类", "type": "concept"},
                                            {"name": "闭区间上连续函数的性质", "type": "theorem"}
                                        ]
                                    }
                                ]
                            }
                        ]
                    }
                    """;

            // 解析JSON
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(json);

            System.out.println("知识点树形结构遍历结果:");
            System.out.println("==========================");

            // 从根节点开始遍历
            saveKnowledgeTree(rootNode, null, 0);

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
     * 递归保存知识点树到数据库
     *
     * @param node     当前节点
     * @param parentId 父节点ID
     * @param level    节点层级
     */
    private void saveKnowledgeTree(JsonNode node, Long parentId, int level) {
        KnowledgeTree knowledgeTree = new KnowledgeTree();
        knowledgeTree.setName(node.get("name").asText());
        if (node.has("masteryLevel")) {
            knowledgeTree.setMasteryLevel(node.get("masteryLevel").asInt());
        }
        knowledgeTree.setParentId(parentId);
        // knowledgeTree.setLevel(level);

        // 保存当前节点
        KnowledgeTree savedNode = knowledgeTreeService.createKnowledgeTree(knowledgeTree);

        // 递归处理子节点
        if (node.has("children")) {
            JsonNode childrenNode = node.get("children");
            if (childrenNode.isArray()) {
                for (JsonNode childNode : childrenNode) {
                    saveKnowledgeTree(childNode, savedNode.getId(), level + 1);
                }
            }
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