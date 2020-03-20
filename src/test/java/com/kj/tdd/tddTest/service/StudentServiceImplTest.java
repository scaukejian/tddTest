package com.kj.tdd.tddTest.service;

import com.alibaba.fastjson.JSON;
import com.kj.tdd.tddTest.domain.Student;
import com.kj.tdd.tddTest.mapper.StudentMapper;
import com.kj.tdd.tddTest.utils.RedisUtil;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;
import springfox.documentation.spring.web.json.Json;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

@SpringBootTest
@RunWith(SpringRunner.class)
public class StudentServiceImplTest {

    @InjectMocks
    //@Autowired
    private StudentServiceImpl studentService = new StudentServiceImpl();
    @Mock
    private StudentMapper studentMapper;
    @Autowired
    //@Mock
    RedisUtil redisUtil;
    @Autowired
    //@InjectMocks
    RedisTemplate redisTemplate;
    //@Mock
    //ValueOperations valueOperations; //redisTemplate需要依赖ValueOperations，如果不启用spring注入RedisTemplate，则需要使用Mock注解注入

    @Before
    public void setUp1() throws Exception {
        System.out.println("begin");
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown1() throws Exception {
        System.out.println("end");
    }

    /**
     * 测试使用redis
     * 使用redis作为缓存的步骤：
     * 1、先查缓存有没有值
     * 2、有值返回，没值查数据库
     * 3、更新缓存，并且设置过期时间
     */
    @Test
    public void testRedis() {
        String a = "test redis1";
        String b = "test redis2";
        Student student1 = Student.builder().id(1L).name(a).build();
        Student student2 = Student.builder().id(1L).name(b).build();
        List<String> list1 = Arrays.asList(a, b); //fastjson不支持这种定义数组转换
        List<Student> studentList = new ArrayList<>();
        studentList.add(student1);
        studentList.add(student2);
        Map<String, Student> map = new HashMap<>();
        map.put("student1", student1);
        map.put("student2", student2);
        redisUtil.setEx("a1", a, 100, TimeUnit.SECONDS);
        redisUtil.setEx("student1", JSON.toJSONString(student1), 100, TimeUnit.SECONDS);
        redisUtil.setEx("list1", JSON.toJSONString(studentList), 100, TimeUnit.SECONDS);
        redisUtil.setEx("map", JSON.toJSONString(map), 100, TimeUnit.SECONDS);

        String a1 = redisUtil.get("a1");
        Student student_result = JSON.parseObject(redisUtil.get("student1"), Student.class);
        List<Student> list_result = JSON.parseArray(redisUtil.get("list1"), Student.class);
        Map<String, Student> map_result = JSON.parseObject(redisUtil.get("map"), Map.class);
        Map<String, Student> map_result2 = JSON.parseObject(redisUtil.get("map3"), Map.class);
        Map<String, Student> map_result3 = JSON.parseObject(redisUtil.get("map4"), Map.class);
        System.out.println("a1:" + a1);
        System.out.println("student_result:" + student_result);
        System.out.println("list_result:" + list_result);
        System.out.println("map_result:" + map_result);
        System.out.println("map_result2:" + map_result2);
        System.out.println("map_result3:" + map_result3);

        //如果不存在，则设置值，并返回是否设置成功
        redisTemplate.opsForValue().setIfAbsent("b", b);
        redisTemplate.opsForValue().setIfAbsent("student2", JSON.toJSONString(student2));
        redisTemplate.opsForValue().setIfAbsent("studentList", JSON.toJSONString(studentList));
        redisTemplate.opsForValue().setIfAbsent("studentMap", JSON.toJSONString(map));
        //haha1已存在，所以返回false，不会更新值
        boolean flag1 = redisTemplate.opsForValue().setIfAbsent("haha1", JSON.toJSONString(map));
        //haha2不存在，所以返回true，会新建一个haha2键值对
        boolean flag2 = redisTemplate.opsForValue().setIfAbsent("haha2", JSON.toJSONString(map));
        //如果key存在，则设置key的过期时间
        redisTemplate.opsForValue().setIfPresent("b", b, 100, TimeUnit.SECONDS);
        redisTemplate.opsForValue().setIfPresent("student2", JSON.toJSONString(student2), 100, TimeUnit.SECONDS);
        redisTemplate.opsForValue().setIfPresent("studentList", JSON.toJSONString(studentList), 100, TimeUnit.SECONDS);
        //studentMap已存在，所以返回true，会更新值
        boolean flag3 = redisTemplate.opsForValue().setIfPresent("studentMap", JSON.toJSONString(map), 100, TimeUnit.SECONDS);
        //studentMap2不存在，所以返回false，不会创建studentMap2键值对
        boolean flag4 = redisTemplate.opsForValue().setIfPresent("studentMap2", JSON.toJSONString(map), 100, TimeUnit.SECONDS);

        System.out.println("b:" + redisTemplate.opsForValue().get("b"));
        System.out.println("student2:" + JSON.parseObject((String)redisTemplate.opsForValue().get("student2"), Student.class));
        System.out.println("studentList:" + JSON.parseArray((String)redisTemplate.opsForValue().get("studentList"), Student.class));
        System.out.println("studentMap:" + JSON.parseObject((String)redisTemplate.opsForValue().get("studentMap"), Map.class));

        System.out.println("flag1:" + flag1);
        System.out.println("flag2:" + flag2);
        System.out.println("flag3:" + flag3);
        System.out.println("flag4:" + flag4);
    }

    @Test
    public void testPrintStudentInfo() {
        System.out.println("ok");
        Student student = Student.builder().name("柯坚").sex("男").age(30).build();
        Student student2 = Mockito.mock(Student.class);
        //Mockito.doReturn(20).when(student2).getAge();
        Mockito.when(student2.getAge()).thenReturn(20);
        studentService.printStudentInfo(student);
        studentService.printStudentInfo(student2);
        Assert.assertEquals(student.getAge(),30);
        Assert.assertEquals(student2.getAge(),20);
    }

    @Test
    public void testAddStudent() {
        Student student = Student.builder().name("柯坚").sex("男").age(30).build();
        System.out.println(studentService.addStudent(student));
        //Assert.assertNotEquals(0L, (long)Optional.ofNullable(student.getId()).orElse(0L));
    }

    @Test
    public void testUpdateStudent() {
        Student student = Student.builder().id(1L).name("柯坚").sex("男").age(1).build();
        System.out.println(studentService.updateStudent(student));
        Assert.assertEquals(1, (long)Optional.ofNullable(student.getAge()).orElse(1));
    }

    @Test
    public void testDeleteStudent() {
        studentService.deleteStudent(8L);
        //Assert.assertEquals(1, (long)Optional.ofNullable(student.getAge()).orElse(1));
    }

    @Test
    public void testGetStudent() {
        Student student = Student.builder().id(1L).name("柯坚").sex("男").age(1).build();
        Mockito.when(studentService.getStudent(9L)).thenReturn(student);
        Assert.assertNotNull(studentService.getStudent(9L));
    }

    @Test
    public void testListStudent() {
        List<Student> students = studentService.listStudent(0L, 10L);
        Assert.assertNotNull(students);
    }
}