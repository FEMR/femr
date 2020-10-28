
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
Seq[Any](format.raw/*1.82*/("""
    """),format.raw/*17.6*/("""
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
                  DATE: Tue Oct 27 21:59:18 PDT 2020
                  SOURCE: C:/Users/Noel/Documents/csc402/lemur-femr/femr/app/femr/ui/views/partials/triage/inputText.scala.html
                  HASH: 204667e26d2a70b9b320ee64670ac8a7423a7e6b
                  MATRIX: 1005->1|1163->88|1181->98|1261->102|1299->114|1370->159|1398->179|1437->181|1482->199|1521->212|1549->220|1578->223|1602->227|1630->228|1702->283|1714->288|1752->289|1797->307|1836->320|1864->328|1893->331|1917->335|1970->358|2014->375|2040->392|2080->394|2126->412|2167->426|2192->430|2241->452|2270->460|2306->469|2335->477|2378->493|2404->497|2440->515|2453->520|2492->521|2538->539|2579->553|2604->557|2653->579|2682->587|2718->596|2747->604|2784->614|2810->619|2868->646|2905->656|2957->81|2990->669|3019->672|3050->682
                  LINES: 28->1|32->2|32->2|34->2|36->4|37->5|37->5|37->5|38->6|38->6|38->6|38->6|38->6|38->6|39->7|39->7|39->7|40->8|40->8|40->8|40->8|40->8|41->9|43->11|43->11|43->11|44->12|44->12|44->12|44->12|44->12|44->12|44->12|44->12|44->12|45->13|45->13|45->13|46->14|46->14|46->14|46->14|46->14|46->14|46->14|46->14|46->14|47->15|48->16|50->1|51->17|52->18|52->18
                  -- GENERATED --
              */
          