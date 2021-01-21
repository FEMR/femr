
package femr.ui.views.html.partials.triage

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

object inputGender extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template1[femr.ui.models.triage.IndexViewModelGet,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(viewModel: femr.ui.models.triage.IndexViewModelGet):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {
/*2.2*/import femr.ui.views.html.partials.triage.inputButton

def /*3.6*/inputBlock/*3.16*/:play.twirl.api.HtmlFormat.Appendable = {_display_(

Seq[Any](format.raw/*3.20*/("""
        """),format.raw/*4.9*/("""<div class="generalInfoInput">
            <label>Gender <span class="red bold">*</span></label>
            <div class="btn-group" id="genderBtns" data-toggle="buttons">
                """),_display_(/*7.18*/inputButton("Male", "sex", "maleBtn",
                    if(viewModel != null) viewModel.getPatient.getSex == "" || viewModel.getPatient.getSex == null else true,
                    if(viewModel != null) viewModel.getPatient.getSex == "Male" else false,
                    "Male")),format.raw/*10.28*/("""
                """),_display_(/*11.18*/inputButton("Female", "sex", "femaleBtn",
                    if(viewModel != null) viewModel.getPatient.getSex == "" || viewModel.getPatient.getSex == null else true,
                    if(viewModel != null) viewModel.getPatient.getSex == "Female" else false,
                    "Female")),format.raw/*14.30*/("""
            """),format.raw/*15.13*/("""</div>
        </div>
    """)))};
Seq[Any](format.raw/*3.1*/("""    """),format.raw/*17.6*/("""
"""),_display_(/*18.2*/inputBlock),format.raw/*18.12*/("""
"""))
      }
    }
  }

  def render(viewModel:femr.ui.models.triage.IndexViewModelGet): play.twirl.api.HtmlFormat.Appendable = apply(viewModel)

  def f:((femr.ui.models.triage.IndexViewModelGet) => play.twirl.api.HtmlFormat.Appendable) = (viewModel) => apply(viewModel)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  DATE: Wed Jan 20 18:29:28 PST 2021
                  SOURCE: /Users/noel/Desktop/CPE/femr/femr/app/femr/ui/views/partials/triage/inputGender.scala.html
                  HASH: b90c7cb3e88c527c16e9478713a57fc0a2d0aa23
                  MATRIX: 1011->1|1136->55|1202->114|1220->124|1300->128|1335->137|1549->325|1853->608|1898->626|2210->917|2251->930|2316->109|2347->957|2375->959|2406->969
                  LINES: 28->1|31->2|33->3|33->3|35->3|36->4|39->7|42->10|43->11|46->14|47->15|50->3|50->17|51->18|51->18
                  -- GENERATED --
              */
          