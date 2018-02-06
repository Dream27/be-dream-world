package com.dream.dw.util;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import lombok.Setter;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Component
@Setter
public class FastdfsUtils {

    @Autowired
    private FastFileStorageClient fastFileStorageClient;

    private static FastdfsUtils fastdfsUtils;

    @Value("${fdfs.fileurl}")
    private String webServerUrl;

    @PostConstruct
    private void init() {
        fastdfsUtils = this;
    }

    public static String uploadFile(MultipartFile file) {
        try{
            StorePath storePath = fastdfsUtils.fastFileStorageClient.uploadFile(file.getInputStream(), file.getSize(),
                    FilenameUtils.getExtension(file.getOriginalFilename()), null);
            return getAccessUrl(storePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String uploadFile(File file) {
        try{
            FileInputStream inputStream = new FileInputStream (file);
            StorePath storePath = fastdfsUtils.fastFileStorageClient.uploadFile(inputStream, file.length(),
                    FilenameUtils.getExtension(file.getName()), null);
            return getAccessUrl(storePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String getAccessUrl(StorePath storePath) {
        return fastdfsUtils.webServerUrl + storePath.getFullPath();
    }

    public static boolean deleteFile(String key) {
        try {
            fastdfsUtils.fastFileStorageClient.deleteFile(key);
            //StorePath storePath = StorePath.praseFromUrl(fileUrl);
            //fastdfsUtils.fastFileStorageClient.deleteFile(storePath.getGroup(), storePath.getPath());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
