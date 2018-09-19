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
 * Created by 18057046 on 2018/7/13.
 */
@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Table(name = "t_board")
@Setter
@Getter
@Accessors(chain = true)
public class Board extends BaseEntity {

    @Id
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
    @Column(name = "id")
    private String id;// 版块id

    @Column(name = "name")
    private String name;// 版块名

    @Column(name = "board_desc")
    private String desc;// 版块描述

    @Column(name = "board_level")
    private int level = 1;

    @Column(name = "create_time")
    private Date createTime = new Date();

    @Column(name = "update_time")
    private Date updateTime = new Date();

    @ManyToMany(targetEntity = User.class)
    @JoinTable(name = "t_board_user", joinColumns = @JoinColumn(name = "board_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> userSet;

    @OneToMany(targetEntity = Post.class, mappedBy = "board")
    private Set<Post> postSet;

    @OneToMany(targetEntity = Ask.class, mappedBy = "board")
    private Set<Ask> askSet;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Board parentBoard;// 父版块

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "parentBoard", fetch = FetchType.EAGER)
    private Set<Board> childBoardSet;// 子版块
}
