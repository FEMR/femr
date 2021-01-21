// @GENERATOR:play-routes-compiler
// @SOURCE:/Users/noel/Desktop/CPE/femr/femr/conf/routes
// @DATE:Wed Jan 20 18:29:24 PST 2021

import play.api.mvc.Call


import _root_.controllers.Assets.Asset
import _root_.play.libs.F

// @LINE:12
package femr.ui.controllers.admin {

  // @LINE:39
  class ReverseAdminController(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:39
    def index(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "admin")
    }
  
  }

  // @LINE:18
  class ReverseInventoryController(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:24
    def exportGet(tripId:Integer): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "admin/inventory/export/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[Integer]].unbind("tripId", tripId)))
    }
  
    // @LINE:22
    def existingGet(tripId:Integer): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "admin/inventory/existing/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[Integer]].unbind("tripId", tripId)))
    }
  
    // @LINE:18
    def manageGet(tripId:Integer): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "admin/inventory/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[Integer]].unbind("tripId", tripId)))
    }
  
    // @LINE:26
    def ajaxReadd(id:Integer, tripId:Integer): Call = {
      
      Call("POST", _prefix + { _defaultPrefix } + "admin/inventory/readd/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[Integer]].unbind("id", id)) + "/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[Integer]].unbind("tripId", tripId)))
    }
  
    // @LINE:20
    def customGet(tripId:Integer): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "admin/inventory/custom/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[Integer]].unbind("tripId", tripId)))
    }
  
    // @LINE:25
    def ajaxDelete(id:Integer, tripId:Integer): Call = {
      
      Call("POST", _prefix + { _defaultPrefix } + "admin/inventory/delete/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[Integer]].unbind("id", id)) + "/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[Integer]].unbind("tripId", tripId)))
    }
  
    // @LINE:28
    def ajaxEditTotal(id:Integer, tripId:Integer): Call = {
      
      Call("POST", _prefix + { _defaultPrefix } + "admin/inventory/editTotal/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[Integer]].unbind("id", id)) + "/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[Integer]].unbind("tripId", tripId)))
    }
  
    // @LINE:23
    def existingPost(tripId:Integer): Call = {
      
      Call("POST", _prefix + { _defaultPrefix } + "admin/inventory/existing/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[Integer]].unbind("tripId", tripId)))
    }
  
    // @LINE:21
    def customPost(tripId:Integer): Call = {
      
      Call("POST", _prefix + { _defaultPrefix } + "admin/inventory/custom/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[Integer]].unbind("tripId", tripId)))
    }
  
    // @LINE:19
    def managePost(): Call = {
      
      Call("POST", _prefix + { _defaultPrefix } + "admin/inventory")
    }
  
    // @LINE:27
    def ajaxEditCurrent(id:Integer, tripId:Integer): Call = {
      
      Call("POST", _prefix + { _defaultPrefix } + "admin/inventory/editCurrent/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[Integer]].unbind("id", id)) + "/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[Integer]].unbind("tripId", tripId)))
    }
  
  }

  // @LINE:12
  class ReverseUsersController(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:17
    def manageGet(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "admin/users")
    }
  
    // @LINE:13
    def createGet(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "admin/users/create")
    }
  
    // @LINE:15
    def editGet(id:Integer): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "admin/users/edit/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[Integer]].unbind("id", id)))
    }
  
    // @LINE:14
    def editPost(id:Integer): Call = {
      
      Call("POST", _prefix + { _defaultPrefix } + "admin/users/edit" + play.core.routing.queryString(List(Some(implicitly[play.api.mvc.QueryStringBindable[Integer]].unbind("id", id)))))
    }
  
    // @LINE:16
    def toggleUser(id:Integer): Call = {
      
      Call("POST", _prefix + { _defaultPrefix } + "admin/users/toggle/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[Integer]].unbind("id", id)))
    }
  
    // @LINE:12
    def createPost(): Call = {
      
      Call("POST", _prefix + { _defaultPrefix } + "admin/users/create")
    }
  
  }

  // @LINE:29
  class ReverseConfigureController(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:29
    def manageGet(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "admin/configure")
    }
  
    // @LINE:30
    def managePost(): Call = {
      
      Call("POST", _prefix + { _defaultPrefix } + "admin/configure")
    }
  
  }

  // @LINE:31
  class ReverseTripController(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:31
    def manageGet(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "admin/trips")
    }
  
    // @LINE:38
    def citiesPost(): Call = {
      
      Call("POST", _prefix + { _defaultPrefix } + "admin/trips/cities")
    }
  
    // @LINE:37
    def citiesGet(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "admin/trips/cities")
    }
  
    // @LINE:36
    def teamsPost(): Call = {
      
      Call("POST", _prefix + { _defaultPrefix } + "admin/trips/teams")
    }
  
    // @LINE:33
    def editGet(id:Integer): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "admin/trips/edit/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[Integer]].unbind("id", id)))
    }
  
    // @LINE:34
    def editPost(id:Integer): Call = {
      
      Call("POST", _prefix + { _defaultPrefix } + "admin/trips/edit/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[Integer]].unbind("id", id)))
    }
  
    // @LINE:35
    def teamsGet(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "admin/trips/teams")
    }
  
    // @LINE:32
    def managePost(): Call = {
      
      Call("POST", _prefix + { _defaultPrefix } + "admin/trips")
    }
  
  }


}
