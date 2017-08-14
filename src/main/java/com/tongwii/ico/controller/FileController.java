package com.tongwii.ico.controller;

import com.alibaba.fastjson.JSONObject;
import com.tongwii.ico.core.Result;
import com.tongwii.ico.exception.StorageFileNotFoundException;
import com.tongwii.ico.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
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
     * 上传头像
     */
    @PostMapping()
    @ResponseBody
    public Result handleFileUpload(@RequestParam("file") MultipartFile file, HttpServletResponse response) {
        fileService.store(file);
        Path path = fileService.load(file.getOriginalFilename());
        JSONObject object = new JSONObject();
        String url = MvcUriComponentsBuilder.fromMethodName(FileController.class,
                "getFile", path.getFileName().toString(), response).build().toString();
        object.put("path", url);
        return Result.successResult(object);
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }

    /**
     * 获取头像文件
     */
    @GetMapping("/{filename:.+}")
    @ResponseBody
    public void getFile(@PathVariable String filename, HttpServletResponse response) throws IOException {

        org.springframework.core.io.Resource file = fileService.loadAsResource(filename);

        File files = file.getFile();
        String mimeType= URLConnection.guessContentTypeFromName(files.getName());
        response.setContentType(mimeType);
        response.setHeader("Content-Disposition", String.format("inline; filename=\"" + files.getName() +"\""));
        response.setContentLength((int)files.length());
        InputStream inputStream = new BufferedInputStream(new FileInputStream(files));

        FileCopyUtils.copy(inputStream, response.getOutputStream());
    }
}
