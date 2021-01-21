
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
Seq[Any](format.raw/*2.1*/("""
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
                  DATE: Wed Jan 20 18:29:25 PST 2021
                  SOURCE: /Users/noel/Desktop/CPE/femr/femr/app/femr/ui/views/admin/users/manage.scala.html
                  HASH: 18b1487813a768ce3d675267efbe818c46804bd2
                  MATRIX: 1050->1|1249->130|1312->187|1359->228|1425->288|1449->304|1529->308|1564->317|1620->347|1634->353|1682->381|1749->422|1763->428|1831->476|1862->490|1888->507|1969->511|2005->520|2069->557|2084->563|2131->589|2217->648|2232->654|2300->700|2355->128|2382->282|2414->484|2446->717|2475->720|2585->820|2626->822|2659->828|2696->838|2720->853|2753->865|3288->1373|3345->1414|3385->1416|3426->1429|3496->1472|3511->1478|3594->1552|3635->1554|3684->1576|3744->1627|3784->1629|3837->1654|3919->1709|3941->1710|4003->1741|4052->1759|4098->1777|4152->1804|4173->1816|4222->1844|4276->1871|4297->1883|4345->1910|4399->1937|4420->1949|4465->1973|4519->2000|4540->2012|4585->2036|4656->2080|4735->2143|4775->2145|4824->2166|4885->2200|4906->2212|4961->2246|5041->2295|5086->2312|5140->2339|5161->2351|5214->2383|5305->2447|5351->2484|5399->2494|5448->2516|5485->2544|5525->2546|5578->2572|5640->2613|5693->2635|5743->2654|5789->2672|5853->2705|5889->2714|6026->2876|6062->2885
                  LINES: 28->1|31->3|32->4|33->5|35->7|35->7|37->7|38->8|38->8|38->8|38->8|39->9|39->9|39->9|40->11|40->11|42->11|43->12|43->12|43->12|43->12|44->13|44->13|44->13|46->2|47->6|48->10|49->14|51->16|51->16|51->16|53->18|53->18|53->18|53->18|70->35|70->35|70->35|71->36|73->38|73->38|73->38|73->38|74->39|74->39|74->39|75->40|75->40|75->40|76->41|77->42|79->44|80->45|80->45|80->45|81->46|81->46|81->46|82->47|82->47|82->47|83->48|83->48|83->48|85->50|85->50|85->50|86->51|87->52|87->52|87->52|89->54|90->55|91->56|91->56|91->56|93->58|93->58|93->58|94->59|94->59|94->59|95->60|95->60|96->61|98->63|100->65|102->67|103->68|107->72|108->73
                  -- GENERATED --
              */
          