
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
Seq[Any](format.raw/*1.123*/("""

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
                  DATE: Tue Oct 27 21:59:17 PDT 2020
                  SOURCE: C:/Users/Noel/Documents/csc402/lemur-femr/femr/app/femr/ui/views/admin/trips/teams.scala.html
                  HASH: 9a1f50ea16ec2e1e69c8290daba9e8486c93f07b
                  MATRIX: 1047->1|1241->127|1288->169|1352->228|1419->287|1443->303|1523->307|1555->313|1611->343|1625->349|1693->397|1757->435|1771->441|1827->477|1855->487|1881->504|1962->508|1995->514|2061->553|2076->559|2143->605|2224->659|2239->665|2294->699|2348->122|2378->284|2407->482|2438->713|2469->718|2594->833|2635->835|2670->844|2702->855|2737->864|2805->916|2844->917|2881->927|2912->931|2930->940|2985->974|3026->985|3061->994|3076->1000|3127->1042|3167->1044|3206->1056|4020->1840|4055->1848|4379->2145|4450->2200|4490->2202|4536->2221|4599->2275|4647->2285|4699->2309|4761->2344|4774->2348|4807->2360|4870->2396|4883->2400|4923->2419|4986->2455|4999->2459|5036->2475|5118->2526|5164->2541|5206->2555|5283->2602
                  LINES: 28->1|31->3|32->4|33->5|35->7|35->7|37->7|38->8|38->8|38->8|38->8|39->9|39->9|39->9|40->12|40->12|42->12|43->13|43->13|43->13|43->13|44->14|44->14|44->14|46->1|48->6|49->10|51->15|53->17|53->17|53->17|55->19|55->19|57->21|57->21|57->21|58->22|58->22|58->22|58->22|59->23|61->25|61->25|61->25|61->25|63->27|90->54|92->56|103->67|103->67|103->67|104->68|104->68|104->68|106->70|107->71|107->71|107->71|108->72|108->72|108->72|109->73|109->73|109->73|111->75|112->76|113->77|119->83
                  -- GENERATED --
              */
          