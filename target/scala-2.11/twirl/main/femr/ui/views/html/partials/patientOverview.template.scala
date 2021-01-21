
package femr.ui.views.html.partials

import _root_.play.twirl.api.TwirlFeatureImports._
import _root_.play.twirl.api.TwirlHelperImports._
import _root_.play.twirl.api.Html
import _root_.play.twirl.api.JavaScript
import _root_.play.twirl.api.Txt
import _root_.play.twirl.api.Xml
import models._
import controllers._
import play.api.i18n._
import views.html._
import play.api.templates.PlayMagic._
import java.lang._
import java.util._
import scala.collection.JavaConverters._
import play.core.j.PlayMagicForJava._
import play.mvc._
import play.api.data.Field
import play.mvc.Http.Context.Implicit._
import play.data._
import play.core.j.PlayFormsMagicForJava._

object patientOverview extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template4[femr.common.models.PatientItem,femr.common.models.PatientEncounterItem,femr.common.models.SettingItem,java.lang.String,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(patient: femr.common.models.PatientItem, patientEncounter: femr.common.models.PatientEncounterItem,
        settings: femr.common.models.SettingItem, pageName: java.lang.String):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {
/*4.2*/import femr.ui.views.html.partials.helpers.outputHeightOrNA
/*5.2*/import femr.ui.views.html.partials.helpers.outputWeightOrNA
/*6.2*/import femr.ui.views.html.partials.helpers.outputIntOrNA


Seq[Any](format.raw/*3.1*/("""
"""),format.raw/*7.1*/("""
"""),format.raw/*8.1*/("""<div id="patientOverviewWrap" class="backgroundForWrap">

    <h3>Patient Overview - """),_display_(/*10.29*/pageName),format.raw/*10.37*/("""</h3>

    """),_display_(/*12.6*/if(patient.getPathToPhoto != null && !patient.getPathToPhoto.isEmpty)/*12.75*/ {_display_(Seq[Any](format.raw/*12.77*/("""
        """),format.raw/*13.9*/("""<img src=""""),_display_(/*13.20*/patient/*13.27*/.getPathToPhoto),format.raw/*13.42*/("""" height="90" width="90">
        <div id= "patientOverViewInfoPhoto">
    """)))}/*15.7*/else/*15.12*/{_display_(Seq[Any](format.raw/*15.13*/("""
        """),format.raw/*16.9*/("""<div id= "patientOverViewInfo">
    """)))}),format.raw/*17.6*/("""

    """),format.raw/*19.5*/("""<label>Name: </label><span>"""),_display_(/*19.33*/patient/*19.40*/.getFirstName),format.raw/*19.53*/(""" """),_display_(/*19.55*/patient/*19.62*/.getLastName),format.raw/*19.74*/("""</span>
    <label for="">Age: </label><span>"""),_display_(/*20.39*/patient/*20.46*/.getAge),format.raw/*20.53*/("""</span>
    <label for="">Sex: </label><span>"""),_display_(/*21.39*/patient/*21.46*/.getSex),format.raw/*21.53*/("""</span>
    <label for="">City: </label><span>"""),_display_(/*22.40*/patient/*22.47*/.getCity),format.raw/*22.55*/("""</span>
    <!-- [FEMR-217] - views/medical/edit.scala.html no longer passes simply "Medical" - now passes "Medical - " + "Patient ID: " + viewModel.getPatientItem.getId -->
    <!-- broken in commit 23c533c -->
    <!-- Currently assumes "Medical -" will start pageName argument.  If this changes, updated string in conditional execution -->
    <!-- Contributed by Jonathan Clow during the CEN5035 course at FSU -->
    """),_display_(/*27.6*/if(pageName.contains("Medical -"))/*27.40*/ {_display_(Seq[Any](format.raw/*27.42*/("""
        """),format.raw/*28.9*/("""<label for="">BMI: </label> <span id="bmi"></span>
    """)))}/*29.7*/else/*29.12*/{_display_(Seq[Any](format.raw/*29.13*/("""
           """),format.raw/*30.12*/("""<label>Height: </label> <span>"""),_display_(/*30.43*/outputHeightOrNA(String.valueOf(patient.getHeightFeet), String.valueOf(patient.getHeightInches), settings.isMetric)),format.raw/*30.158*/(""" """),format.raw/*30.159*/("""/ """),_display_(/*30.162*/outputHeightOrNA(String.valueOf(patient.getHeightFeetDual), String.valueOf(patient.getHeightInchesDual), !settings.isMetric)),format.raw/*30.286*/("""</span>
            <label>Weight: </label> <span>"""),_display_(/*31.44*/outputWeightOrNA(String.valueOf(patient.getWeight), settings.isMetric)),format.raw/*31.114*/(""" """),format.raw/*31.115*/("""/ """),_display_(/*31.118*/outputWeightOrNA(String.valueOf(patient.getWeightDual), !settings.isMetric)),format.raw/*31.193*/("""</span>

    """)))}),format.raw/*33.6*/("""

    """),format.raw/*35.25*/("""

    """),format.raw/*37.5*/("""<label>Weeks Pregnant: </label> <span>"""),_display_(/*37.44*/outputIntOrNA(patient.getWeeksPregnant)),format.raw/*37.83*/("""</span>

    """),format.raw/*39.25*/("""
    """),_display_(/*40.6*/if(patientEncounter.getChiefComplaints != null && patientEncounter.getChiefComplaints.size > 0 && patientEncounter.getChiefComplaints.get(0) != null && !patientEncounter.getChiefComplaints.get(0).isEmpty)/*40.210*/ {_display_(Seq[Any](format.raw/*40.212*/("""
        """),format.raw/*41.9*/("""<label>Complaint: </label> <span>"""),_display_(/*41.43*/for(x <- 1 to patientEncounter.getChiefComplaints.size) yield /*41.98*/ {_display_(Seq[Any](format.raw/*41.100*/("""
            """),_display_(/*42.14*/patientEncounter/*42.30*/.getChiefComplaints.get(x - 1)),format.raw/*42.60*/(""" """),_display_(/*42.62*/if(x < patientEncounter.getChiefComplaints.size)/*42.110*/ {_display_(Seq[Any](format.raw/*42.112*/(""" """),format.raw/*42.113*/("""| """)))}),format.raw/*42.116*/("""
        """)))}),format.raw/*43.10*/("""</span>
    """)))}/*44.7*/else/*44.12*/{_display_(Seq[Any](format.raw/*44.13*/("""
        """),format.raw/*45.9*/("""<label>Complaint: </label> <span>N/A</span>
    """)))}),format.raw/*46.6*/("""
"""),format.raw/*47.1*/("""</div>
</div>
</div>
"""))
      }
    }
  }

  def render(patient:femr.common.models.PatientItem,patientEncounter:femr.common.models.PatientEncounterItem,settings:femr.common.models.SettingItem,pageName:java.lang.String): play.twirl.api.HtmlFormat.Appendable = apply(patient,patientEncounter,settings,pageName)

  def f:((femr.common.models.PatientItem,femr.common.models.PatientEncounterItem,femr.common.models.SettingItem,java.lang.String) => play.twirl.api.HtmlFormat.Appendable) = (patient,patientEncounter,settings,pageName) => apply(patient,patientEncounter,settings,pageName)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  DATE: Wed Jan 20 18:29:28 PST 2021
                  SOURCE: /Users/noel/Desktop/CPE/femr/femr/app/femr/ui/views/partials/patientOverview.scala.html
                  HASH: fbc609d21314f8be2715fcf8042e083e990c6332
                  MATRIX: 1087->1|1338->182|1405->243|1472->304|1557->180|1584->361|1611->362|1724->448|1753->456|1791->468|1869->537|1909->539|1945->548|1983->559|1999->566|2035->581|2129->658|2142->663|2181->664|2217->673|2284->710|2317->716|2372->744|2388->751|2422->764|2451->766|2467->773|2500->785|2573->831|2589->838|2617->845|2690->891|2706->898|2734->905|2808->952|2824->959|2853->967|3302->1390|3345->1424|3385->1426|3421->1435|3495->1492|3508->1497|3547->1498|3587->1510|3645->1541|3782->1656|3812->1657|3843->1660|3989->1784|4067->1835|4159->1905|4189->1906|4220->1909|4317->1984|4361->1998|4395->2024|4428->2030|4494->2069|4554->2108|4595->2141|4627->2147|4841->2351|4882->2353|4918->2362|4979->2396|5050->2451|5091->2453|5132->2467|5157->2483|5208->2513|5237->2515|5295->2563|5336->2565|5366->2566|5401->2569|5442->2579|5473->2593|5486->2598|5525->2599|5561->2608|5640->2657|5668->2658
                  LINES: 28->1|32->4|33->5|34->6|37->3|38->7|39->8|41->10|41->10|43->12|43->12|43->12|44->13|44->13|44->13|44->13|46->15|46->15|46->15|47->16|48->17|50->19|50->19|50->19|50->19|50->19|50->19|50->19|51->20|51->20|51->20|52->21|52->21|52->21|53->22|53->22|53->22|58->27|58->27|58->27|59->28|60->29|60->29|60->29|61->30|61->30|61->30|61->30|61->30|61->30|62->31|62->31|62->31|62->31|62->31|64->33|66->35|68->37|68->37|68->37|70->39|71->40|71->40|71->40|72->41|72->41|72->41|72->41|73->42|73->42|73->42|73->42|73->42|73->42|73->42|73->42|74->43|75->44|75->44|75->44|76->45|77->46|78->47
                  -- GENERATED --
              */
          