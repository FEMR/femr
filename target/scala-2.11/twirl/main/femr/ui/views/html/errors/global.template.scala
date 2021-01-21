
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
Seq[Any](format.raw/*2.1*/("""
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
                  DATE: Wed Jan 20 18:29:27 PST 2021
                  SOURCE: /Users/noel/Desktop/CPE/femr/femr/app/femr/ui/views/errors/global.scala.html
                  HASH: 668d97f32a63eb9c85ba95cf0b4cc8a0af4d8342
                  MATRIX: 970->1|1065->26|1117->71|1132->78|1212->82|1247->91|1597->24|1624->66|1655->403|1683->405
                  LINES: 28->1|31->3|33->4|33->4|35->4|36->5|44->2|45->4|45->12|46->13
                  -- GENERATED --
              */
          