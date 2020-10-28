
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
Seq[Any](format.raw/*3.30*/("""

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
                  DATE: Tue Oct 27 21:59:18 PDT 2020
                  SOURCE: C:/Users/Noel/Documents/csc402/lemur-femr/femr/app/femr/ui/views/superuser/tabs/fields.scala.html
                  HASH: 0ac6b21c4c1520d9bf90a06e09c01133b1778336
                  MATRIX: 1052->1|1265->146|1312->188|1382->254|1406->270|1486->274|1522->284|1578->314|1592->320|1648->356|1696->141|1726->247|1759->365|1790->370|1875->446|1915->448|1948->454|1998->477|2016->486|2045->494|2559->981|2634->1040|2674->1042|2724->1065|2793->1125|2850->1143|2904->1169|2970->1208|2991->1220|3020->1228|3087->1268|3108->1280|3137->1288|3204->1328|3225->1340|3254->1348|3321->1388|3342->1400|3372->1409|3439->1449|3460->1461|3496->1476|3586->1535|3638->1556|3686->1576|4141->2004|4216->2063|4256->2065|4306->2088|4375->2148|4432->2166|4486->2192|4552->2231|4573->2243|4602->2251|4669->2291|4690->2303|4719->2311|4786->2351|4807->2363|4836->2371|4926->2430|4976->2449|5024->2469|5150->2569|5165->2575|5233->2634|5273->2636|5310->2646|5615->2996|5657->3010|5738->3064|5803->3113|5843->3115|5889->3133|5932->3149|5950->3158|6002->3189|6032->3192|6050->3201|6103->3232|6158->3256|6200->3270|6320->3435|6362->3449|6443->3503|6508->3552|6548->3554|6594->3572|6637->3588|6655->3597|6707->3628|6737->3631|6755->3640|6808->3671|6863->3695|6905->3709|7675->4448
                  LINES: 28->1|33->5|34->6|36->8|36->8|38->8|39->9|39->9|39->9|39->9|41->3|43->7|44->10|46->12|46->12|46->12|47->13|47->13|47->13|47->13|64->30|64->30|64->30|65->31|65->31|65->31|66->32|67->33|67->33|67->33|68->34|68->34|68->34|69->35|69->35|69->35|70->36|70->36|70->36|71->37|71->37|71->37|73->39|75->41|77->43|92->58|92->58|92->58|93->59|93->59|93->59|94->60|95->61|95->61|95->61|96->62|96->62|96->62|97->63|97->63|97->63|99->65|100->66|102->68|110->76|110->76|110->76|110->76|111->77|117->83|118->84|119->85|119->85|119->85|120->86|120->86|120->86|120->86|120->86|120->86|120->86|121->87|122->88|126->92|127->93|128->94|128->94|128->94|129->95|129->95|129->95|129->95|129->95|129->95|129->95|130->96|131->97|150->116
                  -- GENERATED --
              */
          