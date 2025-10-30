package com.example.simpleDemo.utils;

import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Component
public class UtilCustom {

  private static final Map<String, Integer> TAG_TO_MASTERY = new HashMap<>();
  static {
    TAG_TO_MASTERY.put("记忆", 0);
    TAG_TO_MASTERY.put("理解", 1);
    TAG_TO_MASTERY.put("应用", 2);
    TAG_TO_MASTERY.put("分析", 3);
    TAG_TO_MASTERY.put("评价", 4);
    TAG_TO_MASTERY.put("创造", 5);
  }

  // 将方法改为public static以便可以直接调用
  public ObjectNode JsonTransformer(String inputJson, String RootName) throws Exception {

    ObjectMapper mapper = new ObjectMapper();
    JsonNode rootNode = mapper.readTree(inputJson);

    ObjectNode outputRoot = mapper.createObjectNode();
    outputRoot.put("name", RootName); // 根据内容，这里应该是物理而不是高等数学

    ArrayNode chapters = mapper.createArrayNode();
    outputRoot.set("children", chapters);

    // 遍历每个章节
    rootNode.fields().forEachRemaining(chapterEntry -> {
      String chapterName = chapterEntry.getKey();
      JsonNode chapterNode = chapterEntry.getValue();

      ObjectNode chapterObj = mapper.createObjectNode();
      chapterObj.put("name", chapterName);
      ArrayNode sections = mapper.createArrayNode();
      chapterObj.set("children", sections);

      // 遍历每个小节
      chapterNode.fields().forEachRemaining(sectionEntry -> {
        String sectionName = sectionEntry.getKey();
        JsonNode sectionNode = sectionEntry.getValue();

        ObjectNode sectionObj = mapper.createObjectNode();
        sectionObj.put("name", sectionName);
        ArrayNode topics = mapper.createArrayNode();
        sectionObj.set("children", topics);

        // 获取大纲数组
        JsonNode outlineArray = sectionNode.get("大纲");
        if (outlineArray != null && outlineArray.isArray()) {
          for (JsonNode item : outlineArray) {
            ObjectNode topicObj = mapper.createObjectNode();
            String content = item.get("content").asText();
            String tag = item.get("tag").asText();

            topicObj.put("name", content);
            topicObj.put("masteryLevel", TAG_TO_MASTERY.getOrDefault(tag, 0));

            topics.add(topicObj);
          }
        }

        sections.add(sectionObj);
      });

      chapters.add(chapterObj);
    });
    return outputRoot;
  }

  // 辅助方法：从总分列标题中提取 fullScore
  public Integer extractFullScoreFromHeader(Cell cell) {
    if (cell == null)
      return null;
    String headerText = getCellValueAsString(cell);
    if (headerText == null || headerText.isEmpty())
      return null;

    // 支持多种格式：总分（40）、总分(40)、总分: 40、总分=40 等
    // 使用 [（(] 匹配中文或英文左括号，[）)] 匹配中文或英文右括号
    java.util.regex.Pattern pattern = java.util.regex.Pattern
        .compile("\\b总分\\s*[（(]?([\\d]+)[）)]?|\\s*:\\s*(\\d+)|\\s*=\\s*(\\d+)");
    java.util.regex.Matcher matcher = pattern.matcher(headerText);
    if (matcher.find()) {
      // 获取第一个数字组
      for (int i = 1; i <= matcher.groupCount(); i++) {
        String group = matcher.group(i);
        if (group != null && !group.isEmpty()) {
          return Integer.parseInt(group);
        }
      }
    }
    // 如果未匹配到，返回默认值 100
    return 100; // 默认满分
  }

  // 辅助方法：安全地获取单元格的字符串值
  public String getCellValueAsString(Cell cell) {
    if (cell == null) {
      return "";
    }

    switch (cell.getCellType()) {
      case STRING:
        return cell.getStringCellValue();
      case NUMERIC:
        if (DateUtil.isCellDateFormatted(cell)) {
          return cell.getDateCellValue().toString();
        } else {
          // 避免科学计数法显示
          return String.valueOf((long) cell.getNumericCellValue());
        }
      case BOOLEAN:
        return String.valueOf(cell.getBooleanCellValue());
      case FORMULA:
        switch (cell.getCachedFormulaResultType()) {
          case NUMERIC:
            return String.valueOf((long) cell.getNumericCellValue());
          case STRING:
            return cell.getStringCellValue();
          default:
            return "";
        }
      default:
        return "";
    }
  }
}