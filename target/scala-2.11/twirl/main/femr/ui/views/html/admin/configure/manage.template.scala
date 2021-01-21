
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
Seq[Any](format.raw/*4.1*/("""
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
                  DATE: Wed Jan 20 18:29:25 PST 2021
                  SOURCE: /Users/noel/Desktop/CPE/femr/femr/app/femr/ui/views/admin/configure/manage.scala.html
                  HASH: 496c3ee747f52a59c66c90b2b83e1334e2ff7f85
                  MATRIX: 1057->1|1272->146|1339->207|1391->253|1415->269|1495->273|1530->282|1586->312|1600->318|1652->350|1698->144|1725->247|1757->358|1786->361|1870->436|1910->438|1942->444|1957->450|2014->498|2054->500|2090->509|2300->692|2359->735|2399->737|2444->754|2514->797|2538->800|2596->831|2649->875|2704->892|2733->893|2786->918|2819->929|2857->935|2906->956|3028->1051|3052->1054|3082->1057|3101->1067|3141->1069|3170->1070|3220->1088|3250->1089|3345->1153|3382->1163|3495->1246|3528->1249
                  LINES: 28->1|33->5|34->6|36->8|36->8|38->8|39->9|39->9|39->9|39->9|41->4|42->7|43->10|45->12|45->12|45->12|46->13|46->13|46->13|46->13|47->14|54->21|54->21|54->21|55->22|56->23|56->23|57->24|57->24|57->24|57->24|57->24|57->24|57->24|58->25|59->26|59->26|59->26|59->26|59->26|59->26|59->26|59->26|62->29|64->31|67->34|69->36
                  -- GENERATED --
              */
          