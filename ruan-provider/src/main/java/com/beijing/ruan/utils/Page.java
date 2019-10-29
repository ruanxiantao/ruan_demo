package com.beijing.ruan.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Page<T> implements Serializable {
    private static final long serialVersionUID = 5061822903676924846L;
    public int pageMaxSize;
    public int currentPageDataSize;
    private List<T> dataList;
    public int totalSize;
    public int currentPageNumber;
    public int totalPageSize;
    private String _page_div;

    public int getPageMaxSize() {
        return pageMaxSize;
    }

    public void setPageMaxSize(int pageMaxSize) {
        this.pageMaxSize = pageMaxSize;
    }

    public int getCurrentPageDataSize() {
        return currentPageDataSize;
    }

    public void setCurrentPageDataSize(int currentPageDataSize) {
        this.currentPageDataSize = currentPageDataSize;
    }

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    public int getCurrentPageNumber() {
        return currentPageNumber;
    }

    public void setCurrentPageNumber(int currentPageNumber) {
        this.currentPageNumber = currentPageNumber;
    }

    public int getTotalPageSize() {
        return totalPageSize;
    }

    public void setTotalPageSize(int totalPageSize) {
        this.totalPageSize = totalPageSize;
    }

    public String get_page_div() {
        return _page_div;
    }

    public void set_page_div(String _page_div) {
        this._page_div = _page_div;
    }

    public Page() {
        this(0,1,new ArrayList(),0,(String)null);
    }

    public Page(int pageMaxSize, int currentPageDataSize, List<T> dataList, int totalSize, String _page_div) {
        this.pageMaxSize = pageMaxSize;
        this.currentPageDataSize = currentPageDataSize;
        this.dataList = dataList;
        this.totalSize = totalSize;
        this._page_div = _page_div;
    }

    public Page(List<T> dataList) {
        this.dataList = dataList;
    }
}
