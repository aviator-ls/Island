package com.aviator.island.utils;

import com.google.common.collect.Sets;
import com.aviator.island.constants.Constants;
import com.aviator.island.exception.UnsupportedUploadMimeTypeException;
import com.aviator.island.exception.UploadStaticException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Set;

/**
 * Created by aviator_ls on 2018/8/27.
 */
@Slf4j
public class UploadUtil {

    private static final String staticPath = System.getProperty("user.dir").replace("bin", "webapps") + File.separator + "upload";

    private static final Set<String> imgMimeTypeSet = Sets.newHashSet(MimeTypeUtils.IMAGE_GIF_VALUE, MimeTypeUtils.IMAGE_JPEG_VALUE, MimeTypeUtils.IMAGE_PNG_VALUE);

    public static String upload(MultipartFile file) {
        return upload(file, null, false, Constants.UPLOAD_CLASSIFY.IMG.DEFAULT);
    }

    public static String upload(MultipartFile file, String classify) {
        return upload(file, null, false, classify);
    }

    public static String upload(MultipartFile file, String serverPath, String classify) {
        return upload(file, serverPath, false, classify);
    }

    /**
     * 文件上传
     *
     * @param file       上传的文件
     * @param serverPath 服务器路径
     * @param reName     是否重写上传文件名称
     * @param classify
     * @return
     */
    public static String upload(MultipartFile file, String serverPath, boolean reName, String classify) {
        String fileName = file.getOriginalFilename();
        String contentType = file.getContentType();// 上传的文件类型
        if (StringUtils.isBlank(fileName)) {
            throw new UploadStaticException("fileName contentType is blank");
        }
        if (StringUtils.isBlank(contentType)) {
            throw new UploadStaticException("file's contentType is blank");
        }
        String path;// 相对路径
        String storePath;// 最终存储路径
        if (imgMimeTypeSet.contains(contentType)) {
            path = getImgServerPath(classify);
        } else {
            log.error("unsupported upload mimeType:{}", contentType);
            throw new UnsupportedUploadMimeTypeException();
        }
        serverPath += path;
        // 是否重写文件名称
        if (reName) {
            fileName = reName(fileName);
        }
        // 如果路径不存在则创建路径
        File serverPathFile = new File(serverPath);
        if (!serverPathFile.exists()) {
            serverPathFile.mkdirs();
        }
        storePath = serverPath + fileName;
        try {
            file.transferTo(new File(storePath));
        } catch (IOException e) {
            throw new UploadStaticException("upload to static server error", e);
        }
        log.debug("upload storePath:{}", storePath);
        return File.separator + "upload" + File.separator + path + fileName;
    }

    private static String getImgServerPath(String classify) {
        return File.separator + "img" + File.separator + classify + File.separator;
    }

    private static String reName(String fileName) {
        String suffix = fileName.substring(fileName.lastIndexOf('.'));
        return DateUtil.getCurrentDateTimeString(DateUtil.FORMAT_PATTERN_1) + suffix;
    }

    public static void main(String[] args) {
        System.out.println(new File(staticPath).exists());
    }

}
