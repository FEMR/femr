
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


Seq[Any](format.raw/*1.83*/("""

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
                  DATE: Tue Oct 27 21:59:18 PDT 2020
                  SOURCE: C:/Users/Noel/Documents/csc402/lemur-femr/femr/app/femr/ui/views/partials/shared/searchBox.scala.html
                  HASH: 695409055cd01e11e071817a58693a1b7d3460bd
                  MATRIX: 1021->1|1197->82|1227->86|1288->121|1312->125|1340->126|1383->144|1408->161|1447->163|1485->175|1639->303|1668->312|1725->342|1753->349|1824->393|1854->402|2010->541|2023->546|2062->547|2101->559|2289->720|2317->727|2359->784|2396->794|2527->895|2556->897
                  LINES: 28->1|33->1|35->3|36->4|36->4|36->4|37->5|37->5|37->5|39->7|40->8|40->8|43->11|43->11|45->13|45->13|49->17|49->17|49->17|51->19|54->22|54->22|55->23|56->24|57->25|58->26
                  -- GENERATED --
              */
          