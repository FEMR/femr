
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


Seq[Any](format.raw/*1.58*/("""

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
                  DATE: Tue Oct 27 21:59:18 PDT 2020
                  SOURCE: C:/Users/Noel/Documents/csc402/lemur-femr/femr/app/femr/ui/views/partials/admin/toggleButton.scala.html
                  HASH: 5c168468331542ac85d2ef40b4d17d5ed3a8abce
                  MATRIX: 1007->1|1158->57|1188->62|1212->78|1251->80|1283->86|1374->151|1396->153|1437->178|1448->183|1485->184|1517->190|1609->256|1631->258|1682->280
                  LINES: 28->1|33->1|35->3|35->3|35->3|36->4|36->4|36->4|37->5|37->5|37->5|38->6|38->6|38->6|39->7
                  -- GENERATED --
              */
          