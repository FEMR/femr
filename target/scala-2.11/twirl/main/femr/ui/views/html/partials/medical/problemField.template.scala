
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


Seq[Any](format.raw/*1.66*/("""

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
                  DATE: Tue Oct 27 21:59:18 PDT 2020
                  SOURCE: C:/Users/Noel/Documents/csc402/lemur-femr/femr/app/femr/ui/views/partials/medical/problemField.scala.html
                  HASH: e24a2c20fc2f2a52a575600899b7804ba43dfcac
                  MATRIX: 1013->1|1172->65|1202->70|1225->85|1264->87|1296->94|1328->118|1367->120|1405->132|1485->186|1511->192|1589->244|1610->256|1640->265|1692->301|1704->306|1742->307|1781->319|1825->336|1852->342|1940->403|1967->409|2010->425|2037->431|2144->508|2165->512|2177->517|2215->518|2248->525|2281->549|2321->551|2360->563|2404->580|2431->586|2519->647|2546->653|2625->705|2647->717|2678->726|2731->762|2744->767|2783->768|2822->780|2866->797|2893->803|2988->871|3015->877|3058->893|3085->899|3192->976
                  LINES: 28->1|33->1|35->3|35->3|35->3|36->4|36->4|36->4|38->6|39->7|39->7|39->7|39->7|39->7|41->9|41->9|41->9|43->11|43->11|43->11|44->12|44->12|44->12|44->12|46->14|47->15|47->15|47->15|48->16|48->16|48->16|50->18|50->18|50->18|51->19|51->19|51->19|51->19|51->19|53->21|53->21|53->21|55->23|55->23|55->23|56->24|56->24|56->24|56->24|58->26
                  -- GENERATED --
              */
          