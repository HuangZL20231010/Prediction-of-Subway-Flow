package com.example.demo;

import com.example.demo.mapper.StudentMapper;
import com.example.demo.pojo.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.boot.test.context.SpringBootTest
public class SpringBootTest {

    @Autowired
    private StudentMapper studentMapper;

    @Test
    public void test()
    {
        Student student = new Student();
        student.setNo("1");
        student.setName("W");


        studentMapper.insert(student);
    }
}
