
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

object cities extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template3[femr.common.dtos.CurrentUser,femr.ui.models.admin.trips.TripViewModelGet,AssetsFinder,play.twirl.api.HtmlFormat.Appendable] {

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

"""),_display_(/*17.2*/admin("Trips - Manage Cities", currentUser, scripts = additionalScripts, styles = additionalStyles, assets = assets)/*17.118*/ {_display_(Seq[Any](format.raw/*17.120*/("""

    """),_display_(/*19.6*/tripsMenu()),format.raw/*19.17*/("""

    """),_display_(/*21.6*/for(messageIndex <- 1 to viewModel.getMessages.size) yield /*21.58*/{_display_(Seq[Any](format.raw/*21.59*/("""
        """),format.raw/*22.9*/("""<p>"""),_display_(/*22.13*/viewModel/*22.22*/.getMessages.get(messageIndex - 1)),format.raw/*22.56*/("""</p>
    """)))}),format.raw/*23.6*/("""

    """),_display_(/*25.6*/helper/*25.12*/.form(action = TripController.citiesPost())/*25.55*/ {_display_(Seq[Any](format.raw/*25.57*/("""
        """),format.raw/*26.9*/("""<div id="addCityWrap">
            <h1>Add City:</h1>
            <div>
                <label>City:<span class="red bold">*</span>
                    <input class="fInput" type="text" name="newCity"/>
                </label>
            </div>

            <div>
                <label>Country:<span class="red bold">*</span>
                    <select class="fSelect" name="newCityCountry">
                        <option selected disabled value=""></option>
                        """),_display_(/*38.26*/for(countryIndex <- 1 to viewModel.getAvailableCountries.size) yield /*38.88*/ {_display_(Seq[Any](format.raw/*38.90*/("""
                            """),format.raw/*39.29*/("""<option>"""),_display_(/*39.38*/viewModel/*39.47*/.getAvailableCountries.get(countryIndex - 1)),format.raw/*39.91*/("""</option>
                        """)))}),format.raw/*40.26*/("""
                    """),format.raw/*41.21*/("""</select>
                </label>
            </div>
        </div>
        <div id="submitWrap">
            <button type="submit" class="fButton">Submit</button>
        </div>
    """)))}),format.raw/*48.6*/("""

    """),format.raw/*50.5*/("""<div id="tableWrap">

        <table id="cityTable">
            <thead>
                <tr>
                    <th>City</th>
                    <th>Country</th>
                </tr>
            </thead>
            <tbody>
            """),_display_(/*60.14*/for(i <- 1 to viewModel.getAvailableCities.size) yield /*60.62*/ {_display_(Seq[Any](format.raw/*60.64*/("""
                """),_display_(/*61.18*/defining(viewModel.getAvailableCities.get(i - 1))/*61.67*/ { city =>_display_(Seq[Any](format.raw/*61.77*/("""

                    """),format.raw/*63.21*/("""<tr>
                        <td>"""),_display_(/*64.30*/city/*64.34*/.getCityName),format.raw/*64.46*/("""</td>
                        <td>"""),_display_(/*65.30*/city/*65.34*/.getCountryName),format.raw/*65.49*/("""</td>
                    </tr>
                """)))}),format.raw/*67.18*/("""
            """)))}),format.raw/*68.14*/("""
            """),format.raw/*69.13*/("""</tbody>
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
                  SOURCE: /Users/noel/Desktop/CPE/femr/femr/app/femr/ui/views/admin/trips/cities.scala.html
                  HASH: 825eae7eee4e7f72edda646128686c32cbdf4916
                  MATRIX: 1048->1|1242->125|1289->166|1353->224|1420->281|1444->297|1524->301|1555->306|1611->336|1625->342|1693->390|1756->427|1770->433|1826->469|1853->476|1879->493|1960->497|1992->502|2058->541|2073->547|2140->593|2220->646|2235->652|2290->686|2341->123|2368->279|2396->473|2425->699|2454->702|2580->818|2621->820|2654->827|2686->838|2719->845|2787->897|2826->898|2862->907|2893->911|2911->920|2966->954|3006->964|3039->971|3054->977|3106->1020|3146->1022|3182->1031|3699->1521|3777->1583|3817->1585|3874->1614|3910->1623|3928->1632|3993->1676|4059->1711|4108->1732|4323->1917|4356->1923|4624->2164|4688->2212|4728->2214|4773->2232|4831->2281|4879->2291|4929->2313|4990->2347|5003->2351|5036->2363|5098->2398|5111->2402|5147->2417|5227->2466|5272->2480|5313->2493
                  LINES: 28->1|31->3|32->4|33->5|35->7|35->7|37->7|38->8|38->8|38->8|38->8|39->9|39->9|39->9|40->12|40->12|42->12|43->13|43->13|43->13|43->13|44->14|44->14|44->14|46->2|47->6|48->10|50->15|52->17|52->17|52->17|54->19|54->19|56->21|56->21|56->21|57->22|57->22|57->22|57->22|58->23|60->25|60->25|60->25|60->25|61->26|73->38|73->38|73->38|74->39|74->39|74->39|74->39|75->40|76->41|83->48|85->50|95->60|95->60|95->60|96->61|96->61|96->61|98->63|99->64|99->64|99->64|100->65|100->65|100->65|102->67|103->68|104->69
                  -- GENERATED --
              */
          