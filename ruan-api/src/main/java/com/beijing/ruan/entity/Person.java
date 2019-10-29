package com.beijing.ruan.entity;

import java.io.Serializable;

public class Person implements Serializable {
    private static final long serialVersionUID = -5439292105908010878L;

    /**
     * 主键id
     */
    private String id;

    /**
     * 人员姓名
     */
    private String name;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 是否被删除
     */
    private Boolean deleted;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
}
