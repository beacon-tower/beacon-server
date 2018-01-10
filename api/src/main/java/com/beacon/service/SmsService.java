package com.beacon.service;

import com.beacon.commons.base.BaseDao;
import com.beacon.commons.base.BaseService;
import com.beacon.commons.redis.RedisHelper;
import com.beacon.commons.utils.SmsUtils;
import com.beacon.commons.utils.StringUtils;
import com.beacon.dao.SmsLogDao;
import com.beacon.entity.SmsLog;
import com.beacon.enums.dict.SmsLogDict;
import com.yunpian.sdk.model.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

import static com.beacon.global.constant.Constant.MOBILE_CODE;

/**
 * 发送短信
 *
 * @author luckyhua
 * @version 1.0
 * @since 2017/10/12
 */
@Service
public class SmsService extends BaseService<SmsLog, Integer> {

    private static final Logger log = LoggerFactory.getLogger(SmsService.class);

    private static final int EXPIRE_MINUTE = 5; //有效时间

    @Inject
    private SmsLogDao smsLogDao;

    @Inject
    private RedisHelper redisHelper;

    @Override
    public BaseDao<SmsLog, Integer> getBaseDao() {
        return this.smsLogDao;
    }

    /**
     * 发送验证码类的短信，存入缓存中，用于校验
     *
     * @param mobile 手机号
     * @param type   业务类型
     * @return 发送状态
     */
    public boolean sendMobileCode(String mobile, String type) {
        String code = StringUtils.getNumberUUID(6);
        String content = String.format(SmsLogDict.getValue(type), code, EXPIRE_MINUTE);
        boolean sendState = sendSms(mobile, content, type);
        if (sendState) { //发送成功，放入redis中
            redisHelper.setExpire(MOBILE_CODE + type + ":" + mobile, code, EXPIRE_MINUTE * 60L);
        }
        return sendState;
    }

    /**
     * 发送业务类短信，直接发送通知内容
     *
     * @param mobile 手机号
     * @param type   业务类型
     * @return 发送状态
     */
    public boolean sendMobileSms(String mobile, String type) {
        String content = SmsLogDict.getValue(type);
        return sendSms(mobile, content, type);
    }

    /**
     * 校验手机号验证码
     *
     * @param mobile 手机号
     * @param code   手机验证码
     * @param type   业务类型
     * @return 校验结果
     */
    public boolean checkMobileCode(String mobile, String code, SmsLogDict type) {
        String key = MOBILE_CODE + type.getKey() + ":" + mobile;
        if (redisHelper.exists(key)) {
            String cacheCode = (String) redisHelper.get(key);
            if (cacheCode.equals(code)) {
                redisHelper.remove(key);
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    /**
     * 所有发送短信，统一调用该方法，记录发送日志
     *
     * @param mobile  手机号
     * @param content 内容
     * @param type    业务类型
     * @return 发送状态
     */
    private boolean sendSms(String mobile, String content, String type) {
        Result<?> result = SmsUtils.send(mobile, content);
        log.debug("send sms result : " + result);
        Boolean sendState = Integer.valueOf(0).equals(result.getCode());
        //插入发送日志
        SmsLog smsLog = new SmsLog();
        smsLog.setMobile(mobile);
        smsLog.setContent(content);
        smsLog.setType(type);
        smsLog.setState(String.valueOf(sendState ? SmsLogDict.STATE_SUCCESS : SmsLogDict.STATE_FAIL));
        smsLog.setBackResult(result.getMsg());
        super.save(smsLog);
        return sendState;
    }
}
