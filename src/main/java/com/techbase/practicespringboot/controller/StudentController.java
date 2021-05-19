package com.techbase.practicespringboot.controller;

import com.techbase.practicespringboot.entity.StudentEntity;
import com.techbase.practicespringboot.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@Controller
public class StudentController {
    @Autowired
    private StudentService studentService;

    @GetMapping("/")
    public String viewHomePage(Model model) {
        return index(1, model);
    }

    @GetMapping("/student/page/{pageNo}")
    public String index(@PathVariable(value = "pageNo") int pageNo, Model model) {

//        @RequestParam(name = "page", required = false, defaultValue = "0") Integer pageNo
        int pageSize = 5;
        Page<StudentEntity> page = studentService.findAllStudent(pageNo, pageSize);
        List<StudentEntity> listEmployees = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("student", listEmployees);
        return "list";
    }

    @GetMapping("/download/file")
    public ResponseEntity<Resource> downloadFile() throws IOException {
        InputStreamResource resource = new InputStreamResource(studentService.writeDataToCsv());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=customers.tsv")
                .contentType(MediaType.TEXT_PLAIN)
                .body(resource);
    }

    @GetMapping("/student/create")
    public String create(Model model) {
        model.addAttribute("student", new StudentEntity());
        return "form";
    }

    @GetMapping("/student/{id}/edit")
    public String edit(@PathVariable int id, Model model) {
        model.addAttribute("student", studentService.findOneStudent(id));
        return "form";
    }

    @PostMapping("/student/save")
    public String save(@Validated StudentEntity studentEntity, BindingResult result, RedirectAttributes redirect) {
        if (result.hasErrors()) {
            return "form";
        }
        studentService.saveStudent(studentEntity);
        redirect.addFlashAttribute("success", "Saved student successfully!");
        return "redirect:/student/page/1";
    }

    @GetMapping("/student/{id}/delete")
    public String delete(@PathVariable int id, RedirectAttributes redirect) {
        StudentEntity student = studentService.findOneStudent(id);
        studentService.deleteStudent(student);
        redirect.addFlashAttribute("success", "Deleted student successfully!");
        return "redirect:/student/page/1";
    }

    @GetMapping("/student/search")
    public String search(@RequestParam("s") String s, Model model) {
        if (s.equals("")) {
            return "redirect:/student/page/1";
        }

        model.addAttribute("student", studentService.searchStudent(s));
        return "list";
    }
}
