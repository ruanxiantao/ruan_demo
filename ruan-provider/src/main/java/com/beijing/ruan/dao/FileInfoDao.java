package com.beijing.ruan.dao;

import com.beijing.ruan.entity.FileInfo;

import java.util.List;

public interface FileInfoDao {

    void insertFileInfoList(List<FileInfo> list) throws Exception;
}
