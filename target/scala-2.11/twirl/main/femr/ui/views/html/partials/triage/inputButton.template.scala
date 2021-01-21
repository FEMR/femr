
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

object inputButton extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template6[String,String,String,Boolean,Boolean,String,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(name: String, forInput: String, id: String, emptyModel: Boolean, active: Boolean, value: String):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {

def /*3.4*/inputBlock/*3.14*/:play.twirl.api.HtmlFormat.Appendable = {_display_(

Seq[Any](format.raw/*3.18*/("""
  """),_display_(/*4.4*/if(Boolean2boolean(emptyModel))/*4.35*/ {_display_(Seq[Any](format.raw/*4.37*/("""
    """),format.raw/*5.5*/("""<label class="btn btn-default width-50">
      <input type="radio" name=""""),_display_(/*6.34*/forInput),format.raw/*6.42*/("""" id=""""),_display_(/*6.49*/id),format.raw/*6.51*/("""" value=""""),_display_(/*6.61*/value),format.raw/*6.66*/("""">
      """),_display_(/*7.8*/name),format.raw/*7.12*/("""
    """),format.raw/*8.5*/("""</label>
  """)))}/*9.5*/else/*9.10*/{_display_(Seq[Any](format.raw/*9.11*/("""
    """),_display_(/*10.6*/if(Boolean2boolean(active))/*10.33*/ {_display_(Seq[Any](format.raw/*10.35*/("""
      """),format.raw/*11.7*/("""<label class="btn btn-primary active disabled  width-50">
        <input type="radio" name=""""),_display_(/*12.36*/forInput),format.raw/*12.44*/("""" id=""""),_display_(/*12.51*/id),format.raw/*12.53*/("""" value=""""),_display_(/*12.63*/value),format.raw/*12.68*/("""" checked="checked">
        """),_display_(/*13.10*/name),format.raw/*13.14*/("""
      """),format.raw/*14.7*/("""</label>
    """)))}/*15.7*/else/*15.12*/{_display_(Seq[Any](format.raw/*15.13*/("""
      """),format.raw/*16.7*/("""<label class="btn btn-default disabled  width-50">
        <input type="radio" name=""""),_display_(/*17.36*/forInput),format.raw/*17.44*/("""" id=""""),_display_(/*17.51*/id),format.raw/*17.53*/("""" value=""""),_display_(/*17.63*/value),format.raw/*17.68*/("""">
        """),_display_(/*18.10*/name),format.raw/*18.14*/("""
      """),format.raw/*19.7*/("""</label>
    """)))}),format.raw/*20.6*/("""
  """)))}),format.raw/*21.4*/("""
  """)))};
Seq[Any](format.raw/*2.1*/("""
  """),format.raw/*22.4*/("""
"""),_display_(/*23.2*/inputBlock),format.raw/*23.12*/("""
"""))
      }
    }
  }

  def render(name:String,forInput:String,id:String,emptyModel:Boolean,active:Boolean,value:String): play.twirl.api.HtmlFormat.Appendable = apply(name,forInput,id,emptyModel,active,value)

  def f:((String,String,String,Boolean,Boolean,String) => play.twirl.api.HtmlFormat.Appendable) = (name,forInput,id,emptyModel,active,value) => apply(name,forInput,id,emptyModel,active,value)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  DATE: Wed Jan 20 18:29:28 PST 2021
                  SOURCE: /Users/noel/Desktop/CPE/femr/femr/app/femr/ui/views/partials/triage/inputButton.scala.html
                  HASH: 69ed5267f0bdbadcdbdf43e78a96cddb02b6fb3a
                  MATRIX: 1015->1|1190->103|1208->113|1288->117|1317->121|1356->152|1395->154|1426->159|1526->233|1554->241|1587->248|1609->250|1645->260|1670->265|1705->275|1729->279|1760->284|1789->297|1801->302|1839->303|1871->309|1907->336|1947->338|1981->345|2101->438|2130->446|2164->453|2187->455|2224->465|2250->470|2307->500|2332->504|2366->511|2398->526|2411->531|2450->532|2484->539|2597->625|2626->633|2660->640|2683->642|2720->652|2746->657|2785->669|2810->673|2844->680|2888->694|2922->698|2964->99|2994->702|3022->704|3053->714
                  LINES: 28->1|32->3|32->3|34->3|35->4|35->4|35->4|36->5|37->6|37->6|37->6|37->6|37->6|37->6|38->7|38->7|39->8|40->9|40->9|40->9|41->10|41->10|41->10|42->11|43->12|43->12|43->12|43->12|43->12|43->12|44->13|44->13|45->14|46->15|46->15|46->15|47->16|48->17|48->17|48->17|48->17|48->17|48->17|49->18|49->18|50->19|51->20|52->21|54->2|55->22|56->23|56->23
                  -- GENERATED --
              */
          