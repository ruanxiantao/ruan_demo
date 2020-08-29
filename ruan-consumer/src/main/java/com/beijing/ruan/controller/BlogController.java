package com.beijing.ruan.controller;

import com.beijing.ruan.api.BlogService;
import com.beijing.ruan.entity.Blog;
import com.beijing.ruan.entity.FileInfo;
import com.beijing.ruan.entity.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.rmi.server.ExportException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

@Controller
public class BlogController {

    /**
     * 文件夹
     */
    private static final String DIRECTORY = "directory";
    /**
     * 文件
     */
    private static final String FILE = "file";

    /**
     * 调用博客服务
     */
    @Autowired
    private BlogService blogService;

    /**
     * 插入文件
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/insertFileInfo")
    public String insertFileInfo() throws Exception {













        ExecutorService es = Executors.newFixedThreadPool(100);
        CopyOnWriteArrayList<Object> list = new CopyOnWriteArrayList<>();
        list.add(1);
//        es.execute();
//        es.submit()
//        new Thread()
        FutureTask<Integer> task = new FutureTask<Integer>(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return 100;
            }
        });
//        es.submit()
//        es.execute(task);
//        Future<Integer> future = es.submit(new Callable<Integer>() {
//            @Override
//            public Integer call() throws Exception {
//                return 200;
//            }
//        });
        new Thread(task).start();
//        Integer integer = future.get();
        Integer integer = task.get();
        return String.valueOf(integer);
//        return "success";
//        return blogService.insertPerson();
    }
    @RequestMapping("/he")
    public String he() throws Exception {
        return "forward:/hello";
    }
    @RequestMapping("/hi")
    public String hi() throws Exception {
        return "redirect:/hello";
    }
    @ResponseBody
    @RequestMapping("/insertBlog")
    public ReturnObject insertBlog(@RequestBody Blog blog) throws Exception {
        blogService.insertBlog(blog);
        ReturnObject returnObject = new ReturnObject();
        returnObject.setSuccess(true);
        return returnObject;
    }
    @ResponseBody
    @RequestMapping("/multipartFile")
    public ReturnObject uploadFile(@RequestParam("file") MultipartFile multipartFile, @RequestParam(required = false) String dir) throws Exception {
        String osName = System.getProperty("os.name");
        ReturnObject returnObject = new ReturnObject();
        File file = null;
        returnObject.setSuccess(true);
        if(osName.toLowerCase().contains("win")){
            File filePath = new File("D:/uploadFile");
            if(!filePath.exists()){
                filePath.mkdirs();
            }
            file = new File(filePath, multipartFile.getOriginalFilename());
        }else if (osName.toLowerCase().contains("linux")){
            File filePath = new File("/home/filePath");
            if(!filePath.exists()){
                filePath.mkdirs();
            }
            file = new File(filePath, multipartFile.getOriginalFilename());
        }else {
            throw new Exception("未知操作系统");
        }
        if(dir != null){
            file = new File(dir, multipartFile.getOriginalFilename());
        }
        multipartFile.transferTo(file);
        returnObject.setData(file.toString());
        return returnObject;
    }

    @ResponseBody
    @RequestMapping("/downloadFileInfo")
    public void downloadFileInfo(@RequestParam String filePath, HttpServletResponse response) throws UnsupportedEncodingException {
        File file = new File(filePath);
        FileInputStream fileInputStream = null;
        OutputStream outputStream = null;
        String fileName = filePath.substring(filePath.lastIndexOf("\\") + 1);
        response.addHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("gb2312"),"ISO8859-1"));
        response.addHeader("Content-Length", "" + file.length());
        response.setContentType("application/octet-stream");
        if(file.exists()){
            try {
                fileInputStream = new FileInputStream(file);
                outputStream = response.getOutputStream();
                byte[] bytes = new byte[8092];
                int len = 0;
                while ( (len = fileInputStream.read(bytes)) > 0){
                    outputStream.write(bytes, 0, len);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if(outputStream != null){
                    try {
                        outputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if(fileInputStream != null){
                    try {
                        fileInputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @ResponseBody
    @RequestMapping("openNetDisk")
    public ReturnObject openNetDisk(@RequestParam String dir) throws Exception {
        Boolean winOrLinux = isWinOrLinux();
        File file = null;
        if(winOrLinux == null){
            return null;
        }else if (!winOrLinux){
            file = new File("D:/uploadFile");
        }else if (winOrLinux){
            file = new File("/home/filePath");
        }else {
            throw new Exception("未知的系统");
        }
        if(!"/".equals(dir)){
            file = new File(dir);
        }
        File[] files = file.listFiles();
        List<FileInfo> list = new ArrayList<>();
        if(files != null && files.length > 0){
            for (File fileInfo : files) {
                FileInfo info = new FileInfo();
                info.setFileName(fileInfo.getName());
                info.setFilePath(fileInfo.getPath());
                info.setFileSize(fileInfo.length());
                info.setLastModifiedTime(new Date(fileInfo.lastModified()));
                if(fileInfo.isDirectory()){
                    info.setFileType(DIRECTORY);
                }else {
                    info.setFileType(FILE);
                }
                list.add(info);
            }
        }
        ReturnObject returnObject = new ReturnObject();
        returnObject.setSuccess(true);
        returnObject.setData(list);
        return returnObject;

    }

    private Boolean isWinOrLinux(){
        String osName = System.getProperty("os.name");
        if(osName.toLowerCase().contains("win")){
            return false;
        }else if (osName.toLowerCase().contains("linux")){
            return true;
        }else {
            return null;
        }
    }
}
