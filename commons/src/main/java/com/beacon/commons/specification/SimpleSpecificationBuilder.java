package com.beacon.commons.specification;

import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

/**
 * 条件构造器
 * 用于创建条件表达式
 *
 * @author luckyhua
 * @version 1.0
 * @since 2017/9/19
 */
public class SimpleSpecificationBuilder<T> {

    /**
     * 条件列表
     */
    private List<SpecificationOperator> operators;

    public SimpleSpecificationBuilder() {
        operators = new ArrayList<>();
    }

    /**
     * 构造函数，初始化的条件是and
     *
     * @param key      条件的建
     * @param operator 条件
     * @param value    条件的值
     * @return 添加完的条件
     */
    public SimpleSpecificationBuilder(String key, SpecificationOperator.Operator operator, Object value) {
        SpecificationOperator so = new SpecificationOperator();
        so.setJoin(SpecificationOperator.Join.and);
        so.setKey(key);
        so.setOperator(operator);
        so.setValue(value);
        operators = new ArrayList<>();
        operators.add(so);
    }

    /**
     * 完成条件的添加
     *
     * @param join     连接符
     * @param key      条件的建
     * @param operator 条件
     * @param value    条件的值
     * @return this，方便后续的链式调用
     */
    public SimpleSpecificationBuilder<T> add(SpecificationOperator.Join join, String key, SpecificationOperator.Operator operator, Object value) {
        SpecificationOperator so = new SpecificationOperator();
        so.setJoin(join);
        so.setKey(key);
        so.setValue(value);
        so.setOperator(operator);
        operators.add(so);
        return this;
    }

    /**
     * 添加or条件
     *
     * @param key      条件的建
     * @param operator 条件
     * @param value    条件的值
     * @return this，方便后续的链式调用
     */
    public SimpleSpecificationBuilder<T> addOr(String key, SpecificationOperator.Operator operator, Object value) {
        return this.add(SpecificationOperator.Join.or, key, operator, value);
    }

    /**
     * 添加and条件
     *
     * @param key      条件的建
     * @param operator 条件
     * @param value    条件的值
     * @return this，方便后续的链式调用
     */
    public SimpleSpecificationBuilder<T> add(String key, SpecificationOperator.Operator operator, Object value) {
        return this.add(SpecificationOperator.Join.and, key, operator, value);
    }

    /**
     * 构建条件表达式
     *
     * @return 表达式
     */
    public Specification<T> generateSpecification() {
        return new SimpleSpecification<>(operators);
    }
}
