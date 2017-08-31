package com.tongwii.ico.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tongwii.ico.core.Result;
import com.tongwii.ico.model.ProjectWallet;
import com.tongwii.ico.model.TokenMoney;
import com.tongwii.ico.model.UserProjectInvestRecord;
import com.tongwii.ico.model.UserWallet;
import com.tongwii.ico.service.*;
import com.tongwii.ico.util.ContextUtils;
import com.tongwii.ico.util.OperatorRecordUtil;
import com.tongwii.ico.util.TokenMoneyEnum;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 *  用户项目投资记录
 *
 *  @date 2017-08-25
 */
@RestController
@RequestMapping("/userProjectInvestRecord")
public class UserProjectInvestRecordController {
    @Autowired
    private UserProjectInvestRecordService userProjectInvestRecordService;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private TokenMoneyService tokenMoneyService;
    @Autowired
    private ProjectWalletService projectWalletService;
    @Autowired
    private UserWalletService userWalletService;

    /***
     * 保存用户项目投资记录
     * @param userProjectInvestRecord
     * @return
     */
    @PostMapping
    @ResponseBody
    public Result add(@RequestBody UserProjectInvestRecord userProjectInvestRecord) {
        userProjectInvestRecordService.save(userProjectInvestRecord);
        OperatorRecordUtil.record("用户投资");
        return Result.successResult();
    }
    /***
     * 根据当前用户获取用户项目投资记录,包括项目钱包、用户钱包信息
     *
     * @param page
     * @param size
     * @return
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public Result findUserProjectInvestRecordByUserId(@RequestParam(required = true,defaultValue = "0") Integer page,
                                                      @RequestParam(required = true,defaultValue = "1") Integer size){
        PageHelper.startPage(page, size);
        Integer userId = ContextUtils.getUserId();
        List<UserProjectInvestRecord> userProjectInvestRecordList = userProjectInvestRecordService.findByUserId(userId);
        if(CollectionUtils.isNotEmpty(userProjectInvestRecordList)){
            for(int i=0;i<userProjectInvestRecordList.size();i++){
                // 根据projectId获取projictInfo
                userProjectInvestRecordList.get(i).setProject(projectService.findById(userProjectInvestRecordList.get(i).getProjectId()));
                // 查询币种信息
                TokenMoney tokenMoney = tokenMoneyService.findById(userProjectInvestRecordList.get(i).getTokenId());
                userProjectInvestRecordList.get(i).setTokenMoney(tokenMoney);
                // 根据tokenMoneyId与projectId查询projectWallet
                ProjectWallet projectWallet = projectWalletService.findWalletByCionId(tokenMoney.getId(),userProjectInvestRecordList.get(i).getProjectId());
                userProjectInvestRecordList.get(i).setProjectWallet(projectWallet);
                // 根据tokenMoneyId与userId查询userWallet
                UserWallet userWallet = userWalletService.findWalletByCionId(tokenMoney.getId(),userProjectInvestRecordList.get(i).getUserId());
                userProjectInvestRecordList.get(i).setUserWallet(userWallet);
            }
            PageInfo pageInfo = new PageInfo(userProjectInvestRecordList);
            return Result.successResult(pageInfo);
        }else {
            return Result.failResult("暂无用户交易记录!");
        }
    }

    /***
     * 根据项目获取用户项目投资记录
     * @param page
     * @param size
     * @return
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/project/{projectId}")
    public Result findUserProjectInvestRecordByProjectId(@PathVariable("projectId")Integer projectId,
                                                         @RequestParam(required = true,defaultValue = "0") Integer page,
                                                         @RequestParam(required = true,defaultValue = "1") Integer size){
        PageHelper.startPage(page, size);
        List<UserProjectInvestRecord> userProjectInvestRecordList = userProjectInvestRecordService.findByProjectId(projectId);
        if(CollectionUtils.isNotEmpty(userProjectInvestRecordList)){
            for (UserProjectInvestRecord userProjectInvestRecord : userProjectInvestRecordList) {
                // 根据projectId获取projictInfo
                userProjectInvestRecord.setProject(projectService.findById(userProjectInvestRecord.getProjectId()));
                // 查询币种信息
                TokenMoney tokenMoney = tokenMoneyService.findById(userProjectInvestRecord.getTokenId());
                userProjectInvestRecord.setTokenMoney(tokenMoney);
                // 根据tokenMoneyId与projectId查询projectWallet
                ProjectWallet projectWallet = projectWalletService.findWalletByCionId(tokenMoney.getId(),userProjectInvestRecord.getProjectId());
                userProjectInvestRecord.setProjectWallet(projectWallet);
                // 根据tokenMoneyId与userId查询userWallet
                UserWallet userWallet = userWalletService.findWalletByCionId(tokenMoney.getId(),userProjectInvestRecord.getUserId());
                userProjectInvestRecord.setUserWallet(userWallet);
            }
            PageInfo pageInfo = new PageInfo(userProjectInvestRecordList);
            return Result.successResult(pageInfo);
        }else {
            return Result.failResult("暂无用户交易记录!");
        }
    }


    /***
     * 根据当前用户获取用户项目投资记录简单信息
     *
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/userInvestRecords")
    public Result findUserProjectInvestRecord(@RequestParam(required = true,defaultValue = "1") Integer page,
                                              @RequestParam(required = true,defaultValue = "5") Integer size){
        PageHelper.startPage(page, size);
        Integer userId = ContextUtils.getUserId();
        List<UserProjectInvestRecord> userProjectInvestRecordList = userProjectInvestRecordService.findByUserId(userId);
        if(CollectionUtils.isNotEmpty(userProjectInvestRecordList)){
            for(int i=0;i<userProjectInvestRecordList.size();i++){
                // 根据projectId获取projictInfo
                userProjectInvestRecordList.get(i).setProject(projectService.findById(userProjectInvestRecordList.get(i).getProjectId()));
                // 查询币种信息
                TokenMoney tokenMoney = tokenMoneyService.findById(userProjectInvestRecordList.get(i).getTokenId());
                userProjectInvestRecordList.get(i).setTokenMoney(tokenMoney);
            }
            PageInfo pageInfo = new PageInfo(userProjectInvestRecordList);
            return Result.successResult(pageInfo);
        }else {
            return Result.failResult("暂无用户交易记录!");
        }
    }

    /***
     * 根据当前用户和当前项目查看投资记录
     *
     * @return
     */
    @GetMapping("/investRecords/project/{id}")
    public Result findUserProjectInvestRecordByUserIdAndProjectId(@PathVariable("id") Integer projectId){
        Integer userId = ContextUtils.getUserId();
        List<UserProjectInvestRecord> userProjectInvestRecordList = userProjectInvestRecordService.findByUserIdAndProjectId(userId, projectId);
        if(CollectionUtils.isNotEmpty(userProjectInvestRecordList)){
            for (UserProjectInvestRecord userProjectInvestRecord : userProjectInvestRecordList) {
                // 根据projectId获取projictInfo
                userProjectInvestRecord.setProject(projectService.findById(userProjectInvestRecord.getProjectId()));
                // 查询币种信息
                TokenMoney tokenMoney = tokenMoneyService.findById(userProjectInvestRecord.getTokenId());
                userProjectInvestRecord.setTokenMoney(tokenMoney);
            }
            return Result.successResult(userProjectInvestRecordList);
        }else {
            return Result.failResult("暂无用户交易记录!");
        }
    }

