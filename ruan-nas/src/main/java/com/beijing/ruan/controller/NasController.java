package com.beijing.ruan.controller;

import com.beijing.ruan.model.DownLoadFileFromUrl;
import com.beijing.ruan.model.FileInfo;
import com.beijing.ruan.model.ReturnObject;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadPoolExecutor;

@RestController
@CrossOrigin
public class NasController {

    /**
     * 文件夹
     */
    private static final String DIRECTORY = "directory";
    /**
     * 文件
     */
    private static final String FILE = "file";

    /**
     * 根路径
     */
    private static final String ROOT_DIRECTORY = "/";

    /**
     * 文件夹
     */
    private static final String DIRECTORY_NAME = "文件夹";

    @Autowired
    private ThreadPoolExecutor executor;

    @RequestMapping("/multipartFile")
    public ReturnObject uploadFile(@RequestParam("file") MultipartFile multipartFile, @RequestParam String dir) throws Exception {
        ReturnObject returnObject = new ReturnObject();
        File filePath = new File(dir);
        while (!filePath.exists()) {
            filePath.mkdirs();
        }
        String filename = multipartFile.getOriginalFilename();
        if (filePath.exists()) {
            File[] files = filePath.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.getName().equals(filename)) {
                        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                        StringBuilder builder = new StringBuilder();
                        builder.append(filename.substring(0, filename.lastIndexOf(".")))
                                .append("_")
                                .append(timestamp.toString())
                                .append(filename.substring(filename.lastIndexOf(".")));
                        filename = builder.toString();
                    }
                }
            }

        }
        File file = new File(filePath, Objects.requireNonNull(filename));
        multipartFile.transferTo(file);
        returnObject.setSuccess(true);
        returnObject.setData(file.getPath());
        return returnObject;
    }

    @RequestMapping("/downloadFileInfo")
    public void downloadFileInfo(@RequestParam String filePath, HttpServletResponse response) throws UnsupportedEncodingException {
        File file = new File(filePath);
        FileInputStream fileInputStream = null;
        OutputStream outputStream = null;
        String fileName = filePath.substring(filePath.lastIndexOf("\\") + 1);
        response.addHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("gb2312"), "ISO8859-1"));
        response.addHeader("Content-Length", "" + file.length());
        response.setContentType("application/octet-stream");
        if (file.exists()) {
            try {
                fileInputStream = new FileInputStream(file);
                outputStream = response.getOutputStream();
                byte[] bytes = new byte[8092];
                int len = 0;
                while ((len = fileInputStream.read(bytes)) > 0) {
                    outputStream.write(bytes, 0, len);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (outputStream != null) {
                    try {
                        outputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (fileInputStream != null) {
                    try {
                        fileInputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @RequestMapping("/openNetDisk")
    public ReturnObject openNetDisk(@RequestParam String dir) throws Exception {
        File[] files;
        if (!ROOT_DIRECTORY.equals(dir)) {
            File file = new File(dir);
            files = file.listFiles();
        } else {
            files = File.listRoots();
        }

        List<FileInfo> list = new ArrayList<>();
        if (files != null && files.length > 0) {
            for (File file : files) {
                FileInfo filIinfo = new FileInfo();
                filIinfo.setFileName(file.getName());
                filIinfo.setFilePath(file.getPath());
                filIinfo.setFileSize(file.length());
                filIinfo.setLastModifiedTime(new Date(file.lastModified()));
                if (file.isDirectory()) {
                    filIinfo.setFileType(DIRECTORY);
                    filIinfo.setFileExt(DIRECTORY_NAME);
                } else {
                    filIinfo.setFileType(FILE);
                    String fileName = file.getName();
                    filIinfo.setFileExt(fileName.substring(fileName.lastIndexOf(".") + 1));
                }
                list.add(filIinfo);
            }
        }
        ReturnObject returnObject = new ReturnObject();
        returnObject.setSuccess(true);
        returnObject.setData(list);
        return returnObject;

    }

    @RequestMapping("/downloadFileFromUrl")
    public ReturnObject downloadFileFromUrl(@RequestBody DownLoadFileFromUrl downLoadFileFromUrl) {
        ReturnObject returnObject = new ReturnObject();
        returnObject.setSuccess(true);
        String dir = downLoadFileFromUrl.getDir();
        String url = downLoadFileFromUrl.getUrl();
        if (StringUtils.isEmpty(dir)) {
            returnObject.setMessage("目标路径不能为空");
            return returnObject;
        }
        if (StringUtils.isEmpty(url)) {
            returnObject.setMessage("目标url不能为空");
            return returnObject;
        }
        String fileName = getFileNameFromUrl(url);
        final String directory = dir.endsWith(ROOT_DIRECTORY) ? dir : dir + ROOT_DIRECTORY;
        File file = new File(directory + fileName);
        File dirFile = new File(dir);
        File[] files = dirFile.listFiles();
        if (files != null) {
            for (File fi : files) {
                if (fi.getName().equals(fileName)) {
                    returnObject.setMessage("目标文件已存在");
                    return returnObject;
                }
            }
        }
        executor.execute(() -> {
            URL httpUrl;
            try {
                httpUrl = new URL(url);
                FileUtils.copyURLToFile(httpUrl, file);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        returnObject.setMessage("下载中,请稍后查看");
        return returnObject;
    }

    private static String getFileNameFromUrl(String url) {
        String name = System.currentTimeMillis() + ".X";
        int index = url.lastIndexOf("/");
        if (index > 0) {
            name = url.substring(index + 1);
            if (name.trim().length() > 0) {
                return name;
            }
        }
        return name;
    }

}
