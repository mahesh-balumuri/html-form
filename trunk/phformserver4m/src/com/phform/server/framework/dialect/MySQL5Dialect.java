package com.phform.server.framework.dialect;

import org.hibernate.dialect.MySQLDialect;
import org.hibernate.Hibernate;

import java.sql.Types;

public class MySQL5Dialect extends MySQLDialect
{
    public MySQL5Dialect()
    {
        super();
        registerHibernateType(Types.LONGVARCHAR, Hibernate.STRING.getName());
    }
    
    protected void registerVarcharTypes()
    {
        registerColumnType(Types.VARCHAR, "longtext");
        registerColumnType(Types.VARCHAR, 16777215, "mediumtext");
        registerColumnType(Types.VARCHAR, 65535, "varchar($l)");
    }
}
