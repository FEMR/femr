
package femr.ui.views.html.errors

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

object global extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template1[AssetsFinder,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(assets: AssetsFinder):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {
/*3.2*/import femr.ui.views.html.layouts.error

def /*4.6*/content/*4.13*/:play.twirl.api.HtmlFormat.Appendable = {_display_(

Seq[Any](format.raw/*4.17*/("""
        """),format.raw/*5.9*/("""<div id="errorMessage" style="text-align : center ;">
            <p>Oops! This was not supposed to happen.</p>
            <p>Whatever caused this has been logged and will be looked at by a nerd as soon as possible.</p>
            <p>Please click <a href="/">here</a> to return home.</p>
        </div>


    """)))};
Seq[Any](format.raw/*1.24*/("""

"""),format.raw/*4.1*/("""    """),format.raw/*12.6*/("""
"""),_display_(/*13.2*/error(errorContent = content, assets = assets)))
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
                  SOURCE: C:/Users/Noel/Documents/csc402/lemur-femr/femr/app/femr/ui/views/errors/global.scala.html
                  HASH: 06f9c0e5f4a56c6f2333156b47a036465c85f20a
                  MATRIX: 970->1|1065->28|1117->74|1132->81|1212->85|1248->95|1606->23|1636->69|1667->414|1696->417
                  LINES: 28->1|31->3|33->4|33->4|35->4|36->5|44->1|46->4|46->12|47->13
                  -- GENERATED --
              */
          