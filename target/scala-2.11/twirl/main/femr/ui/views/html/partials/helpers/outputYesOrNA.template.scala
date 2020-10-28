
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


Seq[Any](format.raw/*1.28*/("""
"""),format.raw/*3.1*/("""
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
                  DATE: Tue Oct 27 21:59:18 PDT 2020
                  SOURCE: C:/Users/Noel/Documents/csc402/lemur-femr/femr/app/femr/ui/views/partials/helpers/outputYesOrNA.scala.html
                  HASH: 380d6ee22ea77c65678d7a16ba0f95e99183acf3
                  MATRIX: 991->1|1090->30|1162->27|1190->74|1218->77|1268->119|1306->120|1338->126|1361->132|1372->136|1409->137|1441->143
                  LINES: 28->1|31->2|34->1|35->3|36->4|36->4|36->4|37->5|38->6|38->6|38->6|39->7
                  -- GENERATED --
              */
          