package com.example.simpleDemo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
// 添加事务支持
import org.springframework.transaction.annotation.Transactional;

import com.example.simpleDemo.entity.Classes;
import com.example.simpleDemo.entity.ClassStudent;
import com.example.simpleDemo.entity.Student;
import com.example.simpleDemo.mapper.ClassesMapper;
import com.example.simpleDemo.utils.PageInfoResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
// 为整个类启用事务管理
@Transactional
public class ClassesService {

    @Autowired
    private ClassesMapper classesMapper;

    @Autowired
    private ClassStudentService classStudentService;

    /**
     * 分页查询班级列表
     * 
     * @param pageNum  页码
     * @param pageSize 每页大小
     * @param name     班级名称（可选）
     * @return 分页结果
     */
    public PageInfoResult<Classes> findClasses(int pageNum, int pageSize, String name) {
        // 开启分页
        PageHelper.startPage(pageNum, pageSize);

        // 查询班级数据
        List<Classes> classes = classesMapper.findClasses(name, null, null);

        // 获取分页信息
        PageInfo<Classes> classesPageInfo = new PageInfo<>(classes);
        return new PageInfoResult<>(classesPageInfo);
    }

    /**
     * 新增班级
     * 
     * @param classes 班级对象
     * @return 是否添加成功
     */
    public int createClasses(Classes classes) {
        // 设置创建和更新时间
        classes.setCreateTime(new java.util.Date());
        classes.setUpdateTime(new java.util.Date());

        // 插入班级信息
        int result = classesMapper.insertClass(classes);

        // 验证是否成功获取到插入后的主键ID
        System.out.println("插入班级后的ID值: " + classes.getId());

        // 确保classes.getId()能获取到插入后的主键值，用于后续的学生关联
        if (classes.getId() == null) {
            throw new RuntimeException("未能获取到插入班级后的主键ID");
        }

        // 遍历学生列表，为每个学生创建班级学生关联
        if (classes.getStudents() != null) {
            for (Student student : classes.getStudents()) {
                // 添加空值检查，确保student和student.getId()不为null
                if (student != null && student.getId() != null) {
                    ClassStudent classStudent = new ClassStudent();
                    classStudent.setClassId(classes.getId());
                    classStudent.setStudentId(student.getId());
                    classStudent.setCreateTime(new java.util.Date());
                    classStudent.setUpdateTime(new java.util.Date());
                    classStudentService.createClassStudent(classStudent);
                }
            }
        }

        return result;
    }

    /**
     * 更新班级
     * 
     * @param classes 班级对象
     * @return 是否更新成功
     */
    public int updateClasses(Classes classes) {
        // 设置更新时间
        classes.setUpdateTime(new java.util.Date());

        // 更新班级信息
        int result = classesMapper.updateClass(classes);

        // 删除原先的班级学生关联
        classStudentService.deleteClassStudentByClassId(classes.getId());

        // 重新创建班级学生关联
        if (classes.getStudents() != null) {
            for (Student student : classes.getStudents()) {
                // 添加空值检查，确保student和student.getId()不为null
                if (student != null && student.getId() != null) {
                    ClassStudent classStudent = new ClassStudent();
                    classStudent.setClassId(classes.getId());
                    classStudent.setStudentId(student.getId());
                    classStudent.setCreateTime(new java.util.Date());
                    classStudent.setUpdateTime(new java.util.Date());
                    classStudentService.createClassStudent(classStudent);
                }
            }
        }

        return result;
    }
}