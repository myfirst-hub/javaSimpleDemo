package com.example.simpleDemo.utils;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

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
}