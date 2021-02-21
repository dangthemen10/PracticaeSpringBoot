package com.techbase.practicespringboot.controller;

import com.techbase.practicespringboot.entity.StudentEntity;
import com.techbase.practicespringboot.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class StudentController {
    @Autowired
    private StudentService studentService;

    @GetMapping("/student")
    public String index(Model model) {
        model.addAttribute("student", studentService.findAllStudent());
        return "list";
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
        return "redirect:/student";
    }

    @GetMapping("/student/{id}/delete")
    public String delete(@PathVariable int id, RedirectAttributes redirect) {
        StudentEntity student = studentService.findOneStudent(id);
        studentService.deleteStudent(student);
        redirect.addFlashAttribute("success", "Deleted student successfully!");
        return "redirect:/student";
    }

    @GetMapping("/student/search")
    public String search(@RequestParam("s") String s, Model model) {
        if (s.equals("")) {
            return "redirect:/student";
        }

        model.addAttribute("student", studentService.searchStudent(s));
        return "list";
    }
}
