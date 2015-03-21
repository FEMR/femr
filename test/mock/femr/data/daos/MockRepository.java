package mock.femr.data.daos;

import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.Query;
import femr.data.daos.IRepository;

import java.util.List;

public class MockRepository<T> implements IRepository<T> {
    public T entityPassedIn;
    public List<? extends T> entitiesPassedIn;
    public boolean createWasCalled = false;
    public boolean createAllWasCalled = false;
    public boolean updateWasCalled = false;
    public boolean findOneWasCalled = false;


    @Override
    public int count(Class<? extends T> clazz) {
        return 0;
    }

    @Override
    public T create(T entity) {
        createWasCalled = true;
        entityPassedIn = entity;
        return entityPassedIn;
    }
    @Override
    public List<? extends T> createAll(List<? extends T> entities){
        createAllWasCalled = true;
        entitiesPassedIn = entities;
        return entitiesPassedIn;
    }

    @Override
    public List<? extends T> findAll(Class<? extends T> clazz) {
//        return Ebean.find(clazz).findList();
        return null;
    }

    @Override
    public List<? extends T> find(ExpressionList<? extends T> query) {
//        return query.findList();
        return null;
    }

    @Override
    public List<? extends T> find(Query<? extends T> query) {
//        return query.findList();
        return null;
    }

    @Override
    public T findOne(ExpressionList<? extends T> query) {
        findOneWasCalled = true;

        return null;
    }

    @Override
    public T update(T entity) {
        updateWasCalled = true;
        entityPassedIn = entity;
        return entityPassedIn;
    }

    @Override
    public void delete(List<? extends T> entities){


    }

    @Override
    public void delete(T entity){


    }
}
