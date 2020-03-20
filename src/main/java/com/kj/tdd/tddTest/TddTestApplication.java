package com.kj.tdd.tddTest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 1、spring boot
 * 2、mybatis
 * 3、junit
 * 4、mockito
 * 5、mysql
 * 6、redis
 * 7、aop
 * 8、swagger
 * 9、filter：doFilter方法作用在所有Controller执行前，所以对Controller的切面、异常处理会不执行
 * 10、exception：@RestControllerAdvice、@ExceptionHandler
 * 11、euraka:服务的注册和发现 @EnableEurekaServer @EnableDiscoveryClient
 * 12、feign:在SpringBoot启动类加上@EnableFeignClients,创建feign远程调用接口和回调函数
 *
 * 13、lambda
 * 14、task
 * 15、logback
 *
 * 16、shiro
 * 17、消息队列
 * 18、多线程作业
 * 19、分布式定时器、锁、事务、ID
 * 20、各个中心的调用关系
 */
@EnableSwagger2
@SpringBootApplication(scanBasePackages = {"com.kj.tdd.tddTest"})
@EnableDiscoveryClient
@EnableFeignClients
public class TddTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(TddTestApplication.class, args);
	}

}
