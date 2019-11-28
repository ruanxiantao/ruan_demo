package com.beijing.ruan.service.impl;

import com.beijing.ruan.api.BlogService;
import com.beijing.ruan.api.FileInfoService;
import com.beijing.ruan.dao.BlogDao;
import com.beijing.ruan.entity.Blog;
import com.beijing.ruan.entity.FileInfo;
import com.beijing.ruan.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service("blogService")
public class BlogServiceImpl implements BlogService {
    @Autowired
    private BlogDao blogDao;

    @Autowired
    private FileInfoService fileInfoService;
    @Override
    public void insertBlog(Blog blog) throws Exception {
        blog.setId(getUUID());
        if(blog != null){
            List<FileInfo> fileList = blog.getFileList();
            if(fileList != null && fileList.size() > 0){
                fileInfoService.insertFileInfoList(fileList);
            }
            blogDao.insertBlog(blog);
        }

    }

    public String getUUID(){
        long uuid;
        do{
            uuid = UUID.randomUUID().getMostSignificantBits();
        }while (uuid < 0L);
        return uuid + "";
    }

}
