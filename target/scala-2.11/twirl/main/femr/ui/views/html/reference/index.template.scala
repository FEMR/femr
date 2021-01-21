
package femr.ui.views.html.reference

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

object index extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template2[femr.common.dtos.CurrentUser,AssetsFinder,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(currentUser: femr.common.dtos.CurrentUser, assets: AssetsFinder):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {
/*3.2*/import femr.ui.views.html.layouts.main
/*4.2*/import femr.ui.controllers.routes.ReferenceController

def /*6.2*/additionalStyles/*6.18*/:play.twirl.api.HtmlFormat.Appendable = {_display_(

Seq[Any](format.raw/*6.22*/("""
    """),format.raw/*7.5*/("""<link rel="stylesheet" href=""""),_display_(/*7.35*/assets/*7.41*/.path("css/reference.css")),format.raw/*7.67*/("""">
""")))};def /*9.2*/additionalScripts/*9.19*/:play.twirl.api.HtmlFormat.Appendable = {_display_(

Seq[Any](format.raw/*9.23*/("""

""")))};
Seq[Any](format.raw/*2.1*/("""
"""),format.raw/*5.1*/("""
"""),format.raw/*8.2*/("""
"""),format.raw/*11.2*/("""

"""),_display_(/*13.2*/main("Reference", currentUser, styles = additionalStyles, scripts = additionalScripts, assets = assets )/*13.106*/ {_display_(Seq[Any](format.raw/*13.108*/("""


    """),format.raw/*16.5*/("""<div class="referenceContentWrap">

        <img src=""""),_display_(/*18.20*/assets/*18.26*/.path("img/reference/pt-chart-ros.png")),format.raw/*18.65*/(""""  />
        <img src=""""),_display_(/*19.20*/assets/*19.26*/.path("img/reference/cass-physical.png")),format.raw/*19.66*/("""" class="physical" />

    </div>

""")))}))
      }
    }
  }

  def render(currentUser:femr.common.dtos.CurrentUser,assets:AssetsFinder): play.twirl.api.HtmlFormat.Appendable = apply(currentUser,assets)

  def f:((femr.common.dtos.CurrentUser,AssetsFinder) => play.twirl.api.HtmlFormat.Appendable) = (currentUser,assets) => apply(currentUser,assets)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  DATE: Wed Jan 20 18:29:29 PST 2021
                  SOURCE: /Users/noel/Desktop/CPE/femr/femr/app/femr/ui/views/reference/index.scala.html
                  HASH: 23514d099955746b7acbecca0443e9145772a48c
                  MATRIX: 1001->1|1139->69|1185->109|1251->165|1275->181|1355->185|1386->190|1442->220|1456->226|1502->252|1528->258|1553->275|1633->279|1674->67|1701->163|1728->256|1756->282|1785->285|1899->389|1940->391|1974->398|2056->453|2071->459|2131->498|2183->523|2198->529|2259->569
                  LINES: 28->1|31->3|32->4|34->6|34->6|36->6|37->7|37->7|37->7|37->7|38->9|38->9|40->9|43->2|44->5|45->8|46->11|48->13|48->13|48->13|51->16|53->18|53->18|53->18|54->19|54->19|54->19
                  -- GENERATED --
              */
          