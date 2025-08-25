package com.example.simpleDemo.service;

import com.example.simpleDemo.entity.SubjectOutline;
import com.example.simpleDemo.mapper.SubjectOutlineMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class SubjectOutlineService {

    @Autowired
    private SubjectOutlineMapper subjectOutlineMapper;

    /**
     * 插入一个SubjectOutline数据到数据库
     * @param subjectOutline 要插入的数据对象
     * @return 插入成功的记录数
     */
    public int insertSubjectOutline(SubjectOutline subjectOutline) {
        // 设置创建时间和更新时间
        Date now = new Date();
        subjectOutline.setCreatedAt(now);
        subjectOutline.setUpdatedAt(now);
        
        // 调用Mapper插入数据
        return subjectOutlineMapper.insertSubjectOutline(subjectOutline);
    }
}