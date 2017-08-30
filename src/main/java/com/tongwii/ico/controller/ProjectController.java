package com.tongwii.ico.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tongwii.ico.core.Result;
import com.tongwii.ico.model.*;
import com.tongwii.ico.service.*;
import com.tongwii.ico.util.*;
import org.apache.commons.collections4.CollectionUtils;
import org.bitcoinj.core.Address;
import org.bitcoinj.core.Coin;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.params.MainNetParams;
import org.bitcoinj.params.TestNet3Params;
import org.spongycastle.util.encoders.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;

import static com.tongwii.ico.model.Project.State.END;
import static com.tongwii.ico.model.Project.State.NOW;
import static com.tongwii.ico.model.Project.State.UN_COMING;
import static com.tongwii.ico.model.Role.RoleCode.ADMIN;
import static com.tongwii.ico.model.TokenDetail.TokenDetailType.INPUT_CION;
import static com.tongwii.ico.model.TokenDetail.TokenDetailType.OUTPUT_ICON;

/**
* Created by Zeral on 2017-08-02.
*/
@RestController
@RequestMapping("/project")
public class ProjectController {
    @Resource
    private ProjectService projectService;
    @Autowired
    private TokenDetailService tokenDetailService;
    @Autowired
    private TokenMoneyService tokenMoneyService;
    @Autowired
    private UserService userService;
    @Autowired
    private ProjectWalletService projectWalletService;
    @Autowired
    private TransactionsService transactionsService;
    @Autowired
    private projectFileService projectFileService;

