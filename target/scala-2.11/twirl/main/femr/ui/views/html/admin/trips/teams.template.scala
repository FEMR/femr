
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

object teams extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template3[femr.common.dtos.CurrentUser,femr.ui.models.admin.trips.TripViewModelGet,AssetsFinder,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(currentUser: femr.common.dtos.CurrentUser, viewModel: femr.ui.models.admin.trips.TripViewModelGet, assets: AssetsFinder):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {
/*3.2*/import femr.ui.views.html.layouts.admin
/*4.2*/import femr.ui.views.html.partials.admin.trips.tripsMenu
/*5.2*/import femr.ui.controllers.admin.routes.TripController

def /*7.2*/additionalStyles/*7.18*/:play.twirl.api.HtmlFormat.Appendable = {_display_(

Seq[Any](format.raw/*7.22*/("""
    """),format.raw/*8.5*/("""<link rel="stylesheet" href=""""),_display_(/*8.35*/assets/*8.41*/.path("css/libraries/jquery.dataTables.min.css")),format.raw/*8.89*/("""">
    <link rel="stylesheet" href=""""),_display_(/*9.35*/assets/*9.41*/.path("css/superuser/superuser.css")),format.raw/*9.77*/("""">
""")))};def /*12.2*/additionalScripts/*12.19*/:play.twirl.api.HtmlFormat.Appendable = {_display_(

Seq[Any](format.raw/*12.23*/("""
    """),format.raw/*13.5*/("""<script type = "text/javascript" src=""""),_display_(/*13.44*/assets/*13.50*/.path("js/libraries/jquery.dataTables.min.js")),format.raw/*13.96*/(""""></script>
    <script type="text/javascript" src=""""),_display_(/*14.42*/assets/*14.48*/.path("js/superuser/superuser.js")),format.raw/*14.82*/(""""></script>
""")))};
Seq[Any](format.raw/*2.1*/("""
"""),format.raw/*6.1*/("""
"""),format.raw/*10.2*/("""

"""),format.raw/*15.2*/("""

"""),_display_(/*17.2*/admin("Trips - Manage Teams", currentUser, styles = additionalStyles, scripts = additionalScripts, assets = assets)/*17.117*/ {_display_(Seq[Any](format.raw/*17.119*/("""

    """),_display_(/*19.6*/tripsMenu()),format.raw/*19.17*/("""

    """),_display_(/*21.6*/for(messageIndex <- 1 to viewModel.getMessages.size) yield /*21.58*/{_display_(Seq[Any](format.raw/*21.59*/("""
        """),format.raw/*22.9*/("""<p>"""),_display_(/*22.13*/viewModel/*22.22*/.getMessages.get(messageIndex - 1)),format.raw/*22.56*/("""</p>
    """)))}),format.raw/*23.6*/("""

    """),_display_(/*25.6*/helper/*25.12*/.form(action = TripController.teamsPost())/*25.54*/ {_display_(Seq[Any](format.raw/*25.56*/("""

        """),format.raw/*27.9*/("""<div id="addTeamWrap">
            <h1>Add Team:</h1>
            <div>
                <label>Team Name<span class="red bold">*</span>:
                    <input class="fInput" type="text" name="newTeamName"/>
                </label>
            </div>

            <div>
                <label>Team Location:
                    <input class="fInput" type="text" name="newTeamLocation"/>
                </label>
            </div>

            <div>
                <label>Team Description:
                    <input class="fInput" type="text" name="newTeamDescription"/>
                </label>
            </div>


        </div>


        <div id="submitWrap">
            <button type="submit" class="fButton">Submit</button>
        </div>
    """)))}),format.raw/*54.6*/("""

    """),format.raw/*56.5*/("""<div id="tableWrap">

        <table id="teamTable">
            <thead>
                <tr>
                    <th>Team Name</th>
                    <th>Description</th>
                    <th>Origin</th>
                </tr>
            </thead>
            <tbody>
            """),_display_(/*67.14*/for(teamIndex <- 1 to viewModel.getMissionItems.size()) yield /*67.69*/ {_display_(Seq[Any](format.raw/*67.71*/("""
                """),_display_(/*68.18*/defining(viewModel.getMissionItems.get(teamIndex - 1))/*68.72*/ { item =>_display_(Seq[Any](format.raw/*68.82*/("""

                    """),format.raw/*70.21*/("""<tr>
                        <td>"""),_display_(/*71.30*/item/*71.34*/.getTeamName),format.raw/*71.46*/("""</td>
                        <td>"""),_display_(/*72.30*/item/*72.34*/.getTeamDescription),format.raw/*72.53*/("""</td>
                        <td>"""),_display_(/*73.30*/item/*73.34*/.getTeamLocation),format.raw/*73.50*/("""</td>
                    </tr>
                """)))}),format.raw/*75.18*/("""
            """)))}),format.raw/*76.14*/("""
            """),format.raw/*77.13*/("""</tbody>
        </table>


    </div>

""")))}),format.raw/*83.2*/("""
"""))
      }
    }
  }

  def render(currentUser:femr.common.dtos.CurrentUser,viewModel:femr.ui.models.admin.trips.TripViewModelGet,assets:AssetsFinder): play.twirl.api.HtmlFormat.Appendable = apply(currentUser,viewModel,assets)

  def f:((femr.common.dtos.CurrentUser,femr.ui.models.admin.trips.TripViewModelGet,AssetsFinder) => play.twirl.api.HtmlFormat.Appendable) = (currentUser,viewModel,assets) => apply(currentUser,viewModel,assets)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  DATE: Wed Jan 20 18:29:25 PST 2021
                  SOURCE: /Users/noel/Desktop/CPE/femr/femr/app/femr/ui/views/admin/trips/teams.scala.html
                  HASH: 437460f52df0d1aaca6c0c58b85e039c402f6021
                  MATRIX: 1047->1|1241->125|1288->166|1352->224|1419->281|1443->297|1523->301|1554->306|1610->336|1624->342|1692->390|1755->427|1769->433|1825->469|1852->476|1878->493|1959->497|1991->502|2057->541|2072->547|2139->593|2219->646|2234->652|2289->686|2340->123|2367->279|2395->473|2424->699|2453->702|2578->817|2619->819|2652->826|2684->837|2717->844|2785->896|2824->897|2860->906|2891->910|2909->919|2964->953|3004->963|3037->970|3052->976|3103->1018|3143->1020|3180->1030|3967->1787|4000->1793|4313->2079|4384->2134|4424->2136|4469->2154|4532->2208|4580->2218|4630->2240|4691->2274|4704->2278|4737->2290|4799->2325|4812->2329|4852->2348|4914->2383|4927->2387|4964->2403|5044->2452|5089->2466|5130->2479|5201->2520
                  LINES: 28->1|31->3|32->4|33->5|35->7|35->7|37->7|38->8|38->8|38->8|38->8|39->9|39->9|39->9|40->12|40->12|42->12|43->13|43->13|43->13|43->13|44->14|44->14|44->14|46->2|47->6|48->10|50->15|52->17|52->17|52->17|54->19|54->19|56->21|56->21|56->21|57->22|57->22|57->22|57->22|58->23|60->25|60->25|60->25|60->25|62->27|89->54|91->56|102->67|102->67|102->67|103->68|103->68|103->68|105->70|106->71|106->71|106->71|107->72|107->72|107->72|108->73|108->73|108->73|110->75|111->76|112->77|118->83
                  -- GENERATED --
              */
          