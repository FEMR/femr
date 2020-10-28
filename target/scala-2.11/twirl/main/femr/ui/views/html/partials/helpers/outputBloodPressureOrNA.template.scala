
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


Seq[Any](format.raw/*1.59*/("""

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
                  DATE: Tue Oct 27 21:59:18 PDT 2020
                  SOURCE: C:/Users/Noel/Documents/csc402/lemur-femr/femr/app/femr/ui/views/partials/helpers/outputBloodPressureOrNA.scala.html
                  HASH: 2f66d8d4921908244228c871befac977046d885b
                  MATRIX: 1018->1|1170->58|1200->63|1249->104|1288->106|1320->112|1343->119|1354->124|1391->125|1423->132|1451->140|1479->141|1508->144|1537->153|1571->158
                  LINES: 28->1|33->1|35->3|35->3|35->3|36->4|37->5|37->5|37->5|38->6|38->6|38->6|38->6|38->6|40->8
                  -- GENERATED --
              */
          