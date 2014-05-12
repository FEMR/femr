package femr.ui.controllers;

import com.google.inject.Inject;
import femr.business.dtos.CurrentUser;
import femr.business.dtos.ServiceResponse;
import femr.business.dtos.TabFieldItem;
import femr.business.dtos.TabItem;
import femr.business.services.ISessionService;
import femr.business.services.ISuperuserService;
import femr.common.models.Roles;
import femr.ui.helpers.security.AllowedRoles;
import femr.ui.helpers.security.FEMRAuthenticated;
import femr.ui.models.superuser.*;
import femr.util.stringhelpers.StringUtils;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import femr.ui.views.html.superuser.index;
import femr.ui.views.html.superuser.tabs;
import femr.ui.views.html.superuser.fields;

import java.util.List;

@Security.Authenticated(FEMRAuthenticated.class)
@AllowedRoles({Roles.SUPERUSER})
public class SuperuserController extends Controller {
    private final Form<TabsViewModelPost> TabsViewModelForm = Form.form(TabsViewModelPost.class);
    private final Form<ContentViewModelPost> ContentViewModelForm = Form.form(ContentViewModelPost.class);
    private ISessionService sessionService;
    private ISuperuserService superuserService;

    @Inject
    public SuperuserController(ISessionService sessionService,
                               ISuperuserService superuserService) {
        this.sessionService = sessionService;
        this.superuserService = superuserService;
    }

    public Result indexGet() {
        CurrentUser currentUser = sessionService.getCurrentUserSession();
        return ok(index.render(currentUser));
    }

    public Result tabsGet() {
        CurrentUser currentUser = sessionService.getCurrentUserSession();
        ServiceResponse<List<TabItem>> response;

        response = superuserService.getTabs(false);
        if (response.hasErrors()) {
            return internalServerError();
        }

        TabsViewModelGet viewModelGet = new TabsViewModelGet();
        viewModelGet.setCurrentTabs(response.getResponseObject());

        //get deleted tabs
        response = superuserService.getTabs(true);
        if (response.hasErrors()) {
            return internalServerError();
        }
        viewModelGet.setDeletedTabs(response.getResponseObject());

        return ok(tabs.render(currentUser, viewModelGet));
    }

    public Result tabsPost() {
        CurrentUser currentUser = sessionService.getCurrentUserSession();
        TabsViewModelPost viewModelPost = TabsViewModelForm.bindFromRequest().get();

        //becomes new or edit
        if (StringUtils.isNotNullOrWhiteSpace(viewModelPost.getAddTabName())) {
            TabItem tabItem = new TabItem();
            //new
            if (superuserService.doesTabExist(viewModelPost.getAddTabName()).getResponseObject() == false) {
                tabItem.setName(viewModelPost.getAddTabName());
                if (viewModelPost.getAddTabLeft() != null) tabItem.setLeftColumnSize(viewModelPost.getAddTabLeft());
                if (viewModelPost.getAddTabRight() != null) tabItem.setRightColumnSize(viewModelPost.getAddTabRight());
                ServiceResponse<TabItem> response = superuserService.createTab(tabItem, currentUser.getId());
                if (response.hasErrors()) {
                    return internalServerError();
                }
            } else {//edit
                if (viewModelPost.getAddTabLeft() == null) tabItem.setLeftColumnSize(0);
                else tabItem.setLeftColumnSize(viewModelPost.getAddTabLeft());

                if (viewModelPost.getAddTabRight() == null) tabItem.setRightColumnSize(0);
                else tabItem.setRightColumnSize(viewModelPost.getAddTabRight());

                tabItem.setName(viewModelPost.getAddTabName());
                ServiceResponse<TabItem> response = superuserService.editTab(tabItem, currentUser.getId());
                if (response.hasErrors()) {
                    return internalServerError();
                }
            }
        }
        //becomes toggle
        if (StringUtils.isNotNullOrWhiteSpace(viewModelPost.getDeleteTab())) {
            ServiceResponse<TabItem> response = superuserService.toggleTab(viewModelPost.getDeleteTab());
            if (response.hasErrors()) {
                return internalServerError();
            }
        }

        return redirect("/superuser/tabs");
    }

    //name = tab name
    public Result contentGet(String name) {
        CurrentUser currentUser = sessionService.getCurrentUserSession();
        ContentViewModelGet viewModelGet = new ContentViewModelGet();

        viewModelGet.setName(name);

        //get current custom fields
        ServiceResponse<List<TabFieldItem>> currentFieldItemsResponse = superuserService.getTabFields(name, false);
        if (currentFieldItemsResponse.hasErrors()) {
            return internalServerError();
        }
        viewModelGet.setCurrentCustomFieldItemList(currentFieldItemsResponse.getResponseObject());

        //get removed custom fields
        ServiceResponse<List<TabFieldItem>> removedFieldItemsResponse = superuserService.getTabFields(name, true);
        if (currentFieldItemsResponse.hasErrors()) {
            return internalServerError();
        }
        viewModelGet.setRemovedCustomFieldItemList(removedFieldItemsResponse.getResponseObject());

        //get available field types
        ServiceResponse<List<String>> fieldTypesResponse = superuserService.getTypes();
        if (fieldTypesResponse.hasErrors()) {
            return internalServerError();
        }
        viewModelGet.setCustomFieldTypes(fieldTypesResponse.getResponseObject());

        //get available fields sizes
        ServiceResponse<List<String>> fieldSizesResponse = superuserService.getSizes();
        if (fieldSizesResponse.hasErrors()) {
            return internalServerError();
        }
        viewModelGet.setCustomFieldSizes(fieldSizesResponse.getResponseObject());

        return ok(fields.render(currentUser, viewModelGet));
    }

    //name = tab name
    public Result contentPost(String name) {
        CurrentUser currentUser = sessionService.getCurrentUserSession();
        ContentViewModelPost viewModelPost = ContentViewModelForm.bindFromRequest().get();

        //adding/editing a field
        if (StringUtils.isNotNullOrWhiteSpace(viewModelPost.getAddName()) && StringUtils.isNotNullOrWhiteSpace(viewModelPost.getAddSize()) && StringUtils.isNotNullOrWhiteSpace(viewModelPost.getAddType())) {
            TabFieldItem tabFieldItem = new TabFieldItem();
            tabFieldItem.setName(viewModelPost.getAddName());
            tabFieldItem.setSize(viewModelPost.getAddSize().toLowerCase());
            tabFieldItem.setType(viewModelPost.getAddType().toLowerCase());
            tabFieldItem.setOrder(viewModelPost.getAddOrder());
            tabFieldItem.setPlaceholder(viewModelPost.getAddPlaceholder());
            //edit
            if (superuserService.doesTabFieldExist(viewModelPost.getAddName()).getResponseObject() == true) {
                superuserService.editTabField(tabFieldItem);
            } else {

                ServiceResponse<TabFieldItem> response = superuserService.createTabField(tabFieldItem, currentUser.getId(), name);
                if (response.hasErrors()) {
                    return internalServerError();
                }
            }
        }
        //deactivating a field
        if (StringUtils.isNotNullOrWhiteSpace(viewModelPost.getToggleName())) {
            ServiceResponse<TabFieldItem> response = superuserService.toggleTabField(viewModelPost.getToggleName(), name);
            if (response.hasErrors()) {
                return internalServerError();
            }
        }

        return redirect("/superuser/tabs/" + name);
    }

}
