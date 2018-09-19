package com.aviator.island.entity.po;

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
 * 文章专辑
 * Created by 18057046 on 2018/9/12.
 */
@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Table(name = "t_post_special")
@Setter
@Getter
@Accessors(chain = true)
public class PostSpecial {
    @Id
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
    @Column(name = "id")
    private String id;// 专辑id

    @Column(name = "name")
    private String name;// 专辑名

    @Column(name = "special_desc")
    private String desc;// 专辑描述

    @Column(name = "create_time")
    private Date createTime = new Date();

    @Column(name = "update_time")
    private Date updateTime = new Date();

    @OneToMany(targetEntity = Post.class, mappedBy = "special", cascade = CascadeType.REMOVE)
    private Set<Post> postSet;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "user_id")
    private User user;
}
