package edu.wayne.femr.data.daos;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.ExpressionList;

import java.util.List;

public class Repository<T> implements IRepository<T> {

    @Override
    public int count(Class<T> clazz) {
        return Ebean.find(clazz).findRowCount();
    }

    @Override
    public T create(T entity) {
        Ebean.save(entity);
        return entity;
    }

    @Override
    public List<T> find(ExpressionList<T> query) {
        return query.findList();
    }

    @Override
    public T findOne(ExpressionList<T> query) {
        T entity = query.findUnique();
        return entity;
    }

    @Override
    public T update(T entity) {
        Ebean.save(entity);
        return entity;
    }
}
