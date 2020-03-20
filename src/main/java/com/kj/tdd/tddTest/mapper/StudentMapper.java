package com.kj.tdd.tddTest.mapper;

import com.kj.tdd.tddTest.domain.Student;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface StudentMapper {

    @Insert("insert into student(name, sex, age) values (#{name}, #{sex}, #{age})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int addStudent(Student student);

    @Select("select * from student limit #{pageSize} offset #{index}")
    @ResultMap("studentMap")
    /*
    这里的映射有问题，sex和age拿不到值，不知道为什么！！！
    @Results(id="studentMaps", value={
            @Result(column="id", property="id", jdbcType= JdbcType.BIGINT, id=true),
            @Result(column="name", property="name", jdbcType= JdbcType.VARCHAR),
            @Result(column="sex ", property="sex", jdbcType= JdbcType.VARCHAR),
            @Result(column="age ", property="age", jdbcType= JdbcType.INTEGER),
            //@Result(column="head_img", property="headImg", jdbcType=JdbcType.BLOB),
            //@Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP)
    })*/
    List<Student> listStudent(@Param("index") Long index, @Param("pageSize") Long pageSize);

    @Select({"select * from student where id = #{id}"})
    @ResultMap("studentMap")
    Student getStudent(@Param("id") Long id);

    @Update("update student set age = #{age}, name = #{name}, sex = #{sex} where id = #{id}")
    int updateStudent(Student student);

    @Delete("delete from student where id = #{id}")
    int deleteStudent(@Param("id") Long id);



}
