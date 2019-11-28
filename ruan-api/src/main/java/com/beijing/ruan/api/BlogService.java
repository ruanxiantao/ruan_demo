package com.beijing.ruan.api;

import com.beijing.ruan.entity.Blog;
import com.beijing.ruan.entity.Person;

public interface BlogService {
//    Person getPersonById(String id);
//
//    String insertPerson() throws Exception;

    void insertBlog(Blog blog) throws Exception;
}
