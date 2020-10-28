
package femr.ui.views.html.partials.medical

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

object severityDropDown extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template2[String,femr.common.models.TabFieldItem,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(chiefComplaint: String, severity: femr.common.models.TabFieldItem):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*1.69*/("""

"""),format.raw/*3.1*/("""<label for="severity">Severity</label>
<input name="tabFieldItems["""),_display_(/*4.29*/severity/*4.37*/.getIndex),format.raw/*4.46*/("""].name" type="text" class="hidden" value="severity"/>
<input name="tabFieldItems["""),_display_(/*5.29*/severity/*5.37*/.getIndex),format.raw/*5.46*/("""].chiefComplaint" type="text" class="hidden" value=""""),_display_(/*5.99*/chiefComplaint),format.raw/*5.113*/(""""/>
<select name="tabFieldItems["""),_display_(/*6.30*/severity/*6.38*/.getIndex),format.raw/*6.47*/("""].value" id="severityDropDown">
    <option></option>
    """),_display_(/*8.6*/for(x <- 1 to 10) yield /*8.23*/ {_display_(Seq[Any](format.raw/*8.25*/("""
        """),_display_(/*9.10*/if(severity.getValue != null && !severity.getValue.isEmpty)/*9.69*/ {_display_(Seq[Any](format.raw/*9.71*/("""
            """),_display_(/*10.14*/if(severity.getValue.equals(x.toString))/*10.54*/ {_display_(Seq[Any](format.raw/*10.56*/("""
                """),format.raw/*11.17*/("""<option value=""""),_display_(/*11.33*/x),format.raw/*11.34*/("""" selected="selected">"""),_display_(/*11.57*/x),format.raw/*11.58*/("""</option>
            """)))}/*12.15*/else/*12.20*/{_display_(Seq[Any](format.raw/*12.21*/("""
                """),format.raw/*13.17*/("""<option value=""""),_display_(/*13.33*/x),format.raw/*13.34*/("""">"""),_display_(/*13.37*/x),format.raw/*13.38*/("""</option>
            """)))}),format.raw/*14.14*/("""

        """)))}/*16.11*/else/*16.16*/{_display_(Seq[Any](format.raw/*16.17*/("""
            """),format.raw/*17.13*/("""<option value=""""),_display_(/*17.29*/x),format.raw/*17.30*/("""">"""),_display_(/*17.33*/x),format.raw/*17.34*/("""</option>
        """)))}),format.raw/*18.10*/("""
    """)))}),format.raw/*19.6*/("""
"""),format.raw/*20.1*/("""</select>

"""))
      }
    }
  }

  def render(chiefComplaint:String,severity:femr.common.models.TabFieldItem): play.twirl.api.HtmlFormat.Appendable = apply(chiefComplaint,severity)

  def f:((String,femr.common.models.TabFieldItem) => play.twirl.api.HtmlFormat.Appendable) = (chiefComplaint,severity) => apply(chiefComplaint,severity)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  DATE: Tue Oct 27 21:59:18 PDT 2020
                  SOURCE: C:/Users/Noel/Documents/csc402/lemur-femr/femr/app/femr/ui/views/partials/medical/severityDropDown.scala.html
                  HASH: aae67008586e52015aadcb29b2b5eeebd62cbdb4
                  MATRIX: 1016->1|1178->68|1208->72|1302->140|1318->148|1347->157|1456->240|1472->248|1501->257|1580->310|1615->324|1675->358|1691->366|1720->375|1806->436|1838->453|1877->455|1914->466|1981->525|2020->527|2062->542|2111->582|2151->584|2197->602|2240->618|2262->619|2312->642|2334->643|2377->668|2390->673|2429->674|2475->692|2518->708|2540->709|2570->712|2592->713|2647->737|2679->751|2692->756|2731->757|2773->771|2816->787|2838->788|2868->791|2890->792|2941->812|2978->819|3007->821
                  LINES: 28->1|33->1|35->3|36->4|36->4|36->4|37->5|37->5|37->5|37->5|37->5|38->6|38->6|38->6|40->8|40->8|40->8|41->9|41->9|41->9|42->10|42->10|42->10|43->11|43->11|43->11|43->11|43->11|44->12|44->12|44->12|45->13|45->13|45->13|45->13|45->13|46->14|48->16|48->16|48->16|49->17|49->17|49->17|49->17|49->17|50->18|51->19|52->20
                  -- GENERATED --
              */
          