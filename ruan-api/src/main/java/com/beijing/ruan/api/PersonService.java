package com.beijing.ruan.api;

import com.beijing.ruan.entity.Person;

public interface PersonService {

    Person getPersonById(String id);

    String insertPerson();
}
