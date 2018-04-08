package femr.ui.controllers.superuser;


import com.google.inject.Inject;
import controllers.AssetsFinder;
import femr.business.services.core.ISessionService;
import femr.business.services.core.ITabService;
import femr.common.dtos.CurrentUser;
import femr.common.dtos.ServiceResponse;
import femr.common.models.TabFieldItem;
import femr.common.models.TabItem;
import femr.data.models.mysql.Roles;
import femr.ui.helpers.security.AllowedRoles;
import femr.ui.helpers.security.FEMRAuthenticated;
import femr.ui.models.superuser.ContentViewModelGet;
import femr.ui.models.superuser.ContentViewModelPost;
import femr.ui.models.superuser.TabsViewModelGet;
import femr.ui.models.superuser.TabsViewModelPost;
import femr.util.stringhelpers.StringUtils;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import femr.ui.views.html.superuser.tabs.manage;
import femr.ui.views.html.superuser.tabs.fields;

import java.util.List;

@Security.Authenticated(FEMRAuthenticated.class)
@AllowedRoles({Roles.SUPERUSER})
public class TabController extends Controller {

    private final AssetsFinder assetsFinder;
    private final FormFactory formFactory;
    private final ITabService tabService;
    private final ISessionService sessionService;

    @Inject
    public TabController(AssetsFinder assetsFinder,
                         FormFactory formFactory,
                         ITabService tabService,
                         ISessionService sessionService) {

        this.assetsFinder = assetsFinder;
        this.formFactory = formFactory;
        this.tabService = tabService;
        this.sessionService = sessionService;
    }

    public Result manageGet() {
        CurrentUser currentUser = sessionService.retrieveCurrentUserSession();
        ServiceResponse<List<TabItem>> response;

        response = tabService.retrieveCustomTabs(false);
        if (response.hasErrors()) {
            throw new RuntimeException();
        }

        TabsViewModelGet viewModelGet = new TabsViewModelGet();
        viewModelGet.setCurrentTabs(response.getResponseObject());

        //get deleted tabs
        response = tabService.retrieveCustomTabs(true);
        if (response.hasErrors()) {
            throw new RuntimeException();
        }
        viewModelGet.setDeletedTabs(response.getResponseObject());

        return ok(manage.render(currentUser, viewModelGet, assetsFinder));
    }

    public Result managePost() {
        CurrentUser currentUser = sessionService.retrieveCurrentUserSession();

        final Form<TabsViewModelPost> TabsViewModelForm = formFactory.form(TabsViewModelPost.class);
        TabsViewModelPost viewModelPost = TabsViewModelForm.bindFromRequest().get();

        //becomes new or edit
        if (StringUtils.isNotNullOrWhiteSpace(viewModelPost.getAddTabName())) {
            TabItem tabItem = new TabItem();
            //new
            if (!tabService.doesTabExist(viewModelPost.getAddTabName()).getResponseObject()) {
                tabItem.setName(viewModelPost.getAddTabName());
                if (viewModelPost.getAddTabLeft() != null) tabItem.setLeftColumnSize(viewModelPost.getAddTabLeft());
                if (viewModelPost.getAddTabRight() != null) tabItem.setRightColumnSize(viewModelPost.getAddTabRight());
                ServiceResponse<TabItem> response = tabService.createTab(tabItem, currentUser.getId());
                if (response.hasErrors()) {
                    throw new RuntimeException();
                }
            } else {//edit
                if (viewModelPost.getAddTabLeft() == null) tabItem.setLeftColumnSize(0);
                else tabItem.setLeftColumnSize(viewModelPost.getAddTabLeft());

                if (viewModelPost.getAddTabRight() == null) tabItem.setRightColumnSize(0);
                else tabItem.setRightColumnSize(viewModelPost.getAddTabRight());

                tabItem.setName(viewModelPost.getAddTabName());
                ServiceResponse<TabItem> response = tabService.updateTab(tabItem, currentUser.getId());
                if (response.hasErrors()) {
                    throw new RuntimeException();
                }
            }
        }
        //becomes toggle
        if (StringUtils.isNotNullOrWhiteSpace(viewModelPost.getDeleteTab())) {
            ServiceResponse<TabItem> response = tabService.toggleTab(viewModelPost.getDeleteTab());
            if (response.hasErrors()) {
                throw new RuntimeException();
            }
        }

        return redirect("/superuser/tabs");
    }

    //name = tab name
    public Result fieldsGet(String name) {
        CurrentUser currentUser = sessionService.retrieveCurrentUserSession();
        ContentViewModelGet viewModelGet = new ContentViewModelGet();

        viewModelGet.setName(name);

        //get current custom fields
        ServiceResponse<List<TabFieldItem>> currentFieldItemsResponse = tabService.retrieveTabFieldsByTabName(name, false);
        if (currentFieldItemsResponse.hasErrors()) {
            throw new RuntimeException();
        }
        viewModelGet.setCurrentCustomFieldItemList(currentFieldItemsResponse.getResponseObject());

        //get removed custom fields
        ServiceResponse<List<TabFieldItem>> removedFieldItemsResponse = tabService.retrieveTabFieldsByTabName(name, true);
        if (currentFieldItemsResponse.hasErrors()) {
            throw new RuntimeException();
        }
        viewModelGet.setRemovedCustomFieldItemList(removedFieldItemsResponse.getResponseObject());

        //get available field types
        ServiceResponse<List<String>> fieldTypesResponse = tabService.retrieveTypes();
        if (fieldTypesResponse.hasErrors()) {
            throw new RuntimeException();
        }
        viewModelGet.setCustomFieldTypes(fieldTypesResponse.getResponseObject());

        //get available fields sizes
        ServiceResponse<List<String>> fieldSizesResponse = tabService.retrieveSizes();
        if (fieldSizesResponse.hasErrors()) {
            throw new RuntimeException();
        }
        viewModelGet.setCustomFieldSizes(fieldSizesResponse.getResponseObject());

        return ok(fields.render(currentUser, viewModelGet, assetsFinder));
    }

    //name = tab name
    public Result fieldsPost(String name) {
        CurrentUser currentUser = sessionService.retrieveCurrentUserSession();

        final Form<ContentViewModelPost> ContentViewModelForm = formFactory.form(ContentViewModelPost.class);
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
            if (tabService.doesTabFieldExist(viewModelPost.getAddName()).getResponseObject()) {
                tabService.updateTabField(tabFieldItem);
            } else {

                ServiceResponse<TabFieldItem> response = tabService.createTabField(tabFieldItem, currentUser.getId(), name);
                if (response.hasErrors()) {
                    throw new RuntimeException();
                }
            }
        }
        //deactivating a field
        if (StringUtils.isNotNullOrWhiteSpace(viewModelPost.getToggleName())) {
            ServiceResponse<TabFieldItem> response = tabService.toggleTabField(viewModelPost.getToggleName(), name);
            if (response.hasErrors()) {
                throw new RuntimeException();
            }
        }

        return redirect("/superuser/tabs/" + name);
    }
}