    /***
     * 根据用户代币投资总和
     *
     * @return
     */
    @GetMapping("/sumRecord")
    public Result findUserInvestSumRecord(){
        Integer userId = ContextUtils.getUserId();
        List<UserProjectInvestRecord> records = userProjectInvestRecordService.findByUserId(userId);
        TokenMoney BTCTokenMoney = tokenMoneyService.findByENShortName(TokenMoneyEnum.BTC.toString());
        TokenMoney ETCTokenMoney = tokenMoneyService.findByENShortName(TokenMoneyEnum.ETH.toString());
        Double BTCSumRecord = 0.0;
        Double ETHSumRecord = 0.0;
        if(CollectionUtils.isNotEmpty(records)) {
            for (UserProjectInvestRecord record : records) {
                if(record.getTokenId().equals(BTCTokenMoney.getId())) {
                    BTCSumRecord += Objects.isNull(record.getInvestValue()) ? 0.0 : record.getInvestValue();
                } else if(record.getTokenId().equals(ETCTokenMoney.getId())) {
                    ETHSumRecord += Objects.isNull(record.getInvestValue()) ? 0.0 :record.getInvestValue();
                }
            }
            return Result.successResult("查询成功").add("BTCSumRecord", BTCSumRecord).add("ETHSumRecord", ETHSumRecord);
        } else {
            return Result.failResult("用户无交易记录");
        }
    }

}
