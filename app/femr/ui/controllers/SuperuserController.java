package femr.ui.controllers;

import com.google.inject.Inject;
import femr.business.dtos.CurrentUser;
import femr.business.dtos.ServiceResponse;
import femr.business.services.ISessionService;
import femr.business.services.ISuperuserService;
import femr.common.models.Roles;
import femr.common.models.custom.ICustomTab;
import femr.data.models.custom.CustomTab;
import femr.ui.helpers.security.AllowedRoles;
import femr.ui.helpers.security.FEMRAuthenticated;
import femr.ui.models.data.custom.CustomFieldItem;
import femr.ui.models.data.custom.CustomTabItem;
import femr.ui.models.superuser.*;
import femr.util.stringhelpers.StringUtils;
import org.joda.time.DateTime;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import femr.ui.views.html.superuser.*;

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

        ServiceResponse<List<CustomTabItem>> response;
        response = superuserService.getCustomMedicalTabs(false);
        if (response.hasErrors()) {
            return internalServerError();
        }

        TabsViewModelGet viewModelGet = new TabsViewModelGet();
        viewModelGet.setCurrentTabs(response.getResponseObject());

        //get deleted tabs
        response = superuserService.getCustomMedicalTabs(true);
        if (response.hasErrors()) {
            return internalServerError();
        }
        viewModelGet.setDeletedTabs(response.getResponseObject());


        return ok(tabs.render(currentUser, viewModelGet));
    }

    public Result tabsPost() {
        CurrentUser currentUser = sessionService.getCurrentUserSession();
        TabsViewModelPost viewModelPost = TabsViewModelForm.bindFromRequest().get();

        //becomes new tab
        if (StringUtils.isNotNullOrWhiteSpace(viewModelPost.getAddTabName())) {
            if (superuserService.doesCustomTabExist(viewModelPost.getAddTabName()).getResponseObject() == false) {
                ICustomTab customTab = new CustomTab();
                customTab.setDateCreated(DateTime.now());
                customTab.setName(viewModelPost.getAddTabName());
                customTab.setUserId(currentUser.getId());
                customTab.setIsDeleted(false);
                if (viewModelPost.getAddTabLeft() != null)
                    customTab.setLeftColumnSize(viewModelPost.getAddTabLeft());
                if (viewModelPost.getAddTabRight() != null)
                    customTab.setRightColumnSize(viewModelPost.getAddTabRight());
                ServiceResponse<ICustomTab> response = superuserService.createCustomMedicalTab(customTab);
                if (response.hasErrors()) {
                    return internalServerError();
                }
            } else {
                CustomTabItem customTabItem = new CustomTabItem();
                if (viewModelPost.getAddTabLeft() == null) {
                    customTabItem.setLeftColumnSize(0);
                } else {
                    customTabItem.setLeftColumnSize(viewModelPost.getAddTabLeft());
                }
                if (viewModelPost.getAddTabRight() == null) {
                    customTabItem.setRightColumnSize(0);

                } else {
                    customTabItem.setRightColumnSize(viewModelPost.getAddTabRight());
                }

                customTabItem.setName(viewModelPost.getAddTabName());
                superuserService.editCustomTab(customTabItem, currentUser.getId());

            }

        }
        //becomes toggle
        if (StringUtils.isNotNullOrWhiteSpace(viewModelPost.getDeleteTab())) {
            ServiceResponse<ICustomTab> response = superuserService.toggleCustomMedicalTab(viewModelPost.getDeleteTab());
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
        ServiceResponse<List<CustomFieldItem>> currentFieldItemsResponse = superuserService.getCustomFields(false, name);
        if (currentFieldItemsResponse.hasErrors()) {
            return internalServerError();
        }
        viewModelGet.setCurrentCustomFieldItemList(currentFieldItemsResponse.getResponseObject());

        //get removed custom fields
        ServiceResponse<List<CustomFieldItem>> removedFieldItemsResponse = superuserService.getCustomFields(true, name);
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
            CustomFieldItem customFieldItem = new CustomFieldItem();
            customFieldItem.setName(viewModelPost.getAddName());
            customFieldItem.setSize(viewModelPost.getAddSize().toLowerCase());
            customFieldItem.setType(viewModelPost.getAddType().toLowerCase());
            customFieldItem.setOrder(viewModelPost.getAddOrder());
            customFieldItem.setPlaceholder(viewModelPost.getAddPlaceholder());
            //edit
            if (superuserService.doesCustomFieldExist(viewModelPost.getAddName()).getResponseObject() == true) {
                superuserService.editCustomField(customFieldItem, currentUser.getId());
            } else {

                ServiceResponse<CustomFieldItem> response = superuserService.createCustomField(customFieldItem, currentUser.getId(), name);
                if (response.hasErrors()) {
                    return internalServerError();
                }
            }
        }
        //deactivating a field
        if (StringUtils.isNotNullOrWhiteSpace(viewModelPost.getToggleName())) {
            ServiceResponse<CustomFieldItem> response = superuserService.toggleCustomField(viewModelPost.getToggleName(), name);
            if (response.hasErrors()) {
                return internalServerError();
            }
        }


        return redirect("/superuser/tabs/" + name);
    }

}
