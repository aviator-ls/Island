package com.aviator.island.shiro;

import com.aviator.island.entity.sys.User;
import org.apache.shiro.crypto.AesCipherService;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.DefaultHashService;
import org.apache.shiro.crypto.hash.HashRequest;
import org.apache.shiro.crypto.hash.HashService;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.SimpleByteSource;

/**
 * Created by 18057046 on 2018/7/27.
 */
public class ShiroEncryptUserUtil {

    private static final DefaultHashService hashService = new DefaultHashService();

    private static final AesCipherService aesCipherService = new AesCipherService();

    private static final String algorithmName = "SHA-256";

    private static final String publicSalt = "helloSha256PublicSalt";

    private static final int hashIterations = 5;

    static {
        hashService.setHashAlgorithmName(algorithmName);
        hashService.setGeneratePublicSalt(true);
        hashService.setHashIterations(hashIterations);
        aesCipherService.setKeySize(128);
    }

    private User encryptUser(User user) {
        User resultUser = new User();
        // 随机盐对象
        SecureRandomNumberGenerator secureRandomNumberGenerator = new SecureRandomNumberGenerator();
        // 根据用户名以及随机生成的盐来拼接成密码盐
        String salt = user.getUserName() + user.getPassword() + secureRandomNumberGenerator.nextBytes().toHex();
        // 获取加密后的密码
        HashService hashService = getHashService(salt);
        // 使用非对称加密sha256加密用户密码
        String encryptedUserPassword = encryptStr(hashService, user.getPassword());
        // 设置密码盐
        resultUser.setSalt(salt);
        // 设置加密后的用户密码
        resultUser.setPassword(encryptedUserPassword);    //设置加密过后的密码
        return resultUser;
    }

    private HashService getHashService(String privateSalt) {
        hashService.setPrivateSalt(new SimpleByteSource(privateSalt));
        return hashService;
    }

    private String encryptStr(HashService hashService, String unencryptStr) {
        return hashService.computeHash(getHashRequest(unencryptStr)).toHex();
    }

    private HashRequest getHashRequest(String unencryptStr) {
        return new HashRequest.Builder().setAlgorithmName(algorithmName).setSource(ByteSource.Util.bytes(unencryptStr)).setSalt(publicSalt).setIterations(hashIterations).build();
    }
}
