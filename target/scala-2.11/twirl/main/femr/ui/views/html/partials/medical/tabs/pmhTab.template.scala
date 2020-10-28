
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

object pmhTab extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template1[femr.common.models.TabItem,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(pmhTab: femr.common.models.TabItem):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*1.38*/("""

"""),format.raw/*3.1*/("""<div class="controlWrap hidden" id="pmhControl">
    <div class="form-group">
        <label for="medicalSurgicalHistory">Medical/Surgical History</label>
        """),_display_(/*6.10*/defining(pmhTab.getTabFieldItemByName(null, "medicalSurgicalHistory"))/*6.80*/ { medicalSurgicalHistory =>_display_(Seq[Any](format.raw/*6.108*/("""
            """),format.raw/*7.13*/("""<input type="text" class="hidden" name="tabFieldItems["""),_display_(/*7.68*/medicalSurgicalHistory/*7.90*/.getIndex),format.raw/*7.99*/("""].name" value="medicalSurgicalHistory"/>
            <textarea name="tabFieldItems["""),_display_(/*8.44*/medicalSurgicalHistory/*8.66*/.getIndex),format.raw/*8.75*/("""].value" class="form-control input-sm" >"""),_display_(/*8.116*/medicalSurgicalHistory/*8.138*/.getValue),format.raw/*8.147*/("""</textarea>
        """)))}),format.raw/*9.10*/("""
    """),format.raw/*10.5*/("""</div>

    <div class="form-group">
        <label for="socialHistory">Social History</label>
        """),_display_(/*14.10*/defining(pmhTab.getTabFieldItemByName(null, "socialHistory"))/*14.71*/ { socialHistory =>_display_(Seq[Any](format.raw/*14.90*/("""
            """),format.raw/*15.13*/("""<input type="text" class="hidden" name="tabFieldItems["""),_display_(/*15.68*/socialHistory/*15.81*/.getIndex),format.raw/*15.90*/("""].name" value="socialHistory"/>
            <textarea name="tabFieldItems["""),_display_(/*16.44*/socialHistory/*16.57*/.getIndex),format.raw/*16.66*/("""].value" class="form-control input-sm" >"""),_display_(/*16.107*/socialHistory/*16.120*/.getValue),format.raw/*16.129*/("""</textarea>
        """)))}),format.raw/*17.10*/("""
    """),format.raw/*18.5*/("""</div>

    <div class="form-group">
        <label for="currentMedication">Current Medications</label>
        """),_display_(/*22.10*/defining(pmhTab.getTabFieldItemByName(null, "currentMedication"))/*22.75*/ { currentMedication =>_display_(Seq[Any](format.raw/*22.98*/("""
            """),format.raw/*23.13*/("""<input type="text" class="hidden" name="tabFieldItems["""),_display_(/*23.68*/currentMedication/*23.85*/.getIndex),format.raw/*23.94*/("""].name" value="currentMedication"/>
            <textarea name="tabFieldItems["""),_display_(/*24.44*/currentMedication/*24.61*/.getIndex),format.raw/*24.70*/("""].value" class="form-control input-sm">"""),_display_(/*24.110*/currentMedication/*24.127*/.getValue),format.raw/*24.136*/("""</textarea>
        """)))}),format.raw/*25.10*/("""
    """),format.raw/*26.5*/("""</div>

    <div class="form-group">
        <label for="familyHistory">Family History</label>
        """),_display_(/*30.10*/defining(pmhTab.getTabFieldItemByName(null, "familyHistory"))/*30.71*/ { familyHistory =>_display_(Seq[Any](format.raw/*30.90*/("""
            """),format.raw/*31.13*/("""<input type="text" class="hidden" name="tabFieldItems["""),_display_(/*31.68*/familyHistory/*31.81*/.getIndex),format.raw/*31.90*/("""].name" value="familyHistory"/>
            <textarea name="tabFieldItems["""),_display_(/*32.44*/familyHistory/*32.57*/.getIndex),format.raw/*32.66*/("""].value" class="form-control input-sm">"""),_display_(/*32.106*/familyHistory/*32.119*/.getValue),format.raw/*32.128*/("""</textarea>
        """)))}),format.raw/*33.10*/("""
    """),format.raw/*34.5*/("""</div>
</div>"""))
      }
    }
  }

  def render(pmhTab:femr.common.models.TabItem): play.twirl.api.HtmlFormat.Appendable = apply(pmhTab)

  def f:((femr.common.models.TabItem) => play.twirl.api.HtmlFormat.Appendable) = (pmhTab) => apply(pmhTab)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  DATE: Tue Oct 27 21:59:18 PDT 2020
                  SOURCE: C:/Users/Noel/Documents/csc402/lemur-femr/femr/app/femr/ui/views/partials/medical/tabs/pmhTab.scala.html
                  HASH: 3d80089eef89bb0f0a668d1391e79ea0ce7e8951
                  MATRIX: 999->1|1130->37|1160->41|1353->208|1431->278|1497->306|1538->320|1619->375|1649->397|1678->406|1789->491|1819->513|1848->522|1916->563|1947->585|1977->594|2029->616|2062->622|2197->730|2267->791|2324->810|2366->824|2448->879|2470->892|2500->901|2603->977|2625->990|2655->999|2724->1040|2747->1053|2778->1062|2831->1084|2864->1090|3008->1207|3082->1272|3143->1295|3185->1309|3267->1364|3293->1381|3323->1390|3430->1470|3456->1487|3486->1496|3554->1536|3581->1553|3612->1562|3665->1584|3698->1590|3833->1698|3903->1759|3960->1778|4002->1792|4084->1847|4106->1860|4136->1869|4239->1945|4261->1958|4291->1967|4359->2007|4382->2020|4413->2029|4466->2051|4499->2057
                  LINES: 28->1|33->1|35->3|38->6|38->6|38->6|39->7|39->7|39->7|39->7|40->8|40->8|40->8|40->8|40->8|40->8|41->9|42->10|46->14|46->14|46->14|47->15|47->15|47->15|47->15|48->16|48->16|48->16|48->16|48->16|48->16|49->17|50->18|54->22|54->22|54->22|55->23|55->23|55->23|55->23|56->24|56->24|56->24|56->24|56->24|56->24|57->25|58->26|62->30|62->30|62->30|63->31|63->31|63->31|63->31|64->32|64->32|64->32|64->32|64->32|64->32|65->33|66->34
                  -- GENERATED --
              */
          