package com.beijing.ruan.api;

import com.beijing.ruan.entity.Blog;

/**
 *@Author 阮先涛
 *@Date 2020/3/7 16:46
 */
public interface BlogService {

    /**
     * 插入博客
     * @param blog
     * @return Integer
     * @throws Exception
     */
    void insertBlog(Blog blog) throws Exception;
}
