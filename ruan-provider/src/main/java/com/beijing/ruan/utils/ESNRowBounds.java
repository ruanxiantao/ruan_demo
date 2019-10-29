package com.beijing.ruan.utils;

import org.apache.ibatis.session.RowBounds;

public class ESNRowBounds extends RowBounds {
    public static final int NO_ROW_OFFSET = 0;
    public static final int NO_ROW_LIMIT = 2147483647;
    public static final RowBounds DEFAULT = new RowBounds();
    private int offset;
    private int limit;
    private String orderKey;
    private String orderType;
    private String orderBy;

    @Override
    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    @Override
    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getOrderKey() {
        return orderKey;
    }

    public void setOrderKey(String orderKey) {
        this.orderKey = orderKey;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public ESNRowBounds() {
        this.offset = 0;
        this.limit = 2147483647;
    }

    public ESNRowBounds(int offset, int limit, String orderKey, String orderType, String orderBy) {
        this.offset = offset;
        this.limit = limit;
        this.orderKey = orderKey;
        this.orderType = orderType;
        this.orderBy = orderBy;
    }
}
