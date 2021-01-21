
package femr.ui.views.html.partials.medical

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

object problemField extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template2[Integer,femr.common.models.TabFieldItem,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(number: Integer, problemField: femr.common.models.TabFieldItem):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*2.1*/("""
"""),_display_(/*3.2*/if(number == 0)/*3.17*/ {_display_(Seq[Any](format.raw/*3.19*/("""
    """),_display_(/*4.6*/if(problemField != null)/*4.30*/ {_display_(Seq[Any](format.raw/*4.32*/("""

        """),format.raw/*6.9*/("""<div class="problem">
            <input id="problem"""),_display_(/*7.32*/number),format.raw/*7.38*/("""" type="text" class="form-control input-sm" value=""""),_display_(/*7.90*/problemField/*7.102*/.getValue),format.raw/*7.111*/("""" readonly/>
        </div>
    """)))}/*9.7*/else/*9.12*/{_display_(Seq[Any](format.raw/*9.13*/("""

        """),format.raw/*11.9*/("""<div id="problem"""),_display_(/*11.26*/number),format.raw/*11.32*/("""-container" class="problem">
            <input id="problem"""),_display_(/*12.32*/number),format.raw/*12.38*/("""" name="problem"""),_display_(/*12.54*/number),format.raw/*12.60*/("""" type="text" class="form-control input-sm" value=""/>
        </div>
    """)))}),format.raw/*14.6*/("""
""")))}/*15.3*/else/*15.8*/{_display_(Seq[Any](format.raw/*15.9*/("""
    """),_display_(/*16.6*/if(problemField != null)/*16.30*/ {_display_(Seq[Any](format.raw/*16.32*/("""

        """),format.raw/*18.9*/("""<div id="problem"""),_display_(/*18.26*/number),format.raw/*18.32*/("""-container" class="problem">
            <input id="problem"""),_display_(/*19.32*/number),format.raw/*19.38*/("""" type="text" class="form-control input-sm" value=""""),_display_(/*19.90*/problemField/*19.102*/.getValue),format.raw/*19.111*/("""" readonly/>
        </div>
    """)))}/*21.7*/else/*21.12*/{_display_(Seq[Any](format.raw/*21.13*/("""

        """),format.raw/*23.9*/("""<div id="problem"""),_display_(/*23.26*/number),format.raw/*23.32*/("""-container" class="problem hidden">
            <input id="problem"""),_display_(/*24.32*/number),format.raw/*24.38*/("""" name="problem"""),_display_(/*24.54*/number),format.raw/*24.60*/("""" type="text" class="form-control input-sm" value=""/>
        </div>
    """)))}),format.raw/*26.6*/("""
""")))}))
      }
    }
  }

  def render(number:Integer,problemField:femr.common.models.TabFieldItem): play.twirl.api.HtmlFormat.Appendable = apply(number,problemField)

  def f:((Integer,femr.common.models.TabFieldItem) => play.twirl.api.HtmlFormat.Appendable) = (number,problemField) => apply(number,problemField)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  DATE: Wed Jan 20 18:29:28 PST 2021
                  SOURCE: /Users/noel/Desktop/CPE/femr/femr/app/femr/ui/views/partials/medical/problemField.scala.html
                  HASH: 34cadd46f12ca2f522db8a7fe9489cb3f04e6e8f
                  MATRIX: 1013->1|1171->66|1198->68|1221->83|1260->85|1291->91|1323->115|1362->117|1398->127|1477->180|1503->186|1581->238|1602->250|1632->259|1682->293|1694->298|1732->299|1769->309|1813->326|1840->332|1927->392|1954->398|1997->414|2024->420|2129->495|2149->498|2161->503|2199->504|2231->510|2264->534|2304->536|2341->546|2385->563|2412->569|2499->629|2526->635|2605->687|2627->699|2658->708|2709->742|2722->747|2761->748|2798->758|2842->775|2869->781|2963->848|2990->854|3033->870|3060->876|3165->951
                  LINES: 28->1|33->2|34->3|34->3|34->3|35->4|35->4|35->4|37->6|38->7|38->7|38->7|38->7|38->7|40->9|40->9|40->9|42->11|42->11|42->11|43->12|43->12|43->12|43->12|45->14|46->15|46->15|46->15|47->16|47->16|47->16|49->18|49->18|49->18|50->19|50->19|50->19|50->19|50->19|52->21|52->21|52->21|54->23|54->23|54->23|55->24|55->24|55->24|55->24|57->26
                  -- GENERATED --
              */
          