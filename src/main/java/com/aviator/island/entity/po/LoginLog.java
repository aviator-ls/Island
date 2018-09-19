package com.aviator.island.entity.po;

import com.aviator.island.entity.BaseEntity;
import com.aviator.island.entity.sys.User;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by 18057046 on 2018/7/13.
 */
@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Table(name = "t_login_log")
@Setter
@Getter
@Accessors(chain = true)
public class LoginLog extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;// 日志自增id

    @Column(name = "ip")
    private String ip;// 用户登录ip

    @Column(name = "login_date")
    private Date loginDate = new Date();// 登录时间

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "user_id")
    private User user;
}
