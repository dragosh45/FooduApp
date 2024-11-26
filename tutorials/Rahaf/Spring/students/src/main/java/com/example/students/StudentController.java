package com.example.students;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    StudentRepository sRepo;

    @PostMapping
    public Student newStudent(@RequestBody Student student)
    {
       return sRepo.save(student);
    }

    @GetMapping
    public List<Student> findAllStudents()
    {
        return sRepo.findAll();
    }

    @PostMapping("/{id}")
    public Student update(@PathVariable int id, @RequestBody Student student)
    {
        if(sRepo.existsById(id))
        {
            student.setId(id);
            sRepo.deleteById(id);
        }
        return sRepo.save(student);
    }

    @GetMapping("/{id}")
    public Optional<Student> findStudentById(@PathVariable int id)
    {

        //Hibernate.initialize(Student s.toString());
        return sRepo.findById(id);
    }

    @DeleteMapping("/{id}")
    public void DeleteStudent(@PathVariable int id)
    {
        sRepo.deleteById(id);
    }


}
