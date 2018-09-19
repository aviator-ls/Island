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
 * Created by aviator_ls on 2018/7/13.
 */
@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Table(name = "t_post")
@Setter
@Getter
@Accessors(chain = true)
public class Post extends BaseEntity {
    @Id
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
    @Column(name = "id")
    private String id;// 帖子id

    @Column(name = "title")
    private String title;// 帖子标题

    @Column(name = "content", columnDefinition = "longtext")
    private String content;// 帖子内容

    @Column(name = "views")
    private int views = 1;// 浏览数

    @Column(name = "source_type", columnDefinition = "tinyint")
    private int sourceType = 1;// 来源,1:原创,2:转载,3:翻译

    @Column(name = "source")
    private String source;// 来源

    @Column(name = "reference")
    private String reference;// 引用资料

    @Column(name = "boutique", columnDefinition = "tinyint")
    private int boutique;//是否精品 是:1,否:0

    @Column(name = "create_time")
    private Date createTime = new Date();// 发布时间

    @Column(name = "update_time")
    private Date updateTime = new Date();// 更新时间

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(targetEntity = Board.class)
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToMany(targetEntity = Tag.class, fetch = FetchType.EAGER)
    @JoinTable(name = "t_post_tag", joinColumns = @JoinColumn(name = "post_id"), inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tagSet;

    @ManyToMany(targetEntity = User.class, mappedBy = "collectPostSet")
    private Set<User> userSet;// 收藏文章的用户

    @OneToMany(targetEntity = ReplyPost.class, mappedBy = "mainPost", cascade = CascadeType.REMOVE)
    private Set<ReplyPost> replyPostSet;

    @ManyToOne(targetEntity = PostSpecial.class)
    @JoinColumn(name = "special_id")
    private PostSpecial special;
}
