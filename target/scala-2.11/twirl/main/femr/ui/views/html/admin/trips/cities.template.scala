
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
Seq[Any](format.raw/*1.123*/("""

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
                  DATE: Tue Oct 27 21:59:17 PDT 2020
                  SOURCE: C:/Users/Noel/Documents/csc402/lemur-femr/femr/app/femr/ui/views/admin/trips/cities.scala.html
                  HASH: 9e67b7dff780637aa51a2b06250222d674cd3fae
                  MATRIX: 1048->1|1242->127|1289->169|1353->228|1420->287|1444->303|1524->307|1556->313|1612->343|1626->349|1694->397|1758->435|1772->441|1828->477|1856->487|1882->504|1963->508|1996->514|2062->553|2077->559|2144->605|2225->659|2240->665|2295->699|2349->122|2379->284|2408->482|2439->713|2470->718|2596->834|2637->836|2672->845|2704->856|2739->865|2807->917|2846->918|2883->928|2914->932|2932->941|2987->975|3028->986|3063->995|3078->1001|3130->1044|3170->1046|3207->1056|3736->1558|3814->1620|3854->1622|3912->1652|3948->1661|3966->1670|4031->1714|4098->1750|4148->1772|4370->1964|4405->1972|4683->2223|4747->2271|4787->2273|4833->2292|4891->2341|4939->2351|4991->2375|5053->2410|5066->2414|5099->2426|5162->2462|5175->2466|5211->2481|5293->2532|5339->2547|5381->2561
                  LINES: 28->1|31->3|32->4|33->5|35->7|35->7|37->7|38->8|38->8|38->8|38->8|39->9|39->9|39->9|40->12|40->12|42->12|43->13|43->13|43->13|43->13|44->14|44->14|44->14|46->1|48->6|49->10|51->15|53->17|53->17|53->17|55->19|55->19|57->21|57->21|57->21|58->22|58->22|58->22|58->22|59->23|61->25|61->25|61->25|61->25|62->26|74->38|74->38|74->38|75->39|75->39|75->39|75->39|76->40|77->41|84->48|86->50|96->60|96->60|96->60|97->61|97->61|97->61|99->63|100->64|100->64|100->64|101->65|101->65|101->65|103->67|104->68|105->69
                  -- GENERATED --
              */
          