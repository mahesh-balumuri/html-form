package com.phform.server.framework.base.dao;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.engine.impl.ExtendedSqlMapClient;
import com.ibatis.sqlmap.engine.mapping.statement.MappedStatement;
import com.ibatis.sqlmap.engine.scope.RequestScope;
import com.phform.server.framework.base.model.Pagination;
import com.phform.server.framework.util.FLog;
import com.phform.server.framework.util.StringUtil;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.*;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.io.Serializable;
import java.util.*;
import java.util.regex.Pattern;

public class BaseDao extends HibernateDaoSupport
{
    private SqlMapClient sqlMapClient;
    
    public void setSqlMapClient(SqlMapClient sqlMapClient)
    {
        this.sqlMapClient = sqlMapClient;
    }
    
    // ~ Methods CRUD ========================================================
    
    /**
     * @see BaseDao#getObject(Class, java.io.Serializable)
     */
    public Object getObject(Class clazz, Serializable id)
    {
        return getHibernateTemplate().get(clazz, id);
    }
    
    /**
     * @see BaseDao#getAllObject(Class)
     */
    public List getAllObject(Class clazz)
    {
        return getHibernateTemplate().loadAll(clazz);
    }
    
    /**
     * @see BaseDao#saveObject(Object)
     */
    public void saveObject(Object o)
    {
        getHibernateTemplate().saveOrUpdate(o);
    }
    
    public void mergeObject(Object o)
    {
        getHibernateTemplate().merge(o);
    }
    
    public void saveOrUpdateAll(Collection collection)
    {
        getHibernateTemplate().saveOrUpdateAll(collection);
    }
    
    /**
     * @see BaseDao#removeObject(Object)
     */
    public void removeObject(Object o)
    {
        getHibernateTemplate().delete(o);
    }
    
    /**
     * @see BaseDao#removeObject(Class, java.io.Serializable)
     */
    public void removeObject(Class clazz, Serializable id)
    {
        getHibernateTemplate().delete(getObject(clazz, id));
    }
    
    /**
     * @see BaseDao#removeAllObject(java.util.Collection)
     */
    public void removeAllObject(Collection collection)
    {
        getHibernateTemplate().deleteAll(collection);
    }
    
    // ~ Bulk Update ========================================================
    protected int executeBulkUpdate(String hsql)
    {
        return getHibernateTemplate().bulkUpdate(hsql);
    }
    
    protected int executeBulkUpdate(String hsql, Object arg)
    {
        return getHibernateTemplate().bulkUpdate(hsql, arg);
    }
    
    protected int executeBulkUpdate(String hsql, Object[] args)
    {
        return getHibernateTemplate().bulkUpdate(hsql, args);
    }
    
    public int executeUpdateByHsql(String hsql, Map paraMap)
    {
        if (hsql != null)
        {
            // Prepare Query
            Query query = getSession().createQuery(hsql);
            setQueryParameters(query, paraMap);
            
            // Execute Query List
            return query.executeUpdate();
        }
        return 0;
    }
    
    public int executeUpdateHSQLId(String hsqlId, Map paraMap)
    {
        String hsql = getCombinedHsqlStatement(hsqlId, paraMap);
        if (hsql != null)
        {
            // Prepare Query
            Query query = getSession().createQuery(hsql);
            setQueryParameters(query, paraMap);
            
            // Execute Query List
            return query.executeUpdate();
        }
        return 0;
    }
    
    // ~ Methods Query ========================================================
    
    /**
     * @see BaseDao#findBy(Class, String, Object)
     */
    public Object findBy(Class clazz, String name, Object value)
    {
        Criteria criteria = getSession().createCriteria(clazz);
        criteria.add(Restrictions.eq(name, value));
        criteria.setMaxResults(1);
        return criteria.uniqueResult();
    }
    
