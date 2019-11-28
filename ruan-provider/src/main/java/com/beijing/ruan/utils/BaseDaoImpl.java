package com.beijing.ruan.utils;

import org.apache.ibatis.executor.BaseExecutor;
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
        return MAPPER_NAMESPACE_SUFFIX + NAMESPACE_ID_LINK_TAG + nameSpaceName;
    }

    public T findById(String paramName, Object object) throws Exception {
        try {
            return this.getSqlSession().selectOne(paramName,object);
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    public Page<T> findByPage(String paramName, Object paramValue, PaginationParameters paginationParameters) throws Exception {
        try {
            int totalDataSize = findTotalDataSize(paramName, paramValue, paginationParameters);
            List dataList = this.getDataListByPage(paramName, paramValue, paginationParameters,totalDataSize);
            Page<T> page = new Page(paginationParameters.getCurrentPageNumber(), paginationParameters.getPageMaxSize(), dataList, totalDataSize, paginationParameters.get_page_div());
            return page;
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    public List<T> getDataListByPage(String paramName, Object paramValue, PaginationParameters paginationParameters, int totalDataSize) throws Exception {
        try {
            int firstPageValue = PageCreater.getFirstPageValue(paginationParameters, totalDataSize);
            int secondValue = PageCreater.getSecondPageValue(firstPageValue, totalDataSize, paginationParameters.getPageMaxSize());
            List<T> dataListByPage = getDataListByPages(paramName, paramValue, paginationParameters, firstPageValue, secondValue);
            return dataListByPage;
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    private List<T> getDataListByPages(String paramName, Object paramValue, PaginationParameters paginationParameters, int firstPageValue, int secondValue) throws Exception {
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

    public int insert(String paramName, Object object) throws Exception{
        try{
            return this.getSqlSession().insert(paramName, object);
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    public List<T> findByParam(String paramName, Object object) throws Exception{
        try{
            return this.getSqlSession().selectList(paramName, object);
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    public int update(String paramName, Object object) throws Exception{
        try{
            return this.getSqlSession().update(paramName, object);
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    public int delete(String paramName, Object object) throws Exception{
        try{
            return this.getSqlSession().delete(paramName, object);
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    public T findEntityById(E e) throws Exception{
        try{
            String paramName = this.getMapperNamespaceByClass(e) + "." + SqlIdSub.findById.toString();
            return this.findById(paramName, e);
        }catch (Exception var){
            throw new Exception(var);
        }
    }

    public Page<T> findEntityByPage(E e, PaginationParameters paginationParameters) throws Exception{
        try{
            String paramName = this.getMapperNamespaceByClass(e) + "." + SqlIdSub.findByPage;
            return this.findByPage(paramName, e, paginationParameters);
        }catch (Exception var){
            throw new Exception(var);
        }
    }

    public List<T> findByEntity(E e) throws Exception{
        try {
            String paramName = this.getMapperNamespaceByClass(e) + "." + SqlIdSub.list;
            return this.findByParam(paramName, e);
        }catch (Exception var){
            throw new Exception(var);
        }
    }

    public int deleteByEntity(E e) throws Exception{
        try{
            String paramName = this.getMapperNamespaceByClass(e) + "." + SqlIdSub.delete;
            return this.delete(paramName, e);
        }catch (Exception var){
            throw new Exception(var);
        }
    }

    public int insertEntity(E e) throws Exception{
        try {
            String paramName = this.getMapperNamespaceByClass(e) + "." + SqlIdSub.insert;
            return this.insert(paramName, e);
        }catch (Exception var){
            throw new Exception(var);
        }
    }

    public int updateEntityById(E e) throws Exception{
        try {
            String paramName = this.getMapperNamespaceByClass(e) + "." + SqlIdSub.update;
            return this.update(paramName, e);
        }catch (Exception var){
            throw new Exception(var);
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
