package com.aviator.island.shiro;

import com.aviator.island.entity.sys.User;
import com.aviator.island.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

/**
 * Created by aviator_ls on 2018/7/27.
 */
public class UserAuthorizingRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        String userName = (String) principalCollection.getPrimaryPrincipal();
        Set<String> roleSet = userService.findRoleStrSetByUserName(userName);
        Set<String> resourceSet = userService.findResourceStrSetByUserName(userName);
        authorizationInfo.setRoles(roleSet);
        authorizationInfo.setStringPermissions(resourceSet);
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String userName = (String) authenticationToken.getPrincipal();
        User dbUser = userService.getUserByUserName(userName);
        if (dbUser == null) {
            throw new UnknownAccountException();
        }
        if (User.USER_LOCK.UNLOCK != dbUser.getLocked()) {
            throw new LockedAccountException();
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(dbUser.getUserName(), dbUser.getPassword(), getName());
        authenticationInfo.setCredentialsSalt(ByteSource.Util.bytes(dbUser.getSalt()));
        return authenticationInfo;
    }
}
