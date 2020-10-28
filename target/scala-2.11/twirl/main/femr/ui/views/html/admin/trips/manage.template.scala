
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

object manage extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template3[femr.common.dtos.CurrentUser,femr.ui.models.admin.trips.TripViewModelGet,AssetsFinder,play.twirl.api.HtmlFormat.Appendable] {

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

"""),_display_(/*17.2*/admin("Trips - Manage Trips", currentUser, styles = additionalStyles, scripts = additionalScripts, assets = assets)/*17.117*/ {_display_(Seq[Any](format.raw/*17.119*/("""

    """),_display_(/*19.6*/tripsMenu()),format.raw/*19.17*/("""

    """),_display_(/*21.6*/for(messageIndex <- 1 to viewModel.getMessages.size) yield /*21.58*/{_display_(Seq[Any](format.raw/*21.59*/("""
        """),format.raw/*22.9*/("""<p>"""),_display_(/*22.13*/viewModel/*22.22*/.getMessages.get(messageIndex - 1)),format.raw/*22.56*/("""</p>
    """)))}),format.raw/*23.6*/("""

    """),_display_(/*25.6*/helper/*25.12*/.form(action = TripController.managePost())/*25.55*/ {_display_(Seq[Any](format.raw/*25.57*/("""
        """),format.raw/*26.9*/("""<div id="addTripWrap">
            <h1>Add Trip:</h1>
            <div>
                <label>Trip Team:<span class="red bold">*</span>
                    <select class="fSelect" name="newTripTeamName">
                        <option selected value=""></option>
                        """),_display_(/*32.26*/for(teamIndex <- 1 to viewModel.getMissionItems.size) yield /*32.79*/ {_display_(Seq[Any](format.raw/*32.81*/("""
                            """),_display_(/*33.30*/defining(viewModel.getMissionItems.get(teamIndex - 1))/*33.84*/ { item =>_display_(Seq[Any](format.raw/*33.94*/("""
                                """),format.raw/*34.33*/("""<option>"""),_display_(/*34.42*/item/*34.46*/.getTeamName),format.raw/*34.58*/("""</option>
                            """)))}),format.raw/*35.30*/("""
                        """)))}),format.raw/*36.26*/("""
                    """),format.raw/*37.21*/("""</select>
                </label>
            </div>
            <div>
                <label>Trip City:<span class="red bold">*</span>
                    <select class="fSelect" name="newTripCity">
                        <option selected value=""></option>
                        """),_display_(/*44.26*/for(tripIndex <- 1 to viewModel.getAvailableCities.size) yield /*44.82*/ {_display_(Seq[Any](format.raw/*44.84*/("""
                            """),_display_(/*45.30*/defining(viewModel.getAvailableCities.get(tripIndex - 1))/*45.87*/ { city =>_display_(Seq[Any](format.raw/*45.97*/("""
                                """),format.raw/*46.33*/("""<option country-name=""""),_display_(/*46.56*/city/*46.60*/.getCountryName),format.raw/*46.75*/("""">"""),_display_(/*46.78*/city/*46.82*/.getCityName),format.raw/*46.94*/("""</option>
                            """)))}),format.raw/*47.30*/("""
                        """)))}),format.raw/*48.26*/("""
                    """),format.raw/*49.21*/("""</select>
                </label>
            </div>
            <div>
                <label>Trip Country:
                    <input class="fInput" type="text" value="" name="newTripCountry" readonly/>
                </label>
            </div>

            <div>
                <label>Start Date:<span class="red bold">*</span>
                    <input type="date" name="newTripStartDate"/>
                </label>
            </div>
            <div>
                <label>End Date:<span class="red bold">*</span>
                    <input type="date" name="newTripEndDate"/>
                </label>
            </div>
        </div>

        <div id="submitWrap">
            <button type="submit" class="fButton">Submit</button>
        </div>
    """)))}),format.raw/*73.6*/("""

    """),format.raw/*75.5*/("""<div id="tableWrap">

        <table id="tripTable">
            <thead>
                <tr>
                    <th>Edit</th>
                    <th>Team Name</th>
                    <th>Country</th>
                    <th>City</th>
                    <th>Start Date</th>
                    <th>End Date</th>
                </tr>
            </thead>
            <tbody>
            """),_display_(/*89.14*/for(i <- 1 to viewModel.getMissionItems.size) yield /*89.59*/ {_display_(Seq[Any](format.raw/*89.61*/("""
                """),_display_(/*90.18*/defining(viewModel.getMissionItems.get(i - 1))/*90.64*/ { missionItem =>_display_(Seq[Any](format.raw/*90.81*/("""
                    """),_display_(/*91.22*/for(tripIndex <- 1 to missionItem.getMissionTrips.size) yield /*91.77*/ {_display_(Seq[Any](format.raw/*91.79*/("""
                        """),_display_(/*92.26*/defining(missionItem.getMissionTrips.get(tripIndex - 1))/*92.82*/ { missionTrip =>_display_(Seq[Any](format.raw/*92.99*/("""
                            """),format.raw/*93.29*/("""<tr>
                                <td>
                                    """),_display_(/*95.38*/helper/*95.44*/.form(action = TripController.editGet(missionTrip.getId))/*95.101*/ {_display_(Seq[Any](format.raw/*95.103*/("""
                                        """),format.raw/*96.41*/("""<button type="submit" class="btn btn-default editBtn">Edit</button>
                                    """)))}),format.raw/*97.38*/("""
                                """),format.raw/*98.33*/("""</td>
                                <td>"""),_display_(/*99.38*/missionItem/*99.49*/.getTeamName),format.raw/*99.61*/("""</td>
                                <td>"""),_display_(/*100.38*/missionTrip/*100.49*/.getTripCountry),format.raw/*100.64*/("""</td>
                                <td>"""),_display_(/*101.38*/missionTrip/*101.49*/.getTripCity),format.raw/*101.61*/("""</td>
                                <td>"""),_display_(/*102.38*/missionTrip/*102.49*/.getFriendlyTripStartDate),format.raw/*102.74*/("""</td>
                                <td>"""),_display_(/*103.38*/missionTrip/*103.49*/.getFriendlyTripEndDate),format.raw/*103.72*/("""</td>
                            </tr>
                        """)))}),format.raw/*105.26*/("""
                    """)))}),format.raw/*106.22*/("""
                """)))}),format.raw/*107.18*/("""
            """)))}),format.raw/*108.14*/("""
            """),format.raw/*109.13*/("""</tbody>
        </table>


    </div>

""")))}))
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
                  SOURCE: C:/Users/Noel/Documents/csc402/lemur-femr/femr/app/femr/ui/views/admin/trips/manage.scala.html
                  HASH: b935a0652939267291700a346e52fb842b515260
                  MATRIX: 1048->1|1242->127|1289->169|1353->228|1420->287|1444->303|1524->307|1556->313|1612->343|1626->349|1694->397|1758->435|1772->441|1828->477|1856->487|1882->504|1963->508|1996->514|2062->553|2077->559|2144->605|2225->659|2240->665|2295->699|2349->122|2379->284|2408->482|2439->713|2470->718|2595->833|2636->835|2671->844|2703->855|2738->864|2806->916|2845->917|2882->927|2913->931|2931->940|2986->974|3027->985|3062->994|3077->1000|3129->1043|3169->1045|3206->1055|3529->1351|3598->1404|3638->1406|3696->1437|3759->1491|3807->1501|3869->1535|3905->1544|3918->1548|3951->1560|4022->1600|4080->1627|4130->1649|4450->1942|4522->1998|4562->2000|4620->2031|4686->2088|4734->2098|4796->2132|4846->2155|4859->2159|4895->2174|4925->2177|4938->2181|4971->2193|5042->2233|5100->2260|5150->2282|5968->3070|6003->3078|6436->3484|6497->3529|6537->3531|6583->3550|6638->3596|6693->3613|6743->3636|6814->3691|6854->3693|6908->3720|6973->3776|7028->3793|7086->3823|7194->3904|7209->3910|7276->3967|7317->3969|7387->4011|7524->4117|7586->4151|7657->4195|7677->4206|7710->4218|7782->4262|7803->4273|7840->4288|7912->4332|7933->4343|7967->4355|8039->4399|8060->4410|8107->4435|8179->4479|8200->4490|8245->4513|8344->4580|8399->4603|8450->4622|8497->4637|8540->4651
                  LINES: 28->1|31->3|32->4|33->5|35->7|35->7|37->7|38->8|38->8|38->8|38->8|39->9|39->9|39->9|40->12|40->12|42->12|43->13|43->13|43->13|43->13|44->14|44->14|44->14|46->1|48->6|49->10|51->15|53->17|53->17|53->17|55->19|55->19|57->21|57->21|57->21|58->22|58->22|58->22|58->22|59->23|61->25|61->25|61->25|61->25|62->26|68->32|68->32|68->32|69->33|69->33|69->33|70->34|70->34|70->34|70->34|71->35|72->36|73->37|80->44|80->44|80->44|81->45|81->45|81->45|82->46|82->46|82->46|82->46|82->46|82->46|82->46|83->47|84->48|85->49|109->73|111->75|125->89|125->89|125->89|126->90|126->90|126->90|127->91|127->91|127->91|128->92|128->92|128->92|129->93|131->95|131->95|131->95|131->95|132->96|133->97|134->98|135->99|135->99|135->99|136->100|136->100|136->100|137->101|137->101|137->101|138->102|138->102|138->102|139->103|139->103|139->103|141->105|142->106|143->107|144->108|145->109
                  -- GENERATED --
              */
          