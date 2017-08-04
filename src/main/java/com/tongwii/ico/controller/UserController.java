package com.tongwii.ico.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tongwii.ico.core.Result;
import com.tongwii.ico.exception.EmailExistException;
import com.tongwii.ico.exception.StorageFileNotFoundException;
import com.tongwii.ico.model.User;
import com.tongwii.ico.service.FileService;
import com.tongwii.ico.service.UserService;
import com.tongwii.ico.util.ContextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLConnection;
import java.nio.file.Path;
import java.util.List;

/**
* Created by Zeral on 2017-08-02.
*/
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private FileService fileService;

    @PostMapping
    public Result add(@RequestBody User user) {
        userService.save(user);
        return Result.successResult();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        userService.deleteById(id);
        return Result.successResult();
    }

    @PutMapping
    public Result update(@RequestBody User user) {
        userService.update(user);
        return Result.successResult();
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Integer id) {
        User user = userService.findById(id);
        return Result.successResult(user);
    }

    @GetMapping
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<User> list = userService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return Result.successResult(pageInfo);
    }

    @PostMapping("register")
    public Result register(@RequestBody User user) {
        userService.register(user);
        return Result.successResult("注册成功");
    }

    @ExceptionHandler(EmailExistException.class)
    public Result handleStorageFileNotFound(EmailExistException e) {
        return Result.failResult(e.getMessage());
    }

    @PostMapping("/avator")
    public Result handleFileUpload(@RequestParam("file") MultipartFile file) {
        fileService.store(file);
        Path path = fileService.load(file.getOriginalFilename());
        JSONObject object = new JSONObject();
        String url = MvcUriComponentsBuilder.fromMethodName(UserController.class,
                "avatorFile", path.getFileName().toString()).build().toString();
        object.put("path", url);

        Integer userId = ContextUtils.getUserId();
        userService.userUploadAvator(userId, url);
        return Result.successResult(object);
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/avator/{filename:.+}")
    public void avatorFile(@PathVariable String filename, HttpServletResponse response) throws IOException {

        Resource file = fileService.loadAsResource(filename);

        File files = file.getFile();
        String mimeType= URLConnection.guessContentTypeFromName(files.getName());
        response.setContentType(mimeType);
        response.setHeader("Content-Disposition", String.format("inline; filename=\"" + files.getName() +"\""));
        response.setContentLength((int)files.length());
        InputStream inputStream = new BufferedInputStream(new FileInputStream(files));

        FileCopyUtils.copy(inputStream, response.getOutputStream());
    }
}
