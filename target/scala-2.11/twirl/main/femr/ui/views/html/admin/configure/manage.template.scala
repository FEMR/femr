
package femr.ui.views.html.admin.configure

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

object manage extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template3[femr.common.dtos.CurrentUser,femr.ui.models.admin.configure.IndexViewModelGet,AssetsFinder,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(currentUser: femr.common.dtos.CurrentUser,
        viewModel: femr.ui.models.admin.configure.IndexViewModelGet,
        assets: AssetsFinder):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {
/*5.2*/import femr.ui.controllers.admin.routes.ConfigureController
/*6.2*/import femr.ui.views.html.layouts.admin

def /*8.6*/additionalStyles/*8.22*/:play.twirl.api.HtmlFormat.Appendable = {_display_(

Seq[Any](format.raw/*8.26*/("""
        """),format.raw/*9.9*/("""<link rel="stylesheet" href=""""),_display_(/*9.39*/assets/*9.45*/.path("css/admin/configure.css")),format.raw/*9.77*/("""">
    """)))};
Seq[Any](format.raw/*3.30*/("""

"""),format.raw/*7.1*/("""
    """),format.raw/*10.6*/("""

"""),_display_(/*12.2*/admin("Configure", currentUser, styles = additionalStyles, assets = assets)/*12.77*/ {_display_(Seq[Any](format.raw/*12.79*/("""
    """),_display_(/*13.6*/helper/*13.12*/.form(action = ConfigureController.managePost())/*13.60*/ {_display_(Seq[Any](format.raw/*13.62*/("""
        """),format.raw/*14.9*/("""<table id="configurationTable">
            <tr>
                <th>Feature</th>
                <th>Description</th>
                <th>Toggle</th>
            </tr>

            """),_display_(/*21.14*/for((key, valyew) <- viewModel.getSettings) yield /*21.57*/ {_display_(Seq[Any](format.raw/*21.59*/("""
                """),format.raw/*22.17*/("""<tr>
                    <td class="name">"""),_display_(/*23.39*/key),format.raw/*23.42*/("""</td>
                        """),_display_(/*24.26*/defining(viewModel.getDescriptions.get(key))/*24.70*/ { description =>_display_(Seq[Any](format.raw/*24.87*/(""" """),format.raw/*24.88*/("""<td class="description">"""),_display_(/*24.113*/description),format.raw/*24.124*/("""</td>""")))}),format.raw/*24.130*/("""
                    """),format.raw/*25.21*/("""<td class="isActive">
                        <input type="checkbox" name="settings[]" value=""""),_display_(/*26.74*/key),format.raw/*26.77*/("""" """),_display_(/*26.80*/if(valyew)/*26.90*/ {_display_(Seq[Any](format.raw/*26.92*/(""" """),format.raw/*26.93*/("""checked="checked"""")))}),format.raw/*26.111*/(""" """),format.raw/*26.112*/("""/>
                    </td>
                </tr>
            """)))}),format.raw/*29.14*/("""

        """),format.raw/*31.9*/("""</table>
        
        <input type="submit" class="fButton" value="Save"/>
    """)))}),format.raw/*34.6*/("""

""")))}),format.raw/*36.2*/("""
"""))
      }
    }
  }

  def render(currentUser:femr.common.dtos.CurrentUser,viewModel:femr.ui.models.admin.configure.IndexViewModelGet,assets:AssetsFinder): play.twirl.api.HtmlFormat.Appendable = apply(currentUser,viewModel,assets)

  def f:((femr.common.dtos.CurrentUser,femr.ui.models.admin.configure.IndexViewModelGet,AssetsFinder) => play.twirl.api.HtmlFormat.Appendable) = (currentUser,viewModel,assets) => apply(currentUser,viewModel,assets)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  DATE: Tue Oct 27 21:59:17 PDT 2020
                  SOURCE: C:/Users/Noel/Documents/csc402/lemur-femr/femr/app/femr/ui/views/admin/configure/manage.scala.html
                  HASH: 623264e26c2818142409727e18803272a7d3116e
                  MATRIX: 1057->1|1274->150|1341->212|1393->260|1417->276|1497->280|1533->290|1589->320|1603->326|1655->358|1703->145|1733->253|1766->367|1797->372|1881->447|1921->449|1954->456|1969->462|2026->510|2066->512|2103->522|2320->712|2379->755|2419->757|2465->775|2536->819|2560->822|2619->854|2672->898|2727->915|2756->916|2809->941|2842->952|2880->958|2930->980|3053->1076|3077->1079|3107->1082|3126->1092|3166->1094|3195->1095|3245->1113|3275->1114|3373->1181|3412->1193|3528->1279|3563->1284
                  LINES: 28->1|33->5|34->6|36->8|36->8|38->8|39->9|39->9|39->9|39->9|41->3|43->7|44->10|46->12|46->12|46->12|47->13|47->13|47->13|47->13|48->14|55->21|55->21|55->21|56->22|57->23|57->23|58->24|58->24|58->24|58->24|58->24|58->24|58->24|59->25|60->26|60->26|60->26|60->26|60->26|60->26|60->26|60->26|63->29|65->31|68->34|70->36
                  -- GENERATED --
              */
          