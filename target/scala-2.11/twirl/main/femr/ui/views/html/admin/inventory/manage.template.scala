
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
Seq[Any](format.raw/*2.98*/("""

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
                  DATE: Tue Oct 27 21:59:17 PDT 2020
                  SOURCE: C:/Users/Noel/Documents/csc402/lemur-femr/femr/app/femr/ui/views/admin/inventory/manage.scala.html
                  HASH: fa8a3b4a352e232fb8fedd2258af58491c48b42a
                  MATRIX: 675->1|1091->60|1260->161|1327->225|1399->292|1451->340|1475->356|1555->360|1594->372|1651->402|1666->408|1725->446|1794->488|1809->494|1878->542|1947->584|1962->590|2015->622|2047->638|2073->655|2154->659|2193->671|2257->708|2272->714|2329->750|2416->810|2431->816|2499->862|2584->920|2599->926|2665->971|2752->1031|2767->1037|2818->1067|2875->156|2905->222|2933->333|2966->631|2999->1085|3030->1090|3156->1206|3197->1208|3232->1217|3296->1260|3331->1269|3373->1302|3413->1304|3448->1312|3510->1347|3525->1353|3582->1401|3622->1403|3664->1417|3825->1551|3889->1599|3929->1601|3975->1619|4018->1635|4036->1644|4093->1680|4123->1683|4141->1692|4214->1743|4269->1767|4311->1781|4413->1852|4446->1858|5234->2619|5280->2656|5319->2657|5369->2679|5563->2855|5576->2860|5615->2861|5665->2883|5803->2994|5896->3065|5973->3111|6015->3125|6562->3645|6622->3689|6662->3691|6712->3714|6781->3774|6820->3775|6874->3801|6940->3840|6962->3841|7042->3894|7060->3903|7119->3941|7219->4014|7237->4023|7289->4053|7419->4156|7438->4165|7505->4210|7657->4334|7676->4343|7743->4388|7859->4477|7877->4486|7929->4516|8039->4599|8057->4608|8106->4635|8285->4786|8304->4795|8356->4825|8537->4979|8555->4988|8607->5018|8735->5119|8754->5128|8819->5171|8969->5293|8988->5302|9053->5345|9169->5434|9187->5443|9239->5473|9349->5556|9367->5565|9416->5592|9593->5741|9612->5750|9664->5780|9830->5918|9849->5927|9908->5964|9992->6020|10011->6029|10070->6066|10249->6216|10269->6225|10322->6255|10367->6271|10387->6280|10437->6307|10577->6415|10628->6434|10675->6452|10771->6517|10805->6520
                  LINES: 24->1|29->2|32->4|33->6|34->7|36->9|36->9|38->9|40->11|40->11|40->11|40->11|41->12|41->12|41->12|42->13|42->13|42->13|43->15|43->15|45->15|47->17|47->17|47->17|47->17|48->18|48->18|48->18|49->19|49->19|49->19|50->20|50->20|50->20|52->2|54->5|55->8|56->14|57->21|59->23|59->23|59->23|61->25|61->25|63->27|63->27|63->27|65->29|66->30|66->30|66->30|66->30|67->31|69->33|69->33|69->33|70->34|70->34|70->34|70->34|70->34|70->34|70->34|71->35|72->36|74->38|75->39|90->54|90->54|90->54|91->55|93->57|93->57|93->57|94->58|95->59|95->59|96->60|97->61|112->76|112->76|112->76|113->77|113->77|113->77|114->78|115->79|115->79|116->80|116->80|116->80|118->82|118->82|118->82|120->84|120->84|120->84|121->85|121->85|121->85|122->86|122->86|122->86|123->87|123->87|123->87|124->88|124->88|124->88|128->92|128->92|128->92|130->94|130->94|130->94|131->95|131->95|131->95|132->96|132->96|132->96|133->97|133->97|133->97|134->98|134->98|134->98|138->102|138->102|138->102|139->103|139->103|139->103|142->106|142->106|142->106|142->106|142->106|142->106|146->110|147->111|148->112|152->116|153->117
                  -- GENERATED --
              */
          