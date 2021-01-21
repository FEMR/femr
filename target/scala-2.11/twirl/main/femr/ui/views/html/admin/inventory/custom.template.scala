
package femr.ui.views.html.admin.inventory

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

object custom extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template3[femr.common.dtos.CurrentUser,femr.ui.models.admin.inventory.CustomViewModelGet,AssetsFinder,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(currentUser: femr.common.dtos.CurrentUser, viewModel: femr.ui.models.admin.inventory.CustomViewModelGet, assets: AssetsFinder):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {
/*3.2*/import femr.ui.controllers.admin.routes.InventoryController
/*4.2*/import femr.ui.views.html.partials.admin.inventory.inventoryMenu
/*5.2*/import femr.ui.views.html.layouts.admin

def /*7.2*/additionalStyles/*7.18*/:play.twirl.api.HtmlFormat.Appendable = {_display_(

Seq[Any](format.raw/*7.22*/("""

    """),format.raw/*9.5*/("""<link rel="stylesheet" href=""""),_display_(/*9.35*/assets/*9.41*/.path("css/libraries/jquery.dataTables.min.css")),format.raw/*9.89*/("""">
    <link rel="stylesheet" href=""""),_display_(/*10.35*/assets/*10.41*/.path("css/admin/inventory.css")),format.raw/*10.73*/("""">
""")))};def /*12.2*/additionalScripts/*12.19*/:play.twirl.api.HtmlFormat.Appendable = {_display_(

Seq[Any](format.raw/*12.23*/("""

    """),format.raw/*14.5*/("""<script type = "text/javascript" src=""""),_display_(/*14.44*/assets/*14.50*/.path("js/libraries/jquery.dataTables.min.js")),format.raw/*14.96*/(""""></script>
    <script type = "text/javascript" src=""""),_display_(/*15.44*/assets/*15.50*/.path("js/admin/inventory.js")),format.raw/*15.80*/(""""></script>
""")))};
Seq[Any](format.raw/*2.1*/("""
"""),format.raw/*6.1*/("""
"""),format.raw/*11.2*/("""
"""),format.raw/*16.2*/("""

"""),_display_(/*18.2*/admin("Inventory - Add Custom Medication", currentUser, styles = additionalStyles, scripts = additionalScripts, assets = assets)/*18.130*/ {_display_(Seq[Any](format.raw/*18.132*/("""

    """),_display_(/*20.6*/inventoryMenu(viewModel.getMissionTripItem)),format.raw/*20.49*/("""

    """),_display_(/*22.6*/if(currentUser.getTripId == null)/*22.39*/ {_display_(Seq[Any](format.raw/*22.41*/("""
        """),format.raw/*23.9*/("""<fieldset disabled="disabled">
        """)))}),format.raw/*24.10*/("""

    """),_display_(/*26.6*/helper/*26.12*/.form(action = InventoryController.customPost(viewModel.getMissionTripItem.getId))/*26.94*/ {_display_(Seq[Any](format.raw/*26.96*/("""

        """),format.raw/*28.9*/("""<h3>Add Custom Medicine To Formulary:</h3>
        <ul>
            <li>If a medication does not exist in the concept dictionary, add it below</li>
        </ul>
        <div class="newMedicationWrap">

            <div class="row">
                <div class="fieldMedication" >
                    <label for="medicationName">Brand</label>
                    <input type="text" class="fInput" id="medicationName" name="medicationName"  placeholder="" />
                </div>

                <div class="fieldMedication">
                    <label for="medicationForm">Form</label>
                    <select name="medicationForm" class="fOption">
                        """),format.raw/*43.77*/("""
                        """),_display_(/*44.26*/for(formIndex <- 1 to viewModel.getAvailableForms.size) yield /*44.81*/ {_display_(Seq[Any](format.raw/*44.83*/("""
                            """),_display_(/*45.30*/defining(viewModel.getAvailableForms.get(formIndex - 1))/*45.86*/ { form =>_display_(Seq[Any](format.raw/*45.96*/("""
                                """),format.raw/*46.33*/("""<option value=""""),_display_(/*46.49*/form),format.raw/*46.53*/("""">"""),_display_(/*46.56*/form),format.raw/*46.60*/("""</option>
                            """)))}),format.raw/*47.30*/("""
                        """)))}),format.raw/*48.26*/("""
                    """),format.raw/*49.21*/("""</select>
                </div>

                <div class="fieldMedication">
                    <label for="medicationQuantity">Quantity</label>
                    <input type="number" class="fInput text" name="medicationQuantity" placeholder="" />
                </div>

            </div>
            <div class="row ingredients">
                <div class="ingredientWrap">

                    <div class="ingredientFields first">

                        <div class="fieldMedication">
                            <label for="medicationIngredient">Generic</label>
                            <input type="text" name="medicationIngredient[]" class="fInput medicationIngredient" placeholder="" />
                        </div>

                        <div class="fieldMedication">
                            <label for="medicationStrength">Strength</label>
                            <input type="number" name="medicationStrength[]" class="fInput medicationsStrength" placeholder="" />
                        </div>

                        <div class="fieldMedication">
                            <label for="medicationUnit">Unit</label>
                            <select name="medicationUnit[]" class="fOption">
                                """),format.raw/*76.76*/("""
                                """),_display_(/*77.34*/for(unitIndex <- 1 to viewModel.getAvailableUnits.size) yield /*77.89*/ {_display_(Seq[Any](format.raw/*77.91*/("""
                                    """),_display_(/*78.38*/defining(viewModel.getAvailableUnits.get(unitIndex - 1))/*78.94*/ { unit =>_display_(Seq[Any](format.raw/*78.104*/("""
                                        """),format.raw/*79.41*/("""<option value=""""),_display_(/*79.57*/unit),format.raw/*79.61*/("""">"""),_display_(/*79.64*/unit),format.raw/*79.68*/("""</option>
                                    """)))}),format.raw/*80.38*/("""
                                """)))}),format.raw/*81.34*/("""
                            """),format.raw/*82.29*/("""</select>
                        </div>

                        <a class="fButton removeIngredient">
                            <span>-</span>
                        </a>

                    </div>

                    <a class="fButton" id="addNewIngredient">
                        <span>+</span>
                    </a>
                </div>
            </div>
            <div class="row">
                <button class="fButton" id="submitMedicationButton">Submit</button>
            </div>

        </div>
        """),_display_(/*101.10*/if(currentUser.getTripId == null)/*101.43*/ {_display_(Seq[Any](format.raw/*101.45*/("""
        """),format.raw/*102.9*/("""</fieldset>
        """)))}),format.raw/*103.10*/("""

    """)))}),format.raw/*105.6*/("""
""")))}))
      }
    }
  }

  def render(currentUser:femr.common.dtos.CurrentUser,viewModel:femr.ui.models.admin.inventory.CustomViewModelGet,assets:AssetsFinder): play.twirl.api.HtmlFormat.Appendable = apply(currentUser,viewModel,assets)

  def f:((femr.common.dtos.CurrentUser,femr.ui.models.admin.inventory.CustomViewModelGet,AssetsFinder) => play.twirl.api.HtmlFormat.Appendable) = (currentUser,viewModel,assets) => apply(currentUser,viewModel,assets)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  DATE: Wed Jan 20 18:29:25 PST 2021
                  SOURCE: /Users/noel/Desktop/CPE/femr/femr/app/femr/ui/views/admin/inventory/custom.scala.html
                  HASH: 929e73e05792cb846d32eba42ae06cf8ca3972c3
                  MATRIX: 1058->1|1258->131|1325->192|1397->258|1449->300|1473->316|1553->320|1585->326|1641->356|1655->362|1723->410|1787->447|1802->453|1855->485|1882->491|1908->508|1989->512|2022->518|2088->557|2103->563|2170->609|2252->664|2267->670|2318->700|2369->129|2396->298|2424->489|2452->713|2481->716|2619->844|2660->846|2693->853|2757->896|2790->903|2832->936|2872->938|2908->947|2979->987|3012->994|3027->1000|3118->1082|3158->1084|3195->1094|3902->1825|3955->1851|4026->1906|4066->1908|4123->1938|4188->1994|4236->2004|4297->2037|4340->2053|4365->2057|4395->2060|4420->2064|4490->2103|4547->2129|4596->2150|5887->3456|5948->3490|6019->3545|6059->3547|6124->3585|6189->3641|6238->3651|6307->3692|6350->3708|6375->3712|6405->3715|6430->3719|6508->3766|6573->3800|6630->3829|7188->4359|7231->4392|7272->4394|7309->4403|7362->4424|7400->4431
                  LINES: 28->1|31->3|32->4|33->5|35->7|35->7|37->7|39->9|39->9|39->9|39->9|40->10|40->10|40->10|41->12|41->12|43->12|45->14|45->14|45->14|45->14|46->15|46->15|46->15|48->2|49->6|50->11|51->16|53->18|53->18|53->18|55->20|55->20|57->22|57->22|57->22|58->23|59->24|61->26|61->26|61->26|61->26|63->28|78->43|79->44|79->44|79->44|80->45|80->45|80->45|81->46|81->46|81->46|81->46|81->46|82->47|83->48|84->49|111->76|112->77|112->77|112->77|113->78|113->78|113->78|114->79|114->79|114->79|114->79|114->79|115->80|116->81|117->82|136->101|136->101|136->101|137->102|138->103|140->105
                  -- GENERATED --
              */
          