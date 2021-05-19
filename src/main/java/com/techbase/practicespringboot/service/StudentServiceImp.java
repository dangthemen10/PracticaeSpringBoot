package com.techbase.practicespringboot.service;

import com.techbase.practicespringboot.entity.StudentEntity;
import com.techbase.practicespringboot.repository.StudentRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

@Service
public class StudentServiceImp implements StudentService {

    private static final String[] HEADERS = {"Id", "Name", "Age", "Gender"};
    private static final CSVFormat FORMAT = CSVFormat.EXCEL.withHeader(HEADERS);


    @Autowired
    private StudentRepository studentRepository;

    @Override
    public Page<StudentEntity> findAllStudent(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return this.studentRepository.findAll(pageable);
    }

    @Override
    public List<StudentEntity> getListStudent(){
        return studentRepository.findAll();
    }

    @Override
    public ByteArrayInputStream writeDataToCsv(){
        List<StudentEntity> listStudents = studentRepository.findAll();
        try (ByteArrayOutputStream stream = new ByteArrayOutputStream();
             CSVPrinter printer = new CSVPrinter(new PrintWriter(stream), FORMAT)) {
            for (StudentEntity studentEntity : listStudents) {
                List<String> data = Arrays.asList(
                        String.valueOf(studentEntity.getId()),
                        studentEntity.getName(),
                        String.valueOf(studentEntity.getAge()),
                        studentEntity.getGender());
                printer.printRecord(data);
            }
            printer.flush();
            return new ByteArrayInputStream(stream.toByteArray());
        } catch (final IOException e) {
            throw new RuntimeException("Csv writing error: " + e.getMessage());
        }
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
