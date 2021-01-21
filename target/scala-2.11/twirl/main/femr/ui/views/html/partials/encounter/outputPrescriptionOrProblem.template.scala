
package femr.ui.views.html.partials.encounter

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

object outputPrescriptionOrProblem extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template3[Integer,String,String,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(number: Integer, value: String, name: String):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*2.1*/("""
"""),format.raw/*3.73*/("""

"""),_display_(/*5.2*/if(number == 1)/*5.17*/ {_display_(Seq[Any](format.raw/*5.19*/("""
    """),_display_(/*6.6*/if(value != null && !value.isEmpty)/*6.41*/ {_display_(Seq[Any](format.raw/*6.43*/("""
        """),format.raw/*7.9*/("""<input id = """"),_display_(/*7.23*/name),_display_(/*7.28*/number),format.raw/*7.34*/("""" type="text" class="form-control input-sm """),_display_(/*7.78*/name),format.raw/*7.82*/("""" value=""""),_display_(/*7.92*/value),format.raw/*7.97*/("""" readonly/>
    """)))}),format.raw/*8.6*/("""
""")))}/*9.3*/else/*9.8*/{_display_(Seq[Any](format.raw/*9.9*/("""
    """),_display_(/*10.6*/if(value != null && !value.isEmpty)/*10.41*/ {_display_(Seq[Any](format.raw/*10.43*/("""
        """),format.raw/*11.9*/("""<input id = """"),_display_(/*11.23*/name),_display_(/*11.28*/number),format.raw/*11.34*/("""" type="text" class="form-control input-sm """),_display_(/*11.78*/name),format.raw/*11.82*/("""" value=""""),_display_(/*11.92*/value),format.raw/*11.97*/("""" readonly/>
    """)))}),format.raw/*12.6*/("""
""")))}),format.raw/*13.2*/("""
"""))
      }
    }
  }

  def render(number:Integer,value:String,name:String): play.twirl.api.HtmlFormat.Appendable = apply(number,value,name)

  def f:((Integer,String,String) => play.twirl.api.HtmlFormat.Appendable) = (number,value,name) => apply(number,value,name)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  DATE: Wed Jan 20 18:29:28 PST 2021
                  SOURCE: /Users/noel/Desktop/CPE/femr/femr/app/femr/ui/views/partials/encounter/outputPrescriptionOrProblem.scala.html
                  HASH: 5742ca5abdfcc8e346a289ee1548ffdcb7e2a1d5
                  MATRIX: 1012->1|1152->48|1180->121|1208->124|1231->139|1270->141|1301->147|1344->182|1383->184|1418->193|1458->207|1482->212|1508->218|1578->262|1602->266|1638->276|1663->281|1710->299|1729->302|1740->307|1777->308|1809->314|1853->349|1893->351|1929->360|1970->374|1995->379|2022->385|2093->429|2118->433|2155->443|2181->448|2229->466|2261->468
                  LINES: 28->1|33->2|34->3|36->5|36->5|36->5|37->6|37->6|37->6|38->7|38->7|38->7|38->7|38->7|38->7|38->7|38->7|39->8|40->9|40->9|40->9|41->10|41->10|41->10|42->11|42->11|42->11|42->11|42->11|42->11|42->11|42->11|43->12|44->13
                  -- GENERATED --
              */
          