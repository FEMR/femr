
package femr.ui.views.html.admin.trips

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

object edit extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template3[femr.common.dtos.CurrentUser,femr.ui.models.admin.trips.EditViewModelGet,AssetsFinder,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(currentUser: femr.common.dtos.CurrentUser, viewModel: femr.ui.models.admin.trips.EditViewModelGet, assets: AssetsFinder):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {
/*3.2*/import femr.ui.views.html.layouts.admin
/*4.2*/import femr.ui.controllers.admin.routes.TripController

def /*6.2*/additionalStyles/*6.18*/:play.twirl.api.HtmlFormat.Appendable = {_display_(

Seq[Any](format.raw/*6.22*/("""

    """),format.raw/*8.5*/("""<link rel="stylesheet" href=""""),_display_(/*8.35*/assets/*8.41*/.path("css/libraries/select2.min.css")),format.raw/*8.79*/("""">
    <link rel="stylesheet" href=""""),_display_(/*9.35*/assets/*9.41*/.path("css/superuser/superuser.css")),format.raw/*9.77*/("""">
""")))};def /*12.2*/additionalScripts/*12.19*/:play.twirl.api.HtmlFormat.Appendable = {_display_(

Seq[Any](format.raw/*12.23*/("""

    """),format.raw/*14.5*/("""<script type="text/javascript" src=""""),_display_(/*14.42*/assets/*14.48*/.path("js/libraries/select2.min.js")),format.raw/*14.84*/(""""></script>
    <script type="text/javascript" src=""""),_display_(/*15.42*/assets/*15.48*/.path("js/superuser/superuser.js")),format.raw/*15.82*/(""""></script>
""")))};
Seq[Any](format.raw/*2.1*/("""
"""),format.raw/*5.1*/("""
"""),format.raw/*10.2*/("""

"""),format.raw/*16.2*/("""

"""),_display_(/*18.2*/admin("Trips - Manage Trips - Edit Trip", currentUser, styles = additionalStyles, scripts = additionalScripts, assets = assets)/*18.129*/ {_display_(Seq[Any](format.raw/*18.131*/("""

    """),_display_(/*20.6*/defining(viewModel.getTrip)/*20.33*/ { trip =>_display_(Seq[Any](format.raw/*20.43*/("""
        """),format.raw/*21.9*/("""<p>Id: """),_display_(/*21.17*/trip/*21.21*/.getId),format.raw/*21.27*/("""</p>
        <p>Team name: """),_display_(/*22.24*/trip/*22.28*/.getTeamName),format.raw/*22.40*/("""</p>
        <p>City: """),_display_(/*23.19*/trip/*23.23*/.getTripCity),format.raw/*23.35*/("""</p>
        <p>Country: """),_display_(/*24.22*/trip/*24.26*/.getTripCountry),format.raw/*24.41*/("""</p>
        <p>Start Date: """),_display_(/*25.25*/trip/*25.29*/.getFriendlyTripStartDate),format.raw/*25.54*/("""</p>
        <p>End Date: """),_display_(/*26.23*/trip/*26.27*/.getFriendlyTripEndDate),format.raw/*26.50*/("""</p>
    """)))}),format.raw/*27.6*/("""


    """),_display_(/*30.6*/helper/*30.12*/.form(action = TripController.editPost(viewModel.getTripId))/*30.72*/ {_display_(Seq[Any](format.raw/*30.74*/("""
        """),format.raw/*31.9*/("""<h4>Add users to this trip: </h4>
        <select id="addUsersSelect2" multiple="multiple" name="newUsersForTrip[]">
        """),_display_(/*33.10*/defining(viewModel.getAllUsers)/*33.41*/ { allUsers =>_display_(Seq[Any](format.raw/*33.55*/("""
            """),_display_(/*34.14*/for(i <- 1 to allUsers.size) yield /*34.42*/ {_display_(Seq[Any](format.raw/*34.44*/("""
                """),format.raw/*35.17*/("""<option value=""""),_display_(/*35.33*/allUsers/*35.41*/.get(i - 1).getId),format.raw/*35.58*/("""">"""),_display_(/*35.61*/allUsers/*35.69*/.get(i - 1).getFirstName),format.raw/*35.93*/(""" """),_display_(/*35.95*/allUsers/*35.103*/.get(i - 1).getLastName),format.raw/*35.126*/("""</option>
            """)))}),format.raw/*36.14*/("""
        """)))}),format.raw/*37.10*/("""
        """),format.raw/*38.9*/("""</select>
        <button type="submit">Add</button>

        <h4>Remove users from this trip:</h4>
        <select id="removeUsersSelect2" multiple="multiple" name="removeUsersForTrip[]">
            """),_display_(/*43.14*/defining(viewModel.getUsers)/*43.42*/ { users =>_display_(Seq[Any](format.raw/*43.53*/("""
                """),_display_(/*44.18*/for(i <- 1 to users.size) yield /*44.43*/ {_display_(Seq[Any](format.raw/*44.45*/("""
                    """),format.raw/*45.21*/("""<option value=""""),_display_(/*45.37*/users/*45.42*/.get(i - 1).getId),format.raw/*45.59*/("""">"""),_display_(/*45.62*/users/*45.67*/.get(i - 1).getFirstName),format.raw/*45.91*/(""" """),_display_(/*45.93*/users/*45.98*/.get(i - 1).getLastName),format.raw/*45.121*/("""</option>
                """)))}),format.raw/*46.18*/("""
            """)))}),format.raw/*47.14*/("""
        """),format.raw/*48.9*/("""</select>
        <button type="submit">Remove</button>
    """)))}),format.raw/*50.6*/("""


    """),format.raw/*53.5*/("""<table id="usersTripTable">
        <thead>
            <tr>
                <th>First Name</th>
                <th>Last Name</th>
                <th>Email</th>
                <th>About</th>
            </tr>
        </thead>
        <tbody>
        """),_display_(/*63.10*/defining(viewModel.getUsers)/*63.38*/ { users =>_display_(Seq[Any](format.raw/*63.49*/("""
            """),_display_(/*64.14*/for(i <- 1 to users.size()) yield /*64.41*/ {_display_(Seq[Any](format.raw/*64.43*/("""
                """),format.raw/*65.17*/("""<tr>
                    <td>"""),_display_(/*66.26*/users/*66.31*/.get(i - 1).getFirstName),format.raw/*66.55*/("""</td>
                    <td>"""),_display_(/*67.26*/users/*67.31*/.get(i - 1).getLastName),format.raw/*67.54*/("""</td>
                    <td>"""),_display_(/*68.26*/users/*68.31*/.get(i - 1).getEmail),format.raw/*68.51*/("""</td>
                    <td>"""),_display_(/*69.26*/users/*69.31*/.get(i - 1).getNotes),format.raw/*69.51*/("""</td>
                </tr>
            """)))}),format.raw/*71.14*/("""
        """)))}),format.raw/*72.10*/("""
        """),format.raw/*73.9*/("""</tbody>
    </table>


""")))}),format.raw/*77.2*/("""

"""))
      }
    }
  }

  def render(currentUser:femr.common.dtos.CurrentUser,viewModel:femr.ui.models.admin.trips.EditViewModelGet,assets:AssetsFinder): play.twirl.api.HtmlFormat.Appendable = apply(currentUser,viewModel,assets)

  def f:((femr.common.dtos.CurrentUser,femr.ui.models.admin.trips.EditViewModelGet,AssetsFinder) => play.twirl.api.HtmlFormat.Appendable) = (currentUser,viewModel,assets) => apply(currentUser,viewModel,assets)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  DATE: Wed Jan 20 18:29:25 PST 2021
                  SOURCE: /Users/noel/Desktop/CPE/femr/femr/app/femr/ui/views/admin/trips/edit.scala.html
                  HASH: f5ffd97b643ead14bda717d320af23e297d2a0fc
                  MATRIX: 1046->1|1240->125|1287->166|1354->223|1378->239|1458->243|1490->249|1546->279|1560->285|1618->323|1681->360|1695->366|1751->402|1778->409|1804->426|1885->430|1918->436|1982->473|1997->479|2054->515|2134->568|2149->574|2204->608|2255->123|2282->221|2310->406|2339->621|2368->624|2505->751|2546->753|2579->760|2615->787|2663->797|2699->806|2734->814|2747->818|2774->824|2829->852|2842->856|2875->868|2925->891|2938->895|2971->907|3024->933|3037->937|3073->952|3129->981|3142->985|3188->1010|3242->1037|3255->1041|3299->1064|3339->1074|3373->1082|3388->1088|3457->1148|3497->1150|3533->1159|3686->1285|3726->1316|3778->1330|3819->1344|3863->1372|3903->1374|3948->1391|3991->1407|4008->1415|4046->1432|4076->1435|4093->1443|4138->1467|4167->1469|4185->1477|4230->1500|4284->1523|4325->1533|4361->1542|4590->1744|4627->1772|4676->1783|4721->1801|4762->1826|4802->1828|4851->1849|4894->1865|4908->1870|4946->1887|4976->1890|4990->1895|5035->1919|5064->1921|5078->1926|5123->1949|5181->1976|5226->1990|5262->1999|5353->2060|5387->2067|5668->2321|5705->2349|5754->2360|5795->2374|5838->2401|5878->2403|5923->2420|5980->2450|5994->2455|6039->2479|6097->2510|6111->2515|6155->2538|6213->2569|6227->2574|6268->2594|6326->2625|6340->2630|6381->2650|6453->2691|6494->2701|6530->2710|6585->2735
                  LINES: 28->1|31->3|32->4|34->6|34->6|36->6|38->8|38->8|38->8|38->8|39->9|39->9|39->9|40->12|40->12|42->12|44->14|44->14|44->14|44->14|45->15|45->15|45->15|47->2|48->5|49->10|51->16|53->18|53->18|53->18|55->20|55->20|55->20|56->21|56->21|56->21|56->21|57->22|57->22|57->22|58->23|58->23|58->23|59->24|59->24|59->24|60->25|60->25|60->25|61->26|61->26|61->26|62->27|65->30|65->30|65->30|65->30|66->31|68->33|68->33|68->33|69->34|69->34|69->34|70->35|70->35|70->35|70->35|70->35|70->35|70->35|70->35|70->35|70->35|71->36|72->37|73->38|78->43|78->43|78->43|79->44|79->44|79->44|80->45|80->45|80->45|80->45|80->45|80->45|80->45|80->45|80->45|80->45|81->46|82->47|83->48|85->50|88->53|98->63|98->63|98->63|99->64|99->64|99->64|100->65|101->66|101->66|101->66|102->67|102->67|102->67|103->68|103->68|103->68|104->69|104->69|104->69|106->71|107->72|108->73|112->77
                  -- GENERATED --
              */
          