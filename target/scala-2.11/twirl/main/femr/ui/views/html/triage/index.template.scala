
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
Seq[Any](format.raw/*1.119*/("""

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
                  DATE: Tue Oct 27 21:59:18 PDT 2020
                  SOURCE: C:/Users/Noel/Documents/csc402/lemur-femr/femr/app/femr/ui/views/triage/index.scala.html
                  HASH: 42c110528a9520cb794abea3682ac1599cf81c9f
                  MATRIX: 1038->1|1228->123|1270->160|1316->201|1365->245|1423->298|1482->352|1533->398|1592->451|1665->521|1691->538|1772->542|1809->552|1873->589|1888->595|1947->633|2032->691|2047->697|2111->740|2160->847|2197->857|2261->894|2276->900|2325->928|2412->988|2427->994|2477->1023|2562->1081|2577->1087|2636->1125|2721->1183|2736->1189|2801->1233|2886->1291|2901->1297|2965->1340|3054->1402|3069->1408|3129->1447|3218->1509|3233->1515|3300->1560|3341->1585|3366->1601|3447->1605|3484->1615|3541->1645|3556->1651|3600->1674|3669->1716|3684->1722|3742->1759|3791->118|3822->512|3857->1578|3890->1768|3919->1771|4056->1898|4097->1900|4132->1908|4198->1947|4234->1974|4274->1976|4316->1990|4397->2040|4435->2051|4475->2082|4514->2083|4557->2098|4691->2204|4710->2213|4753->2234|4833->2283|4870->2293|4956->2352|4971->2358|5127->2504|5168->2506|5210->2520|5380->2663|5398->2672|5437->2689|5530->2755|5669->2872|5715->2891|5851->3005|5897->3024|6042->3147|6088->3166|6221->3277|6267->3295|6455->3456|6504->3496|6544->3498|6598->3524|6718->3626|6731->3631|6770->3632|6824->3658|6929->3735|6948->3744|6989->3763|7057->3800|7105->3820|7438->4126|7567->4233|7617->4255|7697->4308|7812->4401|7862->4423|8058->4592|8135->4653|8175->4655|8233->4685|8320->4745|8344->4748|8377->4754|8402->4757|8432->4758|8467->4765|8495->4771|8568->4813|8624->4841|8732->4922|8775->4944|8825->4966|9457->5571|9475->5580|9523->5606|11358->7413|11402->7447|11443->7449|11502->7479|11604->7562|11618->7567|11658->7568|11717->7598|11827->7676|11884->7704|12063->7855|12107->7889|12148->7891|12211->7925|12307->7992|12327->8001|12379->8030|12417->8039|12437->8048|12489->8077|12565->8134|12579->8139|12619->8140|12682->8174|12778->8241|12798->8250|12850->8279|12888->8288|12908->8297|12960->8326|13048->8382|13105->8410|13453->8730|13473->8739|13525->8768|13586->8800|13606->8809|13658->8838|13786->8938|13806->8947|13858->8976|13920->9009|13940->9018|13992->9047|14304->9331|14324->9340|14376->9369|14414->9378|14434->9387|14486->9416|14787->9689|14807->9698|14859->9727|14897->9736|14917->9745|14969->9774|15215->9992|15268->10035|15309->10037|15372->10071|15542->10222|15556->10227|15596->10228|15659->10262|15850->10421|15909->10451|16552->11066|16596->11100|16637->11102|16667->11103|16819->11236|16833->11241|16873->11242|16932->11272|17042->11350|17101->11380|17319->11570|17338->11579|17390->11608|17428->11617|17448->11626|17500->11655|17788->11915|17832->11949|17873->11951|17903->11952|18055->12075|18075->12084|18127->12113|18186->12143|18206->12152|18258->12181|18391->12285|18411->12294|18463->12323|18527->12358|18547->12367|18599->12396|18657->12435|18671->12440|18711->12441|18778->12479|18870->12542|18890->12551|18942->12580|18999->12608|19019->12617|19071->12646|19204->12750|19224->12759|19276->12788|19335->12818|19355->12827|19407->12856|19477->12894|19536->12924|19746->13106|19790->13140|19831->13142|19861->13143|20005->13259|20025->13268|20077->13297|20115->13306|20135->13315|20187->13344|20259->13397|20273->13402|20313->13403|20376->13437|20464->13497|20484->13506|20536->13535|20574->13544|20594->13553|20646->13582|20730->13634|20787->13662|21239->14086|21259->14095|21311->14124|21349->14133|21369->14142|21421->14171|22033->14755|22093->14805|22134->14807|22197->14841|22297->14909|22356->14939|22673->15228|22733->15278|22774->15280|22829->15306|22917->15455|22972->15481|23032->15509|23081->15529|23883->16303|23902->16312|23954->16341|24414->16773|24459->16808|24500->16810|24547->16828|24585->16838|24612->16855|24687->16908|24810->16999|24850->17011|24918->17048|24952->17051
                  LINES: 28->1|31->3|32->4|33->5|34->6|35->7|36->8|37->9|38->10|40->13|40->13|42->13|43->14|43->14|43->14|43->14|44->15|44->15|44->15|45->16|46->17|46->17|46->17|46->17|48->19|48->19|48->19|49->20|49->20|49->20|50->21|50->21|50->21|51->22|51->22|51->22|52->23|52->23|52->23|53->24|53->24|53->24|54->26|54->26|56->26|57->27|57->27|57->27|57->27|58->28|58->28|58->28|60->1|62->11|64->25|65->29|66->30|66->30|66->30|68->32|69->33|69->33|69->33|70->34|71->35|72->36|72->36|72->36|74->38|74->38|74->38|74->38|75->39|76->40|78->42|78->42|78->42|78->42|79->43|81->45|81->45|81->45|85->49|85->49|86->50|86->50|87->51|87->51|88->52|88->52|89->53|91->55|91->55|91->55|92->56|93->57|93->57|93->57|94->58|94->58|94->58|94->58|95->59|97->61|105->69|105->69|106->70|107->71|107->71|108->72|111->75|111->75|111->75|112->76|112->76|112->76|112->76|112->76|112->76|112->76|112->76|113->77|115->79|120->84|120->84|123->87|133->97|133->97|133->97|168->132|168->132|168->132|169->133|170->134|170->134|170->134|171->135|172->136|174->138|176->140|176->140|176->140|177->141|177->141|177->141|177->141|177->141|177->141|177->141|178->142|178->142|178->142|179->143|179->143|179->143|179->143|179->143|179->143|179->143|180->144|182->146|187->151|187->151|187->151|187->151|187->151|187->151|188->152|188->152|188->152|188->152|188->152|188->152|193->157|193->157|193->157|193->157|193->157|193->157|198->162|198->162|198->162|198->162|198->162|198->162|205->169|205->169|205->169|206->170|207->171|207->171|207->171|208->172|209->173|212->176|228->192|228->192|228->192|228->192|230->194|230->194|230->194|231->195|232->196|235->199|238->202|238->202|238->202|238->202|238->202|238->202|244->208|244->208|244->208|244->208|245->209|245->209|245->209|245->209|245->209|245->209|246->210|246->210|246->210|246->210|246->210|246->210|247->211|247->211|247->211|248->212|248->212|248->212|248->212|248->212|248->212|248->212|249->213|249->213|249->213|249->213|249->213|249->213|250->214|251->215|255->219|255->219|255->219|255->219|256->220|256->220|256->220|256->220|256->220|256->220|257->221|257->221|257->221|258->222|258->222|258->222|258->222|258->222|258->222|258->222|259->223|261->225|270->234|270->234|270->234|270->234|270->234|270->234|286->250|286->250|286->250|287->251|288->252|289->253|295->259|295->259|295->259|296->260|297->261|298->262|299->263|301->265|312->276|312->276|312->276|321->285|321->285|321->285|322->286|322->286|322->286|322->286|323->287|325->289|329->293|330->294
                  -- GENERATED --
              */
          