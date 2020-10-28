
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
Seq[Any](format.raw/*1.131*/("""

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
                  DATE: Tue Oct 27 21:59:17 PDT 2020
                  SOURCE: C:/Users/Noel/Documents/csc402/lemur-femr/femr/app/femr/ui/views/admin/inventory/existing.scala.html
                  HASH: 8557802a4abbda3b82092f43fd3e991202ad7882
                  MATRIX: 1062->1|1264->135|1331->197|1378->239|1455->308|1479->324|1559->328|1593->336|1649->366|1663->372|1721->410|1786->448|1801->454|1870->502|1935->540|1950->546|2003->578|2031->586|2057->603|2138->607|2173->615|2237->652|2252->658|2309->694|2392->750|2407->756|2474->802|2555->856|2570->862|2636->907|2719->963|2734->969|2785->999|2839->130|2869->305|2898->583|2927->1013|2958->1018|3098->1148|3139->1150|3174->1159|3238->1202|3273->1211|3315->1244|3355->1246|3392->1256|3464->1297|3499->1306|3514->1312|3607->1396|3647->1398|3686->1410|4119->1816|4169->1857|4224->1874|4270->1893|4317->1924|4357->1926|4407->1948|4450->1964|4470->1975|4508->1992|4538->1995|4558->2006|4603->2029|4662->2057|4708->2072|4750->2086|4953->2262|4995->2295|5035->2297|5077->2311|5130->2333|5167->2340
                  LINES: 28->1|31->3|32->4|33->5|35->7|35->7|37->7|39->9|39->9|39->9|39->9|40->10|40->10|40->10|41->11|41->11|41->11|42->13|42->13|44->13|46->15|46->15|46->15|46->15|47->16|47->16|47->16|48->17|48->17|48->17|49->18|49->18|49->18|51->1|53->6|54->12|55->19|57->21|57->21|57->21|59->23|59->23|61->25|61->25|61->25|62->26|63->27|65->29|65->29|65->29|65->29|67->31|74->38|74->38|74->38|75->39|75->39|75->39|76->40|76->40|76->40|76->40|76->40|76->40|76->40|77->41|78->42|79->43|86->50|86->50|86->50|87->51|88->52|89->53
                  -- GENERATED --
              */
          