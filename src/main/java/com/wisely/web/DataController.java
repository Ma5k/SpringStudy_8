package com.wisely.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wisely.dao.PersonRepository;
import com.wisely.domain.Person;

@RestController
public class DataController {
	//1、Spring Data JPA已自动为你注册bean，所以可自动注入
	@Autowired
	PersonRepository personRepository;
	/**
	 * 保存
	 * save支持批量保存：<S extends T>Iterable<S> save (Iterable<S> entities);
	 * 
	 * 删除
	 * 支持使用id删除对象、批量删除以及删除全部：
	 * void delete(Id id);
	 * void delete(T entity);
	 * void delete(Iterable<? extends T> entities);
	 * void deleteAll();
	 */
	
	
	/**
	 * 控制器中接受一个Person对象，当Person的name有值时，会自动对name进行like查询；
	 * 当age有值时会进行等于查询；当Person中有多个值不为空的时候，会自动构造多个查询条件；
	 * 当Person所有值为空的时候，默认查询出所有记录。
	 * 此处需要特别指出的是，在实体类中定义的数据类型要有包装类型（Long、Integer），而不能使用原始数据类型（long、int）
	 * 因为在SpringMVC中，使用原始数据类型会自动初始化为0，而不是空，会导致构造条件失败。
	 * @param person
	 * @return
	 */
	@RequestMapping("/auto")
	public Page<Person> AutoCloseable(Person person){
		Page<Person> pagePeople = personRepository.findByAuto(person, PageRequest.of(0, 10));
		return pagePeople;
	}
	
	@RequestMapping("/save")
	public Person save(String name, String address, Integer age) {
		Person person = personRepository.save(new Person(null, name, age, address));
		return person;
	}
	
	/**
	 * 测试findByAddress
	 */
	@RequestMapping("/q1")
	public List<Person> q1(String address) {
		List<Person> people = personRepository.findByAddress(address);
		return people;
	}
	
	/**
	 * 测试findByNameAndAddress
	 */
	@RequestMapping("/q2")
	public Person q1(String name, String address) {
		Person people = personRepository.findByNameAndAddress(name, address);
		return people;
	}
	
	/**
	 * 测试withNameAndAddressQuery
	 */
	@RequestMapping("/q3")
	public Person q3(String name, String address) {
		Person person = personRepository.withNameAndAddressQuery(name, address);
		return person;
	}
	
	/**
	 * 测试排序
	 */
	@RequestMapping("/sort")
	public List<Person> sort() {
		List<Person> people = personRepository.findAll(new Sort(Direction.ASC,"age"));
		return people;
	}
	
	/**
	 * 测试分页
	 */
	@RequestMapping("/page")
	public Page<Person> page(Integer page, Integer size) {
		//new PageRequest()已过时，使用PageRequest.of()
		Page<Person> pagePeople = personRepository.findAll(PageRequest.of(page, size));
		return pagePeople;
	}
}
