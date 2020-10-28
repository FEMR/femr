
package femr.ui.views.html.partials.helpers

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

object outputFloatOrNA extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template1[java.lang.Float,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(number: java.lang.Float):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*1.27*/("""


"""),_display_(/*4.2*/if(number == null)/*4.20*/{_display_(Seq[Any](format.raw/*4.21*/("""
    """),format.raw/*5.5*/("""N/A
""")))}/*6.2*/else/*6.6*/{_display_(Seq[Any](format.raw/*6.7*/("""
    """),_display_(/*7.6*/number),format.raw/*7.12*/("""
""")))}))
      }
    }
  }

  def render(number:java.lang.Float): play.twirl.api.HtmlFormat.Appendable = apply(number)

  def f:((java.lang.Float) => play.twirl.api.HtmlFormat.Appendable) = (number) => apply(number)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  DATE: Tue Oct 27 21:59:18 PDT 2020
                  SOURCE: C:/Users/Noel/Documents/csc402/lemur-femr/femr/app/femr/ui/views/partials/helpers/outputFloatOrNA.scala.html
                  HASH: 020b6cf8fbfe98f7db1d61249815dd9d6c9bac25
                  MATRIX: 992->1|1112->26|1144->33|1170->51|1208->52|1240->58|1263->64|1274->68|1311->69|1343->76|1369->82
                  LINES: 28->1|33->1|36->4|36->4|36->4|37->5|38->6|38->6|38->6|39->7|39->7
                  -- GENERATED --
              */
          