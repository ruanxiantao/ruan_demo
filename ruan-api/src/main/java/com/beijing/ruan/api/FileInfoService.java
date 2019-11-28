package com.beijing.ruan.api;

import com.beijing.ruan.entity.FileInfo;

import java.util.List;

public interface FileInfoService {
    void insertFileInfoList(List<FileInfo> list) throws Exception;
}
