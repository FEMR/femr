
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
Seq[Any](format.raw/*1.67*/("""

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
                  DATE: Tue Oct 27 21:59:18 PDT 2020
                  SOURCE: C:/Users/Noel/Documents/csc402/lemur-femr/femr/app/femr/ui/views/reference/index.scala.html
                  HASH: 2edc47397d98c3253a0d56d539834a9b593adac8
                  MATRIX: 1001->1|1139->71|1185->112|1251->170|1275->186|1355->190|1387->196|1443->226|1457->232|1503->258|1530->266|1555->283|1635->287|1679->66|1709->167|1737->263|1766->292|1797->297|1911->401|1952->403|1989->413|2073->470|2088->476|2148->515|2201->541|2216->547|2277->587
                  LINES: 28->1|31->3|32->4|34->6|34->6|36->6|37->7|37->7|37->7|37->7|38->9|38->9|40->9|43->1|45->5|46->8|47->11|49->13|49->13|49->13|52->16|54->18|54->18|54->18|55->19|55->19|55->19
                  -- GENERATED --
              */
          