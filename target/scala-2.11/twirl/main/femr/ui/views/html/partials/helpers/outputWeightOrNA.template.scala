
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

object outputWeightOrNA extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template3[java.lang.String,java.lang.Boolean,java.lang.String,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(weight: java.lang.String, isMetric: java.lang.Boolean = false, emptyValue: java.lang.String = "N/A"):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*1.103*/("""


"""),_display_(/*4.2*/if(weight == null || weight.equals("null"))/*4.45*/{_display_(Seq[Any](format.raw/*4.46*/("""
    """),_display_(/*5.6*/emptyValue),format.raw/*5.16*/("""
""")))}/*6.2*/else/*6.6*/{_display_(Seq[Any](format.raw/*6.7*/("""
    """),_display_(/*7.6*/if(isMetric)/*7.18*/ {_display_(Seq[Any](format.raw/*7.20*/("""
        """),_display_(/*8.10*/weight/*8.16*/.replaceAll("\\..*", "")),format.raw/*8.40*/("""kg
    """)))}/*9.7*/else/*9.12*/{_display_(Seq[Any](format.raw/*9.13*/("""
        """),format.raw/*10.74*/("""
        """),_display_(/*11.10*/weight/*11.16*/.replaceAll("\\..*", "")),format.raw/*11.40*/("""lbs
    """)))}),format.raw/*12.6*/("""
""")))}))
      }
    }
  }

  def render(weight:java.lang.String,isMetric:java.lang.Boolean,emptyValue:java.lang.String): play.twirl.api.HtmlFormat.Appendable = apply(weight,isMetric,emptyValue)

  def f:((java.lang.String,java.lang.Boolean,java.lang.String) => play.twirl.api.HtmlFormat.Appendable) = (weight,isMetric,emptyValue) => apply(weight,isMetric,emptyValue)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  DATE: Tue Oct 27 21:59:18 PDT 2020
                  SOURCE: C:/Users/Noel/Documents/csc402/lemur-femr/femr/app/femr/ui/views/partials/helpers/outputWeightOrNA.scala.html
                  HASH: fd5bf1cf264e5bf360c5471c6c042449f0c08afb
                  MATRIX: 1029->1|1226->102|1258->109|1309->152|1347->153|1379->160|1409->170|1429->173|1440->177|1477->178|1509->185|1529->197|1568->199|1605->210|1619->216|1663->240|1689->250|1701->255|1739->256|1777->331|1815->342|1830->348|1875->372|1915->382
                  LINES: 28->1|33->1|36->4|36->4|36->4|37->5|37->5|38->6|38->6|38->6|39->7|39->7|39->7|40->8|40->8|40->8|41->9|41->9|41->9|42->10|43->11|43->11|43->11|44->12
                  -- GENERATED --
              */
          