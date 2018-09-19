package com.aviator.island.entity.po;

import com.aviator.island.entity.BaseEntity;
import com.aviator.island.entity.sys.User;
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
 * Created by aviator_ls on 2018/8/16.
 */
@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Table(name = "t_reply_post")
@Setter
@Getter
@Accessors(chain = true)
public class ReplyPost extends BaseEntity {
    @Id
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
    @Column(name = "id")
    private String id;// 回复贴id

    @Column(name = "content")
    private String content;// 回复内容

    @Column(name = "create_time")
    private Date createTime = new Date();// 发布时间

    @Column(name = "update_time")
    private Date updateTime = new Date();// 更新时间

    @ManyToOne(targetEntity = Post.class)
    @JoinColumn(name = "main_post_id")
    private Post mainPost;

    @ManyToOne(targetEntity = ReplyPost.class)
    @JoinColumn(name = "main_reply_post_id")
    private ReplyPost mainReplyPost;// 本回复回复的ReplyPostId

    @OneToMany(targetEntity = ReplyPost.class, mappedBy = "mainReplyPost")
    private Set<ReplyPost> replyPostSet;// 回复本跟帖的回复

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;// 回复用户
}
