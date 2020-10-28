
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
Seq[Any](format.raw/*1.126*/("""
    """),format.raw/*14.6*/("""
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
                  DATE: Tue Oct 27 21:59:18 PDT 2020
                  SOURCE: C:/Users/Noel/Documents/csc402/lemur-femr/femr/app/femr/ui/views/partials/triage/inputAge.scala.html
                  HASH: cb166fb89fc03ff6132ef1749e11c88ddbddda74
                  MATRIX: 1034->1|1236->132|1254->142|1334->146|1370->156|1453->213|1478->218|1534->248|1566->272|1605->274|1650->292|1725->341|1754->350|1789->359|1818->368|1861->384|1888->390|1983->459|2012->468|2047->477|2076->486|2119->502|2146->508|2181->526|2193->531|2231->532|2276->550|2380->628|2394->633|2422->640|2518->709|2548->718|2585->728|2599->733|2632->745|2719->805|2749->814|2786->824|2800->829|2834->842|2883->860|2920->870|2973->125|3006->883|3035->886|3066->896
                  LINES: 28->1|32->2|32->2|34->2|35->3|36->4|36->4|37->5|37->5|37->5|38->6|38->6|38->6|38->6|38->6|38->6|38->6|39->7|39->7|39->7|39->7|39->7|39->7|40->8|40->8|40->8|41->9|41->9|41->9|41->9|42->10|42->10|42->10|42->10|42->10|43->11|43->11|43->11|43->11|43->11|44->12|45->13|47->1|48->14|49->15|49->15
                  -- GENERATED --
              */
          