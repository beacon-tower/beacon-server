package com.beacon.commons.specification;

/**
 * 操作符类，这个类中存储了键值对和操作符号，另外存储了连接下一个条件的类型是and还是or
 * <br>
 * 创建时通过 id>=7,其中id就是key,>=就是operator操作符，7就是value
 * <br>
 * 特殊的自定义几个操作符(:表示like %v%，b:表示v%,:b表示%v)
 *
 * @author luckyhua
 * @version 1.0
 * @since 2017/9/19
 */
public class SpecificationOperator {

    /**
     * 操作符的key，如查询时的name,id之类
     */
    private String key;

    /**
     * 操作符的value，具体要查询的值
     */
    private Object value;

    /**
     * 操作符，自己定义的一组操作符，用来方便查询
     */
    private Operator operator;

    /**
     * 连接的方式：and或者or
     */
    private Join join;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    public Join getJoin() {
        return join;
    }

    public void setJoin(Join join) {
        this.join = join;
    }

    public enum Operator {
        eq, ne, ge, le, gt, lt, likeL, likeR, likeAll, isNull, isNotNull, in, notIn,
        lessThan, lessThanEqual, greaterThan, greaterThanEqual, between;

        @Override
        public String toString() {
            return name();
        }
    }

    public enum Join {
        and, or;

        @Override
        public String toString() {
            return name();
        }
    }
}
