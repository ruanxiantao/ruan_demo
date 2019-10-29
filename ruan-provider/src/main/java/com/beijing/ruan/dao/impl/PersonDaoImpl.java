package com.beijing.ruan.dao.impl;

import com.beijing.ruan.dao.PersonDao;
import com.beijing.ruan.entity.Person;
import org.springframework.stereotype.Repository;

@Repository
public class PersonDaoImpl implements PersonDao {
    @Override
    public Person getPersonById(String id) {
        Person person = new Person();
        person.setId("123456");
        person.setName("张三");
        person.setAge(18);
        person.setDeleted(false);
        return person;
    }
}
