package com.kj.tdd.tddTest.service;

import com.kj.tdd.tddTest.domain.Student;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LambdaTest {

    List<Student> stuList;

    @Before
    public void init() {
        Random random = new Random();
        stuList = new ArrayList<Student>() {
            {
                for (int i = 0; i < 100; i++) {
                    add(Student.builder().id(Long.valueOf(i)).name("student" + i).age(random.nextInt(100)).build());
                }
            }
        };
    }

    @Test
    public void testStudent() {
        System.out.println(Thread.currentThread().getName() + "-开始测试lambda表达式");
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + "-函数式接口可直接用lambda表达式生成接口对象");
            }
        };
        new Thread(runnable).start();
        //以上代码可用下面的lambda语法代替，原因是Runnable是函数式接口（只有一个抽象方法）
        new Thread(() -> System.out.println(Thread.currentThread().getName() + "-函数式接口可直接用lambda表达式生成接口对象")).start();

        //打印年龄在50岁以上的学生姓名和年龄，并且按照年龄倒序排序
        List<String> stuNameList = stuList.stream()
                .filter(stu -> stu.getAge() > 50) //过滤
                .sorted(Comparator.comparing(Student::getAge).reversed()) //倒序排序
                .map(Student::getName) //把一种类型流转换为另一种类型流
                .collect(Collectors.toList());
        System.out.println("学生姓名如下：" + stuNameList);

        //stream 可以赋值多次，但是只能对流操作一次，再次操作会报错
        Stream<String> stream = Stream.empty(); //创建空流
        stream = Arrays.stream(new String[]{"1","3","5"});  //通过数组创建流
        stream = Arrays.asList("2","4","6").stream();  //通过列表集合创建流
        stream = Stream.generate(() -> "test").limit(3);  //创建无限流
        stream = Stream.of("1", "2", "3", "4", "5");
        stream.forEach(System.out::println);
        //stream.forEach(System.out::println); 会报错


        /**
         * flapMap：拆解流
         */
        String[] arr1 = {"a", "b", "c", "d"};
        String[] arr2 = {"e", "f", "c", "d"};
        String[] arr3 = {"h", "j", "c", "d"};
        List<String> list1 = Arrays.asList(arr1);
        List<String> list2 = Arrays.asList(arr2);
        List<String> list3 = Arrays.asList(arr3);
        // Stream.of(arr1, arr2, arr3).flatMap(x -> Arrays.stream(x)).forEach(System.out::println);
        Stream.of(arr1, arr2, arr3).forEach(System.out::println); //打印的是每个字符串数组对象
        Stream.of(arr1, arr2, arr3).flatMap(Arrays::stream).forEach(System.out::println);//打印的是每个字符串数组对象里面的元素
        Stream.of(list1, list2, list3).flatMap(List::stream).forEach(System.out::println);//打印的是每个字符串数组对象里面的元素

        /**
         * 排序
         * 1、Comparator.naturalOrder()和 Comparator.naturalOrder()适合String、Integer、Long、Double等包装类型流排序
         * 2、Comparator.comparing适合对一般对象流中的对象属性进行排序
         */
        String[] arr4 = {"abc","aB","bc","abcd"};
        Double[] d = {1.0,2.0,5.0,4.0};
        //Comparator.comparing()适合比较一个元素属性大小，并按升序返回元素，可在后面加上reversed()方法降序
        Arrays.stream(arr4).sorted(Comparator.comparing(String::length).reversed().thenComparing(Comparator.reverseOrder())).forEach(System.out::println); //thenComparing可以再次排序
        //Comparator.reverseOrder()按倒序排序，适合String、Integer、Long、Double等包装类型，不支持int、long、double等基础数据类型
        Arrays.stream(d).sorted(Comparator.reverseOrder()).forEach(System.out::println);
        //Comparator.naturalOrder()按自然顺序排序
        Arrays.stream(d).sorted(Comparator.naturalOrder()).forEach(System.out::println);

        //截取流元素
        Arrays.stream(d).limit(3).skip(4).forEach(System.out::println);  //limit截图前面N个元素，skip跳过前面N个元素，输出 5

        System.out.println("===========");
        //ifPresent如果存在，orElse如果不存在,orElseThrow如果不满足则抛出异常，eg. RuntimeException::new
        Arrays.stream(d).max(Comparator.naturalOrder()).ifPresent(System.out::print); //取最大元素
        Arrays.stream(d).min(Comparator.naturalOrder()).ifPresent(System.out::print);//取最小元素
        //Arrays.stream(d).filter(num -> num > 6).findFirst().orElseThrow(RuntimeException::new);
        Arrays.stream(d).filter(num -> num > 6).findFirst().orElse(0d);

        Optional<Double> doubleOptional = Optional.empty(); //初始化空的可选类型
        doubleOptional = Optional.of(6d);//初始化可选类型

        //收集结果
        Set<Student> studentSet = stuList.stream().collect(Collectors.toSet());
        System.out.println(studentSet);
        Map<Long, Student> studentMap = stuList.stream().collect(Collectors.toMap(Student::getId, Function.identity(), (x, y) -> y));
        studentMap.forEach((x, y) -> System.out.println(x + "->" + y));

        //聚合操作
        Map<Integer, List<Student>> sameAgeStudentMap = stuList.stream().collect(Collectors.groupingBy(Student::getAge));
        sameAgeStudentMap.forEach((x, y) -> System.out.println(x + "->" + y));
        //分组转换为指定类型
        Map<Integer, Set<Student>> sameAgeStudentSetMap = stuList.stream().collect(Collectors.groupingBy(Student::getAge, Collectors.toSet()));
        //分组计数
        Map<Integer, Long> sameAgeStudentCountMap = stuList.stream().collect(Collectors.groupingBy(Student::getAge, Collectors.counting()));
        sameAgeStudentCountMap.forEach((x, y) -> System.out.println(x + "->" + y));
        //分组求和
        Map<Integer, Long> sameAgeStudentIdSumMap = stuList.stream().collect(Collectors.groupingBy(Student::getAge, Collectors.summingLong(Student::getId)));
        sameAgeStudentIdSumMap.forEach((x, y) -> System.out.println(x + "->" + y));
        //分组求最大值
        Map<Integer, Optional<Student>> maxAgeStudentIdSumMap = stuList.stream().collect(Collectors.groupingBy(Student::getAge, Collectors.maxBy(Comparator.comparing(Student::getId))));
        maxAgeStudentIdSumMap.forEach((x, y) -> System.out.println(x + "->" + y));
        //分组获取集合后，抽取集合内的对象属性，并再次收集属性结果集合
        Map<Integer, Set<String>> sameAgeNameSetMap = stuList.stream().collect(Collectors.groupingBy(Student::getAge, Collectors.mapping(Student::getName, Collectors.toSet())));
        sameAgeNameSetMap.forEach((x, y) -> System.out.println(x + "->" + y));

        //parallelStream()并发流处理，非线程安全，处理外部共享变量要特别注意，应当使用.collect(Collectors.toList())收集流
        //=====================不安全写法=================
       Student student = Student.builder().id(1L).name("kejian").sex("男").age(1).build();
       List<Integer> integerList = new ArrayList<>();
        for (int i = 1; i <= 1000; i++) {
            integerList.add(i);
        }
        System.out.println("=================");
        List<Student> students = new ArrayList<>();
        integerList.parallelStream().forEach(i -> {
            student.setAge(i);
            students.add(student);
        });
        //System.out.println(students);
        System.out.println(students.size());

        //=====================线程安全写法=================
        List<Student> studentList = integerList.parallelStream().map(i -> {
            student.setAge(i);
            return student;
        }).collect(Collectors.toList());
        System.out.println(studentList.size());
    }
}
