
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

object outputHeightOrNA extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template4[java.lang.String,java.lang.String,java.lang.Boolean,java.lang.String,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(feet: java.lang.String, inches: java.lang.String, isMetric: java.lang.Boolean = false, emptyValue: java.lang.String = "N/A"):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*2.1*/("""

    """),_display_(/*4.6*/if(isMetric)/*4.18*/ {_display_(Seq[Any](format.raw/*4.20*/("""
        """),_display_(/*5.10*/if(feet == null && inches == null )/*5.45*/ {_display_(Seq[Any](format.raw/*5.47*/("""
            """),_display_(/*6.14*/emptyValue),format.raw/*6.24*/("""
        """)))}/*7.11*/else/*7.16*/{_display_(Seq[Any](format.raw/*7.17*/("""

            """),format.raw/*10.65*/("""
            """),_display_(/*11.14*/("%s.%2sm".format(feet, inches).replace(' ', '0'))),format.raw/*11.64*/("""
            
        """)))}),format.raw/*13.10*/("""
    """)))}/*14.7*/else/*14.12*/{_display_(Seq[Any](format.raw/*14.13*/("""
		"""),format.raw/*15.73*/("""
		"""),_display_(/*16.4*/if((inches != null && feet != null) && (inches.equals("null") && feet.equals("null")) )/*16.91*/ {_display_(Seq[Any](format.raw/*16.93*/("""
			"""),_display_(/*17.5*/emptyValue),format.raw/*17.15*/("""
		""")))}/*18.5*/else/*18.10*/{_display_(Seq[Any](format.raw/*18.11*/("""
			"""),_display_(/*19.5*/if(feet != null && !feet.equals("null"))/*19.45*/ {_display_(Seq[Any](format.raw/*19.47*/("""
				"""),format.raw/*20.59*/("""
				"""),_display_(/*21.6*/feet/*21.10*/.replaceAll("\\..*", "")),format.raw/*21.34*/("""'
			""")))}),format.raw/*22.5*/("""
			"""),_display_(/*23.5*/if(inches != null && !inches.equals("null"))/*23.49*/ {_display_(Seq[Any](format.raw/*23.51*/("""
				"""),format.raw/*24.63*/("""
				"""),_display_(/*25.6*/inches/*25.12*/.replaceAll("\\..*", "")),format.raw/*25.36*/(""""
		    """)))}),format.raw/*26.8*/("""
        """)))}),format.raw/*27.10*/("""
    """)))}),format.raw/*28.6*/("""

"""))
      }
    }
  }

  def render(feet:java.lang.String,inches:java.lang.String,isMetric:java.lang.Boolean,emptyValue:java.lang.String): play.twirl.api.HtmlFormat.Appendable = apply(feet,inches,isMetric,emptyValue)

  def f:((java.lang.String,java.lang.String,java.lang.Boolean,java.lang.String) => play.twirl.api.HtmlFormat.Appendable) = (feet,inches,isMetric,emptyValue) => apply(feet,inches,isMetric,emptyValue)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  DATE: Wed Jan 20 18:29:29 PST 2021
                  SOURCE: /Users/noel/Desktop/CPE/femr/femr/app/femr/ui/views/partials/helpers/outputHeightOrNA.scala.html
                  HASH: 77e991fe28b579525646fd960facb2069bdeb95c
                  MATRIX: 1046->1|1265->127|1297->134|1317->146|1356->148|1392->158|1435->193|1474->195|1514->209|1544->219|1572->230|1584->235|1622->236|1664->379|1705->393|1776->443|1830->466|1854->473|1867->478|1906->479|1937->552|1967->556|2063->643|2103->645|2134->650|2165->660|2187->665|2200->670|2239->671|2270->676|2319->716|2359->718|2392->777|2424->783|2437->787|2482->811|2518->817|2549->822|2602->866|2642->868|2675->931|2707->937|2722->943|2767->967|2806->976|2847->986|2883->992
                  LINES: 28->1|33->2|35->4|35->4|35->4|36->5|36->5|36->5|37->6|37->6|38->7|38->7|38->7|40->10|41->11|41->11|43->13|44->14|44->14|44->14|45->15|46->16|46->16|46->16|47->17|47->17|48->18|48->18|48->18|49->19|49->19|49->19|50->20|51->21|51->21|51->21|52->22|53->23|53->23|53->23|54->24|55->25|55->25|55->25|56->26|57->27|58->28
                  -- GENERATED --
              */
          