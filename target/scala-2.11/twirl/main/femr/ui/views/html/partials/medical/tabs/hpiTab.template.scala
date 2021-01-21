
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


Seq[Any](format.raw/*2.1*/("""
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
                  DATE: Wed Jan 20 18:29:29 PST 2021
                  SOURCE: /Users/noel/Desktop/CPE/femr/femr/app/femr/ui/views/partials/medical/tabs/hpiTab.scala.html
                  HASH: 0c2317adbd010e5ce81a2755f3edffd06dce7e62
                  MATRIX: 1040->1|1225->116|1312->114|1339->175|1367->177|1440->225|1478->255|1517->257|1553->347|1589->356|1665->420|1706->433|1834->553|1875->567|1929->605|1969->607|2014->624|2108->691|2143->705|2218->749|2260->780|2301->793|2439->901|2472->953|2504->959|2544->990|2584->992|2621->1002|2682->1042|2706->1049|2719->1054|2758->1055|2795->1065|2849->1103|2889->1105|2930->1119|3001->1169|3042->1179|3078->1185|3108->1188
                  LINES: 28->1|31->3|34->2|35->4|37->6|39->8|39->8|39->8|40->9|41->10|42->11|43->12|47->16|48->17|48->17|48->17|49->18|50->19|50->19|52->21|54->23|55->24|59->28|60->29|61->30|61->30|61->30|62->31|62->31|63->32|63->32|63->32|64->33|64->33|64->33|65->34|65->34|66->35|67->36|70->39
                  -- GENERATED --
              */
          