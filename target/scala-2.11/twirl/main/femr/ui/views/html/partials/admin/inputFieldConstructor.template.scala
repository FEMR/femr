
package femr.ui.views.html.partials.admin

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

object inputFieldConstructor extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template1[helper.FieldElements,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(elements: helper.FieldElements):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*2.1*/("""

"""),format.raw/*4.1*/("""<div class="fieldWrap """),_display_(/*4.24*/if(elements.hasErrors)/*4.46*/ {_display_(Seq[Any](format.raw/*4.48*/("""error""")))}),format.raw/*4.54*/("""">
    <label for=""""),_display_(/*5.18*/elements/*5.26*/.id),format.raw/*5.29*/("""">"""),_display_(/*5.32*/elements/*5.40*/.label),format.raw/*5.46*/("""
        """),_display_(/*6.10*/if(elements.args.get('_isRequired).contains(true))/*6.60*/ {_display_(Seq[Any](format.raw/*6.62*/("""
            """),format.raw/*7.13*/("""<span class="red bold">*</span>
        """)))}),format.raw/*8.10*/("""

    """),format.raw/*10.5*/("""</label>
    <div class="input">
        """),_display_(/*12.10*/elements/*12.18*/.input),format.raw/*12.24*/("""
        """),format.raw/*13.9*/("""<span class="errors">"""),_display_(/*13.31*/elements/*13.39*/.errors.mkString(", ")),format.raw/*13.61*/("""</span>
        <span class="help">"""),_display_(/*14.29*/elements/*14.37*/.infos.mkString(", ")),format.raw/*14.58*/("""</span>
    </div>
</div>
"""))
      }
    }
  }

  def render(elements:helper.FieldElements): play.twirl.api.HtmlFormat.Appendable = apply(elements)

  def f:((helper.FieldElements) => play.twirl.api.HtmlFormat.Appendable) = (elements) => apply(elements)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  DATE: Wed Jan 20 18:29:28 PST 2021
                  SOURCE: /Users/noel/Desktop/CPE/femr/femr/app/femr/ui/views/partials/admin/inputFieldConstructor.scala.html
                  HASH: f57fcdc997c491181ac7091c3197d58253c01f89
                  MATRIX: 1001->1|1127->34|1155->36|1204->59|1234->81|1273->83|1309->89|1355->109|1371->117|1394->120|1423->123|1439->131|1465->137|1501->147|1559->197|1598->199|1638->212|1709->253|1742->259|1811->301|1828->309|1855->315|1891->324|1940->346|1957->354|2000->376|2063->412|2080->420|2122->441
                  LINES: 28->1|33->2|35->4|35->4|35->4|35->4|35->4|36->5|36->5|36->5|36->5|36->5|36->5|37->6|37->6|37->6|38->7|39->8|41->10|43->12|43->12|43->12|44->13|44->13|44->13|44->13|45->14|45->14|45->14
                  -- GENERATED --
              */
          