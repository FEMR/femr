
package femr.ui.views.html.partials.medical.tabs

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

object dynamicTab extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template1[femr.common.models.TabItem,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(genericTab: femr.common.models.TabItem):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {
/*3.2*/import femr.ui.views.html.partials.medical.dynamicField


Seq[Any](format.raw/*1.42*/("""

"""),format.raw/*4.1*/("""
"""),format.raw/*5.86*/("""
"""),format.raw/*6.1*/("""<div class="customWrap" id=""""),_display_(/*6.30*/genericTab/*6.40*/.getName.toLowerCase()),format.raw/*6.62*/("""DynamicTab">

    <div class="customLeft">
    """),_display_(/*9.6*/for(i <- 1 to genericTab.getFields.get(null).size) yield /*9.56*/ {_display_(Seq[Any](format.raw/*9.58*/("""

        """),_display_(/*11.10*/if(i - 1 < genericTab.getLeftColumnSize)/*11.50*/ {_display_(Seq[Any](format.raw/*11.52*/("""
            """),format.raw/*12.13*/("""<div class="customFieldWrap">
            """),_display_(/*13.14*/defining(genericTab.getFields(null).get(i - 1))/*13.61*/ { field =>_display_(Seq[Any](format.raw/*13.72*/("""
                """),_display_(/*14.18*/dynamicField(field.getValue, field.getName, field.getSize, field.getOrder, field.getType, field.getPlaceholder, false, field.getIndex)),format.raw/*14.152*/("""
            """)))}),format.raw/*15.14*/("""
            """),format.raw/*16.13*/("""</div>
        """)))}),format.raw/*17.10*/("""
    """)))}),format.raw/*18.6*/("""
    """),format.raw/*19.5*/("""</div>

    <div class="customRight">
    """),_display_(/*22.6*/for(i <- 1 to genericTab.getFields.get(null).size) yield /*22.56*/ {_display_(Seq[Any](format.raw/*22.58*/("""

        """),_display_(/*24.10*/if(i - 1 >= genericTab.getLeftColumnSize && i - 1 <= (genericTab.getLeftColumnSize + genericTab.getRightColumnSize))/*24.126*/ {_display_(Seq[Any](format.raw/*24.128*/("""

            """),format.raw/*26.13*/("""<div class="customFieldWrap">
            """),_display_(/*27.14*/defining(genericTab.getFields(null).get(i - 1))/*27.61*/ { field =>_display_(Seq[Any](format.raw/*27.72*/("""
                """),_display_(/*28.18*/dynamicField(field.getValue, field.getName, field.getSize, field.getOrder, field.getType, field.getPlaceholder, false, field.getIndex)),format.raw/*28.152*/("""
            """)))}),format.raw/*29.14*/("""
            """),format.raw/*30.13*/("""</div>
        """)))}),format.raw/*31.10*/("""
    """)))}),format.raw/*32.6*/("""
    """),format.raw/*33.5*/("""</div>

    <div class="customBottom">
    """),_display_(/*36.6*/for(i <- 1 to genericTab.getFields.get(null).size) yield /*36.56*/ {_display_(Seq[Any](format.raw/*36.58*/("""

        """),_display_(/*38.10*/if(i - 1 > (genericTab.getLeftColumnSize + genericTab.getRightColumnSize))/*38.84*/ {_display_(Seq[Any](format.raw/*38.86*/("""

            """),format.raw/*40.13*/("""<div class="customFieldWrap">
            """),_display_(/*41.14*/defining(genericTab.getFields(null).get(i - 1))/*41.61*/ { field =>_display_(Seq[Any](format.raw/*41.72*/("""
                """),_display_(/*42.18*/dynamicField(field.getValue, field.getName, field.getSize, field.getOrder, field.getType, field.getPlaceholder, false, field.getIndex)),format.raw/*42.152*/("""
            """)))}),format.raw/*43.14*/("""
            """),format.raw/*44.13*/("""</div>
        """)))}),format.raw/*45.10*/("""
    """)))}),format.raw/*46.6*/("""
    """),format.raw/*47.5*/("""</div>

</div>



"""))
      }
    }
  }

  def render(genericTab:femr.common.models.TabItem): play.twirl.api.HtmlFormat.Appendable = apply(genericTab)

  def f:((femr.common.models.TabItem) => play.twirl.api.HtmlFormat.Appendable) = (genericTab) => apply(genericTab)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  DATE: Tue Oct 27 21:59:18 PDT 2020
                  SOURCE: C:/Users/Noel/Documents/csc402/lemur-femr/femr/app/femr/ui/views/partials/medical/tabs/dynamicTab.scala.html
                  HASH: a305c20087ff38f0b3382fb82b6403cfc76f55e3
                  MATRIX: 1003->1|1116->46|1201->41|1231->103|1260->190|1288->192|1343->221|1361->231|1403->253|1479->304|1544->354|1583->356|1623->369|1672->409|1712->411|1754->425|1825->469|1881->516|1930->527|1976->546|2132->680|2178->695|2220->709|2268->726|2305->733|2338->739|2410->785|2476->835|2516->837|2556->850|2682->966|2723->968|2767->984|2838->1028|2894->1075|2943->1086|2989->1105|3145->1239|3191->1254|3233->1268|3281->1285|3318->1292|3351->1298|3424->1345|3490->1395|3530->1397|3570->1410|3653->1484|3693->1486|3737->1502|3808->1546|3864->1593|3913->1604|3959->1623|4115->1757|4161->1772|4203->1786|4251->1803|4288->1810|4321->1816
                  LINES: 28->1|31->3|34->1|36->4|37->5|38->6|38->6|38->6|38->6|41->9|41->9|41->9|43->11|43->11|43->11|44->12|45->13|45->13|45->13|46->14|46->14|47->15|48->16|49->17|50->18|51->19|54->22|54->22|54->22|56->24|56->24|56->24|58->26|59->27|59->27|59->27|60->28|60->28|61->29|62->30|63->31|64->32|65->33|68->36|68->36|68->36|70->38|70->38|70->38|72->40|73->41|73->41|73->41|74->42|74->42|75->43|76->44|77->45|78->46|79->47
                  -- GENERATED --
              */
          