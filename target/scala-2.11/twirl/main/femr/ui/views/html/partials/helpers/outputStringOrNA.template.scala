
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

object outputStringOrNA extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template1[java.lang.String,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(text: java.lang.String):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {
/*3.2*/import femr.util.stringhelpers.StringUtils


Seq[Any](format.raw/*1.26*/("""

"""),format.raw/*4.1*/("""
"""),_display_(/*5.2*/if(StringUtils.isNullOrWhiteSpace(text))/*5.42*/{_display_(Seq[Any](format.raw/*5.43*/("""
    """),format.raw/*6.5*/("""N/A
""")))}/*7.2*/else/*7.6*/{_display_(Seq[Any](format.raw/*7.7*/("""
    """),_display_(/*8.6*/text),format.raw/*8.10*/("""
""")))}),format.raw/*9.2*/("""
"""))
      }
    }
  }

  def render(text:java.lang.String): play.twirl.api.HtmlFormat.Appendable = apply(text)

  def f:((java.lang.String) => play.twirl.api.HtmlFormat.Appendable) = (text) => apply(text)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  DATE: Tue Oct 27 21:59:18 PDT 2020
                  SOURCE: C:/Users/Noel/Documents/csc402/lemur-femr/femr/app/femr/ui/views/partials/helpers/outputStringOrNA.scala.html
                  HASH: 017a43469a202f28f762632fe8c814b47f8c9e87
                  MATRIX: 994->1|1091->30|1163->25|1193->74|1221->77|1269->117|1307->118|1339->124|1362->130|1373->134|1410->135|1442->142|1466->146|1498->149
                  LINES: 28->1|31->3|34->1|36->4|37->5|37->5|37->5|38->6|39->7|39->7|39->7|40->8|40->8|41->9
                  -- GENERATED --
              */
          