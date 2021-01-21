// @GENERATOR:play-routes-compiler
// @SOURCE:/Users/noel/Desktop/CPE/femr/femr/conf/routes
// @DATE:Wed Jan 20 18:29:24 PST 2021

import play.api.routing.JavaScriptReverseRoute


import _root_.controllers.Assets.Asset
import _root_.play.libs.F

// @LINE:5
package femr.ui.controllers.javascript {

  // @LINE:63
  class ReversePhotoController(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:63
    def GetPatientPhoto: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "femr.ui.controllers.PhotoController.GetPatientPhoto",
      """
        function(id0,showDefault1) {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "photo/patient/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[Integer]].javascriptUnbind + """)("id", id0)) + _qS([(showDefault1 == null ? null : (""" + implicitly[play.api.mvc.QueryStringBindable[Boolean]].javascriptUnbind + """)("showDefault", showDefault1))])})
        }
      """
    )
  
    // @LINE:64
    def GetPhoto: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "femr.ui.controllers.PhotoController.GetPhoto",
      """
        function(id0) {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "photo/encounter/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[Int]].javascriptUnbind + """)("id", id0))})
        }
      """
    )
  
  }

  // @LINE:43
  class ReversePharmaciesController(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:43
    def editGet: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "femr.ui.controllers.PharmaciesController.editGet",
      """
        function(id0) {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "pharmacy/edit/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[Integer]].javascriptUnbind + """)("id", id0))})
        }
      """
    )
  
    // @LINE:45
    def indexGet: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "femr.ui.controllers.PharmaciesController.indexGet",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "pharmacy"})
        }
      """
    )
  
    // @LINE:46
    def indexPost: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "femr.ui.controllers.PharmaciesController.indexPost",
      """
        function() {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "pharmacy"})
        }
      """
    )
  
    // @LINE:44
    def editPost: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "femr.ui.controllers.PharmaciesController.editPost",
      """
        function(id0) {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "pharmacy/edit/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[Integer]].javascriptUnbind + """)("id", id0))})
        }
      """
    )
  
  }

  // @LINE:5
  class ReverseApplicationController(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:5
    def removeTrailingSlash: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "femr.ui.controllers.ApplicationController.removeTrailingSlash",
      """
        function(path0) {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + (""" + implicitly[play.api.mvc.PathBindable[String]].javascriptUnbind + """)("path", path0) + "/"})
        }
      """
    )
  
  }

  // @LINE:48
  class ReverseHistoryController(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:49
    def updateEncounterPost: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "femr.ui.controllers.HistoryController.updateEncounterPost",
      """
        function(id0) {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "history/encounter/updateField/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[Integer]].javascriptUnbind + """)("id", id0))})
        }
      """
    )
  
    // @LINE:50
    def listTabFieldHistoryGet: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "femr.ui.controllers.HistoryController.listTabFieldHistoryGet",
      """
        function(id0) {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "history/encounter/listTabFieldHistory/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[Integer]].javascriptUnbind + """)("id", id0))})
        }
      """
    )
  
    // @LINE:48
    def indexEncounterGet: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "femr.ui.controllers.HistoryController.indexEncounterGet",
      """
        function(id0) {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "history/encounter/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[Integer]].javascriptUnbind + """)("id", id0))})
        }
      """
    )
  
    // @LINE:52
    def indexPatientGet: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "femr.ui.controllers.HistoryController.indexPatientGet",
      """
        function(query0) {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "history/patient/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[String]].javascriptUnbind + """)("query", query0))})
        }
      """
    )
  
  }

  // @LINE:92
  class ReverseReferenceController(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:92
    def indexGet: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "femr.ui.controllers.ReferenceController.indexGet",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "reference"})
        }
      """
    )
  
  }

  // @LINE:94
  class ReverseHomeController(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:94
    def index: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "femr.ui.controllers.HomeController.index",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + """"})
        }
      """
    )
  
  }

  // @LINE:71
  class ReverseMedicalController(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:77
    def indexPost: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "femr.ui.controllers.MedicalController.indexPost",
      """
        function() {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "medical"})
        }
      """
    )
  
    // @LINE:73
    def listVitalsGet: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "femr.ui.controllers.MedicalController.listVitalsGet",
      """
        function(id0) {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "medical/listVitals/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[Integer]].javascriptUnbind + """)("id", id0))})
        }
      """
    )
  
    // @LINE:79
    def deleteExistingProblem: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "femr.ui.controllers.MedicalController.deleteExistingProblem",
      """
        function(patientId0,problem1) {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "medical/deleteProblem/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[Integer]].javascriptUnbind + """)("patientId", patientId0)) + "/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[String]].javascriptUnbind + """)("problem", problem1))})
        }
      """
    )
  
    // @LINE:71
    def updateVitalsPost: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "femr.ui.controllers.MedicalController.updateVitalsPost",
      """
        function(id0) {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "medical/updateVitals/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[Integer]].javascriptUnbind + """)("id", id0))})
        }
      """
    )
  
    // @LINE:72
    def newVitalsGet: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "femr.ui.controllers.MedicalController.newVitalsGet",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "medical/newVitals"})
        }
      """
    )
  
    // @LINE:74
    def editGet: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "femr.ui.controllers.MedicalController.editGet",
      """
        function(id0) {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "medical/edit/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[Integer]].javascriptUnbind + """)("id", id0))})
        }
      """
    )
  
    // @LINE:78
    def prescriptionRowGet: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "femr.ui.controllers.MedicalController.prescriptionRowGet",
      """
        function(index0) {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "medical/prescriptionRow/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[Integer]].javascriptUnbind + """)("index", index0))})
        }
      """
    )
  
    // @LINE:75
    def editPost: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "femr.ui.controllers.MedicalController.editPost",
      """
        function(id0) {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "medical/edit/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[Integer]].javascriptUnbind + """)("id", id0))})
        }
      """
    )
  
    // @LINE:76
    def indexGet: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "femr.ui.controllers.MedicalController.indexGet",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "medical"})
        }
      """
    )
  
  }

  // @LINE:54
  class ReverseSearchController(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:56
    def typeaheadPatientsJSONGet: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "femr.ui.controllers.SearchController.typeaheadPatientsJSONGet",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "search/typeahead/patients"})
        }
      """
    )
  
    // @LINE:55
    def doesPatientExist: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "femr.ui.controllers.SearchController.doesPatientExist",
      """
        function(query0) {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "search/check/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[String]].javascriptUnbind + """)("query", query0))})
        }
      """
    )
  
    // @LINE:59
    def typeaheadDiagnosisJSONGet: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "femr.ui.controllers.SearchController.typeaheadDiagnosisJSONGet",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "search/typeahead/diagnoses"})
        }
      """
    )
  
    // @LINE:60
    def typeaheadMedicationsWithIDJSONGet: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "femr.ui.controllers.SearchController.typeaheadMedicationsWithIDJSONGet",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "search/typeahead/medicationsWithID"})
        }
      """
    )
  
    // @LINE:58
    def typeaheadCitiesJSONGet: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "femr.ui.controllers.SearchController.typeaheadCitiesJSONGet",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "search/typeahead/cities"})
        }
      """
    )
  
    // @LINE:54
    def handleSearch: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "femr.ui.controllers.SearchController.handleSearch",
      """
        function(page0) {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "search/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[String]].javascriptUnbind + """)("page", page0))})
        }
      """
    )
  
    // @LINE:61
    def typeaheadMedicationAdministrationsJSONGet: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "femr.ui.controllers.SearchController.typeaheadMedicationAdministrationsJSONGet",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "search/typeahead/medicationAdministrations"})
        }
      """
    )
  
  }

  // @LINE:81
  class ReverseResearchController(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:81
    def indexGet: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "femr.ui.controllers.ResearchController.indexGet",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "research"})
        }
      """
    )
  
    // @LINE:83
    def exportPost: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "femr.ui.controllers.ResearchController.exportPost",
      """
        function() {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "research/exportData"})
        }
      """
    )
  
    // @LINE:82
    def indexPost: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "femr.ui.controllers.ResearchController.indexPost",
      """
        function() {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "research"})
        }
      """
    )
  
  }

  // @LINE:85
  class ReversePDFController(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:85
    def index: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "femr.ui.controllers.PDFController.index",
      """
        function(encounterId0) {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "pdf/encounter/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[Integer]].javascriptUnbind + """)("encounterId", encounterId0))})
        }
      """
    )
  
  }

  // @LINE:100
  class ReverseFeedbackController(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:100
    def indexGet: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "femr.ui.controllers.FeedbackController.indexGet",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "feedback"})
        }
      """
    )
  
    // @LINE:101
    def indexPost: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "femr.ui.controllers.FeedbackController.indexPost",
      """
        function() {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "feedback"})
        }
      """
    )
  
  }

  // @LINE:87
  class ReverseSessionsController(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:88
    def editPasswordPost: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "femr.ui.controllers.SessionsController.editPasswordPost",
      """
        function() {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "login/reset"})
        }
      """
    )
  
    // @LINE:87
    def delete: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "femr.ui.controllers.SessionsController.delete",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "logout"})
        }
      """
    )
  
    // @LINE:89
    def createPost: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "femr.ui.controllers.SessionsController.createPost",
      """
        function() {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "login"})
        }
      """
    )
  
    // @LINE:90
    def createGet: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "femr.ui.controllers.SessionsController.createGet",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "login"})
        }
      """
    )
  
  }

  // @LINE:51
  class ReverseTriageController(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:69
    def deletePatientPost: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "femr.ui.controllers.TriageController.deletePatientPost",
      """
        function(id0) {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "triage/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[Integer]].javascriptUnbind + """)("id", id0))})
        }
      """
    )
  
    // @LINE:51
    def deleteEncounterPost: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "femr.ui.controllers.TriageController.deleteEncounterPost",
      """
        function(id0,encounterId1) {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "history/encounter/delete/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[Integer]].javascriptUnbind + """)("id", id0)) + _qS([(""" + implicitly[play.api.mvc.QueryStringBindable[Integer]].javascriptUnbind + """)("encounterId", encounterId1)])})
        }
      """
    )
  
    // @LINE:67
    def indexGet: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "femr.ui.controllers.TriageController.indexGet",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "triage"})
        }
      """
    )
  
    // @LINE:68
    def indexPost: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "femr.ui.controllers.TriageController.indexPost",
      """
        function(id0) {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "triage" + _qS([(""" + implicitly[play.api.mvc.QueryStringBindable[Integer]].javascriptUnbind + """)("id", id0)])})
        }
      """
    )
  
    // @LINE:66
    def indexPopulatedGet: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "femr.ui.controllers.TriageController.indexPopulatedGet",
      """
        function(id0) {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "triage/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[Integer]].javascriptUnbind + """)("id", id0))})
        }
      """
    )
  
  }


}
