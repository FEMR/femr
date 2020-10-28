
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


Seq[Any](format.raw/*4.2*/("""

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
                  DATE: Tue Oct 27 21:59:18 PDT 2020
                  SOURCE: C:/Users/Noel/Documents/csc402/lemur-femr/femr/app/femr/ui/views/partials/medical/tabs/prescriptionRow.scala.html
                  HASH: e6f5579fbe6600cd0196c8af93813d8e1b3f6522
                  MATRIX: 1079->1|1310->164|1398->159|1428->225|1456->228|1484->248|1523->250|1552->346|1583->450|1618->458|1771->584|1797->589|1972->737|1998->742|2191->980|2233->994|2329->1063|2355->1068|2477->1163|2535->1205|2575->1207|2625->1229|2668->1245|2681->1249|2708->1255|2753->1273|2766->1277|2804->1294|2834->1297|2847->1301|2877->1309|2936->1337|2978->1351|3293->1639|3319->1644|3426->1733|3438->1737|3476->1738|3507->1850|3540->1856|3680->1969|3695->1975|3732->1991|3925->2157|3940->2163|3992->2193|4276->2450|4334->2492|4374->2494|4424->2516|4467->2532|4480->2536|4507->2542|4552->2560|4565->2564|4603->2581|4633->2584|4689->2630|4729->2631|4759->2632|4812->2653|4842->2655|4856->2659|4886->2667|4945->2695|4987->2709|5342->3037|5357->3043|5396->3061
                  LINES: 28->1|34->6|37->4|39->7|40->8|40->8|40->8|41->9|42->10|44->12|46->14|46->14|49->17|49->17|52->20|53->21|53->21|53->21|55->23|55->23|55->23|56->24|56->24|56->24|56->24|56->24|56->24|56->24|56->24|56->24|56->24|57->25|58->26|64->32|64->32|68->36|68->36|68->36|69->37|70->38|72->40|72->40|72->40|75->43|75->43|75->43|81->49|81->49|81->49|82->50|82->50|82->50|82->50|82->50|82->50|82->50|82->50|82->50|82->50|82->50|82->50|82->50|82->50|82->50|83->51|84->52|90->58|90->58|90->58
                  -- GENERATED --
              */
          