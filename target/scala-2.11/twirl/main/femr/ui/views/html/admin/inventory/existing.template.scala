
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

object existing extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template3[femr.common.dtos.CurrentUser,femr.ui.models.admin.inventory.ExistingViewModelGet,AssetsFinder,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(currentUser: femr.common.dtos.CurrentUser, viewModel: femr.ui.models.admin.inventory.ExistingViewModelGet, assets: AssetsFinder):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {
/*3.2*/import femr.ui.controllers.admin.routes.InventoryController
/*4.2*/import femr.ui.views.html.layouts.admin
/*5.2*/import femr.ui.views.html.partials.admin.inventory.inventoryMenu

def /*7.2*/additionalStyles/*7.18*/:play.twirl.api.HtmlFormat.Appendable = {_display_(

Seq[Any](format.raw/*7.22*/("""

    """),format.raw/*9.5*/("""<link rel="stylesheet" href=""""),_display_(/*9.35*/assets/*9.41*/.path("css/libraries/select2.min.css")),format.raw/*9.79*/("""">
    <link rel="stylesheet" href=""""),_display_(/*10.35*/assets/*10.41*/.path("css/libraries/jquery.dataTables.min.css")),format.raw/*10.89*/("""">
    <link rel="stylesheet" href=""""),_display_(/*11.35*/assets/*11.41*/.path("css/admin/inventory.css")),format.raw/*11.73*/("""">
""")))};def /*13.2*/additionalScripts/*13.19*/:play.twirl.api.HtmlFormat.Appendable = {_display_(

Seq[Any](format.raw/*13.23*/("""

    """),format.raw/*15.5*/("""<script type="text/javascript" src=""""),_display_(/*15.42*/assets/*15.48*/.path("js/libraries/select2.min.js")),format.raw/*15.84*/(""""></script>
    <script type = "text/javascript" src=""""),_display_(/*16.44*/assets/*16.50*/.path("js/libraries/jquery.dataTables.min.js")),format.raw/*16.96*/(""""></script>
    <script type="text/javascript" src=""""),_display_(/*17.42*/assets/*17.48*/.path("js/libraries/typeahead.bundle.min.js")),format.raw/*17.93*/(""""></script>
    <script type = "text/javascript" src=""""),_display_(/*18.44*/assets/*18.50*/.path("js/admin/inventory.js")),format.raw/*18.80*/(""""></script>
""")))};
Seq[Any](format.raw/*2.1*/("""
"""),format.raw/*6.1*/("""
"""),format.raw/*12.2*/("""
"""),format.raw/*19.2*/("""

"""),_display_(/*21.2*/admin("Inventory - Add Existing Medication", currentUser, styles = additionalStyles, scripts = additionalScripts, assets = assets)/*21.132*/ {_display_(Seq[Any](format.raw/*21.134*/("""

    """),_display_(/*23.6*/inventoryMenu(viewModel.getMissionTripItem)),format.raw/*23.49*/("""

    """),_display_(/*25.6*/if(currentUser.getTripId == null)/*25.39*/ {_display_(Seq[Any](format.raw/*25.41*/("""
        """),format.raw/*26.9*/("""<fieldset disabled="disabled">
        """)))}),format.raw/*27.10*/("""

    """),_display_(/*29.6*/helper/*29.12*/.form(action = InventoryController.existingPost(viewModel.getMissionTripItem.getId))/*29.96*/ {_display_(Seq[Any](format.raw/*29.98*/("""

        """),format.raw/*31.9*/("""<h3>Add Existing Medicine To Formulary:</h3>
        <ul>
            <li>Add medication(s) from fEMR's concept dictionary below</li>
            <li>Adjust current/initial quantity in the Formulary after adding</li>
        </ul>
        <div class="newMedicationWrap">
            <select id="addConceptMedicineSelect2" multiple="multiple" name="newConceptMedicationsForInventory[]">
            """),_display_(/*38.14*/defining(viewModel.getConceptMedications)/*38.55*/ { conceptMeds =>_display_(Seq[Any](format.raw/*38.72*/("""
                """),_display_(/*39.18*/for(i <- 1 to conceptMeds.size) yield /*39.49*/ {_display_(Seq[Any](format.raw/*39.51*/("""
                    """),format.raw/*40.21*/("""<option value=""""),_display_(/*40.37*/conceptMeds/*40.48*/.get(i - 1).getId),format.raw/*40.65*/("""">"""),_display_(/*40.68*/conceptMeds/*40.79*/.get(i - 1).getFullName),format.raw/*40.102*/("""</option>
                """)))}),format.raw/*41.18*/("""
            """)))}),format.raw/*42.14*/("""
            """),format.raw/*43.13*/("""</select>

            <div class="row">
                <button class="fButton" id="submitMedicationButton">Submit</button>
            </div>
        </div>

        """),_display_(/*50.10*/if(currentUser.getTripId == null)/*50.43*/ {_display_(Seq[Any](format.raw/*50.45*/("""
            """),format.raw/*51.13*/("""</fieldset>
        """)))}),format.raw/*52.10*/("""
    """)))}),format.raw/*53.6*/("""
""")))}))
      }
    }
  }

  def render(currentUser:femr.common.dtos.CurrentUser,viewModel:femr.ui.models.admin.inventory.ExistingViewModelGet,assets:AssetsFinder): play.twirl.api.HtmlFormat.Appendable = apply(currentUser,viewModel,assets)

  def f:((femr.common.dtos.CurrentUser,femr.ui.models.admin.inventory.ExistingViewModelGet,AssetsFinder) => play.twirl.api.HtmlFormat.Appendable) = (currentUser,viewModel,assets) => apply(currentUser,viewModel,assets)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  DATE: Wed Jan 20 18:29:25 PST 2021
                  SOURCE: /Users/noel/Desktop/CPE/femr/femr/app/femr/ui/views/admin/inventory/existing.scala.html
                  HASH: 2e9f29eb77393ee7448ce8d1776baca767403716
                  MATRIX: 1062->1|1264->133|1331->194|1378->235|1455->302|1479->318|1559->322|1591->328|1647->358|1661->364|1719->402|1783->439|1798->445|1867->493|1931->530|1946->536|1999->568|2026->574|2052->591|2133->595|2166->601|2230->638|2245->644|2302->680|2384->735|2399->741|2466->787|2546->840|2561->846|2627->891|2709->946|2724->952|2775->982|2826->131|2853->300|2881->572|2909->995|2938->998|3078->1128|3119->1130|3152->1137|3216->1180|3249->1187|3291->1220|3331->1222|3367->1231|3438->1271|3471->1278|3486->1284|3579->1368|3619->1370|3656->1380|4082->1779|4132->1820|4187->1837|4232->1855|4279->1886|4319->1888|4368->1909|4411->1925|4431->1936|4469->1953|4499->1956|4519->1967|4564->1990|4622->2017|4667->2031|4708->2044|4904->2213|4946->2246|4986->2248|5027->2261|5079->2282|5115->2288
                  LINES: 28->1|31->3|32->4|33->5|35->7|35->7|37->7|39->9|39->9|39->9|39->9|40->10|40->10|40->10|41->11|41->11|41->11|42->13|42->13|44->13|46->15|46->15|46->15|46->15|47->16|47->16|47->16|48->17|48->17|48->17|49->18|49->18|49->18|51->2|52->6|53->12|54->19|56->21|56->21|56->21|58->23|58->23|60->25|60->25|60->25|61->26|62->27|64->29|64->29|64->29|64->29|66->31|73->38|73->38|73->38|74->39|74->39|74->39|75->40|75->40|75->40|75->40|75->40|75->40|75->40|76->41|77->42|78->43|85->50|85->50|85->50|86->51|87->52|88->53
                  -- GENERATED --
              */
          