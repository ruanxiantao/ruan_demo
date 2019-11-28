package com.beijing.ruan.dao;

import com.beijing.ruan.entity.Blog;
import com.beijing.ruan.entity.Person;

public interface BlogDao {
    Person getPersonById(String id);

    /**
     * 插入人员
     * @param person
     * @throws Exception
     */
    void insertPerson(Person person) throws Exception;

    void insertBlog(Blog blog) throws Exception;
}
