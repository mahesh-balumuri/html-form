package com.phform.server.framework.base.model;

import org.displaytag.pagination.PaginatedList;
import org.displaytag.properties.SortOrderEnum;
import org.displaytag.util.ParamEncoder;
import org.displaytag.tags.TableTagParameters;
import org.apache.commons.lang.StringUtils;

import com.phform.server.framework.util.SysPropertiesUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class Pagination implements PaginatedList
{
    public static final String SORT_DESC = "descending";
    
    public static final String SORT_ASC = "ascending";
    
    private int pageNumber;
    
    private int pageSize =
        Integer
                .parseInt(SysPropertiesUtil.getProperty("paging.size") == null ? "10"
                        : SysPropertiesUtil.getProperty("paging.size"));
    
    private int firstResult;
    
    public int totalCount;
    
    public List list;
    
    private String searchId;
    
    private String sortCriterion;
    
    private String sortType;
    
    private boolean exportAllData =
        SysPropertiesUtil.getBoolean("export.exportall", true);
    
    private ParamEncoder paramEncoder = null;
    
    protected Pagination()
    {
        
    }
    
    public Pagination(HttpServletRequest request, String id,
            boolean exportDateAll)
    {
        setExportAllData(exportDateAll);
        decideExportOption(request, id);
        init(request, id);
    }
    
    public Pagination(HttpServletRequest request, String id)
    {
        decideExportOption(request, id);
        init(request, id);
    }
    
    private void init(HttpServletRequest request, String id)
    {
        String searchid = request.getParameter("searchid");
        this.searchId = id;
        if (!id.equals(searchid) || getExportAllData())
        {
            if (StringUtils.isEmpty(searchid))
            {
                setPageNumber(1);
                String pagesizeStr = request.getParameter("pagesize");
                if (pagesizeStr != null && !"".equals(pagesizeStr))
                {
                    if (StringUtils.isNumeric(pagesizeStr))
                    {
                        setPageSize(Integer.parseInt(pagesizeStr));
                    }
                    else
                    {
                        setPageSize(Integer.MAX_VALUE); // ALL
                    }
                }
                else
                {
                    setPageSize(getPageSizeWithSearchIdDifferent(request));
                }
            }
            else
            {
                setPageNumber(getPageNumberWithSearchIdDifferent(request));
                setSortType(getSortTypeWithSearchIdDifferent(request));
                setSortCriterion(getSortCodeWithSearchIdDifferent(request));
                setPageSize(getPageSizeWithSearchIdDifferent(request));
            }
        }
        else
        {
            String page;
            Object obj =
                request.getParameterMap() == null ? null : request
                        .getParameterMap().get("page");
            if (obj != null && obj.getClass().isArray())
            {
                String[] pageArr = (String[]) obj;
                page = pageArr[pageArr.length - 1];
            }
            else
            {
                page = request.getParameter("page");
            }
            if (StringUtils.isEmpty(page) || !StringUtils.isNumeric(page))
            {
                page = "1";
            }
            setPageNumber(Integer.parseInt(page));
            String sort = request.getParameter("dir");
            if ("desc".equals(sort))
            {
                setSortType(Pagination.SORT_DESC);
            }
            else
            {
                setSortType(Pagination.SORT_ASC);
            }
            String orderBy = request.getParameter("sort");
            if (StringUtils.isNotEmpty(orderBy))
            {
                setSortCriterion(orderBy);
            }
            // set dynamic page size
            String pagesizeStr = request.getParameter("pagesize");
            if (pagesizeStr != null && !"".equals(pagesizeStr))
            {
                if (StringUtils.isNumeric(pagesizeStr))
                {
                    setPageSize(Integer.parseInt(pagesizeStr));
                }
                else
                {
                    setPageSize(Integer.MAX_VALUE); // ALL
                }
            }
            else
            {
                setPageSize(getPageSizeWithSearchIdDifferent(request));
            }
        }
    }
    
    private int getPageNumberWithSearchIdDifferent(HttpServletRequest request)
    {
        String pag = request.getParameter(this.searchId + "number");
        return StringUtils.isEmpty(pag) ? 1 : Integer.parseInt(pag);
    }
    
    private int getPageSizeWithSearchIdDifferent(HttpServletRequest request)
    {
        String pag = request.getParameter(this.searchId + "size");
        return StringUtils.isEmpty(pag) ? getPageSize() : Integer.parseInt(pag);
    }
    
    private String getSortTypeWithSearchIdDifferent(HttpServletRequest request)
    {
        String pag = request.getParameter(this.searchId + "sortType");
        return StringUtils.isEmpty(pag) ? Pagination.SORT_ASC : pag;
    }
    
    private String getSortCodeWithSearchIdDifferent(HttpServletRequest request)
    {
        String pag = request.getParameter(this.searchId + "sortCode");
        return StringUtils.isEmpty(pag) ? null : pag;
    }
    
    public void setList(List list)
    {
        this.list = list;
    }
    
    public int getTotalCount()
    {
        return totalCount;
    }
    
    public void setTotalCount(int totalCount)
    {
        this.totalCount = totalCount;
    }
    
    public int getPageSize()
    {
        return pageSize;
    }
    
    public void setSearchId(String searchId)
    {
        this.searchId = searchId;
    }
    
    public void setSortCriterion(String sortCriterion)
    {
        this.sortCriterion = sortCriterion;
    }
    
    public void setSortType(String sortType)
    {
        this.sortType = sortType;
    }
    
    public int getFirstResult()
    {
        if (pageNumber > 0)
        {
            return (pageNumber - 1) * pageSize;
        }
        return firstResult;
    }
    
    public void setPageNumber(int pageNumber)
    {
        this.pageNumber = pageNumber;
    }
    
    public void setPageSize(int pageSize)
    {
        this.pageSize = pageSize;
    }
    
    public void setFirstResult(int firstResult)
    {
        this.firstResult = firstResult;
    }
    
    public String getSortType()
    {
        return sortType;
    }
    
    public List getList()
    {
        return list;
    }
    
    public int getPageNumber()
    {
        return this.pageNumber;
    }
    
    public int getObjectsPerPage()
    {
        return this.pageSize;
    }
    
    public int getFullListSize()
    {
        return this.totalCount;
    }
    
    public String getSortCriterion()
    {
        return this.sortCriterion;
    }
    
    public SortOrderEnum getSortDirection()
    {
        if (SORT_DESC.equals(this.sortType))
        {
            return SortOrderEnum.DESCENDING;
        }
        else
        {
            return SortOrderEnum.ASCENDING;
        }
    }
    
    public String getSearchId()
    {
        return this.searchId;
    }
    
    private String encodeParameter(String parameterName)
    {
        // paramEncoder has been already instantiated?
        if (this.paramEncoder == null)
        {
            // use the id attribute to get the unique identifier
            this.paramEncoder = new ParamEncoder(this.searchId);
        }
        
        return this.paramEncoder.encodeParameterName(parameterName);
    }
    
    protected void decideExportOption(HttpServletRequest request, String id)
    {
        if (getExportAllData())
        {
            String export =
                request
                        .getParameter(encodeParameter(TableTagParameters.PARAMETER_EXPORTTYPE));
            String exportMark =
                request.getParameter(TableTagParameters.PARAMETER_EXPORTING);
            if (!"1".equals(exportMark) || !StringUtils.isEmpty(export))
            {
                setExportAllData(false);
            }
        }
    }
    
    public boolean getExportAllData()
    {
        return exportAllData;
    }
    
    public void setExportAllData(boolean exportAllData)
    {
        this.exportAllData = exportAllData;
    }
    
}
