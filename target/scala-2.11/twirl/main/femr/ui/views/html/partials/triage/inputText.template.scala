
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

object inputText extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template5[String,String,Boolean,Object,String,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(name: String, forInput: String, required: Boolean, value: Object, tipe: String):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {

def /*2.6*/inputBlock/*2.16*/:play.twirl.api.HtmlFormat.Appendable = {_display_(

Seq[Any](format.raw/*2.20*/("""

        """),format.raw/*4.9*/("""<div class="generalInfoInput">
            """),_display_(/*5.14*/if(required == true)/*5.34*/ {_display_(Seq[Any](format.raw/*5.36*/("""
                """),format.raw/*6.17*/("""<label for=""""),_display_(/*6.30*/forInput),format.raw/*6.38*/("""">"""),_display_(/*6.41*/name),format.raw/*6.45*/(""" """),format.raw/*6.46*/("""<span class="red bold">*</span></label>
            """)))}/*7.15*/else/*7.20*/{_display_(Seq[Any](format.raw/*7.21*/("""
                """),format.raw/*8.17*/("""<label for=""""),_display_(/*8.30*/forInput),format.raw/*8.38*/("""">"""),_display_(/*8.41*/name),format.raw/*8.45*/("""</label>
            """)))}),format.raw/*9.14*/("""

            """),_display_(/*11.14*/if(value == null)/*11.31*/ {_display_(Seq[Any](format.raw/*11.33*/("""
                """),format.raw/*12.17*/("""<input type=""""),_display_(/*12.31*/tipe),format.raw/*12.35*/("""" class="fInput" id=""""),_display_(/*12.57*/forInput),format.raw/*12.65*/("""" name=""""),_display_(/*12.74*/forInput),format.raw/*12.82*/("""" placeholder=""""),_display_(/*12.98*/name),format.raw/*12.102*/("""">
            """)))}/*13.15*/else/*13.20*/{_display_(Seq[Any](format.raw/*13.21*/("""
                """),format.raw/*14.17*/("""<input type=""""),_display_(/*14.31*/tipe),format.raw/*14.35*/("""" class="fInput" id=""""),_display_(/*14.57*/forInput),format.raw/*14.65*/("""" name=""""),_display_(/*14.74*/forInput),format.raw/*14.82*/("""" value=""""),_display_(/*14.92*/value),format.raw/*14.97*/("""" readonly/>
            """)))}),format.raw/*15.14*/("""
        """),format.raw/*16.9*/("""</div>
    """)))};
Seq[Any](format.raw/*2.1*/("""    """),format.raw/*17.6*/("""
"""),_display_(/*18.2*/inputBlock),format.raw/*18.12*/("""
"""))
      }
    }
  }

  def render(name:String,forInput:String,required:Boolean,value:Object,tipe:String): play.twirl.api.HtmlFormat.Appendable = apply(name,forInput,required,value,tipe)

  def f:((String,String,Boolean,Object,String) => play.twirl.api.HtmlFormat.Appendable) = (name,forInput,required,value,tipe) => apply(name,forInput,required,value,tipe)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  DATE: Wed Jan 20 18:29:28 PST 2021
                  SOURCE: /Users/noel/Desktop/CPE/femr/femr/app/femr/ui/views/partials/triage/inputText.scala.html
                  HASH: d090302f21d3491175624103a04fafe26a779834
                  MATRIX: 1005->1|1163->87|1181->97|1261->101|1297->111|1367->155|1395->175|1434->177|1478->194|1517->207|1545->215|1574->218|1598->222|1626->223|1697->277|1709->282|1747->283|1791->300|1830->313|1858->321|1887->324|1911->328|1963->350|2005->365|2031->382|2071->384|2116->401|2157->415|2182->419|2231->441|2260->449|2296->458|2325->466|2368->482|2394->486|2429->503|2442->508|2481->509|2526->526|2567->540|2592->544|2641->566|2670->574|2706->583|2735->591|2772->601|2798->606|2855->632|2891->641|2941->82|2972->653|3000->655|3031->665
                  LINES: 28->1|32->2|32->2|34->2|36->4|37->5|37->5|37->5|38->6|38->6|38->6|38->6|38->6|38->6|39->7|39->7|39->7|40->8|40->8|40->8|40->8|40->8|41->9|43->11|43->11|43->11|44->12|44->12|44->12|44->12|44->12|44->12|44->12|44->12|44->12|45->13|45->13|45->13|46->14|46->14|46->14|46->14|46->14|46->14|46->14|46->14|46->14|47->15|48->16|50->2|50->17|51->18|51->18
                  -- GENERATED --
              */
          