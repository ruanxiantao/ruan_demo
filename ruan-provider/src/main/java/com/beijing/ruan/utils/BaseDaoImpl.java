package com.beijing.ruan.utils;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.List;

public class BaseDaoImpl<T,E> extends SqlSessionDaoSupport {
    @Autowired
    private SqlSessionFactory sqlSessionFactory;
    public static final String NAMESPACE_ID_LINK_TAG = ".";
    public static final String MAPPER_NAMESPACE_SUFFIX = "Mapper";

    public BaseDaoImpl() {
    }
    @PostConstruct
    private void init() {
        super.setSqlSessionFactory(this.sqlSessionFactory);
    }
    private String getMapperNamespaceByClass(E e){
        String nameSpaceName = "";
        nameSpaceName = e.getClass().getSimpleName();
        return "Mapper" + "." + nameSpaceName;
    }

    public T fingById(String paramName, Object object) throws Exception {
        try {
            return this.getSqlSession().selectOne(paramName,object);
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    public Page<T> findByPage(String paramName, Object paramValue, PaginationParameters paginationParameters) throws Exception {
        try {
            int totalDataSize = this.findTotalDataSize(paramName, paramValue, paginationParameters);
            List dataList = this.getDataListByPage(paramName, paramValue, paginationParameters);
            Page<T> page = new Page(paginationParameters.getCurrentPageNumber(), paginationParameters.getPageMaxSize(), dataList, totalDataSize, paginationParameters.get_page_div());
            return page;
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    public List<T> getDataListByPage(String paramName, Object paramValue, PaginationParameters paginationParameters, int totalDataSize) throws Exception{
        int firstPageValue = PageCreater.getFirstPageValue(paginationParameters, totalDataSize);
        int secondValue = PageCreater.getSecondPageValue(firstPageValue, totalDataSize, paginationParameters.getPageMaxSize());
        return this.getDataListByPage(paramName, paramValue, paginationParameters, firstPageValue, secondValue);
    }catch (Exception e) {
        throw new Exception(e);
    }

    public List<T> getDataListByPage(String paramName, Object paramValue, PaginationParameters paginationParameters, int firstPageValue, int secondValue) throws Exception {
        try {
            return this.getSqlSession().selectList(paramName, paramValue, new ESNRowBounds(firstPageValue,secondValue, paginationParameters.getOrderKey(), paginationParameters.getOrderType(), paginationParameters.getOrderBy()));
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    public int findTotalDataSize(String paramName, Object paramValue, PaginationParameters paginationParameters) throws Exception {
        try {
            List countDataList = this.getSqlSession().selectList(paramName, paramValue, new ESNRowBounds(-1, -1, paginationParameters.getOrderKey(), paginationParameters.getOrderType(), paginationParameters.getOrderBy()));
            if(countDataList.size() == 0) {
                return 0;
            }else {
                int count_num = (Integer) countDataList.get(0);
                return count_num;
            }
        }catch (Exception e){
            throw new Exception(e);
        }
    }




    @Override
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
        super.setSqlSessionFactory(sqlSessionFactory);
    }

    private static enum SqlIdSub {
        insert,
        update,
        delete,
        list,
        findById,
        findByPage;

        SqlIdSub() {
        }
    }
}
