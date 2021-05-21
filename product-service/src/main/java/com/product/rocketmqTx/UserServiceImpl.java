package com.product.rocketmqTx;

import com.product.entity.AbcPerson;
import com.product.entity.TransferDetail;
import com.product.mapper.TransferDetailMapper;
import com.product.repository.AbcPersonService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;

@Service
public class UserServiceImpl implements UserService {
    
    @Resource
    private TransferDetailMapper transferDetailMapper;
    @Resource
    private AbcPersonService abcPersonServiceImpl;
    
    @Transactional
    @Override
    public Boolean transferMoney(Long userId, BigDecimal money, String msgId) {
        boolean flag = false;
        
        TransferDetail transferDetail = new TransferDetail();
        transferDetail.setMoney(money);
        transferDetail.setUserId(userId);
        transferDetail.setMsgId(msgId);
        transferDetail.setDeleteFlg("0");
        transferDetail.setCreateTime(new Date());

        /**生成转账明细*/
        flag = transferDetailMapper.insert(transferDetail) > 0;
        throwRuntimeException(flag, "交易信息存储失败，异常回滚...");

        // 加悲观锁
        AbcPerson lockAbcPerson = abcPersonServiceImpl.selectByPrimaryKey(userId);
        if (ObjectUtils.isNotEmpty(lockAbcPerson) && lockAbcPerson.getBanlance().doubleValue() > money.doubleValue()) {
            // 修改用户金额
            BigDecimal currentBanlance = new BigDecimal(lockAbcPerson.getBanlance().doubleValue() - money.doubleValue());
            lockAbcPerson.setBanlance(currentBanlance);
            flag = abcPersonServiceImpl.updateByPrimaryKeySelective(lockAbcPerson) > 0 ? true : false;
            throwRuntimeException(flag, "修改用户金额失败，异常回滚...");
            if(flag){
                // throw new RuntimeException("模拟转账异常");
                /**转账方扣款成功后，修改交易明细状态*/
                transferDetail.setDeleteFlg("1");
                flag = transferDetailMapper.updateByPrimaryKeySelective(transferDetail) > 0 ? true : false;
                throwRuntimeException(flag, "交易信息变更状态失败，异常回滚...");
            }
        }
        return flag;
    }

    public void throwRuntimeException(boolean flag, String msg) {
        if (!flag) {
            throw new RuntimeException(msg);
        }
    }

}
