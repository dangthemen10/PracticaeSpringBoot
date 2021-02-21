package com.techbase.practicespringboot.service;

import com.techbase.practicespringboot.entity.StudentEntity;

import java.util.List;

public interface StudentService {
    Iterable<StudentEntity> findAllStudent();

    List<StudentEntity> searchStudent(String name);

    StudentEntity findOneStudent(long id);

    void saveStudent(StudentEntity studentEntity);

    void deleteStudent(StudentEntity studentEntity);
}
