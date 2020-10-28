
package femr.ui.views.html.partials

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

object anonymous extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template1[AssetsFinder,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(assets: AssetsFinder):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {
/*2.2*/import femr.ui.controllers.routes.HomeController


Seq[Any](format.raw/*1.24*/("""
"""),format.raw/*3.1*/("""
"""),format.raw/*4.1*/("""<div class="navigationLogo">
  <a href=""""),_display_(/*5.13*/HomeController/*5.27*/.index()),format.raw/*5.35*/("""">
      <img src=""""),_display_(/*6.18*/assets/*6.24*/.path("img/logo_color_wordless_sm.png")),format.raw/*6.63*/("""" />
  </a>
</div>"""))
      }
    }
  }

  def render(assets:AssetsFinder): play.twirl.api.HtmlFormat.Appendable = apply(assets)

  def f:((AssetsFinder) => play.twirl.api.HtmlFormat.Appendable) = (assets) => apply(assets)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  DATE: Tue Oct 27 23:13:32 PDT 2020
                  SOURCE: C:/Users/Noel/Documents/csc402/lemur-femr/femr/app/femr/ui/views/partials/anonymous.scala.html
                  HASH: aff52c3985008c7a02a3bf5671f718547133a043
                  MATRIX: 975->1|1070->26|1148->23|1176->76|1204->78|1272->120|1294->134|1322->142|1369->163|1383->169|1442->208
                  LINES: 28->1|31->2|34->1|35->3|36->4|37->5|37->5|37->5|38->6|38->6|38->6
                  -- GENERATED --
              */
          