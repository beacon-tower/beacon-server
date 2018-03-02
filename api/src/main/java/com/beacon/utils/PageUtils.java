package com.beacon.utils;

import com.beacon.pojo.PageResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 分页工具类
 *
 * @author luckyhua
 * @version 1.0
 * @since 2018/1/24
 */
public class PageUtils {

    /**
     * 封装分页数据
     */
    @SuppressWarnings("unchecked")
    public static PageResult getPageResult(Page<?> objPage) {
        if (objPage == null) {
            return null;
        }
        PageResult pageResult = new PageResult();
        pageResult.setResultList(objPage.getContent());
        pageResult.setTotalPages(objPage.getTotalPages());
        pageResult.setTotalElements(objPage.getTotalElements());
        pageResult.setNumber(objPage.getNumber());
        pageResult.setSize(objPage.getSize());
        return pageResult;
    }

    /**
     * 数据转换
     *
     * @param pageable 分页
     * @param sourcePage 原数据
     * @param targetList 目标数据
     * @return 目标分页数据
     */
    public static Page<?> convert(Pageable pageable, Page<?> sourcePage, List<?> targetList) {
        if (sourcePage.hasContent()) {
            return new PageImpl<>(targetList, pageable, sourcePage.getTotalElements());
        }
        return null;
    }

}
