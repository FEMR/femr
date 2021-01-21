
package femr.ui.views.html.partials.shared

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

object searchBox extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template3[java.lang.String,java.lang.Integer,java.lang.String,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(page: java.lang.String, patientId: java.lang.Integer, message: java.lang.String):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*2.1*/("""
"""),format.raw/*3.1*/("""<div class="searchWrap">
    <h2>"""),_display_(/*4.10*/page),format.raw/*4.14*/(""" """),format.raw/*4.15*/("""Search</h2>
    """),_display_(/*5.6*/if(patientId > 0)/*5.23*/ {_display_(Seq[Any](format.raw/*5.25*/("""

        """),format.raw/*7.9*/("""<label for="id" class="hidden">Patient ID</label>
        <input type="number" class="fButtonSearch" id="id" name="id" value=""""),_display_(/*8.78*/patientId),format.raw/*8.87*/("""" readonly/>


        <p>"""),_display_(/*11.13*/message),format.raw/*11.20*/("""</p>

            <a href="/medical/edit/"""),_display_(/*13.37*/patientId),format.raw/*13.46*/("""" class="fButton fOtherButton fYesButton">Yes</a>
            <a href="/medical" class="fButton fOtherButton fNoButton">No</a>


    """)))}/*17.7*/else/*17.12*/{_display_(Seq[Any](format.raw/*17.13*/("""

        """),format.raw/*19.9*/("""<label for="id" class="hidden">Patient ID</label>
        <input type="number" class="fButtonSearch" id="id" name="id" placeholder="Patient ID">

        <p>"""),_display_(/*22.13*/message),format.raw/*22.20*/("""</p>
        """),format.raw/*23.52*/("""
        """),format.raw/*24.9*/("""<button type="submit" class="idSearch fButton fRedButton fLandingSearchButton">Search</button>
    """)))}),format.raw/*25.6*/("""
"""),format.raw/*26.1*/("""</div>
"""))
      }
    }
  }

  def render(page:java.lang.String,patientId:java.lang.Integer,message:java.lang.String): play.twirl.api.HtmlFormat.Appendable = apply(page,patientId,message)

  def f:((java.lang.String,java.lang.Integer,java.lang.String) => play.twirl.api.HtmlFormat.Appendable) = (page,patientId,message) => apply(page,patientId,message)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  DATE: Wed Jan 20 18:29:28 PST 2021
                  SOURCE: /Users/noel/Desktop/CPE/femr/femr/app/femr/ui/views/partials/shared/searchBox.scala.html
                  HASH: 1dd3dfe31232bea67757b69b4eef6b9c57de3e69
                  MATRIX: 1021->1|1196->83|1223->84|1283->118|1307->122|1335->123|1377->140|1402->157|1441->159|1477->169|1630->296|1659->305|1713->332|1741->339|1810->381|1840->390|1992->525|2005->530|2044->531|2081->541|2266->699|2294->706|2335->762|2371->771|2501->871|2529->872
                  LINES: 28->1|33->2|34->3|35->4|35->4|35->4|36->5|36->5|36->5|38->7|39->8|39->8|42->11|42->11|44->13|44->13|48->17|48->17|48->17|50->19|53->22|53->22|54->23|55->24|56->25|57->26
                  -- GENERATED --
              */
          