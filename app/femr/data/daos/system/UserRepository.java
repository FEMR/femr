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
package femr.data.daos.system;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.Query;
import femr.data.daos.core.IUserRepository;
import femr.data.models.core.IRole;
import femr.data.models.core.IUser;
import femr.data.models.mysql.Role;
import femr.data.models.mysql.User;
import femr.util.stringhelpers.StringUtils;
import play.Logger;

import java.util.ArrayList;
import java.util.List;

public class UserRepository implements IUserRepository {

    /**
     * {@inheritDoc}
     */
    @Override
    public IUser retrieveUserByEmail(String email) {

        if (StringUtils.isNullOrWhiteSpace(email)) {

            return null;
        }

        ExpressionList<User> expressionList = getUserQuery()
                .where()
                .eq("email", email);

        IUser user = null;
        try {

            user = expressionList.findUnique();
        } catch (Exception ex) {

            Logger.error("UserRepository-retrieveUserByEmail", ex.getMessage());
        }

        return user;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IUser retrieveUserByEmailEagerFetchRoles(String email) {

        if (StringUtils.isNullOrWhiteSpace(email)) {

            return null;
        }

        ExpressionList<User> expressionList = getUserQuery()
                .fetch("roles")
                .where()
                .eq("email", email);

        IUser user = null;
        try {

            user = expressionList.findUnique();
        } catch (Exception ex) {

            Logger.error("UserRepository-retrieveUserByEmailEagerFetchRoles", ex.getMessage());
        }

        return user;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IUser retrieveUserById(int id) {

        ExpressionList<User> expressionList = getUserQuery()
                .where()
                .eq("id", id);

        IUser user = null;
        try {

            user = expressionList.findUnique();
        } catch (Exception ex) {

            Logger.error("UserRepository-retrieveUserById", ex.getMessage());
        }

        return user;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IUser retrieveUserByIdEagerFetchRoles(int id) {

        ExpressionList<User> expressionList = getUserQuery()
                .fetch("roles")
                .where()
                .eq("id", id);

        IUser user = null;
        try {

            user = expressionList.findUnique();
        } catch (Exception ex) {

            Logger.error("UserRepository-retrieveUserByIdEagerFetchRoles", ex.getMessage());
        }

        return user;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<? extends IRole> retrieveAllRoles() {

        ExpressionList<Role> expressionList = getRoleQuery()
                .where()
                .ne("name", "SuperUser");

        List<? extends IRole> roles = new ArrayList<>();
        try {

            roles = expressionList.findList();
        } catch (Exception ex) {

            Logger.error("UserRepository-retrieveAllRoles", ex.getMessage());
        }

        return roles;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<? extends IUser> retrieveAllUsers() {

        ExpressionList<User> expressionList = getUserQuery()
                .fetch("roles")
                .where()
                .ne("roles.name", "SuperUser");

        List<? extends IUser> users = new ArrayList<>();

        try {

            users = expressionList.findList();
        } catch (Exception ex) {

            Logger.error("UserRepository-retrieveAllUsers", ex.getMessage());
        }

        return users;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<? extends IRole> retrieveRoles(List<String> names) {

        if (names == null || names.size() == 0) {

            return null;
        }

        ExpressionList<Role> expressionList = getRoleQuery()
                .where()
                .in("name", names);

        List<? extends IRole> roles = new ArrayList<>();
        try {

            roles = expressionList.findList();
        } catch (Exception ex) {

            Logger.error("UserRepository-retrieveRoles", ex.getMessage());
        }

        return roles;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IUser saveUser(IUser user) {

        if (user == null) {

            return null;
        }

        try {

            Ebean.save(user);
        } catch (Exception ex) {

            Logger.error("UserRepository-saveUser", ex.getMessage());
        }

        return user;
    }


    /**
     * Provides the Ebean object to start building queries
     *
     * @return The patient age classification type Query object
     */
    public static Query<Role> getRoleQuery() {
        return Ebean.find(Role.class);
    }

    /**
     * Provides the Ebean object to start building queries
     *
     * @return The patient age classification type Query object
     */
    public static Query<User> getUserQuery() {
        return Ebean.find(User.class);
    }
}
