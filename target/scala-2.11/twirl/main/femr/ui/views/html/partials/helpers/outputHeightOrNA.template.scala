
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


Seq[Any](format.raw/*1.127*/("""


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
                  DATE: Tue Oct 27 21:59:18 PDT 2020
                  SOURCE: C:/Users/Noel/Documents/csc402/lemur-femr/femr/app/femr/ui/views/partials/helpers/outputHeightOrNA.scala.html
                  HASH: ca86f6cd0a56606eca06041c8e0c42f91e2e5ff2
                  MATRIX: 1046->1|1267->126|1303->137|1323->149|1362->151|1399->162|1442->197|1481->199|1522->214|1552->224|1581->236|1593->241|1631->242|1675->388|1717->403|1788->453|1844->478|1869->486|1882->491|1921->492|1953->566|1984->571|2080->658|2120->660|2152->666|2183->676|2206->682|2219->687|2258->688|2290->694|2339->734|2379->736|2413->796|2446->803|2459->807|2504->831|2541->838|2573->844|2626->888|2666->890|2700->954|2733->961|2748->967|2793->991|2833->1001|2875->1012|2912->1019
                  LINES: 28->1|33->1|36->4|36->4|36->4|37->5|37->5|37->5|38->6|38->6|39->7|39->7|39->7|41->10|42->11|42->11|44->13|45->14|45->14|45->14|46->15|47->16|47->16|47->16|48->17|48->17|49->18|49->18|49->18|50->19|50->19|50->19|51->20|52->21|52->21|52->21|53->22|54->23|54->23|54->23|55->24|56->25|56->25|56->25|57->26|58->27|59->28
                  -- GENERATED --
              */
          