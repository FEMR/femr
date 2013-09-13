package edu.wayne.femr.data.daos;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.ExpressionList;

import java.util.List;

public class Repository<T> implements IRepository<T> {

    @Override
    public int count(Class<? extends T> clazz) {
        return Ebean.find(clazz).findRowCount();
    }

    @Override
    public T create(T entity) {
        Ebean.save(entity);
        return entity;
    }

    @Override
    public List<? extends T> find(ExpressionList<? extends T> query) {
        return query.findList();
    }

    @Override
    public T findOne(ExpressionList<? extends T> query) {
        T entity = query.findUnique();
        return entity;
    }

    @Override
    public T update(T entity) {
        Ebean.save(entity);
        return entity;
    }
}
