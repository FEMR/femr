// @GENERATOR:play-routes-compiler
// @SOURCE:/Users/noel/Desktop/CPE/femr/femr/conf/routes
// @DATE:Wed Jan 20 18:29:24 PST 2021

import play.api.routing.JavaScriptReverseRoute


import _root_.controllers.Assets.Asset
import _root_.play.libs.F

// @LINE:41
package femr.ui.controllers.manager.javascript {

  // @LINE:41
  class ReverseManagerController(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:41
    def indexGet: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "femr.ui.controllers.manager.ManagerController.indexGet",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "manager"})
        }
      """
    )
  
  }


}
