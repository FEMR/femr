
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


Seq[Any](format.raw/*2.1*/("""
"""),format.raw/*3.1*/("""<div class="navigationLogo">
      <img src=""""),_display_(/*4.18*/assets/*4.24*/.path("img/logo_color_wordless_sm.png")),format.raw/*4.63*/("""" />
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
                  DATE: Wed Jan 20 19:05:22 PST 2021
                  SOURCE: /Users/noel/Desktop/CPE/femr/femr/app/femr/ui/views/partials/anonymous.scala.html
                  HASH: 0804b3d1344480d9ccc513478a2456b3c6234790
                  MATRIX: 975->1|1091->24|1118->25|1190->71|1204->77|1263->116
                  LINES: 28->1|33->2|34->3|35->4|35->4|35->4
                  -- GENERATED --
              */
          