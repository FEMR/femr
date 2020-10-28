
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


Seq[Any](format.raw/*1.48*/("""

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
                  DATE: Tue Oct 27 21:59:18 PDT 2020
                  SOURCE: C:/Users/Noel/Documents/csc402/lemur-femr/femr/app/femr/ui/views/partials/encounter/outputPrescriptionOrProblem.scala.html
                  HASH: 650610cad2ecea4404a24ed30744a7ea51191c69
                  MATRIX: 1012->1|1153->47|1184->123|1214->128|1237->143|1276->145|1308->152|1351->187|1390->189|1426->199|1466->213|1490->218|1516->224|1586->268|1610->272|1646->282|1671->287|1719->306|1739->310|1750->315|1787->316|1820->323|1864->358|1904->360|1941->370|1982->384|2007->389|2034->395|2105->439|2130->443|2167->453|2193->458|2242->477|2275->480
                  LINES: 28->1|33->1|35->3|37->5|37->5|37->5|38->6|38->6|38->6|39->7|39->7|39->7|39->7|39->7|39->7|39->7|39->7|40->8|41->9|41->9|41->9|42->10|42->10|42->10|43->11|43->11|43->11|43->11|43->11|43->11|43->11|43->11|44->12|45->13
                  -- GENERATED --
              */
          