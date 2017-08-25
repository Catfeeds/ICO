package com.tongwii.ico.service.impl;

import com.tongwii.ico.core.Result;
import com.tongwii.ico.dao.UserProjectInvestRecordMapper;
import com.tongwii.ico.model.ProjectUserRelation;
import com.tongwii.ico.model.TokenDetail;
import com.tongwii.ico.model.UserProjectInvestRecord;
import com.tongwii.ico.service.ProjectUserRelationService;
import com.tongwii.ico.service.TokenDetailService;
import com.tongwii.ico.service.UserProjectInvestRecordService;
import com.tongwii.ico.core.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

import static com.tongwii.ico.model.TokenDetail.TokenDetailType.INPUT_CION;


/**
 * Created by Zeral on 2017-08-25.
 */
@Service
@Transactional
public class UserProjectInvestRecordServiceImpl extends AbstractService<UserProjectInvestRecord> implements UserProjectInvestRecordService {
    @Resource
    private UserProjectInvestRecordMapper userProjectInvestRecordMapper;
    @Autowired
    private ProjectUserRelationService projectUserRelationService;
    @Autowired
    private TokenDetailService tokenDetailService;

    @Override
    public List<UserProjectInvestRecord> findByUserId(Integer userId) {
        UserProjectInvestRecord userProjectInvestRecord = new UserProjectInvestRecord();
        userProjectInvestRecord.setUserId(userId);
        List<UserProjectInvestRecord> userProjectInvestRecords = userProjectInvestRecordMapper.select(userProjectInvestRecord);
        if(CollectionUtils.isEmpty(userProjectInvestRecords)){
            return null;
        }
        return userProjectInvestRecords;
    }
    /**
     * 根据项目Id查询所有用户的交易记录
     *
     * @param projectId
     * @return
     */
    @Override
    public List<UserProjectInvestRecord> findByProjectId(Integer projectId) {
        UserProjectInvestRecord userProjectInvestRecord = new UserProjectInvestRecord();
        userProjectInvestRecord.setProjectId(projectId);
        List<UserProjectInvestRecord> userProjectInvestRecords = userProjectInvestRecordMapper.select(userProjectInvestRecord);
        if(CollectionUtils.isEmpty(userProjectInvestRecords)){
            return null;
        }
        return userProjectInvestRecords;
    }

    @Override
    public void saveRecordAndUpdateProjectProjess(Integer userId, Integer coinId, Integer projectId, String investmentMoney) {
        UserProjectInvestRecord userProjectInvestRecord = new UserProjectInvestRecord();
        userProjectInvestRecord.setProjectId(projectId);
        userProjectInvestRecord.setUserId(userId);
        userProjectInvestRecord.setTokenId(coinId);
        userProjectInvestRecord.setInvestValue(Double.parseDouble(investmentMoney));
        userProjectInvestRecord.setLockDate(new Date());
        // 给project_user表中增加记录
        // 该用户是否锁定过该项目
        if(projectUserRelationService.userIsLockedProject(userId,projectId)){
            // 保存投资记录
            this.save(userProjectInvestRecord);
            ProjectUserRelation projectUserRelation = new ProjectUserRelation();
            projectUserRelation.setUserId(userId);
            projectUserRelation.setProjectId(projectId);
            // 更新项目进度
            List<TokenDetail> tokenDetails = tokenDetailService.findByProjectIdAndType(projectId, INPUT_CION.getCode());
            for (TokenDetail tokenDetail : tokenDetails) {
                if (tokenDetail.getTokenMoneyId().equals(coinId)) {
                    tokenDetail.setCurrentNumber(tokenDetail.getCurrentNumber() + Double.parseDouble(investmentMoney));
                    tokenDetailService.update(tokenDetail);
                }
            }
            // 保存用户项目投资关系
            projectUserRelationService.save(projectUserRelation);
        }else{
            // 保存投资记录
            this.save(userProjectInvestRecord);
        }
    }
}
