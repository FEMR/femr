// @GENERATOR:play-routes-compiler
// @SOURCE:C:/Users/Noel/Documents/csc402/lemur-femr/femr/conf/routes
// @DATE:Tue Oct 27 20:43:13 PDT 2020

import play.api.mvc.Call


import _root_.controllers.Assets.Asset
import _root_.play.libs.F

// @LINE:6
package femr.ui.controllers.superuser {

  // @LINE:10
  class ReverseSuperuserController(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:10
    def indexGet(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "superuser")
    }
  
  }

  // @LINE:6
  class ReverseTabController(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:7
    def fieldsPost(name:String): Call = {
      
      Call("POST", _prefix + { _defaultPrefix } + "superuser/tabs/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[String]].unbind("name", name)))
    }
  
    // @LINE:8
    def manageGet(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "superuser/tabs")
    }
  
    // @LINE:6
    def fieldsGet(name:String): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "superuser/tabs/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[String]].unbind("name", name)))
    }
  
    // @LINE:9
    def managePost(): Call = {
      
      Call("POST", _prefix + { _defaultPrefix } + "superuser/tabs")
    }
  
  }


}
