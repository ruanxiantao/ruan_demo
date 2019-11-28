package com.beijing.ruan.dao.impl;

import com.beijing.ruan.dao.FileInfoDao;
import com.beijing.ruan.entity.FileInfo;
import com.beijing.ruan.utils.BaseDaoImpl;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Repository("fileInfoDao")
public class FileInfoDaoImpl extends BaseDaoImpl implements FileInfoDao {
    @Override
    public void insertFileInfoList(List<FileInfo> list) throws Exception {
        Map<Object, Object> map = new HashMap<>(2);
        map.put("list", list);
        this.insert("Mapper.FileInfo.insertFileInfoList", map);
    }
}
