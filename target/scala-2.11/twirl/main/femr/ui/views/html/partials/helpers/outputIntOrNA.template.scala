
package femr.ui.views.html.partials.helpers

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

object outputIntOrNA extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template1[java.lang.Integer,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(number: java.lang.Integer):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*2.1*/("""
"""),_display_(/*3.2*/if(number == null)/*3.20*/{_display_(Seq[Any](format.raw/*3.21*/("""
    """),format.raw/*4.5*/("""N/A
""")))}/*5.2*/else/*5.6*/{_display_(Seq[Any](format.raw/*5.7*/("""
    """),_display_(/*6.6*/number),format.raw/*6.12*/("""
""")))}))
      }
    }
  }

  def render(number:java.lang.Integer): play.twirl.api.HtmlFormat.Appendable = apply(number)

  def f:((java.lang.Integer) => play.twirl.api.HtmlFormat.Appendable) = (number) => apply(number)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  DATE: Wed Jan 20 18:29:29 PST 2021
                  SOURCE: /Users/noel/Desktop/CPE/femr/femr/app/femr/ui/views/partials/helpers/outputIntOrNA.scala.html
                  HASH: 0e4d03f98f1a549df1cc44e0bb92a67150a7c0fa
                  MATRIX: 992->1|1113->29|1140->31|1166->49|1204->50|1235->55|1257->60|1268->64|1305->65|1336->71|1362->77
                  LINES: 28->1|33->2|34->3|34->3|34->3|35->4|36->5|36->5|36->5|37->6|37->6
                  -- GENERATED --
              */
          