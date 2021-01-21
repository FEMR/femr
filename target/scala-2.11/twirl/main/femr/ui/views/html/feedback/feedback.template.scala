
package femr.ui.views.html.feedback

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

object feedback extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template2[femr.common.dtos.CurrentUser,AssetsFinder,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(currentUser: femr.common.dtos.CurrentUser, assets: AssetsFinder):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {
/*3.2*/import femr.ui.views.html.layouts.main
/*4.2*/import femr.ui.controllers.routes.FeedbackController

def /*7.4*/additionalScripts/*7.21*/:play.twirl.api.HtmlFormat.Appendable = {_display_(

Seq[Any]())};def /*8.4*/additionalStyles/*8.20*/:play.twirl.api.HtmlFormat.Appendable = {_display_(

Seq[Any](format.raw/*8.24*/("""
      """),format.raw/*9.7*/("""<link rel="stylesheet" href=""""),_display_(/*9.37*/assets/*9.43*/.path("css/feedback.css")),format.raw/*9.68*/("""">
  """)))};
Seq[Any](format.raw/*2.1*/("""
"""),format.raw/*5.1*/("""

  """),format.raw/*7.26*/("""
  """),format.raw/*10.4*/("""

"""),_display_(/*12.2*/main("Feedback", currentUser, styles = additionalStyles, scripts = additionalScripts, assets = assets)/*12.104*/ {_display_(Seq[Any](format.raw/*12.106*/("""

      """),format.raw/*14.7*/("""<div class="ifContainer">

        <h1>Give Feedback</h1>
        <p class="welcomeMessage">Thanks for choosing to give feedback to fEMR. Please be honest and as thorough as possible to make sure we are able to understand and implement your feedback appropriately.</p>

        <!-- Need form helper here -->
        """),_display_(/*20.10*/helper/*20.16*/.form(action = FeedbackController.indexPost())/*20.62*/ {_display_(Seq[Any](format.raw/*20.64*/("""

            """),format.raw/*22.13*/("""<textarea name="feedbackMsg" id="usrFeedback" cols="50" rows="10"></textarea>


            <button type="submit" class="fButton fSubmitButton" id="feedbackSubmit">Submit</button>
        """)))}),format.raw/*26.10*/("""

          """),format.raw/*28.11*/("""<p id="disclaimer">Your feedback is completely anonymous and will only be used to produce a better fEMR product.</p>
      </div>

""")))}))
      }
    }
  }

  def render(currentUser:femr.common.dtos.CurrentUser,assets:AssetsFinder): play.twirl.api.HtmlFormat.Appendable = apply(currentUser,assets)

  def f:((femr.common.dtos.CurrentUser,AssetsFinder) => play.twirl.api.HtmlFormat.Appendable) = (currentUser,assets) => apply(currentUser,assets)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  DATE: Wed Jan 20 18:29:26 PST 2021
                  SOURCE: /Users/noel/Desktop/CPE/femr/femr/app/femr/ui/views/feedback/feedback.scala.html
                  HASH: fbf63ce792492a9e7c130c5adc47565c997f919f
                  MATRIX: 1003->1|1141->69|1187->109|1252->167|1277->184|1354->193|1378->209|1458->213|1491->220|1547->250|1561->256|1606->281|1650->67|1677->162|1708->189|1738->287|1767->290|1879->392|1920->394|1955->402|2300->720|2315->726|2370->772|2410->774|2452->788|2672->977|2712->989
                  LINES: 28->1|31->3|32->4|34->7|34->7|36->8|36->8|38->8|39->9|39->9|39->9|39->9|41->2|42->5|44->7|45->10|47->12|47->12|47->12|49->14|55->20|55->20|55->20|55->20|57->22|61->26|63->28
                  -- GENERATED --
              */
          