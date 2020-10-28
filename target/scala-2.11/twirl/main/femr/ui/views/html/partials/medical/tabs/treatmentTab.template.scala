
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


Seq[Any](format.raw/*1.236*/("""

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
                  DATE: Tue Oct 27 21:59:18 PDT 2020
                  SOURCE: C:/Users/Noel/Documents/csc402/lemur-femr/femr/app/femr/ui/views/partials/medical/tabs/treatmentTab.scala.html
                  HASH: 304a34175a8c7f3d6af618b536303ef6aa0d3918
                  MATRIX: 1138->1|1445->240|1539->235|1569->305|1597->307|1770->454|1842->518|1895->534|1936->548|1990->576|2008->586|2037->595|2173->704|2192->714|2222->723|2290->763|2310->773|2341->782|2394->804|2431->814|2659->1015|2699->1039|2739->1041|2789->1063|2931->1178|2947->1185|2977->1193|3179->1367|3196->1374|3226->1382|3396->1521|3442->1539|5021->3091|5065->3119|5105->3121|5157->3146|5237->3205|5287->3224|5335->3244|5420->3302|5498->3359|5544->3377|6233->4039|6316->4113|6369->4128|6411->4142|6466->4170|6484->4179|6514->4188|6660->4307|6678->4316|6708->4325|6831->4420|6850->4429|6881->4438|6934->4460|6967->4466|7095->4567|7171->4634|7224->4649|7266->4663|7321->4691|7339->4700|7369->4709|7508->4821|7526->4830|7556->4839|7680->4935|7699->4944|7730->4953|7783->4975|7816->4981
                  LINES: 28->1|31->3|34->1|36->4|37->5|40->8|40->8|40->8|41->9|41->9|41->9|41->9|42->10|42->10|42->10|42->10|42->10|42->10|43->11|44->12|50->18|50->18|50->18|51->19|52->20|52->20|52->20|55->23|55->23|55->23|58->26|59->27|87->55|87->55|87->55|89->57|89->57|90->58|92->60|93->61|93->61|94->62|109->77|109->77|109->77|110->78|110->78|110->78|110->78|111->79|111->79|111->79|111->79|111->79|111->79|112->80|113->81|116->84|116->84|116->84|117->85|117->85|117->85|117->85|118->86|118->86|118->86|118->86|118->86|118->86|119->87|120->88
                  -- GENERATED --
              */
          