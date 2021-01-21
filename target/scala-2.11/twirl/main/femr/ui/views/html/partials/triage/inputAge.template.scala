
package femr.ui.views.html.partials.triage

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

object inputAge extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template6[String,String,String,String,String,femr.common.models.PatientItem,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(label: String, field1: String, forInput1: String, field2: String, forInput2: String, value: femr.common.models.PatientItem):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {

def /*2.6*/inputBlock/*2.16*/:play.twirl.api.HtmlFormat.Appendable = {_display_(

Seq[Any](format.raw/*2.20*/("""
        """),format.raw/*3.9*/("""<div class="generalInfoInput">
            <label for=""""),_display_(/*4.26*/label),format.raw/*4.31*/("""">Years</label>
            """),_display_(/*5.14*/if(value.getAge == null)/*5.38*/ {_display_(Seq[Any](format.raw/*5.40*/("""
                """),format.raw/*6.17*/("""<input type="text" class="age-input fInput" id=""""),_display_(/*6.66*/forInput1),format.raw/*6.75*/("""" name=""""),_display_(/*6.84*/forInput1),format.raw/*6.93*/("""" placeholder=""""),_display_(/*6.109*/field1),format.raw/*6.115*/("""">
                <input type="text" class="age-input fInput" id=""""),_display_(/*7.66*/forInput2),format.raw/*7.75*/("""" name=""""),_display_(/*7.84*/forInput2),format.raw/*7.93*/("""" placeholder=""""),_display_(/*7.109*/field2),format.raw/*7.115*/("""">
            """)))}/*8.15*/else/*8.20*/{_display_(Seq[Any](format.raw/*8.21*/("""
                """),format.raw/*9.17*/("""<input type="text" class="fInput" id="readOnlyAge" name="readOnlyAge" value=""""),_display_(/*9.95*/value/*9.100*/.getAge),format.raw/*9.107*/("""" readonly/>
                <input type="text" class="hidden" id=""""),_display_(/*10.56*/forInput1),format.raw/*10.65*/("""" value=""""),_display_(/*10.75*/value/*10.80*/.getYearsOld),format.raw/*10.92*/(""""/>
                <input type="text" class="hidden" id=""""),_display_(/*11.56*/forInput2),format.raw/*11.65*/("""" value=""""),_display_(/*11.75*/value/*11.80*/.getMonthsOld),format.raw/*11.93*/(""""/>
            """)))}),format.raw/*12.14*/("""
        """),format.raw/*13.9*/("""</div>
    """)))};
Seq[Any](format.raw/*2.1*/("""    """),format.raw/*14.6*/("""
"""),_display_(/*15.2*/inputBlock),format.raw/*15.12*/("""
"""))
      }
    }
  }

  def render(label:String,field1:String,forInput1:String,field2:String,forInput2:String,value:femr.common.models.PatientItem): play.twirl.api.HtmlFormat.Appendable = apply(label,field1,forInput1,field2,forInput2,value)

  def f:((String,String,String,String,String,femr.common.models.PatientItem) => play.twirl.api.HtmlFormat.Appendable) = (label,field1,forInput1,field2,forInput2,value) => apply(label,field1,forInput1,field2,forInput2,value)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  DATE: Wed Jan 20 18:29:28 PST 2021
                  SOURCE: /Users/noel/Desktop/CPE/femr/femr/app/femr/ui/views/partials/triage/inputAge.scala.html
                  HASH: abdc294b5d41f636d325d7eacc6d044534482445
                  MATRIX: 1034->1|1236->131|1254->141|1334->145|1369->154|1451->210|1476->215|1531->244|1563->268|1602->270|1646->287|1721->336|1750->345|1785->354|1814->363|1857->379|1884->385|1978->453|2007->462|2042->471|2071->480|2114->496|2141->502|2175->519|2187->524|2225->525|2269->542|2373->620|2387->625|2415->632|2510->700|2540->709|2577->719|2591->724|2624->736|2710->795|2740->804|2777->814|2791->819|2825->832|2873->849|2909->858|2959->126|2990->870|3018->872|3049->882
                  LINES: 28->1|32->2|32->2|34->2|35->3|36->4|36->4|37->5|37->5|37->5|38->6|38->6|38->6|38->6|38->6|38->6|38->6|39->7|39->7|39->7|39->7|39->7|39->7|40->8|40->8|40->8|41->9|41->9|41->9|41->9|42->10|42->10|42->10|42->10|42->10|43->11|43->11|43->11|43->11|43->11|44->12|45->13|47->2|47->14|48->15|48->15
                  -- GENERATED --
              */
          