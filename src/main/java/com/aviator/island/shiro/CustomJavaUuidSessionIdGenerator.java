package com.aviator.island.shiro;

import com.aviator.island.utils.UUIDGenerator;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;

import java.io.Serializable;

/**
 * Created by aviator_ls on 2018/8/2.
 */
public class CustomJavaUuidSessionIdGenerator implements SessionIdGenerator {
    @Override
    public Serializable generateId(Session session) {
        return UUIDGenerator.createUUID();
    }
}
