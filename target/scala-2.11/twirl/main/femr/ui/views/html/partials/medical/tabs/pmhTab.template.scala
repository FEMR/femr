
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


Seq[Any](format.raw/*2.1*/("""
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
                  DATE: Wed Jan 20 18:29:28 PST 2021
                  SOURCE: /Users/noel/Desktop/CPE/femr/femr/app/femr/ui/views/partials/medical/tabs/pmhTab.scala.html
                  HASH: d0e64fa7188cb9f357ef0c01a6dec15c037be394
                  MATRIX: 999->1|1129->38|1156->39|1346->203|1424->273|1490->301|1530->314|1611->369|1641->391|1670->400|1780->484|1810->506|1839->515|1907->556|1938->578|1968->587|2019->608|2051->613|2182->717|2252->778|2309->797|2350->810|2432->865|2454->878|2484->887|2586->962|2608->975|2638->984|2707->1025|2730->1038|2761->1047|2813->1068|2845->1073|2985->1186|3059->1251|3120->1274|3161->1287|3243->1342|3269->1359|3299->1368|3405->1447|3431->1464|3461->1473|3529->1513|3556->1530|3587->1539|3639->1560|3671->1565|3802->1669|3872->1730|3929->1749|3970->1762|4052->1817|4074->1830|4104->1839|4206->1914|4228->1927|4258->1936|4326->1976|4349->1989|4380->1998|4432->2019|4464->2024
                  LINES: 28->1|33->2|34->3|37->6|37->6|37->6|38->7|38->7|38->7|38->7|39->8|39->8|39->8|39->8|39->8|39->8|40->9|41->10|45->14|45->14|45->14|46->15|46->15|46->15|46->15|47->16|47->16|47->16|47->16|47->16|47->16|48->17|49->18|53->22|53->22|53->22|54->23|54->23|54->23|54->23|55->24|55->24|55->24|55->24|55->24|55->24|56->25|57->26|61->30|61->30|61->30|62->31|62->31|62->31|62->31|63->32|63->32|63->32|63->32|63->32|63->32|64->33|65->34
                  -- GENERATED --
              */
          