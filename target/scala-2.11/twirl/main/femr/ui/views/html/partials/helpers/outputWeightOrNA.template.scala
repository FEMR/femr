
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


Seq[Any](format.raw/*2.1*/("""

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
                  DATE: Wed Jan 20 18:29:29 PST 2021
                  SOURCE: /Users/noel/Desktop/CPE/femr/femr/app/femr/ui/views/partials/helpers/outputWeightOrNA.scala.html
                  HASH: cc5f8228c1e410db8c1e0a934f88fec37f2521d7
                  MATRIX: 1029->1|1224->103|1252->106|1303->149|1341->150|1372->156|1402->166|1421->168|1432->172|1469->173|1500->179|1520->191|1559->193|1595->203|1609->209|1653->233|1678->242|1690->247|1728->248|1765->322|1802->332|1817->338|1862->362|1901->371
                  LINES: 28->1|33->2|35->4|35->4|35->4|36->5|36->5|37->6|37->6|37->6|38->7|38->7|38->7|39->8|39->8|39->8|40->9|40->9|40->9|41->10|42->11|42->11|42->11|43->12
                  -- GENERATED --
              */
          