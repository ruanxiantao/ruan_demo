package com.beijing.ruan.entity;

import java.io.Serializable;
import java.util.List;

public class Blog implements Serializable {
    private static final long serialVersionUID = -2787896389356183030L;

    private String id;

    private String type;

    private String content;

    private List<FileInfo> fileList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<FileInfo> getFileList() {
        return fileList;
    }

    public void setFileList(List<FileInfo> fileList) {
        this.fileList = fileList;
    }
}
