
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


Seq[Any](format.raw/*2.1*/("""
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
                  DATE: Wed Jan 20 18:29:29 PST 2021
                  SOURCE: /Users/noel/Desktop/CPE/femr/femr/app/femr/ui/views/partials/helpers/outputStringOrNA.scala.html
                  HASH: df97d1230d45a5db13a978e8a65733e1258a6a74
                  MATRIX: 994->1|1091->28|1162->26|1189->71|1216->73|1264->113|1302->114|1333->119|1355->124|1366->128|1403->129|1434->135|1458->139|1489->141
                  LINES: 28->1|31->3|34->2|35->4|36->5|36->5|36->5|37->6|38->7|38->7|38->7|39->8|39->8|40->9
                  -- GENERATED --
              */
          