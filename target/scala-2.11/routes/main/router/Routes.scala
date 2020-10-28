// @GENERATOR:play-routes-compiler
// @SOURCE:C:/Users/Noel/Documents/csc402/lemur-femr/femr/conf/routes
// @DATE:Tue Oct 27 20:43:13 PDT 2020

package router

import play.core.routing._
import play.core.routing.HandlerInvokerFactory._

import play.api.mvc._

import _root_.controllers.Assets.Asset
import _root_.play.libs.F

class Routes(
  override val errorHandler: play.api.http.HttpErrorHandler, 
  // @LINE:5
  ApplicationController_11: femr.ui.controllers.ApplicationController,
  // @LINE:6
  TabController_0: javax.inject.Provider[femr.ui.controllers.superuser.TabController],
  // @LINE:10
  SuperuserController_15: javax.inject.Provider[femr.ui.controllers.superuser.SuperuserController],
  // @LINE:12
  UsersController_10: javax.inject.Provider[femr.ui.controllers.admin.UsersController],
  // @LINE:18
  InventoryController_1: javax.inject.Provider[femr.ui.controllers.admin.InventoryController],
  // @LINE:29
  ConfigureController_6: javax.inject.Provider[femr.ui.controllers.admin.ConfigureController],
  // @LINE:31
  TripController_18: javax.inject.Provider[femr.ui.controllers.admin.TripController],
  // @LINE:39
  AdminController_12: javax.inject.Provider[femr.ui.controllers.admin.AdminController],
  // @LINE:41
  ManagerController_20: javax.inject.Provider[femr.ui.controllers.manager.ManagerController],
  // @LINE:43
  PharmaciesController_3: javax.inject.Provider[femr.ui.controllers.PharmaciesController],
  // @LINE:48
  HistoryController_5: javax.inject.Provider[femr.ui.controllers.HistoryController],
  // @LINE:51
  TriageController_4: javax.inject.Provider[femr.ui.controllers.TriageController],
  // @LINE:54
  SearchController_9: javax.inject.Provider[femr.ui.controllers.SearchController],
  // @LINE:63
  PhotoController_7: javax.inject.Provider[femr.ui.controllers.PhotoController],
  // @LINE:71
  MedicalController_19: javax.inject.Provider[femr.ui.controllers.MedicalController],
  // @LINE:81
  ResearchController_13: javax.inject.Provider[femr.ui.controllers.ResearchController],
  // @LINE:85
  PDFController_2: javax.inject.Provider[femr.ui.controllers.PDFController],
  // @LINE:87
  SessionsController_14: javax.inject.Provider[femr.ui.controllers.SessionsController],
  // @LINE:92
  ReferenceController_21: javax.inject.Provider[femr.ui.controllers.ReferenceController],
  // @LINE:94
  HomeController_8: javax.inject.Provider[femr.ui.controllers.HomeController],
  // @LINE:97
  Assets_17: controllers.Assets,
  // @LINE:100
  FeedbackController_16: javax.inject.Provider[femr.ui.controllers.FeedbackController],
  val prefix: String
) extends GeneratedRouter {

   @javax.inject.Inject()
   def this(errorHandler: play.api.http.HttpErrorHandler,
    // @LINE:5
    ApplicationController_11: femr.ui.controllers.ApplicationController,
    // @LINE:6
    TabController_0: javax.inject.Provider[femr.ui.controllers.superuser.TabController],
    // @LINE:10
    SuperuserController_15: javax.inject.Provider[femr.ui.controllers.superuser.SuperuserController],
    // @LINE:12
    UsersController_10: javax.inject.Provider[femr.ui.controllers.admin.UsersController],
    // @LINE:18
    InventoryController_1: javax.inject.Provider[femr.ui.controllers.admin.InventoryController],
    // @LINE:29
    ConfigureController_6: javax.inject.Provider[femr.ui.controllers.admin.ConfigureController],
    // @LINE:31
    TripController_18: javax.inject.Provider[femr.ui.controllers.admin.TripController],
    // @LINE:39
    AdminController_12: javax.inject.Provider[femr.ui.controllers.admin.AdminController],
    // @LINE:41
    ManagerController_20: javax.inject.Provider[femr.ui.controllers.manager.ManagerController],
    // @LINE:43
    PharmaciesController_3: javax.inject.Provider[femr.ui.controllers.PharmaciesController],
    // @LINE:48
    HistoryController_5: javax.inject.Provider[femr.ui.controllers.HistoryController],
    // @LINE:51
    TriageController_4: javax.inject.Provider[femr.ui.controllers.TriageController],
    // @LINE:54
    SearchController_9: javax.inject.Provider[femr.ui.controllers.SearchController],
    // @LINE:63
    PhotoController_7: javax.inject.Provider[femr.ui.controllers.PhotoController],
    // @LINE:71
    MedicalController_19: javax.inject.Provider[femr.ui.controllers.MedicalController],
    // @LINE:81
    ResearchController_13: javax.inject.Provider[femr.ui.controllers.ResearchController],
    // @LINE:85
    PDFController_2: javax.inject.Provider[femr.ui.controllers.PDFController],
    // @LINE:87
    SessionsController_14: javax.inject.Provider[femr.ui.controllers.SessionsController],
    // @LINE:92
    ReferenceController_21: javax.inject.Provider[femr.ui.controllers.ReferenceController],
    // @LINE:94
    HomeController_8: javax.inject.Provider[femr.ui.controllers.HomeController],
    // @LINE:97
    Assets_17: controllers.Assets,
    // @LINE:100
    FeedbackController_16: javax.inject.Provider[femr.ui.controllers.FeedbackController]
  ) = this(errorHandler, ApplicationController_11, TabController_0, SuperuserController_15, UsersController_10, InventoryController_1, ConfigureController_6, TripController_18, AdminController_12, ManagerController_20, PharmaciesController_3, HistoryController_5, TriageController_4, SearchController_9, PhotoController_7, MedicalController_19, ResearchController_13, PDFController_2, SessionsController_14, ReferenceController_21, HomeController_8, Assets_17, FeedbackController_16, "/")

  def withPrefix(prefix: String): Routes = {
    router.RoutesPrefix.setPrefix(prefix)
    new Routes(errorHandler, ApplicationController_11, TabController_0, SuperuserController_15, UsersController_10, InventoryController_1, ConfigureController_6, TripController_18, AdminController_12, ManagerController_20, PharmaciesController_3, HistoryController_5, TriageController_4, SearchController_9, PhotoController_7, MedicalController_19, ResearchController_13, PDFController_2, SessionsController_14, ReferenceController_21, HomeController_8, Assets_17, FeedbackController_16, prefix)
  }

  private[this] val defaultPrefix: String = {
    if (this.prefix.endsWith("/")) "" else "/"
  }

  def documentation = List(
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """""" + "$" + """path<.+>/""", """femr.ui.controllers.ApplicationController.removeTrailingSlash(path:String)"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """superuser/tabs/""" + "$" + """name<[^/]+>""", """@femr.ui.controllers.superuser.TabController@.fieldsGet(name:String)"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """superuser/tabs/""" + "$" + """name<[^/]+>""", """@femr.ui.controllers.superuser.TabController@.fieldsPost(name:String)"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """superuser/tabs""", """@femr.ui.controllers.superuser.TabController@.manageGet()"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """superuser/tabs""", """@femr.ui.controllers.superuser.TabController@.managePost()"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """superuser""", """@femr.ui.controllers.superuser.SuperuserController@.indexGet()"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """admin/users/create""", """@femr.ui.controllers.admin.UsersController@.createPost()"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """admin/users/create""", """@femr.ui.controllers.admin.UsersController@.createGet()"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """admin/users/edit""", """@femr.ui.controllers.admin.UsersController@.editPost(id:Integer)"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """admin/users/edit/""" + "$" + """id<[^/]+>""", """@femr.ui.controllers.admin.UsersController@.editGet(id:Integer)"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """admin/users/toggle/""" + "$" + """id<[^/]+>""", """@femr.ui.controllers.admin.UsersController@.toggleUser(id:Integer)"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """admin/users""", """@femr.ui.controllers.admin.UsersController@.manageGet()"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """admin/inventory/""" + "$" + """tripId<[^/]+>""", """@femr.ui.controllers.admin.InventoryController@.manageGet(tripId:Integer)"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """admin/inventory""", """@femr.ui.controllers.admin.InventoryController@.managePost()"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """admin/inventory/custom/""" + "$" + """tripId<[^/]+>""", """@femr.ui.controllers.admin.InventoryController@.customGet(tripId:Integer)"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """admin/inventory/custom/""" + "$" + """tripId<[^/]+>""", """@femr.ui.controllers.admin.InventoryController@.customPost(tripId:Integer)"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """admin/inventory/existing/""" + "$" + """tripId<[^/]+>""", """@femr.ui.controllers.admin.InventoryController@.existingGet(tripId:Integer)"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """admin/inventory/existing/""" + "$" + """tripId<[^/]+>""", """@femr.ui.controllers.admin.InventoryController@.existingPost(tripId:Integer)"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """admin/inventory/export/""" + "$" + """tripId<[^/]+>""", """@femr.ui.controllers.admin.InventoryController@.exportGet(tripId:Integer)"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """admin/inventory/delete/""" + "$" + """id<[^/]+>/""" + "$" + """tripId<[^/]+>""", """@femr.ui.controllers.admin.InventoryController@.ajaxDelete(id:Integer, tripId:Integer)"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """admin/inventory/readd/""" + "$" + """id<[^/]+>/""" + "$" + """tripId<[^/]+>""", """@femr.ui.controllers.admin.InventoryController@.ajaxReadd(id:Integer, tripId:Integer)"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """admin/inventory/editCurrent/""" + "$" + """id<[^/]+>/""" + "$" + """tripId<[^/]+>""", """@femr.ui.controllers.admin.InventoryController@.ajaxEditCurrent(id:Integer, tripId:Integer)"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """admin/inventory/editTotal/""" + "$" + """id<[^/]+>/""" + "$" + """tripId<[^/]+>""", """@femr.ui.controllers.admin.InventoryController@.ajaxEditTotal(id:Integer, tripId:Integer)"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """admin/configure""", """@femr.ui.controllers.admin.ConfigureController@.manageGet()"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """admin/configure""", """@femr.ui.controllers.admin.ConfigureController@.managePost()"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """admin/trips""", """@femr.ui.controllers.admin.TripController@.manageGet()"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """admin/trips""", """@femr.ui.controllers.admin.TripController@.managePost()"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """admin/trips/edit/""" + "$" + """id<[^/]+>""", """@femr.ui.controllers.admin.TripController@.editGet(id:Integer)"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """admin/trips/edit/""" + "$" + """id<[^/]+>""", """@femr.ui.controllers.admin.TripController@.editPost(id:Integer)"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """admin/trips/teams""", """@femr.ui.controllers.admin.TripController@.teamsGet()"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """admin/trips/teams""", """@femr.ui.controllers.admin.TripController@.teamsPost()"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """admin/trips/cities""", """@femr.ui.controllers.admin.TripController@.citiesGet()"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """admin/trips/cities""", """@femr.ui.controllers.admin.TripController@.citiesPost()"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """admin""", """@femr.ui.controllers.admin.AdminController@.index()"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """manager""", """@femr.ui.controllers.manager.ManagerController@.indexGet()"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """pharmacy/edit/""" + "$" + """id<[^/]+>""", """@femr.ui.controllers.PharmaciesController@.editGet(id:Integer)"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """pharmacy/edit/""" + "$" + """id<[^/]+>""", """@femr.ui.controllers.PharmaciesController@.editPost(id:Integer)"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """pharmacy""", """@femr.ui.controllers.PharmaciesController@.indexGet()"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """pharmacy""", """@femr.ui.controllers.PharmaciesController@.indexPost()"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """history/encounter/""" + "$" + """id<[^/]+>""", """@femr.ui.controllers.HistoryController@.indexEncounterGet(id:Integer)"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """history/encounter/updateField/""" + "$" + """id<[^/]+>""", """@femr.ui.controllers.HistoryController@.updateEncounterPost(id:Integer)"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """history/encounter/listTabFieldHistory/""" + "$" + """id<[^/]+>""", """@femr.ui.controllers.HistoryController@.listTabFieldHistoryGet(id:Integer)"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """history/encounter/delete/""" + "$" + """id<[^/]+>""", """@femr.ui.controllers.TriageController@.deleteEncounterPost(id:Integer, encounterId:Integer)"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """history/patient/""" + "$" + """query<[^/]+>""", """@femr.ui.controllers.HistoryController@.indexPatientGet(query:String)"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """search/""" + "$" + """page<[^/]+>""", """@femr.ui.controllers.SearchController@.handleSearch(page:String)"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """search/check/""" + "$" + """query<[^/]+>""", """@femr.ui.controllers.SearchController@.doesPatientExist(query:String)"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """search/typeahead/patients""", """@femr.ui.controllers.SearchController@.typeaheadPatientsJSONGet()"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """search/typeahead/cities""", """@femr.ui.controllers.SearchController@.typeaheadCitiesJSONGet()"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """search/typeahead/diagnoses""", """@femr.ui.controllers.SearchController@.typeaheadDiagnosisJSONGet()"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """search/typeahead/medicationsWithID""", """@femr.ui.controllers.SearchController@.typeaheadMedicationsWithIDJSONGet()"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """search/typeahead/medicationAdministrations""", """@femr.ui.controllers.SearchController@.typeaheadMedicationAdministrationsJSONGet()"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """photo/patient/""" + "$" + """id<[^/]+>""", """@femr.ui.controllers.PhotoController@.GetPatientPhoto(id:Integer, showDefault:Boolean ?= false)"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """photo/encounter/""" + "$" + """id<[^/]+>""", """@femr.ui.controllers.PhotoController@.GetPhoto(id:Int)"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """triage/""" + "$" + """id<[^/]+>""", """@femr.ui.controllers.TriageController@.indexPopulatedGet(id:Integer)"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """triage""", """@femr.ui.controllers.TriageController@.indexGet()"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """triage""", """@femr.ui.controllers.TriageController@.indexPost(id:Integer)"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """triage/""" + "$" + """id<[^/]+>""", """@femr.ui.controllers.TriageController@.deletePatientPost(id:Integer)"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """medical/updateVitals/""" + "$" + """id<[^/]+>""", """@femr.ui.controllers.MedicalController@.updateVitalsPost(id:Integer)"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """medical/newVitals""", """@femr.ui.controllers.MedicalController@.newVitalsGet()"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """medical/listVitals/""" + "$" + """id<[^/]+>""", """@femr.ui.controllers.MedicalController@.listVitalsGet(id:Integer)"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """medical/edit/""" + "$" + """id<[^/]+>""", """@femr.ui.controllers.MedicalController@.editGet(id:Integer)"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """medical/edit/""" + "$" + """id<[^/]+>""", """@femr.ui.controllers.MedicalController@.editPost(id:Integer)"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """medical""", """@femr.ui.controllers.MedicalController@.indexGet()"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """medical""", """@femr.ui.controllers.MedicalController@.indexPost()"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """medical/prescriptionRow/""" + "$" + """index<[^/]+>""", """@femr.ui.controllers.MedicalController@.prescriptionRowGet(index:Integer)"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """medical/deleteProblem/""" + "$" + """patientId<[^/]+>/""" + "$" + """problem<[^/]+>""", """@femr.ui.controllers.MedicalController@.deleteExistingProblem(patientId:Integer, problem:String)"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """research""", """@femr.ui.controllers.ResearchController@.indexGet()"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """research""", """@femr.ui.controllers.ResearchController@.indexPost()"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """research/exportData""", """@femr.ui.controllers.ResearchController@.exportPost()"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """pdf/encounter/""" + "$" + """encounterId<[^/]+>""", """@femr.ui.controllers.PDFController@.index(encounterId:Integer)"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """logout""", """@femr.ui.controllers.SessionsController@.delete()"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """login/reset""", """@femr.ui.controllers.SessionsController@.editPasswordPost()"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """login""", """@femr.ui.controllers.SessionsController@.createPost()"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """login""", """@femr.ui.controllers.SessionsController@.createGet()"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """reference""", """@femr.ui.controllers.ReferenceController@.indexGet()"""),
    ("""GET""", this.prefix, """@femr.ui.controllers.HomeController@.index()"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """assets/""" + "$" + """file<.+>""", """controllers.Assets.versioned(file:String)"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """feedback""", """@femr.ui.controllers.FeedbackController@.indexGet()"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """feedback""", """@femr.ui.controllers.FeedbackController@.indexPost()"""),
    Nil
  ).foldLeft(List.empty[(String,String,String)]) { (s,e) => e.asInstanceOf[Any] match {
    case r @ (_,_,_) => s :+ r.asInstanceOf[(String,String,String)]
    case l => s ++ l.asInstanceOf[List[(String,String,String)]]
  }}


  // @LINE:5
  private[this] lazy val femr_ui_controllers_ApplicationController_removeTrailingSlash0_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), DynamicPart("path", """.+""",false), StaticPart("/")))
  )
  private[this] lazy val femr_ui_controllers_ApplicationController_removeTrailingSlash0_invoker = createInvoker(
    ApplicationController_11.removeTrailingSlash(fakeValue[String]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "femr.ui.controllers.ApplicationController",
      "removeTrailingSlash",
      Seq(classOf[String]),
      "GET",
      this.prefix + """""" + "$" + """path<.+>/""",
      """ Routes
 This file defines all application routes (Higher priority routes first)
 ~~~~""",
      Seq()
    )
  )

  // @LINE:6
  private[this] lazy val femr_ui_controllers_superuser_TabController_fieldsGet1_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("superuser/tabs/"), DynamicPart("name", """[^/]+""",true)))
  )
  private[this] lazy val femr_ui_controllers_superuser_TabController_fieldsGet1_invoker = createInvoker(
    TabController_0.get.fieldsGet(fakeValue[String]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "femr.ui.controllers.superuser.TabController",
      "fieldsGet",
      Seq(classOf[String]),
      "GET",
      this.prefix + """superuser/tabs/""" + "$" + """name<[^/]+>""",
      """""",
      Seq()
    )
  )

  // @LINE:7
  private[this] lazy val femr_ui_controllers_superuser_TabController_fieldsPost2_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("superuser/tabs/"), DynamicPart("name", """[^/]+""",true)))
  )
  private[this] lazy val femr_ui_controllers_superuser_TabController_fieldsPost2_invoker = createInvoker(
    TabController_0.get.fieldsPost(fakeValue[String]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "femr.ui.controllers.superuser.TabController",
      "fieldsPost",
      Seq(classOf[String]),
      "POST",
      this.prefix + """superuser/tabs/""" + "$" + """name<[^/]+>""",
      """""",
      Seq()
    )
  )

  // @LINE:8
  private[this] lazy val femr_ui_controllers_superuser_TabController_manageGet3_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("superuser/tabs")))
  )
  private[this] lazy val femr_ui_controllers_superuser_TabController_manageGet3_invoker = createInvoker(
    TabController_0.get.manageGet(),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "femr.ui.controllers.superuser.TabController",
      "manageGet",
      Nil,
      "GET",
      this.prefix + """superuser/tabs""",
      """""",
      Seq()
    )
  )

  // @LINE:9
  private[this] lazy val femr_ui_controllers_superuser_TabController_managePost4_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("superuser/tabs")))
  )
  private[this] lazy val femr_ui_controllers_superuser_TabController_managePost4_invoker = createInvoker(
    TabController_0.get.managePost(),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "femr.ui.controllers.superuser.TabController",
      "managePost",
      Nil,
      "POST",
      this.prefix + """superuser/tabs""",
      """""",
      Seq()
    )
  )

  // @LINE:10
  private[this] lazy val femr_ui_controllers_superuser_SuperuserController_indexGet5_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("superuser")))
  )
  private[this] lazy val femr_ui_controllers_superuser_SuperuserController_indexGet5_invoker = createInvoker(
    SuperuserController_15.get.indexGet(),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "femr.ui.controllers.superuser.SuperuserController",
      "indexGet",
      Nil,
      "GET",
      this.prefix + """superuser""",
      """""",
      Seq()
    )
  )

  // @LINE:12
  private[this] lazy val femr_ui_controllers_admin_UsersController_createPost6_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("admin/users/create")))
  )
  private[this] lazy val femr_ui_controllers_admin_UsersController_createPost6_invoker = createInvoker(
    UsersController_10.get.createPost(),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "femr.ui.controllers.admin.UsersController",
      "createPost",
      Nil,
      "POST",
      this.prefix + """admin/users/create""",
      """Admin""",
      Seq()
    )
  )

  // @LINE:13
  private[this] lazy val femr_ui_controllers_admin_UsersController_createGet7_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("admin/users/create")))
  )
  private[this] lazy val femr_ui_controllers_admin_UsersController_createGet7_invoker = createInvoker(
    UsersController_10.get.createGet(),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "femr.ui.controllers.admin.UsersController",
      "createGet",
      Nil,
      "GET",
      this.prefix + """admin/users/create""",
      """""",
      Seq()
    )
  )

  // @LINE:14
  private[this] lazy val femr_ui_controllers_admin_UsersController_editPost8_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("admin/users/edit")))
  )
  private[this] lazy val femr_ui_controllers_admin_UsersController_editPost8_invoker = createInvoker(
    UsersController_10.get.editPost(fakeValue[Integer]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "femr.ui.controllers.admin.UsersController",
      "editPost",
      Seq(classOf[Integer]),
      "POST",
      this.prefix + """admin/users/edit""",
      """""",
      Seq()
    )
  )

  // @LINE:15
  private[this] lazy val femr_ui_controllers_admin_UsersController_editGet9_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("admin/users/edit/"), DynamicPart("id", """[^/]+""",true)))
  )
  private[this] lazy val femr_ui_controllers_admin_UsersController_editGet9_invoker = createInvoker(
    UsersController_10.get.editGet(fakeValue[Integer]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "femr.ui.controllers.admin.UsersController",
      "editGet",
      Seq(classOf[Integer]),
      "GET",
      this.prefix + """admin/users/edit/""" + "$" + """id<[^/]+>""",
      """""",
      Seq()
    )
  )

  // @LINE:16
  private[this] lazy val femr_ui_controllers_admin_UsersController_toggleUser10_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("admin/users/toggle/"), DynamicPart("id", """[^/]+""",true)))
  )
  private[this] lazy val femr_ui_controllers_admin_UsersController_toggleUser10_invoker = createInvoker(
    UsersController_10.get.toggleUser(fakeValue[Integer]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "femr.ui.controllers.admin.UsersController",
      "toggleUser",
      Seq(classOf[Integer]),
      "POST",
      this.prefix + """admin/users/toggle/""" + "$" + """id<[^/]+>""",
      """""",
      Seq()
    )
  )

  // @LINE:17
  private[this] lazy val femr_ui_controllers_admin_UsersController_manageGet11_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("admin/users")))
  )
  private[this] lazy val femr_ui_controllers_admin_UsersController_manageGet11_invoker = createInvoker(
    UsersController_10.get.manageGet(),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "femr.ui.controllers.admin.UsersController",
      "manageGet",
      Nil,
      "GET",
      this.prefix + """admin/users""",
      """""",
      Seq()
    )
  )

  // @LINE:18
  private[this] lazy val femr_ui_controllers_admin_InventoryController_manageGet12_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("admin/inventory/"), DynamicPart("tripId", """[^/]+""",true)))
  )
  private[this] lazy val femr_ui_controllers_admin_InventoryController_manageGet12_invoker = createInvoker(
    InventoryController_1.get.manageGet(fakeValue[Integer]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "femr.ui.controllers.admin.InventoryController",
      "manageGet",
      Seq(classOf[Integer]),
      "GET",
      this.prefix + """admin/inventory/""" + "$" + """tripId<[^/]+>""",
      """""",
      Seq()
    )
  )

  // @LINE:19
  private[this] lazy val femr_ui_controllers_admin_InventoryController_managePost13_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("admin/inventory")))
  )
  private[this] lazy val femr_ui_controllers_admin_InventoryController_managePost13_invoker = createInvoker(
    InventoryController_1.get.managePost(),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "femr.ui.controllers.admin.InventoryController",
      "managePost",
      Nil,
      "POST",
      this.prefix + """admin/inventory""",
      """""",
      Seq()
    )
  )

  // @LINE:20
  private[this] lazy val femr_ui_controllers_admin_InventoryController_customGet14_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("admin/inventory/custom/"), DynamicPart("tripId", """[^/]+""",true)))
  )
  private[this] lazy val femr_ui_controllers_admin_InventoryController_customGet14_invoker = createInvoker(
    InventoryController_1.get.customGet(fakeValue[Integer]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "femr.ui.controllers.admin.InventoryController",
      "customGet",
      Seq(classOf[Integer]),
      "GET",
      this.prefix + """admin/inventory/custom/""" + "$" + """tripId<[^/]+>""",
      """""",
      Seq()
    )
  )

  // @LINE:21
  private[this] lazy val femr_ui_controllers_admin_InventoryController_customPost15_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("admin/inventory/custom/"), DynamicPart("tripId", """[^/]+""",true)))
  )
  private[this] lazy val femr_ui_controllers_admin_InventoryController_customPost15_invoker = createInvoker(
    InventoryController_1.get.customPost(fakeValue[Integer]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "femr.ui.controllers.admin.InventoryController",
      "customPost",
      Seq(classOf[Integer]),
      "POST",
      this.prefix + """admin/inventory/custom/""" + "$" + """tripId<[^/]+>""",
      """""",
      Seq()
    )
  )

  // @LINE:22
  private[this] lazy val femr_ui_controllers_admin_InventoryController_existingGet16_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("admin/inventory/existing/"), DynamicPart("tripId", """[^/]+""",true)))
  )
  private[this] lazy val femr_ui_controllers_admin_InventoryController_existingGet16_invoker = createInvoker(
    InventoryController_1.get.existingGet(fakeValue[Integer]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "femr.ui.controllers.admin.InventoryController",
      "existingGet",
      Seq(classOf[Integer]),
      "GET",
      this.prefix + """admin/inventory/existing/""" + "$" + """tripId<[^/]+>""",
      """""",
      Seq()
    )
  )

  // @LINE:23
  private[this] lazy val femr_ui_controllers_admin_InventoryController_existingPost17_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("admin/inventory/existing/"), DynamicPart("tripId", """[^/]+""",true)))
  )
  private[this] lazy val femr_ui_controllers_admin_InventoryController_existingPost17_invoker = createInvoker(
    InventoryController_1.get.existingPost(fakeValue[Integer]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "femr.ui.controllers.admin.InventoryController",
      "existingPost",
      Seq(classOf[Integer]),
      "POST",
      this.prefix + """admin/inventory/existing/""" + "$" + """tripId<[^/]+>""",
      """""",
      Seq()
    )
  )

  // @LINE:24
  private[this] lazy val femr_ui_controllers_admin_InventoryController_exportGet18_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("admin/inventory/export/"), DynamicPart("tripId", """[^/]+""",true)))
  )
  private[this] lazy val femr_ui_controllers_admin_InventoryController_exportGet18_invoker = createInvoker(
    InventoryController_1.get.exportGet(fakeValue[Integer]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "femr.ui.controllers.admin.InventoryController",
      "exportGet",
      Seq(classOf[Integer]),
      "GET",
      this.prefix + """admin/inventory/export/""" + "$" + """tripId<[^/]+>""",
      """""",
      Seq()
    )
  )

  // @LINE:25
  private[this] lazy val femr_ui_controllers_admin_InventoryController_ajaxDelete19_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("admin/inventory/delete/"), DynamicPart("id", """[^/]+""",true), StaticPart("/"), DynamicPart("tripId", """[^/]+""",true)))
  )
  private[this] lazy val femr_ui_controllers_admin_InventoryController_ajaxDelete19_invoker = createInvoker(
    InventoryController_1.get.ajaxDelete(fakeValue[Integer], fakeValue[Integer]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "femr.ui.controllers.admin.InventoryController",
      "ajaxDelete",
      Seq(classOf[Integer], classOf[Integer]),
      "POST",
      this.prefix + """admin/inventory/delete/""" + "$" + """id<[^/]+>/""" + "$" + """tripId<[^/]+>""",
      """""",
      Seq()
    )
  )

  // @LINE:26
  private[this] lazy val femr_ui_controllers_admin_InventoryController_ajaxReadd20_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("admin/inventory/readd/"), DynamicPart("id", """[^/]+""",true), StaticPart("/"), DynamicPart("tripId", """[^/]+""",true)))
  )
  private[this] lazy val femr_ui_controllers_admin_InventoryController_ajaxReadd20_invoker = createInvoker(
    InventoryController_1.get.ajaxReadd(fakeValue[Integer], fakeValue[Integer]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "femr.ui.controllers.admin.InventoryController",
      "ajaxReadd",
      Seq(classOf[Integer], classOf[Integer]),
      "POST",
      this.prefix + """admin/inventory/readd/""" + "$" + """id<[^/]+>/""" + "$" + """tripId<[^/]+>""",
      """""",
      Seq()
    )
  )

  // @LINE:27
  private[this] lazy val femr_ui_controllers_admin_InventoryController_ajaxEditCurrent21_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("admin/inventory/editCurrent/"), DynamicPart("id", """[^/]+""",true), StaticPart("/"), DynamicPart("tripId", """[^/]+""",true)))
  )
  private[this] lazy val femr_ui_controllers_admin_InventoryController_ajaxEditCurrent21_invoker = createInvoker(
    InventoryController_1.get.ajaxEditCurrent(fakeValue[Integer], fakeValue[Integer]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "femr.ui.controllers.admin.InventoryController",
      "ajaxEditCurrent",
      Seq(classOf[Integer], classOf[Integer]),
      "POST",
      this.prefix + """admin/inventory/editCurrent/""" + "$" + """id<[^/]+>/""" + "$" + """tripId<[^/]+>""",
      """""",
      Seq()
    )
  )

  // @LINE:28
  private[this] lazy val femr_ui_controllers_admin_InventoryController_ajaxEditTotal22_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("admin/inventory/editTotal/"), DynamicPart("id", """[^/]+""",true), StaticPart("/"), DynamicPart("tripId", """[^/]+""",true)))
  )
  private[this] lazy val femr_ui_controllers_admin_InventoryController_ajaxEditTotal22_invoker = createInvoker(
    InventoryController_1.get.ajaxEditTotal(fakeValue[Integer], fakeValue[Integer]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "femr.ui.controllers.admin.InventoryController",
      "ajaxEditTotal",
      Seq(classOf[Integer], classOf[Integer]),
      "POST",
      this.prefix + """admin/inventory/editTotal/""" + "$" + """id<[^/]+>/""" + "$" + """tripId<[^/]+>""",
      """""",
      Seq()
    )
  )

  // @LINE:29
  private[this] lazy val femr_ui_controllers_admin_ConfigureController_manageGet23_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("admin/configure")))
  )
  private[this] lazy val femr_ui_controllers_admin_ConfigureController_manageGet23_invoker = createInvoker(
    ConfigureController_6.get.manageGet(),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "femr.ui.controllers.admin.ConfigureController",
      "manageGet",
      Nil,
      "GET",
      this.prefix + """admin/configure""",
      """""",
      Seq()
    )
  )

  // @LINE:30
  private[this] lazy val femr_ui_controllers_admin_ConfigureController_managePost24_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("admin/configure")))
  )
  private[this] lazy val femr_ui_controllers_admin_ConfigureController_managePost24_invoker = createInvoker(
    ConfigureController_6.get.managePost(),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "femr.ui.controllers.admin.ConfigureController",
      "managePost",
      Nil,
      "POST",
      this.prefix + """admin/configure""",
      """""",
      Seq()
    )
  )

  // @LINE:31
  private[this] lazy val femr_ui_controllers_admin_TripController_manageGet25_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("admin/trips")))
  )
  private[this] lazy val femr_ui_controllers_admin_TripController_manageGet25_invoker = createInvoker(
    TripController_18.get.manageGet(),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "femr.ui.controllers.admin.TripController",
      "manageGet",
      Nil,
      "GET",
      this.prefix + """admin/trips""",
      """""",
      Seq()
    )
  )

  // @LINE:32
  private[this] lazy val femr_ui_controllers_admin_TripController_managePost26_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("admin/trips")))
  )
  private[this] lazy val femr_ui_controllers_admin_TripController_managePost26_invoker = createInvoker(
    TripController_18.get.managePost(),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "femr.ui.controllers.admin.TripController",
      "managePost",
      Nil,
      "POST",
      this.prefix + """admin/trips""",
      """""",
      Seq()
    )
  )

  // @LINE:33
  private[this] lazy val femr_ui_controllers_admin_TripController_editGet27_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("admin/trips/edit/"), DynamicPart("id", """[^/]+""",true)))
  )
  private[this] lazy val femr_ui_controllers_admin_TripController_editGet27_invoker = createInvoker(
    TripController_18.get.editGet(fakeValue[Integer]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "femr.ui.controllers.admin.TripController",
      "editGet",
      Seq(classOf[Integer]),
      "GET",
      this.prefix + """admin/trips/edit/""" + "$" + """id<[^/]+>""",
      """""",
      Seq()
    )
  )

  // @LINE:34
  private[this] lazy val femr_ui_controllers_admin_TripController_editPost28_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("admin/trips/edit/"), DynamicPart("id", """[^/]+""",true)))
  )
  private[this] lazy val femr_ui_controllers_admin_TripController_editPost28_invoker = createInvoker(
    TripController_18.get.editPost(fakeValue[Integer]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "femr.ui.controllers.admin.TripController",
      "editPost",
      Seq(classOf[Integer]),
      "POST",
      this.prefix + """admin/trips/edit/""" + "$" + """id<[^/]+>""",
      """""",
      Seq()
    )
  )

  // @LINE:35
  private[this] lazy val femr_ui_controllers_admin_TripController_teamsGet29_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("admin/trips/teams")))
  )
  private[this] lazy val femr_ui_controllers_admin_TripController_teamsGet29_invoker = createInvoker(
    TripController_18.get.teamsGet(),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "femr.ui.controllers.admin.TripController",
      "teamsGet",
      Nil,
      "GET",
      this.prefix + """admin/trips/teams""",
      """""",
      Seq()
    )
  )

  // @LINE:36
  private[this] lazy val femr_ui_controllers_admin_TripController_teamsPost30_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("admin/trips/teams")))
  )
  private[this] lazy val femr_ui_controllers_admin_TripController_teamsPost30_invoker = createInvoker(
    TripController_18.get.teamsPost(),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "femr.ui.controllers.admin.TripController",
      "teamsPost",
      Nil,
      "POST",
      this.prefix + """admin/trips/teams""",
      """""",
      Seq()
    )
  )

  // @LINE:37
  private[this] lazy val femr_ui_controllers_admin_TripController_citiesGet31_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("admin/trips/cities")))
  )
  private[this] lazy val femr_ui_controllers_admin_TripController_citiesGet31_invoker = createInvoker(
    TripController_18.get.citiesGet(),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "femr.ui.controllers.admin.TripController",
      "citiesGet",
      Nil,
      "GET",
      this.prefix + """admin/trips/cities""",
      """""",
      Seq()
    )
  )

  // @LINE:38
  private[this] lazy val femr_ui_controllers_admin_TripController_citiesPost32_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("admin/trips/cities")))
  )
  private[this] lazy val femr_ui_controllers_admin_TripController_citiesPost32_invoker = createInvoker(
    TripController_18.get.citiesPost(),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "femr.ui.controllers.admin.TripController",
      "citiesPost",
      Nil,
      "POST",
      this.prefix + """admin/trips/cities""",
      """""",
      Seq()
    )
  )

  // @LINE:39
  private[this] lazy val femr_ui_controllers_admin_AdminController_index33_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("admin")))
  )
  private[this] lazy val femr_ui_controllers_admin_AdminController_index33_invoker = createInvoker(
    AdminController_12.get.index(),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "femr.ui.controllers.admin.AdminController",
      "index",
      Nil,
      "GET",
      this.prefix + """admin""",
      """""",
      Seq()
    )
  )

  // @LINE:41
  private[this] lazy val femr_ui_controllers_manager_ManagerController_indexGet34_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("manager")))
  )
  private[this] lazy val femr_ui_controllers_manager_ManagerController_indexGet34_invoker = createInvoker(
    ManagerController_20.get.indexGet(),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "femr.ui.controllers.manager.ManagerController",
      "indexGet",
      Nil,
      "GET",
      this.prefix + """manager""",
      """Manager""",
      Seq()
    )
  )

  // @LINE:43
  private[this] lazy val femr_ui_controllers_PharmaciesController_editGet35_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("pharmacy/edit/"), DynamicPart("id", """[^/]+""",true)))
  )
  private[this] lazy val femr_ui_controllers_PharmaciesController_editGet35_invoker = createInvoker(
    PharmaciesController_3.get.editGet(fakeValue[Integer]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "femr.ui.controllers.PharmaciesController",
      "editGet",
      Seq(classOf[Integer]),
      "GET",
      this.prefix + """pharmacy/edit/""" + "$" + """id<[^/]+>""",
      """Pharmacy""",
      Seq()
    )
  )

  // @LINE:44
  private[this] lazy val femr_ui_controllers_PharmaciesController_editPost36_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("pharmacy/edit/"), DynamicPart("id", """[^/]+""",true)))
  )
  private[this] lazy val femr_ui_controllers_PharmaciesController_editPost36_invoker = createInvoker(
    PharmaciesController_3.get.editPost(fakeValue[Integer]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "femr.ui.controllers.PharmaciesController",
      "editPost",
      Seq(classOf[Integer]),
      "POST",
      this.prefix + """pharmacy/edit/""" + "$" + """id<[^/]+>""",
      """""",
      Seq()
    )
  )

  // @LINE:45
  private[this] lazy val femr_ui_controllers_PharmaciesController_indexGet37_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("pharmacy")))
  )
  private[this] lazy val femr_ui_controllers_PharmaciesController_indexGet37_invoker = createInvoker(
    PharmaciesController_3.get.indexGet(),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "femr.ui.controllers.PharmaciesController",
      "indexGet",
      Nil,
      "GET",
      this.prefix + """pharmacy""",
      """""",
      Seq()
    )
  )

  // @LINE:46
  private[this] lazy val femr_ui_controllers_PharmaciesController_indexPost38_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("pharmacy")))
  )
  private[this] lazy val femr_ui_controllers_PharmaciesController_indexPost38_invoker = createInvoker(
    PharmaciesController_3.get.indexPost(),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "femr.ui.controllers.PharmaciesController",
      "indexPost",
      Nil,
      "POST",
      this.prefix + """pharmacy""",
      """""",
      Seq()
    )
  )

  // @LINE:48
  private[this] lazy val femr_ui_controllers_HistoryController_indexEncounterGet39_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("history/encounter/"), DynamicPart("id", """[^/]+""",true)))
  )
  private[this] lazy val femr_ui_controllers_HistoryController_indexEncounterGet39_invoker = createInvoker(
    HistoryController_5.get.indexEncounterGet(fakeValue[Integer]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "femr.ui.controllers.HistoryController",
      "indexEncounterGet",
      Seq(classOf[Integer]),
      "GET",
      this.prefix + """history/encounter/""" + "$" + """id<[^/]+>""",
      """History""",
      Seq()
    )
  )

  // @LINE:49
  private[this] lazy val femr_ui_controllers_HistoryController_updateEncounterPost40_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("history/encounter/updateField/"), DynamicPart("id", """[^/]+""",true)))
  )
  private[this] lazy val femr_ui_controllers_HistoryController_updateEncounterPost40_invoker = createInvoker(
    HistoryController_5.get.updateEncounterPost(fakeValue[Integer]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "femr.ui.controllers.HistoryController",
      "updateEncounterPost",
      Seq(classOf[Integer]),
      "POST",
      this.prefix + """history/encounter/updateField/""" + "$" + """id<[^/]+>""",
      """""",
      Seq()
    )
  )

  // @LINE:50
  private[this] lazy val femr_ui_controllers_HistoryController_listTabFieldHistoryGet41_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("history/encounter/listTabFieldHistory/"), DynamicPart("id", """[^/]+""",true)))
  )
  private[this] lazy val femr_ui_controllers_HistoryController_listTabFieldHistoryGet41_invoker = createInvoker(
    HistoryController_5.get.listTabFieldHistoryGet(fakeValue[Integer]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "femr.ui.controllers.HistoryController",
      "listTabFieldHistoryGet",
      Seq(classOf[Integer]),
      "GET",
      this.prefix + """history/encounter/listTabFieldHistory/""" + "$" + """id<[^/]+>""",
      """""",
      Seq()
    )
  )

  // @LINE:51
  private[this] lazy val femr_ui_controllers_TriageController_deleteEncounterPost42_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("history/encounter/delete/"), DynamicPart("id", """[^/]+""",true)))
  )
  private[this] lazy val femr_ui_controllers_TriageController_deleteEncounterPost42_invoker = createInvoker(
    TriageController_4.get.deleteEncounterPost(fakeValue[Integer], fakeValue[Integer]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "femr.ui.controllers.TriageController",
      "deleteEncounterPost",
      Seq(classOf[Integer], classOf[Integer]),
      "POST",
      this.prefix + """history/encounter/delete/""" + "$" + """id<[^/]+>""",
      """""",
      Seq()
    )
  )

  // @LINE:52
  private[this] lazy val femr_ui_controllers_HistoryController_indexPatientGet43_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("history/patient/"), DynamicPart("query", """[^/]+""",true)))
  )
  private[this] lazy val femr_ui_controllers_HistoryController_indexPatientGet43_invoker = createInvoker(
    HistoryController_5.get.indexPatientGet(fakeValue[String]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "femr.ui.controllers.HistoryController",
      "indexPatientGet",
      Seq(classOf[String]),
      "GET",
      this.prefix + """history/patient/""" + "$" + """query<[^/]+>""",
      """""",
      Seq()
    )
  )

  // @LINE:54
  private[this] lazy val femr_ui_controllers_SearchController_handleSearch44_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("search/"), DynamicPart("page", """[^/]+""",true)))
  )
  private[this] lazy val femr_ui_controllers_SearchController_handleSearch44_invoker = createInvoker(
    SearchController_9.get.handleSearch(fakeValue[String]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "femr.ui.controllers.SearchController",
      "handleSearch",
      Seq(classOf[String]),
      "GET",
      this.prefix + """search/""" + "$" + """page<[^/]+>""",
      """Search""",
      Seq()
    )
  )

  // @LINE:55
  private[this] lazy val femr_ui_controllers_SearchController_doesPatientExist45_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("search/check/"), DynamicPart("query", """[^/]+""",true)))
  )
  private[this] lazy val femr_ui_controllers_SearchController_doesPatientExist45_invoker = createInvoker(
    SearchController_9.get.doesPatientExist(fakeValue[String]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "femr.ui.controllers.SearchController",
      "doesPatientExist",
      Seq(classOf[String]),
      "GET",
      this.prefix + """search/check/""" + "$" + """query<[^/]+>""",
      """""",
      Seq()
    )
  )

  // @LINE:56
  private[this] lazy val femr_ui_controllers_SearchController_typeaheadPatientsJSONGet46_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("search/typeahead/patients")))
  )
  private[this] lazy val femr_ui_controllers_SearchController_typeaheadPatientsJSONGet46_invoker = createInvoker(
    SearchController_9.get.typeaheadPatientsJSONGet(),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "femr.ui.controllers.SearchController",
      "typeaheadPatientsJSONGet",
      Nil,
      "GET",
      this.prefix + """search/typeahead/patients""",
      """""",
      Seq()
    )
  )

  // @LINE:58
  private[this] lazy val femr_ui_controllers_SearchController_typeaheadCitiesJSONGet47_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("search/typeahead/cities")))
  )
  private[this] lazy val femr_ui_controllers_SearchController_typeaheadCitiesJSONGet47_invoker = createInvoker(
    SearchController_9.get.typeaheadCitiesJSONGet(),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "femr.ui.controllers.SearchController",
      "typeaheadCitiesJSONGet",
      Nil,
      "GET",
      this.prefix + """search/typeahead/cities""",
      """ AJ Saclayan!  Adding in typeahead""",
      Seq()
    )
  )

  // @LINE:59
  private[this] lazy val femr_ui_controllers_SearchController_typeaheadDiagnosisJSONGet48_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("search/typeahead/diagnoses")))
  )
  private[this] lazy val femr_ui_controllers_SearchController_typeaheadDiagnosisJSONGet48_invoker = createInvoker(
    SearchController_9.get.typeaheadDiagnosisJSONGet(),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "femr.ui.controllers.SearchController",
      "typeaheadDiagnosisJSONGet",
      Nil,
      "GET",
      this.prefix + """search/typeahead/diagnoses""",
      """""",
      Seq()
    )
  )

  // @LINE:60
  private[this] lazy val femr_ui_controllers_SearchController_typeaheadMedicationsWithIDJSONGet49_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("search/typeahead/medicationsWithID")))
  )
  private[this] lazy val femr_ui_controllers_SearchController_typeaheadMedicationsWithIDJSONGet49_invoker = createInvoker(
    SearchController_9.get.typeaheadMedicationsWithIDJSONGet(),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "femr.ui.controllers.SearchController",
      "typeaheadMedicationsWithIDJSONGet",
      Nil,
      "GET",
      this.prefix + """search/typeahead/medicationsWithID""",
      """""",
      Seq()
    )
  )

  // @LINE:61
  private[this] lazy val femr_ui_controllers_SearchController_typeaheadMedicationAdministrationsJSONGet50_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("search/typeahead/medicationAdministrations")))
  )
  private[this] lazy val femr_ui_controllers_SearchController_typeaheadMedicationAdministrationsJSONGet50_invoker = createInvoker(
    SearchController_9.get.typeaheadMedicationAdministrationsJSONGet(),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "femr.ui.controllers.SearchController",
      "typeaheadMedicationAdministrationsJSONGet",
      Nil,
      "GET",
      this.prefix + """search/typeahead/medicationAdministrations""",
      """""",
      Seq()
    )
  )

  // @LINE:63
  private[this] lazy val femr_ui_controllers_PhotoController_GetPatientPhoto51_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("photo/patient/"), DynamicPart("id", """[^/]+""",true)))
  )
  private[this] lazy val femr_ui_controllers_PhotoController_GetPatientPhoto51_invoker = createInvoker(
    PhotoController_7.get.GetPatientPhoto(fakeValue[Integer], fakeValue[Boolean]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "femr.ui.controllers.PhotoController",
      "GetPatientPhoto",
      Seq(classOf[Integer], classOf[Boolean]),
      "GET",
      this.prefix + """photo/patient/""" + "$" + """id<[^/]+>""",
      """Photo""",
      Seq()
    )
  )

  // @LINE:64
  private[this] lazy val femr_ui_controllers_PhotoController_GetPhoto52_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("photo/encounter/"), DynamicPart("id", """[^/]+""",true)))
  )
  private[this] lazy val femr_ui_controllers_PhotoController_GetPhoto52_invoker = createInvoker(
    PhotoController_7.get.GetPhoto(fakeValue[Int]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "femr.ui.controllers.PhotoController",
      "GetPhoto",
      Seq(classOf[Int]),
      "GET",
      this.prefix + """photo/encounter/""" + "$" + """id<[^/]+>""",
      """""",
      Seq()
    )
  )

  // @LINE:66
  private[this] lazy val femr_ui_controllers_TriageController_indexPopulatedGet53_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("triage/"), DynamicPart("id", """[^/]+""",true)))
  )
  private[this] lazy val femr_ui_controllers_TriageController_indexPopulatedGet53_invoker = createInvoker(
    TriageController_4.get.indexPopulatedGet(fakeValue[Integer]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "femr.ui.controllers.TriageController",
      "indexPopulatedGet",
      Seq(classOf[Integer]),
      "GET",
      this.prefix + """triage/""" + "$" + """id<[^/]+>""",
      """Triage""",
      Seq()
    )
  )

  // @LINE:67
  private[this] lazy val femr_ui_controllers_TriageController_indexGet54_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("triage")))
  )
  private[this] lazy val femr_ui_controllers_TriageController_indexGet54_invoker = createInvoker(
    TriageController_4.get.indexGet(),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "femr.ui.controllers.TriageController",
      "indexGet",
      Nil,
      "GET",
      this.prefix + """triage""",
      """""",
      Seq()
    )
  )

  // @LINE:68
  private[this] lazy val femr_ui_controllers_TriageController_indexPost55_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("triage")))
  )
  private[this] lazy val femr_ui_controllers_TriageController_indexPost55_invoker = createInvoker(
    TriageController_4.get.indexPost(fakeValue[Integer]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "femr.ui.controllers.TriageController",
      "indexPost",
      Seq(classOf[Integer]),
      "POST",
      this.prefix + """triage""",
      """""",
      Seq()
    )
  )

  // @LINE:69
  private[this] lazy val femr_ui_controllers_TriageController_deletePatientPost56_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("triage/"), DynamicPart("id", """[^/]+""",true)))
  )
  private[this] lazy val femr_ui_controllers_TriageController_deletePatientPost56_invoker = createInvoker(
    TriageController_4.get.deletePatientPost(fakeValue[Integer]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "femr.ui.controllers.TriageController",
      "deletePatientPost",
      Seq(classOf[Integer]),
      "POST",
      this.prefix + """triage/""" + "$" + """id<[^/]+>""",
      """""",
      Seq()
    )
  )

  // @LINE:71
  private[this] lazy val femr_ui_controllers_MedicalController_updateVitalsPost57_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("medical/updateVitals/"), DynamicPart("id", """[^/]+""",true)))
  )
  private[this] lazy val femr_ui_controllers_MedicalController_updateVitalsPost57_invoker = createInvoker(
    MedicalController_19.get.updateVitalsPost(fakeValue[Integer]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "femr.ui.controllers.MedicalController",
      "updateVitalsPost",
      Seq(classOf[Integer]),
      "POST",
      this.prefix + """medical/updateVitals/""" + "$" + """id<[^/]+>""",
      """Medical""",
      Seq()
    )
  )

  // @LINE:72
  private[this] lazy val femr_ui_controllers_MedicalController_newVitalsGet58_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("medical/newVitals")))
  )
  private[this] lazy val femr_ui_controllers_MedicalController_newVitalsGet58_invoker = createInvoker(
    MedicalController_19.get.newVitalsGet(),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "femr.ui.controllers.MedicalController",
      "newVitalsGet",
      Nil,
      "GET",
      this.prefix + """medical/newVitals""",
      """""",
      Seq()
    )
  )

  // @LINE:73
  private[this] lazy val femr_ui_controllers_MedicalController_listVitalsGet59_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("medical/listVitals/"), DynamicPart("id", """[^/]+""",true)))
  )
  private[this] lazy val femr_ui_controllers_MedicalController_listVitalsGet59_invoker = createInvoker(
    MedicalController_19.get.listVitalsGet(fakeValue[Integer]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "femr.ui.controllers.MedicalController",
      "listVitalsGet",
      Seq(classOf[Integer]),
      "GET",
      this.prefix + """medical/listVitals/""" + "$" + """id<[^/]+>""",
      """""",
      Seq()
    )
  )

  // @LINE:74
  private[this] lazy val femr_ui_controllers_MedicalController_editGet60_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("medical/edit/"), DynamicPart("id", """[^/]+""",true)))
  )
  private[this] lazy val femr_ui_controllers_MedicalController_editGet60_invoker = createInvoker(
    MedicalController_19.get.editGet(fakeValue[Integer]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "femr.ui.controllers.MedicalController",
      "editGet",
      Seq(classOf[Integer]),
      "GET",
      this.prefix + """medical/edit/""" + "$" + """id<[^/]+>""",
      """""",
      Seq()
    )
  )

  // @LINE:75
  private[this] lazy val femr_ui_controllers_MedicalController_editPost61_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("medical/edit/"), DynamicPart("id", """[^/]+""",true)))
  )
  private[this] lazy val femr_ui_controllers_MedicalController_editPost61_invoker = createInvoker(
    MedicalController_19.get.editPost(fakeValue[Integer]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "femr.ui.controllers.MedicalController",
      "editPost",
      Seq(classOf[Integer]),
      "POST",
      this.prefix + """medical/edit/""" + "$" + """id<[^/]+>""",
      """""",
      Seq()
    )
  )

  // @LINE:76
  private[this] lazy val femr_ui_controllers_MedicalController_indexGet62_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("medical")))
  )
  private[this] lazy val femr_ui_controllers_MedicalController_indexGet62_invoker = createInvoker(
    MedicalController_19.get.indexGet(),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "femr.ui.controllers.MedicalController",
      "indexGet",
      Nil,
      "GET",
      this.prefix + """medical""",
      """""",
      Seq()
    )
  )

  // @LINE:77
  private[this] lazy val femr_ui_controllers_MedicalController_indexPost63_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("medical")))
  )
  private[this] lazy val femr_ui_controllers_MedicalController_indexPost63_invoker = createInvoker(
    MedicalController_19.get.indexPost(),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "femr.ui.controllers.MedicalController",
      "indexPost",
      Nil,
      "POST",
      this.prefix + """medical""",
      """""",
      Seq()
    )
  )

  // @LINE:78
  private[this] lazy val femr_ui_controllers_MedicalController_prescriptionRowGet64_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("medical/prescriptionRow/"), DynamicPart("index", """[^/]+""",true)))
  )
  private[this] lazy val femr_ui_controllers_MedicalController_prescriptionRowGet64_invoker = createInvoker(
    MedicalController_19.get.prescriptionRowGet(fakeValue[Integer]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "femr.ui.controllers.MedicalController",
      "prescriptionRowGet",
      Seq(classOf[Integer]),
      "GET",
      this.prefix + """medical/prescriptionRow/""" + "$" + """index<[^/]+>""",
      """""",
      Seq()
    )
  )

  // @LINE:79
  private[this] lazy val femr_ui_controllers_MedicalController_deleteExistingProblem65_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("medical/deleteProblem/"), DynamicPart("patientId", """[^/]+""",true), StaticPart("/"), DynamicPart("problem", """[^/]+""",true)))
  )
  private[this] lazy val femr_ui_controllers_MedicalController_deleteExistingProblem65_invoker = createInvoker(
    MedicalController_19.get.deleteExistingProblem(fakeValue[Integer], fakeValue[String]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "femr.ui.controllers.MedicalController",
      "deleteExistingProblem",
      Seq(classOf[Integer], classOf[String]),
      "POST",
      this.prefix + """medical/deleteProblem/""" + "$" + """patientId<[^/]+>/""" + "$" + """problem<[^/]+>""",
      """""",
      Seq()
    )
  )

  // @LINE:81
  private[this] lazy val femr_ui_controllers_ResearchController_indexGet66_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("research")))
  )
  private[this] lazy val femr_ui_controllers_ResearchController_indexGet66_invoker = createInvoker(
    ResearchController_13.get.indexGet(),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "femr.ui.controllers.ResearchController",
      "indexGet",
      Nil,
      "GET",
      this.prefix + """research""",
      """Research""",
      Seq()
    )
  )

  // @LINE:82
  private[this] lazy val femr_ui_controllers_ResearchController_indexPost67_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("research")))
  )
  private[this] lazy val femr_ui_controllers_ResearchController_indexPost67_invoker = createInvoker(
    ResearchController_13.get.indexPost(),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "femr.ui.controllers.ResearchController",
      "indexPost",
      Nil,
      "POST",
      this.prefix + """research""",
      """""",
      Seq()
    )
  )

  // @LINE:83
  private[this] lazy val femr_ui_controllers_ResearchController_exportPost68_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("research/exportData")))
  )
  private[this] lazy val femr_ui_controllers_ResearchController_exportPost68_invoker = createInvoker(
    ResearchController_13.get.exportPost(),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "femr.ui.controllers.ResearchController",
      "exportPost",
      Nil,
      "POST",
      this.prefix + """research/exportData""",
      """""",
      Seq()
    )
  )

  // @LINE:85
  private[this] lazy val femr_ui_controllers_PDFController_index69_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("pdf/encounter/"), DynamicPart("encounterId", """[^/]+""",true)))
  )
  private[this] lazy val femr_ui_controllers_PDFController_index69_invoker = createInvoker(
    PDFController_2.get.index(fakeValue[Integer]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "femr.ui.controllers.PDFController",
      "index",
      Seq(classOf[Integer]),
      "GET",
      this.prefix + """pdf/encounter/""" + "$" + """encounterId<[^/]+>""",
      """Pdf""",
      Seq()
    )
  )

  // @LINE:87
  private[this] lazy val femr_ui_controllers_SessionsController_delete70_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("logout")))
  )
  private[this] lazy val femr_ui_controllers_SessionsController_delete70_invoker = createInvoker(
    SessionsController_14.get.delete(),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "femr.ui.controllers.SessionsController",
      "delete",
      Nil,
      "GET",
      this.prefix + """logout""",
      """Sessions""",
      Seq()
    )
  )

  // @LINE:88
  private[this] lazy val femr_ui_controllers_SessionsController_editPasswordPost71_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("login/reset")))
  )
  private[this] lazy val femr_ui_controllers_SessionsController_editPasswordPost71_invoker = createInvoker(
    SessionsController_14.get.editPasswordPost(),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "femr.ui.controllers.SessionsController",
      "editPasswordPost",
      Nil,
      "POST",
      this.prefix + """login/reset""",
      """""",
      Seq()
    )
  )

  // @LINE:89
  private[this] lazy val femr_ui_controllers_SessionsController_createPost72_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("login")))
  )
  private[this] lazy val femr_ui_controllers_SessionsController_createPost72_invoker = createInvoker(
    SessionsController_14.get.createPost(),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "femr.ui.controllers.SessionsController",
      "createPost",
      Nil,
      "POST",
      this.prefix + """login""",
      """""",
      Seq()
    )
  )

  // @LINE:90
  private[this] lazy val femr_ui_controllers_SessionsController_createGet73_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("login")))
  )
  private[this] lazy val femr_ui_controllers_SessionsController_createGet73_invoker = createInvoker(
    SessionsController_14.get.createGet(),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "femr.ui.controllers.SessionsController",
      "createGet",
      Nil,
      "GET",
      this.prefix + """login""",
      """""",
      Seq()
    )
  )

  // @LINE:92
  private[this] lazy val femr_ui_controllers_ReferenceController_indexGet74_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("reference")))
  )
  private[this] lazy val femr_ui_controllers_ReferenceController_indexGet74_invoker = createInvoker(
    ReferenceController_21.get.indexGet(),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "femr.ui.controllers.ReferenceController",
      "indexGet",
      Nil,
      "GET",
      this.prefix + """reference""",
      """Reference""",
      Seq()
    )
  )

  // @LINE:94
  private[this] lazy val femr_ui_controllers_HomeController_index75_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix)))
  )
  private[this] lazy val femr_ui_controllers_HomeController_index75_invoker = createInvoker(
    HomeController_8.get.index(),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "femr.ui.controllers.HomeController",
      "index",
      Nil,
      "GET",
      this.prefix + """""",
      """Home""",
      Seq()
    )
  )

  // @LINE:97
  private[this] lazy val controllers_Assets_versioned76_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("assets/"), DynamicPart("file", """.+""",false)))
  )
  private[this] lazy val controllers_Assets_versioned76_invoker = createInvoker(
    Assets_17.versioned(fakeValue[String]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.Assets",
      "versioned",
      Seq(classOf[String]),
      "GET",
      this.prefix + """assets/""" + "$" + """file<.+>""",
      """ Map static resources without global state""",
      Seq()
    )
  )

  // @LINE:100
  private[this] lazy val femr_ui_controllers_FeedbackController_indexGet77_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("feedback")))
  )
  private[this] lazy val femr_ui_controllers_FeedbackController_indexGet77_invoker = createInvoker(
    FeedbackController_16.get.indexGet(),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "femr.ui.controllers.FeedbackController",
      "indexGet",
      Nil,
      "GET",
      this.prefix + """feedback""",
      """Feedback""",
      Seq()
    )
  )

  // @LINE:101
  private[this] lazy val femr_ui_controllers_FeedbackController_indexPost78_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("feedback")))
  )
  private[this] lazy val femr_ui_controllers_FeedbackController_indexPost78_invoker = createInvoker(
    FeedbackController_16.get.indexPost(),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "femr.ui.controllers.FeedbackController",
      "indexPost",
      Nil,
      "POST",
      this.prefix + """feedback""",
      """""",
      Seq()
    )
  )


  def routes: PartialFunction[RequestHeader, Handler] = {
  
    // @LINE:5
    case femr_ui_controllers_ApplicationController_removeTrailingSlash0_route(params@_) =>
      call(params.fromPath[String]("path", None)) { (path) =>
        femr_ui_controllers_ApplicationController_removeTrailingSlash0_invoker.call(ApplicationController_11.removeTrailingSlash(path))
      }
  
    // @LINE:6
    case femr_ui_controllers_superuser_TabController_fieldsGet1_route(params@_) =>
      call(params.fromPath[String]("name", None)) { (name) =>
        femr_ui_controllers_superuser_TabController_fieldsGet1_invoker.call(TabController_0.get.fieldsGet(name))
      }
  
    // @LINE:7
    case femr_ui_controllers_superuser_TabController_fieldsPost2_route(params@_) =>
      call(params.fromPath[String]("name", None)) { (name) =>
        femr_ui_controllers_superuser_TabController_fieldsPost2_invoker.call(TabController_0.get.fieldsPost(name))
      }
  
    // @LINE:8
    case femr_ui_controllers_superuser_TabController_manageGet3_route(params@_) =>
      call { 
        femr_ui_controllers_superuser_TabController_manageGet3_invoker.call(TabController_0.get.manageGet())
      }
  
    // @LINE:9
    case femr_ui_controllers_superuser_TabController_managePost4_route(params@_) =>
      call { 
        femr_ui_controllers_superuser_TabController_managePost4_invoker.call(TabController_0.get.managePost())
      }
  
    // @LINE:10
    case femr_ui_controllers_superuser_SuperuserController_indexGet5_route(params@_) =>
      call { 
        femr_ui_controllers_superuser_SuperuserController_indexGet5_invoker.call(SuperuserController_15.get.indexGet())
      }
  
    // @LINE:12
    case femr_ui_controllers_admin_UsersController_createPost6_route(params@_) =>
      call { 
        femr_ui_controllers_admin_UsersController_createPost6_invoker.call(UsersController_10.get.createPost())
      }
  
    // @LINE:13
    case femr_ui_controllers_admin_UsersController_createGet7_route(params@_) =>
      call { 
        femr_ui_controllers_admin_UsersController_createGet7_invoker.call(UsersController_10.get.createGet())
      }
  
    // @LINE:14
    case femr_ui_controllers_admin_UsersController_editPost8_route(params@_) =>
      call(params.fromQuery[Integer]("id", None)) { (id) =>
        femr_ui_controllers_admin_UsersController_editPost8_invoker.call(UsersController_10.get.editPost(id))
      }
  
    // @LINE:15
    case femr_ui_controllers_admin_UsersController_editGet9_route(params@_) =>
      call(params.fromPath[Integer]("id", None)) { (id) =>
        femr_ui_controllers_admin_UsersController_editGet9_invoker.call(UsersController_10.get.editGet(id))
      }
  
    // @LINE:16
    case femr_ui_controllers_admin_UsersController_toggleUser10_route(params@_) =>
      call(params.fromPath[Integer]("id", None)) { (id) =>
        femr_ui_controllers_admin_UsersController_toggleUser10_invoker.call(UsersController_10.get.toggleUser(id))
      }
  
    // @LINE:17
    case femr_ui_controllers_admin_UsersController_manageGet11_route(params@_) =>
      call { 
        femr_ui_controllers_admin_UsersController_manageGet11_invoker.call(UsersController_10.get.manageGet())
      }
  
    // @LINE:18
    case femr_ui_controllers_admin_InventoryController_manageGet12_route(params@_) =>
      call(params.fromPath[Integer]("tripId", None)) { (tripId) =>
        femr_ui_controllers_admin_InventoryController_manageGet12_invoker.call(InventoryController_1.get.manageGet(tripId))
      }
  
    // @LINE:19
    case femr_ui_controllers_admin_InventoryController_managePost13_route(params@_) =>
      call { 
        femr_ui_controllers_admin_InventoryController_managePost13_invoker.call(InventoryController_1.get.managePost())
      }
  
    // @LINE:20
    case femr_ui_controllers_admin_InventoryController_customGet14_route(params@_) =>
      call(params.fromPath[Integer]("tripId", None)) { (tripId) =>
        femr_ui_controllers_admin_InventoryController_customGet14_invoker.call(InventoryController_1.get.customGet(tripId))
      }
  
    // @LINE:21
    case femr_ui_controllers_admin_InventoryController_customPost15_route(params@_) =>
      call(params.fromPath[Integer]("tripId", None)) { (tripId) =>
        femr_ui_controllers_admin_InventoryController_customPost15_invoker.call(InventoryController_1.get.customPost(tripId))
      }
  
    // @LINE:22
    case femr_ui_controllers_admin_InventoryController_existingGet16_route(params@_) =>
      call(params.fromPath[Integer]("tripId", None)) { (tripId) =>
        femr_ui_controllers_admin_InventoryController_existingGet16_invoker.call(InventoryController_1.get.existingGet(tripId))
      }
  
    // @LINE:23
    case femr_ui_controllers_admin_InventoryController_existingPost17_route(params@_) =>
      call(params.fromPath[Integer]("tripId", None)) { (tripId) =>
        femr_ui_controllers_admin_InventoryController_existingPost17_invoker.call(InventoryController_1.get.existingPost(tripId))
      }
  
    // @LINE:24
    case femr_ui_controllers_admin_InventoryController_exportGet18_route(params@_) =>
      call(params.fromPath[Integer]("tripId", None)) { (tripId) =>
        femr_ui_controllers_admin_InventoryController_exportGet18_invoker.call(InventoryController_1.get.exportGet(tripId))
      }
  
    // @LINE:25
    case femr_ui_controllers_admin_InventoryController_ajaxDelete19_route(params@_) =>
      call(params.fromPath[Integer]("id", None), params.fromPath[Integer]("tripId", None)) { (id, tripId) =>
        femr_ui_controllers_admin_InventoryController_ajaxDelete19_invoker.call(InventoryController_1.get.ajaxDelete(id, tripId))
      }
  
    // @LINE:26
    case femr_ui_controllers_admin_InventoryController_ajaxReadd20_route(params@_) =>
      call(params.fromPath[Integer]("id", None), params.fromPath[Integer]("tripId", None)) { (id, tripId) =>
        femr_ui_controllers_admin_InventoryController_ajaxReadd20_invoker.call(InventoryController_1.get.ajaxReadd(id, tripId))
      }
  
    // @LINE:27
    case femr_ui_controllers_admin_InventoryController_ajaxEditCurrent21_route(params@_) =>
      call(params.fromPath[Integer]("id", None), params.fromPath[Integer]("tripId", None)) { (id, tripId) =>
        femr_ui_controllers_admin_InventoryController_ajaxEditCurrent21_invoker.call(InventoryController_1.get.ajaxEditCurrent(id, tripId))
      }
  
    // @LINE:28
    case femr_ui_controllers_admin_InventoryController_ajaxEditTotal22_route(params@_) =>
      call(params.fromPath[Integer]("id", None), params.fromPath[Integer]("tripId", None)) { (id, tripId) =>
        femr_ui_controllers_admin_InventoryController_ajaxEditTotal22_invoker.call(InventoryController_1.get.ajaxEditTotal(id, tripId))
      }
  
    // @LINE:29
    case femr_ui_controllers_admin_ConfigureController_manageGet23_route(params@_) =>
      call { 
        femr_ui_controllers_admin_ConfigureController_manageGet23_invoker.call(ConfigureController_6.get.manageGet())
      }
  
    // @LINE:30
    case femr_ui_controllers_admin_ConfigureController_managePost24_route(params@_) =>
      call { 
        femr_ui_controllers_admin_ConfigureController_managePost24_invoker.call(ConfigureController_6.get.managePost())
      }
  
    // @LINE:31
    case femr_ui_controllers_admin_TripController_manageGet25_route(params@_) =>
      call { 
        femr_ui_controllers_admin_TripController_manageGet25_invoker.call(TripController_18.get.manageGet())
      }
  
    // @LINE:32
    case femr_ui_controllers_admin_TripController_managePost26_route(params@_) =>
      call { 
        femr_ui_controllers_admin_TripController_managePost26_invoker.call(TripController_18.get.managePost())
      }
  
    // @LINE:33
    case femr_ui_controllers_admin_TripController_editGet27_route(params@_) =>
      call(params.fromPath[Integer]("id", None)) { (id) =>
        femr_ui_controllers_admin_TripController_editGet27_invoker.call(TripController_18.get.editGet(id))
      }
  
    // @LINE:34
    case femr_ui_controllers_admin_TripController_editPost28_route(params@_) =>
      call(params.fromPath[Integer]("id", None)) { (id) =>
        femr_ui_controllers_admin_TripController_editPost28_invoker.call(TripController_18.get.editPost(id))
      }
  
    // @LINE:35
    case femr_ui_controllers_admin_TripController_teamsGet29_route(params@_) =>
      call { 
        femr_ui_controllers_admin_TripController_teamsGet29_invoker.call(TripController_18.get.teamsGet())
      }
  
    // @LINE:36
    case femr_ui_controllers_admin_TripController_teamsPost30_route(params@_) =>
      call { 
        femr_ui_controllers_admin_TripController_teamsPost30_invoker.call(TripController_18.get.teamsPost())
      }
  
    // @LINE:37
    case femr_ui_controllers_admin_TripController_citiesGet31_route(params@_) =>
      call { 
        femr_ui_controllers_admin_TripController_citiesGet31_invoker.call(TripController_18.get.citiesGet())
      }
  
    // @LINE:38
    case femr_ui_controllers_admin_TripController_citiesPost32_route(params@_) =>
      call { 
        femr_ui_controllers_admin_TripController_citiesPost32_invoker.call(TripController_18.get.citiesPost())
      }
  
    // @LINE:39
    case femr_ui_controllers_admin_AdminController_index33_route(params@_) =>
      call { 
        femr_ui_controllers_admin_AdminController_index33_invoker.call(AdminController_12.get.index())
      }
  
    // @LINE:41
    case femr_ui_controllers_manager_ManagerController_indexGet34_route(params@_) =>
      call { 
        femr_ui_controllers_manager_ManagerController_indexGet34_invoker.call(ManagerController_20.get.indexGet())
      }
  
    // @LINE:43
    case femr_ui_controllers_PharmaciesController_editGet35_route(params@_) =>
      call(params.fromPath[Integer]("id", None)) { (id) =>
        femr_ui_controllers_PharmaciesController_editGet35_invoker.call(PharmaciesController_3.get.editGet(id))
      }
  
    // @LINE:44
    case femr_ui_controllers_PharmaciesController_editPost36_route(params@_) =>
      call(params.fromPath[Integer]("id", None)) { (id) =>
        femr_ui_controllers_PharmaciesController_editPost36_invoker.call(PharmaciesController_3.get.editPost(id))
      }
  
    // @LINE:45
    case femr_ui_controllers_PharmaciesController_indexGet37_route(params@_) =>
      call { 
        femr_ui_controllers_PharmaciesController_indexGet37_invoker.call(PharmaciesController_3.get.indexGet())
      }
  
    // @LINE:46
    case femr_ui_controllers_PharmaciesController_indexPost38_route(params@_) =>
      call { 
        femr_ui_controllers_PharmaciesController_indexPost38_invoker.call(PharmaciesController_3.get.indexPost())
      }
  
    // @LINE:48
    case femr_ui_controllers_HistoryController_indexEncounterGet39_route(params@_) =>
      call(params.fromPath[Integer]("id", None)) { (id) =>
        femr_ui_controllers_HistoryController_indexEncounterGet39_invoker.call(HistoryController_5.get.indexEncounterGet(id))
      }
  
    // @LINE:49
    case femr_ui_controllers_HistoryController_updateEncounterPost40_route(params@_) =>
      call(params.fromPath[Integer]("id", None)) { (id) =>
        femr_ui_controllers_HistoryController_updateEncounterPost40_invoker.call(HistoryController_5.get.updateEncounterPost(id))
      }
  
    // @LINE:50
    case femr_ui_controllers_HistoryController_listTabFieldHistoryGet41_route(params@_) =>
      call(params.fromPath[Integer]("id", None)) { (id) =>
        femr_ui_controllers_HistoryController_listTabFieldHistoryGet41_invoker.call(HistoryController_5.get.listTabFieldHistoryGet(id))
      }
  
    // @LINE:51
    case femr_ui_controllers_TriageController_deleteEncounterPost42_route(params@_) =>
      call(params.fromPath[Integer]("id", None), params.fromQuery[Integer]("encounterId", None)) { (id, encounterId) =>
        femr_ui_controllers_TriageController_deleteEncounterPost42_invoker.call(TriageController_4.get.deleteEncounterPost(id, encounterId))
      }
  
    // @LINE:52
    case femr_ui_controllers_HistoryController_indexPatientGet43_route(params@_) =>
      call(params.fromPath[String]("query", None)) { (query) =>
        femr_ui_controllers_HistoryController_indexPatientGet43_invoker.call(HistoryController_5.get.indexPatientGet(query))
      }
  
    // @LINE:54
    case femr_ui_controllers_SearchController_handleSearch44_route(params@_) =>
      call(params.fromPath[String]("page", None)) { (page) =>
        femr_ui_controllers_SearchController_handleSearch44_invoker.call(SearchController_9.get.handleSearch(page))
      }
  
    // @LINE:55
    case femr_ui_controllers_SearchController_doesPatientExist45_route(params@_) =>
      call(params.fromPath[String]("query", None)) { (query) =>
        femr_ui_controllers_SearchController_doesPatientExist45_invoker.call(SearchController_9.get.doesPatientExist(query))
      }
  
    // @LINE:56
    case femr_ui_controllers_SearchController_typeaheadPatientsJSONGet46_route(params@_) =>
      call { 
        femr_ui_controllers_SearchController_typeaheadPatientsJSONGet46_invoker.call(SearchController_9.get.typeaheadPatientsJSONGet())
      }
  
    // @LINE:58
    case femr_ui_controllers_SearchController_typeaheadCitiesJSONGet47_route(params@_) =>
      call { 
        femr_ui_controllers_SearchController_typeaheadCitiesJSONGet47_invoker.call(SearchController_9.get.typeaheadCitiesJSONGet())
      }
  
    // @LINE:59
    case femr_ui_controllers_SearchController_typeaheadDiagnosisJSONGet48_route(params@_) =>
      call { 
        femr_ui_controllers_SearchController_typeaheadDiagnosisJSONGet48_invoker.call(SearchController_9.get.typeaheadDiagnosisJSONGet())
      }
  
    // @LINE:60
    case femr_ui_controllers_SearchController_typeaheadMedicationsWithIDJSONGet49_route(params@_) =>
      call { 
        femr_ui_controllers_SearchController_typeaheadMedicationsWithIDJSONGet49_invoker.call(SearchController_9.get.typeaheadMedicationsWithIDJSONGet())
      }
  
    // @LINE:61
    case femr_ui_controllers_SearchController_typeaheadMedicationAdministrationsJSONGet50_route(params@_) =>
      call { 
        femr_ui_controllers_SearchController_typeaheadMedicationAdministrationsJSONGet50_invoker.call(SearchController_9.get.typeaheadMedicationAdministrationsJSONGet())
      }
  
    // @LINE:63
    case femr_ui_controllers_PhotoController_GetPatientPhoto51_route(params@_) =>
      call(params.fromPath[Integer]("id", None), params.fromQuery[Boolean]("showDefault", Some(false))) { (id, showDefault) =>
        femr_ui_controllers_PhotoController_GetPatientPhoto51_invoker.call(PhotoController_7.get.GetPatientPhoto(id, showDefault))
      }
  
    // @LINE:64
    case femr_ui_controllers_PhotoController_GetPhoto52_route(params@_) =>
      call(params.fromPath[Int]("id", None)) { (id) =>
        femr_ui_controllers_PhotoController_GetPhoto52_invoker.call(PhotoController_7.get.GetPhoto(id))
      }
  
    // @LINE:66
    case femr_ui_controllers_TriageController_indexPopulatedGet53_route(params@_) =>
      call(params.fromPath[Integer]("id", None)) { (id) =>
        femr_ui_controllers_TriageController_indexPopulatedGet53_invoker.call(TriageController_4.get.indexPopulatedGet(id))
      }
  
    // @LINE:67
    case femr_ui_controllers_TriageController_indexGet54_route(params@_) =>
      call { 
        femr_ui_controllers_TriageController_indexGet54_invoker.call(TriageController_4.get.indexGet())
      }
  
    // @LINE:68
    case femr_ui_controllers_TriageController_indexPost55_route(params@_) =>
      call(params.fromQuery[Integer]("id", None)) { (id) =>
        femr_ui_controllers_TriageController_indexPost55_invoker.call(TriageController_4.get.indexPost(id))
      }
  
    // @LINE:69
    case femr_ui_controllers_TriageController_deletePatientPost56_route(params@_) =>
      call(params.fromPath[Integer]("id", None)) { (id) =>
        femr_ui_controllers_TriageController_deletePatientPost56_invoker.call(TriageController_4.get.deletePatientPost(id))
      }
  
    // @LINE:71
    case femr_ui_controllers_MedicalController_updateVitalsPost57_route(params@_) =>
      call(params.fromPath[Integer]("id", None)) { (id) =>
        femr_ui_controllers_MedicalController_updateVitalsPost57_invoker.call(MedicalController_19.get.updateVitalsPost(id))
      }
  
    // @LINE:72
    case femr_ui_controllers_MedicalController_newVitalsGet58_route(params@_) =>
      call { 
        femr_ui_controllers_MedicalController_newVitalsGet58_invoker.call(MedicalController_19.get.newVitalsGet())
      }
  
    // @LINE:73
    case femr_ui_controllers_MedicalController_listVitalsGet59_route(params@_) =>
      call(params.fromPath[Integer]("id", None)) { (id) =>
        femr_ui_controllers_MedicalController_listVitalsGet59_invoker.call(MedicalController_19.get.listVitalsGet(id))
      }
  
    // @LINE:74
    case femr_ui_controllers_MedicalController_editGet60_route(params@_) =>
      call(params.fromPath[Integer]("id", None)) { (id) =>
        femr_ui_controllers_MedicalController_editGet60_invoker.call(MedicalController_19.get.editGet(id))
      }
  
    // @LINE:75
    case femr_ui_controllers_MedicalController_editPost61_route(params@_) =>
      call(params.fromPath[Integer]("id", None)) { (id) =>
        femr_ui_controllers_MedicalController_editPost61_invoker.call(MedicalController_19.get.editPost(id))
      }
  
    // @LINE:76
    case femr_ui_controllers_MedicalController_indexGet62_route(params@_) =>
      call { 
        femr_ui_controllers_MedicalController_indexGet62_invoker.call(MedicalController_19.get.indexGet())
      }
  
    // @LINE:77
    case femr_ui_controllers_MedicalController_indexPost63_route(params@_) =>
      call { 
        femr_ui_controllers_MedicalController_indexPost63_invoker.call(MedicalController_19.get.indexPost())
      }
  
    // @LINE:78
    case femr_ui_controllers_MedicalController_prescriptionRowGet64_route(params@_) =>
      call(params.fromPath[Integer]("index", None)) { (index) =>
        femr_ui_controllers_MedicalController_prescriptionRowGet64_invoker.call(MedicalController_19.get.prescriptionRowGet(index))
      }
  
    // @LINE:79
    case femr_ui_controllers_MedicalController_deleteExistingProblem65_route(params@_) =>
      call(params.fromPath[Integer]("patientId", None), params.fromPath[String]("problem", None)) { (patientId, problem) =>
        femr_ui_controllers_MedicalController_deleteExistingProblem65_invoker.call(MedicalController_19.get.deleteExistingProblem(patientId, problem))
      }
  
    // @LINE:81
    case femr_ui_controllers_ResearchController_indexGet66_route(params@_) =>
      call { 
        femr_ui_controllers_ResearchController_indexGet66_invoker.call(ResearchController_13.get.indexGet())
      }
  
    // @LINE:82
    case femr_ui_controllers_ResearchController_indexPost67_route(params@_) =>
      call { 
        femr_ui_controllers_ResearchController_indexPost67_invoker.call(ResearchController_13.get.indexPost())
      }
  
    // @LINE:83
    case femr_ui_controllers_ResearchController_exportPost68_route(params@_) =>
      call { 
        femr_ui_controllers_ResearchController_exportPost68_invoker.call(ResearchController_13.get.exportPost())
      }
  
    // @LINE:85
    case femr_ui_controllers_PDFController_index69_route(params@_) =>
      call(params.fromPath[Integer]("encounterId", None)) { (encounterId) =>
        femr_ui_controllers_PDFController_index69_invoker.call(PDFController_2.get.index(encounterId))
      }
  
    // @LINE:87
    case femr_ui_controllers_SessionsController_delete70_route(params@_) =>
      call { 
        femr_ui_controllers_SessionsController_delete70_invoker.call(SessionsController_14.get.delete())
      }
  
    // @LINE:88
    case femr_ui_controllers_SessionsController_editPasswordPost71_route(params@_) =>
      call { 
        femr_ui_controllers_SessionsController_editPasswordPost71_invoker.call(SessionsController_14.get.editPasswordPost())
      }
  
    // @LINE:89
    case femr_ui_controllers_SessionsController_createPost72_route(params@_) =>
      call { 
        femr_ui_controllers_SessionsController_createPost72_invoker.call(SessionsController_14.get.createPost())
      }
  
    // @LINE:90
    case femr_ui_controllers_SessionsController_createGet73_route(params@_) =>
      call { 
        femr_ui_controllers_SessionsController_createGet73_invoker.call(SessionsController_14.get.createGet())
      }
  
    // @LINE:92
    case femr_ui_controllers_ReferenceController_indexGet74_route(params@_) =>
      call { 
        femr_ui_controllers_ReferenceController_indexGet74_invoker.call(ReferenceController_21.get.indexGet())
      }
  
    // @LINE:94
    case femr_ui_controllers_HomeController_index75_route(params@_) =>
      call { 
        femr_ui_controllers_HomeController_index75_invoker.call(HomeController_8.get.index())
      }
  
    // @LINE:97
    case controllers_Assets_versioned76_route(params@_) =>
      call(params.fromPath[String]("file", None)) { (file) =>
        controllers_Assets_versioned76_invoker.call(Assets_17.versioned(file))
      }
  
    // @LINE:100
    case femr_ui_controllers_FeedbackController_indexGet77_route(params@_) =>
      call { 
        femr_ui_controllers_FeedbackController_indexGet77_invoker.call(FeedbackController_16.get.indexGet())
      }
  
    // @LINE:101
    case femr_ui_controllers_FeedbackController_indexPost78_route(params@_) =>
      call { 
        femr_ui_controllers_FeedbackController_indexPost78_invoker.call(FeedbackController_16.get.indexPost())
      }
  }
}
