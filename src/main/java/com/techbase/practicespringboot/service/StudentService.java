package com.techbase.practicespringboot.service;

import com.techbase.practicespringboot.entity.StudentEntity;
import org.springframework.data.domain.Page;

import java.io.ByteArrayInputStream;
import java.util.List;

public interface StudentService {

    List<StudentEntity> getListStudent();

    Page<StudentEntity> findAllStudent(int pageNo, int pageSize);

    List<StudentEntity> searchStudent(String name);

    StudentEntity findOneStudent(long id);

    void saveStudent(StudentEntity studentEntity);

    void deleteStudent(StudentEntity studentEntity);

    ByteArrayInputStream writeDataToCsv();
}
