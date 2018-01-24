package com.beacon.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 分页结果
 *
 * @author luckyhua
 * @version 1.0
 * @since 2018/1/24
 */
@ApiModel(value = "PageResult", description = "分页结果")
public class PageResult {

    @ApiModelProperty(notes = "数据列表")
    private Object resultList;

    @ApiModelProperty(notes = "总页数")
    private int totalPages;

    @ApiModelProperty(notes = "总记录数")
    private long totalElements;

    @ApiModelProperty(notes = "当前页码")
    private int number;

    @ApiModelProperty(notes = "每页显示的数量")
    private int size;

    public Object getResultList() {
        return resultList;
    }

    public void setResultList(Object resultList) {
        this.resultList = resultList;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
