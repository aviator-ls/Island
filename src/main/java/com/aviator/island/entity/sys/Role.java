package com.aviator.island.entity.sys;

import com.aviator.island.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by aviator_ls on 2018/7/27.
 */
@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Table(name = "t_sys_role")
@Setter
@Getter
public class Role extends BaseEntity{
    public interface ROLE_AVAILABLE {
        int unAvailable = 0;

        int available = 1;
    }
    @Id
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
    @Column(name = "id")
    private String id;// 角色id

    @Column(name = "role_name")
    private String name;// 角色名称

    @Column(name = "role_desc")
    private String desc;// 角色描述

    @Column(name = "available", columnDefinition = "tinyint")
    private int available;

    @ManyToMany(targetEntity = User.class)
    @JoinTable(name = "t_sys_user_role", joinColumns = @JoinColumn(name = "role_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> userSet;

    @ManyToMany(targetEntity = Resource.class, mappedBy = "roleSet")
    private Set<Resource> resourceSet;

}