    /**
     * @see BaseDao#findLike(Class, String, String)
     */
    public List findLike(Class clazz, String name, String value)
    {
        Criteria criteria = getSession().createCriteria(clazz);
        criteria.add(Restrictions.like(name, value, MatchMode.ANYWHERE));
        return criteria.list();
    }
    
    /**
     * @see BaseDao#findAllBy(Class, String, Object)
     */
    public List findAllBy(Class clazz, String name, Object value)
    {
        Criteria criteria = getSession().createCriteria(clazz);
        criteria.add(Restrictions.eq(name, value));
        return criteria.list();
    }
    
    protected Pagination findPageByCriteria(
            final DetachedCriteria detachedCriteria, Pagination pagination)
    {
        Criteria criteria =
            detachedCriteria.getExecutableCriteria(getSession());
        if (!pagination.getExportAllData())
        {
            criteria.setProjection(Projections.rowCount());
            Integer iCount = (Integer) criteria.uniqueResult();
            int totalCount = ((iCount != null) ? iCount.intValue() : 0);
            criteria.setProjection(null);
            criteria.setFirstResult(pagination.getFirstResult()).setMaxResults(
                    pagination.getPageSize());
            pagination.setTotalCount(totalCount);
        }
        if (!StringUtils.isEmpty(pagination.getSortCriterion()))
        {
            if (Pagination.SORT_DESC.equals(pagination.getSortType()))
            {
                criteria.addOrder(Order.desc(pagination.getSortCriterion()));
            }
            else
            {
                criteria.addOrder(Order.asc(pagination.getSortCriterion()));
            }
        }
        List items = criteria.list();
        pagination.setList(items);
        return pagination;
    }
    
    public Pagination findPageByCombinedHsql(final String sqlId,
            final Map paraMap, Pagination pagination)
    {
        String hsql = getCombinedHsqlStatement(sqlId, paraMap);
        String countSql = convertToCountHsqlStatement(hsql);
        
        if (hsql != null)
        {
            // prepare sort info
            StringBuffer buffHSQL = new StringBuffer(hsql);
            buffHSQL = appendSqlOrder(pagination, buffHSQL);
            
            // Prepare Query
            Query query = getSession().createQuery(buffHSQL.toString());
            setQueryParameters(query, paraMap);
            if (!pagination.getExportAllData())
            {
                query.setFirstResult(pagination.getFirstResult());
                query.setMaxResults(pagination.getPageSize());
            }
            // Execute Query List
            List restList = query.list();
            
            // set result List info
            pagination.setList(restList);
            
            // Query the totalCount
            if (countSql != null || !pagination.getExportAllData())
            {
                Query countQuery = getSession().createQuery(countSql);
                setQueryParameters(countQuery, paraMap);
                int count =
                    Integer.parseInt(countQuery.uniqueResult().toString());
                ;
                pagination.setTotalCount(count);
            }
        }
        
        return pagination;
    }
    
    public Pagination findPageBySqlId(final String sqlId, final Map paraMap,
            Pagination pagination)
    {
        String hsql = getCombinedHsqlStatement(sqlId, paraMap);
        String countSql = convertToCountHsqlStatement(hsql);
        
        if (hsql != null)
        {
            // prepare sort info
            StringBuffer buffHSQL = new StringBuffer(hsql);
            buffHSQL = appendSqlOrder(pagination, buffHSQL);
            
            // Prepare Query
            Query query = getSession().createSQLQuery(buffHSQL.toString());
            setQueryParameters(query, paraMap);
            if (!pagination.getExportAllData())
            {
                query.setFirstResult(pagination.getFirstResult());
                query.setMaxResults(pagination.getPageSize());
            }
            // Execute Query List
            List restList = query.list();
            // set result List info
            pagination.setList(restList);
            
            // Query the totalCount
            if (countSql != null || !pagination.getExportAllData())
            {
                Query countQuery = getSession().createSQLQuery(countSql);
                setQueryParameters(countQuery, paraMap);
                int count =
                    Integer.parseInt(countQuery.uniqueResult().toString());
                pagination.setTotalCount(count);
            }
        }
        
        return pagination;
    }
    
