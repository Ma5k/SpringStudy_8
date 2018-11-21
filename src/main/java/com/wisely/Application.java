package com.wisely;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.wisely.dao.PersonRepository;
import com.wisely.support.CustomRepositoryFactoryBean;

/**
 * 在配置类上配置@EnableJpaRepositories，并指定repositoryFactoryBeanClass，让我们自定义的Repository实现起效。
 * 如果我们不需要自定义Repository实现，则在SpringDataJpa里无需添加@EnableJpaRepositories注解，因为@SpringBootApplication包含的
 * @EnabAutoConfiguration注解已经开启了对SpringDataJPA的支持。
 * @author Mask
 *
 */

@SpringBootApplication
@EnableJpaRepositories(repositoryFactoryBeanClass=CustomRepositoryFactoryBean.class)
public class Application {

	@Autowired
	PersonRepository personRepository;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
