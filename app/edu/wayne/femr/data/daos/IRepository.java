package edu.wayne.femr.data.daos;

import com.avaje.ebean.ExpressionList;

import java.util.List;

public interface IRepository<T> {
    int count();
    T create(T entity);
    List<T> find(ExpressionList<T> query);
    T findOne(ExpressionList<T> query);
    T update(T entity);
}
