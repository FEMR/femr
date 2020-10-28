
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
Seq[Any](format.raw/*1.67*/("""

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
                  DATE: Tue Oct 27 21:59:18 PDT 2020
                  SOURCE: C:/Users/Noel/Documents/csc402/lemur-femr/femr/app/femr/ui/views/feedback/feedback.scala.html
                  HASH: c1090bc7ba3fc83d2d45266b039bb4e10a318fd4
                  MATRIX: 1003->1|1141->71|1187->112|1252->173|1277->190|1354->200|1378->216|1458->220|1492->228|1548->258|1562->264|1607->289|1653->66|1683->166|1716->195|1747->296|1778->301|1890->403|1931->405|1968->415|2319->739|2334->745|2389->791|2429->793|2473->809|2697->1002|2739->1016
                  LINES: 28->1|31->3|32->4|34->7|34->7|36->8|36->8|38->8|39->9|39->9|39->9|39->9|41->1|43->5|45->7|46->10|48->12|48->12|48->12|50->14|56->20|56->20|56->20|56->20|58->22|62->26|64->28
                  -- GENERATED --
              */
          