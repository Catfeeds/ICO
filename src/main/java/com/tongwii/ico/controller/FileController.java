package com.tongwii.ico.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tongwii.ico.core.Result;
import com.tongwii.ico.exception.StorageFileNotFoundException;
import com.tongwii.ico.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLConnection;
import java.nio.file.Path;

/**
 * 上传文件
 *
 * @author Zeral
 * @date 2017-08-14
 */
@RestController
@RequestMapping("/file")
public class FileController {
    @Autowired
    private FileService fileService;


    /**
     * 上传文件
     */
    @PostMapping()
    public Result handleFileUpload(@RequestParam("file") MultipartFile file) {
        Path path = fileService.store(file);
        /*JSONObject object = new JSONObject();
        String url = MvcUriComponentsBuilder.fromMethodName(FileController.class,
                "getFile", path.getFileName().toString(), response).build().toString();
        object.put("path", path.toString());*/
        return Result.successResult().add("path", "\\"+path.toString());
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }

    /**
     * 上传文件多个文件
     */
    @PostMapping("/files")
    public Result handleFilesUpload(@RequestParam("files") MultipartFile [] files, HttpServletResponse response) {
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < files.length; i++) {
            Path path = fileService.store(files[i]);
            JSONObject object = new JSONObject();
            /*Path path = fileService.load(files[i].getOriginalFilename());
            JSONObject object = new JSONObject();
            String url = MvcUriComponentsBuilder.fromMethodName(FileController.class,
                    "getFile", path.getFileName().toString(), response).build().toString();*/
            object.put("path", "\\" + path.toString());
            jsonArray.add(object);
        }
        return Result.successResult(jsonArray);
    }

    /**
     * 获取文件
     */
    @GetMapping("/{filename:.+}")
    public void getFile(@PathVariable String filename, HttpServletResponse response) throws IOException {

        Resource file = fileService.loadAsResource(filename);

        File files = file.getFile();
        String mimeType= URLConnection.guessContentTypeFromName(files.getName());
        response.setContentType(mimeType);
        response.setHeader("Content-Disposition", String.format("inline; filename=\"" + files.getName() +"\""));
        response.setContentLength((int)files.length());

        FileCopyUtils.copy(file.getInputStream(), response.getOutputStream());
        response.reset();
    }
}
