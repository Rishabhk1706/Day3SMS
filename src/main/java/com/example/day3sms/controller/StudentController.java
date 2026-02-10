package com.example.day3sms.controller;

import com.example.day3sms.dto.StudentRequestDto;
import com.example.day3sms.dto.StudentResponseDto;
import com.example.day3sms.model.StudentModel;
import com.example.day3sms.service.StudentService;
import com.example.day3sms.util.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
public class StudentController{
    private final StudentService service;
    private final JwtUtil jwtUtil;

    public StudentController(StudentService service, JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
        this.service = service;
    }

    private void checkToken(String authHeader){
        if(authHeader==null || !authHeader.startsWith("Bearer ")){
            throw new RuntimeException("Invalid token");
        }

        String token = authHeader.substring(7);

        jwtUtil.validateTokenAndGetEmail(token);
    }

//    @PostMapping("/add-student")
//    public StudentModel addStudent(@Valid @RequestBody StudentModel student){
//        return service.addStudent(student);
//    }

    @PostMapping("/add-student")
    public StudentResponseDto addStudent(@RequestHeader("Authorization") String authHeader, @Valid @RequestBody StudentRequestDto student){
        checkToken(authHeader);
        return service.addStudent(student);
    }

//    @GetMapping("/students")
//    public List<StudentModel> getStudents(){
//        return service.getStudents();
//    }

    @GetMapping("/students")
    public List<StudentResponseDto> getStudents(@RequestHeader(value = "Authorization", required = false) String authHeader){
        checkToken(authHeader);
        return service.getAllStudents();
    }

//    @DeleteMapping("delete/{id}")
//    public void deleteStudent(@PathVariable String id){
//        service.deleteStudent(id);
//    }

    @DeleteMapping("delete/{id}")
    public void deleteStudent(@PathVariable String id){
        service.deleteStudent(id);
    }

//    @GetMapping("/students/{id}")
//    public StudentResponseDto getStudents(@PathVariable String id){
//        return service.getStudent(id);
//    }

//    @PutMapping("/update/{id}")
//    public StudentModel updateStudent(@PathVariable String id, @RequestBody StudentModel student){
//        return service.updateStudent(id, student);
//    }

    @PutMapping("/update/{id}")
    public StudentResponseDto updateStudent(@PathVariable String id, @RequestBody StudentModel student){
        return service.updateStudent(id, student);
    }

    @PatchMapping("/patch/{id}")
    public StudentResponseDto patchStudent(@PathVariable String id, @RequestBody Map<String,Object> updates){
        return service.patchStudent(id, updates);
    }

}
