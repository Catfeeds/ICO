package com.tongwii.ico.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tongwii.ico.core.Result;
import com.tongwii.ico.model.Message;
import com.tongwii.ico.model.User;
import com.tongwii.ico.service.MessageService;
import com.tongwii.ico.util.ContextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
* Created by Zeral on 2017-08-15.
*/
@RestController
@RequestMapping("/message")
public class MessageController {
    @Autowired
    private MessageService messageService;

    private static int NOTIFYMESSAGE = 2;
    private static int NEWSMESSAGE = 1;
    @PostMapping
    public Result add(@RequestBody Message message) {
        messageService.save(message);
        return Result.successResult();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        messageService.deleteById(id);
        return Result.successResult();
    }

    @PutMapping
    public Result update(@RequestBody Message message) {
        messageService.update(message);
        return Result.successResult();
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Integer id) {
        Message message = messageService.findById(id);
        return Result.successResult(message);
    }

    @GetMapping
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<Message> list = messageService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return Result.successResult(pageInfo);
    }
    // 获取所有正常状态下的message信息
    @GetMapping("/getOfficalMessage")
    public Result officalMessageList(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "1") Integer size) {
        PageHelper.startPage(page, size);
        try {
            // 获取新闻消息列表
            List<Message> newsMessages = messageService.findMessagesByType(NEWSMESSAGE);
            PageInfo pageInfo = new PageInfo(newsMessages);
            return Result.successResult("获取信息成功!").add("newsMessages", pageInfo).add("notifyMessages", messageService.findMessagesByType(NOTIFYMESSAGE));
        }catch (Exception e){
            return Result.errorResult("获取信息失败!");
        }
    }
    // 获取新闻消息
    @GetMapping("/getNotifyMessage")
    public Result getNotifyMessage() {
        try{
            List<Message> list = messageService.findMessagesByType(NOTIFYMESSAGE);
            return Result.successResult("获取信息成功!").add("notifyMessages", list);
        }catch (Exception e){
            return Result.failResult("公告信息获取失败!");
        }

    }

    // 获取新闻消息
    @GetMapping("/getNewsMessage")
    public Result getNewsMessage(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "1") Integer size) {
        PageHelper.startPage(page, size);
        List<Message> list = messageService.findMessagesByType(NEWSMESSAGE);
        PageInfo pageInfo = new PageInfo(list);
        return Result.successResult("获取信息成功!").add("newsMessages", pageInfo);
    }
    // 添加新闻消息
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/insertMessage")
    public Result insertMessage(@RequestBody Message message) {
        User user = ContextUtils.getUser();
        message.setState(1);
        message.setCreateUser(user);
        messageService.save(message);
        return Result.successResult("消息添加成功!");
    }
}
