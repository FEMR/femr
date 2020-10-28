
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

object outputTemperatureOrNA extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template3[java.lang.String,java.lang.Boolean,java.lang.String,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(temp: java.lang.String, isMetric: java.lang.Boolean = false, emptyValue: java.lang.String = "N/A"):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*1.101*/("""


"""),_display_(/*4.2*/if(temp == null)/*4.18*/{_display_(Seq[Any](format.raw/*4.19*/("""
    """),_display_(/*5.6*/emptyValue),format.raw/*5.16*/("""
""")))}/*6.2*/else/*6.6*/{_display_(Seq[Any](format.raw/*6.7*/("""
    """),_display_(/*7.6*/if(isMetric)/*7.18*/ {_display_(Seq[Any](format.raw/*7.20*/("""
        """),format.raw/*8.60*/("""
        """),_display_(/*9.10*/temp/*9.14*/.replaceAll("(\\.\\d).*", "$1")),format.raw/*9.45*/("""&deg;C
    """)))}/*10.7*/else/*10.12*/{_display_(Seq[Any](format.raw/*10.13*/("""
        """),format.raw/*11.60*/("""
        """),_display_(/*12.10*/temp/*12.14*/.replaceAll("(\\.\\d).*", "$1")),format.raw/*12.45*/("""&deg;F
    """)))}),format.raw/*13.6*/("""
""")))}))
      }
    }
  }

  def render(temp:java.lang.String,isMetric:java.lang.Boolean,emptyValue:java.lang.String): play.twirl.api.HtmlFormat.Appendable = apply(temp,isMetric,emptyValue)

  def f:((java.lang.String,java.lang.Boolean,java.lang.String) => play.twirl.api.HtmlFormat.Appendable) = (temp,isMetric,emptyValue) => apply(temp,isMetric,emptyValue)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  DATE: Tue Oct 27 21:59:18 PDT 2020
                  SOURCE: C:/Users/Noel/Documents/csc402/lemur-femr/femr/app/femr/ui/views/partials/helpers/outputTemperatureOrNA.scala.html
                  HASH: 3a13f97108f20f668f2e4b8de528a2a4306d23d3
                  MATRIX: 1034->1|1229->100|1261->107|1285->123|1323->124|1355->131|1385->141|1405->144|1416->148|1453->149|1485->156|1505->168|1544->170|1581->231|1618->242|1630->246|1681->277|1712->291|1725->296|1764->297|1802->358|1840->369|1853->373|1905->404|1948->417
                  LINES: 28->1|33->1|36->4|36->4|36->4|37->5|37->5|38->6|38->6|38->6|39->7|39->7|39->7|40->8|41->9|41->9|41->9|42->10|42->10|42->10|43->11|44->12|44->12|44->12|45->13
                  -- GENERATED --
              */
          