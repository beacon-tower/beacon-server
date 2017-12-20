package com.beacon.commons.specification;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * 简单条件表达式
 *
 * @author luckyhua
 * @version 1.0
 * @since 2017/9/19
 */
public class SimpleSpecification<T> implements Specification<T> {

    /**
     * 查询的条件列表，是一组列表
     */
    private List<SpecificationOperator> operators;

    public SimpleSpecification(List<SpecificationOperator> operators) {
        this.operators = operators;
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        int index = 0;
        //通过resultPre来组合多个条件
        Predicate resultPre = null;
        for (SpecificationOperator op : operators) {
            if (index++ == 0) {
                resultPre = generatePredicate(root, cb, op);
                continue;
            }
            Predicate pre = generatePredicate(root, cb, op);
            if (pre == null) {
                continue;
            }
            switch (op.getJoin()) {
                case and:
                    resultPre = cb.and(resultPre, pre);
                    break;
                case or:
                    resultPre = cb.or(resultPre, pre);
                    break;
                default:
            }
        }
        return resultPre;
    }

    /**
     * 根据不同的操作符返回特定的查询
     *
     * @param root root
     * @param cb   条件构造器
     * @param op   操作对象
     * @return 逻辑表达式
     */
    private Predicate generatePredicate(Root<T> root, CriteriaBuilder cb, SpecificationOperator op) {
        Object value = op.getValue();
        switch (op.getOperator()) {
            case eq:
                return cb.equal(root.get(op.getKey()), value);
            case ne:
                return cb.notEqual(root.get(op.getKey()), value);
            case ge:
                return cb.ge(root.get(op.getKey()).as(Number.class), (Number) value);
            case le:
                return cb.le(root.get(op.getKey()).as(Number.class), (Number) value);
            case gt:
                return cb.gt(root.get(op.getKey()).as(Number.class), (Number) value);
            case lt:
                return cb.lt(root.get(op.getKey()).as(Number.class), (Number) value);
            case in:
                if (value instanceof Collection) {
                    return root.get(op.getKey()).in((Collection) value);
                }
                return root.get(op.getKey()).in(value);
            case notIn:
                if (value instanceof Collection) {
                    return cb.not(root.get(op.getKey()).in((Collection) value));
                }
                return cb.not(root.get(op.getKey()).in(value));
            case likeAll:
                return cb.like(root.get(op.getKey()).as(String.class), "%" + value + "%");
            case likeL:
                return cb.like(root.get(op.getKey()).as(String.class), value + "%");
            case likeR:
                return cb.like(root.get(op.getKey()).as(String.class), "%" + value);
            case isNull:
                return cb.isNull(root.get(op.getKey()));
            case isNotNull:
                return cb.isNotNull(root.get(op.getKey()));
            case lessThan:
                if (value instanceof Date) {
                    return cb.lessThan(root.get(op.getKey()), (Date) value);
                }
            case lessThanEqual:
                if (value instanceof Date) {
                    return cb.lessThanOrEqualTo(root.get(op.getKey()), (Date) value);
                }
            case greaterThan:
                if (value instanceof Date) {
                    return cb.greaterThan(root.get(op.getKey()), (Date) value);
                }
            case greaterThanEqual:
                if (value instanceof Date) {
                    return cb.greaterThanOrEqualTo(root.get(op.getKey()), (Date) value);
                }
            case between:
                if (value instanceof Date[]) {
                    Date[] dateArray = (Date[]) value;
                    return cb.between(root.get(op.getKey()), dateArray[0], dateArray[1]);
                }
            default:
                return null;
        }
    }
}
