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

/**
 *@Author 阮先涛
 *@Date 2020/3/7 16:46
 */
@Service("blogService")
public class BlogServiceImpl implements BlogService {
    @Autowired
    private BlogDao blogDao;

    /**
     * 调用文件服务
     */
    @Autowired
    private FileInfoService fileInfoService;
    /**
     * 添加博客
     * @param blog
     * @throws Exception
     */
    @Override
    public void insertBlog(Blog blog) throws Exception {
        // 主键id使用uuid
        blog.setId(getUUID());
        if(blog != null){
            /**
             * 获取博客中的文件集合
             * 循环插入表中
             */
            List<FileInfo> fileList = blog.getFileList();
            if(fileList != null && fileList.size() > 0){
                fileInfoService.insertFileInfoList(fileList);
            }
            blogDao.insertBlog(blog);
        }

    }
    private String getUUID(){
        long uuid;
        do{
            uuid = UUID.randomUUID().getMostSignificantBits();
        }while (uuid < 0L);
        return uuid + "";
    }

}
