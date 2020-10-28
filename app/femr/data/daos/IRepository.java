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

import io.ebean.ExpressionList;
import io.ebean.Query;

import java.util.List;

public interface IRepository<T> {
    int count(Class<? extends T> clazz);

    void delete(T entity);

    void delete(List<? extends T> entities);

    T create(T entity);

    List<? extends T> createAll(List<? extends T> entities);

    List<? extends T> find(ExpressionList<? extends T> query);

    T findOne(ExpressionList<? extends T> query);

    T update(T entity);

    List<? extends T> findAll(Class<? extends T> clazz);

    List<? extends T> find(Query<? extends T> query);
}
