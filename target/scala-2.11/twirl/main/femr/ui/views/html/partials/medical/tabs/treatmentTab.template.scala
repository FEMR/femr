
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

object treatmentTab extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template4[femr.common.models.TabItem,List[femr.common.models.PrescriptionItem],List[femr.common.models.MedicationAdministrationItem],List[femr.common.models.ProblemItem],play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(treatmentTab: femr.common.models.TabItem, prescriptions: List[femr.common.models.PrescriptionItem], medicationAdministrationItems: List[femr.common.models.MedicationAdministrationItem], problems: List[femr.common.models.ProblemItem]):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {
/*3.2*/import femr.ui.views.html.partials.medical.tabs.prescriptionRow


Seq[Any](format.raw/*2.1*/("""
"""),format.raw/*4.1*/("""
"""),format.raw/*5.1*/("""<div class="controlWrap hidden" id="treatmentControl">
    <div class="form-group">
        <label for="assessment">Assessment</label>
        """),_display_(/*8.10*/defining(treatmentTab.getTabFieldItemByName(null, "assessment"))/*8.74*/ { assessment =>_display_(Seq[Any](format.raw/*8.90*/("""
            """),format.raw/*9.13*/("""<input name="tabFieldItems["""),_display_(/*9.41*/assessment/*9.51*/.getIndex),format.raw/*9.60*/("""].name" type="text" class="hidden" value="assessment"/>
            <textarea rows="3" name="tabFieldItems["""),_display_(/*10.53*/assessment/*10.63*/.getIndex),format.raw/*10.72*/("""].value" class="form-control input-sm">"""),_display_(/*10.112*/assessment/*10.122*/.getValue),format.raw/*10.131*/("""</textarea>
        """)))}),format.raw/*11.10*/("""
        """),format.raw/*12.9*/("""</div>

    <div class="form-group">
        <label for="problem">Diagnosis</label>
        <div class="row">
            <div class="col-xs-10 col-sm-10 col-md-10 problemWrap">
                """),_display_(/*18.18*/for(problem <- problems) yield /*18.42*/ {_display_(Seq[Any](format.raw/*18.44*/("""
                    """),format.raw/*19.21*/("""<div class="input-group">
                            <input type="text" class="form-control oldProblems" value=""""),_display_(/*20.89*/problem/*20.96*/.getName),format.raw/*20.104*/("""" readonly/>

                        <span class="input-group-btn">
                            <button type="button" class="btn btn-danger deleteProblem" data-problem=""""),_display_(/*23.103*/problem/*23.110*/.getName),format.raw/*23.118*/(""""><span class="glyphicon glyphicon-remove"></span></button>
                        </span>
                    </div>
                """)))}),format.raw/*26.18*/("""
                """),format.raw/*27.17*/("""<div class="problem">
                    <input name="problems[0].name" type="text" class="form-control newProblems"/>
                </div>
            </div>
            <div class="col-xs-2 col-sm-2 col-md-2">
                <div class="row">
                    <div class="col-xs-12 col-sm-12 col-md-12">
                        <button id="addProblemButton" class="btn addSubtractBtn pull-right" type="button"><span class="glyphicon glyphicon-plus"></span></button>
                    </div>
                </div>
                <div class="row">
                    <div class="col-xs-12 col-sm-12 col-md-12">
                        <button id="subtractProblemButton" class="btn addSubtractBtn pull-right" type="button"><span class="glyphicon glyphicon-minus"></span></button>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="form-group">
        <div class="row">
            <div class="col-xs-10 col-sm-10 col-md-10 prescriptionWrap">
                <div class="prescription-grid prescriptionHeader">
                    <label class="prescription-field prescriptionName" for="prescription">Prescriptions</label>
                    <label class="prescription-field prescriptionAdministration">Administration</label>
                    <label class="prescription-field prescriptionAdministrationDays">Days</label>
                    <label class="prescription-field prescriptionAmount">Amount</label>
                </div>

                """),_display_(/*55.18*/for(script <- prescriptions) yield /*55.46*/ {_display_(Seq[Any](format.raw/*55.48*/("""

                    """),_display_(/*57.22*/prescriptionRow( medicationAdministrationItems, 0, script )),format.raw/*57.81*/("""
                """)))}),format.raw/*58.18*/("""

                """),format.raw/*60.17*/("""<div class="newPrescriptionsContainer">
                """),_display_(/*61.18*/prescriptionRow( medicationAdministrationItems, 0, null )),format.raw/*61.75*/("""
                """),format.raw/*62.17*/("""</div>

            </div>
            <div class="col-xs-2 col-sm-2 col-md-2">
                <div class="row">
                    <button id="addPrescriptionButton" class="btn addSubtractBtn" type="button"><span class="glyphicon glyphicon-plus"></span></button>
                </div>
                <div class="row">
                    <button id="subtractPrescriptionButton" class="btn addSubtractBtn" type="button"><span class="glyphicon glyphicon-minus"></span></button>
                </div>
            </div>
        </div>
    </div>
    <div class="form-group">
        <label for="treatment">Procedure/Counseling</label>
        """),_display_(/*77.10*/defining(treatmentTab.getTabFieldItemByName(null, "procedure_counseling"))/*77.84*/ { treatment =>_display_(Seq[Any](format.raw/*77.99*/("""
            """),format.raw/*78.13*/("""<input name="tabFieldItems["""),_display_(/*78.41*/treatment/*78.50*/.getIndex),format.raw/*78.59*/("""].name" type="text" class="hidden" value="procedure_counseling"/>
            <textarea rows="3" name="tabFieldItems["""),_display_(/*79.53*/treatment/*79.62*/.getIndex),format.raw/*79.71*/("""].value" class="form-control input-sm" placeholder="Ex: Suturing, Ultrasound, Splint, etc...">"""),_display_(/*79.166*/treatment/*79.175*/.getValue),format.raw/*79.184*/("""</textarea>
        """)))}),format.raw/*80.10*/("""
    """),format.raw/*81.5*/("""</div>
    <div class="form-group">
        <label for="treatment">Pharmacy Note</label>
        """),_display_(/*84.10*/defining(treatmentTab.getTabFieldItemByName(null, "pharmacy_note"))/*84.77*/ { treatment =>_display_(Seq[Any](format.raw/*84.92*/("""
            """),format.raw/*85.13*/("""<input name="tabFieldItems["""),_display_(/*85.41*/treatment/*85.50*/.getIndex),format.raw/*85.59*/("""].name" type="text" class="hidden" value="pharmacy_note"/>
            <textarea rows="3" name="tabFieldItems["""),_display_(/*86.53*/treatment/*86.62*/.getIndex),format.raw/*86.71*/("""].value" class="form-control input-sm" placeholder="Ex: Three times/day for two weeks, etc...">"""),_display_(/*86.167*/treatment/*86.176*/.getValue),format.raw/*86.185*/("""</textarea>
        """)))}),format.raw/*87.10*/("""
    """),format.raw/*88.5*/("""</div>
</div>
"""))
      }
    }
  }

  def render(treatmentTab:femr.common.models.TabItem,prescriptions:List[femr.common.models.PrescriptionItem],medicationAdministrationItems:List[femr.common.models.MedicationAdministrationItem],problems:List[femr.common.models.ProblemItem]): play.twirl.api.HtmlFormat.Appendable = apply(treatmentTab,prescriptions,medicationAdministrationItems,problems)

  def f:((femr.common.models.TabItem,List[femr.common.models.PrescriptionItem],List[femr.common.models.MedicationAdministrationItem],List[femr.common.models.ProblemItem]) => play.twirl.api.HtmlFormat.Appendable) = (treatmentTab,prescriptions,medicationAdministrationItems,problems) => apply(treatmentTab,prescriptions,medicationAdministrationItems,problems)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  DATE: Wed Jan 20 18:29:29 PST 2021
                  SOURCE: /Users/noel/Desktop/CPE/femr/femr/app/femr/ui/views/partials/medical/tabs/treatmentTab.scala.html
                  HASH: 5ef13fd3da30fc0e4b3ae43c2dc11855c8122246
                  MATRIX: 1138->1|1445->238|1537->236|1564->302|1591->303|1761->447|1833->511|1886->527|1926->540|1980->568|1998->578|2027->587|2162->695|2181->705|2211->714|2279->754|2299->764|2330->773|2382->794|2418->803|2640->998|2680->1022|2720->1024|2769->1045|2910->1159|2926->1166|2956->1174|3155->1345|3172->1352|3202->1360|3369->1496|3414->1513|4965->3037|5009->3065|5049->3067|5099->3090|5179->3149|5228->3167|5274->3185|5358->3242|5436->3299|5481->3316|6155->3963|6238->4037|6291->4052|6332->4065|6387->4093|6405->4102|6435->4111|6580->4229|6598->4238|6628->4247|6751->4342|6770->4351|6801->4360|6853->4381|6885->4386|7010->4484|7086->4551|7139->4566|7180->4579|7235->4607|7253->4616|7283->4625|7421->4736|7439->4745|7469->4754|7593->4850|7612->4859|7643->4868|7695->4889|7727->4894
                  LINES: 28->1|31->3|34->2|35->4|36->5|39->8|39->8|39->8|40->9|40->9|40->9|40->9|41->10|41->10|41->10|41->10|41->10|41->10|42->11|43->12|49->18|49->18|49->18|50->19|51->20|51->20|51->20|54->23|54->23|54->23|57->26|58->27|86->55|86->55|86->55|88->57|88->57|89->58|91->60|92->61|92->61|93->62|108->77|108->77|108->77|109->78|109->78|109->78|109->78|110->79|110->79|110->79|110->79|110->79|110->79|111->80|112->81|115->84|115->84|115->84|116->85|116->85|116->85|116->85|117->86|117->86|117->86|117->86|117->86|117->86|118->87|119->88
                  -- GENERATED --
              */
          