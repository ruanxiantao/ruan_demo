package com.beijing.ruan.service.impl;

import com.beijing.ruan.api.FileInfoService;
import com.beijing.ruan.dao.FileInfoDao;
import com.beijing.ruan.entity.FileInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service("fileInfoService")
public class FileInfoServiceImpl implements FileInfoService {

    @Autowired
    private FileInfoDao fileInfoDao;
    @Override
    public void insertFileInfoList(List<FileInfo> list) throws Exception {
        fileInfoDao.insertFileInfoList(list);
    }
}
