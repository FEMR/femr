// @GENERATOR:play-routes-compiler
// @SOURCE:C:/Users/Noel/Documents/csc402/lemur-femr/femr/conf/routes
// @DATE:Tue Oct 27 20:43:13 PDT 2020

import play.api.mvc.Call


import _root_.controllers.Assets.Asset
import _root_.play.libs.F

// @LINE:41
package femr.ui.controllers.manager {

  // @LINE:41
  class ReverseManagerController(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:41
    def indexGet(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "manager")
    }
  
  }


}
