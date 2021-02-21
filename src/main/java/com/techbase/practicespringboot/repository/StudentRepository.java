package com.techbase.practicespringboot.repository;

import com.techbase.practicespringboot.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
public interface StudentRepository extends JpaRepository<StudentEntity, Long> {
    List<StudentEntity> findByNameContaining(String name);
}