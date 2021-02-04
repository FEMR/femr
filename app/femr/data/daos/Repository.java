/*
     fEMR - fast Electronic Medical Records
     Copyright (C) 2014  Team fEMR

     fEMR is free software: you can redistribute it and/or modify
     it under the terms of the GNU General Public License as published by
     the Free Software Foundation, either version 3 of the License, or
     (at your option) any later version.

     fEMR is distributed in the hope that it will be useful,
     but WITHOUT ANY WARRANTY; without even the implied warranty of
     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
     GNU General Public License for more details.

     You should have received a copy of the GNU General Public License
     along with fEMR.  If not, see <http://www.gnu.org/licenses/>. If
     you have any questions, contact <info@teamfemr.org>.
*/
package femr.data.daos;

import io.ebean.Ebean;
import io.ebean.ExpressionList;
import io.ebean.Query;

import java.util.List;

public class Repository<T> implements IRepository<T> {

    @Override
    public int count(Class<? extends T> clazz) {
        return Ebean.find(clazz).findCount();
    }

    @Override
    public void delete(T entity){
        Ebean.delete(entity);
    }

    @Override
    public void delete(List<? extends T> entities){
        Ebean.deleteAll(entities);
    }

    @Override
    public T create(T entity) {
        Ebean.save(entity);
        return entity;
    }
    @Override
    public List<? extends T> createAll(List<? extends T> entities){
        Ebean.saveAll(entities);
        return entities;
    }

    @Override
    public List<? extends T> findAll(Class<? extends T> clazz) {
        return Ebean.find(clazz).findList();
    }

    @Override
    public List<? extends T> find(ExpressionList<? extends T> query) {
        return query.findList();
    }

    @Override
    public List<? extends T> find(Query<? extends T> query) {
        return query.findList();
    }

    @Override
    public T findOne(ExpressionList<? extends T> query) {
        T entity = query.findOne();
        return entity;
    }

    @Override
    public T update(T entity) {
        Ebean.save(entity);
        return entity;
    }
}
