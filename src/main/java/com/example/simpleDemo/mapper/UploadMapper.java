package com.example.simpleDemo.mapper;

import com.example.simpleDemo.entity.SubjectOutline;
import com.example.simpleDemo.entity.SubjectQuestionFile;
import com.example.simpleDemo.entity.SubjectQuestion;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface UploadMapper {

    /**
     * 插入一个SubjectOutline数据到数据库
     * 
     * @param subjectOutline 要插入的数据对象
     * @return 插入成功的记录数
     */
    int insertSubjectOutline(SubjectOutline subjectOutline);

    /**
     * 根据ID更新SubjectOutline的uploadStatus
     * 
     * @param subjectOutline 包含ID和新uploadStatus的对象
     * @return 更新成功的记录数
     */
    int updateUploadStatusById(SubjectOutline subjectOutline);

    /**
     * 根据科目ID查找所有关联的大纲ID
     * 
     * @param subjectId 科目ID
     * @return 大纲ID列表
     */
    List<Long> selectOutlineIdsBySubjectId(@Param("subjectId") Long subjectId);

    /**
     * 根据科目ID删除数据
     * 
     * @param subjectId 科目ID
     * @return 删除的记录数
     */
    int deleteBySubjectId(@Param("subjectId") Long subjectId);

    /**
     * 插入一个SubjectQuestionFile数据到数据库
     * 
     * @param subjectQuestionFile 要插入的数据对象
     * @return 插入成功的记录数
     */
    int insertSubjectQuestionFile(SubjectQuestionFile subjectQuestionFile);

    /**
     * 根据ID更新SubjectQuestionFile的uploadStatus
     * 
     * @param SubjectQuestionFile 包含ID和新uploadStatus的对象
     * @return 更新成功的记录数
     */
    int updateSubjectQuestionUploadStatusById(SubjectQuestionFile subjectQuestionFile);

    /**
     * 插入一个SubjectQuestion数据到数据库
     * 
     * @param subjectQuestion 要插入的数据对象
     * @return 插入成功的记录数
     */
    int insertSubjectQuestion(SubjectQuestion subjectQuestion);
}