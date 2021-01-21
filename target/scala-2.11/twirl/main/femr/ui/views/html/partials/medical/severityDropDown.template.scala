
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


Seq[Any](format.raw/*2.1*/("""
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
                  DATE: Wed Jan 20 18:29:28 PST 2021
                  SOURCE: /Users/noel/Desktop/CPE/femr/femr/app/femr/ui/views/partials/medical/severityDropDown.scala.html
                  HASH: ca75339b9aaca2a7bc57af7a46f9bfacc01b168d
                  MATRIX: 1016->1|1177->69|1204->70|1297->137|1313->145|1342->154|1450->236|1466->244|1495->253|1574->306|1609->320|1668->353|1684->361|1713->370|1797->429|1829->446|1868->448|1904->458|1971->517|2010->519|2051->533|2100->573|2140->575|2185->592|2228->608|2250->609|2300->632|2322->633|2364->657|2377->662|2416->663|2461->680|2504->696|2526->697|2556->700|2578->701|2632->724|2662->736|2675->741|2714->742|2755->755|2798->771|2820->772|2850->775|2872->776|2922->795|2958->801|2986->802
                  LINES: 28->1|33->2|34->3|35->4|35->4|35->4|36->5|36->5|36->5|36->5|36->5|37->6|37->6|37->6|39->8|39->8|39->8|40->9|40->9|40->9|41->10|41->10|41->10|42->11|42->11|42->11|42->11|42->11|43->12|43->12|43->12|44->13|44->13|44->13|44->13|44->13|45->14|47->16|47->16|47->16|48->17|48->17|48->17|48->17|48->17|49->18|50->19|51->20
                  -- GENERATED --
              */
          