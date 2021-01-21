
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

object fields extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template3[femr.common.dtos.CurrentUser,femr.ui.models.superuser.ContentViewModelGet,AssetsFinder,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(currentUser: femr.common.dtos.CurrentUser,
        viewModel: femr.ui.models.superuser.ContentViewModelGet,
        assets: AssetsFinder):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {
/*5.2*/import femr.ui.views.html.layouts.admin
/*6.2*/import femr.ui.controllers.superuser.routes.TabController

def /*8.6*/additionalStyles/*8.22*/:play.twirl.api.HtmlFormat.Appendable = {_display_(

Seq[Any](format.raw/*8.26*/("""
        """),format.raw/*9.9*/("""<link rel="stylesheet" href=""""),_display_(/*9.39*/assets/*9.45*/.path("css/superuser/superuser.css")),format.raw/*9.81*/("""">
    """)))};
Seq[Any](format.raw/*4.1*/("""
"""),format.raw/*7.1*/("""
    """),format.raw/*10.6*/("""

"""),_display_(/*12.2*/admin("Add Fields", currentUser, styles = additionalStyles, assets = assets)/*12.78*/ {_display_(Seq[Any](format.raw/*12.80*/("""
    """),format.raw/*13.5*/("""<h1>Adding content to """),_display_(/*13.28*/viewModel/*13.37*/.getName),format.raw/*13.45*/("""</h1>
    <div id="superUserWrap">
        <div class="halfPageTables">

            <h3>Current Fields</h3>
            <table>
                <thead>
                    <tr>
                        <th>Label</th>
                        <th>Type</th>
                        <th>Size</th>
                        <th>Order</th>
                        <th>Placeholder</th>
                    </tr>
                </thead>
                <tbody>

                """),_display_(/*30.18*/for(f <- 1 to viewModel.getCurrentCustomFieldItemList.size) yield /*30.77*/ {_display_(Seq[Any](format.raw/*30.79*/("""
                    """),_display_(/*31.22*/defining(viewModel.getCurrentCustomFieldItemList.get(f - 1))/*31.82*/ { currentField =>_display_(Seq[Any](format.raw/*31.100*/("""
                        """),format.raw/*32.25*/("""<tr>
                            <td>"""),_display_(/*33.34*/currentField/*33.46*/.getName),format.raw/*33.54*/("""</td>
                            <td>"""),_display_(/*34.34*/currentField/*34.46*/.getType),format.raw/*34.54*/("""</td>
                            <td>"""),_display_(/*35.34*/currentField/*35.46*/.getSize),format.raw/*35.54*/("""</td>
                            <td>"""),_display_(/*36.34*/currentField/*36.46*/.getOrder),format.raw/*36.55*/("""</td>
                            <td>"""),_display_(/*37.34*/currentField/*37.46*/.getPlaceholder),format.raw/*37.61*/("""</td>
                        </tr>
                    """)))}),format.raw/*39.22*/("""

                """)))}),format.raw/*41.18*/("""

                """),format.raw/*43.17*/("""</tbody>
            </table>
        </div>
        <div class="halfPageTables">
            <h3>Deleted Fields</h3>
            <table>
                <thead>
                    <tr>
                        <th>Field Label</th>
                        <th>Field Type</th>
                        <th>Field Size</th>
                    </tr>
                </thead>
                <tbody>

                """),_display_(/*58.18*/for(f <- 1 to viewModel.getRemovedCustomFieldItemList.size) yield /*58.77*/ {_display_(Seq[Any](format.raw/*58.79*/("""
                    """),_display_(/*59.22*/defining(viewModel.getRemovedCustomFieldItemList.get(f - 1))/*59.82*/ { removedField =>_display_(Seq[Any](format.raw/*59.100*/("""
                        """),format.raw/*60.25*/("""<tr>
                            <td>"""),_display_(/*61.34*/removedField/*61.46*/.getName),format.raw/*61.54*/("""</td>
                            <td>"""),_display_(/*62.34*/removedField/*62.46*/.getType),format.raw/*62.54*/("""</td>
                            <td>"""),_display_(/*63.34*/removedField/*63.46*/.getSize),format.raw/*63.54*/("""</td>
                        </tr>
                    """)))}),format.raw/*65.22*/("""
                """)))}),format.raw/*66.18*/("""

                """),format.raw/*68.17*/("""</tbody>
            </table>

        </div>

    </div>

    <div id="bottomFields">
    """),_display_(/*76.6*/helper/*76.12*/.form(action = TabController.fieldsPost(viewModel.getName))/*76.71*/ {_display_(Seq[Any](format.raw/*76.73*/("""
        """),format.raw/*77.9*/("""<div class="halfPageForms">
            <h4>Add/Edit Field</h4>
            <label for="addName">Name:</label>
            <input type="text" class="fInput" name="addName" placeholder="Label"/>
            <br/>
            <label for="addType">Type:</label>
            """),format.raw/*83.86*/("""
            """),format.raw/*84.13*/("""<select name="addType" class="fOption">
            """),_display_(/*85.14*/for(t <- 1 to viewModel.getCustomFieldTypes.size) yield /*85.63*/ {_display_(Seq[Any](format.raw/*85.65*/("""
                """),format.raw/*86.17*/("""<option value=""""),_display_(/*86.33*/viewModel/*86.42*/.getCustomFieldTypes.get(t - 1)),format.raw/*86.73*/("""">"""),_display_(/*86.76*/viewModel/*86.85*/.getCustomFieldTypes.get(t - 1)),format.raw/*86.116*/("""</option>
            """)))}),format.raw/*87.14*/("""
            """),format.raw/*88.13*/("""</select>

            <br/>
            <label for="addSize">Size:</label>
            """),format.raw/*92.86*/("""
            """),format.raw/*93.13*/("""<select name="addSize" class="fOption">
            """),_display_(/*94.14*/for(s <- 1 to viewModel.getCustomFieldSizes.size) yield /*94.63*/ {_display_(Seq[Any](format.raw/*94.65*/("""
                """),format.raw/*95.17*/("""<option value=""""),_display_(/*95.33*/viewModel/*95.42*/.getCustomFieldSizes.get(s - 1)),format.raw/*95.73*/("""">"""),_display_(/*95.76*/viewModel/*95.85*/.getCustomFieldSizes.get(s - 1)),format.raw/*95.116*/("""</option>
            """)))}),format.raw/*96.14*/("""
            """),format.raw/*97.13*/("""</select>
            <br/>
            <label for="addOrder">Order:</label>
            <input type="number" class="fInput" name="addOrder" placeholder="Order"/>
            <br/>
            <label for="addPlaceholder">Placeholder:</label>
            <input type="text" class="fInput" name="addPlaceholder" placeholder="Placeholder"/>
        </div>
        <div class="halfPageForms">
            <h4>Toggle Field</h4>
            <p>Activate or Deactivate a field by name</p>
            <label for="toggleName">Name</label>
            <input type="text" class="fInput" name="toggleName"/>
            <br/>

        </div>

    </div>
    <button type="submit" class="fButton" id="contentSubmit">Submit</button>
""")))}),format.raw/*116.2*/("""

""")))}))
      }
    }
  }

  def render(currentUser:femr.common.dtos.CurrentUser,viewModel:femr.ui.models.superuser.ContentViewModelGet,assets:AssetsFinder): play.twirl.api.HtmlFormat.Appendable = apply(currentUser,viewModel,assets)

  def f:((femr.common.dtos.CurrentUser,femr.ui.models.superuser.ContentViewModelGet,AssetsFinder) => play.twirl.api.HtmlFormat.Appendable) = (currentUser,viewModel,assets) => apply(currentUser,viewModel,assets)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  DATE: Wed Jan 20 18:29:26 PST 2021
                  SOURCE: /Users/noel/Desktop/CPE/femr/femr/app/femr/ui/views/superuser/tabs/fields.scala.html
                  HASH: 3efb0376a8be006fe38aa1a40bf8e7c487e87034
                  MATRIX: 1052->1|1263->142|1310->183|1380->247|1404->263|1484->267|1519->276|1575->306|1589->312|1645->348|1691->140|1718->241|1750->356|1779->359|1864->435|1904->437|1936->442|1986->465|2004->474|2033->482|2530->952|2605->1011|2645->1013|2694->1035|2763->1095|2820->1113|2873->1138|2938->1176|2959->1188|2988->1196|3054->1235|3075->1247|3104->1255|3170->1294|3191->1306|3220->1314|3286->1353|3307->1365|3337->1374|3403->1413|3424->1425|3460->1440|3548->1497|3598->1516|3644->1534|4084->1947|4159->2006|4199->2008|4248->2030|4317->2090|4374->2108|4427->2133|4492->2171|4513->2183|4542->2191|4608->2230|4629->2242|4658->2250|4724->2289|4745->2301|4774->2309|4862->2366|4911->2384|4957->2402|5075->2494|5090->2500|5158->2559|5198->2561|5234->2570|5533->2914|5574->2927|5654->2980|5719->3029|5759->3031|5804->3048|5847->3064|5865->3073|5917->3104|5947->3107|5965->3116|6018->3147|6072->3170|6113->3183|6229->3344|6270->3357|6350->3410|6415->3459|6455->3461|6500->3478|6543->3494|6561->3503|6613->3534|6643->3537|6661->3546|6714->3577|6768->3600|6809->3613|7560->4333
                  LINES: 28->1|33->5|34->6|36->8|36->8|38->8|39->9|39->9|39->9|39->9|41->4|42->7|43->10|45->12|45->12|45->12|46->13|46->13|46->13|46->13|63->30|63->30|63->30|64->31|64->31|64->31|65->32|66->33|66->33|66->33|67->34|67->34|67->34|68->35|68->35|68->35|69->36|69->36|69->36|70->37|70->37|70->37|72->39|74->41|76->43|91->58|91->58|91->58|92->59|92->59|92->59|93->60|94->61|94->61|94->61|95->62|95->62|95->62|96->63|96->63|96->63|98->65|99->66|101->68|109->76|109->76|109->76|109->76|110->77|116->83|117->84|118->85|118->85|118->85|119->86|119->86|119->86|119->86|119->86|119->86|119->86|120->87|121->88|125->92|126->93|127->94|127->94|127->94|128->95|128->95|128->95|128->95|128->95|128->95|128->95|129->96|130->97|149->116
                  -- GENERATED --
              */
          