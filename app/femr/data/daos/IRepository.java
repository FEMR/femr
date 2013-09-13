package femr.data.daos;

import com.avaje.ebean.ExpressionList;

import java.util.List;

public interface IRepository<T> {
    int count(Class<? extends T> clazz);

    T create(T entity);

    List<? extends T> find(ExpressionList<? extends T> query);

    T findOne(ExpressionList<? extends T> query);

    T update(T entity);
}
