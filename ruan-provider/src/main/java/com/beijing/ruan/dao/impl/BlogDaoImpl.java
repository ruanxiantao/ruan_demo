package com.beijing.ruan.dao.impl;

import com.beijing.ruan.dao.BlogDao;
import com.beijing.ruan.entity.Blog;
import com.beijing.ruan.entity.Person;
import com.beijing.ruan.utils.BaseDaoImpl;
import org.springframework.stereotype.Repository;

@Repository
public class BlogDaoImpl extends BaseDaoImpl implements BlogDao {
    @Override
    public Person getPersonById(String id) {
        Person person = new Person();
        person.setId("123456");
        person.setName("张三");
        person.setAge(18);
        person.setDeleted(false);
        return person;
    }

    @Override
    public void insertPerson(Person person) throws Exception {
        this.insert("Mapper.Person.insert",person);
    }

    @Override
    public void insertBlog(Blog blog) throws Exception {
        this.insert("Mapper.Blog.insert",blog);
    }
}
