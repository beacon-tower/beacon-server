package com.beacon.dao;

import com.beacon.commons.base.BaseDao;
import com.beacon.entity.SmsLog;
import org.springframework.stereotype.Repository;

/**
 * 短信发送记录
 *
 * @author luckyhua
 * @version 1.0
 * @since 2018/1/10
 */
@Repository
public interface SmsLogDao extends BaseDao<SmsLog, Integer> {
}
