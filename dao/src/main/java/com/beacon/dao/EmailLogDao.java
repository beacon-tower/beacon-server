package com.beacon.dao;

import com.beacon.commons.base.BaseDao;
import com.beacon.entity.EmailLog;
import org.springframework.stereotype.Repository;

/**
 * 邮件发送
 *
 * @author luckyhua
 * @version 1.0
 * @since 2018/1/10
 */
@Repository
public interface EmailLogDao extends BaseDao<EmailLog, Integer> {
}
