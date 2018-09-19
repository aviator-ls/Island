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
 * Created by 18057046 on 2018/8/15.
 */
@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Table(name = "t_tag")
@Setter
@Getter
@Accessors(chain = true)
public class Tag extends BaseEntity {

    @Id
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
    @Column(name = "id")
    private String id;

    @Column(name = "tag_name")
    private String name;

    @Column(name = "tag_desc")
    private String desc;

    @Column(name = "tag_img")
    private String imgPath;

    @Column(name = "path")
    private String path;

    @Column(name = "create_time")
    private Date createTime = new Date();

    @Column(name = "update_time")
    private Date updateTime = new Date();

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "create_id")
    private User createUser;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "update_id")
    private User updateUser;

    @ManyToMany(targetEntity = Post.class, mappedBy = "tagSet")
    private Set<Post> postSet;

    @ManyToMany(targetEntity = Ask.class, mappedBy = "tagSet")
    private Set<Ask> askSet;
}
