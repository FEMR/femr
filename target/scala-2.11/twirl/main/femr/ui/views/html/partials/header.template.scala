
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

object header extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template2[femr.common.dtos.CurrentUser,AssetsFinder,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(currentUser: femr.common.dtos.CurrentUser = null, assets: AssetsFinder):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {
/*3.2*/import femr.ui.views.html.partials


Seq[Any](format.raw/*2.1*/("""
"""),format.raw/*4.1*/("""<div class="navigation">
    <div class="container">
    """),_display_(/*6.6*/if(currentUser != null)/*6.29*/ {_display_(Seq[Any](format.raw/*6.31*/("""
        """),_display_(/*7.10*/partials/*7.18*/.authenticated(currentUser, assets)),format.raw/*7.53*/("""
    """)))}/*8.7*/else/*8.12*/{_display_(Seq[Any](format.raw/*8.13*/("""
        """),_display_(/*9.10*/partials/*9.18*/.anonymous(assets)),format.raw/*9.36*/("""
    """)))}),format.raw/*10.6*/("""
    """),format.raw/*11.5*/("""</div>
</div>

"""))
      }
    }
  }

  def render(currentUser:femr.common.dtos.CurrentUser,assets:AssetsFinder): play.twirl.api.HtmlFormat.Appendable = apply(currentUser,assets)

  def f:((femr.common.dtos.CurrentUser,AssetsFinder) => play.twirl.api.HtmlFormat.Appendable) = (currentUser,assets) => apply(currentUser,assets)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  DATE: Wed Jan 20 18:29:28 PST 2021
                  SOURCE: /Users/noel/Desktop/CPE/femr/femr/app/femr/ui/views/partials/header.scala.html
                  HASH: 44e20b4ee8244691e81e72656ea3d9769161ca25
                  MATRIX: 1001->1|1146->76|1209->74|1236->111|1319->169|1350->192|1389->194|1425->204|1441->212|1496->247|1519->254|1531->259|1569->260|1605->270|1621->278|1659->296|1695->302|1727->307
                  LINES: 28->1|31->3|34->2|35->4|37->6|37->6|37->6|38->7|38->7|38->7|39->8|39->8|39->8|40->9|40->9|40->9|41->10|42->11
                  -- GENERATED --
              */
          