package com.beacon.commons.base;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * 实体基类
 *
 * @author luckyhua
 * @version 1.0
 * @since 2017/9/19
 */
@MappedSuperclass
public abstract class BaseEntity implements Serializable {

    private static final long serialVersionUID = -250118731239275742L;
}
