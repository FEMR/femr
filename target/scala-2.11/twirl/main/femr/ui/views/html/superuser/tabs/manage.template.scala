
package femr.ui.views.html.superuser.tabs

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

object manage extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template3[femr.common.dtos.CurrentUser,femr.ui.models.superuser.TabsViewModelGet,AssetsFinder,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(currentUser: femr.common.dtos.CurrentUser, viewModel: femr.ui.models.superuser.TabsViewModelGet, assets: AssetsFinder):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {
/*3.2*/import femr.ui.views.html.layouts.admin
/*4.2*/import femr.ui.controllers.superuser.routes.TabController

def /*6.2*/additionalStyles/*6.18*/:play.twirl.api.HtmlFormat.Appendable = {_display_(

Seq[Any](format.raw/*6.22*/("""
    """),format.raw/*7.5*/("""<link rel="stylesheet" href=""""),_display_(/*7.35*/assets/*7.41*/.path("css/superuser/superuser.css")),format.raw/*7.77*/("""">
""")))};
Seq[Any](format.raw/*2.1*/("""
"""),format.raw/*5.1*/("""
"""),format.raw/*8.2*/("""

"""),_display_(/*10.2*/admin("Tabs", currentUser, styles = additionalStyles, assets = assets)/*10.72*/ {_display_(Seq[Any](format.raw/*10.74*/("""
    """),format.raw/*11.5*/("""<h1>Tabs</h1>
    <p>Do not put spaces in Tab Names.</p>
    <div id="superUserWrap">
        <div class="halfPageTables">
            <h3>Current Custom Medical Tabs:</h3>
            <table>
                <thead>
                    <tr>
                        <th>Name</th>
                        <th>Left Size</th>
                        <th>Right Size</th>
                    </tr>
                </thead>
                <tbody>
                """),_display_(/*25.18*/for(x <- 1 to viewModel.getCurrentTabs.size) yield /*25.62*/ {_display_(Seq[Any](format.raw/*25.64*/("""
                    """),format.raw/*26.21*/("""<tr>

                    """),_display_(/*28.22*/defining(viewModel.getCurrentTabs.get(x - 1))/*28.67*/ { currentTab =>_display_(Seq[Any](format.raw/*28.83*/("""
                        """),format.raw/*29.25*/("""<td><a href="/superuser/tabs/"""),_display_(/*29.55*/currentTab/*29.65*/.getName),format.raw/*29.73*/("""">"""),_display_(/*29.76*/currentTab/*29.86*/.getName),format.raw/*29.94*/("""</a></td>
                        <td>"""),_display_(/*30.30*/currentTab/*30.40*/.getLeftColumnSize),format.raw/*30.58*/("""</td>
                        <td>"""),_display_(/*31.30*/currentTab/*31.40*/.getRightColumnSize),format.raw/*31.59*/("""</td>
                    """)))}),format.raw/*32.22*/("""
                    """),format.raw/*33.21*/("""</tr>
                """)))}),format.raw/*34.18*/("""

                """),format.raw/*36.17*/("""</tbody>
            </table>
        </div>
        <div class="halfPageTables">
            <h3>Deleted Custom Medical Tabs:</h3>
            <table>
                <thead>
                    <tr>
                        <th>Name</th>
                        <th>Left Size</th>
                        <th>Right Size</th>
                    </tr>
                </thead>
                <tbody>

                """),_display_(/*51.18*/for(x <- 1 to viewModel.getDeletedTabs.size) yield /*51.62*/ {_display_(Seq[Any](format.raw/*51.64*/("""
                    """),format.raw/*52.21*/("""<tr>

                    """),_display_(/*54.22*/defining(viewModel.getDeletedTabs.get(x - 1))/*54.67*/ { deletedTab =>_display_(Seq[Any](format.raw/*54.83*/("""
                        """),format.raw/*55.25*/("""<td><a href="/superuser/tabs/"""),_display_(/*55.55*/deletedTab/*55.65*/.getName),format.raw/*55.73*/("""">"""),_display_(/*55.76*/deletedTab/*55.86*/.getName),format.raw/*55.94*/("""</a></td>
                        <td>"""),_display_(/*56.30*/deletedTab/*56.40*/.getLeftColumnSize),format.raw/*56.58*/("""</td>
                        <td>"""),_display_(/*57.30*/deletedTab/*57.40*/.getRightColumnSize),format.raw/*57.59*/("""</td>

                    """)))}),format.raw/*59.22*/("""

                    """),format.raw/*61.21*/("""</tr>
                """)))}),format.raw/*62.18*/("""


                """),format.raw/*65.17*/("""</tbody>
            </table>
        </div>

    </div>

    <div id="bottomFields">
    """),_display_(/*72.6*/helper/*72.12*/.form(action = TabController.managePost())/*72.54*/ {_display_(Seq[Any](format.raw/*72.56*/("""
        """),format.raw/*73.9*/("""<div class="halfPageForms">
            <h4>Add Tab:</h4>
            <label for="addTabName">Name</label>
            <input type="text" class="fInput" name="addTabName"/>
            <br/>
            <label for="addTabLeft">Left Tab</label>
            <input type="number" class="fInput" name="addTabLeft"/>
            <br/>
            <label for="addTabRight">Right Tab</label>
            <input type="number" class="fInput" name="addTabRight"/>
        </div>
        <div class="halfPageForms">
            <h4>Toggle Tab:</h4>
            <input type="text" class="fInput" name="deleteTab"/>
            <br/>

        </div>

    </div>
    <button type="submit" class="fButton" id="contentSubmit">Submit</button>


""")))}),format.raw/*95.2*/("""

""")))}),format.raw/*97.2*/("""
"""))
      }
    }
  }

  def render(currentUser:femr.common.dtos.CurrentUser,viewModel:femr.ui.models.superuser.TabsViewModelGet,assets:AssetsFinder): play.twirl.api.HtmlFormat.Appendable = apply(currentUser,viewModel,assets)

  def f:((femr.common.dtos.CurrentUser,femr.ui.models.superuser.TabsViewModelGet,AssetsFinder) => play.twirl.api.HtmlFormat.Appendable) = (currentUser,viewModel,assets) => apply(currentUser,viewModel,assets)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  DATE: Wed Jan 20 18:29:26 PST 2021
                  SOURCE: /Users/noel/Desktop/CPE/femr/femr/app/femr/ui/views/superuser/tabs/manage.scala.html
                  HASH: 7565d9094450117ca3e55f33ce6b18a1a236e527
                  MATRIX: 1049->1|1241->123|1288->164|1358->224|1382->240|1462->244|1493->249|1549->279|1563->285|1619->321|1661->121|1688->222|1715->325|1744->328|1823->398|1863->400|1895->405|2381->864|2441->908|2481->910|2530->931|2584->958|2638->1003|2692->1019|2745->1044|2802->1074|2821->1084|2850->1092|2880->1095|2899->1105|2928->1113|2994->1152|3013->1162|3052->1180|3114->1215|3133->1225|3173->1244|3231->1271|3280->1292|3334->1315|3380->1333|3826->1752|3886->1796|3926->1798|3975->1819|4029->1846|4083->1891|4137->1907|4190->1932|4247->1962|4266->1972|4295->1980|4325->1983|4344->1993|4373->2001|4439->2040|4458->2050|4497->2068|4559->2103|4578->2113|4618->2132|4677->2160|4727->2182|4781->2205|4828->2224|4945->2315|4960->2321|5011->2363|5051->2365|5087->2374|5846->3103|5879->3106
                  LINES: 28->1|31->3|32->4|34->6|34->6|36->6|37->7|37->7|37->7|37->7|39->2|40->5|41->8|43->10|43->10|43->10|44->11|58->25|58->25|58->25|59->26|61->28|61->28|61->28|62->29|62->29|62->29|62->29|62->29|62->29|62->29|63->30|63->30|63->30|64->31|64->31|64->31|65->32|66->33|67->34|69->36|84->51|84->51|84->51|85->52|87->54|87->54|87->54|88->55|88->55|88->55|88->55|88->55|88->55|88->55|89->56|89->56|89->56|90->57|90->57|90->57|92->59|94->61|95->62|98->65|105->72|105->72|105->72|105->72|106->73|128->95|130->97
                  -- GENERATED --
              */
          