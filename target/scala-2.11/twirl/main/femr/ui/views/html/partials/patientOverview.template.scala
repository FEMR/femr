
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


Seq[Any](format.raw/*2.78*/("""

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
                  DATE: Tue Oct 27 21:59:18 PDT 2020
                  SOURCE: C:/Users/Noel/Documents/csc402/lemur-femr/femr/app/femr/ui/views/partials/patientOverview.scala.html
                  HASH: d1dcbab03ef67e33e29c596c60b94d76bc77fb4f
                  MATRIX: 1087->1|1339->185|1406->247|1473->309|1559->180|1589->367|1617->369|1732->457|1761->465|1801->479|1879->548|1919->550|1956->560|1994->571|2010->578|2046->593|2142->672|2155->677|2194->678|2231->688|2299->726|2334->734|2389->762|2405->769|2439->782|2468->784|2484->791|2517->803|2591->850|2607->857|2635->864|2709->911|2725->918|2753->925|2828->973|2844->980|2873->988|3327->1416|3370->1450|3410->1452|3447->1462|3522->1520|3535->1525|3574->1526|3615->1539|3673->1570|3810->1685|3840->1686|3871->1689|4017->1813|4096->1865|4188->1935|4218->1936|4249->1939|4346->2014|4392->2030|4428->2058|4463->2066|4529->2105|4589->2144|4632->2179|4665->2186|4879->2390|4920->2392|4957->2402|5018->2436|5089->2491|5130->2493|5172->2508|5197->2524|5248->2554|5277->2556|5335->2604|5376->2606|5406->2607|5441->2610|5483->2621|5515->2636|5528->2641|5567->2642|5604->2652|5684->2702|5713->2704
                  LINES: 28->1|32->4|33->5|34->6|37->2|39->7|40->8|42->10|42->10|44->12|44->12|44->12|45->13|45->13|45->13|45->13|47->15|47->15|47->15|48->16|49->17|51->19|51->19|51->19|51->19|51->19|51->19|51->19|52->20|52->20|52->20|53->21|53->21|53->21|54->22|54->22|54->22|59->27|59->27|59->27|60->28|61->29|61->29|61->29|62->30|62->30|62->30|62->30|62->30|62->30|63->31|63->31|63->31|63->31|63->31|65->33|67->35|69->37|69->37|69->37|71->39|72->40|72->40|72->40|73->41|73->41|73->41|73->41|74->42|74->42|74->42|74->42|74->42|74->42|74->42|74->42|75->43|76->44|76->44|76->44|77->45|78->46|79->47
                  -- GENERATED --
              */
          