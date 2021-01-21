
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
Seq[Any](format.raw/*2.1*/("""
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
                  DATE: Wed Jan 20 18:29:25 PST 2021
                  SOURCE: /Users/noel/Desktop/CPE/femr/femr/app/femr/ui/views/admin/trips/manage.scala.html
                  HASH: 23fee3a2a68c34f527d300b3dd6fdf8f987c8d2c
                  MATRIX: 1048->1|1242->125|1289->166|1353->224|1420->281|1444->297|1524->301|1555->306|1611->336|1625->342|1693->390|1756->427|1770->433|1826->469|1853->476|1879->493|1960->497|1992->502|2058->541|2073->547|2140->593|2220->646|2235->652|2290->686|2341->123|2368->279|2396->473|2425->699|2454->702|2579->817|2620->819|2653->826|2685->837|2718->844|2786->896|2825->897|2861->906|2892->910|2910->919|2965->953|3005->963|3038->970|3053->976|3105->1019|3145->1021|3181->1030|3498->1320|3567->1373|3607->1375|3664->1405|3727->1459|3775->1469|3836->1502|3872->1511|3885->1515|3918->1527|3988->1566|4045->1592|4094->1613|4407->1899|4479->1955|4519->1957|4576->1987|4642->2044|4690->2054|4751->2087|4801->2110|4814->2114|4850->2129|4880->2132|4893->2136|4926->2148|4996->2187|5053->2213|5102->2234|5896->2998|5929->3004|6348->3396|6409->3441|6449->3443|6494->3461|6549->3507|6604->3524|6653->3546|6724->3601|6764->3603|6817->3629|6882->3685|6937->3702|6994->3731|7100->3810|7115->3816|7182->3873|7223->3875|7292->3916|7428->4021|7489->4054|7559->4097|7579->4108|7612->4120|7683->4163|7704->4174|7741->4189|7812->4232|7833->4243|7867->4255|7938->4298|7959->4309|8006->4334|8077->4377|8098->4388|8143->4411|8240->4476|8294->4498|8344->4516|8390->4530|8432->4543
                  LINES: 28->1|31->3|32->4|33->5|35->7|35->7|37->7|38->8|38->8|38->8|38->8|39->9|39->9|39->9|40->12|40->12|42->12|43->13|43->13|43->13|43->13|44->14|44->14|44->14|46->2|47->6|48->10|50->15|52->17|52->17|52->17|54->19|54->19|56->21|56->21|56->21|57->22|57->22|57->22|57->22|58->23|60->25|60->25|60->25|60->25|61->26|67->32|67->32|67->32|68->33|68->33|68->33|69->34|69->34|69->34|69->34|70->35|71->36|72->37|79->44|79->44|79->44|80->45|80->45|80->45|81->46|81->46|81->46|81->46|81->46|81->46|81->46|82->47|83->48|84->49|108->73|110->75|124->89|124->89|124->89|125->90|125->90|125->90|126->91|126->91|126->91|127->92|127->92|127->92|128->93|130->95|130->95|130->95|130->95|131->96|132->97|133->98|134->99|134->99|134->99|135->100|135->100|135->100|136->101|136->101|136->101|137->102|137->102|137->102|138->103|138->103|138->103|140->105|141->106|142->107|143->108|144->109
                  -- GENERATED --
              */
          