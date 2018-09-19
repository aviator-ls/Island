package com.aviator.island.shiro;

import com.aviator.island.entity.sys.User;
import org.apache.shiro.crypto.AesCipherService;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.DefaultHashService;
import org.apache.shiro.crypto.hash.HashRequest;
import org.apache.shiro.util.ByteSource;

/**
 * Created by 18057046 on 2018/7/27.
 */
public class ShiroEncryptUserUtil {

    private static final DefaultHashService hashService = new DefaultHashService();

    private static final AesCipherService aesCipherService = new AesCipherService();

    private static final SecureRandomNumberGenerator secureRandomNumberGenerator = new SecureRandomNumberGenerator();

    private static final String algorithmName = "SHA-256";

    private static final String publicSalt = "encryptSalt";

    private static final int hashIterations = 5;

    static {
        hashService.setHashAlgorithmName(algorithmName);
        hashService.setGeneratePublicSalt(true);
        hashService.setHashIterations(hashIterations);
        aesCipherService.setKeySize(128);
    }

    public static User encryptUser(User user) {
        User resultUser = new User();
        // 随机盐对象
        String randomNumber = secureRandomNumberGenerator.nextBytes().toHex();
        // 根据用户名以及随机生成的盐来拼接成密码盐
        String salt = encryptStr(user.getUserName() + randomNumber, publicSalt, 1);
        // 使用非对称加密sha256加密用户密码
        String encryptedUserPassword = encryptStr(user.getPassword(), salt);
        // 设置密码盐
        resultUser.setSalt(salt);
        // 设置加密后的用户密码
        resultUser.setPassword(encryptedUserPassword);    //设置加密过后的密码
        return resultUser;
    }

    private static String encryptStr(String unEncryptStr, String salt) {
        return encryptStr(unEncryptStr, salt, hashIterations);
    }

    private static String encryptStr(String unEncryptStr, String salt, int hashIterations) {
        return hashService.computeHash(getHashRequest(unEncryptStr, salt, hashIterations)).toHex();
    }

    private static HashRequest getHashRequest(String unEncryptStr, String salt, int hashIterations) {
        return new HashRequest.Builder().setAlgorithmName(algorithmName).setSource(ByteSource.Util.bytes(unEncryptStr)).setSalt(salt).setIterations(hashIterations).build();
    }
}
