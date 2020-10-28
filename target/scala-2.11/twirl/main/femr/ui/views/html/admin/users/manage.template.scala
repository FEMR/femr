
package femr.ui.views.html.admin.users

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

object manage extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template3[femr.common.dtos.CurrentUser,femr.ui.models.admin.users.ManageViewModelGet,AssetsFinder,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(currentUser: femr.common.dtos.CurrentUser, viewModelGet: femr.ui.models.admin.users.ManageViewModelGet, assets: AssetsFinder):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {
/*3.2*/import femr.ui.controllers.admin.routes.UsersController
/*4.2*/import femr.ui.views.html.layouts.admin
/*5.2*/import femr.ui.views.html.partials.admin.toggleButton

def /*7.6*/additionalStyles/*7.22*/:play.twirl.api.HtmlFormat.Appendable = {_display_(

Seq[Any](format.raw/*7.26*/("""
        """),format.raw/*8.9*/("""<link rel="stylesheet" href=""""),_display_(/*8.39*/assets/*8.45*/.path("css/admin/users.css")),format.raw/*8.73*/("""">
        <link rel="stylesheet" href=""""),_display_(/*9.39*/assets/*9.45*/.path("css/libraries/jquery.dataTables.min.css")),format.raw/*9.93*/("""">
    """)))};def /*11.6*/additionalScripts/*11.23*/:play.twirl.api.HtmlFormat.Appendable = {_display_(

Seq[Any](format.raw/*11.27*/("""
        """),format.raw/*12.9*/("""<script type="text/javascript" src=""""),_display_(/*12.46*/assets/*12.52*/.path("js/admin/users.js")),format.raw/*12.78*/(""""></script>
        <script type = "text/javascript" src=""""),_display_(/*13.48*/assets/*13.54*/.path("js/libraries/jquery.dataTables.min.js")),format.raw/*13.100*/(""""></script>
    """)))};
Seq[Any](format.raw/*1.128*/("""

"""),format.raw/*6.1*/("""
    """),format.raw/*10.6*/("""
    """),format.raw/*14.6*/("""

"""),_display_(/*16.2*/admin("Users", currentUser, styles = additionalStyles, scripts = additionalScripts, assets = assets)/*16.102*/ {_display_(Seq[Any](format.raw/*16.104*/("""

    """),format.raw/*18.5*/("""<a href=""""),_display_(/*18.15*/UsersController/*18.30*/.createGet()),format.raw/*18.42*/("""" class="fButton fOtherButton fAdminButton userBtns"><span class="glyphicon glyphicon-plus-sign"></span>
        Add User</a>

    <table id="userTable">
        <thead>
            <tr>
                <th>Edit</th>
                <th>First Name</th>
                <th>Last Name</th>
                <th>Email</th>
                <th>About</th>
                <th>Role</th>
                <th>Last Login</th>
                <th>Toggle</th>
            </tr>
        </thead>
        <tbody>
        """),_display_(/*35.10*/for(y <- 1 to viewModelGet.getUsers.size) yield /*35.51*/ {_display_(Seq[Any](format.raw/*35.53*/("""
            """),format.raw/*36.13*/("""<tr>
                <td>
                """),_display_(/*38.18*/helper/*38.24*/.form(action = UsersController.editGet(viewModelGet.getUser(y - 1).getId))/*38.98*/ {_display_(Seq[Any](format.raw/*38.100*/("""
                    """),_display_(/*39.22*/if(viewModelGet.getUser(y - 1).getEmail != "admin")/*39.73*/ {_display_(Seq[Any](format.raw/*39.75*/("""
                        """),format.raw/*40.25*/("""<button type="submit" class="btn btn-default editBtn">"""),_display_(/*40.80*/y),format.raw/*40.81*/("""</button>
                    """)))}),format.raw/*41.22*/("""
                """)))}),format.raw/*42.18*/("""

                """),format.raw/*44.17*/("""</td>
                <td>"""),_display_(/*45.22*/viewModelGet/*45.34*/.getUser(y - 1).getFirstName),format.raw/*45.62*/("""</td>
                <td>"""),_display_(/*46.22*/viewModelGet/*46.34*/.getUser(y - 1).getLastName),format.raw/*46.61*/("""</td>
                <td>"""),_display_(/*47.22*/viewModelGet/*47.34*/.getUser(y - 1).getEmail),format.raw/*47.58*/("""</td>
                <td>"""),_display_(/*48.22*/viewModelGet/*48.34*/.getUser(y - 1).getNotes),format.raw/*48.58*/("""</td>
                <td>
                """),_display_(/*50.18*/for(role <- 0 to viewModelGet.getUser(y - 1).getRoles.size - 1) yield /*50.81*/ {_display_(Seq[Any](format.raw/*50.83*/("""
                    """),format.raw/*51.21*/("""<ul>
                        <li>"""),_display_(/*52.30*/viewModelGet/*52.42*/.getUser(y - 1).getRoles.get(role)),format.raw/*52.76*/("""</li>
                    </ul>
                """)))}),format.raw/*54.18*/("""
                """),format.raw/*55.17*/("""</td>
                <td>"""),_display_(/*56.22*/viewModelGet/*56.34*/.getUser(y - 1).getLastLoginDate),format.raw/*56.66*/("""</td>
                <td class="text-center">
                """),_display_(/*58.18*/defining(viewModelGet.getUser(y - 1))/*58.55*/ { user =>_display_(Seq[Any](format.raw/*58.65*/("""
                    """),_display_(/*59.22*/if(user.getEmail != "admin")/*59.50*/ {_display_(Seq[Any](format.raw/*59.52*/("""
                        """),_display_(/*60.26*/toggleButton(!user.isDeleted, user.getId)),format.raw/*60.67*/("""
                    """)))}),format.raw/*61.22*/("""

                """)))}),format.raw/*63.18*/("""

                """),format.raw/*65.17*/("""</td>
            </tr>
        """)))}),format.raw/*67.10*/("""
        """),format.raw/*68.9*/("""</tbody>
    </table>
    <div id="editDialog" title="Edit User">
        <div id="editPartial">
            """),format.raw/*72.66*/("""
        """),format.raw/*73.9*/("""</div>
    </div>
""")))}))
      }
    }
  }

  def render(currentUser:femr.common.dtos.CurrentUser,viewModelGet:femr.ui.models.admin.users.ManageViewModelGet,assets:AssetsFinder): play.twirl.api.HtmlFormat.Appendable = apply(currentUser,viewModelGet,assets)

  def f:((femr.common.dtos.CurrentUser,femr.ui.models.admin.users.ManageViewModelGet,AssetsFinder) => play.twirl.api.HtmlFormat.Appendable) = (currentUser,viewModelGet,assets) => apply(currentUser,viewModelGet,assets)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  DATE: Tue Oct 27 21:59:18 PDT 2020
                  SOURCE: C:/Users/Noel/Documents/csc402/lemur-femr/femr/app/femr/ui/views/admin/users/manage.scala.html
                  HASH: fea8222041376c910911e8dcb299ace7de52bd5f
                  MATRIX: 1050->1|1249->132|1312->190|1359->232|1425->294|1449->310|1529->314|1565->324|1621->354|1635->360|1683->388|1751->430|1765->436|1833->484|1865->500|1891->517|1972->521|2009->531|2073->568|2088->574|2135->600|2222->660|2237->666|2305->712|2363->127|2393->287|2426->493|2459->730|2490->735|2600->835|2641->837|2676->845|2713->855|2737->870|2770->882|3322->1407|3379->1448|3419->1450|3461->1464|3533->1509|3548->1515|3631->1589|3672->1591|3722->1614|3782->1665|3822->1667|3876->1693|3958->1748|3980->1749|4043->1781|4093->1800|4141->1820|4196->1848|4217->1860|4266->1888|4321->1916|4342->1928|4390->1955|4445->1983|4466->1995|4511->2019|4566->2047|4587->2059|4632->2083|4705->2129|4784->2192|4824->2194|4874->2216|4936->2251|4957->2263|5012->2297|5094->2348|5140->2366|5195->2394|5216->2406|5269->2438|5362->2504|5408->2541|5456->2551|5506->2574|5543->2602|5583->2604|5637->2631|5699->2672|5753->2695|5805->2716|5853->2736|5919->2771|5956->2781|6097->2947|6134->2957
                  LINES: 28->1|31->3|32->4|33->5|35->7|35->7|37->7|38->8|38->8|38->8|38->8|39->9|39->9|39->9|40->11|40->11|42->11|43->12|43->12|43->12|43->12|44->13|44->13|44->13|46->1|48->6|49->10|50->14|52->16|52->16|52->16|54->18|54->18|54->18|54->18|71->35|71->35|71->35|72->36|74->38|74->38|74->38|74->38|75->39|75->39|75->39|76->40|76->40|76->40|77->41|78->42|80->44|81->45|81->45|81->45|82->46|82->46|82->46|83->47|83->47|83->47|84->48|84->48|84->48|86->50|86->50|86->50|87->51|88->52|88->52|88->52|90->54|91->55|92->56|92->56|92->56|94->58|94->58|94->58|95->59|95->59|95->59|96->60|96->60|97->61|99->63|101->65|103->67|104->68|108->72|109->73
                  -- GENERATED --
              */
          