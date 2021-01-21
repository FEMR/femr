
package femr.ui.views.html.partials.admin

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

object toggleButton extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template2[java.lang.Boolean,java.lang.Integer,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(isDeactivate: java.lang.Boolean, id: java.lang.Integer):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*2.1*/("""
"""),_display_(/*3.2*/if(isDeactivate)/*3.18*/ {_display_(Seq[Any](format.raw/*3.20*/("""
    """),format.raw/*4.5*/("""<button type="button" class="btn btn-danger toggleBtn" data-id=""""),_display_(/*4.70*/id),format.raw/*4.72*/("""">Deactivate</button>
""")))}/*5.3*/else/*5.8*/{_display_(Seq[Any](format.raw/*5.9*/("""
    """),format.raw/*6.5*/("""<button type="button" class="btn btn-success toggleBtn" data-id=""""),_display_(/*6.71*/id),format.raw/*6.73*/("""">Activate</button>
""")))}),format.raw/*7.2*/("""
"""))
      }
    }
  }

  def render(isDeactivate:java.lang.Boolean,id:java.lang.Integer): play.twirl.api.HtmlFormat.Appendable = apply(isDeactivate,id)

  def f:((java.lang.Boolean,java.lang.Integer) => play.twirl.api.HtmlFormat.Appendable) = (isDeactivate,id) => apply(isDeactivate,id)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  DATE: Wed Jan 20 18:29:28 PST 2021
                  SOURCE: /Users/noel/Desktop/CPE/femr/femr/app/femr/ui/views/partials/admin/toggleButton.scala.html
                  HASH: 4a953aae2bd1d05a5917371ea1103ca5f83dc963
                  MATRIX: 1007->1|1157->58|1184->60|1208->76|1247->78|1278->83|1369->148|1391->150|1431->174|1442->179|1479->180|1510->185|1602->251|1624->253|1674->274
                  LINES: 28->1|33->2|34->3|34->3|34->3|35->4|35->4|35->4|36->5|36->5|36->5|37->6|37->6|37->6|38->7
                  -- GENERATED --
              */
          