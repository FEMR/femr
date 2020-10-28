
package femr.ui.views.html.history

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

object indexEncounter extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template5[femr.common.dtos.CurrentUser,femr.ui.models.history.IndexEncounterViewModel,femr.ui.models.history.IndexEncounterMedicalViewModel,femr.ui.models.history.IndexEncounterPharmacyViewModel,AssetsFinder,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(currentUser: femr.common.dtos.CurrentUser,
        viewModel: femr.ui.models.history.IndexEncounterViewModel,
        viewModelMedical: femr.ui.models.history.IndexEncounterMedicalViewModel,
        viewModelPharmacy: femr.ui.models.history.IndexEncounterPharmacyViewModel,
        assets: AssetsFinder):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {
/*7.6*/import femr.ui.views.html.layouts.main
/*8.6*/import femr.ui.views.html.partials.helpers.outputStringOrNA
/*9.6*/import femr.ui.views.html.partials.helpers.outputIntOrNA
/*10.6*/import femr.ui.views.html.partials.helpers.outputHeightOrNA
/*11.6*/import femr.ui.views.html.partials.helpers.outputWeightOrNA
/*12.6*/import femr.ui.views.html.partials.helpers.outputBloodPressureOrNA
/*13.6*/import femr.ui.views.html.partials.helpers.outputYesOrNA
/*15.6*/import femr.ui.views.html.partials.helpers.outputTemperatureOrNA
/*17.6*/import femr.ui.views.html.partials.encounter.dynamicTabSpan
/*18.6*/import femr.data.models.mysql.Roles
/*20.6*/import collection.JavaConversions._
/*21.6*/import femr.ui.controllers.routes.PDFController
/*22.6*/import femr.ui.controllers.routes.TriageController

def /*27.6*/additionalScripts/*27.23*/:play.twirl.api.HtmlFormat.Appendable = {_display_(

Seq[Any](format.raw/*27.27*/("""
        """),format.raw/*28.9*/("""<script type="text/javascript" src=""""),_display_(/*28.46*/assets/*28.52*/.path("js/libraries/jquery-2.2.4.min.js")),format.raw/*28.93*/(""""></script>
        <script type="text/javascript" src=""""),_display_(/*29.46*/assets/*29.52*/.path("js/libraries/jquery.tablescroll.js")),format.raw/*29.95*/(""""></script>
        <script type="text/javascript" src=""""),_display_(/*30.46*/assets/*30.52*/.path("js/history/history.js")),format.raw/*30.82*/(""""></script>
    """)))};def /*32.6*/additionalStyles/*32.22*/:play.twirl.api.HtmlFormat.Appendable = {_display_(

Seq[Any](format.raw/*32.26*/("""
        """),format.raw/*33.9*/("""<link rel="stylesheet" href=""""),_display_(/*33.39*/assets/*33.45*/.path("css/libraries/jquery.tablescroll.css")),format.raw/*33.90*/("""">
        <link rel="stylesheet" href=""""),_display_(/*34.39*/assets/*34.45*/.path("css/history.css")),format.raw/*34.69*/("""">
    """)))};def /*25.6*/roles/*25.11*/ = {{currentUser.getRoles().map(r => r.getId())}};def /*38.6*/editClass/*38.15*/ = {{if(viewModel.getPatientEncounterItem.getIsClosed) "editable" else "nonEditable" }};
Seq[Any](format.raw/*5.30*/("""

    """),format.raw/*8.1*/("""    """),format.raw/*9.1*/("""    """),format.raw/*10.1*/("""    """),format.raw/*11.1*/("""    """),format.raw/*12.1*/("""    """),format.raw/*13.1*/("""    """),format.raw/*14.1*/("""
    """),format.raw/*16.1*/("""
    """),format.raw/*18.1*/("""    """),format.raw/*19.1*/("""
    """),format.raw/*21.1*/("""    """),format.raw/*22.1*/("""    """),format.raw/*23.1*/("""
    """),format.raw/*24.64*/("""
    """),format.raw/*25.59*/("""

    """),format.raw/*31.6*/("""
    """),format.raw/*35.6*/("""

    """),format.raw/*37.90*/("""
    """),format.raw/*38.101*/("""

    """),_display_(/*40.6*/main("History", currentUser, scripts = additionalScripts, styles = additionalStyles, assets = assets)/*40.107*/ {_display_(Seq[Any](format.raw/*40.109*/("""
        """),format.raw/*41.9*/("""<div class="sectionBackground backgroundForWrap" id="encounterViewWrap">
            <div id="encounterViewHeader">


                <img class="" height="90" width="90" src=""""),_display_(/*45.60*/viewModel/*45.69*/.getPatientItem.getPathToPhoto),format.raw/*45.99*/("""" />
                <p>"""),_display_(/*46.21*/viewModel/*46.30*/.getPatientItem.getFirstName),format.raw/*46.58*/(""" """),_display_(/*46.60*/viewModel/*46.69*/.getPatientItem.getLastName),format.raw/*46.96*/("""
                    """),format.raw/*47.21*/("""("""),_display_(/*47.23*/viewModel/*47.32*/.getPatientItem.getId),format.raw/*47.53*/(""")</p>

                <a href=""""),_display_(/*49.27*/PDFController/*49.40*/.index(viewModel.getPatientEncounterItem.getId)),format.raw/*49.87*/("""" target="_blank" id="historyButton" class="btn btn-default pull-left">Generate PDF</a>

                """),_display_(/*51.18*/if(roles.contains(Roles.ADMINISTRATOR) || roles.contains(Roles.SUPERUSER) )/*51.93*/ {_display_(Seq[Any](format.raw/*51.95*/("""
                    """),_display_(/*52.22*/helper/*52.28*/.form(action = TriageController.deleteEncounterPost(viewModel.getPatientItem.getId, viewModel.getPatientEncounterItem.getId))/*52.153*/ {_display_(Seq[Any](format.raw/*52.155*/("""
                        """),format.raw/*53.25*/("""<input type="hidden" name="reasonEncounterDeleted" class="reasonEncounterDeleted" />
                        <button hidden="true" type="submit" class="deleteEcounter"></button>

                        <span>
                            <button type="submit" class="deleteEncounterbtn btn btn-danger pull-right" >
                                Delete this Encounter</button>
                        </span>
                    """)))}),format.raw/*60.22*/("""
                """)))}),format.raw/*61.18*/("""


            """),format.raw/*64.13*/("""</div>

            <div class="encounterViewBodyHeader">
                <p>Basic Information and Vitals</p>
            </div>
            <div class="encounterViewBody">
                <input type="hidden" id="patientEncounterId" value=""""),_display_(/*70.70*/viewModel/*70.79*/.getPatientEncounterItem.getId),format.raw/*70.109*/("""" />
                <div class="encounterViewBodyLeft">
                    <div class="row">
                """),_display_(/*73.18*/defining(viewModel.getPatientItem)/*73.52*/ { patient =>_display_(Seq[Any](format.raw/*73.65*/("""
                    """),format.raw/*74.21*/("""<div class="encounterViewBodyLeftHalf">
                        <p><span class="infoLabel">First Name:</span> """),_display_(/*75.72*/outputStringOrNA(patient.getFirstName)),format.raw/*75.110*/("""</p>
                        <p><span class="infoLabel">Last Name:</span> """),_display_(/*76.71*/outputStringOrNA(patient.getLastName)),format.raw/*76.108*/("""</p>
                        <p><span class="infoLabel">Address:</span> """),_display_(/*77.69*/outputStringOrNA(patient.getAddress)),format.raw/*77.105*/("""</p>
                        <p><span class="infoLabel">Phone Number:</span> """),_display_(/*78.74*/outputStringOrNA(patient.getPhoneNumber)),format.raw/*78.114*/("""</p>
                        <p><span class="infoLabel">Birthday:</span> """),_display_(/*79.70*/outputStringOrNA(patient.getFriendlyDateOfBirth)),format.raw/*79.118*/("""</p>
                        <p><span class="infoLabel">Age:</span> """),_display_(/*80.65*/outputStringOrNA(patient.getAge)),format.raw/*80.97*/("""</p>
                        <p><span class="infoLabel">Gender:</span> """),_display_(/*81.68*/outputStringOrNA(patient.getSex)),format.raw/*81.100*/("""</p>
                    </div>
                    <div class="encounterViewBodyLeftHalf">
                        <!-- """),format.raw/*84.43*/(""" """),format.raw/*84.44*/("""-->
                        """),_display_(/*85.26*/defining(viewModelMedical.getVitalList)/*85.65*/ { vitalMap =>_display_(Seq[Any](format.raw/*85.79*/("""
                            """),format.raw/*86.29*/("""<p>
                                <span class="infoLabel">Weeks Pregnant:</span>
                                """),_display_(/*88.34*/for(dateIndex <- 1 to vitalMap.getDateListChronological.size) yield /*88.95*/ {_display_(Seq[Any](format.raw/*88.97*/("""
                                    """),format.raw/*89.37*/("""<span>
                                    """),_display_(/*90.38*/outputStringOrNA(vitalMap.get("weeksPregnant", vitalMap.getDate(dateIndex - 1)))),format.raw/*90.118*/("""
                                    """),format.raw/*91.37*/("""</span>
                                """)))}),format.raw/*92.34*/("""
                            """),format.raw/*93.29*/("""</p>

                        <p><span class="infoLabel">Height:</span>
                            """),_display_(/*96.30*/defining(viewModelMedical.getVitalList)/*96.69*/ { vitalMap =>_display_(Seq[Any](format.raw/*96.83*/("""
                                """),_display_(/*97.34*/for(dateIndex <- 1 to vitalMap.getDateListChronological.size) yield /*97.95*/ {_display_(Seq[Any](format.raw/*97.97*/("""
                                    """),_display_(/*98.38*/if(viewModelMedical.getSettings.isMetric)/*98.79*/ {_display_(Seq[Any](format.raw/*98.81*/("""
                                        """),_display_(/*99.42*/outputHeightOrNA(
                                            vitalMap.get("heightMeters", vitalMap.getDate(dateIndex - 1)),
                                            vitalMap.get("heightCm", vitalMap.getDate(dateIndex - 1)),
                                            viewModelMedical.getSettings.isMetric,
                                            ""
                                        ))))}/*104.43*/else/*104.48*/{_display_(Seq[Any](format.raw/*104.49*/("""
                                        """),_display_(/*105.42*/outputHeightOrNA(
                                            vitalMap.get("heightFeet", vitalMap.getDate(dateIndex - 1)),
                                            vitalMap.get("heightInches", vitalMap.getDate(dateIndex - 1)),
                                            viewModelMedical.getSettings.isMetric,
                                            ""
                                        )),format.raw/*110.42*/("""
                                    """)))}),format.raw/*111.38*/("""
                                """)))}),format.raw/*112.34*/("""
                        """),format.raw/*113.25*/("""</p>

                        <p><span class="infoLabel">Weight:</span>
                        """),_display_(/*116.26*/for(dateIndex <- 1 to vitalMap.getDateListChronological.size) yield /*116.87*/ {_display_(Seq[Any](format.raw/*116.89*/("""

                            """),_display_(/*118.30*/if(viewModelMedical.getSettings.isMetric)/*118.71*/ {_display_(Seq[Any](format.raw/*118.73*/("""
                                """),_display_(/*119.34*/outputWeightOrNA(vitalMap.get("weightKgs", vitalMap.getDate(dateIndex - 1)), viewModelMedical.getSettings.isMetric, "")),format.raw/*119.153*/("""

                            """)))}/*121.31*/else/*121.36*/{_display_(Seq[Any](format.raw/*121.37*/("""
                                """),_display_(/*122.34*/outputWeightOrNA(vitalMap.get("weight", vitalMap.getDate(dateIndex - 1)), viewModelMedical.getSettings.isMetric, "")),format.raw/*122.150*/("""

                            """)))}),format.raw/*124.30*/("""

                            """)))}),format.raw/*126.30*/("""

                        """),format.raw/*128.25*/("""</p>

                    """)))}),format.raw/*130.22*/("""
                        """),_display_(/*131.26*/defining(viewModelMedical.getVitalList)/*131.65*/ { vitalMap =>_display_(Seq[Any](format.raw/*131.79*/("""

                            """),format.raw/*133.29*/("""<p><span class="infoLabel">Smoking:</span>
                                """),_display_(/*134.34*/for(dateIndex <- 1 to vitalMap.getDateListChronological.size) yield /*134.95*/ {_display_(Seq[Any](format.raw/*134.97*/("""
                                    """),format.raw/*135.37*/("""<span>
                                    """),_display_(/*136.38*/outputYesOrNA(vitalMap.get("smoker", vitalMap.getDate(dateIndex - 1)))),format.raw/*136.108*/("""
                                    """),format.raw/*137.37*/("""</span>
                                """)))}),format.raw/*138.34*/("""
                                """),format.raw/*139.33*/("""</p>

                            <p><span class="infoLabel">Diabetes:</span>
                                """),_display_(/*142.34*/for(dateIndex <- 1 to vitalMap.getDateListChronological.size) yield /*142.95*/ {_display_(Seq[Any](format.raw/*142.97*/("""
                                    """),format.raw/*143.37*/("""<span>
                                    """),_display_(/*144.38*/outputYesOrNA(vitalMap.get("diabetic", vitalMap.getDate(dateIndex - 1)))),format.raw/*144.110*/("""
                                    """),format.raw/*145.37*/("""</span>
                                """)))}),format.raw/*146.34*/("""
                            """),format.raw/*147.29*/("""</p>

                            <p><span class="infoLabel">Alcohol:</span>
                                """),_display_(/*150.34*/for(dateIndex <- 1 to vitalMap.getDateListChronological.size) yield /*150.95*/ {_display_(Seq[Any](format.raw/*150.97*/("""
                                    """),format.raw/*151.37*/("""<span>
                                        """),_display_(/*152.42*/outputYesOrNA(vitalMap.get("alcohol", vitalMap.getDate(dateIndex - 1)))),format.raw/*152.113*/("""
                                    """)))}),format.raw/*153.38*/("""       """),format.raw/*153.45*/("""</span>
                            </p>
                        """)))}),format.raw/*155.26*/("""

                        """),format.raw/*157.25*/("""</div>

                """)))}),format.raw/*159.18*/("""
                    """),format.raw/*160.21*/("""</div>
                </div>
                <div class="encounterViewBodyMiddle">
                """),_display_(/*163.18*/defining(viewModel.getPatientEncounterItem)/*163.61*/ { encounter =>_display_(Seq[Any](format.raw/*163.76*/("""
                    """),format.raw/*164.108*/("""
                    """),format.raw/*165.101*/("""
                    """),format.raw/*166.29*/("""
                    """),format.raw/*167.110*/("""
                    """),format.raw/*168.109*/("""
                    """),format.raw/*169.29*/("""
                    """),format.raw/*170.112*/("""
                    """),format.raw/*171.111*/("""

                    """),format.raw/*173.21*/("""<p><span class="infoLabel">Triage Visit:</span> """),_display_(/*173.70*/encounter/*173.79*/.getTriageDateOfVisit),format.raw/*173.100*/("""</p>
                    <p><span class="infoLabel">Nurse:</span> """),_display_(/*174.63*/encounter/*174.72*/.getNurseFullName),format.raw/*174.89*/("""</p> """),format.raw/*174.111*/("""
                    """),format.raw/*175.21*/("""<br>
                    <p><span class="infoLabel">Medical Visit:</span> """),_display_(/*176.71*/encounter/*176.80*/.getMedicalDateOfVisit),format.raw/*176.102*/("""</p>
                    <p><span class="infoLabel">Physician:</span> """),_display_(/*177.67*/encounter/*177.76*/.getPhysicianFullName),format.raw/*177.97*/("""</p> """),format.raw/*177.119*/("""
                    """),format.raw/*178.21*/("""<br>
                    <p><span class="infoLabel">Pharmacy Visit:</span> """),_display_(/*179.72*/encounter/*179.81*/.getPharmacyDateOfVisit),format.raw/*179.104*/("""</p>
                    <p><span class="infoLabel">Pharmacist:</span> """),_display_(/*180.68*/encounter/*180.77*/.getPharmacistFullName),format.raw/*180.99*/("""</p> """),format.raw/*180.121*/("""


                """)))}),format.raw/*183.18*/("""
                """),format.raw/*184.17*/("""</div>

                <div class="encounterViewBodyRight">
                """),_display_(/*187.18*/defining(viewModelMedical.getVitalList)/*187.57*/ { vitalMap =>_display_(Seq[Any](format.raw/*187.71*/("""
                    """),format.raw/*188.21*/("""<p><span class="infoLabel">Temperature:</span>
                    """),_display_(/*189.22*/for(dateIndex <- 1 to vitalMap.getDateListChronological.size) yield /*189.83*/ {_display_(Seq[Any](format.raw/*189.85*/("""
                        """),_display_(/*190.26*/if(viewModelMedical.getSettings.isMetric)/*190.67*/ {_display_(Seq[Any](format.raw/*190.69*/("""
                            """),_display_(/*191.30*/outputTemperatureOrNA(vitalMap.get("temperatureCelsius", vitalMap.getDate(dateIndex - 1)), viewModelMedical.getSettings.isMetric, "")),format.raw/*191.163*/("""
                        """)))}/*192.27*/else/*192.32*/{_display_(Seq[Any](format.raw/*192.33*/("""
                            """),_display_(/*193.30*/outputTemperatureOrNA(vitalMap.get("temperature", vitalMap.getDate(dateIndex - 1)), viewModelMedical.getSettings.isMetric, "")),format.raw/*193.156*/("""

                        """)))}),format.raw/*195.26*/("""
                    """)))}),format.raw/*196.22*/("""
                        """),format.raw/*197.25*/("""</p>

                    <p>
                        <span class="infoLabel">Blood Pressure:</span>
                        """),_display_(/*201.26*/for(dateIndex <- 1 to vitalMap.getDateListChronological.size) yield /*201.87*/ {_display_(Seq[Any](format.raw/*201.89*/("""
                            """),format.raw/*202.29*/("""<span>
                            """),_display_(/*203.30*/outputBloodPressureOrNA(vitalMap.get("bloodPressureSystolic", vitalMap.getDate(dateIndex - 1)), vitalMap.get("bloodPressureDiastolic", vitalMap.getDate(dateIndex - 1)))),format.raw/*203.198*/("""
                            """),format.raw/*204.29*/("""</span>
                        """)))}),format.raw/*205.26*/("""
                    """),format.raw/*206.21*/("""</p>
                    <p>
                        <span class="infoLabel">Heart Rate:</span>
                        """),_display_(/*209.26*/for(dateIndex <- 1 to vitalMap.getDateListChronological.size) yield /*209.87*/ {_display_(Seq[Any](format.raw/*209.89*/("""
                            """),format.raw/*210.29*/("""<span>
                             """),_display_(/*211.31*/outputStringOrNA(vitalMap.get("heartRate", vitalMap.getDate(dateIndex - 1)))),format.raw/*211.107*/("""
                            """),format.raw/*212.29*/("""</span>
                        """)))}),format.raw/*213.26*/("""
                    """),format.raw/*214.21*/("""</p>
                    <p>
                        <span class="infoLabel">Respiration Rate:</span>
                        """),_display_(/*217.26*/for(dateIndex <- 1 to vitalMap.getDateListChronological.size) yield /*217.87*/ {_display_(Seq[Any](format.raw/*217.89*/("""
                            """),format.raw/*218.29*/("""<span>
                            """),_display_(/*219.30*/outputStringOrNA(vitalMap.get("respiratoryRate", vitalMap.getDate(dateIndex - 1)))),format.raw/*219.112*/("""
                            """),format.raw/*220.29*/("""</span>
                        """)))}),format.raw/*221.26*/("""
                    """),format.raw/*222.21*/("""</p>
                    <p>
                        <span class="infoLabel">Oxygen Saturation:</span>
                        """),_display_(/*225.26*/for(dateIndex <- 1 to vitalMap.getDateListChronological.size) yield /*225.87*/ {_display_(Seq[Any](format.raw/*225.89*/("""
                            """),format.raw/*226.29*/("""<span>
                            """),_display_(/*227.30*/outputStringOrNA(vitalMap.get("oxygenSaturation", vitalMap.getDate(dateIndex - 1)))),format.raw/*227.113*/("""
                            """),format.raw/*228.29*/("""</span>
                        """)))}),format.raw/*229.26*/("""
                    """),format.raw/*230.21*/("""</p>
                    <p>
                        <span class="infoLabel">Glucose:</span>
                        """),_display_(/*233.26*/for(dateIndex <- 1 to vitalMap.getDateListChronological.size) yield /*233.87*/ {_display_(Seq[Any](format.raw/*233.89*/("""
                            """),format.raw/*234.29*/("""<span>
                            """),_display_(/*235.30*/outputStringOrNA(vitalMap.get("glucose", vitalMap.getDate(dateIndex - 1)))),format.raw/*235.104*/("""
                            """),format.raw/*236.29*/("""</span>
                        """)))}),format.raw/*237.26*/("""
                    """),format.raw/*238.21*/("""</p>
                """)))}),format.raw/*239.18*/("""
                """),format.raw/*240.17*/("""</div>
            </div>


            <div class="encounterViewBodyHeader">
                <p>Assessment</p>
            </div>
            <div class="encounterViewBody">
                <div class="encounterViewBodyLeft">
                    """),format.raw/*249.69*/("""
                    """),_display_(/*250.22*/defining(viewModelMedical.getPmhFields)/*250.61*/ { pmhFieldMap =>_display_(Seq[Any](format.raw/*250.78*/("""
                        """),format.raw/*251.25*/("""<p><a class="infoLabel """),_display_(/*251.49*/editClass),format.raw/*251.58*/(""""> Medical/Surgical History:</a>
                            """),_display_(/*252.30*/dynamicTabSpan(pmhFieldMap.get("medicalSurgicalHistory"))),format.raw/*252.87*/("""
                        """),format.raw/*253.25*/("""</p>
                        <p><a class="infoLabel """),_display_(/*254.49*/editClass),format.raw/*254.58*/(""""> Social History:</a>
                            """),_display_(/*255.30*/dynamicTabSpan(pmhFieldMap.get("socialHistory"))),format.raw/*255.78*/("""
                        """),format.raw/*256.25*/("""<p><a class="infoLabel """),_display_(/*256.49*/editClass),format.raw/*256.58*/("""">Current Medications:</a>
                            """),_display_(/*257.30*/dynamicTabSpan(pmhFieldMap.get("currentMedication"))),format.raw/*257.82*/("""
                        """),format.raw/*258.25*/("""<p><a class="infoLabel """),_display_(/*258.49*/editClass),format.raw/*258.58*/(""""> Family History: </a>
                            """),_display_(/*259.30*/dynamicTabSpan(pmhFieldMap.get("familyHistory"))),format.raw/*259.78*/("""
                    """)))}),format.raw/*260.22*/("""
                    """),_display_(/*261.22*/defining(viewModelMedical.getTreatmentFields)/*261.67*/ { treatmentFieldMap =>_display_(Seq[Any](format.raw/*261.90*/("""
                        """),format.raw/*262.25*/("""<p><a  class="infoLabel """),_display_(/*262.50*/editClass),format.raw/*262.59*/(""""> Assessment: </a>
                            """),_display_(/*263.30*/dynamicTabSpan(treatmentFieldMap.get("assessment"))),format.raw/*263.81*/("""

                        """),format.raw/*265.25*/("""<p><a class="infoLabel """),_display_(/*265.49*/editClass),format.raw/*265.58*/("""">Procedure/Counseling:</a>
                            """),_display_(/*266.30*/dynamicTabSpan(treatmentFieldMap.get("procedure_counseling"))),format.raw/*266.91*/("""
                    """)))}),format.raw/*267.22*/("""
                    """),_display_(/*268.22*/for(problemIterator <- 1 to viewModelPharmacy.getProblems.size) yield /*268.85*/{_display_(Seq[Any](format.raw/*268.86*/("""
                        """),format.raw/*269.25*/("""<p><a class="infoLabel nonEditable">Diagnosis:</a>
                            <span class="value">"""),_display_(/*270.50*/outputStringOrNA(viewModelPharmacy.getProblems.get(problemIterator - 1))),format.raw/*270.122*/("""</span></p>
                    """)))}),format.raw/*271.22*/("""
                    """),_display_(/*272.22*/if(!viewModelPharmacy.getPrescriptions.isEmpty())/*272.71*/{_display_(Seq[Any](format.raw/*272.72*/("""
                        """),format.raw/*273.25*/("""<p> <span class="infoLabel">Dispensed Medication(s):</span> </p>

                        """),_display_(/*275.26*/for(x <- 1 to viewModelPharmacy.getPrescriptions.size) yield /*275.80*/ {_display_(Seq[Any](format.raw/*275.82*/("""
                            """),_display_(/*276.30*/defining(viewModelPharmacy.getPrescriptions.get(x - 1))/*276.85*/ { prescription =>_display_(Seq[Any](format.raw/*276.103*/("""
                                """),format.raw/*277.33*/("""<div class="prescription-row">
                                  """),_display_(/*278.36*/if(prescription.getReplacementMedicationName != null)/*278.89*/ {_display_(Seq[Any](format.raw/*278.91*/("""
                                          """),format.raw/*279.43*/("""<p>
                                              <span class="prescription-id">Prescription #"""),_display_(/*280.92*/prescription/*280.104*/.getId()),format.raw/*280.112*/(""" """),format.raw/*280.113*/("""- <span class="red">Replaced</span></span>
                                              <del>"""),_display_(/*281.53*/prescription/*281.65*/.printFullPrescriptionName),format.raw/*281.91*/("""</del>
                                          </p>
                                      <div class="alert">This prescription was replaced by prescription #"""),_display_(/*283.107*/prescription/*283.119*/.getReplacementId),format.raw/*283.136*/("""</div>
                                  """)))}/*284.37*/else/*284.42*/{_display_(Seq[Any](format.raw/*284.43*/("""
                                      """),format.raw/*285.39*/("""<p>
                                          <span class="prescription-id">Prescription #"""),_display_(/*286.88*/prescription/*286.100*/.getId()),format.raw/*286.108*/("""</span>
                                          """),_display_(/*287.44*/prescription/*287.56*/.printFullPrescriptionName),format.raw/*287.82*/("""
                                      """),format.raw/*288.39*/("""</p>
                                  """)))}),format.raw/*289.36*/("""
                                """),format.raw/*290.33*/("""</div>
                            """)))}),format.raw/*291.30*/("""
                        """)))}),format.raw/*292.26*/("""

                    """)))}),format.raw/*294.22*/("""


                """),format.raw/*297.17*/("""</div>
                <div class="encounterViewBodyMiddle">
                """),_display_(/*299.18*/if(viewModelMedical.isMultipleChiefComplaints)/*299.64*/ {_display_(Seq[Any](format.raw/*299.66*/("""
                    """),_display_(/*300.22*/for((key, value) <- viewModelMedical.getHpiFieldsWithMultipleChiefComplaints) yield /*300.99*/ {_display_(Seq[Any](format.raw/*300.101*/("""
                        """),format.raw/*301.25*/("""<div>
                        <h4 data-complaint=""""),_display_(/*302.46*/key),format.raw/*302.49*/("""">Chief Complaint: """),_display_(/*302.69*/key),format.raw/*302.72*/("""</h4>
                        <p> <a class="infoLabel """),_display_(/*303.50*/editClass),format.raw/*303.59*/("""">Onset:</a>
                            """),_display_(/*304.30*/dynamicTabSpan(viewModelMedical.getHpiFieldsWithMultipleChiefComplaints.get(key).get("onset"))),format.raw/*304.124*/("""
                        """),format.raw/*305.25*/("""</p>
                        <p> <a class="infoLabel """),_display_(/*306.50*/editClass),format.raw/*306.59*/(""""> Quality: </a>
                            """),_display_(/*307.30*/dynamicTabSpan(viewModelMedical.getHpiFieldsWithMultipleChiefComplaints.get(key).get("quality"))),format.raw/*307.126*/("""
                        """),format.raw/*308.25*/("""</p>
                        <p> <a class="infoLabel """),_display_(/*309.50*/editClass),format.raw/*309.59*/("""">Radiation:</a>
                            """),_display_(/*310.30*/dynamicTabSpan(viewModelMedical.getHpiFieldsWithMultipleChiefComplaints.get(key).get("radiation"))),format.raw/*310.128*/("""
                        """),format.raw/*311.25*/("""</p>
                        <p> <a class="infoLabel """),_display_(/*312.50*/editClass),format.raw/*312.59*/("""">Severity:</a>
                            """),_display_(/*313.30*/dynamicTabSpan(viewModelMedical.getHpiFieldsWithMultipleChiefComplaints.get(key).get("severity"))),format.raw/*313.127*/("""
                        """),format.raw/*314.25*/("""</p>
                        <p> <a class="infoLabel """),_display_(/*315.50*/editClass),format.raw/*315.59*/("""">Provokes:</a>
                            """),_display_(/*316.30*/dynamicTabSpan(viewModelMedical.getHpiFieldsWithMultipleChiefComplaints.get(key).get("provokes"))),format.raw/*316.127*/("""
                        """),format.raw/*317.25*/("""</p>
                        <p> <a class="infoLabel """),_display_(/*318.50*/editClass),format.raw/*318.59*/("""">Palliates:</a>
                            """),_display_(/*319.30*/dynamicTabSpan(viewModelMedical.getHpiFieldsWithMultipleChiefComplaints.get(key).get("palliates"))),format.raw/*319.128*/("""
                        """),format.raw/*320.25*/("""</p>

                        <p> <a class="infoLabel """),_display_(/*322.50*/editClass),format.raw/*322.59*/("""">Narrative:</a>
                            """),_display_(/*323.30*/dynamicTabSpan(viewModelMedical.getHpiFieldsWithMultipleChiefComplaints.get(key).get("narrative"))),format.raw/*323.128*/("""
                        """),format.raw/*324.25*/("""</p>
                        <p> <a class="infoLabel """),_display_(/*325.50*/editClass),format.raw/*325.59*/("""">Time Of Day:</a>
                            """),_display_(/*326.30*/dynamicTabSpan(viewModelMedical.getHpiFieldsWithMultipleChiefComplaints.get(key).get("timeOfDay"))),format.raw/*326.128*/("""
                        """),format.raw/*327.25*/("""</p>

                        <p> <a class="infoLabel """),_display_(/*329.50*/editClass),format.raw/*329.59*/("""">Physical Examination:</a>
                            """),_display_(/*330.30*/dynamicTabSpan(viewModelMedical.getHpiFieldsWithMultipleChiefComplaints.get(key).get("physicalExamination"))),format.raw/*330.138*/("""
                        """),format.raw/*331.25*/("""</p>

                        </div>
                        <br>
                    """)))}),format.raw/*335.22*/("""
                """)))}/*336.19*/else/*336.24*/{_display_(Seq[Any](format.raw/*336.25*/("""
                    """),format.raw/*337.21*/("""<div>
                    """),_display_(/*338.22*/if(viewModel.getPatientEncounterItem.getChiefComplaints != null && viewModel.getPatientEncounterItem.getChiefComplaints.size > 0)/*338.151*/{_display_(Seq[Any](format.raw/*338.152*/("""
                        """),format.raw/*339.25*/("""<h4 data-complaint=""""),_display_(/*339.46*/viewModel/*339.55*/.getPatientEncounterItem.getChiefComplaints.get(0)),format.raw/*339.105*/("""">Chief Complaint: """),_display_(/*339.125*/viewModel/*339.134*/.getPatientEncounterItem.getChiefComplaints.get(0)),format.raw/*339.184*/(""" """),format.raw/*339.185*/("""</h4>
                    """)))}),format.raw/*340.22*/("""
                    """),format.raw/*341.21*/("""<p> <a class= "infoLabel """),_display_(/*341.47*/editClass),format.raw/*341.56*/("""">Onset:</a>
                        """),_display_(/*342.26*/dynamicTabSpan(viewModelMedical.getHpiFieldsWithoutMultipleChiefComplaints.get("onset"))),format.raw/*342.114*/("""
                    """),format.raw/*343.21*/("""</p>
                    <p> <a class= "infoLabel """),_display_(/*344.47*/editClass),format.raw/*344.56*/("""">Quality:</a>
                        """),_display_(/*345.26*/dynamicTabSpan(viewModelMedical.getHpiFieldsWithoutMultipleChiefComplaints.get("quality"))),format.raw/*345.116*/("""
                    """),format.raw/*346.21*/("""</p>

                    <p> <a class= "infoLabel """),_display_(/*348.47*/editClass),format.raw/*348.56*/("""">Radiation:</a>
                    """),_display_(/*349.22*/dynamicTabSpan(viewModelMedical.getHpiFieldsWithoutMultipleChiefComplaints.get("radiation"))),format.raw/*349.114*/("""
                    """),format.raw/*350.21*/("""</p>

                    <p> <a class= "infoLabel """),_display_(/*352.47*/editClass),format.raw/*352.56*/("""">Severity:</a>
                        """),_display_(/*353.26*/dynamicTabSpan(viewModelMedical.getHpiFieldsWithoutMultipleChiefComplaints.get("severity"))),format.raw/*353.117*/("""
                    """),format.raw/*354.21*/("""</p>

                    <p> <a class= "infoLabel """),_display_(/*356.47*/editClass),format.raw/*356.56*/("""">Provokes:</a>
                        """),_display_(/*357.26*/dynamicTabSpan(viewModelMedical.getHpiFieldsWithoutMultipleChiefComplaints.get("provokes"))),format.raw/*357.117*/("""
                    """),format.raw/*358.21*/("""</p>

                    <p> <a class= "infoLabel """),_display_(/*360.47*/editClass),format.raw/*360.56*/("""">Palliates:</a>
                        """),_display_(/*361.26*/dynamicTabSpan(viewModelMedical.getHpiFieldsWithoutMultipleChiefComplaints.get("palliates"))),format.raw/*361.118*/("""
                    """),format.raw/*362.21*/("""</p>

                    <p> <a class= "infoLabel """),_display_(/*364.47*/editClass),format.raw/*364.56*/("""">Time Of Day:</a>
                        """),_display_(/*365.26*/dynamicTabSpan(viewModelMedical.getHpiFieldsWithoutMultipleChiefComplaints.get("timeOfDay"))),format.raw/*365.118*/("""
                    """),format.raw/*366.21*/("""</p>
                    <p> <a class= "infoLabel """),_display_(/*367.47*/editClass),format.raw/*367.56*/("""">Narrative:</a>
                        """),_display_(/*368.26*/dynamicTabSpan(viewModelMedical.getHpiFieldsWithoutMultipleChiefComplaints.get("narrative"))),format.raw/*368.118*/("""
                    """),format.raw/*369.21*/("""</p>

                    <p> <a class= "infoLabel """),_display_(/*371.47*/editClass),format.raw/*371.56*/("""">Physical Examination:</a>
                        """),_display_(/*372.26*/dynamicTabSpan(viewModelMedical.getHpiFieldsWithoutMultipleChiefComplaints.get("physicalExamination"))),format.raw/*372.128*/("""
                    """),format.raw/*373.21*/("""</p>
                    </div>

                """)))}),format.raw/*376.18*/("""


                """),format.raw/*379.17*/("""</div>

                <div class="encounterViewBodyRight">
                    """),format.raw/*382.58*/("""
                    """),_display_(/*383.22*/for((key, value) <- viewModelMedical.getCustomFields) yield /*383.75*/ {_display_(Seq[Any](format.raw/*383.77*/("""
                        """),format.raw/*384.25*/("""<p><span class="infoLabel nonEditable">
                            """),_display_(/*385.30*/key),format.raw/*385.33*/(""":
                        </span>
                            """),_display_(/*387.30*/outputStringOrNA(viewModelMedical.getCustomFields.get(key).getValue)),format.raw/*387.98*/("""
                        """),format.raw/*388.25*/("""</p>
                    """)))}),format.raw/*389.22*/("""

                """),format.raw/*391.17*/("""</div>
            </div>

            """),_display_(/*394.14*/if(viewModelMedical.getPhotos.size > 0)/*394.53*/ {_display_(Seq[Any](format.raw/*394.55*/("""
                """),format.raw/*395.17*/("""<div class="encounterViewBodyHeader">
                    <p>Photos</p>
                </div>
                <div class="encounterViewBody">
                """),_display_(/*399.18*/for(x <- 1 to viewModelMedical.getPhotos.size) yield /*399.64*/ {_display_(Seq[Any](format.raw/*399.66*/("""
                    """),format.raw/*400.21*/("""<img src=""""),_display_(/*400.32*/viewModelMedical/*400.48*/.getPhoto(x - 1).getImageUrl),format.raw/*400.76*/("""">
                    <p class="imageDescription">"""),_display_(/*401.50*/viewModelMedical/*401.66*/.getPhoto(x - 1).getImageDesc),format.raw/*401.95*/("""</p>
                """)))}),format.raw/*402.18*/("""

                """),format.raw/*404.17*/("""</div>
            """)))}),format.raw/*405.14*/("""

            """),format.raw/*407.13*/("""<div id="edit-form" >

                <p class="form-label"></p>
                <input type="text" id="editInput" class="form-control input-sm pull-left value" name="edit-value"  value=""/>
                <input  id = "fieldIdInput" class= "fieldId" type="hidden" name="edit-field-name" value="" />

                <button type="button" id="saveEncounterBtn" class="fButton">Save</button>
                <button type="button" id="cancelEncounterBtn" class="fButton">Cancel</button>

                <div  class="form-history">
                    <table id="tabFieldHistory" cellspacing="0">
                    </table>
                </div>
            </div>

        </div>

    """)))}),format.raw/*424.6*/("""
""")))}),format.raw/*425.2*/("""


"""))
      }
    }
  }

  def render(currentUser:femr.common.dtos.CurrentUser,viewModel:femr.ui.models.history.IndexEncounterViewModel,viewModelMedical:femr.ui.models.history.IndexEncounterMedicalViewModel,viewModelPharmacy:femr.ui.models.history.IndexEncounterPharmacyViewModel,assets:AssetsFinder): play.twirl.api.HtmlFormat.Appendable = apply(currentUser,viewModel,viewModelMedical,viewModelPharmacy,assets)

  def f:((femr.common.dtos.CurrentUser,femr.ui.models.history.IndexEncounterViewModel,femr.ui.models.history.IndexEncounterMedicalViewModel,femr.ui.models.history.IndexEncounterPharmacyViewModel,AssetsFinder) => play.twirl.api.HtmlFormat.Appendable) = (currentUser,viewModel,viewModelMedical,viewModelPharmacy,assets) => apply(currentUser,viewModel,viewModelMedical,viewModelPharmacy,assets)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  DATE: Tue Oct 27 21:59:18 PDT 2020
                  SOURCE: C:/Users/Noel/Documents/csc402/lemur-femr/femr/app/femr/ui/views/history/indexEncounter.scala.html
                  HASH: 95c149495f8376c0b2bce648ce416a2f3c26c873
                  MATRIX: 1164->1|1545->318|1591->363|1658->429|1723->492|1791->558|1859->624|1934->697|1999->762|2072->835|2140->901|2184->945|2228->987|2284->1041|2348->1227|2374->1244|2455->1248|2492->1258|2556->1295|2571->1301|2633->1342|2718->1400|2733->1406|2797->1449|2882->1507|2897->1513|2948->1543|2989->1568|3014->1584|3095->1588|3132->1598|3189->1628|3204->1634|3270->1679|3339->1721|3354->1727|3399->1751|3431->1165|3445->1170|3507->1860|3525->1869|3641->309|3675->358|3705->424|3736->487|3767->553|3798->619|3829->692|3860->755|3893->828|3926->896|3957->938|3990->982|4021->1036|4052->1093|4086->1158|4120->1218|4155->1561|4188->1760|4224->1853|4259->1955|4294->1964|4405->2065|4446->2067|4483->2077|4691->2258|4709->2267|4760->2297|4813->2323|4831->2332|4880->2360|4909->2362|4927->2371|4975->2398|5025->2420|5054->2422|5072->2431|5114->2452|5176->2487|5198->2500|5266->2547|5401->2655|5485->2730|5525->2732|5575->2755|5590->2761|5725->2886|5766->2888|5820->2914|6289->3352|6339->3371|6385->3389|6660->3637|6678->3646|6730->3676|6872->3791|6915->3825|6966->3838|7016->3860|7155->3972|7215->4010|7318->4086|7377->4123|7478->4197|7536->4233|7642->4312|7704->4352|7806->4427|7876->4475|7973->4545|8026->4577|8126->4650|8180->4682|8332->4819|8361->4820|8418->4850|8466->4889|8518->4903|8576->4933|8721->5051|8798->5112|8838->5114|8904->5152|8976->5197|9078->5277|9144->5315|9217->5357|9275->5387|9406->5491|9454->5530|9506->5544|9568->5579|9645->5640|9685->5642|9751->5681|9801->5722|9841->5724|9911->5767|10329->6172|10343->6177|10383->6178|10454->6221|10882->6627|10953->6666|11020->6701|11075->6727|11203->6827|11281->6888|11322->6890|11383->6923|11434->6964|11475->6966|11538->7001|11680->7120|11733->7154|11747->7159|11787->7160|11850->7195|11989->7311|12054->7344|12119->7377|12176->7405|12237->7434|12292->7461|12341->7500|12394->7514|12455->7546|12560->7623|12638->7684|12679->7686|12746->7724|12819->7769|12912->7839|12979->7877|13053->7919|13116->7953|13258->8067|13336->8128|13377->8130|13444->8168|13517->8213|13612->8285|13679->8323|13753->8365|13812->8395|13953->8508|14031->8569|14072->8571|14139->8609|14216->8658|14310->8729|14381->8768|14417->8775|14517->8843|14574->8871|14633->8898|14684->8920|14816->9024|14869->9067|14923->9082|14975->9191|15027->9293|15078->9323|15130->9434|15182->9544|15233->9574|15285->9687|15337->9799|15390->9823|15467->9872|15486->9881|15530->9902|15626->9970|15645->9979|15684->9996|15719->10018|15770->10040|15874->10116|15893->10125|15938->10147|16038->10219|16057->10228|16100->10249|16135->10271|16186->10293|16291->10370|16310->10379|16356->10402|16457->10475|16476->10484|16520->10506|16555->10528|16610->10551|16657->10569|16766->10650|16815->10689|16868->10703|16919->10725|17016->10794|17094->10855|17135->10857|17190->10884|17241->10925|17282->10927|17341->10958|17497->11091|17544->11119|17558->11124|17598->11125|17657->11156|17806->11282|17867->11311|17922->11334|17977->11360|18135->11490|18213->11551|18254->11553|18313->11583|18378->11620|18569->11788|18628->11818|18694->11852|18745->11874|18897->11998|18975->12059|19016->12061|19075->12091|19141->12129|19240->12205|19299->12235|19365->12269|19416->12291|19574->12421|19652->12482|19693->12484|19752->12514|19817->12551|19922->12633|19981->12663|20047->12697|20098->12719|20257->12850|20335->12911|20376->12913|20435->12943|20500->12980|20606->13063|20665->13093|20731->13127|20782->13149|20931->13270|21009->13331|21050->13333|21109->13363|21174->13400|21271->13474|21330->13504|21396->13538|21447->13560|21502->13583|21549->13601|21834->13905|21885->13928|21934->13967|21990->13984|22045->14010|22097->14034|22128->14043|22219->14106|22298->14163|22353->14189|22435->14243|22466->14252|22547->14305|22617->14353|22672->14379|22724->14403|22755->14412|22840->14469|22914->14521|22969->14547|23021->14571|23052->14580|23134->14634|23204->14682|23259->14705|23310->14728|23365->14773|23427->14796|23482->14822|23535->14847|23566->14856|23644->14906|23717->14957|23774->14985|23826->15009|23857->15018|23943->15076|24026->15137|24081->15160|24132->15183|24212->15246|24252->15247|24307->15273|24436->15374|24531->15446|24597->15480|24648->15503|24707->15552|24747->15553|24802->15579|24923->15672|24994->15726|25035->15728|25094->15759|25159->15814|25217->15832|25280->15866|25375->15933|25438->15986|25479->15988|25552->16032|25676->16128|25699->16140|25730->16148|25761->16149|25885->16245|25907->16257|25955->16283|26146->16445|26169->16457|26209->16474|26272->16518|26286->16523|26326->16524|26395->16564|26515->16656|26538->16668|26569->16676|26649->16728|26671->16740|26719->16766|26788->16806|26861->16847|26924->16881|26993->16918|27052->16945|27109->16970|27160->16992|27268->17072|27324->17118|27365->17120|27416->17143|27510->17220|27552->17222|27607->17248|27687->17300|27712->17303|27760->17323|27785->17326|27869->17382|27900->17391|27971->17434|28088->17528|28143->17554|28226->17609|28257->17618|28332->17665|28451->17761|28506->17787|28589->17842|28620->17851|28695->17898|28816->17996|28871->18022|28954->18077|28985->18086|29059->18132|29179->18229|29234->18255|29317->18310|29348->18319|29422->18365|29542->18462|29597->18488|29680->18543|29711->18552|29786->18599|29907->18697|29962->18723|30047->18780|30078->18789|30153->18836|30274->18934|30329->18960|30412->19015|30443->19024|30520->19073|30641->19171|30696->19197|30781->19254|30812->19263|30898->19321|31029->19429|31084->19455|31207->19546|31246->19566|31260->19571|31300->19572|31351->19594|31407->19622|31547->19751|31588->19752|31643->19778|31692->19799|31711->19808|31784->19858|31833->19878|31853->19887|31926->19937|31957->19938|32017->19966|32068->19988|32122->20014|32153->20023|32220->20062|32331->20150|32382->20172|32462->20224|32493->20233|32562->20274|32675->20364|32726->20386|32808->20440|32839->20449|32906->20488|33021->20580|33072->20602|33154->20656|33185->20665|33255->20707|33369->20798|33420->20820|33502->20874|33533->20883|33603->20925|33717->21016|33768->21038|33850->21092|33881->21101|33952->21144|34067->21236|34118->21258|34200->21312|34231->21321|34304->21366|34419->21458|34470->21480|34550->21532|34581->21541|34652->21584|34767->21676|34818->21698|34900->21752|34931->21761|35013->21815|35138->21917|35189->21939|35274->21992|35325->22014|35438->22135|35489->22158|35559->22211|35600->22213|35655->22239|35753->22309|35778->22312|35871->22377|35961->22445|36016->22471|36075->22498|36124->22518|36195->22561|36244->22600|36285->22602|36332->22620|36524->22784|36587->22830|36628->22832|36679->22854|36718->22865|36744->22881|36794->22909|36875->22962|36901->22978|36952->23007|37007->23030|37056->23050|37109->23071|37154->23087|37892->23794|37926->23797
                  LINES: 28->1|35->7|36->8|37->9|38->10|39->11|40->12|41->13|42->15|43->17|44->18|45->20|46->21|47->22|49->27|49->27|51->27|52->28|52->28|52->28|52->28|53->29|53->29|53->29|54->30|54->30|54->30|55->32|55->32|57->32|58->33|58->33|58->33|58->33|59->34|59->34|59->34|60->25|60->25|60->38|60->38|61->5|63->8|63->9|63->10|63->11|63->12|63->13|63->14|64->16|65->18|65->19|66->21|66->22|66->23|67->24|68->25|70->31|71->35|73->37|74->38|76->40|76->40|76->40|77->41|81->45|81->45|81->45|82->46|82->46|82->46|82->46|82->46|82->46|83->47|83->47|83->47|83->47|85->49|85->49|85->49|87->51|87->51|87->51|88->52|88->52|88->52|88->52|89->53|96->60|97->61|100->64|106->70|106->70|106->70|109->73|109->73|109->73|110->74|111->75|111->75|112->76|112->76|113->77|113->77|114->78|114->78|115->79|115->79|116->80|116->80|117->81|117->81|120->84|120->84|121->85|121->85|121->85|122->86|124->88|124->88|124->88|125->89|126->90|126->90|127->91|128->92|129->93|132->96|132->96|132->96|133->97|133->97|133->97|134->98|134->98|134->98|135->99|140->104|140->104|140->104|141->105|146->110|147->111|148->112|149->113|152->116|152->116|152->116|154->118|154->118|154->118|155->119|155->119|157->121|157->121|157->121|158->122|158->122|160->124|162->126|164->128|166->130|167->131|167->131|167->131|169->133|170->134|170->134|170->134|171->135|172->136|172->136|173->137|174->138|175->139|178->142|178->142|178->142|179->143|180->144|180->144|181->145|182->146|183->147|186->150|186->150|186->150|187->151|188->152|188->152|189->153|189->153|191->155|193->157|195->159|196->160|199->163|199->163|199->163|200->164|201->165|202->166|203->167|204->168|205->169|206->170|207->171|209->173|209->173|209->173|209->173|210->174|210->174|210->174|210->174|211->175|212->176|212->176|212->176|213->177|213->177|213->177|213->177|214->178|215->179|215->179|215->179|216->180|216->180|216->180|216->180|219->183|220->184|223->187|223->187|223->187|224->188|225->189|225->189|225->189|226->190|226->190|226->190|227->191|227->191|228->192|228->192|228->192|229->193|229->193|231->195|232->196|233->197|237->201|237->201|237->201|238->202|239->203|239->203|240->204|241->205|242->206|245->209|245->209|245->209|246->210|247->211|247->211|248->212|249->213|250->214|253->217|253->217|253->217|254->218|255->219|255->219|256->220|257->221|258->222|261->225|261->225|261->225|262->226|263->227|263->227|264->228|265->229|266->230|269->233|269->233|269->233|270->234|271->235|271->235|272->236|273->237|274->238|275->239|276->240|285->249|286->250|286->250|286->250|287->251|287->251|287->251|288->252|288->252|289->253|290->254|290->254|291->255|291->255|292->256|292->256|292->256|293->257|293->257|294->258|294->258|294->258|295->259|295->259|296->260|297->261|297->261|297->261|298->262|298->262|298->262|299->263|299->263|301->265|301->265|301->265|302->266|302->266|303->267|304->268|304->268|304->268|305->269|306->270|306->270|307->271|308->272|308->272|308->272|309->273|311->275|311->275|311->275|312->276|312->276|312->276|313->277|314->278|314->278|314->278|315->279|316->280|316->280|316->280|316->280|317->281|317->281|317->281|319->283|319->283|319->283|320->284|320->284|320->284|321->285|322->286|322->286|322->286|323->287|323->287|323->287|324->288|325->289|326->290|327->291|328->292|330->294|333->297|335->299|335->299|335->299|336->300|336->300|336->300|337->301|338->302|338->302|338->302|338->302|339->303|339->303|340->304|340->304|341->305|342->306|342->306|343->307|343->307|344->308|345->309|345->309|346->310|346->310|347->311|348->312|348->312|349->313|349->313|350->314|351->315|351->315|352->316|352->316|353->317|354->318|354->318|355->319|355->319|356->320|358->322|358->322|359->323|359->323|360->324|361->325|361->325|362->326|362->326|363->327|365->329|365->329|366->330|366->330|367->331|371->335|372->336|372->336|372->336|373->337|374->338|374->338|374->338|375->339|375->339|375->339|375->339|375->339|375->339|375->339|375->339|376->340|377->341|377->341|377->341|378->342|378->342|379->343|380->344|380->344|381->345|381->345|382->346|384->348|384->348|385->349|385->349|386->350|388->352|388->352|389->353|389->353|390->354|392->356|392->356|393->357|393->357|394->358|396->360|396->360|397->361|397->361|398->362|400->364|400->364|401->365|401->365|402->366|403->367|403->367|404->368|404->368|405->369|407->371|407->371|408->372|408->372|409->373|412->376|415->379|418->382|419->383|419->383|419->383|420->384|421->385|421->385|423->387|423->387|424->388|425->389|427->391|430->394|430->394|430->394|431->395|435->399|435->399|435->399|436->400|436->400|436->400|436->400|437->401|437->401|437->401|438->402|440->404|441->405|443->407|460->424|461->425
                  -- GENERATED --
              */
          