package com.aviator.island.entity.sys;

import com.aviator.island.entity.BaseEntity;
import com.aviator.island.entity.po.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * Created by aviator_ls on 2018/7/13.
 */
@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Table(name = "t_sys_user")
@Setter
@Getter
@Accessors(chain = true)
public class User extends BaseEntity {

    public interface USER_LOCK {
        int UNLOCK = 0;

        int LOCK = 1;
    }

    @Id
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
    @Column(name = "id")
    private String id;// 用户id

    @Column(name = "user_name")
    private String userName;// 用户名

    @Column(name = "user_password")
    private String password;// 用户密码

    @Column(name = "nick_name")
    private String nickName;// 昵称

    @Column(name = "profile_picture")
    private String profilePicture;// 头像

    @Column(name = "user_type", columnDefinition = "tinyint")
    private int type = 1;// 1:普通用户,2:管理员

    @Column(name = "locked", columnDefinition = "tinyint")
    private int locked;// 0:未锁定,1:锁定

    @Column(name = "credit")
    private int credit;// 积分

    @ManyToMany(targetEntity = Board.class, mappedBy = "userSet")
    private Set<Board> boardSet;

    @OneToMany(targetEntity = LoginLog.class, cascade = CascadeType.REMOVE, mappedBy = "user")
    private Set<LoginLog> loginLogSet;

    @OneToMany(targetEntity = Post.class, mappedBy = "user")
    private Set<Post> postSet;// 发送的帖子

    @OneToMany(targetEntity = Post.class, mappedBy = "user")
    private Set<ReplyPost> replyPostSet;// 回复的帖子

    @OneToMany(targetEntity = Tag.class, mappedBy = "createUser")
    private Set<Tag> createTagSet;// 创建的标签

    @OneToMany(targetEntity = Tag.class, mappedBy = "updateUser")
    private Set<Tag> updateTagSet;// 更新的标签

    @OneToMany(targetEntity = PostSpecial.class, mappedBy = "user")
    private Set<PostSpecial> postSpecialSet;// 更新的标签

    @OneToMany(targetEntity = Ask.class, mappedBy = "askUser")
    private Set<Ask> askSet;// 提的问题

    @OneToMany(targetEntity = Answer.class, mappedBy = "answerUser")
    private Set<Answer> answerSet;// 回答的问题

    @ManyToMany(targetEntity = Post.class)
    @JoinTable(name = "t_user_post_collect", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "post_id"))
    private Set<Post> collectPostSet;// 收藏的文章

    @ManyToMany(targetEntity = Ask.class)
    @JoinTable(name = "t_user_ask_collect", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "ask_id"))
    private Set<Ask> collectAskSet;// 收藏的问答

    @ManyToMany(targetEntity = User.class)
    @JoinTable(name = "t_user_user_follow", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "follower_id"))
    private Set<User> followerSet;// 被关注的用户set(被这些用户关注)

    @ManyToMany(targetEntity = User.class, mappedBy = "followerSet")
    private Set<User> followUserSet;// 关注的用户

    /* 安全模块 */
    @ManyToMany(targetEntity = Role.class, mappedBy = "userSet")
    private Set<Role> roleSet;

    @Column(name = "salt")
    private String salt;

    @Column(name = "create_time")
    private Date createTime = new Date();

    @Column(name = "update_time")
    private Date updateTime = new Date();

}