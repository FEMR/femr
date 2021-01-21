
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


Seq[Any](format.raw/*2.1*/("""
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
                  DATE: Wed Jan 20 18:29:28 PST 2021
                  SOURCE: /Users/noel/Desktop/CPE/femr/femr/app/femr/ui/views/partials/medical/tabs/dynamicTab.scala.html
                  HASH: 90d4d3f1737610633e62495a54a69da6d9a870ad
                  MATRIX: 1003->1|1116->44|1200->42|1227->100|1255->186|1282->187|1337->216|1355->226|1397->248|1470->296|1535->346|1574->348|1612->359|1661->399|1701->401|1742->414|1812->457|1868->504|1917->515|1962->533|2118->667|2163->681|2204->694|2251->710|2287->716|2319->721|2388->764|2454->814|2494->816|2532->827|2658->943|2699->945|2741->959|2811->1002|2867->1049|2916->1060|2961->1078|3117->1212|3162->1226|3203->1239|3250->1255|3286->1261|3318->1266|3388->1310|3454->1360|3494->1362|3532->1373|3615->1447|3655->1449|3697->1463|3767->1506|3823->1553|3872->1564|3917->1582|4073->1716|4118->1730|4159->1743|4206->1759|4242->1765|4274->1770
                  LINES: 28->1|31->3|34->2|35->4|36->5|37->6|37->6|37->6|37->6|40->9|40->9|40->9|42->11|42->11|42->11|43->12|44->13|44->13|44->13|45->14|45->14|46->15|47->16|48->17|49->18|50->19|53->22|53->22|53->22|55->24|55->24|55->24|57->26|58->27|58->27|58->27|59->28|59->28|60->29|61->30|62->31|63->32|64->33|67->36|67->36|67->36|69->38|69->38|69->38|71->40|72->41|72->41|72->41|73->42|73->42|74->43|75->44|76->45|77->46|78->47
                  -- GENERATED --
              */
          