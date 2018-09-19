package com.aviator.island.shiro;

import com.aviator.island.dao.SessionDao;
import com.aviator.island.entity.sys.MySession;
import com.aviator.island.utils.SerializableUtil;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.ValidatingSession;
import org.apache.shiro.session.mgt.eis.CachingSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by aviator_ls on 2018/8/2.
 */
public class DBSessionDao extends CachingSessionDAO {

    @Autowired
    private SessionDao sessionDao;

    @Override
    protected void doUpdate(Session session) {
        if (session instanceof ValidatingSession && !((ValidatingSession) session).isValid()) {
            return;
        }
        sessionDao.update(sessionToMySession(session));
    }

    @Override
    protected void doDelete(Session session) {
        sessionDao.remove(sessionToMySession(session));
    }

    @Override
    protected Serializable doCreate(Session session) {
        return sessionDao.save(sessionToMySession(session));
    }

    @Override
    protected Session doReadSession(Serializable serializable) {
        List<MySession> result = sessionDao.findAll();
        if (result != null && result.size() > 0) {
            return SerializableUtil.simpleDeserialize(result.get(0).getSessionSerialize(), Session.class);
        }
        return null;
    }

    private MySession sessionToMySession(Session session) {
        MySession mySession = new MySession();
        mySession.setSessionId((String) session.getId());
        mySession.setSessionSerialize(SerializableUtil.simpleSerialize(session));
        mySession.setCreateTime(new Date());
        mySession.setUpdateTime(new Date());
        return mySession;
    }
}
