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
                                {
                                  "name": "函数的定义",
                                  "masteryLevel": 1
                                },
                                {
                                  "name": "函数的性质",
                                  "masteryLevel": 1
                                },
                                {
                                  "name": "反函数与复合函数",
                                  "masteryLevel": 2
                                },
                                {
                                  "name": "初等函数",
                                  "masteryLevel": 2
                                }
                              ]
                            },
                            {
                              "name": "1.2 数列的极限",
                              "children": [
                                {
                                  "name": "数列极限的定义",
                                  "masteryLevel": 3
                                },
                                {
                                  "name": "收敛数列的性质",
                                  "masteryLevel": 3
                                },
                                {
                                  "name": "极限存在准则",
                                  "masteryLevel": 2
                                },
                                {
                                  "name": "重要极限 lim(1+1/n)^n=e",
                                  "masteryLevel": 1
                                }
                              ]
                            },
                            {
                              "name": "1.3 函数的极限",
                              "children": [
                                {
                                  "name": "函数极限的定义",
                                  "masteryLevel": 4
                                },
                                {
                                  "name": "函数极限的性质",
                                  "masteryLevel": 4
                                },
                                {
                                  "name": "无穷小与无穷大",
                                  "masteryLevel": 4
                                },
                                {
                                  "name": "极限运算法则",
                                  "masteryLevel": 5
                                }
                              ]
                            },
                            {
                              "name": "1.4 连续函数",
                              "children": [
                                {
                                  "name": "连续性的定义",
                                  "masteryLevel": 4
                                },
                                {
                                  "name": "间断点分类",
                                  "masteryLevel": 4
                                },
                                {
                                  "name": "闭区间上连续函数的性质",
                                  "masteryLevel": 5
                                }
                              ]
                            }
                          ]
                        },
                        {
                          "name": "第二章 导数与微分",
                          "children": [
                            {
                              "name": "2.1 导数的概念",
                              "children": [
                                {
                                  "name": "导数的定义",
                                  "masteryLevel": 4
                                },
                                {
                                  "name": "导数的几何意义",
                                  "masteryLevel": 4
                                },
                                {
                                  "name": "函数的可导性与连续性",
                                  "masteryLevel": 5
                                }
                              ]
                            },
                            {
                              "name": "2.2 求导法则",
                              "children": [
                                {
                                  "name": "基本初等函数导数",
                                  "masteryLevel": 2
                                },
                                {
                                  "name": "四则运算法则",
                                  "masteryLevel": 5
                                },
                                {
                                  "name": "复合函数求导法则",
                                  "masteryLevel": 5
                                },
                                {
                                  "name": "隐函数求导法",
                                  "masteryLevel": 5
                                },
                                {
                                  "name": "参数方程求导法",
                                  "masteryLevel": 5
                                }
                              ]
                            },
                            {
                              "name": "2.3 高阶导数",
                              "children": [
                                {
                                  "name": "高阶导数的概念",
                                  "masteryLevel": 4
                                },
                                {
                                  "name": "莱布尼茨公式",
                                  "masteryLevel": 2
                                }
                              ]
                            },
                            {
                              "name": "2.4 微分",
                              "children": [
                                {
                                  "name": "微分的定义",
                                  "masteryLevel": 4
                                },
                                {
                                  "name": "微分公式与法则",
                                  "masteryLevel": 2
                                },
                                {
                                  "name": "微分在近似计算中的应用",
                                  "masteryLevel": 3
                                }
                              ]
                            }
                          ]
                        },
                        {
                          "name": "第三章 微分中值定理与导数的应用",
                          "children": [
                            {
                              "name": "3.1 微分中值定理",
                              "children": [
                                {
                                  "name": "罗尔定理",
                                  "masteryLevel": 5
                                },
                                {
                                  "name": "拉格朗日中值定理",
                                  "masteryLevel": 5
                                },
                                {
                                  "name": "柯西中值定理",
                                  "masteryLevel": 5
                                }
                              ]
                            },
                            {
                              "name": "3.2 洛必达法则",
                              "children": [
                                {
                                  "name": "0/0型未定式",
                                  "masteryLevel": 5
                                },
                                {
                                  "name": "∞/∞型未定式",
                                  "masteryLevel": 5
                                },
                                {
                                  "name": "其他未定式",
                                  "masteryLevel": 5
                                }
                              ]
                            },
                            {
                              "name": "3.3 泰勒公式",
                              "children": [
                                {
                                  "name": "泰勒中值定理",
                                  "masteryLevel": 5
                                },
                                {
                                  "name": "麦克劳林公式",
                                  "masteryLevel": 2
                                },
                                {
                                  "name": "常见函数的泰勒展开",
                                  "masteryLevel": 2
                                }
                              ]
                            },
                            {
                              "name": "3.4 函数性态研究",
                              "children": [
                                {
                                  "name": "函数的单调性",
                                  "masteryLevel": 5
                                },
                                {
                                  "name": "函数的极值",
                                  "masteryLevel": 4
                                },
                                {
                                  "name": "曲线的凹凸性与拐点",
                                  "masteryLevel": 4
                                },
                                {
                                  "name": "函数图形的描绘",
                                  "masteryLevel": 5
                                }
                              ]
                            }
                          ]
                        },
                        {
                          "name": "第四章 不定积分",
                          "children": [
                            {
                              "name": "4.1 不定积分的概念与性质",
                              "children": [
                                {
                                  "name": "原函数与不定积分",
                                  "masteryLevel": 4
                                },
                                {
                                  "name": "基本积分表",
                                  "masteryLevel": 2
                                },
                                {
                                  "name": "不定积分的性质",
                                  "masteryLevel": 1
                                }
                              ]
                            },
                            {
                              "name": "4.2 换元积分法",
                              "children": [
                                {
                                  "name": "第一类换元法",
                                  "masteryLevel": 5
                                },
                                {
                                  "name": "第二类换元法",
                                  "masteryLevel": 5
                                }
                              ]
                            },
                            {
                              "name": "4.3 分部积分法",
                              "children": [
                                {
                                  "name": "分部积分公式",
                                  "masteryLevel": 2
                                },
                                {
                                  "name": "常见积分技巧",
                                  "masteryLevel": 5
                                }
                              ]
                            },
                            {
                              "name": "4.4 有理函数的积分",
                              "children": [
                                {
                                  "name": "有理函数的分解",
                                  "masteryLevel": 5
                                },
                                {
                                  "name": "可化为有理函数的积分",
                                  "masteryLevel": 5
                                }
                              ]
                            }
                          ]
                        },
                        {
                          "name": "第五章 定积分",
                          "children": [
                            {
                              "name": "5.1 定积分的概念与性质",
                              "children": [
                                {
                                  "name": "定积分的定义",
                                  "masteryLevel": 4
                                },
                                {
                                  "name": "定积分的几何意义",
                                  "masteryLevel": 4
                                },
                                {
                                  "name": "定积分的基本性质",
                                  "masteryLevel": 1
                                }
                              ]
                            },
                            {
                              "name": "5.2 微积分基本公式",
                              "children": [
                                {
                                  "name": "积分上限函数",
                                  "masteryLevel": 4
                                },
                                {
                                  "name": "牛顿-莱布尼茨公式",
                                  "masteryLevel": 2
                                }
                              ]
                            },
                            {
                              "name": "5.3 定积分的换元法与分部积分法",
                              "children": [
                                {
                                  "name": "定积分的换元法",
                                  "masteryLevel": 5
                                },
                                {
                                  "name": "定积分的分部积分法",
                                  "masteryLevel": 5
                                }
                              ]
                            },
                            {
                              "name": "5.4 反常积分",
                              "children": [
                                {
                                  "name": "无穷限的反常积分",
                                  "masteryLevel": 4
                                },
                                {
                                  "name": "无界函数的反常积分",
                                  "masteryLevel": 4
                                },
                                {
                                  "name": "Γ函数",
                                  "type": 4
                                }
                              ]
                            }
                          ]
                        },
                        {
                          "name": "第六章 定积分的应用",
                          "children": [
                            {
                              "name": "6.1 定积分的几何应用",
                              "children": [
                                {
                                  "name": "平面图形的面积",
                                  "masteryLevel": 3
                                },
                                {
                                  "name": "体积计算",
                                  "masteryLevel": 3
                                },
                                {
                                  "name": "平面曲线的弧长",
                                  "masteryLevel": 3
                                }
                              ]
                            },
                            {
                              "name": "6.2 定积分的物理应用",
                              "children": [
                                {
                                  "name": "变力做功",
                                  "masteryLevel": 3
                                },
                                {
                                  "name": "液体压力",
                                  "masteryLevel": 3
                                },
                                {
                                  "name": "引力问题",
                                  "masteryLevel": 3
                                }
                              ]
                            }
                          ]
                        },
                        {
                          "name": "第七章 微分方程",
                          "children": [
                            {
                              "name": "7.1 微分方程的基本概念",
                              "children": [
                                {
                                  "name": "微分方程的定义",
                                  "masteryLevel": 4
                                },
                                {
                                  "name": "解、通解与特解",
                                  "masteryLevel": 4
                                },
                                {
                                  "name": "初值问题",
                                  "masteryLevel": 4
                                }
                              ]
                            },
                            {
                              "name": "7.2 一阶微分方程",
                              "children": [
                                {
                                  "name": "可分离变量方程",
                                  "masteryLevel": 5
                                },
                                {
                                  "name": "齐次方程",
                                  "masteryLevel": 5
                                },
                                {
                                  "name": "一阶线性方程",
                                  "masteryLevel": 5
                                }
                              ]
                            },
                            {
                              "name": "7.3 可降阶的高阶微分方程",
                              "children": [
                                {
                                  "name": "y''=f(x)型",
                                  "masteryLevel": 5
                                },
                                {
                                  "name": "y''=f(x,y')型",
                                  "masteryLevel": 5
                                },
                                {
                                  "name": "y''=f(y,y')型",
                                  "masteryLevel": 5
                                }
                              ]
                            },
                            {
                              "name": "7.4 高阶线性微分方程",
                              "children": [
                                {
                                  "name": "线性微分方程的结构",
                                  "masteryLevel": 5
                                },
                                {
                                  "name": "常系数齐次线性方程",
                                  "masteryLevel": 5
                                },
                                {
                                  "name": "常系数非齐次线性方程",
                                  "masteryLevel": 5
                                }
                              ]
                            }
                          ]
                        }
                      ]
                    }
                                """;

            // 解析JSON
            // ObjectMapper mapper = new ObjectMapper();
            // JsonNode rootNode = mapper.readTree(json);

            // System.out.println("知识点树形结构遍历结果:");
            // System.out.println("==========================");

            // 从根节点开始遍历
            // saveKnowledgeTree(rootNode, null, 0);

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
        // 设置层级信息
        knowledgeTree.setLevel(level);

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