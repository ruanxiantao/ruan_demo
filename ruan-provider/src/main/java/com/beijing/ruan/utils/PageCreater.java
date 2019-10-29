package com.beijing.ruan.utils;

import org.omg.CORBA.PRIVATE_MEMBER;

public class PageCreater {
    public static final int FIND_PAGE_SIZE_TAG = -1;
    public static PageCreater.DataBaseType CURRENT_DATABASE_TYPE;
    public static final int FIRST_PAGE_NUMBER = 1;
    public static final String FIRST_PAGE_NUMBER_STRING = "1";
    private PageCreater.DataBaseType dataBaseType;

    public PageCreater() {
    }

    public PageCreater.DataBaseType getDataBaseType() {
        return this.dataBaseType;
    }

    public void setDataBaseType(PageCreater.DataBaseType dataBaseType) {
        this.dataBaseType = dataBaseType;
    }

    public static int getFirstPageValue(PaginationParameters paginationParameters, int totalSize) {
        int firstPageValue = 0;
        int currentPageNum = paginationParameters.getCurrentPageNumber();
        int pageMaxSize = paginationParameters.getPageMaxSize();
        switch (CURRENT_DATABASE_TYPE) {
            case MYSQL:
            case ORACLE:
            default:
                firstPageValue = (currentPageNum - 1) * pageMaxSize;
                if(firstPageValue > totalSize) {
                    currentPageNum = totalSize /pageMaxSize;
                    paginationParameters.setCurrentPageNumber(currentPageNum);
                    firstPageValue = totalSize - pageMaxSize;
                }
                if(firstPageValue < 0){
                    firstPageValue = 0;
                    paginationParameters.setCurrentPageNumber(1);
                }
                return firstPageValue;
        }
    }

    public static int getSecondPageValue(int firstPageValue, int totalSize, int pageMaxSize) {
        int secondPageNum = 0;
        switch (CURRENT_DATABASE_TYPE){
            case MYSQL:
                secondPageNum = pageMaxSize;
                break;
            case ORACLE:
                secondPageNum = firstPageValue + pageMaxSize;
        }
        return secondPageNum;
    }

    static {
        CURRENT_DATABASE_TYPE = PageCreater.DataBaseType.MYSQL;
    }

    public static enum DataBaseType {
        MYSQL,
        ORACLE;

        DataBaseType() {
        }
    }
}
