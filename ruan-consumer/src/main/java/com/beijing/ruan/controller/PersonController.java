package com.beijing.ruan.controller;

import com.beijing.ruan.api.PersonService;
import com.beijing.ruan.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PersonController {

    @Autowired
    private PersonService personService;

    @ResponseBody
    @RequestMapping("/getPersonById")
    public Person getPersonById(String id){
        Person person = personService.getPersonById(id);
        return person;
    }
    @ResponseBody
    @RequestMapping("/hello")
    public String hello(){
        return personService.insertPerson();
    }
}
