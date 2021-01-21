
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

object prescriptionRow extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template3[List[femr.common.models.MedicationAdministrationItem],Integer,femr.common.models.PrescriptionItem,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/( medicationAdministrationItems: List[femr.common.models.MedicationAdministrationItem],
   index: Integer,
   script: femr.common.models.PrescriptionItem
):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {
/*6.2*/import femr.ui.views.html.partials.helpers.outputStringOrNA


Seq[Any](format.raw/*5.1*/("""
"""),format.raw/*7.1*/("""
"""),_display_(/*8.2*/if( script == null )/*8.22*/ {_display_(Seq[Any](format.raw/*8.24*/("""
"""),format.raw/*9.95*/("""
"""),format.raw/*10.103*/("""

    """),format.raw/*12.5*/("""<div class="prescription-grid prescriptionRow prescriptionInput">

        <input class='medicationID' name='prescriptions["""),_display_(/*14.58*/index),format.raw/*14.63*/("""].medicationID' type='hidden' />

        <span class="prescription-field prescriptionName">
            <input type='text' name="prescriptions["""),_display_(/*17.53*/index),format.raw/*17.58*/("""].medicationName" class='medicationName form-control input-sm'/>
        </span>
        <span class="prescription-field prescriptionAdministration">
            """),format.raw/*20.86*/("""
            """),format.raw/*21.13*/("""<select class="administrationName form-control" name="prescriptions["""),_display_(/*21.82*/index),format.raw/*21.87*/("""].administrationID">
                <option value="-1">- Select -</option>
                """),_display_(/*23.18*/for(item <- medicationAdministrationItems) yield /*23.60*/ {_display_(Seq[Any](format.raw/*23.62*/("""
                    """),format.raw/*24.21*/("""<option value=""""),_display_(/*24.37*/item/*24.41*/.getId),format.raw/*24.47*/("""" data-modifier=""""),_display_(/*24.65*/item/*24.69*/.getDailyModifier),format.raw/*24.86*/("""">"""),_display_(/*24.89*/item/*24.93*/.getName),format.raw/*24.101*/("""</option>
                """)))}),format.raw/*25.18*/("""
            """),format.raw/*26.13*/("""</select>
        </span>
        <span class="prescription-field prescriptionAdministrationDays">
            <input type="number" class="form-control input-sm" />
        </span>
        <span class="prescription-field prescriptionAmount">
            <input name="prescriptions["""),_display_(/*32.41*/index),format.raw/*32.46*/("""].amount" type="number" class="form-control input-sm"/>
        </span>
    </div>

""")))}/*36.2*/else/*36.6*/{_display_(Seq[Any](format.raw/*36.7*/("""
"""),format.raw/*37.111*/("""
    """),format.raw/*38.5*/("""<div class="prescription-grid prescriptionRow prescriptionInput">

        <input class='medicationID' value='"""),_display_(/*40.45*/script/*40.51*/.getMedicationID),format.raw/*40.67*/("""' type='hidden' />

        <span class="prescription-field prescriptionName">
            <input type='text' class='medicationName form-control input-sm' value=""""),_display_(/*43.85*/script/*43.91*/.getMedicationItem.getFullName),format.raw/*43.121*/("""" readonly />
        </span>
        <span class="prescription-field prescriptionAdministration">

            <select class="administrationName form-control" readonly disabled>
                <option value="-1">- Select -</option>
                """),_display_(/*49.18*/for(item <- medicationAdministrationItems) yield /*49.60*/ {_display_(Seq[Any](format.raw/*49.62*/("""
                    """),format.raw/*50.21*/("""<option value=""""),_display_(/*50.37*/item/*50.41*/.getId),format.raw/*50.47*/("""" data-modifier=""""),_display_(/*50.65*/item/*50.69*/.getDailyModifier),format.raw/*50.86*/("""" """),_display_(/*50.89*/if( item.getId == script.getAdministrationID )/*50.135*/{_display_(Seq[Any](format.raw/*50.136*/(""" """),format.raw/*50.137*/("""selected="selected" """)))}),format.raw/*50.158*/(""">"""),_display_(/*50.160*/item/*50.164*/.getName),format.raw/*50.172*/("""</option>
                """)))}),format.raw/*51.18*/("""
            """),format.raw/*52.13*/("""</select>
        </span>
        <span class="prescription-field prescriptionAdministrationDays">
            <input type="number" class="form-control input-sm" readonly />
        </span>
        <span class="prescription-field prescriptionAmount">
            <input type="number" class="form-control input-sm" value='"""),_display_(/*58.72*/script/*58.78*/.getAmountWithNull),format.raw/*58.96*/("""' readonly />
        </span>
    </div>
""")))}))
      }
    }
  }

  def render(medicationAdministrationItems:List[femr.common.models.MedicationAdministrationItem],index:Integer,script:femr.common.models.PrescriptionItem): play.twirl.api.HtmlFormat.Appendable = apply(medicationAdministrationItems,index,script)

  def f:((List[femr.common.models.MedicationAdministrationItem],Integer,femr.common.models.PrescriptionItem) => play.twirl.api.HtmlFormat.Appendable) = (medicationAdministrationItems,index,script) => apply(medicationAdministrationItems,index,script)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  DATE: Wed Jan 20 18:29:29 PST 2021
                  SOURCE: /Users/noel/Desktop/CPE/femr/femr/app/femr/ui/views/partials/medical/tabs/prescriptionRow.scala.html
                  HASH: 0725befe5029acb0617f4efede0db8beacd78d52
                  MATRIX: 1079->1|1307->159|1395->157|1422->219|1449->221|1477->241|1516->243|1544->338|1574->441|1607->447|1758->571|1784->576|1956->721|1982->726|2172->961|2213->974|2309->1043|2335->1048|2455->1141|2513->1183|2553->1185|2602->1206|2645->1222|2658->1226|2685->1232|2730->1250|2743->1254|2781->1271|2811->1274|2824->1278|2854->1286|2912->1313|2953->1326|3262->1608|3288->1613|3391->1698|3403->1702|3441->1703|3471->1814|3503->1819|3641->1930|3656->1936|3693->1952|3883->2115|3898->2121|3950->2151|4228->2402|4286->2444|4326->2446|4375->2467|4418->2483|4431->2487|4458->2493|4503->2511|4516->2515|4554->2532|4584->2535|4640->2581|4680->2582|4710->2583|4763->2604|4793->2606|4807->2610|4837->2618|4895->2645|4936->2658|5285->2980|5300->2986|5339->3004
                  LINES: 28->1|34->6|37->5|38->7|39->8|39->8|39->8|40->9|41->10|43->12|45->14|45->14|48->17|48->17|51->20|52->21|52->21|52->21|54->23|54->23|54->23|55->24|55->24|55->24|55->24|55->24|55->24|55->24|55->24|55->24|55->24|56->25|57->26|63->32|63->32|67->36|67->36|67->36|68->37|69->38|71->40|71->40|71->40|74->43|74->43|74->43|80->49|80->49|80->49|81->50|81->50|81->50|81->50|81->50|81->50|81->50|81->50|81->50|81->50|81->50|81->50|81->50|81->50|81->50|82->51|83->52|89->58|89->58|89->58
                  -- GENERATED --
              */
          