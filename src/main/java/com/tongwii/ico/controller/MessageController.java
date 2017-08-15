package com.tongwii.ico.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tongwii.ico.core.Result;
import com.tongwii.ico.model.Message;
import com.tongwii.ico.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
        List<Message> list = messageService.findOfficalMessages();
        //分离公告类信息与新闻信息
        List<Message> notifyMessages = new ArrayList<>();
        List<Message> newsMessages = new ArrayList<>();
        try {
            for(int i=0; i<list.size(); i++){
                // 获取公告信息
                if(list.get(i).getType() == NOTIFYMESSAGE){
                    notifyMessages.add(list.get(i));
                }
                // 获取新闻信息
                if(list.get(i).getType() == NEWSMESSAGE){
                    newsMessages.add(list.get(i));
                }
            }
            PageInfo pageInfo1 = new PageInfo(notifyMessages);
            PageInfo pageInfo2 = new PageInfo(newsMessages);
            return Result.successResult("获取信息成功!").add("newsMessages", pageInfo2).add("notifyMessages", pageInfo1);
        }catch (Exception e){
            return Result.errorResult("获取信息失败!");
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
}
