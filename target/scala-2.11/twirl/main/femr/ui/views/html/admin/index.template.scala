
package femr.ui.views.html.admin

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
/*3.2*/import femr.ui.views.html.layouts.admin


Seq[Any](format.raw/*1.67*/("""

"""),format.raw/*4.1*/("""

"""),_display_(/*6.2*/admin("Admin Panel", currentUser, assets = assets)/*6.52*/ {_display_(Seq[Any](format.raw/*6.54*/("""
    """),format.raw/*7.5*/("""<h3>Welcome, Administrator. Choose an option to get started.</h3>
""")))}),format.raw/*8.2*/("""
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
                  DATE: Tue Oct 27 21:59:17 PDT 2020
                  SOURCE: C:/Users/Noel/Documents/csc402/lemur-femr/femr/app/femr/ui/views/admin/index.scala.html
                  HASH: e7832cf18bb84956fa1d247b504f544df5306e73
                  MATRIX: 997->1|1135->71|1204->66|1234->112|1264->117|1322->167|1361->169|1393->175|1490->243
                  LINES: 28->1|31->3|34->1|36->4|38->6|38->6|38->6|39->7|40->8
                  -- GENERATED --
              */
          