
package femr.ui.views.html.medical

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

object newVitals extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template2[femr.ui.models.medical.EditViewModelGet,AssetsFinder,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(viewModel: femr.ui.models.medical.EditViewModelGet, assets: AssetsFinder):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*1.76*/("""

"""),format.raw/*3.1*/("""<link rel="stylesheet" href=""""),_display_(/*3.31*/assets/*3.37*/.path("css/medical/newVitals.css")),format.raw/*3.71*/("""" xmlns="http://www.w3.org/1999/xhtml">
<script type="text/javascript" src=""""),_display_(/*4.38*/assets/*4.44*/.path("js/medical/newVitals.js")),format.raw/*4.76*/(""""></script>
<script type="text/javascript" src=""""),_display_(/*5.38*/assets/*5.44*/.path("js/shared/vitalClientValidation.js")),format.raw/*5.87*/(""""></script>
<div id="vitalContainer">

    <div id="left">
        <div class="vitalWrap">
            <label>BP</label>
            <br/>
            <input type="number" class="fInput" id="newSystolic" placeholder="systolic"/>
            <input type="number" class="fInput" id="newDiastolic" placeholder="diastolic"/>
        </div>
        <div class="vitalWrap">
            <label>HR</label>
            <br/>
            <input type="number" class="fInput" id="newHeartRate" placeholder="bpm"/>
        </div>
        <div class="vitalWrap">
            <label>T</label>
            <br/>
            """),_display_(/*23.14*/if(viewModel.getSettings.isMetric)/*23.48*/ {_display_(Seq[Any](format.raw/*23.50*/(""" """),format.raw/*23.51*/("""<!---  Alaa Serhan -->
                <input type="number" class="fInput" id="newTemperature" placeholder="C"/>
            """)))}/*25.15*/else/*25.20*/{_display_(Seq[Any](format.raw/*25.21*/("""
                """),format.raw/*26.17*/("""<input type="number" class="fInput" id="newTemperature" placeholder="F"/>
            """)))}),format.raw/*27.14*/("""
        """),format.raw/*28.9*/("""</div>
        <div class="vitalWrap">
            <label>RR</label>
            <br/>
            <input type="number" class="fInput" id="newRespiratoryRate" placeholder="bpm"/>
        </div>
        <label class="btn btn-default" for="newSmoker">Smoking
        <input type="checkbox" class="fButton" id="newSmoker" name="smoker" value="1" />
        </label>

        <br/>
        <label class="btn btn-default" for="newDiabetic">Diabetes
        <input type="checkbox" class="fButton" id="newDiabetic" name="diabetic" value="1" />
        </label>
        <br>
        <label class="btn btn-default" for="newAlcohol">Alcohol
        <input type="checkbox" class="fButton" id="newAlcohol" name="alcohol" value="1" />
        </label>
        <br/>

    </div>
    <div id="right">
        <div class="vitalWrap">
            <label>Gluc</label>
            <br/>
            <input type="number" class="fInput" id="newGlucose" placeholder="mg/dl"/>
        </div>
        <div class="vitalWrap">
            <label>SpO2</label>
            <br/>
            <input type="number" class="fInput" id="newOxygen" placeholder="%"/>
        </div>
        <div class="vitalWrap">
            <label>Ht</label>
            <br/>
            """),_display_(/*63.14*/if(viewModel.getSettings.isMetric)/*63.48*/ {_display_(Seq[Any](format.raw/*63.50*/(""" """),format.raw/*63.51*/("""<!---  Alaa Serhan -->
                <input type="number" class="fInput" id="newHeightFeet" placeholder="m"/>
                <input type="number" class="fInput" id="newHeightInches" placeholder="cm"/>
            """)))}/*66.15*/else/*66.20*/{_display_(Seq[Any](format.raw/*66.21*/("""
                """),format.raw/*67.17*/("""<input type="number" class="fInput" id="newHeightFeet" placeholder="ft"/>
                <input type="number" class="fInput" id="newHeightInches" placeholder="in"/>
            """)))}),format.raw/*69.14*/("""
        """),format.raw/*70.9*/("""</div>
        <div class="vitalWrap">
            <label>Wt</label>
            <br/>
            """),_display_(/*74.14*/if(viewModel.getSettings.isMetric)/*74.48*/ {_display_(Seq[Any](format.raw/*74.50*/(""" """),format.raw/*74.51*/("""<!---  Alaa Serhan -->
                <input type="number" class="fInput" id="newWeight" placeholder="kgs"/>
            """)))}/*76.15*/else/*76.20*/{_display_(Seq[Any](format.raw/*76.21*/("""
                """),format.raw/*77.17*/("""<input type="number" class="fInput" id="newWeight" placeholder="lbs"/>
            """)))}),format.raw/*78.14*/("""
        """),format.raw/*79.9*/("""</div>
        <div class="vitalWrap">
            <label>WP</label> <!--- Sam Zanni -->
            <br/>
            <input type="number" class="fInput" id="weeksPreg" placeholder="weeks"/>
        </div>
    </div>
    <div id="theButtons">
        <button type="button" id="saveVitalsBtn" class="fButton">Save</button>
        <button type="button" id="cancelVitalsBtn" class="fButton">Cancel</button>
    </div>

</div>




"""))
      }
    }
  }

  def render(viewModel:femr.ui.models.medical.EditViewModelGet,assets:AssetsFinder): play.twirl.api.HtmlFormat.Appendable = apply(viewModel,assets)

  def f:((femr.ui.models.medical.EditViewModelGet,AssetsFinder) => play.twirl.api.HtmlFormat.Appendable) = (viewModel,assets) => apply(viewModel,assets)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  DATE: Tue Oct 27 21:59:18 PDT 2020
                  SOURCE: C:/Users/Noel/Documents/csc402/lemur-femr/femr/app/femr/ui/views/medical/newVitals.scala.html
                  HASH: c6a6156980df51af9d0adeeb0f3e66538885f933
                  MATRIX: 1014->1|1183->75|1213->79|1269->109|1283->115|1337->149|1441->227|1455->233|1507->265|1583->315|1597->321|1660->364|2314->991|2357->1025|2397->1027|2426->1028|2573->1157|2586->1162|2625->1163|2671->1181|2790->1269|2827->1279|4129->2554|4172->2588|4212->2590|4241->2591|4480->2812|4493->2817|4532->2818|4578->2836|4790->3017|4827->3027|4958->3131|5001->3165|5041->3167|5070->3168|5214->3294|5227->3299|5266->3300|5312->3318|5428->3403|5465->3413
                  LINES: 28->1|33->1|35->3|35->3|35->3|35->3|36->4|36->4|36->4|37->5|37->5|37->5|55->23|55->23|55->23|55->23|57->25|57->25|57->25|58->26|59->27|60->28|95->63|95->63|95->63|95->63|98->66|98->66|98->66|99->67|101->69|102->70|106->74|106->74|106->74|106->74|108->76|108->76|108->76|109->77|110->78|111->79
                  -- GENERATED --
              */
          