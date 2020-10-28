
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
Seq[Any](format.raw/*1.69*/("""
    """),format.raw/*12.6*/("""
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
                  DATE: Tue Oct 27 21:59:18 PDT 2020
                  SOURCE: C:/Users/Noel/Documents/csc402/lemur-femr/femr/app/femr/ui/views/partials/triage/inputDate.scala.html
                  HASH: 0ea876fa71aae11d49603c0465835defd981ad9d
                  MATRIX: 993->1|1138->75|1156->85|1236->89|1272->99|1355->156|1383->164|1412->167|1436->171|1487->196|1512->213|1551->215|1596->233|1661->272|1689->280|1724->289|1752->297|1807->325|1831->340|1870->341|1899->342|1932->348|1944->351|1986->372|2020->375|2054->392|2066->397|2104->398|2149->416|2239->480|2267->488|2303->498|2317->503|2359->524|2417->551|2454->561|2506->68|2539->574|2568->577|2599->587
                  LINES: 28->1|32->2|32->2|34->2|35->3|36->4|36->4|36->4|36->4|38->6|38->6|38->6|39->7|39->7|39->7|39->7|39->7|39->7|39->7|39->7|39->7|39->7|39->7|39->7|39->7|40->8|40->8|40->8|41->9|41->9|41->9|41->9|41->9|41->9|42->10|43->11|45->1|46->12|47->13|47->13
                  -- GENERATED --
              */
          