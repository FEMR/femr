
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

object outputYesOrNA extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template1[java.lang.String,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(number: java.lang.String):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {
/*2.2*/import femr.util.stringhelpers.StringUtils


Seq[Any](format.raw/*3.1*/("""
"""),_display_(/*4.2*/if(StringUtils.isNullOrWhiteSpace(number))/*4.44*/{_display_(Seq[Any](format.raw/*4.45*/("""
    """),format.raw/*5.5*/("""N/A
""")))}/*6.2*/else/*6.6*/{_display_(Seq[Any](format.raw/*6.7*/("""
    """),format.raw/*7.5*/("""Yes
""")))}))
      }
    }
  }

  def render(number:java.lang.String): play.twirl.api.HtmlFormat.Appendable = apply(number)

  def f:((java.lang.String) => play.twirl.api.HtmlFormat.Appendable) = (number) => apply(number)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  DATE: Wed Jan 20 18:29:29 PST 2021
                  SOURCE: /Users/noel/Desktop/CPE/femr/femr/app/femr/ui/views/partials/helpers/outputYesOrNA.scala.html
                  HASH: 5b8381578bc4f8ef554be282f26e50aa884ce45d
                  MATRIX: 991->1|1090->29|1161->72|1188->74|1238->116|1276->117|1307->122|1329->127|1340->131|1377->132|1408->137
                  LINES: 28->1|31->2|34->3|35->4|35->4|35->4|36->5|37->6|37->6|37->6|38->7
                  -- GENERATED --
              */
          