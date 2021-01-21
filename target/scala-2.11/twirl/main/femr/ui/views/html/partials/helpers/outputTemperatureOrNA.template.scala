
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


Seq[Any](format.raw/*2.1*/("""

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
                  DATE: Wed Jan 20 18:29:29 PST 2021
                  SOURCE: /Users/noel/Desktop/CPE/femr/femr/app/femr/ui/views/partials/helpers/outputTemperatureOrNA.scala.html
                  HASH: b0f9bf59814285ade069bb650463cbdf7d249d9c
                  MATRIX: 1034->1|1227->101|1255->104|1279->120|1317->121|1348->127|1378->137|1397->139|1408->143|1445->144|1476->150|1496->162|1535->164|1571->224|1607->234|1619->238|1670->269|1700->282|1713->287|1752->288|1789->348|1826->358|1839->362|1891->393|1933->405
                  LINES: 28->1|33->2|35->4|35->4|35->4|36->5|36->5|37->6|37->6|37->6|38->7|38->7|38->7|39->8|40->9|40->9|40->9|41->10|41->10|41->10|42->11|43->12|43->12|43->12|44->13
                  -- GENERATED --
              */
          