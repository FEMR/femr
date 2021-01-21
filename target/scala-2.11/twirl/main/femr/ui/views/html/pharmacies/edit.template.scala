
package femr.ui.views.html.pharmacies

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

object edit extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template4[femr.common.dtos.CurrentUser,femr.ui.models.pharmacy.EditViewModelGet,java.lang.Boolean,AssetsFinder,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(currentUser: femr.common.dtos.CurrentUser, viewModel: femr.ui.models.pharmacy.EditViewModelGet, searchError: java.lang.Boolean, assets: AssetsFinder ):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {
/*3.2*/import femr.ui.controllers.routes.HistoryController
/*4.2*/import femr.ui.controllers.routes.MedicalController
/*5.2*/import femr.ui.controllers.routes.PharmaciesController
/*6.2*/import femr.ui.views.html.layouts.main
/*7.2*/import femr.ui.views.html.partials.search
/*8.2*/import femr.ui.views.html.partials.patientOverview
/*9.2*/import femr.ui.views.html.partials.medical.tabs.prescriptionRow
/*10.2*/import femr.ui.views.html.partials.helpers.outputStringOrNA

def /*12.6*/additionalScripts/*12.23*/:play.twirl.api.HtmlFormat.Appendable = {_display_(

Seq[Any](format.raw/*12.27*/("""

        """),format.raw/*14.9*/("""<script type = "text/javascript" src=""""),_display_(/*14.48*/assets/*14.54*/.path("js/pharmacy/pharmacy.js")),format.raw/*14.86*/(""""></script>
        <script type = "text/javascript" src=""""),_display_(/*15.48*/assets/*15.54*/.path("js/pharmacy/pharmacyClientValidation.js")),format.raw/*15.102*/(""""></script>
        <script type = "text/javascript" src=""""),_display_(/*16.48*/assets/*16.54*/.path("js/libraries/typeahead.bundle.min.js")),format.raw/*16.99*/(""""></script>
        <script type = "text/javascript"   src=""""),_display_(/*17.50*/assets/*17.56*/.path("js/libraries/jquery-ui.min.js")),format.raw/*17.94*/(""""></script>
    """)))};def /*19.6*/additionalStyles/*19.22*/:play.twirl.api.HtmlFormat.Appendable = {_display_(

Seq[Any](format.raw/*19.26*/("""

        """),format.raw/*21.9*/("""<link rel="stylesheet" href=""""),_display_(/*21.39*/assets/*21.45*/.path("css/pharmacy.css")),format.raw/*21.70*/("""">
        <link rel="stylesheet" href=""""),_display_(/*22.39*/assets/*22.45*/.path("css/libraries/jquery-ui.min.css")),format.raw/*22.85*/("""">
    """)))};
Seq[Any](format.raw/*2.1*/("""
"""),format.raw/*11.1*/("""
    """),format.raw/*18.6*/("""
    """),format.raw/*23.6*/("""

"""),_display_(/*25.2*/main("Pharmacy", currentUser, scripts = additionalScripts, styles = additionalStyles, search = search("pharmacy"), assets = assets)/*25.133*/ {_display_(Seq[Any](format.raw/*25.135*/("""
    """),format.raw/*26.5*/("""<div id="pharmacyContentWrap">

        """),_display_(/*28.10*/patientOverview(viewModel.getPatient, viewModel.getPatientEncounterItem, viewModel.getSettings, "Pharmacy - " + "Patient ID: " + viewModel.getPatient.getId)),format.raw/*28.166*/("""



        """),format.raw/*32.9*/("""<div id="mainWrap" class="backgroundForWrap">
        """),_display_(/*33.10*/helper/*33.16*/.form(action = PharmaciesController.editPost(viewModel.getPatient.getId))/*33.89*/ {_display_(Seq[Any](format.raw/*33.91*/("""


            """),format.raw/*36.13*/("""<div id="diagnosisWrap">
                <h4>List of Diagnoses</h4>

                <ol class="problems">

                """),_display_(/*41.18*/if(viewModel.getProblems != null)/*41.51*/ {_display_(Seq[Any](format.raw/*41.53*/("""
                    """),_display_(/*42.22*/for(y <- 1 to viewModel.getProblems.size) yield /*42.63*/ {_display_(Seq[Any](format.raw/*42.65*/("""
                        """),_display_(/*43.26*/if(viewModel.getProblems.get(y - 1) != null)/*43.70*/ {_display_(Seq[Any](format.raw/*43.72*/("""
                            """),format.raw/*44.29*/("""<li>"""),_display_(/*44.34*/viewModel/*44.43*/.getProblems.get(y - 1).getName),format.raw/*44.74*/("""</li>

                        """)))}),format.raw/*46.26*/("""
                    """)))}),format.raw/*47.22*/("""
                """)))}),format.raw/*48.18*/("""
                """),format.raw/*49.17*/("""</ol>
            </div>

            <div id="medicationWrap" class="form-group">
                <h4>Pharmacy Notes</h4>
                        <table class="noteTable">
                        <tr class="noteRow"><th width="70%">Note</th><th width="30%">Prescriber</th></tr>
                        """),_display_(/*56.26*/if(viewModel.getNotes != null)/*56.56*/ {_display_(Seq[Any](format.raw/*56.58*/("""
                            """),_display_(/*57.30*/for(y <- 1 to viewModel.getNotes.size) yield /*57.68*/ {_display_(Seq[Any](format.raw/*57.70*/("""
                                """),_display_(/*58.34*/if(viewModel.getNotes.get(y - 1) != null)/*58.75*/ {_display_(Seq[Any](format.raw/*58.77*/("""
                                    """),format.raw/*59.37*/("""<tr><td>"""),_display_(/*59.46*/viewModel/*59.55*/.getNotes.get(y - 1).getName),format.raw/*59.83*/("""</td><td> """),_display_(/*59.94*/viewModel/*59.103*/.getNotes.get(y - 1).getReporter),format.raw/*59.135*/("""</td></tr>
                                    """)))}),format.raw/*60.38*/("""
                                """)))}),format.raw/*61.34*/("""
                            """)))}),format.raw/*62.30*/("""
                        """),format.raw/*63.25*/("""</table>
                <h4>List of Medications</h4>
                <ol id="medicationList">
                """),_display_(/*66.18*/for(x <- 1 to viewModel.getPrescriptions.size) yield /*66.64*/ {_display_(Seq[Any](format.raw/*66.66*/("""
                    """),_display_(/*67.22*/defining(viewModel.getPrescriptions.get(x - 1))/*67.69*/ { prescription =>_display_(Seq[Any](format.raw/*67.87*/("""
                        """),format.raw/*68.25*/("""<li class="prescription" id="med"""),_display_(/*68.58*/(x-1)),format.raw/*68.63*/("""" data-medID=""""),_display_(/*68.78*/prescription/*68.90*/.getMedicationID),format.raw/*68.106*/("""">
                            <input type="text" class="hidden" name="prescriptions["""),_display_(/*69.84*/(x-1)),format.raw/*69.89*/("""].id" value=""""),_display_(/*69.103*/prescription/*69.115*/.getId),format.raw/*69.121*/(""""/>
                            <button type="button" class="fButton fOtherButton replaceBtn">R</button>
                            <div>
                                <span>"""),_display_(/*72.40*/prescription/*72.52*/.printFullPrescriptionName),format.raw/*72.78*/(""" """),format.raw/*72.79*/("""</span>
                            </div>
                            <div>
                                <span id="prescriber">
                                    Prescriber: """),_display_(/*76.50*/prescription/*76.62*/.getPrescriberLastName),format.raw/*76.84*/(""", """),_display_(/*76.87*/prescription/*76.99*/.getPrescriberFirstName),format.raw/*76.122*/("""</span>
                            </div>
                            <div class="medicationRemaining">Current Quantity: """),_display_(/*78.81*/prescription/*78.93*/.getMedicationItem.getQuantityCurrent),format.raw/*78.130*/("""</div>
                                <div class="lowMedication">"""),_display_(/*79.61*/prescription/*79.73*/.getFormularyMessage),format.raw/*79.93*/("""</div>
                            <div class="replacement hidden">
                                <div class="prescription-grid prescriptionHeader">
                                    <label class="prescription-field prescriptionName" for="prescription">Prescription</label>
                                    <label class="prescription-field prescriptionAdministration">Administration</label>
                                    <label class="prescription-field prescriptionAdministrationDays">Days</label>
                                    <label class="prescription-field prescriptionAmount">Amount</label>
                                </div>

                                """),_display_(/*88.34*/prescriptionRow( viewModel.getMedicationAdministrationItems, x-1, null )),format.raw/*88.106*/("""

                            """),format.raw/*90.29*/("""</div>
                        </li>
                    """)))}),format.raw/*92.22*/("""

                """)))}),format.raw/*94.18*/("""
                """),format.raw/*95.17*/("""</ol>

                <div id="disclaimerWrap">
                    <label for="disclaimer">
                        The patient was counseled on the risks and side effects of the medications dispensed.</label>
                    <!--ISSUE#138 https://teamfemr.atlassian.net/browse/FEMR-138
                     Contributed by Mohammad Alahmadi during the CEN5035 course at FSU -->
                    <input type="checkbox" name="disclaimer" value="1" required/>
                    <input type="hidden" name="disclaimer" value="0" />
                </div>
                <div id="submitWrap">
                    <button type="submit" id="pharmacySubmitBtn" class="fButton fRedButton fSubmitButton pull-right">
                        Submit</button>

                    <a href=""""),_display_(/*109.31*/{HistoryController.indexPatientGet(
                        Integer.toString(viewModel.getPatient.getId)).url}),format.raw/*110.75*/("""" class="fButton fOtherButton pull-left" target="_blank">Patient History</a>
                    <a href=""""),_display_(/*111.31*/{MedicalController.editGet(viewModel.getPatient.getId).url}),format.raw/*111.90*/("""" class="fButton fOtherButton pull-left" target="_blank">View in Medical</a>
                </div>

            </div>

        </div>


    </div>
""")))}),format.raw/*120.2*/("""
""")))}),format.raw/*121.2*/("""
"""))
      }
    }
  }

  def render(currentUser:femr.common.dtos.CurrentUser,viewModel:femr.ui.models.pharmacy.EditViewModelGet,searchError:java.lang.Boolean,assets:AssetsFinder): play.twirl.api.HtmlFormat.Appendable = apply(currentUser,viewModel,searchError,assets)

  def f:((femr.common.dtos.CurrentUser,femr.ui.models.pharmacy.EditViewModelGet,java.lang.Boolean,AssetsFinder) => play.twirl.api.HtmlFormat.Appendable) = (currentUser,viewModel,searchError,assets) => apply(currentUser,viewModel,searchError,assets)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  DATE: Wed Jan 20 18:29:27 PST 2021
                  SOURCE: /Users/noel/Desktop/CPE/femr/femr/app/femr/ui/views/pharmacies/edit.scala.html
                  HASH: 628ca3af80bf2b6512ae0a2b353ad68ae8132772
                  MATRIX: 1060->1|1284->155|1343->208|1402->261|1464->317|1510->357|1559->400|1617->452|1689->517|1762->583|1788->600|1869->604|1906->614|1972->653|1987->659|2040->691|2126->750|2141->756|2211->804|2297->863|2312->869|2378->914|2466->975|2481->981|2540->1019|2580->1042|2605->1058|2686->1062|2723->1072|2780->1102|2795->1108|2841->1133|2909->1174|2924->1180|2985->1220|3031->153|3059->577|3091->1036|3123->1228|3152->1231|3293->1362|3334->1364|3366->1369|3434->1410|3612->1566|3651->1578|3733->1633|3748->1639|3830->1712|3870->1714|3913->1729|4065->1854|4107->1887|4147->1889|4196->1911|4253->1952|4293->1954|4346->1980|4399->2024|4439->2026|4496->2055|4528->2060|4546->2069|4598->2100|4661->2132|4714->2154|4763->2172|4808->2189|5139->2493|5178->2523|5218->2525|5275->2555|5329->2593|5369->2595|5430->2629|5480->2670|5520->2672|5585->2709|5621->2718|5639->2727|5688->2755|5726->2766|5745->2775|5799->2807|5878->2855|5943->2889|6004->2919|6057->2944|6196->3056|6258->3102|6298->3104|6347->3126|6403->3173|6459->3191|6512->3216|6572->3249|6598->3254|6640->3269|6661->3281|6699->3297|6812->3383|6838->3388|6880->3402|6902->3414|6930->3420|7135->3598|7156->3610|7203->3636|7232->3637|7440->3818|7461->3830|7504->3852|7534->3855|7555->3867|7600->3890|7750->4013|7771->4025|7830->4062|7924->4129|7945->4141|7986->4161|8702->4850|8796->4922|8854->4952|8943->5010|8993->5029|9038->5046|9854->5834|9986->5944|10121->6051|10202->6110|10383->6260|10416->6262
                  LINES: 28->1|31->3|32->4|33->5|34->6|35->7|36->8|37->9|38->10|40->12|40->12|42->12|44->14|44->14|44->14|44->14|45->15|45->15|45->15|46->16|46->16|46->16|47->17|47->17|47->17|48->19|48->19|50->19|52->21|52->21|52->21|52->21|53->22|53->22|53->22|55->2|56->11|57->18|58->23|60->25|60->25|60->25|61->26|63->28|63->28|67->32|68->33|68->33|68->33|68->33|71->36|76->41|76->41|76->41|77->42|77->42|77->42|78->43|78->43|78->43|79->44|79->44|79->44|79->44|81->46|82->47|83->48|84->49|91->56|91->56|91->56|92->57|92->57|92->57|93->58|93->58|93->58|94->59|94->59|94->59|94->59|94->59|94->59|94->59|95->60|96->61|97->62|98->63|101->66|101->66|101->66|102->67|102->67|102->67|103->68|103->68|103->68|103->68|103->68|103->68|104->69|104->69|104->69|104->69|104->69|107->72|107->72|107->72|107->72|111->76|111->76|111->76|111->76|111->76|111->76|113->78|113->78|113->78|114->79|114->79|114->79|123->88|123->88|125->90|127->92|129->94|130->95|144->109|145->110|146->111|146->111|155->120|156->121
                  -- GENERATED --
              */
          