
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

object inputDate extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template4[String,String,Date,Date,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(name: String, forInput: String, value: Date, max: Date=new Date()):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {

def /*2.6*/inputBlock/*2.16*/:play.twirl.api.HtmlFormat.Appendable = {_display_(

Seq[Any](format.raw/*2.20*/("""
        """),format.raw/*3.9*/("""<div class="generalInfoInput">
            <label for=""""),_display_(/*4.26*/forInput),format.raw/*4.34*/("""">"""),_display_(/*4.37*/name),format.raw/*4.41*/("""</label>

            """),_display_(/*6.14*/if(value == null)/*6.31*/ {_display_(Seq[Any](format.raw/*6.33*/("""
                """),format.raw/*7.17*/("""<input type="date" class="fInput" id=""""),_display_(/*7.56*/forInput),format.raw/*7.64*/("""" name=""""),_display_(/*7.73*/forInput),format.raw/*7.81*/("""" placeholder="yyyy-mm-dd" """),_display_(/*7.109*/if(max != null)/*7.124*/{_display_(Seq[Any](format.raw/*7.125*/(""" """),format.raw/*7.126*/("""max=""""),_display_(/*7.132*/max/*7.135*/.format("yyyy-MM-dd")),format.raw/*7.156*/("""" """)))}),format.raw/*7.159*/(""">
            """)))}/*8.15*/else/*8.20*/{_display_(Seq[Any](format.raw/*8.21*/("""
                """),format.raw/*9.17*/("""<input type="date" class="fInput" id="readOnlyBirthDate" name=""""),_display_(/*9.81*/forInput),format.raw/*9.89*/("""" value=""""),_display_(/*9.99*/value/*9.104*/.format("yyyy-MM-dd")),format.raw/*9.125*/("""" readonly/>
            """)))}),format.raw/*10.14*/("""
        """),format.raw/*11.9*/("""</div>
    """)))};
Seq[Any](format.raw/*2.1*/("""    """),format.raw/*12.6*/("""
"""),_display_(/*13.2*/inputBlock),format.raw/*13.12*/("""
"""))
      }
    }
  }

  def render(name:String,forInput:String,value:Date,max:Date): play.twirl.api.HtmlFormat.Appendable = apply(name,forInput,value,max)

  def f:((String,String,Date,Date) => play.twirl.api.HtmlFormat.Appendable) = (name,forInput,value,max) => apply(name,forInput,value,max)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  DATE: Wed Jan 20 18:29:28 PST 2021
                  SOURCE: /Users/noel/Desktop/CPE/femr/femr/app/femr/ui/views/partials/triage/inputDate.scala.html
                  HASH: e125a766e576b7b100a625bdc6f3931a61a58e0d
                  MATRIX: 993->1|1138->74|1156->84|1236->88|1271->97|1353->153|1381->161|1410->164|1434->168|1483->191|1508->208|1547->210|1591->227|1656->266|1684->274|1719->283|1747->291|1802->319|1826->334|1865->335|1894->336|1927->342|1939->345|1981->366|2015->369|2048->385|2060->390|2098->391|2142->408|2232->472|2260->480|2296->490|2310->495|2352->516|2409->542|2445->551|2495->69|2526->563|2554->565|2585->575
                  LINES: 28->1|32->2|32->2|34->2|35->3|36->4|36->4|36->4|36->4|38->6|38->6|38->6|39->7|39->7|39->7|39->7|39->7|39->7|39->7|39->7|39->7|39->7|39->7|39->7|39->7|40->8|40->8|40->8|41->9|41->9|41->9|41->9|41->9|41->9|42->10|43->11|45->2|45->12|46->13|46->13
                  -- GENERATED --
              */
          