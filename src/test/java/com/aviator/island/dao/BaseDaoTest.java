package com.aviator.island.dao;

import com.aviator.island.BaseJunit4Test;
import com.aviator.island.entity.po.*;
import com.aviator.island.entity.sys.User;
import com.aviator.island.utils.CriteriaBuilder;
import org.hibernate.criterion.DetachedCriteria;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * Created by 18057046 on 2018/8/19.
 */
public class BaseDaoTest extends BaseJunit4Test {

    @Autowired
    private BoardDao<Board> boardDao;

    @Autowired
    private LoginLogDao<LoginLog> loginLogDao;

    @Autowired
    private PostDao<Post> postDao;

    @Autowired
    private ReplyPostDao<ReplyPost> replyPostDao;

    @Autowired
    private TagDao<Tag> tagDao;

    @Autowired
    private UserDao<User> userDao;

    @Test
    public void allTest() {
        Board board = new Board().setName("testBoard");
        boardDao.save(board);
        User user = new User().setUserName("testUser").setPassword("123456");
        userDao.save(user);
        LoginLog loginLog = new LoginLog().setIp("127.0.0.1").setUser(user);
        Post post = new Post().setTitle("testPost").setBoard(board).setUser(user);
        postDao.save(post);
        ReplyPost replyPost = new ReplyPost().setContent("testReplyPost").setMainPost(post).setUser(user);
        Tag tag = new Tag().setName("testTag").setCreateUser(user).setUpdateUser(user);
        allTest(board, boardDao, Board.class);
        allTest(loginLog, loginLogDao, LoginLog.class);
        allTest(post, postDao, Post.class);
        allTest(replyPost, replyPostDao, ReplyPost.class);
        allTest(tag, tagDao, Tag.class);
        allTest(user, userDao, User.class);
    }

    public void allTest(Object saveEntity, BaseDao baseDao, Class entityClass) {
        DetachedCriteria criteria = CriteriaBuilder.newDetachedCriteriaBuilder(entityClass).build();
        // test save()
        Serializable id = save(saveEntity, baseDao);
        logger.debug("save id : {}", id);
        Assert.notNull(id, "id null");
        // test get()
        Object entity = get(id, baseDao);
        logger.debug("get entity : {}", entity);
        Assert.notNull(entity, "entity null");
        // test load()
        entity = load(id, baseDao);
        logger.debug("load entity : {}", entity);
        Assert.notNull(entity, "entity null");
        // test loadAll()
        List<Object> entityList = loadAll(baseDao);
        logger.debug("loadAll entityList : {}", entityList);
        Assert.notEmpty(entityList, "entityList empty");
        // test findAll()
        entityList = findAll(baseDao);
        logger.debug("findAll entityList : {}", entityList);
        Assert.notEmpty(entityList, "entityList empty");
        // test findByCriteria()
        entityList = findByCriteria(criteria, baseDao);
        logger.debug("findByCriteria entityList : {}", entityList);
        Assert.notEmpty(entityList, "entityList empty");
        // test findByCriteriaPageList()
        entityList = findByCriteriaPageList(criteria, 0, 10, baseDao);
        logger.debug("findByCriteriaPageList entityList : {}", entityList);
        Assert.notEmpty(entityList, "entityList empty");
        // test getDataCount()
        long count = getDataCount(criteria, baseDao);
        logger.debug("getDataCount count:{}", count);
        Assert.isTrue(count > 0, "count might gt 0");
        // test contains()
        boolean isContains = contains(entity, baseDao);
        logger.debug("contains :{}", isContains);
        Assert.isTrue(isContains, "contains might true");
        // test initialize()
        initialize(entity, baseDao);
        // test merge()
        entity = merge(entity, baseDao);
        logger.debug("merge entity : {}", entity);
        Assert.notNull(entity, "entity null");
        // test evict()
        evict(entity, baseDao);
        // test refresh()
        refresh(entity, baseDao);
        // test update() remove()
        update(entity, baseDao);
        remove(entity, baseDao);
        entity = get(id, baseDao);
        Assert.isNull(entity, "entity might null");
        // test deleteAll()
        deleteAll(entityList, baseDao);
        // test clear()
        clear(baseDao);
    }

    public Serializable save(Object entity, BaseDao baseDao) {
        return baseDao.save(entity);
    }

    public Object get(Serializable id, BaseDao baseDao) {
        return baseDao.get(id);
    }

    public Object load(Serializable id, BaseDao baseDao) {
        return baseDao.load(id);
    }

    public List<Object> loadAll(BaseDao baseDao) {
        return baseDao.loadAll();
    }

    public List<Object> findAll(BaseDao baseDao) {
        return baseDao.findAll();
    }

    public List<Object> findByCriteria(DetachedCriteria criteria, BaseDao baseDao) {
        return baseDao.findByCriteria(criteria);
    }

    public List<Object> findByCriteriaPageList(DetachedCriteria criteria, int firstResult, int pageSize, BaseDao baseDao) {
        return baseDao.findByCriteriaPageList(criteria, firstResult, pageSize);
    }

    public long getDataCount(DetachedCriteria criteria, BaseDao baseDao) {
        return baseDao.getDataCount(criteria);
    }

    public boolean contains(Object entity, BaseDao baseDao) {
        return baseDao.contains(entity);
    }

    public void initialize(Object entity, BaseDao baseDao) {
        baseDao.initialize(entity);
    }

    public void update(Object entity, BaseDao baseDao) {
        baseDao.update(entity);
    }

    public void remove(Object entity, BaseDao baseDao) {
        baseDao.remove(entity);
    }

    public Object merge(Object entity, BaseDao baseDao) {
        return baseDao.merge(entity);
    }

    public void evict(Object entity, BaseDao baseDao) {
        baseDao.evict(entity);
    }

    public void refresh(Object entity, BaseDao baseDao) {
        baseDao.refresh(entity);
    }

    public void deleteAll(Collection<Object> entities, BaseDao baseDao) {
        baseDao.deleteAll(entities);
    }

    public void clear(BaseDao baseDao) {
        baseDao.clear();
    }

}
