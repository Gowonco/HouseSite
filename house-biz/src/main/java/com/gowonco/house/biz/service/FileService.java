package com.gowonco.house.biz.service;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.io.Files;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.List;

/**
 * 文件服务
 *
 * @author gowonco
 * @date 2019-07-24 15:35
 */
@Service
public class FileService {

    @Value("${file.path}")
    private String filePath;

    public List<String> getImgPath(List<MultipartFile> files) {
        if (Strings.isNullOrEmpty(filePath)) {
            filePath = getResourcePath();
        }
        List<String> paths = Lists.newArrayList();
        files.forEach(file -> {
            File localFile = null;
            if (!file.isEmpty()) {
                try {
                    localFile = saveToLocal(file, filePath);
                    String path = StringUtils.substringAfterLast(localFile.getAbsolutePath(),filePath);
                    paths.add(path);
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new IllegalArgumentException(e);
                }
            }
        });
        return paths;
    }

    private File saveToLocal(MultipartFile file, String path) throws IOException {
        File newFile = new File(filePath + "/" + Instant.now().getEpochSecond() + "/" + file.getOriginalFilename());
        if(!newFile.exists()) {
            newFile.getParentFile().mkdirs();
            newFile.createNewFile();
        }
        Files.write(file.getBytes(),newFile);
        return newFile;
    }

    public static String getResourcePath(){
        File file = new File(".");
        String absolutePath = file.getAbsolutePath();
        return absolutePath;
    }
}
