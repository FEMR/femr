
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
Seq[Any](format.raw/*6.1*/("""
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
                  DATE: Wed Jan 20 18:29:27 PST 2021
                  SOURCE: /Users/noel/Desktop/CPE/femr/femr/app/femr/ui/views/history/indexEncounter.scala.html
                  HASH: c2cb5b89c384b00be1b61de59574306dca488e88
                  MATRIX: 1164->1|1541->312|1587->356|1654->421|1719->483|1787->548|1855->613|1930->685|1995->748|2068->819|2136->884|2180->926|2224->967|2280->1020|2344->1201|2370->1218|2451->1222|2487->1231|2551->1268|2566->1274|2628->1315|2712->1372|2727->1378|2791->1421|2875->1478|2890->1484|2941->1514|2981->1537|3006->1553|3087->1557|3123->1566|3180->1596|3195->1602|3261->1647|3329->1688|3344->1694|3389->1718|3420->1141|3434->1146|3496->1823|3514->1832|3629->306|3660->351|3690->416|3721->478|3752->543|3783->608|3814->680|3845->742|3877->813|3909->879|3940->920|3972->962|4003->1015|4034->1071|4067->1135|4100->1194|4133->1531|4165->1726|4199->1817|4233->1918|4266->1925|4377->2026|4418->2028|4454->2037|4658->2214|4676->2223|4727->2253|4779->2278|4797->2287|4846->2315|4875->2317|4893->2326|4941->2353|4990->2374|5019->2376|5037->2385|5079->2406|5139->2439|5161->2452|5229->2499|5362->2605|5446->2680|5486->2682|5535->2704|5550->2710|5685->2835|5726->2837|5779->2862|6241->3293|6290->3311|6333->3326|6602->3568|6620->3577|6672->3607|6811->3719|6854->3753|6905->3766|6954->3787|7092->3898|7152->3936|7254->4011|7313->4048|7413->4121|7471->4157|7576->4235|7638->4275|7739->4349|7809->4397|7905->4466|7958->4498|8057->4570|8111->4602|8260->4736|8289->4737|8345->4766|8393->4805|8445->4819|8502->4848|8645->4964|8722->5025|8762->5027|8827->5064|8898->5108|9000->5188|9065->5225|9137->5266|9194->5295|9322->5396|9370->5435|9422->5449|9483->5483|9560->5544|9600->5546|9665->5584|9715->5625|9755->5627|9824->5669|10237->6069|10251->6074|10291->6075|10361->6117|10784->6518|10854->6556|10920->6590|10974->6615|11099->6712|11177->6773|11218->6775|11277->6806|11328->6847|11369->6849|11431->6883|11573->7002|11624->7034|11638->7039|11678->7040|11740->7074|11879->7190|11942->7221|12005->7252|12060->7278|12119->7305|12173->7331|12222->7370|12275->7384|12334->7414|12438->7490|12516->7551|12557->7553|12623->7590|12695->7634|12788->7704|12854->7741|12927->7782|12989->7815|13128->7926|13206->7987|13247->7989|13313->8026|13385->8070|13480->8142|13546->8179|13619->8220|13677->8249|13815->8359|13893->8420|13934->8422|14000->8459|14076->8507|14170->8578|14240->8616|14276->8623|14374->8689|14429->8715|14486->8740|14536->8761|14665->8862|14718->8905|14772->8920|14823->9028|14874->9129|14924->9158|14975->9268|15026->9377|15076->9406|15127->9518|15178->9629|15229->9651|15306->9700|15325->9709|15369->9730|15464->9797|15483->9806|15522->9823|15557->9845|15607->9866|15710->9941|15729->9950|15774->9972|15873->10043|15892->10052|15935->10073|15970->10095|16020->10116|16124->10192|16143->10201|16189->10224|16289->10296|16308->10305|16352->10327|16387->10349|16439->10369|16485->10386|16591->10464|16640->10503|16693->10517|16743->10538|16839->10606|16917->10667|16958->10669|17012->10695|17063->10736|17104->10738|17162->10768|17318->10901|17364->10928|17378->10933|17418->10934|17476->10964|17625->11090|17684->11117|17738->11139|17792->11164|17946->11290|18024->11351|18065->11353|18123->11382|18187->11418|18378->11586|18436->11615|18501->11648|18551->11669|18700->11790|18778->11851|18819->11853|18877->11882|18942->11919|19041->11995|19099->12024|19164->12057|19214->12078|19369->12205|19447->12266|19488->12268|19546->12297|19610->12333|19715->12415|19773->12444|19838->12477|19888->12498|20044->12626|20122->12687|20163->12689|20221->12718|20285->12754|20391->12837|20449->12866|20514->12899|20564->12920|20710->13038|20788->13099|20829->13101|20887->13130|20951->13166|21048->13240|21106->13269|21171->13302|21221->13323|21275->13345|21321->13362|21597->13657|21647->13679|21696->13718|21752->13735|21806->13760|21858->13784|21889->13793|21979->13855|22058->13912|22112->13937|22193->13990|22224->13999|22304->14051|22374->14099|22428->14124|22480->14148|22511->14157|22595->14213|22669->14265|22723->14290|22775->14314|22806->14323|22887->14376|22957->14424|23011->14446|23061->14468|23116->14513|23178->14536|23232->14561|23285->14586|23316->14595|23393->14644|23466->14695|23521->14721|23573->14745|23604->14754|23689->14811|23772->14872|23826->14894|23876->14916|23956->14979|23996->14980|24050->15005|24178->15105|24273->15177|24338->15210|24388->15232|24447->15281|24487->15282|24541->15307|24660->15398|24731->15452|24772->15454|24830->15484|24895->15539|24953->15557|25015->15590|25109->15656|25172->15709|25213->15711|25285->15754|25408->15849|25431->15861|25462->15869|25493->15870|25616->15965|25638->15977|25686->16003|25875->16163|25898->16175|25938->16192|26000->16235|26014->16240|26054->16241|26122->16280|26241->16371|26264->16383|26295->16391|26374->16442|26396->16454|26444->16480|26512->16519|26584->16559|26646->16592|26714->16628|26772->16654|26827->16677|26875->16696|26981->16774|27037->16820|27078->16822|27128->16844|27222->16921|27264->16923|27318->16948|27397->16999|27422->17002|27470->17022|27495->17025|27578->17080|27609->17089|27679->17131|27796->17225|27850->17250|27932->17304|27963->17313|28037->17359|28156->17455|28210->17480|28292->17534|28323->17543|28397->17589|28518->17687|28572->17712|28654->17766|28685->17775|28758->17820|28878->17917|28932->17942|29014->17996|29045->18005|29118->18050|29238->18147|29292->18172|29374->18226|29405->18235|29479->18281|29600->18379|29654->18404|29737->18459|29768->18468|29842->18514|29963->18612|30017->18637|30099->18691|30130->18700|30206->18748|30327->18846|30381->18871|30464->18926|30495->18935|30580->18992|30711->19100|30765->19125|30884->19212|30922->19231|30936->19236|30976->19237|31026->19258|31081->19285|31221->19414|31262->19415|31316->19440|31365->19461|31384->19470|31457->19520|31506->19540|31526->19549|31599->19599|31630->19600|31689->19627|31739->19648|31793->19674|31824->19683|31890->19721|32001->19809|32051->19830|32130->19881|32161->19890|32229->19930|32342->20020|32392->20041|32472->20093|32503->20102|32569->20140|32684->20232|32734->20253|32814->20305|32845->20314|32914->20355|33028->20446|33078->20467|33158->20519|33189->20528|33258->20569|33372->20660|33422->20681|33502->20733|33533->20742|33603->20784|33718->20876|33768->20897|33848->20949|33879->20958|33951->21002|34066->21094|34116->21115|34195->21166|34226->21175|34296->21217|34411->21309|34461->21330|34541->21382|34572->21391|34653->21444|34778->21546|34828->21567|34910->21617|34958->21636|35068->21754|35118->21776|35188->21829|35229->21831|35283->21856|35380->21925|35405->21928|35496->21991|35586->22059|35640->22084|35698->22110|35745->22128|35813->22168|35862->22207|35903->22209|35949->22226|36137->22386|36200->22432|36241->22434|36291->22455|36330->22466|36356->22482|36406->22510|36486->22562|36512->22578|36563->22607|36617->22629|36664->22647|36716->22667|36759->22681|37480->23371|37513->23373
                  LINES: 28->1|35->7|36->8|37->9|38->10|39->11|40->12|41->13|42->15|43->17|44->18|45->20|46->21|47->22|49->27|49->27|51->27|52->28|52->28|52->28|52->28|53->29|53->29|53->29|54->30|54->30|54->30|55->32|55->32|57->32|58->33|58->33|58->33|58->33|59->34|59->34|59->34|60->25|60->25|60->38|60->38|61->6|62->8|62->9|62->10|62->11|62->12|62->13|62->14|63->16|64->18|64->19|65->21|65->22|65->23|66->24|67->25|69->31|70->35|72->37|73->38|75->40|75->40|75->40|76->41|80->45|80->45|80->45|81->46|81->46|81->46|81->46|81->46|81->46|82->47|82->47|82->47|82->47|84->49|84->49|84->49|86->51|86->51|86->51|87->52|87->52|87->52|87->52|88->53|95->60|96->61|99->64|105->70|105->70|105->70|108->73|108->73|108->73|109->74|110->75|110->75|111->76|111->76|112->77|112->77|113->78|113->78|114->79|114->79|115->80|115->80|116->81|116->81|119->84|119->84|120->85|120->85|120->85|121->86|123->88|123->88|123->88|124->89|125->90|125->90|126->91|127->92|128->93|131->96|131->96|131->96|132->97|132->97|132->97|133->98|133->98|133->98|134->99|139->104|139->104|139->104|140->105|145->110|146->111|147->112|148->113|151->116|151->116|151->116|153->118|153->118|153->118|154->119|154->119|156->121|156->121|156->121|157->122|157->122|159->124|161->126|163->128|165->130|166->131|166->131|166->131|168->133|169->134|169->134|169->134|170->135|171->136|171->136|172->137|173->138|174->139|177->142|177->142|177->142|178->143|179->144|179->144|180->145|181->146|182->147|185->150|185->150|185->150|186->151|187->152|187->152|188->153|188->153|190->155|192->157|194->159|195->160|198->163|198->163|198->163|199->164|200->165|201->166|202->167|203->168|204->169|205->170|206->171|208->173|208->173|208->173|208->173|209->174|209->174|209->174|209->174|210->175|211->176|211->176|211->176|212->177|212->177|212->177|212->177|213->178|214->179|214->179|214->179|215->180|215->180|215->180|215->180|218->183|219->184|222->187|222->187|222->187|223->188|224->189|224->189|224->189|225->190|225->190|225->190|226->191|226->191|227->192|227->192|227->192|228->193|228->193|230->195|231->196|232->197|236->201|236->201|236->201|237->202|238->203|238->203|239->204|240->205|241->206|244->209|244->209|244->209|245->210|246->211|246->211|247->212|248->213|249->214|252->217|252->217|252->217|253->218|254->219|254->219|255->220|256->221|257->222|260->225|260->225|260->225|261->226|262->227|262->227|263->228|264->229|265->230|268->233|268->233|268->233|269->234|270->235|270->235|271->236|272->237|273->238|274->239|275->240|284->249|285->250|285->250|285->250|286->251|286->251|286->251|287->252|287->252|288->253|289->254|289->254|290->255|290->255|291->256|291->256|291->256|292->257|292->257|293->258|293->258|293->258|294->259|294->259|295->260|296->261|296->261|296->261|297->262|297->262|297->262|298->263|298->263|300->265|300->265|300->265|301->266|301->266|302->267|303->268|303->268|303->268|304->269|305->270|305->270|306->271|307->272|307->272|307->272|308->273|310->275|310->275|310->275|311->276|311->276|311->276|312->277|313->278|313->278|313->278|314->279|315->280|315->280|315->280|315->280|316->281|316->281|316->281|318->283|318->283|318->283|319->284|319->284|319->284|320->285|321->286|321->286|321->286|322->287|322->287|322->287|323->288|324->289|325->290|326->291|327->292|329->294|332->297|334->299|334->299|334->299|335->300|335->300|335->300|336->301|337->302|337->302|337->302|337->302|338->303|338->303|339->304|339->304|340->305|341->306|341->306|342->307|342->307|343->308|344->309|344->309|345->310|345->310|346->311|347->312|347->312|348->313|348->313|349->314|350->315|350->315|351->316|351->316|352->317|353->318|353->318|354->319|354->319|355->320|357->322|357->322|358->323|358->323|359->324|360->325|360->325|361->326|361->326|362->327|364->329|364->329|365->330|365->330|366->331|370->335|371->336|371->336|371->336|372->337|373->338|373->338|373->338|374->339|374->339|374->339|374->339|374->339|374->339|374->339|374->339|375->340|376->341|376->341|376->341|377->342|377->342|378->343|379->344|379->344|380->345|380->345|381->346|383->348|383->348|384->349|384->349|385->350|387->352|387->352|388->353|388->353|389->354|391->356|391->356|392->357|392->357|393->358|395->360|395->360|396->361|396->361|397->362|399->364|399->364|400->365|400->365|401->366|402->367|402->367|403->368|403->368|404->369|406->371|406->371|407->372|407->372|408->373|411->376|414->379|417->382|418->383|418->383|418->383|419->384|420->385|420->385|422->387|422->387|423->388|424->389|426->391|429->394|429->394|429->394|430->395|434->399|434->399|434->399|435->400|435->400|435->400|435->400|436->401|436->401|436->401|437->402|439->404|440->405|442->407|459->424|460->425
                  -- GENERATED --
              */
          