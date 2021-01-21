// @GENERATOR:play-routes-compiler
// @SOURCE:/Users/noel/Desktop/CPE/femr/femr/conf/routes
// @DATE:Wed Jan 20 18:29:24 PST 2021

import play.api.routing.JavaScriptReverseRoute


import _root_.controllers.Assets.Asset
import _root_.play.libs.F

// @LINE:6
package femr.ui.controllers.superuser.javascript {

  // @LINE:10
  class ReverseSuperuserController(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:10
    def indexGet: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "femr.ui.controllers.superuser.SuperuserController.indexGet",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "superuser"})
        }
      """
    )
  
  }

  // @LINE:6
  class ReverseTabController(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:7
    def fieldsPost: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "femr.ui.controllers.superuser.TabController.fieldsPost",
      """
        function(name0) {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "superuser/tabs/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[String]].javascriptUnbind + """)("name", name0))})
        }
      """
    )
  
    // @LINE:8
    def manageGet: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "femr.ui.controllers.superuser.TabController.manageGet",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "superuser/tabs"})
        }
      """
    )
  
    // @LINE:6
    def fieldsGet: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "femr.ui.controllers.superuser.TabController.fieldsGet",
      """
        function(name0) {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "superuser/tabs/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[String]].javascriptUnbind + """)("name", name0))})
        }
      """
    )
  
    // @LINE:9
    def managePost: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "femr.ui.controllers.superuser.TabController.managePost",
      """
        function() {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "superuser/tabs"})
        }
      """
    )
  
  }


}
