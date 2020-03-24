package com.kj.tdd.tddTest.service;

import com.kj.tdd.tddTest.domain.Student;
import com.kj.tdd.tddTest.utils.ExcelAnnotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.SQLOutput;

/**
 * 测试反射
 */
public class ReflectTest {

    public static void main(String[] args) throws Exception{

        //=================测试创建Class类实体和实例化==================
        Student student = new Student();
        Class<?> classObj = student.getClass(); //通过对象.getClass()获取
        Class<?> classObj2 = Student.class; //通过类名.class获取
        Class<?> classObj3 = Class.forName(Student.class.getName()); //通过Class.forName获取
        ClassLoader classLoader = classObj.getClassLoader();//获得类加载器
        Object object = classObj.newInstance(); //实例化对象 Student student = (Student) classObj.newInstance();

        //=================测试构造方法==================
        Constructor constructor = classObj.getDeclaredConstructor(Long.class, String.class); //获取指定参数类型的构造器
        constructor.setAccessible(true);
        Object object2 = constructor.newInstance(1L, "测试学生"); //实例化对象 Student student = (Student) constructor.newInstance(1L, "测试学生");
        System.out.println("student:" +  (Student) object2);

        //=================测试属性==================
        Field[] fields = classObj.getDeclaredFields(); //获取所有属性
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.getName().equals("TAG")) {
                System.out.println("TAG:" + field.get(object));
            } else if (field.getType() == int.class || field.getType() == Integer.class) {
                field.set(object, 10);
            } else if (field.getType() == long.class || field.getType() == Long.class) {
                field.set(object, 2L);
            } else if (field.getType() == String.class) {
                field.set(object, "测试");
            }
            if (field.isAnnotationPresent(ExcelAnnotation.class)) {
                ExcelAnnotation excelAnnotation = field.getDeclaredAnnotation(ExcelAnnotation.class); //获取带有ExcelAnnotation注解的属性
                int columnIndex = excelAnnotation.columnIndex();
                String columnName = excelAnnotation.columnName();
                System.out.println("field getType():" + field.getType() + ", getName():" + field.getName() + ", columnIndex:" + columnIndex +", columnName:" + columnName);
            } else {
                System.out.println("field getClass():" + field.getClass() + ", field getDeclaringClass():" + field.getDeclaringClass());
            }
        }
        System.out.println("student set field :" + object);

        //=================测试方法==================
        Method method = classObj2.getDeclaredMethod("printStudentInfo", String.class);
        method.setAccessible(true);
        method.invoke(object2, "haha");

    }

}
