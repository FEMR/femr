
package femr.ui.views.html.partials.encounter

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

object dynamicTabSpan extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template1[femr.common.models.TabFieldItem,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(tabFieldItem: femr.common.models.TabFieldItem):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {
/*3.2*/import femr.ui.views.html.partials.helpers.outputStringOrNA


Seq[Any](format.raw/*1.49*/("""

"""),format.raw/*4.1*/("""
"""),_display_(/*5.2*/if(tabFieldItem != null)/*5.26*/{_display_(Seq[Any](format.raw/*5.27*/("""
    """),format.raw/*6.5*/("""<span class="value" data-id=""""),_display_(/*6.35*/tabFieldItem/*6.47*/.getName),format.raw/*6.55*/(""""> """),_display_(/*6.59*/outputStringOrNA(tabFieldItem.getValue)),format.raw/*6.98*/("""</span>
""")))}),format.raw/*7.2*/("""
"""))
      }
    }
  }

  def render(tabFieldItem:femr.common.models.TabFieldItem): play.twirl.api.HtmlFormat.Appendable = apply(tabFieldItem)

  def f:((femr.common.models.TabFieldItem) => play.twirl.api.HtmlFormat.Appendable) = (tabFieldItem) => apply(tabFieldItem)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  DATE: Tue Oct 27 21:59:18 PDT 2020
                  SOURCE: C:/Users/Noel/Documents/csc402/lemur-femr/femr/app/femr/ui/views/partials/encounter/dynamicTabSpan.scala.html
                  HASH: 531bcad90ef6cb35bc2a49266f364838e0ebfeec
                  MATRIX: 1009->1|1129->53|1218->48|1248->114|1276->117|1308->141|1346->142|1378->148|1434->178|1454->190|1482->198|1512->202|1571->241|1610->251
                  LINES: 28->1|31->3|34->1|36->4|37->5|37->5|37->5|38->6|38->6|38->6|38->6|38->6|38->6|39->7
                  -- GENERATED --
              */
          