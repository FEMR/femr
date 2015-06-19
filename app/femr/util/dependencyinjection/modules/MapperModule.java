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
package femr.util.dependencyinjection.modules;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import femr.common.IItemModelMapper;
import femr.common.ItemModelMapper;
import femr.data.DataModelMapper;
import femr.data.IDataModelMapper;

public class MapperModule extends AbstractModule {

    @Override
    protected void configure() {

        bind(IDataModelMapper.class).to(DataModelMapper.class);
        bind(IItemModelMapper.class).annotatedWith(Names.named("identified")).to(ItemModelMapper.class);
    }
}
