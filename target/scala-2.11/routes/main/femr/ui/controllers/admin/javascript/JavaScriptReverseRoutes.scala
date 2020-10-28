// @GENERATOR:play-routes-compiler
// @SOURCE:C:/Users/Noel/Documents/csc402/lemur-femr/femr/conf/routes
// @DATE:Tue Oct 27 20:43:13 PDT 2020

import play.api.routing.JavaScriptReverseRoute


import _root_.controllers.Assets.Asset
import _root_.play.libs.F

// @LINE:12
package femr.ui.controllers.admin.javascript {

  // @LINE:39
  class ReverseAdminController(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:39
    def index: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "femr.ui.controllers.admin.AdminController.index",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "admin"})
        }
      """
    )
  
  }

  // @LINE:18
  class ReverseInventoryController(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:24
    def exportGet: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "femr.ui.controllers.admin.InventoryController.exportGet",
      """
        function(tripId0) {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "admin/inventory/export/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[Integer]].javascriptUnbind + """)("tripId", tripId0))})
        }
      """
    )
  
    // @LINE:22
    def existingGet: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "femr.ui.controllers.admin.InventoryController.existingGet",
      """
        function(tripId0) {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "admin/inventory/existing/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[Integer]].javascriptUnbind + """)("tripId", tripId0))})
        }
      """
    )
  
    // @LINE:18
    def manageGet: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "femr.ui.controllers.admin.InventoryController.manageGet",
      """
        function(tripId0) {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "admin/inventory/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[Integer]].javascriptUnbind + """)("tripId", tripId0))})
        }
      """
    )
  
    // @LINE:26
    def ajaxReadd: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "femr.ui.controllers.admin.InventoryController.ajaxReadd",
      """
        function(id0,tripId1) {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "admin/inventory/readd/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[Integer]].javascriptUnbind + """)("id", id0)) + "/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[Integer]].javascriptUnbind + """)("tripId", tripId1))})
        }
      """
    )
  
    // @LINE:20
    def customGet: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "femr.ui.controllers.admin.InventoryController.customGet",
      """
        function(tripId0) {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "admin/inventory/custom/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[Integer]].javascriptUnbind + """)("tripId", tripId0))})
        }
      """
    )
  
    // @LINE:25
    def ajaxDelete: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "femr.ui.controllers.admin.InventoryController.ajaxDelete",
      """
        function(id0,tripId1) {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "admin/inventory/delete/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[Integer]].javascriptUnbind + """)("id", id0)) + "/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[Integer]].javascriptUnbind + """)("tripId", tripId1))})
        }
      """
    )
  
    // @LINE:28
    def ajaxEditTotal: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "femr.ui.controllers.admin.InventoryController.ajaxEditTotal",
      """
        function(id0,tripId1) {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "admin/inventory/editTotal/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[Integer]].javascriptUnbind + """)("id", id0)) + "/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[Integer]].javascriptUnbind + """)("tripId", tripId1))})
        }
      """
    )
  
    // @LINE:23
    def existingPost: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "femr.ui.controllers.admin.InventoryController.existingPost",
      """
        function(tripId0) {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "admin/inventory/existing/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[Integer]].javascriptUnbind + """)("tripId", tripId0))})
        }
      """
    )
  
    // @LINE:21
    def customPost: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "femr.ui.controllers.admin.InventoryController.customPost",
      """
        function(tripId0) {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "admin/inventory/custom/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[Integer]].javascriptUnbind + """)("tripId", tripId0))})
        }
      """
    )
  
    // @LINE:19
    def managePost: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "femr.ui.controllers.admin.InventoryController.managePost",
      """
        function() {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "admin/inventory"})
        }
      """
    )
  
    // @LINE:27
    def ajaxEditCurrent: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "femr.ui.controllers.admin.InventoryController.ajaxEditCurrent",
      """
        function(id0,tripId1) {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "admin/inventory/editCurrent/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[Integer]].javascriptUnbind + """)("id", id0)) + "/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[Integer]].javascriptUnbind + """)("tripId", tripId1))})
        }
      """
    )
  
  }

  // @LINE:12
  class ReverseUsersController(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:17
    def manageGet: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "femr.ui.controllers.admin.UsersController.manageGet",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "admin/users"})
        }
      """
    )
  
    // @LINE:13
    def createGet: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "femr.ui.controllers.admin.UsersController.createGet",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "admin/users/create"})
        }
      """
    )
  
    // @LINE:15
    def editGet: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "femr.ui.controllers.admin.UsersController.editGet",
      """
        function(id0) {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "admin/users/edit/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[Integer]].javascriptUnbind + """)("id", id0))})
        }
      """
    )
  
    // @LINE:14
    def editPost: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "femr.ui.controllers.admin.UsersController.editPost",
      """
        function(id0) {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "admin/users/edit" + _qS([(""" + implicitly[play.api.mvc.QueryStringBindable[Integer]].javascriptUnbind + """)("id", id0)])})
        }
      """
    )
  
    // @LINE:16
    def toggleUser: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "femr.ui.controllers.admin.UsersController.toggleUser",
      """
        function(id0) {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "admin/users/toggle/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[Integer]].javascriptUnbind + """)("id", id0))})
        }
      """
    )
  
    // @LINE:12
    def createPost: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "femr.ui.controllers.admin.UsersController.createPost",
      """
        function() {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "admin/users/create"})
        }
      """
    )
  
  }

  // @LINE:29
  class ReverseConfigureController(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:29
    def manageGet: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "femr.ui.controllers.admin.ConfigureController.manageGet",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "admin/configure"})
        }
      """
    )
  
    // @LINE:30
    def managePost: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "femr.ui.controllers.admin.ConfigureController.managePost",
      """
        function() {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "admin/configure"})
        }
      """
    )
  
  }

  // @LINE:31
  class ReverseTripController(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:31
    def manageGet: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "femr.ui.controllers.admin.TripController.manageGet",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "admin/trips"})
        }
      """
    )
  
    // @LINE:38
    def citiesPost: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "femr.ui.controllers.admin.TripController.citiesPost",
      """
        function() {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "admin/trips/cities"})
        }
      """
    )
  
    // @LINE:37
    def citiesGet: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "femr.ui.controllers.admin.TripController.citiesGet",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "admin/trips/cities"})
        }
      """
    )
  
    // @LINE:36
    def teamsPost: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "femr.ui.controllers.admin.TripController.teamsPost",
      """
        function() {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "admin/trips/teams"})
        }
      """
    )
  
    // @LINE:33
    def editGet: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "femr.ui.controllers.admin.TripController.editGet",
      """
        function(id0) {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "admin/trips/edit/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[Integer]].javascriptUnbind + """)("id", id0))})
        }
      """
    )
  
    // @LINE:34
    def editPost: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "femr.ui.controllers.admin.TripController.editPost",
      """
        function(id0) {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "admin/trips/edit/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[Integer]].javascriptUnbind + """)("id", id0))})
        }
      """
    )
  
    // @LINE:35
    def teamsGet: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "femr.ui.controllers.admin.TripController.teamsGet",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "admin/trips/teams"})
        }
      """
    )
  
    // @LINE:32
    def managePost: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "femr.ui.controllers.admin.TripController.managePost",
      """
        function() {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "admin/trips"})
        }
      """
    )
  
  }


}
