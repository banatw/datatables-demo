package com.example.datatablesdemo.config;

import java.io.Serializable;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;

// import com.aventrix.jnanoid.jnanoid.NanoIdUtils;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

public class MyCustomIdGenerator implements IdentifierGenerator {

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        // TODO Auto-generated method stub
        return NanoIdUtils.randomNanoId();
    }

}
