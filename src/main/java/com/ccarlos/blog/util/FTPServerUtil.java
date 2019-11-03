package com.ccarlos.blog.util;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.apache.commons.net.ftp.FTPClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

/**
 * @description: ftp文件上传工具类
 * @author: ccarlos
 * @date: 2019/5/17 19:43
 */
@Slf4j
@Getter
@Setter
@Component
public class FTPServerUtil {

    private FTPClient ftpClient;

    /**
     * @description: 上传文件
     * @author: ccarlos
     * @date: 2019/5/17 20:11
     * @param: fileList 文件列表
     * @return: boolean
     */
    public static boolean uploadFile(List<File> fileList) throws IOException {
        FTPServerUtil ftpServerUtil = new FTPServerUtil();
        log.info("开始连接ftp服务器");
        boolean result = ftpServerUtil.uploadFile("img", fileList);
        log.info("结束上传,上传结果:{}", result);
        return result;
    }

    /**
     * @description: 上传文件重载方法
     * @author: ccarlos
     * @date: 2019/5/17 20:28
     * @param: remotePath 路径
     * @param: fileList 文件列表
     * @return: boolean
     */
    private boolean uploadFile(String remotePath, List<File> fileList) throws IOException {
        boolean uploaded = false;
        FileInputStream fis = null;
        String severIp = PropertiesUtil.getProperty("ftp.serverIp");
        int port = Integer.valueOf(PropertiesUtil.getProperty("ftp.port"));
        String user = PropertiesUtil.getProperty("ftp.user");
        String pass = PropertiesUtil.getProperty("ftp.pass");
        //连接FTP服务器
        if (connectFTPServer(severIp, port, user, pass)) {
            try {
                ftpClient.changeWorkingDirectory(remotePath);
                ftpClient.setBufferSize(1024);
                ftpClient.setControlEncoding("UTF-8");
                ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
                ftpClient.enterLocalPassiveMode();
                log.info("连接ftp服务器成功");
                for (File file : fileList) {
                    fis = new FileInputStream(file);
                    ftpClient.storeFile(file.getName(), fis);
                }
                uploaded = true;
            } catch (IOException e) {
                log.error("上传文件发生异常，异常原因:{}，异常描述:{}", e.getMessage(), e.toString(), e);
            } finally {
                fis.close();
                ftpClient.disconnect();
            }
        } else {
            log.info("连接ftp服务器失败");
        }
        return uploaded;
    }

    /**
     * @description: 连接ftp文件服务器
     * @author: ccarlos
     * @date: 2019/5/17 20:31
     * @return: boolean
     */
    private boolean connectFTPServer(String serverIp, int port, String user, String pass) {
        boolean isSuccess = false;
        ftpClient = new FTPClient();
        try {
            ftpClient.connect(serverIp);
            isSuccess = ftpClient.login(user, pass);
        } catch (IOException e) {
            log.error("连接FTP服务器异常，异常原因:{}，异常描述:{}", e.getMessage(), e.toString(), e);
        }
        return isSuccess;
    }
}
