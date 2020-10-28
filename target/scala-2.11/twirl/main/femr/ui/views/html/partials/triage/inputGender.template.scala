
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
Seq[Any](format.raw/*1.54*/("""
"""),format.raw/*3.1*/("""    """),format.raw/*17.6*/("""
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
                  DATE: Tue Oct 27 21:59:18 PDT 2020
                  SOURCE: C:/Users/Noel/Documents/csc402/lemur-femr/femr/app/femr/ui/views/partials/triage/inputGender.scala.html
                  HASH: 7ca2e40db4f1acfc7da50f4a31c078a89ab498d1
                  MATRIX: 1011->1|1136->56|1202->116|1220->126|1300->130|1336->140|1553->331|1860->617|1906->636|2221->930|2263->944|2331->53|2359->111|2390->973|2419->976|2450->986
                  LINES: 28->1|31->2|33->3|33->3|35->3|36->4|39->7|42->10|43->11|46->14|47->15|50->1|51->3|51->17|52->18|52->18
                  -- GENERATED --
              */
          