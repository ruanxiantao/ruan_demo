package com.beijing.ruan.service.impl;

import com.beijing.ruan.api.PersonService;
import com.beijing.ruan.dao.PersonDao;
import com.beijing.ruan.entity.Person;
import com.beijing.ruan.mapper.PersonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("personService")
public class PersonServiceImpl implements PersonService {
    @Autowired
    private PersonDao personDao;
    @Autowired
    private PersonMapper personMapper;


    @Override
    public Person getPersonById(String id) {
        return personDao.getPersonById(id);
    }

    @Override
    public String insertPerson() {
        Person person = new Person();
        person.setId("aaa");
        person.setName("bbb");
        person.setAge(19);
        person.setDeleted(false);

        personMapper.insertPerson(person);
        return "success";
    }

}
