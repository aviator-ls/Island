package com.aviator.island.shiro;

import com.aviator.island.entity.sys.Resource;
import com.aviator.island.entity.sys.Role;
import com.aviator.island.entity.sys.User;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by 18057046 on 2018/7/25.
 */
public class Demo {

    private String userName = "root";

    @Test
    public void testBase64(){
        SecureRandomNumberGenerator secureRandomNumberGenerator = new SecureRandomNumberGenerator();
        System.out.println(secureRandomNumberGenerator.nextBytes().toHex());
        System.out.println(secureRandomNumberGenerator.nextBytes().toHex());
        System.out.println(secureRandomNumberGenerator.nextBytes().toHex());
    }

    @Test
    public void testFlatMap(){
        User user = new User();
        Set<Role> roleSet = new HashSet<>();
        Set<Resource> resourceSet1 = new HashSet<>();
        Set<Resource> resourceSet2 = new HashSet<>();
        Role role1 = new Role();
        role1.setName("role1");
        Role role2 = new Role();
        role2.setName("role2");
        Resource resource11 = new Resource();
        Resource resource12 = new Resource();
        Resource resource21 = new Resource();
        Resource resource22 = new Resource();
        resource11.setName("resource11");
        resource12.setName("resource12");
        resourceSet1.add(resource11);
        resourceSet1.add(resource12);
        resource21.setName("resource21");
        resource22.setName("resource22");
        resourceSet2.add(resource21);
        resourceSet2.add(resource22);
        role1.setResourceSet(resourceSet1);
        role2.setResourceSet(resourceSet2);
        roleSet.add(role1);
        roleSet.add(role2);
        user.setRoleSet(roleSet);
        Set<String> set = user.getRoleSet().stream().flatMap(role -> role.getResourceSet().stream().map(resource -> resource.getName())).collect(Collectors.toSet());
        set.forEach(s -> System.out.println(s));
    }

}
