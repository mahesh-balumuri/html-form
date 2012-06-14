package com.phform.server.framework.util.servlet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.hibernate.Session;

import com.phform.server.entity.Form;
import com.phform.server.framework.base.dao.BaseDao;
import com.phform.server.framework.spring.context.ApplicationContextUtil;
import com.phform.server.framework.util.FLog;
import com.phform.server.web.action.form.FormMemory;

public class InitServlet extends HttpServlet
{
    
    @Override
    public void init() throws ServletException
    {
        /*
        BaseDao baseDao = (BaseDao) ApplicationContextUtil.getBean("baseDao");
        Session se = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try
        {
            se = baseDao.getHibernateSession();
            conn = se.connection();
            ps = conn.prepareStatement("select count(*) from PH_FORM");
            rs = ps.executeQuery();
            int count = 0;
            if (rs.next())
            {
                count = rs.getInt(1);
            }
            if (null != rs)
            {
                try
                {
                    rs.close();
                }
                catch (SQLException e)
                {
                }
                rs = null;
            }
            if (null != ps)
            {
                try
                {
                    ps.close();
                }
                catch (SQLException e)
                {
                }
            }
            if (count < 5000)
            {
                ps = conn.prepareStatement("select t.* from PH_FORM t");
                rs = ps.executeQuery();
                while (rs.next())
                {
                    Form f = new Form();
                    f.setId(rs.getString("id"));
                    f.setFormId(rs.getString("formId"));
                    f.setFormName(rs.getString("formName"));
                    f.setFormPath(rs.getString("formPath"));
                    FormMemory.getInstance().addForm(f);
                }
            }
        }
        catch (SQLException e)
        {
            if (FLog.isError())
            {
                FLog.error(e);
            }
        }
        catch (Exception e)
        {
            if (FLog.isError())
            {
                FLog.error(e);
            }
        }
        finally
        {
            if (null != rs)
            {
                try
                {
                    rs.close();
                }
                catch (SQLException e)
                {
                }
            }
            if (null != ps)
            {
                try
                {
                    ps.close();
                }
                catch (SQLException e)
                {
                }
            }
            if (null != se)
            {
                try
                {
                    se.close();
                }
                catch (Exception e)
                {
                }
            }
        }
        */
    }
    
}
