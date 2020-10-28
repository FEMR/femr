
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

object hpiTab extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template3[femr.common.models.TabItem,java.lang.Boolean,List[java.lang.String],play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(hpiTab: femr.common.models.TabItem, isConsolidated: java.lang.Boolean, chiefComplaints: List[java.lang.String]):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {
/*3.2*/import femr.ui.views.html.partials.medical.tabs.hpi_fields


Seq[Any](format.raw/*1.114*/("""

"""),format.raw/*4.1*/("""

"""),format.raw/*6.1*/("""<div class="controlWrap" id="hpiControl">

    """),_display_(/*8.6*/if(chiefComplaints.size() > 1)/*8.36*/ {_display_(Seq[Any](format.raw/*8.38*/("""
        """),format.raw/*9.90*/("""
        """),format.raw/*10.9*/("""<div id="chiefComplaintSliderWrap">
            """),format.raw/*11.29*/("""
            """),format.raw/*12.13*/("""<div id="chiefComplaintLeftArrow">
                <span> < </span>
            </div>

            """),format.raw/*16.33*/("""
            """),_display_(/*17.14*/for(chiefComplaint <- chiefComplaints) yield /*17.52*/ {_display_(Seq[Any](format.raw/*17.54*/("""
                """),format.raw/*18.17*/("""<div class="chiefComplaintText hidden">
                    <span>"""),_display_(/*19.28*/chiefComplaint),format.raw/*19.42*/("""</span>
                </div>
            """)))}),format.raw/*21.14*/("""

            """),format.raw/*23.30*/("""
            """),format.raw/*24.13*/("""<div id="chiefComplaintRightArrow">
                <span> > </span>
            </div>
        </div>
    """)))}),format.raw/*28.6*/("""
    """),format.raw/*29.52*/("""
    """),_display_(/*30.6*/if(chiefComplaints.size() == 0)/*30.37*/ {_display_(Seq[Any](format.raw/*30.39*/("""
        """),_display_(/*31.10*/hpi_fields(hpiTab, isConsolidated, null)),format.raw/*31.50*/("""
    """)))}/*32.7*/else/*32.12*/{_display_(Seq[Any](format.raw/*32.13*/("""
        """),_display_(/*33.10*/for(chiefComplaint <- chiefComplaints) yield /*33.48*/ {_display_(Seq[Any](format.raw/*33.50*/("""
            """),_display_(/*34.14*/hpi_fields(hpiTab, isConsolidated, chiefComplaint)),format.raw/*34.64*/("""
        """)))}),format.raw/*35.10*/("""
    """)))}),format.raw/*36.6*/("""


"""),format.raw/*39.1*/("""</div>
"""))
      }
    }
  }

  def render(hpiTab:femr.common.models.TabItem,isConsolidated:java.lang.Boolean,chiefComplaints:List[java.lang.String]): play.twirl.api.HtmlFormat.Appendable = apply(hpiTab,isConsolidated,chiefComplaints)

  def f:((femr.common.models.TabItem,java.lang.Boolean,List[java.lang.String]) => play.twirl.api.HtmlFormat.Appendable) = (hpiTab,isConsolidated,chiefComplaints) => apply(hpiTab,isConsolidated,chiefComplaints)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  DATE: Tue Oct 27 21:59:18 PDT 2020
                  SOURCE: C:/Users/Noel/Documents/csc402/lemur-femr/femr/app/femr/ui/views/partials/medical/tabs/hpiTab.scala.html
                  HASH: 71772e03410fbc3678f89a2d5a8b61326b9386bb
                  MATRIX: 1040->1|1225->118|1314->113|1344->178|1374->182|1449->232|1487->262|1526->264|1563->355|1600->365|1677->430|1719->444|1851->568|1893->583|1947->621|1987->623|2033->641|2128->709|2163->723|2240->769|2284->802|2326->816|2468->928|2502->981|2535->988|2575->1019|2615->1021|2653->1032|2714->1072|2739->1080|2752->1085|2791->1086|2829->1097|2883->1135|2923->1137|2965->1152|3036->1202|3078->1213|3115->1220|3148->1226
                  LINES: 28->1|31->3|34->1|36->4|38->6|40->8|40->8|40->8|41->9|42->10|43->11|44->12|48->16|49->17|49->17|49->17|50->18|51->19|51->19|53->21|55->23|56->24|60->28|61->29|62->30|62->30|62->30|63->31|63->31|64->32|64->32|64->32|65->33|65->33|65->33|66->34|66->34|67->35|68->36|71->39
                  -- GENERATED --
              */
          