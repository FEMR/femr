
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
/*1.2*/import femr.ui.models.admin.inventory.ManageViewModelGet

object manage extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template3[femr.common.dtos.CurrentUser,ManageViewModelGet,AssetsFinder,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*2.2*/(currentUser: femr.common.dtos.CurrentUser, viewModel: ManageViewModelGet, assets: AssetsFinder):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {
/*4.2*/import femr.ui.controllers.admin.routes.InventoryController
/*6.2*/import femr.ui.views.html.partials.admin.inventory.inventoryMenu
/*7.2*/import femr.ui.views.html.layouts.admin

def /*9.6*/additionalStyles/*9.22*/:play.twirl.api.HtmlFormat.Appendable = {_display_(

Seq[Any](format.raw/*9.26*/("""

        """),format.raw/*11.9*/("""<link rel="stylesheet" href=""""),_display_(/*11.39*/assets/*11.45*/.path("css/libraries/select2.min.css")),format.raw/*11.83*/("""">
        <link rel="stylesheet" href=""""),_display_(/*12.39*/assets/*12.45*/.path("css/libraries/jquery.dataTables.min.css")),format.raw/*12.93*/("""">
        <link rel="stylesheet" href=""""),_display_(/*13.39*/assets/*13.45*/.path("css/admin/inventory.css")),format.raw/*13.77*/("""">
    """)))};def /*15.6*/additionalScripts/*15.23*/:play.twirl.api.HtmlFormat.Appendable = {_display_(

Seq[Any](format.raw/*15.27*/("""

        """),format.raw/*17.9*/("""<script type="text/javascript" src=""""),_display_(/*17.46*/assets/*17.52*/.path("js/libraries/select2.min.js")),format.raw/*17.88*/(""""></script>
        <script type = "text/javascript" src=""""),_display_(/*18.48*/assets/*18.54*/.path("js/libraries/jquery.dataTables.min.js")),format.raw/*18.100*/(""""></script>
        <script type="text/javascript" src=""""),_display_(/*19.46*/assets/*19.52*/.path("js/libraries/typeahead.bundle.min.js")),format.raw/*19.97*/(""""></script>
        <script type = "text/javascript" src=""""),_display_(/*20.48*/assets/*20.54*/.path("js/admin/inventory.js")),format.raw/*20.84*/(""""></script>
    """)))};
Seq[Any](format.raw/*3.1*/("""
"""),format.raw/*5.1*/("""
"""),format.raw/*8.1*/("""
    """),format.raw/*14.6*/("""
    """),format.raw/*21.6*/("""

"""),_display_(/*23.2*/admin("Inventory - Formulary", currentUser, styles = additionalStyles, scripts = additionalScripts, assets = assets)/*23.118*/ {_display_(Seq[Any](format.raw/*23.120*/("""

    """),_display_(/*25.6*/inventoryMenu(viewModel.getMissionTripItem)),format.raw/*25.49*/("""

    """),_display_(/*27.6*/if(currentUser.getTripId != null)/*27.39*/ {_display_(Seq[Any](format.raw/*27.41*/("""

    """),format.raw/*29.5*/("""<div id="tripSelection">
        """),_display_(/*30.10*/helper/*30.16*/.form(action = InventoryController.managePost())/*30.64*/ {_display_(Seq[Any](format.raw/*30.66*/("""
            """),format.raw/*31.13*/("""<h4>Select a different trip inventory to view: </h4>
            <select id="selectTripInventory" name="selectedTrip">
            """),_display_(/*33.14*/for(i <- 1 to viewModel.getMissionTripList.size) yield /*33.62*/ {_display_(Seq[Any](format.raw/*33.64*/("""
                """),format.raw/*34.17*/("""<option value=""""),_display_(/*34.33*/viewModel/*34.42*/.getMissionTripList.get(i - 1).getId),format.raw/*34.78*/("""">"""),_display_(/*34.81*/viewModel/*34.90*/.getMissionTripList.get(i - 1).getFriendlyTripTitle),format.raw/*34.141*/("""</option>
            """)))}),format.raw/*35.14*/("""
            """),format.raw/*36.13*/("""</select>
            <button type="submit">Select</button>
        """)))}),format.raw/*38.10*/("""
    """),format.raw/*39.5*/("""</div>

    <div id="currentMedication">
        <h3>Current Formulary:</h3>
        <div class="row" id="currentMedicationHeader">
            <div class="col-xs-9">
                <ul>
                    <li>Add medications using the buttons above</li>
                    <li>Formulary only applies to Users on the trip <b>mentioned above</b></li>
                    <li>Manually edit Current/Initial quantity below</li>
                    <li>Quantity will be subtracted from the Current Quantity column after being dispensed in Pharmacy</li>
                    <li>Export your inventory data using the "Export as CSV" button to the right</li>
                </ul>
            </div>
            <div class="col-xs-3">
                """),_display_(/*54.18*/if( currentUser.getTripId() == null )/*54.55*/{_display_(Seq[Any](format.raw/*54.56*/("""
                    """),format.raw/*55.21*/("""<span class="fExportCSVButton pull-right disabled">
                        <a class="btn fButton fOtherButton disabled" href="#">Export as CSV</a>
                        """)))}/*57.27*/else/*57.32*/{_display_(Seq[Any](format.raw/*57.33*/("""
                    """),format.raw/*58.21*/("""<span class="fExportCSVButton pull-right">
                        <a class="btn fButton fOtherButton" href=""""),_display_(/*59.68*/{InventoryController.exportGet(viewModel.getMissionTripItem.getId).url}),format.raw/*59.139*/("""">Export as CSV</a>
                        """)))}),format.raw/*60.26*/("""
            """),format.raw/*61.13*/("""</span>
            </div>
        </div>
        <div id="currentMedicationsWrap">
            <table id="inventoryTable" class="table">
                <thead>
                    <th>#</th>
                    <th>Medication</th>
                    <th>Current Quantity</th>
                    <th>Initial Quantity</th>
                    <th>Date Added</th>
                    <th>Added By</th>
                    <th>Remove</th>
                </thead>
                <tbody>
                """),_display_(/*76.18*/for(m <- 1 to viewModel.getMedications.size) yield /*76.62*/ {_display_(Seq[Any](format.raw/*76.64*/("""
                    """),_display_(/*77.22*/if(viewModel.getMedications.get(m - 1).getIsDeleted == null)/*77.82*/{_display_(Seq[Any](format.raw/*77.83*/("""
                        """),format.raw/*78.25*/("""<tr>
                            <td>"""),_display_(/*79.34*/m),format.raw/*79.35*/("""</td>
                            <td class="name">"""),_display_(/*80.47*/viewModel/*80.56*/.getMedications.get(m - 1).getFullName),format.raw/*80.94*/("""</td>

                            <td class="currentQuantity" value=""""),_display_(/*82.65*/viewModel/*82.74*/.getMedications.get(m-1).getId),format.raw/*82.104*/("""">

                                <span class="editCurrentQuantity" style="display:inline-block;">"""),_display_(/*84.98*/viewModel/*84.107*/.getMedications.get(m - 1).getQuantityCurrent),format.raw/*84.152*/("""</span>
                                <input class="editCurrentInput fInput" style="display:none;" type="number" value=""""),_display_(/*85.116*/viewModel/*85.125*/.getMedications.get(m - 1).getQuantityCurrent),format.raw/*85.170*/("""" />
                                <input type="hidden" class="medication_id" value=""""),_display_(/*86.84*/viewModel/*86.93*/.getMedications.get(m-1).getId),format.raw/*86.123*/("""" />
                                <input type="hidden" class="trip_id" value=""""),_display_(/*87.78*/viewModel/*87.87*/.getMissionTripItem.getId()),format.raw/*87.114*/("""" />
                                <button type="button" class="btn btn-xs editCurrentQuantityBtn pull-right" style="display:inline-block;" value=""""),_display_(/*88.146*/viewModel/*88.155*/.getMedications.get(m-1).getId),format.raw/*88.185*/(""""><i class="glyphicon glyphicon-edit"></i></button>

                            </td>

                            <td class="totalQuantity" value=""""),_display_(/*92.63*/viewModel/*92.72*/.getMedications.get(m-1).getId),format.raw/*92.102*/("""">

                                <span class="editTotalQuantity" style="display:inline-block;">"""),_display_(/*94.96*/viewModel/*94.105*/.getMedications.get(m - 1).getQuantityTotal),format.raw/*94.148*/("""</span>
                                <input class="editTotalInput fInput" style="display:none;" type="number" value=""""),_display_(/*95.114*/viewModel/*95.123*/.getMedications.get(m - 1).getQuantityTotal),format.raw/*95.166*/("""" />
                                <input type="hidden" class="medication_id" value=""""),_display_(/*96.84*/viewModel/*96.93*/.getMedications.get(m-1).getId),format.raw/*96.123*/("""" />
                                <input type="hidden" class="trip_id" value=""""),_display_(/*97.78*/viewModel/*97.87*/.getMissionTripItem.getId()),format.raw/*97.114*/("""" />
                                <button type="button" class="btn btn-xs editTotalQuantityBtn pull-right" style="display:inline-block;" value=""""),_display_(/*98.144*/viewModel/*98.153*/.getMedications.get(m-1).getId),format.raw/*98.183*/(""""><i class="glyphicon glyphicon-edit"></i></button>

                            </td>

                            <td class="time">"""),_display_(/*102.47*/viewModel/*102.56*/.getMedications.get(m-1).getTimeAdded),format.raw/*102.93*/("""</td>
                            <td class="created">"""),_display_(/*103.50*/viewModel/*103.59*/.getMedications.get(m-1).getCreatedBy),format.raw/*103.96*/("""</td>
                            <td>

                                <button type="button" class="btn btn-danger removeBtn" data-medicationid=""""),_display_(/*106.108*/viewModel/*106.117*/.getMedications.get(m-1).getId),format.raw/*106.147*/("""" data-tripid=""""),_display_(/*106.163*/viewModel/*106.172*/.getMissionTripItem.getId()),format.raw/*106.199*/("""">Remove</button>

                            </td>
                        </tr>
                    """)))}),format.raw/*110.22*/("""
                """)))}),format.raw/*111.18*/("""
                """),format.raw/*112.17*/("""</tbody>
            </table>
        </div>
    </div>
    """)))}),format.raw/*116.6*/("""
""")))}),format.raw/*117.2*/("""
"""))
      }
    }
  }

  def render(currentUser:femr.common.dtos.CurrentUser,viewModel:ManageViewModelGet,assets:AssetsFinder): play.twirl.api.HtmlFormat.Appendable = apply(currentUser,viewModel,assets)

  def f:((femr.common.dtos.CurrentUser,ManageViewModelGet,AssetsFinder) => play.twirl.api.HtmlFormat.Appendable) = (currentUser,viewModel,assets) => apply(currentUser,viewModel,assets)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  DATE: Wed Jan 20 18:29:25 PST 2021
                  SOURCE: /Users/noel/Desktop/CPE/femr/femr/app/femr/ui/views/admin/inventory/manage.scala.html
                  HASH: 449a18f34c35235d08d8fd36ba92b0f4f40ccfbe
                  MATRIX: 675->1|1091->59|1260->158|1327->220|1399->286|1451->332|1475->348|1555->352|1592->362|1649->392|1664->398|1723->436|1791->477|1806->483|1875->531|1943->572|1958->578|2011->610|2042->624|2068->641|2149->645|2186->655|2250->692|2265->698|2322->734|2408->793|2423->799|2491->845|2575->902|2590->908|2656->953|2742->1012|2757->1018|2808->1048|2863->156|2890->218|2917->326|2949->618|2981->1065|3010->1068|3136->1184|3177->1186|3210->1193|3274->1236|3307->1243|3349->1276|3389->1278|3422->1284|3483->1318|3498->1324|3555->1372|3595->1374|3636->1387|3795->1519|3859->1567|3899->1569|3944->1586|3987->1602|4005->1611|4062->1647|4092->1650|4110->1659|4183->1710|4237->1733|4278->1746|4378->1815|4410->1820|5183->2566|5229->2603|5268->2604|5317->2625|5509->2799|5522->2804|5561->2805|5610->2826|5747->2936|5840->3007|5916->3052|5957->3065|6489->3570|6549->3614|6589->3616|6638->3638|6707->3698|6746->3699|6799->3724|6864->3762|6886->3763|6965->3815|6983->3824|7042->3862|7140->3933|7158->3942|7210->3972|7338->4073|7357->4082|7424->4127|7575->4250|7594->4259|7661->4304|7776->4392|7794->4401|7846->4431|7955->4513|7973->4522|8022->4549|8200->4699|8219->4708|8271->4738|8448->4888|8466->4897|8518->4927|8644->5026|8663->5035|8728->5078|8877->5199|8896->5208|8961->5251|9076->5339|9094->5348|9146->5378|9255->5460|9273->5469|9322->5496|9498->5644|9517->5653|9569->5683|9731->5817|9750->5826|9809->5863|9892->5918|9911->5927|9970->5964|10146->6111|10166->6120|10219->6150|10264->6166|10284->6175|10334->6202|10470->6306|10520->6324|10566->6341|10658->6402|10691->6404
                  LINES: 24->1|29->2|32->4|33->6|34->7|36->9|36->9|38->9|40->11|40->11|40->11|40->11|41->12|41->12|41->12|42->13|42->13|42->13|43->15|43->15|45->15|47->17|47->17|47->17|47->17|48->18|48->18|48->18|49->19|49->19|49->19|50->20|50->20|50->20|52->3|53->5|54->8|55->14|56->21|58->23|58->23|58->23|60->25|60->25|62->27|62->27|62->27|64->29|65->30|65->30|65->30|65->30|66->31|68->33|68->33|68->33|69->34|69->34|69->34|69->34|69->34|69->34|69->34|70->35|71->36|73->38|74->39|89->54|89->54|89->54|90->55|92->57|92->57|92->57|93->58|94->59|94->59|95->60|96->61|111->76|111->76|111->76|112->77|112->77|112->77|113->78|114->79|114->79|115->80|115->80|115->80|117->82|117->82|117->82|119->84|119->84|119->84|120->85|120->85|120->85|121->86|121->86|121->86|122->87|122->87|122->87|123->88|123->88|123->88|127->92|127->92|127->92|129->94|129->94|129->94|130->95|130->95|130->95|131->96|131->96|131->96|132->97|132->97|132->97|133->98|133->98|133->98|137->102|137->102|137->102|138->103|138->103|138->103|141->106|141->106|141->106|141->106|141->106|141->106|145->110|146->111|147->112|151->116|152->117
                  -- GENERATED --
              */
          