    public Pagination findPageBySql(final String sql, final Map paraMap,
            Pagination pagination)
    {
        String countSql = convertToCountHsqlStatement(sql);
        
        if (sql != null)
        {
            // prepare sort info
            StringBuffer buffHSQL = new StringBuffer(sql);
            buffHSQL = appendSqlOrder(pagination, buffHSQL);
            
            // Prepare Query
            Query query = getSession().createSQLQuery(buffHSQL.toString());
            setQueryParameters(query, paraMap);
            if (!pagination.getExportAllData())
            {
                query.setFirstResult(pagination.getFirstResult());
                query.setMaxResults(pagination.getPageSize());
            }
            // Execute Query List
            List restList = query.list();
            
            // set result List info
            pagination.setList(restList);
            
            // Query the totalCount
            if (countSql != null || !pagination.getExportAllData())
            {
                Query countQuery = getSession().createSQLQuery(countSql);
                setQueryParameters(countQuery, paraMap);
                int count =
                    Integer.parseInt(countQuery.uniqueResult().toString());
                pagination.setTotalCount(count);
            }
        }
        
        return pagination;
    }
    
    protected Pagination findPageByCombinedHsqlWithDistinct(final String sqlId,
            final Map paraMap, Pagination pagination)
    {
        String hsql = getCombinedHsqlStatement(sqlId, paraMap);
        
        if (hsql != null)
        {
            // prepare sort info
            StringBuffer buffHSQL = new StringBuffer(hsql);
            buffHSQL = appendSqlOrder(pagination, buffHSQL);
            
            // Prepare Query
            Query query = getSession().createQuery(buffHSQL.toString());
            setQueryParameters(query, paraMap);
            if (!pagination.getExportAllData())
            {
                query.setFirstResult(pagination.getFirstResult());
                query.setMaxResults(pagination.getPageSize());
            }
            // Execute Query List
            List restList = query.list();
            
            // set result List info
            pagination.setList(restList);
            
            // Query the totalCount
            Query countQuery = getSession().createQuery(hsql);
            setQueryParameters(countQuery, paraMap);
            int count = countQuery.list().size();
            pagination.setTotalCount(count);
        }
        
        return pagination;
    }
    
    public List findListByCombinedHsql(final String sqlId, final Map paraMap)
    {
        List restList = null;
        String hsql = getCombinedHsqlStatement(sqlId, paraMap);
        if (hsql != null)
        {
            // Prepare Query
            Query query = getSession().createQuery(hsql);
            setQueryParameters(query, paraMap);
            
            // Execute Query List
            restList = query.list();
        }
        return restList;
    }
    
    public List findListByCombinedHsql(final String sqlId, final Map paraMap,
            int firstResult, int maxResult)
    {
        List restList = null;
        String hsql = getCombinedHsqlStatement(sqlId, paraMap);
        if (hsql != null)
        {
            // Prepare Query
            Query query = getSession().createQuery(hsql);
            setQueryParameters(query, paraMap);
            query.setFirstResult(firstResult);
            query.setMaxResults(maxResult);
            
            // Execute Query List
            restList = query.list();
        }
        return restList;
    }
    
    public List findListByHsql(final String hsql, final Map paraMap)
    {
        List restList = null;
        if (hsql != null)
        {
            // Prepare Query
            Query query = getSession().createQuery(hsql);
            setQueryParameters(query, paraMap);
            
            // Execute Query List
            restList = query.list();
        }
        return restList;
    }
    