    @Value("${spring.profiles.active}")
    private String env;//当前激活的配置文件

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Result add(@RequestBody Project project) {
        projectService.save(project);
        // 生成项目钱包，保存钱包信息
        TokenMoney bitCoin = tokenMoneyService.findByENShortName(TokenMoneyEnum.BTC.name());
        ProjectWallet bitCoinWallet = new ProjectWallet();
        bitCoinWallet.setTokenMoneyId(bitCoin.getId());
        bitCoinWallet.setProjectId(project.getId());
        ECKey bitCoinKey = new ECKey();
        final NetworkParameters netParams;

        if (env.equals(CurrentConfigEnum.dev.toString())) {
            netParams = TestNet3Params.get();
        } else {
            netParams = MainNetParams.get();
        }

        Address addressFromKey = bitCoinKey.toAddress(netParams);

        bitCoinWallet.setWalletAddress(addressFromKey.toBase58());
        try {
            String key1 = RSAEncodeUtil.encrypt(Hex.toHexString(bitCoinKey.getPrivKeyBytes()));
            bitCoinWallet.setWalletPrivateKey(key1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        bitCoinWallet.setDes("比特币钱包");
        projectWalletService.save(bitCoinWallet);

        // 以太坊钱包
        TokenMoney ethMoney = tokenMoneyService.findByENShortName(TokenMoneyEnum.ETH.name());
        ProjectWallet ethWallet = new ProjectWallet();
        ethWallet.setTokenMoneyId(ethMoney.getId());
        ethWallet.setProjectId(project.getId());
        org.ethereum.crypto.ECKey ethKey = new org.ethereum.crypto.ECKey();
        ethWallet.setWalletAddress(Hex.toHexString(ethKey.getAddress()));
        try {
            String key2 = RSAEncodeUtil.encrypt(Hex.toHexString(ethKey.getPrivKeyBytes()));
            ethWallet.setWalletPrivateKey(key2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ethWallet.setDes("以太坊钱包");
        projectWalletService.save(ethWallet);
        OperatorRecordUtil.record("新增项目, 项目id" + project.getId());
        return Result.successResult(project);
    }

    /**
     * Update input token money of Project.
     *
     * @param id          the id
     * @param tokenDetail the token detail
     * @return the result
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}/inputTokenMoney")
    public Result updateInputTokenMoney(@PathVariable Integer id, @RequestParam("tokenDetail") TokenDetail tokenDetail) {
        try {
            // TODO 需要重写，目标代币为多个
//            projectService.updateInputTokenMoney(id, tokenDetail);
        } catch (Exception e) {
            return Result.errorResult("更新目标代币信息失败");
        }
        return Result.successResult("更新目标代币信息成功");
    }

    /**
     * Update out put token money of Project.
     *
     * @param id          the id
     * @param tokenDetail the token detail
     * @return the result
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}/outputTokenMoney")
    public Result updateOutPutTokenMoney(@PathVariable Integer id, @RequestParam("tokenDetail") TokenDetail tokenDetail) {
        try {
            projectService.updateOutputTokenMoney(id, tokenDetail);
        } catch (Exception e) {
            return Result.errorResult("更新发行代币信息失败");
        }
        return Result.successResult("更新发行代币信息成功");
    }

    /**
     * Update create user of Project.
     *
     * @param id   the id
     * @param user the user
     * @return the result
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}/createUser")
    public Result updateCreateUser(@PathVariable Integer id, @RequestParam("user") User user) {
        try {
            projectService.updateCreateUser(id, user);
        } catch (Exception e) {
            return Result.errorResult("更新用户信息失败");
        }
        OperatorRecordUtil.record("更新用户信息");
        return Result.successResult("更新用户信息成功");
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        projectService.deleteById(id);
        OperatorRecordUtil.record("删除项目，项目id" + id);
        return Result.successResult();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping
    public Result update(@RequestBody Project project) {
        projectService.update(project);
        OperatorRecordUtil.record("修改项目，项目id" + project.getId());
        return Result.successResult();
    }

    /**
     * @author Yamo ...
     * 查看项目详情
     * @param id the id
     * @return the result
     */
    @GetMapping("/{id}")
    public Result detail(@PathVariable Integer id) {
        try {
            Project project = projectService.findById(id);

            // 接受代币
            projectFile projectFile = projectFileService.findProjectFileByProjectId(id);
            // 首先需要通过项目id查询token_details表目标代币数据
            List<TokenDetail> inputTokenDetails = tokenDetailService.findByProjectIdAndType(id, INPUT_CION.getCode());
            if(CollectionUtils.isNotEmpty(inputTokenDetails)){
                for(int i=0; i<inputTokenDetails.size(); i++){
                    // 获取目标代币的代币详细信息
                    TokenMoney inputMoney = tokenMoneyService.findById(inputTokenDetails.get(i).getTokenMoneyId());
                    inputTokenDetails.get(i).setTokenMoney(inputMoney);
                }
                project.setInputTokenDetails(inputTokenDetails);
            }

            // 发行代币
            List<TokenDetail> outPutTokenDetails = tokenDetailService.findByProjectIdAndType(id, OUTPUT_ICON.getCode());
            if(CollectionUtils.isNotEmpty(outPutTokenDetails)) {
                TokenMoney inputMoney = tokenMoneyService.findById(outPutTokenDetails.get(0).getTokenMoneyId());
                outPutTokenDetails.get(0).setTokenMoney(inputMoney);
                project.setOutPutTokenDetail(outPutTokenDetails.get(0));
            }

            // 创建用户
            if(Objects.nonNull(project.getCreateUserId())) {
                User createUser = userService.findById(project.getCreateUserId());
                project.setCreateUser(createUser);
            }
            return Result.successResult().add("projectFile",projectFile).add("projectInfo",project);
        }catch (Exception e){
            return Result.errorResult("详情信息为空!");
        }
    }

    @GetMapping
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<Project> list = projectService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return Result.successResult(pageInfo);
    }

    /**
     * @author Yamo
     * 分类获取所有项目的数据
     * @return the result
     */
    @GetMapping("/findProjectsAndSort")
    public Result findProjectsAndSort() {
        List<Project> projectList = projectService.findOfficalProject();

        List<Project> ICOList = new ArrayList<>(); // ICO中的数据列表
        List<Project> willICOList = new ArrayList<>(); // 即将进行ICO中的数据列表
        List<Project> finishICOList = new ArrayList<>(); // 结束ICO的数据列表
        for (int i = 0; i < projectList.size(); i++) {
            Project p = projectList.get(i);
            Integer projectId = p.getId();
            // 获取项目图片
            try {
                String pictureUrl = projectFileService.findProjectFileByProjectId(projectId).getFileUrl();
                p.setPictureUrl(pictureUrl);
            } catch (Exception e) {
                p.setPictureUrl("");
            }

            // 判断时间，设定state的值
            //获取系统当前时间，获取项目的开始时间以及结束时间
            Date date = new Date();
            Date startTime = p.getStartTime();
            Date endTime = p.getEndTime();
            if(endTime.getTime()>date.getTime()){
                long interval = endTime.getTime()-date.getTime();
                System.out.println("两个时间相差"+interval +"秒");
            }

            //分离已结束的项目
            if (date.getTime()>endTime.getTime()) {
                p.setState(END.getState());
                finishICOList.add(p);
            }
            // 分离即将开始的项目
            if (date.getTime()<startTime.getTime()) {
                p.setState(UN_COMING.getState());
                willICOList.add(p);
            }
            // 分离正在进行中的项目
            if ((startTime.getTime()<date.getTime()) && (date.getTime()<endTime.getTime())) {
                p.setState(NOW.getState());
                ICOList.add(p);
            }
            update(p);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("ICO", ICOList);
        jsonObject.put("willICO", willICOList);
        jsonObject.put("finishICO", finishICOList);
        return Result.successResult(jsonObject);
    }

    /***
     * 根据BTC钱包地址获取交易记录
     *
     * @return
     */
    @GetMapping("/getBitCoinAddressTransaction")
    public Result getBitCoinAddressTransaction(@RequestParam(required = true, defaultValue = "155fzsEBHy9Ri2bMQ8uuuR3tv1YzcDywd4") String address) {
        JSONArray coin = transactionsService.getBitCoinAddressTransaction(address);
        JSONArray data = new JSONArray();
        for (int i = 0; i < coin.size(); i++) {
            JSONObject jsonObj = coin.getJSONObject(i);
            String inputsvalue = Coin.valueOf(jsonObj.getLong("inputs_value")).toFriendlyString();
            jsonObj.put("inputs_value", inputsvalue);
            data.add(jsonObj);
            System.out.println(jsonObj);
        }
        return Result.successResult(data);
    }

    /***
     * 根据ETH钱包地址获取交易记录
     *
     * @return
     */
    @GetMapping("/getETHAddressTransaction")
    public Result getETHAddressTransaction(@RequestParam(required = true, defaultValue = "0x3a6e4D83689405a1EA16DafaC6f1614253f3Bb9A") String address, @RequestParam(defaultValue = "1") String page, @RequestParam(defaultValue = "1") String size) {
        JSONArray coin = transactionsService.getETHAddressTransaction(address);
        JSONArray data = new JSONArray();
        for (Iterator iterator = coin.iterator(); iterator.hasNext(); ) {
            JSONObject object = (JSONObject) iterator.next();
            String inputsvalue = EthConverter.fromWei(object.getBigDecimal("value"), EthConverter.Unit.ETHER) + EthConverter.Unit.ETHER.toString().toUpperCase();
            object.put("value", inputsvalue);
            data.add(object);
            System.out.println(object);
        }
        return Result.successResult(data);
    }

    /**
     * @author Yamo
     * 不分类获取所有项目信息的接口
     * @param page
     * @param size
     */
    @GetMapping("/findAllProject")
    public Result findProjectsByState(@RequestParam(required = true, defaultValue = "0") Integer page,
                                      @RequestParam(required = true, defaultValue = "1") Integer size) {
        PageHelper.startPage(page, size);
        List<Project> projectList = projectService.findOfficalProject();
        try{
            for (int i = 0; i < projectList.size(); i++) {
                Integer projectId = projectList.get(i).getId();
                // 获取项目图片
                try {
                    String pictureUrl = projectFileService.findProjectFileByProjectId(projectId).getFileUrl();
                    projectList.get(i).setPictureUrl(pictureUrl);
                } catch (Exception e) {
                    projectList.get(i).setPictureUrl("");
                }

                // 根据项目ID查询项目钱包
                List<ProjectWallet> projectWallets = projectWalletService.findWalletByProjectId(projectId);
                projectList.get(i).setProjectWallets(projectWallets);

                // 根据createUserId查询用户信息
                try {
                    User createUser = userService.findById(projectList.get(i).getCreateUserId());
                    projectList.get(i).setCreateUser(createUser);
                } catch (Exception e) {
                    projectList.get(i).setCreateUser(userService.findById(ADMIN.getId()));
                }


                // 根据项目ID查寻目标代币信息
                List<TokenDetail> inputTokenDetails  = tokenDetailService.findByProjectIdAndType(projectId, INPUT_CION.getCode());
                if(CollectionUtils.isNotEmpty(inputTokenDetails)){
                    for(int j=0; j<inputTokenDetails.size();j++){
                        // 获取目标代币的代币详细信息
                        TokenMoney inputMoney = tokenMoneyService.findById(inputTokenDetails.get(j).getTokenMoneyId());
                        inputTokenDetails.get(j).setTokenMoney(inputMoney);
                    }
                    projectList.get(i).setInputTokenDetails(inputTokenDetails);
                }

                // 根据接收代币ID查寻接收代币信息
                List<TokenDetail> outPutTokenDetails  = tokenDetailService.findByProjectIdAndType(projectId, OUTPUT_ICON.getCode());
                if(CollectionUtils.isNotEmpty(outPutTokenDetails)){
                    // 获取接收代币的代币详细信息
                    TokenMoney inputMoney = tokenMoneyService.findById(outPutTokenDetails.get(0).getTokenMoneyId());
                    outPutTokenDetails.get(0).setTokenMoney(inputMoney);
                    projectList.get(i).setOutPutTokenDetail(outPutTokenDetails.get(0));
                }
            }
            PageInfo pageInfo = new PageInfo(projectList);
            return Result.successResult(pageInfo);
        }catch (Exception e){
            return Result.failResult("无项目数据!");
        }

    }

    /**
     * @author Yamo
     * 获取可以锁定的项目
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/findLockProject")
    public Result findLockProject(@RequestParam(required = true, defaultValue = "0") Integer page,
                                  @RequestParam(required = true, defaultValue = "1") Integer size) {
        PageHelper.startPage(page, size);
        List<Project> projectList = projectService.findProjectByState(NOW.getState());
        for (int i = 0; i < projectList.size(); i++) {
            // 获取项目图片
            try {
                String pictureUrl = projectFileService.findProjectFileByProjectId(projectList.get(i).getId()).getFileUrl();
                projectList.get(i).setPictureUrl(pictureUrl);
            } catch (Exception e) {
                projectList.get(i).setPictureUrl("");
            }

        }
        PageInfo pageInfo = new PageInfo(projectList);
        return Result.successResult(pageInfo);
    }
}
