package com.techbase.practicespringboot.service;

import com.techbase.practicespringboot.entity.StudentEntity;
import com.techbase.practicespringboot.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImp implements StudentService {
    @Autowired
    private StudentRepository studentRepository;

    @Override
    public Page<StudentEntity> findAllStudent(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return this.studentRepository.findAll(pageable);
    }

    @Override
    public List<StudentEntity> searchStudent(String name) {
        return studentRepository.findByNameContaining(name);
    }

    @Override
    public StudentEntity findOneStudent(long id) {
        return studentRepository.getOne(id);
    }

    @Override
    public void saveStudent(StudentEntity studentEntity) {
        studentRepository.save(studentEntity);
    }

    @Override
    public void deleteStudent(StudentEntity studentEntity) {
        studentRepository.delete(studentEntity);
    }
}
