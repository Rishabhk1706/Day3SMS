package com.example.day3sms.service;

import com.example.day3sms.dto.StudentRequestDto;
import com.example.day3sms.dto.StudentResponseDto;
import com.example.day3sms.exception.StudentNotFoundException;
import com.example.day3sms.model.StudentModel;
import com.example.day3sms.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class StudentService {
    private final StudentRepository repository;

    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

//    public StudentModel addStudent(StudentModel student){
//        return repository.save(student);
//    }

    public StudentResponseDto addStudent(StudentRequestDto dto){
        StudentModel student = new StudentModel();
        student.setName(dto.getName());
        student.setAge(dto.getAge());
        student.setEmail(dto.getEmail());
        StudentModel saved = repository.save(student);

        return new StudentResponseDto(
                saved.getId(),
                saved.getName(),
                saved.getAge(),
                saved.getEmail()
        );
    }

//    public List<StudentModel> getStudents(){
//        return repository.findAll();
//    }

    public List<StudentResponseDto> getAllStudents(){
        return repository.findAll()
            .stream()
            .map(s -> new StudentResponseDto(
                    s.getId(),
                    s.getName(),
                    s.getAge(),
                    s.getEmail()
            )).toList();
    }

    public void deleteStudent(String id) {
        repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        repository.deleteById(id);
    }

//    public StudentModel getStudent(String id){
//        return repository.findById(id).orElse(null);
//    }

    public StudentResponseDto getStudent(String id){
        StudentModel student = repository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Student not found"));
        return new StudentResponseDto(
                student.getId(),
                student.getName(),
                student.getAge(),
                student.getEmail()
        );
    }

//    public StudentModel updateStudent(String id, StudentModel student){
//        StudentModel existingStudent = repository.findById(id).orElseThrow(() -> new RuntimeException("Student Not Found"));
//        existingStudent.setName(student.getName());
//        existingStudent.setAge(student.getAge());
//        existingStudent.setEmail(student.getEmail());
//        return repository.save(existingStudent);
//    }

    public StudentResponseDto updateStudent(String id, StudentModel student){
        StudentModel existingStudent = repository.findById(id).orElseThrow(() -> new RuntimeException("Student Not Found"));
        existingStudent.setName(student.getName());
        existingStudent.setAge(student.getAge());
        existingStudent.setEmail(student.getEmail());

        StudentModel updatedStudent = repository.save(existingStudent);
        return new StudentResponseDto(
                updatedStudent.getId(),
                updatedStudent.getName(),
                updatedStudent.getAge(),
                updatedStudent.getEmail()
        );
    }

    public StudentResponseDto patchStudent(String id, Map<String, Object> updates) {

        StudentModel student = repository.findById(id)
                .orElseThrow(() ->
                        new StudentNotFoundException("Student not found"));

        if (updates.containsKey("name")) {
            student.setName((String) updates.get("name"));
        }

        if (updates.containsKey("age")) {
            Object ageObj = updates.get("age");
            if (ageObj instanceof Number) {
                student.setAge(((Number) ageObj).intValue());
            }
        }

        if (updates.containsKey("email")) {
            student.setEmail((String) updates.get("email"));
        }

        StudentModel updated = repository.save(student);

        return new StudentResponseDto(
                updated.getId(),
                updated.getName(),
                updated.getAge(),
                updated.getEmail()
        );
    }
}
