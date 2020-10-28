
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


Seq[Any](format.raw/*1.74*/("""

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
                  DATE: Tue Oct 27 21:59:18 PDT 2020
                  SOURCE: C:/Users/Noel/Documents/csc402/lemur-femr/femr/app/femr/ui/views/partials/header.scala.html
                  HASH: 5f324d073b85294bc7d2f00b7e64a6f9e873ac78
                  MATRIX: 1001->1|1146->78|1210->73|1240->114|1325->174|1356->197|1395->199|1432->210|1448->218|1503->253|1527->261|1539->266|1577->267|1614->278|1630->286|1668->304|1705->311|1738->317
                  LINES: 28->1|31->3|34->1|36->4|38->6|38->6|38->6|39->7|39->7|39->7|40->8|40->8|40->8|41->9|41->9|41->9|42->10|43->11
                  -- GENERATED --
              */
          