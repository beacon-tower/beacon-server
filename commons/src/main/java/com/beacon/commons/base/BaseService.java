package com.beacon.commons.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * 基类service
 *
 * @author luckyhua
 * @version 1.0
 * @since 2017/9/29
 */
@Transactional
@SuppressWarnings("unchecked")
public abstract class BaseService<T extends BaseEntity, ID extends Serializable> {

    public abstract BaseDao<T, ID> getBaseDao();

    public T findById(ID id) {
        return getBaseDao().findOne(id);
    }

    public List<T> findAll() {
        return getBaseDao().findAll();
    }

    public List<T> findAll(Sort sort) {
        return getBaseDao().findAll(sort);
    }

    public List<T> findAll(Specification<T> spec) {
        return getBaseDao().findAll(spec);
    }

    public Page<T> findAll(Pageable pageable) {
        return getBaseDao().findAll(pageable);
    }

    public Page<T> findAll(Specification<T> spec, Pageable pageable) {
        return getBaseDao().findAll(spec, pageable);
    }

    public List<T> findList(ID[] ids) {
        List<ID> idList = Arrays.asList(ids);
        return getBaseDao().findAll(idList);
    }

    public List<T> findList(Iterable<ID> ids) {
        return getBaseDao().findAll(ids);
    }

    public List<T> findList(Specification<T> spec, Sort sort) {
        return getBaseDao().findAll(spec, sort);
    }

    public long count() {
        return getBaseDao().count();
    }

    public long count(Specification<T> spec) {
        return getBaseDao().count(spec);
    }

    public boolean exists(ID id) {
        return getBaseDao().exists(id);
    }

    public void save(T entity) {
        getBaseDao().save(entity);
    }

    public void save(Iterable<T> entitys) {
        getBaseDao().save(entitys);
    }

    public T update(T entity) {
        return getBaseDao().saveAndFlush(entity);
    }

    public void delete(ID id) {
        getBaseDao().delete(id);
    }

    public void deleteByIds(ID... ids) {
        if (ids != null) {
            for (ID id : ids) {
                this.delete(id);
            }
        }
    }

    public void delete(T entity) {
        getBaseDao().delete(entity);
    }

    public void delete(T[] entitys) {
        List<T> tList = Arrays.asList(entitys);
        getBaseDao().delete(tList);
    }

    public void delete(Iterable<T> entitys) {
        getBaseDao().delete(entitys);
    }
}
