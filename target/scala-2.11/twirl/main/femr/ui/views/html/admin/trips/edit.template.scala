
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
Seq[Any](format.raw/*1.123*/("""

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
                  DATE: Tue Oct 27 21:59:17 PDT 2020
                  SOURCE: C:/Users/Noel/Documents/csc402/lemur-femr/femr/app/femr/ui/views/admin/trips/edit.scala.html
                  HASH: d44171584168341ea18207eff1cf6c701eb3985e
                  MATRIX: 1046->1|1240->127|1287->169|1354->228|1378->244|1458->248|1492->256|1548->286|1562->292|1620->330|1684->368|1698->374|1754->410|1782->420|1808->437|1889->441|1924->449|1988->486|2003->492|2060->528|2141->582|2156->588|2211->622|2265->122|2295->225|2324->415|2355->636|2386->641|2523->768|2564->770|2599->779|2635->806|2683->816|2720->826|2755->834|2768->838|2795->844|2851->873|2864->877|2897->889|2948->913|2961->917|2994->929|3048->956|3061->960|3097->975|3154->1005|3167->1009|3213->1034|3268->1062|3281->1066|3325->1089|3366->1100|3403->1111|3418->1117|3487->1177|3527->1179|3564->1189|3719->1317|3759->1348|3811->1362|3853->1377|3897->1405|3937->1407|3983->1425|4026->1441|4043->1449|4081->1466|4111->1469|4128->1477|4173->1501|4202->1503|4220->1511|4265->1534|4320->1558|4362->1569|4399->1579|4633->1786|4670->1814|4719->1825|4765->1844|4806->1869|4846->1871|4896->1893|4939->1909|4953->1914|4991->1931|5021->1934|5035->1939|5080->1963|5109->1965|5123->1970|5168->1993|5227->2021|5273->2036|5310->2046|5403->2109|5440->2119|5731->2383|5768->2411|5817->2422|5859->2437|5902->2464|5942->2466|5988->2484|6046->2515|6060->2520|6105->2544|6164->2576|6178->2581|6222->2604|6281->2636|6295->2641|6336->2661|6395->2693|6409->2698|6450->2718|6524->2761|6566->2772|6603->2782|6662->2811
                  LINES: 28->1|31->3|32->4|34->6|34->6|36->6|38->8|38->8|38->8|38->8|39->9|39->9|39->9|40->12|40->12|42->12|44->14|44->14|44->14|44->14|45->15|45->15|45->15|47->1|49->5|50->10|52->16|54->18|54->18|54->18|56->20|56->20|56->20|57->21|57->21|57->21|57->21|58->22|58->22|58->22|59->23|59->23|59->23|60->24|60->24|60->24|61->25|61->25|61->25|62->26|62->26|62->26|63->27|66->30|66->30|66->30|66->30|67->31|69->33|69->33|69->33|70->34|70->34|70->34|71->35|71->35|71->35|71->35|71->35|71->35|71->35|71->35|71->35|71->35|72->36|73->37|74->38|79->43|79->43|79->43|80->44|80->44|80->44|81->45|81->45|81->45|81->45|81->45|81->45|81->45|81->45|81->45|81->45|82->46|83->47|84->48|86->50|89->53|99->63|99->63|99->63|100->64|100->64|100->64|101->65|102->66|102->66|102->66|103->67|103->67|103->67|104->68|104->68|104->68|105->69|105->69|105->69|107->71|108->72|109->73|113->77
                  -- GENERATED --
              */
          