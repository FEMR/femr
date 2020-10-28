
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
Seq[Any](format.raw/*1.121*/("""

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
                  DATE: Tue Oct 27 21:59:18 PDT 2020
                  SOURCE: C:/Users/Noel/Documents/csc402/lemur-femr/femr/app/femr/ui/views/superuser/tabs/manage.scala.html
                  HASH: 3f3bb8dce88a38126fc4b82653ee0503990d44eb
                  MATRIX: 1049->1|1241->125|1288->167|1358->229|1382->245|1462->249|1494->255|1550->285|1564->291|1620->327|1665->120|1695->226|1723->332|1754->337|1833->407|1873->409|1906->415|2406->888|2466->932|2506->934|2556->956|2612->985|2666->1030|2720->1046|2774->1072|2831->1102|2850->1112|2879->1120|2909->1123|2928->1133|2957->1141|3024->1181|3043->1191|3082->1209|3145->1245|3164->1255|3204->1274|3263->1302|3313->1324|3368->1348|3416->1368|3877->1802|3937->1846|3977->1848|4027->1870|4083->1899|4137->1944|4191->1960|4245->1986|4302->2016|4321->2026|4350->2034|4380->2037|4399->2047|4428->2055|4495->2095|4514->2105|4553->2123|4616->2159|4635->2169|4675->2188|4736->2218|4788->2242|4843->2266|4893->2288|5017->2386|5032->2392|5083->2434|5123->2436|5160->2446|5941->3197|5976->3202
                  LINES: 28->1|31->3|32->4|34->6|34->6|36->6|37->7|37->7|37->7|37->7|39->1|41->5|42->8|44->10|44->10|44->10|45->11|59->25|59->25|59->25|60->26|62->28|62->28|62->28|63->29|63->29|63->29|63->29|63->29|63->29|63->29|64->30|64->30|64->30|65->31|65->31|65->31|66->32|67->33|68->34|70->36|85->51|85->51|85->51|86->52|88->54|88->54|88->54|89->55|89->55|89->55|89->55|89->55|89->55|89->55|90->56|90->56|90->56|91->57|91->57|91->57|93->59|95->61|96->62|99->65|106->72|106->72|106->72|106->72|107->73|129->95|131->97
                  -- GENERATED --
              */
          