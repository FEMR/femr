
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


Seq[Any](format.raw/*1.34*/("""


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
                  DATE: Tue Oct 27 21:59:18 PDT 2020
                  SOURCE: C:/Users/Noel/Documents/csc402/lemur-femr/femr/app/femr/ui/views/partials/admin/inputFieldConstructor.scala.html
                  HASH: 9d12842b2d691e1653521ecb94bad0b3b1176baa
                  MATRIX: 1001->1|1128->33|1160->39|1209->62|1239->84|1278->86|1314->92|1361->113|1377->121|1400->124|1429->127|1445->135|1471->141|1508->152|1566->202|1605->204|1646->218|1718->260|1753->268|1824->312|1841->320|1868->326|1905->336|1954->358|1971->366|2014->388|2078->425|2095->433|2137->454
                  LINES: 28->1|33->1|36->4|36->4|36->4|36->4|36->4|37->5|37->5|37->5|37->5|37->5|37->5|38->6|38->6|38->6|39->7|40->8|42->10|44->12|44->12|44->12|45->13|45->13|45->13|45->13|46->14|46->14|46->14
                  -- GENERATED --
              */
          