    public Pagination findPageByHsql(final String hsql, final Map paraMap,
            Pagination pagination)
    {
        if (hsql != null)
        {
            // prepare sort info
            StringBuffer buffHSQL = new StringBuffer(hsql);
            buffHSQL = appendSqlOrder(pagination, buffHSQL);
            //			
            
            // Prepare Query
            Query query = getSession().createQuery(buffHSQL.toString());
            setQueryParameters(query, paraMap);
            if (!pagination.getExportAllData())
            {
                query.setFirstResult(pagination.getFirstResult());
                query.setMaxResults(pagination.getPageSize());
            }
            // Execute Query List
            List restList = query.list();
            // set result List info
            pagination.setList(restList);
            
            // Query the totalCount
            Query countQuery = getSession().createQuery(hsql);
            setQueryParameters(countQuery, paraMap);
            int count = countQuery.list().size();
            pagination.setTotalCount(count);
        }
        
        return pagination;
    }
    
    public List findListBySql(final String sql, final Map paraMap)
    {
        List restList = null;
        if (sql != null)
        {
            // Prepare Query
            Query query = getSession().createSQLQuery(sql);
            setQueryParameters(query, paraMap);
            
            // Execute Query List
            restList = query.list();
        }
        return restList;
    }
    
    public List findListByCombinedSql(final String sqlId, final Map paraMap)
    {
        List restList = null;
        String sql = getCombinedHsqlStatement(sqlId, paraMap);
        if (sql != null)
        {
            // Prepare Query
            Query query = getSession().createSQLQuery(sql);
            setQueryParameters(query, paraMap);
            
            // Execute Query List
            restList = query.list();
        }
        return restList;
    }
    
    public List findListByCombinedSql(final String sqlId, final Map paraMap,
            int firstResult, int maxResult)
    {
        List restList = null;
        String sql = getCombinedHsqlStatement(sqlId, paraMap);
        if (sql != null)
        {
            // Prepare Query
            Query query = getSession().createSQLQuery(sql);
            
            setQueryParameters(query, paraMap);
            query.setFirstResult(firstResult);
            query.setMaxResults(maxResult);
            
            // Execute Query List
            restList = query.list();
        }
        return restList;
    }
    
    protected List findListByCombinedSqlAppend(final String sqlId,
            final Map paraMap, String appendString)
    {
        List restList = null;
        String sql = getCombinedHsqlStatement(sqlId, paraMap);
        if (sql != null)
        {
            StringBuffer buffSQL = new StringBuffer(sql);
            if (!StringUtil.isNull(appendString))
            {
                buffSQL.append(appendString);
            }
            // Prepare Query
            Query query = getSession().createSQLQuery(buffSQL.toString());
            setQueryParameters(query, paraMap);
            
            // Execute Query List
            restList = query.list();
        }
        return restList;
    }
    
    /**
     * this method is to append string after the HQL
     * 
     * @param hsqlId
     * @param paraMap
     * @param appendString
     * @return List
     */
    protected List findListByCombinedHSqlIDAppend(final String hsqlId,
            final Map paraMap, String appendString)
    {
        List restList = null;
        String hsql = getCombinedHsqlStatement(hsqlId, paraMap);
        if (hsql != null)
        {
            StringBuffer buffHSQL = new StringBuffer(hsql);
            if (!StringUtil.isNull(appendString))
            {
                buffHSQL.append(appendString);
            }
            
            // Prepare Query
            Query query = getSession().createQuery(buffHSQL.toString());
            setQueryParameters(query, paraMap);
            
            // Execute Query List
            restList = query.list();
        }
        return restList;
    }
    
    // ~ Methods Util ========================================================
    
    public void flush()
    {
        getHibernateTemplate().flush();
    }
    
    private StringBuffer appendSqlOrder(Pagination pagination,
            StringBuffer buffHSQL)
    {
        if (!StringUtil.isNull(pagination.getSortCriterion()))
        {
            String sortName = pagination.getSortCriterion();
            String sortType = " asc";
            if (Pagination.SORT_DESC.equals(pagination.getSortType()))
            {
                sortType = " desc";
            }
            if (buffHSQL.toString().toLowerCase().indexOf("order by") == -1)
            {
                buffHSQL.append(" order by ");
                buffHSQL.append(sortName);
                buffHSQL.append(sortType);
            }
            else
            {
                buffHSQL.insert(buffHSQL.toString().toLowerCase().lastIndexOf(
                        "order by") + 9, sortName + " " + sortType + ", ");
            }
        }
        return buffHSQL;
        
    }
    
