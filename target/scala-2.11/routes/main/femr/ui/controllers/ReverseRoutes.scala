// @GENERATOR:play-routes-compiler
// @SOURCE:C:/Users/Noel/Documents/csc402/lemur-femr/femr/conf/routes
// @DATE:Tue Oct 27 20:43:13 PDT 2020

import play.api.mvc.Call


import _root_.controllers.Assets.Asset
import _root_.play.libs.F

// @LINE:5
package femr.ui.controllers {

  // @LINE:63
  class ReversePhotoController(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:63
    def GetPatientPhoto(id:Integer, showDefault:Boolean = false): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "photo/patient/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[Integer]].unbind("id", id)) + play.core.routing.queryString(List(if(showDefault == false) None else Some(implicitly[play.api.mvc.QueryStringBindable[Boolean]].unbind("showDefault", showDefault)))))
    }
  
    // @LINE:64
    def GetPhoto(id:Int): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "photo/encounter/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[Int]].unbind("id", id)))
    }
  
  }

  // @LINE:43
  class ReversePharmaciesController(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:43
    def editGet(id:Integer): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "pharmacy/edit/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[Integer]].unbind("id", id)))
    }
  
    // @LINE:45
    def indexGet(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "pharmacy")
    }
  
    // @LINE:46
    def indexPost(): Call = {
      
      Call("POST", _prefix + { _defaultPrefix } + "pharmacy")
    }
  
    // @LINE:44
    def editPost(id:Integer): Call = {
      
      Call("POST", _prefix + { _defaultPrefix } + "pharmacy/edit/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[Integer]].unbind("id", id)))
    }
  
  }

  // @LINE:5
  class ReverseApplicationController(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:5
    def removeTrailingSlash(path:String): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + implicitly[play.api.mvc.PathBindable[String]].unbind("path", path) + "/")
    }
  
  }

  // @LINE:48
  class ReverseHistoryController(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:49
    def updateEncounterPost(id:Integer): Call = {
      
      Call("POST", _prefix + { _defaultPrefix } + "history/encounter/updateField/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[Integer]].unbind("id", id)))
    }
  
    // @LINE:50
    def listTabFieldHistoryGet(id:Integer): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "history/encounter/listTabFieldHistory/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[Integer]].unbind("id", id)))
    }
  
    // @LINE:48
    def indexEncounterGet(id:Integer): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "history/encounter/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[Integer]].unbind("id", id)))
    }
  
    // @LINE:52
    def indexPatientGet(query:String): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "history/patient/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[String]].unbind("query", query)))
    }
  
  }

  // @LINE:92
  class ReverseReferenceController(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:92
    def indexGet(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "reference")
    }
  
  }

  // @LINE:94
  class ReverseHomeController(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:94
    def index(): Call = {
      
      Call("GET", _prefix)
    }
  
  }

  // @LINE:71
  class ReverseMedicalController(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:77
    def indexPost(): Call = {
      
      Call("POST", _prefix + { _defaultPrefix } + "medical")
    }
  
    // @LINE:73
    def listVitalsGet(id:Integer): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "medical/listVitals/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[Integer]].unbind("id", id)))
    }
  
    // @LINE:79
    def deleteExistingProblem(patientId:Integer, problem:String): Call = {
      
      Call("POST", _prefix + { _defaultPrefix } + "medical/deleteProblem/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[Integer]].unbind("patientId", patientId)) + "/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[String]].unbind("problem", problem)))
    }
  
    // @LINE:71
    def updateVitalsPost(id:Integer): Call = {
      
      Call("POST", _prefix + { _defaultPrefix } + "medical/updateVitals/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[Integer]].unbind("id", id)))
    }
  
    // @LINE:72
    def newVitalsGet(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "medical/newVitals")
    }
  
    // @LINE:74
    def editGet(id:Integer): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "medical/edit/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[Integer]].unbind("id", id)))
    }
  
    // @LINE:78
    def prescriptionRowGet(index:Integer): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "medical/prescriptionRow/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[Integer]].unbind("index", index)))
    }
  
    // @LINE:75
    def editPost(id:Integer): Call = {
      
      Call("POST", _prefix + { _defaultPrefix } + "medical/edit/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[Integer]].unbind("id", id)))
    }
  
    // @LINE:76
    def indexGet(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "medical")
    }
  
  }

  // @LINE:54
  class ReverseSearchController(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:56
    def typeaheadPatientsJSONGet(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "search/typeahead/patients")
    }
  
    // @LINE:55
    def doesPatientExist(query:String): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "search/check/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[String]].unbind("query", query)))
    }
  
    // @LINE:59
    def typeaheadDiagnosisJSONGet(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "search/typeahead/diagnoses")
    }
  
    // @LINE:60
    def typeaheadMedicationsWithIDJSONGet(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "search/typeahead/medicationsWithID")
    }
  
    // @LINE:58
    def typeaheadCitiesJSONGet(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "search/typeahead/cities")
    }
  
    // @LINE:54
    def handleSearch(page:String): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "search/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[String]].unbind("page", page)))
    }
  
    // @LINE:61
    def typeaheadMedicationAdministrationsJSONGet(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "search/typeahead/medicationAdministrations")
    }
  
  }

  // @LINE:81
  class ReverseResearchController(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:81
    def indexGet(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "research")
    }
  
    // @LINE:83
    def exportPost(): Call = {
      
      Call("POST", _prefix + { _defaultPrefix } + "research/exportData")
    }
  
    // @LINE:82
    def indexPost(): Call = {
      
      Call("POST", _prefix + { _defaultPrefix } + "research")
    }
  
  }

  // @LINE:85
  class ReversePDFController(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:85
    def index(encounterId:Integer): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "pdf/encounter/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[Integer]].unbind("encounterId", encounterId)))
    }
  
  }

  // @LINE:100
  class ReverseFeedbackController(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:100
    def indexGet(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "feedback")
    }
  
    // @LINE:101
    def indexPost(): Call = {
      
      Call("POST", _prefix + { _defaultPrefix } + "feedback")
    }
  
  }

  // @LINE:87
  class ReverseSessionsController(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:88
    def editPasswordPost(): Call = {
      
      Call("POST", _prefix + { _defaultPrefix } + "login/reset")
    }
  
    // @LINE:87
    def delete(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "logout")
    }
  
    // @LINE:89
    def createPost(): Call = {
      
      Call("POST", _prefix + { _defaultPrefix } + "login")
    }
  
    // @LINE:90
    def createGet(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "login")
    }
  
  }

  // @LINE:51
  class ReverseTriageController(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:69
    def deletePatientPost(id:Integer): Call = {
      
      Call("POST", _prefix + { _defaultPrefix } + "triage/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[Integer]].unbind("id", id)))
    }
  
    // @LINE:51
    def deleteEncounterPost(id:Integer, encounterId:Integer): Call = {
      
      Call("POST", _prefix + { _defaultPrefix } + "history/encounter/delete/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[Integer]].unbind("id", id)) + play.core.routing.queryString(List(Some(implicitly[play.api.mvc.QueryStringBindable[Integer]].unbind("encounterId", encounterId)))))
    }
  
    // @LINE:67
    def indexGet(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "triage")
    }
  
    // @LINE:68
    def indexPost(id:Integer): Call = {
      
      Call("POST", _prefix + { _defaultPrefix } + "triage" + play.core.routing.queryString(List(Some(implicitly[play.api.mvc.QueryStringBindable[Integer]].unbind("id", id)))))
    }
  
    // @LINE:66
    def indexPopulatedGet(id:Integer): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "triage/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[Integer]].unbind("id", id)))
    }
  
  }


}
