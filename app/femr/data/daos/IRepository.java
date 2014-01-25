package femr.data.daos;

import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.Query;

import java.util.List;

public interface IRepository<T> {
    int count(Class<? extends T> clazz);

    T create(T entity);

    List<? extends T> createAll(List<? extends T> entities);

    List<? extends T> find(ExpressionList<? extends T> query);

    T findOne(ExpressionList<? extends T> query);

    T update(T entity);

    List<? extends T> findAll(Class<? extends T> clazz);

    List<? extends T> find(Query<? extends T> query);
}
