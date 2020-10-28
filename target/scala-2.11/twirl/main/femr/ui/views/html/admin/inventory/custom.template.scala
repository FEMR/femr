
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
Seq[Any](format.raw/*1.129*/("""

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
                  DATE: Tue Oct 27 21:59:17 PDT 2020
                  SOURCE: C:/Users/Noel/Documents/csc402/lemur-femr/femr/app/femr/ui/views/admin/inventory/custom.scala.html
                  HASH: ff0acc0f693abff839a509953f1bfb0b9ce233f3
                  MATRIX: 1058->1|1258->133|1325->195|1397->262|1449->306|1473->322|1553->326|1587->334|1643->364|1657->370|1725->418|1790->456|1805->462|1858->494|1886->502|1912->519|1993->523|2028->531|2094->570|2109->576|2176->622|2259->678|2274->684|2325->714|2379->128|2409->303|2438->499|2467->728|2498->733|2636->861|2677->863|2712->872|2776->915|2811->924|2853->957|2893->959|2930->969|3002->1010|3037->1019|3052->1025|3143->1107|3183->1109|3222->1121|3944->1867|3998->1894|4069->1949|4109->1951|4167->1982|4232->2038|4280->2048|4342->2082|4385->2098|4410->2102|4440->2105|4465->2109|4536->2149|4594->2176|4644->2198|5962->3531|6024->3566|6095->3621|6135->3623|6201->3662|6266->3718|6315->3728|6385->3770|6428->3786|6453->3790|6483->3793|6508->3797|6587->3845|6653->3880|6711->3910|7288->4459|7331->4492|7372->4494|7410->4504|7464->4526|7504->4535
                  LINES: 28->1|31->3|32->4|33->5|35->7|35->7|37->7|39->9|39->9|39->9|39->9|40->10|40->10|40->10|41->12|41->12|43->12|45->14|45->14|45->14|45->14|46->15|46->15|46->15|48->1|50->6|51->11|52->16|54->18|54->18|54->18|56->20|56->20|58->22|58->22|58->22|59->23|60->24|62->26|62->26|62->26|62->26|64->28|79->43|80->44|80->44|80->44|81->45|81->45|81->45|82->46|82->46|82->46|82->46|82->46|83->47|84->48|85->49|112->76|113->77|113->77|113->77|114->78|114->78|114->78|115->79|115->79|115->79|115->79|115->79|116->80|117->81|118->82|137->101|137->101|137->101|138->102|139->103|141->105
                  -- GENERATED --
              */
          