
package femr.ui.views.html.triage

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

object index extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template3[femr.common.dtos.CurrentUser,femr.ui.models.triage.IndexViewModelGet,AssetsFinder,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(currentUser: femr.common.dtos.CurrentUser, viewModel: femr.ui.models.triage.IndexViewModelGet, assets: AssetsFinder):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {
/*3.2*/import femr.common.models.CityItem
/*4.2*/import femr.ui.views.html.layouts.main
/*5.2*/import femr.ui.views.html.partials.search
/*6.2*/import femr.ui.controllers.routes.TriageController
/*7.2*/import femr.ui.controllers.routes.HistoryController
/*8.2*/import femr.ui.views.html.partials.triage._
/*9.2*/import femr.ui.controllers.routes.SearchController
/*10.2*/import femr.ui.views.html.partials.helpers.outputStringOrNA

def /*13.6*/additionalScripts/*13.23*/:play.twirl.api.HtmlFormat.Appendable = {_display_(

Seq[Any](format.raw/*13.27*/("""
        """),format.raw/*14.9*/("""<script type="text/javascript" src=""""),_display_(/*14.46*/assets/*14.52*/.path("js/libraries/touchit-1.0.0.js")),format.raw/*14.90*/(""""></script>
        <script type="text/javascript" src=""""),_display_(/*15.46*/assets/*15.52*/.path("js/libraries/jquery.jWindowCrop.js")),format.raw/*15.95*/(""""></script>
        """),format.raw/*16.95*/("""
        """),format.raw/*17.9*/("""<script type="text/javascript" src=""""),_display_(/*17.46*/assets/*17.52*/.path("js/triage/triage.js")),format.raw/*17.80*/(""""></script>

        <script type="text/javascript" src=""""),_display_(/*19.46*/assets/*19.52*/.path("js/libraries/exif.js")),format.raw/*19.81*/(""""></script>
        <script type="text/javascript" src=""""),_display_(/*20.46*/assets/*20.52*/.path("js/libraries/megapix-image.js")),format.raw/*20.90*/(""""></script>
        <script type="text/javascript" src=""""),_display_(/*21.46*/assets/*21.52*/.path("js/triage/triageClientValidation.js")),format.raw/*21.96*/(""""></script>
        <script type="text/javascript" src=""""),_display_(/*22.46*/assets/*22.52*/.path("js/shared/vitalClientValidation.js")),format.raw/*22.95*/(""""></script>
            <script type="text/javascript" src=""""),_display_(/*23.50*/assets/*23.56*/.path("js/libraries/handlebars.min.js")),format.raw/*23.95*/(""""></script>
            <script type="text/javascript" src=""""),_display_(/*24.50*/assets/*24.56*/.path("js/libraries/typeahead.bundle.min.js")),format.raw/*24.101*/(""""></script>
    """)))};def /*26.6*/additionalStyles/*26.22*/:play.twirl.api.HtmlFormat.Appendable = {_display_(

Seq[Any](format.raw/*26.26*/("""
        """),format.raw/*27.9*/("""<link rel="stylesheet" href=""""),_display_(/*27.39*/assets/*27.45*/.path("css/triage.css")),format.raw/*27.68*/("""">
        <link rel="stylesheet" href=""""),_display_(/*28.39*/assets/*28.45*/.path("css/libraries/datepicker.css")),format.raw/*28.82*/("""">
    """)))};
Seq[Any](format.raw/*2.1*/("""
"""),format.raw/*11.1*/("""

    """),format.raw/*25.6*/("""
    """),format.raw/*29.6*/("""
"""),_display_(/*30.2*/main("Triage", currentUser, styles = additionalStyles, scripts = additionalScripts, search = search("triage"), assets = assets)/*30.129*/ {_display_(Seq[Any](format.raw/*30.131*/("""

    """),format.raw/*32.5*/("""<div id="triageContentWrap">
        """),_display_(/*33.10*/if(viewModel.isSearchError)/*33.37*/ {_display_(Seq[Any](format.raw/*33.39*/("""
            """),format.raw/*34.13*/("""<p>That patient could not be found.</p>
        """)))}),format.raw/*35.10*/("""
        """),_display_(/*36.10*/if(viewModel.isLinkToMedical())/*36.41*/{_display_(Seq[Any](format.raw/*36.42*/("""

           """),format.raw/*38.12*/("""<p class="newEncounterWrap"> Patient has an open encounter <a class="btn btn-danger" href="/medical/edit/"""),_display_(/*38.118*/viewModel/*38.127*/.getPatient().getId()),format.raw/*38.148*/("""" target="blank">Go To Medical</a></p>
        """)))}),format.raw/*39.10*/("""
        """),format.raw/*40.9*/("""<h2 class="text-center">Check In - Triage</h2>

        """),_display_(/*42.10*/helper/*42.16*/.form(action = TriageController.indexPost(viewModel.getPatient.getId), 'class -> "form-horizontal triage-form", 'enctype -> "multipart/form-data")/*42.162*/ {_display_(Seq[Any](format.raw/*42.164*/("""
            """),format.raw/*43.13*/("""<div id="genInfoWrap" class="sectionBackground backgroundForWrap">

                <input class="hidden" type="text" id="patientId" value=""""),_display_(/*45.74*/viewModel/*45.83*/.getPatient.getId),format.raw/*45.100*/("""" />

                <h2>General Info</h2>

                """),_display_(/*49.18*/inputText("First Name", "firstName", true, if(viewModel != null) viewModel.getPatient.getFirstName else null, "text")),format.raw/*49.135*/("""
                """),_display_(/*50.18*/inputText("Last Name", "lastName", true, if(viewModel != null) viewModel.getPatient.getLastName else null, "text")),format.raw/*50.132*/("""
                """),_display_(/*51.18*/inputText("Phone Number", "phoneNumber", false, if(viewModel != null) viewModel.getPatient.getPhoneNumber else null, "tel")),format.raw/*51.141*/("""
                """),_display_(/*52.18*/inputText("Address", "address", false, if(viewModel != null) viewModel.getPatient.getAddress else null, "text")),format.raw/*52.129*/("""
                """),format.raw/*53.17*/("""<div class="generalInfoInput" id="citySearchContainer">
                    <label for="city">City<span class="red bold">*</span></label>
                    """),_display_(/*55.22*/if(viewModel.getPatient.getCity == null)/*55.62*/ {_display_(Seq[Any](format.raw/*55.64*/("""
                        """),format.raw/*56.25*/("""<input type = "text" class="citySearch fInput" name="city" id="city" required>
                    """)))}/*57.23*/else/*57.28*/{_display_(Seq[Any](format.raw/*57.29*/("""
                        """),format.raw/*58.25*/("""<input type = "text" class="citySearch fInput" name="city" id="city" value=""""),_display_(/*58.102*/viewModel/*58.111*/.getPatient.getCity),format.raw/*58.130*/(""""readonly>
                        """)))}),format.raw/*59.26*/("""

                """),format.raw/*61.17*/("""</div>


                <div id="ageClassificationWrap">
                    <label>Age<span class="red bold">*</span></label>

                    <label id="conflictingAgeMessage" class="error-message" style="display: none">Birth Date and Age group are conflicting</label>

                    """),_display_(/*69.22*/inputAge("Age", "Years", "years", "Months", "months", if(viewModel != null) viewModel.getPatient else null)),format.raw/*69.129*/("""
                    """),format.raw/*70.21*/("""<span class="orSpan">OR</span>
                    """),_display_(/*71.22*/inputDate("Birth Date", "age", if(viewModel != null) viewModel.getPatient.getBirth else null)),format.raw/*71.115*/("""
                    """),format.raw/*72.21*/("""<span class="orSpan">OR</span>
                    <div class="generalInfoInput">
                        <div id="classificationRadioWrap">
                        """),_display_(/*75.26*/for((key, valyew) <- viewModel.getPossibleAgeClassifications) yield /*75.87*/ {_display_(Seq[Any](format.raw/*75.89*/("""
                            """),format.raw/*76.29*/("""<label><input type="radio" name="ageClassification" value=""""),_display_(/*76.89*/key),format.raw/*76.92*/("""" /> """),_display_(/*76.98*/key),format.raw/*76.101*/(""" """),format.raw/*76.102*/("""<span>"""),_display_(/*76.109*/valyew),format.raw/*76.115*/("""</span></label>
                        """)))}),format.raw/*77.26*/("""

                        """),format.raw/*79.25*/("""</div>
                    </div>

                </div>

                """),_display_(/*84.18*/inputGender(viewModel)),format.raw/*84.40*/("""


                """),format.raw/*87.17*/("""<div class="generalInfoInput">


                    <label for="photo">Patient Photo</label>
                    <div id="photoInputFormDiv">
                        <input type="file" class="fInput" id="photoInput" name="patientPhoto" placeholder="Choose Image" />
                        <textarea id="photoInputCropped" class="hidden" name="patientPhotoCropped"></textarea>
                    </div>
                    <div id="patientPhotoDiv" hidden="true">
                        <div class="btn-group" >
                            <img id="patientPhoto" class="crop_me" alt="" src=""""),_display_(/*97.81*/viewModel/*97.90*/.getPatient.getPathToPhoto),format.raw/*97.116*/("""" />

                            <div class="btn-toolbar" role="buttons">
                                <div class="btn-group" id="zoomControls">
                                    <button type="button" id="btnZoomIn" class="btn btn-default btn-lg">
                                        <span class="glyphicon glyphicon-zoom-in"></span>
                                    </button>
                                    <button type="button" id="btnZoomOut" class="btn btn-default btn-lg">
                                        <span class="glyphicon glyphicon-zoom-out"></span>
                                    </button>
                                </div>
                                <div class="btn-group">
                                    <button id="btnDeletePhoto" type="button" class="btn btn-danger btn-lg pull-right">
                                        <span class="glyphicon glyphicon-trash"></span>
                                    </button>
                                </div>
                            </div>
                            <canvas id="patientPhotoCanvas" class="hidden"></canvas>
                        </div>
                    </div>
                    <input class="hidden" type="checkbox" name="deletePhoto" value="true" id="deletePhoto">


                </div>

            </div>

            <div id="vitalsWrap" class="sectionBackground backgroundForWrap">
                <h2>Vitals</h2>

                <div id="vitalContainer">

                    <div id="leftVitalsWrap">
                        <!-- Alaa Serhan - Referenced in triage.js for calculating proper BMI
                            as well as in vitalClientValidation.js for validating height inputs -->
                        """),_display_(/*132.26*/if(viewModel.getSettings.isMetric)/*132.60*/ {_display_(Seq[Any](format.raw/*132.62*/("""
                            """),format.raw/*133.29*/("""<input type="hidden" id="vitalsUnits" value="metric" />
                        """)))}/*134.27*/else/*134.32*/{_display_(Seq[Any](format.raw/*134.33*/("""
                            """),format.raw/*135.29*/("""<input type="hidden" id="vitalUnits" value="imp" />
                        """)))}),format.raw/*136.26*/("""

                        """),format.raw/*138.25*/("""<div class="vitalWrap">
                            <label for="temperature">Temperature</label> <!---  Alaa Serhan -->
                            """),_display_(/*140.30*/if(viewModel.getSettings.isMetric)/*140.64*/ {_display_(Seq[Any](format.raw/*140.66*/("""
                                """),format.raw/*141.33*/("""<input type="number" step="any" min="0" class="fInput fVital" id=""""),_display_(/*141.100*/viewModel/*141.109*/.getVitalNames.get(2).getName),format.raw/*141.138*/("""" name=""""),_display_(/*141.147*/viewModel/*141.156*/.getVitalNames.get(2).getName),format.raw/*141.185*/("""" placeholder="&#176;C"/>
                            """)))}/*142.31*/else/*142.36*/{_display_(Seq[Any](format.raw/*142.37*/("""
                                """),format.raw/*143.33*/("""<input type="number" step="any" min="0" class="fInput fVital" id=""""),_display_(/*143.100*/viewModel/*143.109*/.getVitalNames.get(2).getName),format.raw/*143.138*/("""" name=""""),_display_(/*143.147*/viewModel/*143.156*/.getVitalNames.get(2).getName),format.raw/*143.185*/("""" placeholder="&#176;F"/>
                            """)))}),format.raw/*144.30*/("""

                        """),format.raw/*146.25*/("""</div>

                        <div class="vitalWrap">
                            <label for="bloodPressureSystolic" id="bloodPressureTitle">Blood Pressure</label>
                            <div class="doubleVital">
                                <input type="number" step="number" min="0" class="fInput" id=""""),_display_(/*151.96*/viewModel/*151.105*/.getVitalNames.get(7).getName),format.raw/*151.134*/("""" placeholder="Systolic" name=""""),_display_(/*151.166*/viewModel/*151.175*/.getVitalNames.get(7).getName),format.raw/*151.204*/(""""/>
                                <input type="number" step="number" min="0" class="fInput" id=""""),_display_(/*152.96*/viewModel/*152.105*/.getVitalNames.get(8).getName),format.raw/*152.134*/("""" placeholder="Diastolic" name=""""),_display_(/*152.167*/viewModel/*152.176*/.getVitalNames.get(8).getName),format.raw/*152.205*/(""""/>
                            </div>
                        </div>
                        <div class="vitalWrap">
                            <label for="heartRate">Heart Rate</label>
                            <input type="number" step="number" min="0" class="fInput" id=""""),_display_(/*157.92*/viewModel/*157.101*/.getVitalNames.get(1).getName),format.raw/*157.130*/("""" name=""""),_display_(/*157.139*/viewModel/*157.148*/.getVitalNames.get(1).getName),format.raw/*157.177*/("""" placeholder="bpm"/>
                        </div>

                        <div class="vitalWrap">
                            <label for="respirations">Respirations</label>
                            <input type="number" step="number" min="0" class="fInput" id=""""),_display_(/*162.92*/viewModel/*162.101*/.getVitalNames.get(0).getName),format.raw/*162.130*/("""" name=""""),_display_(/*162.139*/viewModel/*162.148*/.getVitalNames.get(0).getName),format.raw/*162.177*/("""" placeholder="bpm"/>
                        </div>

                        <div class="vitalWrap">
                            <label for="weeksPregnant">Weeks Pregnant</label>


                            """),_display_(/*169.30*/if(viewModel.getPatient.getSex == "Female")/*169.73*/ {_display_(Seq[Any](format.raw/*169.75*/("""
                                """),format.raw/*170.33*/("""<input type="number" step="number" min="0" class="fInput" id="weeksPregnant" name="weeksPregnant" placeholder="Weeks"/>
                            """)))}/*171.31*/else/*171.36*/{_display_(Seq[Any](format.raw/*171.37*/("""
                                """),format.raw/*172.33*/("""<input type="number" step="number" min="0" class="fInput" id="weeksPregnant" name="weeksPregnant" placeholder="Weeks" disabled/>
                            """)))}),format.raw/*173.30*/("""


                        """),format.raw/*176.25*/("""</div>
                        <!--Osman-->
                        <br/>
                        <label class="btn btn-default"> Smoking
                        <input type="checkbox" step="any" class="fButton" id="smoker" name="smoker" value="1">
                        </label>

                        <label class="btn btn-default"> Diabetes
                        <input type="checkbox" step="any" class="fButton" id="diabetic" name="diabetic" value="1">
                        </label>


                    </div>


                    <div id="rightVitalsWrap">
                        """),_display_(/*192.26*/if(viewModel.getSettings.isMetric)/*192.60*/ {_display_(Seq[Any](format.raw/*192.62*/(""" """),format.raw/*192.63*/("""<!-- Alaa Serhan -->
                            <input type="hidden" id="vitalsUnits" value="metric" />
                        """)))}/*194.27*/else/*194.32*/{_display_(Seq[Any](format.raw/*194.33*/("""
                            """),format.raw/*195.29*/("""<input type="hidden" id="vitalUnits" value="imp" />
                        """)))}),format.raw/*196.26*/("""


                        """),format.raw/*199.25*/("""<div class="vitalWrap">
                            <label for="oxygen">Oxygen Saturation</label>

                            <input type="number" step="any" min="0" class="fInput" id=""""),_display_(/*202.89*/viewModel/*202.98*/.getVitalNames.get(3).getName),format.raw/*202.127*/("""" name=""""),_display_(/*202.136*/viewModel/*202.145*/.getVitalNames.get(3).getName),format.raw/*202.174*/("""" placeholder="%"/>
                        </div>
                        <div class="vitalWrap">

                            <label for="heightFeet">Height</label>
                            <div class="doubleVital">
                                """),_display_(/*208.34*/if(viewModel.getSettings.isMetric)/*208.68*/ {_display_(Seq[Any](format.raw/*208.70*/(""" """),format.raw/*208.71*/("""<!---  Alaa Serhan -->
                                    <input type="number" step="number" min="0" class="fInput" id=""""),_display_(/*209.100*/viewModel/*209.109*/.getVitalNames.get(4).getName),format.raw/*209.138*/("""" placeholder="Meters" name=""""),_display_(/*209.168*/viewModel/*209.177*/.getVitalNames.get(4).getName),format.raw/*209.206*/(""""/>
                                    <input type="number" step="number" min="0" class="fInput" id=""""),_display_(/*210.100*/viewModel/*210.109*/.getVitalNames.get(5).getName),format.raw/*210.138*/("""" placeholder="Centimeters" name=""""),_display_(/*210.173*/viewModel/*210.182*/.getVitalNames.get(5).getName),format.raw/*210.211*/(""""/>
                                """)))}/*211.35*/else/*211.40*/{_display_(Seq[Any](format.raw/*211.41*/("""
                                    """),format.raw/*212.37*/("""<input type="number" step="number" min="0" class="fInput" id=""""),_display_(/*212.100*/viewModel/*212.109*/.getVitalNames.get(4).getName),format.raw/*212.138*/("""" placeholder="Feet" name=""""),_display_(/*212.166*/viewModel/*212.175*/.getVitalNames.get(4).getName),format.raw/*212.204*/(""""/>
                                    <input type="number" step="number" min="0" class="fInput" id=""""),_display_(/*213.100*/viewModel/*213.109*/.getVitalNames.get(5).getName),format.raw/*213.138*/("""" placeholder="Inches" name=""""),_display_(/*213.168*/viewModel/*213.177*/.getVitalNames.get(5).getName),format.raw/*213.206*/(""""/>
                                """)))}),format.raw/*214.34*/("""
                            """),format.raw/*215.29*/("""</div>
                        </div>
                        <div class="vitalWrap">
                            <label for="weight">Weight</label>
                            """),_display_(/*219.30*/if(viewModel.getSettings.isMetric)/*219.64*/ {_display_(Seq[Any](format.raw/*219.66*/(""" """),format.raw/*219.67*/("""<!---  Alaa Serhan -->
                                <input type="number" step="any" min="0" class="fInput" id=""""),_display_(/*220.93*/viewModel/*220.102*/.getVitalNames.get(6).getName),format.raw/*220.131*/("""" name=""""),_display_(/*220.140*/viewModel/*220.149*/.getVitalNames.get(6).getName),format.raw/*220.178*/("""" placeholder="kgs"/>
                            """)))}/*221.31*/else/*221.36*/{_display_(Seq[Any](format.raw/*221.37*/("""
                                """),format.raw/*222.33*/("""<input type="number" step="any" min="0" class="fInput" id=""""),_display_(/*222.93*/viewModel/*222.102*/.getVitalNames.get(6).getName),format.raw/*222.131*/("""" name=""""),_display_(/*222.140*/viewModel/*222.149*/.getVitalNames.get(6).getName),format.raw/*222.178*/("""" placeholder="lbs"/>
                            """)))}),format.raw/*223.30*/("""

                        """),format.raw/*225.25*/("""</div>
                        <div class="vitalWrap">
                            <label >BMI</label>

                            <input type="text" id="bmi" class="fInput" disabled="true"/>
                        </div>
                        <div class="vitalWrap">
                            <label>Glucose</label>

                            <input type="number" step="number" min="0" class="fInput" id=""""),_display_(/*234.92*/viewModel/*234.101*/.getVitalNames.get(9).getName),format.raw/*234.130*/("""" name=""""),_display_(/*234.139*/viewModel/*234.148*/.getVitalNames.get(9).getName),format.raw/*234.177*/("""" placeholder="mg/dl"/>
                        </div>

                        <!--Osman-->
                        <br/>
                        <label class="btn btn-default">Alcohol
                            <input type="checkbox" step="any" class="fButton" id="alcohol" name="alcohol" value="1">
                        </label>

                    </div>

                </div>
                <div id="chiefComplaintWrap">

                    <div id="chiefComplaintTitle">
                        <label for="chiefComplaint">
                            """),_display_(/*250.30*/if(viewModel.getSettings.isMultipleChiefComplaint)/*250.80*/ {_display_(Seq[Any](format.raw/*250.82*/("""
                                """),format.raw/*251.33*/("""<span id="addChiefComplaint">+</span>
                            """)))}),format.raw/*252.30*/("""
                            """),format.raw/*253.29*/("""Chief Complaint
                        </label>
                    </div>
                    <input type="text" class="hidden" name="chiefComplaintsJSON"/>

                    <textarea class="fTextArea" id="chiefComplaint" name="chiefComplaint"></textarea>
                    """),_display_(/*259.22*/if(viewModel.getSettings.isMultipleChiefComplaint)/*259.72*/ {_display_(Seq[Any](format.raw/*259.74*/("""
                        """),format.raw/*260.25*/("""<ol id="chiefComplaintList">
                            """),format.raw/*261.120*/("""
                        """),format.raw/*262.25*/("""</ol>
                    """)))}),format.raw/*263.22*/("""

                """),format.raw/*265.17*/("""</div>

                <div class="submitResetWrap hidden">
                    <h3>Did you screen this patient for Diabetes?</h3>
                    <button type="submit" id="noDiabetesScreen" class="fButton fSubmitButton pull-right">
                        No</button>
                    <button type="submit" id="yesDiabetesScreen" class="fButton fSubmitButton pull-left">
                        Yes</button>
                        <!-- Data elements for server-side logic, Diabetes Screen
                             Initial value for isDiabetesScreenPerformed set to empty string, only if
                             prompted will it receive a true/false value -->
                    <input type="hidden" id="isDiabetesScreenSettingEnabled" value=""""),_display_(/*276.86*/viewModel/*276.95*/.getSettings.isDiabetesPrompt),format.raw/*276.124*/(""""/>
                    <input type="hidden" name="isDiabetesScreenPerformed" value="" />
                </div>
        <div class="submitResetWrap">
            <button type="submit" id="triageSubmitBtn" class="fButton fRedButton fSubmitButton pull-right">
                Submit Patient</button>
            <button type="button" class="fButton fOtherButton newPatientBtn pull-left">Reset Fields</button>


            """),_display_(/*285.14*/if(viewModel.getPatient.getId != 0)/*285.49*/ {_display_(Seq[Any](format.raw/*285.51*/("""
                """),format.raw/*286.17*/("""<a href=""""),_display_(/*286.27*/HistoryController/*286.44*/.indexPatientGet(viewModel.getPatient.getId.toString)),format.raw/*286.97*/("""" class="fButton fOtherButton pull-left" target="_blank">Patient History</a>
            """)))}),format.raw/*287.14*/("""

        """),format.raw/*289.9*/("""</div>
  </div>

    </div>
    """)))}),format.raw/*293.6*/("""
""")))}),format.raw/*294.2*/("""
"""))
      }
    }
  }

  def render(currentUser:femr.common.dtos.CurrentUser,viewModel:femr.ui.models.triage.IndexViewModelGet,assets:AssetsFinder): play.twirl.api.HtmlFormat.Appendable = apply(currentUser,viewModel,assets)

  def f:((femr.common.dtos.CurrentUser,femr.ui.models.triage.IndexViewModelGet,AssetsFinder) => play.twirl.api.HtmlFormat.Appendable) = (currentUser,viewModel,assets) => apply(currentUser,viewModel,assets)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  DATE: Wed Jan 20 18:29:26 PST 2021
                  SOURCE: /Users/noel/Desktop/CPE/femr/femr/app/femr/ui/views/triage/index.scala.html
                  HASH: f3661e5c2e41a7354e6a929fa59595d03ce9ed14
                  MATRIX: 1038->1|1228->121|1270->157|1316->197|1365->240|1423->292|1482->345|1533->390|1592->442|1665->509|1691->526|1772->530|1808->539|1872->576|1887->582|1946->620|2030->677|2045->683|2109->726|2157->832|2193->841|2257->878|2272->884|2321->912|2406->970|2421->976|2471->1005|2555->1062|2570->1068|2629->1106|2713->1163|2728->1169|2793->1213|2877->1270|2892->1276|2956->1319|3044->1380|3059->1386|3119->1425|3207->1486|3222->1492|3289->1537|3329->1560|3354->1576|3435->1580|3471->1589|3528->1619|3543->1625|3587->1648|3655->1689|3670->1695|3728->1732|3774->119|3802->502|3835->1554|3867->1740|3895->1742|4032->1869|4073->1871|4106->1877|4171->1915|4207->1942|4247->1944|4288->1957|4368->2006|4405->2016|4445->2047|4484->2048|4525->2061|4659->2167|4678->2176|4721->2197|4800->2245|4836->2254|4920->2311|4935->2317|5091->2463|5132->2465|5173->2478|5341->2619|5359->2628|5398->2645|5487->2707|5626->2824|5671->2842|5807->2956|5852->2974|5997->3097|6042->3115|6175->3226|6220->3243|6406->3402|6455->3442|6495->3444|6548->3469|6667->3570|6680->3575|6719->3576|6772->3601|6877->3678|6896->3687|6937->3706|7004->3742|7050->3760|7375->4058|7504->4165|7553->4186|7632->4238|7747->4331|7796->4352|7989->4518|8066->4579|8106->4581|8163->4610|8250->4670|8274->4673|8307->4679|8332->4682|8362->4683|8397->4690|8425->4696|8497->4737|8551->4763|8654->4839|8697->4861|8744->4880|9366->5475|9384->5484|9432->5510|11232->7282|11276->7316|11317->7318|11375->7347|11476->7429|11490->7434|11530->7435|11588->7464|11697->7541|11752->7567|11929->7716|11973->7750|12014->7752|12076->7785|12172->7852|12192->7861|12244->7890|12282->7899|12302->7908|12354->7937|12429->7993|12443->7998|12483->7999|12545->8032|12641->8099|12661->8108|12713->8137|12751->8146|12771->8155|12823->8184|12910->8239|12965->8265|13308->8580|13328->8589|13380->8618|13441->8650|13461->8659|13513->8688|13640->8787|13660->8796|13712->8825|13774->8858|13794->8867|13846->8896|14153->9175|14173->9184|14225->9213|14263->9222|14283->9231|14335->9260|14631->9528|14651->9537|14703->9566|14741->9575|14761->9584|14813->9613|15052->9824|15105->9867|15146->9869|15208->9902|15377->10052|15391->10057|15431->10058|15493->10091|15683->10249|15739->10276|16366->10875|16410->10909|16451->10911|16481->10912|16631->11043|16645->11048|16685->11049|16743->11078|16852->11155|16908->11182|17123->11369|17142->11378|17194->11407|17232->11416|17252->11425|17304->11454|17586->11708|17630->11742|17671->11744|17701->11745|17852->11867|17872->11876|17924->11905|17983->11935|18003->11944|18055->11973|18187->12076|18207->12085|18259->12114|18323->12149|18343->12158|18395->12187|18452->12225|18466->12230|18506->12231|18572->12268|18664->12331|18684->12340|18736->12369|18793->12397|18813->12406|18865->12435|18997->12538|19017->12547|19069->12576|19128->12606|19148->12615|19200->12644|19269->12681|19327->12710|19533->12888|19577->12922|19618->12924|19648->12925|19791->13040|19811->13049|19863->13078|19901->13087|19921->13096|19973->13125|20044->13177|20058->13182|20098->13183|20160->13216|20248->13276|20268->13285|20320->13314|20358->13323|20378->13332|20430->13361|20513->13412|20568->13438|21011->13853|21031->13862|21083->13891|21121->13900|21141->13909|21193->13938|21789->14506|21849->14556|21890->14558|21952->14591|22051->14658|22109->14687|22420->14970|22480->15020|22521->15022|22575->15047|22662->15195|22716->15220|22775->15247|22822->15265|23613->16028|23632->16037|23684->16066|24135->16489|24180->16524|24221->16526|24267->16543|24305->16553|24332->16570|24407->16623|24529->16713|24567->16723|24631->16756|24664->16758
                  LINES: 28->1|31->3|32->4|33->5|34->6|35->7|36->8|37->9|38->10|40->13|40->13|42->13|43->14|43->14|43->14|43->14|44->15|44->15|44->15|45->16|46->17|46->17|46->17|46->17|48->19|48->19|48->19|49->20|49->20|49->20|50->21|50->21|50->21|51->22|51->22|51->22|52->23|52->23|52->23|53->24|53->24|53->24|54->26|54->26|56->26|57->27|57->27|57->27|57->27|58->28|58->28|58->28|60->2|61->11|63->25|64->29|65->30|65->30|65->30|67->32|68->33|68->33|68->33|69->34|70->35|71->36|71->36|71->36|73->38|73->38|73->38|73->38|74->39|75->40|77->42|77->42|77->42|77->42|78->43|80->45|80->45|80->45|84->49|84->49|85->50|85->50|86->51|86->51|87->52|87->52|88->53|90->55|90->55|90->55|91->56|92->57|92->57|92->57|93->58|93->58|93->58|93->58|94->59|96->61|104->69|104->69|105->70|106->71|106->71|107->72|110->75|110->75|110->75|111->76|111->76|111->76|111->76|111->76|111->76|111->76|111->76|112->77|114->79|119->84|119->84|122->87|132->97|132->97|132->97|167->132|167->132|167->132|168->133|169->134|169->134|169->134|170->135|171->136|173->138|175->140|175->140|175->140|176->141|176->141|176->141|176->141|176->141|176->141|176->141|177->142|177->142|177->142|178->143|178->143|178->143|178->143|178->143|178->143|178->143|179->144|181->146|186->151|186->151|186->151|186->151|186->151|186->151|187->152|187->152|187->152|187->152|187->152|187->152|192->157|192->157|192->157|192->157|192->157|192->157|197->162|197->162|197->162|197->162|197->162|197->162|204->169|204->169|204->169|205->170|206->171|206->171|206->171|207->172|208->173|211->176|227->192|227->192|227->192|227->192|229->194|229->194|229->194|230->195|231->196|234->199|237->202|237->202|237->202|237->202|237->202|237->202|243->208|243->208|243->208|243->208|244->209|244->209|244->209|244->209|244->209|244->209|245->210|245->210|245->210|245->210|245->210|245->210|246->211|246->211|246->211|247->212|247->212|247->212|247->212|247->212|247->212|247->212|248->213|248->213|248->213|248->213|248->213|248->213|249->214|250->215|254->219|254->219|254->219|254->219|255->220|255->220|255->220|255->220|255->220|255->220|256->221|256->221|256->221|257->222|257->222|257->222|257->222|257->222|257->222|257->222|258->223|260->225|269->234|269->234|269->234|269->234|269->234|269->234|285->250|285->250|285->250|286->251|287->252|288->253|294->259|294->259|294->259|295->260|296->261|297->262|298->263|300->265|311->276|311->276|311->276|320->285|320->285|320->285|321->286|321->286|321->286|321->286|322->287|324->289|328->293|329->294
                  -- GENERATED --
              */
          