    private String getCombinedHsqlStatement(String sqlId, Object paramObject)
    {
        if (sqlMapClient == null)
        {
            FLog.error("No IBATIS sqlMapClient setted!");
            return null;
        }
        
        String sql = null;
        ExtendedSqlMapClient extendedSqlMapClient =
            (ExtendedSqlMapClient) sqlMapClient;
        MappedStatement mappedStatement =
            extendedSqlMapClient.getMappedStatement(sqlId);
        if (mappedStatement != null)
        {
            RequestScope request = new RequestScope();
            request.setStatement(mappedStatement);
            sql = mappedStatement.getSql().getSql(request, paramObject);
        }
        return sql;
    }
    
    private String convertToCountHsqlStatement(String selectSQL)
    {
        if (!StringUtil.isNull(selectSQL))
        {
//            String patternStr = "\\sfrom\\s";
//            Pattern pattern = Pattern.compile(patternStr);
//            String[] splitArr = pattern.split(selectSQL.toLowerCase());
//            if ((splitArr != null) && (splitArr.length > 0))
//            {
//                int indexOfFrom = splitArr[0].length();
//                return "select count(*) " + selectSQL.substring(indexOfFrom);
//            }
            selectSQL = selectSQL.replaceAll("\\s+", " ");
            String tmp = "";
            if (selectSQL.toLowerCase().startsWith("from"))
            {
                tmp = selectSQL;
            }
            else
            {
                String patternStr = "\\s+from\\s+";
                Pattern pattern = Pattern.compile(patternStr);
                String[] splitArr = pattern.split(selectSQL.toLowerCase());
                if (splitArr != null && splitArr.length > 0)
                {
                    if ("".equals(splitArr[0]))
                    {
                        tmp = selectSQL;
                    }
                    else
                    {
                        int indexOfFrom = splitArr[0].length();
                        tmp = "select count(*) " + selectSQL.substring(indexOfFrom);
                    }
                }
            }
            if (!"".equals(tmp))
            {
                String patternStr = "\\s+order\\s+by\\s+";
                Pattern pattern = Pattern.compile(patternStr);
                String[] splitArr = pattern.split(tmp.toLowerCase());
                if (splitArr != null && splitArr.length > 1)
                {
                    String str = splitArr[splitArr.length - 1];
                    if (!str.contains(")"))
                    {
                        patternStr = "\\s+from\\s+";
                        pattern = Pattern.compile(patternStr);
                        String[] splitArr2 = pattern.split(str.toLowerCase());
                        if (null == splitArr2 || splitArr2.length <= 1)
                        {
                            tmp =
                                tmp.substring(0, tmp.toLowerCase().lastIndexOf(
                                        " order by "));
                        }
                    }
                }
                return tmp;
            }
        }
        return null;
    }
    
    protected void setQueryParameters(Query query, Map paraMap)
    {
        if (query != null && paraMap != null && !paraMap.isEmpty())
        {
            List namedParms = Arrays.asList(query.getNamedParameters());
            Iterator iter = paraMap.keySet().iterator();
            while (iter.hasNext())
            {
                String paraName = (String) iter.next();
                if (namedParms.contains(paraName))
                {
                    Object value = paraMap.get(paraName);
                    if (value instanceof List)
                    {
                        query.setParameterList(paraName, (List) value);
                    }
                    else
                    {
                        query.setParameter(paraName, value);
                    }
                }
            }
        }
    }
    
    public void updateObject(Object o)
    {
        getHibernateTemplate().update(o);
    }
    
    public Session getHibernateSession()
    {
        return getSession();
    }
}
