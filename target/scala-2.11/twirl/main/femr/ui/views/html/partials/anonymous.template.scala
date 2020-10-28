
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
      <img src=""""),_display_(/*5.18*/assets/*5.24*/.path("img/logo_color_wordless_sm.png")),format.raw/*5.63*/("""" />
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
                  DATE: Tue Oct 27 21:59:18 PDT 2020
                  SOURCE: C:/Users/Noel/Documents/csc402/lemur-femr/femr/app/femr/ui/views/partials/anonymous.scala.html
                  HASH: 84b7952dbbccbd56a3a977d577e98e7f66c2b46e
                  MATRIX: 975->1|1070->26|1148->23|1176->76|1204->78|1277->125|1291->131|1350->170
                  LINES: 28->1|31->2|34->1|35->3|36->4|37->5|37->5|37->5
                  -- GENERATED --
              */
          