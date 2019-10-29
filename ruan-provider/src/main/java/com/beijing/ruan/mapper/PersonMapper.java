package com.beijing.ruan.mapper;

import com.beijing.ruan.entity.Person;
import tk.mybatis.mapper.common.Mapper;
public interface PersonMapper extends Mapper<Person> {
    void insertPerson(Person person);
}
