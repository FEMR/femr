
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
Seq[Any](format.raw/*1.153*/("""

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
                  DATE: Tue Oct 27 21:59:18 PDT 2020
                  SOURCE: C:/Users/Noel/Documents/csc402/lemur-femr/femr/app/femr/ui/views/pharmacies/edit.scala.html
                  HASH: 92466c5e6feaca934ebd9a8aa838bcc7366befb4
                  MATRIX: 1060->1|1284->157|1343->211|1402->265|1464->322|1510->363|1559->407|1617->460|1689->526|1762->594|1788->611|1869->615|1908->627|1974->666|1989->672|2042->704|2129->764|2144->770|2214->818|2301->878|2316->884|2382->929|2471->991|2486->997|2545->1035|2586->1060|2611->1076|2692->1080|2731->1092|2788->1122|2803->1128|2849->1153|2918->1195|2933->1201|2994->1241|3043->152|3074->587|3107->1053|3140->1250|3171->1255|3312->1386|3353->1388|3386->1394|3456->1437|3634->1593|3677->1609|3760->1665|3775->1671|3857->1744|3897->1746|3943->1764|4100->1894|4142->1927|4182->1929|4232->1952|4289->1993|4329->1995|4383->2022|4436->2066|4476->2068|4534->2098|4566->2103|4584->2112|4636->2143|4701->2177|4755->2200|4805->2219|4851->2237|5189->2548|5228->2578|5268->2580|5326->2611|5380->2649|5420->2651|5482->2686|5532->2727|5572->2729|5638->2767|5674->2776|5692->2785|5741->2813|5779->2824|5798->2833|5852->2865|5932->2914|5998->2949|6060->2980|6114->3006|6256->3121|6318->3167|6358->3169|6408->3192|6464->3239|6520->3257|6574->3283|6634->3316|6660->3321|6702->3336|6723->3348|6761->3364|6875->3451|6901->3456|6943->3470|6965->3482|6993->3488|7201->3669|7222->3681|7269->3707|7298->3708|7510->3893|7531->3905|7574->3927|7604->3930|7625->3942|7670->3965|7822->4090|7843->4102|7902->4139|7997->4207|8018->4219|8059->4239|8784->4937|8878->5009|8938->5041|9029->5101|9081->5122|9127->5140|9957->5942|10090->6053|10226->6161|10307->6220|10497->6379|10531->6382
                  LINES: 28->1|31->3|32->4|33->5|34->6|35->7|36->8|37->9|38->10|40->12|40->12|42->12|44->14|44->14|44->14|44->14|45->15|45->15|45->15|46->16|46->16|46->16|47->17|47->17|47->17|48->19|48->19|50->19|52->21|52->21|52->21|52->21|53->22|53->22|53->22|55->1|57->11|58->18|59->23|61->25|61->25|61->25|62->26|64->28|64->28|68->32|69->33|69->33|69->33|69->33|72->36|77->41|77->41|77->41|78->42|78->42|78->42|79->43|79->43|79->43|80->44|80->44|80->44|80->44|82->46|83->47|84->48|85->49|92->56|92->56|92->56|93->57|93->57|93->57|94->58|94->58|94->58|95->59|95->59|95->59|95->59|95->59|95->59|95->59|96->60|97->61|98->62|99->63|102->66|102->66|102->66|103->67|103->67|103->67|104->68|104->68|104->68|104->68|104->68|104->68|105->69|105->69|105->69|105->69|105->69|108->72|108->72|108->72|108->72|112->76|112->76|112->76|112->76|112->76|112->76|114->78|114->78|114->78|115->79|115->79|115->79|124->88|124->88|126->90|128->92|130->94|131->95|145->109|146->110|147->111|147->111|156->120|157->121
                  -- GENERATED --
              */
          