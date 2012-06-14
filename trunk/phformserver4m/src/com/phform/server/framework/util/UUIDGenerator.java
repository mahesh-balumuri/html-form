package com.phform.server.framework.util;

import java.io.Serializable;
import java.util.Properties;

import org.hibernate.Hibernate;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.id.AbstractUUIDGenerator;
import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.id.UUIDHexGenerator;

public class UUIDGenerator extends AbstractUUIDGenerator
{
    
    public Serializable generate(SessionImplementor session, Object obj)
    {
        Properties props = new Properties();
        IdentifierGenerator gen = new UUIDHexGenerator();
        ((Configurable) gen).configure(Hibernate.STRING, props, null);
        return (String) gen.generate(null, null) + System.currentTimeMillis();
    }
}
