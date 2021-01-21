
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

object outputBloodPressureOrNA extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template2[java.lang.String,java.lang.String,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(systolic: java.lang.String, diastolic: java.lang.String):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*2.1*/("""
"""),_display_(/*3.2*/if(systolic == null && diastolic == null)/*3.43*/ {_display_(Seq[Any](format.raw/*3.45*/("""
    """),format.raw/*4.5*/("""N/A
""")))}/*5.3*/else/*5.8*/{_display_(Seq[Any](format.raw/*5.9*/("""
    """),_display_(/*6.6*/systolic),format.raw/*6.14*/(""" """),format.raw/*6.15*/("""/ """),_display_(/*6.18*/diastolic),format.raw/*6.27*/("""

""")))}),format.raw/*8.2*/("""
"""))
      }
    }
  }

  def render(systolic:java.lang.String,diastolic:java.lang.String): play.twirl.api.HtmlFormat.Appendable = apply(systolic,diastolic)

  def f:((java.lang.String,java.lang.String) => play.twirl.api.HtmlFormat.Appendable) = (systolic,diastolic) => apply(systolic,diastolic)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  DATE: Wed Jan 20 18:29:29 PST 2021
                  SOURCE: /Users/noel/Desktop/CPE/femr/femr/app/femr/ui/views/partials/helpers/outputBloodPressureOrNA.scala.html
                  HASH: 994571977ac827874b58d67ff2acfd0b627b4aff
                  MATRIX: 1018->1|1169->59|1196->61|1245->102|1284->104|1315->109|1337->115|1348->120|1385->121|1416->127|1444->135|1472->136|1501->139|1530->148|1562->151
                  LINES: 28->1|33->2|34->3|34->3|34->3|35->4|36->5|36->5|36->5|37->6|37->6|37->6|37->6|37->6|39->8
                  -- GENERATED --
              */
          