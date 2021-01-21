
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

object indexPatient extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template5[femr.common.dtos.CurrentUser,java.lang.Boolean,femr.ui.models.history.IndexPatientViewModelGet,List[_$1] forSome { 
   type _$1 >: _root_.scala.Nothing <: femr.common.models.PatientEncounterItem
},AssetsFinder,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(currentUser: femr.common.dtos.CurrentUser, searchError: java.lang.Boolean, viewModel: femr.ui.models.history.IndexPatientViewModelGet, patientEncounters: List[_ <: femr.common.models.PatientEncounterItem], assets: AssetsFinder):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {
/*3.2*/import femr.ui.views.html.layouts.main
/*4.2*/import femr.ui.views.html.partials.search
/*5.2*/import femr.ui.controllers.routes.MedicalController
/*6.2*/import femr.ui.controllers.routes.PharmaciesController
/*7.2*/import femr.ui.controllers.routes.TriageController
/*8.2*/import femr.ui.controllers.HistoryController
/*9.2*/import femr.data.models.mysql.Roles

def /*14.2*/additionalStyles/*14.18*/:play.twirl.api.HtmlFormat.Appendable = {_display_(

Seq[Any](format.raw/*14.22*/("""
    """),format.raw/*15.5*/("""<link rel="stylesheet" href=""""),_display_(/*15.35*/assets/*15.41*/.path("css/history.css")),format.raw/*15.65*/("""">
""")))};def /*17.2*/additionalScripts/*17.19*/:play.twirl.api.HtmlFormat.Appendable = {_display_(

Seq[Any](format.raw/*17.23*/("""
    """),format.raw/*18.5*/("""<script type="text/javascript" src=""""),_display_(/*18.42*/assets/*18.48*/.path("js/history/history.js")),format.raw/*18.78*/(""""></script>
""")))};def /*12.2*/roles/*12.7*/ = {{currentUser.getRoles().map(r => r.getId())}};
Seq[Any](format.raw/*2.1*/("""
"""),format.raw/*10.1*/("""
"""),format.raw/*11.60*/("""
"""),format.raw/*12.55*/("""

"""),format.raw/*16.2*/("""
"""),format.raw/*19.2*/("""

"""),_display_(/*21.2*/main("History", currentUser, styles = additionalStyles, scripts = additionalScripts, search = search("history"), assets = assets)/*21.131*/ {_display_(Seq[Any](format.raw/*21.133*/("""
    """),_display_(/*22.6*/if(viewModel.getPatientItems.size > 1)/*22.44*/ {_display_(Seq[Any](format.raw/*22.46*/("""
        """),format.raw/*23.9*/("""<div class="encounterInfoWrap backgroundForWrap">
            <div class="row well well-sm">
                <h4 class="text-center">Duplicate Patient Search Results</h4>
                <div class="panel panel-default">
                    <table class="table vert-align">
                        <tr>
                            <th>Photo</th>
                            <th>ID</th>
                            <th>Name</th>
                            <th>Phone Number</th>
                            <th>Gender</th>
                            <th>Age</th>
                            <th>City</th>
                            <th>&nbsp;</th>
                        </tr>

                        """),_display_(/*39.26*/for(a <- 1 to viewModel.getPatientItems.size) yield /*39.71*/ {_display_(Seq[Any](format.raw/*39.73*/("""
                            """),_display_(/*40.30*/defining(viewModel.getPatientItems.get(a - 1))/*40.76*/ { patient =>_display_(Seq[Any](format.raw/*40.89*/("""
                                """),format.raw/*41.33*/("""<tr>
                                    <td class="vert-align"><img class="thumbnail searchThumb" src=""""),_display_(/*42.101*/patient/*42.108*/.getPathToPhoto),format.raw/*42.123*/("""" width="90" height="90"/></td>
                                    <td class="vert-align patientId">"""),_display_(/*43.71*/patient/*43.78*/.getId),format.raw/*43.84*/("""</td>
                                    <td class="vert-align">"""),_display_(/*44.61*/patient/*44.68*/.getFirstName),format.raw/*44.81*/(""" """),_display_(/*44.83*/patient/*44.90*/.getLastName),format.raw/*44.102*/("""</td>
                                    <td class="vert-align">"""),_display_(/*45.61*/patient/*45.68*/.getPhoneNumber),format.raw/*45.83*/("""</td>
                                    <td class="vert-align">"""),_display_(/*46.61*/patient/*46.68*/.getSex),format.raw/*46.75*/("""</td>
                                    <td class="vert-align">"""),_display_(/*47.61*/patient/*47.68*/.getAge),format.raw/*47.75*/("""</td>
                                    <td class="vert-align">"""),_display_(/*48.61*/patient/*48.68*/.getCity),format.raw/*48.76*/("""</td>
                                    <td class="vert-align">
                                        <button type="button" class="selectPageFromRow fButton pull-right">Select</button>
                                    </td>
                                </tr>
                            """)))}),format.raw/*53.30*/("""
                        """)))}),format.raw/*54.26*/("""
                    """),format.raw/*55.21*/("""</table>
                </div>
            </div>
        </div>

    """)))}),format.raw/*60.6*/("""
    """),format.raw/*61.5*/("""<div class="encounterInfoWrap backgroundForWrap">

        <div id="viewPatientHeader">
            <div id="patientPhoto">
            """),_display_(/*65.14*/if(viewModel.getPatientItem.getPathToPhoto != null && !viewModel.getPatientItem.getPathToPhoto.isEmpty)/*65.117*/ {_display_(Seq[Any](format.raw/*65.119*/("""

                """),format.raw/*67.17*/("""<img class="" height="90" width="90" src=""""),_display_(/*67.60*/viewModel/*67.69*/.getPatientItem.getPathToPhoto),format.raw/*67.99*/("""" />

            """)))}),format.raw/*69.14*/("""
            """),format.raw/*70.13*/("""</div>
            <div id="patientInformation">
                <h1 class="bold margin-top-zero">Patient Id: """),_display_(/*72.63*/viewModel/*72.72*/.getPatientItem.getId),format.raw/*72.93*/("""</h1>

                """),_display_(/*74.18*/if(roles.contains(Roles.ADMINISTRATOR) || roles.contains(Roles.SUPERUSER) )/*74.93*/ {_display_(Seq[Any](format.raw/*74.95*/("""
                    """),_display_(/*75.22*/helper/*75.28*/.form(action = TriageController.deletePatientPost(viewModel.getPatientItem.getId))/*75.110*/{_display_(Seq[Any](format.raw/*75.111*/("""
                        """),format.raw/*76.25*/("""<input type="hidden" name="reasonDeleted" id="reasonDeleted" />
                        <button hidden="true" type="submit"  id="deletePatient"></button>
                    """)))}),format.raw/*78.22*/("""
                    """),format.raw/*79.21*/("""<span>
                        <button type="submit" id="deletePatientBtn" class="btn btn-danger pull-right"> Delete this Patient</button>
                    </span>
                """)))}),format.raw/*82.18*/("""

                """),format.raw/*84.17*/("""<a href=""""),_display_(/*84.27*/{MedicalController.editGet(viewModel.getPatientItem.getId).url}),format.raw/*84.90*/("""" class="btn btn-default pull-right">View in Medical</a>
                <a href=""""),_display_(/*85.27*/{PharmaciesController.editGet(viewModel.getPatientItem.getId).url}),format.raw/*85.93*/("""" class="btn btn-default pull-right">View in Pharmacy</a>
                <a href=""""),_display_(/*86.27*/{TriageController.indexPopulatedGet(viewModel.getPatientItem.getId).url}),format.raw/*86.99*/("""" class="btn btn-default pull-right">See This Patient In Triage</a>

                <br/><br/>
            </div>
        </div>


        <div class="row inputRow">
            <div class="col-sm-6 col-xs-12 col-md-6">
                <h3 class="pull-left bold margin-top-zero">General Info</h3>
                <div class="medicalHistoryLineDivide"></div>
                <div class="text-center">
                    <label class="inputLabel">First Name:</label>
                    <input type="text" class="form-control input-sm pull-left" name="firstName" value=""""),_display_(/*99.105*/viewModel/*99.114*/.getPatientItem.getFirstName),format.raw/*99.142*/("""" readonly/>
                    <label class="inputLabel">Last Name:</label>
                    <input type="text" class="form-control input-sm pull-left" name="lastName" value=""""),_display_(/*101.104*/viewModel/*101.113*/.getPatientItem.getLastName),format.raw/*101.140*/("""" readonly/>
                    <label class="inputLabel">Phone Number:</label>
                    <input type="tel" class="form-control input-sm pull-left" name="phoneNumber" value=""""),_display_(/*103.106*/viewModel/*103.115*/.getPatientItem.getPhoneNumber),format.raw/*103.145*/("""" readonly/>
                    <label class="inputLabel">Address:</label>
                    <input type="text" class="form-control input-sm pull-left" name="address" value=""""),_display_(/*105.103*/viewModel/*105.112*/.getPatientItem.getAddress),format.raw/*105.138*/("""" readonly/>
                    <label class="inputLabel">City:</label>
                    <input type="text" class="form-control input-sm pull-left" name="city" value=""""),_display_(/*107.100*/viewModel/*107.109*/.getPatientItem.getCity),format.raw/*107.132*/("""" readonly/>
                    <label class="inputLabel">Age:</label>
                    <input type="text" class="form-control input-sm pull-left" name="age" value=""""),_display_(/*109.99*/viewModel/*109.108*/.getPatientItem.getAge),format.raw/*109.130*/("""" readonly/>
                    <label class="inputLabel">Sex:</label>
                    <input type="text" class="form-control input-sm pull-left" name="age" value=""""),_display_(/*111.99*/viewModel/*111.108*/.getPatientItem.getSex),format.raw/*111.130*/("""" readonly/>
                    """),format.raw/*112.43*/("""
                """),format.raw/*113.17*/("""</div>
            </div>
            <div class="col-sm-6 col-xs-12 col-md-6">
                <h3 class="pull-left bold margin-top-zero">Previous Encounters</h3>

                <div class="medicalHistoryLineDivide"></div>
                <ol>
                """),_display_(/*120.18*/for(patientEncounter <- patientEncounters) yield /*120.60*/ {_display_(Seq[Any](format.raw/*120.62*/("""
                    """),format.raw/*121.21*/("""<li>
                        <a href="/history/encounter/"""),_display_(/*122.54*/patientEncounter/*122.70*/.getId),format.raw/*122.76*/("""" class="encbtns btn btn-default btn-xs btn-block" role="button" type="button">
                            <p class="date">"""),_display_(/*123.46*/patientEncounter/*123.62*/.getTriageDateOfVisit),format.raw/*123.83*/("""</p>
                            """),_display_(/*124.30*/if(patientEncounter.getChiefComplaints != null && patientEncounter.getChiefComplaints.size > 0)/*124.125*/{_display_(Seq[Any](format.raw/*124.126*/("""
                                """),format.raw/*125.33*/("""<p class ="chiefComplaint"><strong>Chief complaint: </strong> """),_display_(/*125.96*/for(x <- 1 to patientEncounter.getChiefComplaints.size) yield /*125.151*/ {_display_(Seq[Any](format.raw/*125.153*/(""" """),_display_(/*125.155*/patientEncounter/*125.171*/.getChiefComplaints.get(x-1)),format.raw/*125.199*/("""  """),_display_(/*125.202*/if(x < patientEncounter.getChiefComplaints.size)/*125.250*/{_display_(Seq[Any](format.raw/*125.251*/(""" """),format.raw/*125.252*/("""| """)))})))}),format.raw/*125.256*/("""</p>
                            """)))}/*126.30*/else/*126.34*/{_display_(Seq[Any](format.raw/*126.35*/("""
                                """),format.raw/*127.33*/("""<p class ="chiefComplaint"><strong>Chief complaint: </strong> N/A</p>
                            """)))}),format.raw/*128.30*/("""
                        """),format.raw/*129.25*/("""</a>
                    </li>
                    <div class="medicalHistoryLineDivide"></div>
                """)))}),format.raw/*132.18*/("""
                """),format.raw/*133.17*/("""</ol>
                <div class="alert alert-info" id="encounterAlert">Click an encounter to view past history!</div>
            </div>
        </div>

    </div>
""")))}),format.raw/*139.2*/("""
"""))
      }
    }
  }

  def render(currentUser:femr.common.dtos.CurrentUser,searchError:java.lang.Boolean,viewModel:femr.ui.models.history.IndexPatientViewModelGet,patientEncounters:List[_$1] forSome { 
   type _$1 >: _root_.scala.Nothing <: femr.common.models.PatientEncounterItem
},assets:AssetsFinder): play.twirl.api.HtmlFormat.Appendable = apply(currentUser,searchError,viewModel,patientEncounters,assets)

  def f:((femr.common.dtos.CurrentUser,java.lang.Boolean,femr.ui.models.history.IndexPatientViewModelGet,List[_$1] forSome { 
   type _$1 >: _root_.scala.Nothing <: femr.common.models.PatientEncounterItem
},AssetsFinder) => play.twirl.api.HtmlFormat.Appendable) = (currentUser,searchError,viewModel,patientEncounters,assets) => apply(currentUser,searchError,viewModel,patientEncounters,assets)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  DATE: Wed Jan 20 18:29:27 PST 2021
                  SOURCE: /Users/noel/Desktop/CPE/femr/femr/app/femr/ui/views/history/indexPatient.scala.html
                  HASH: a46a8112d4578616f40ee8ca41700a645cf3d3ca
                  MATRIX: 1174->1|1475->232|1521->272|1570->315|1629->368|1691->424|1749->476|1801->522|1850->676|1875->692|1956->696|1988->701|2045->731|2060->737|2105->761|2132->767|2158->784|2239->788|2271->793|2335->830|2350->836|2401->866|2437->620|2450->625|2527->230|2555->558|2584->618|2613->673|2642->765|2670->879|2699->882|2838->1011|2879->1013|2911->1019|2958->1057|2998->1059|3034->1068|3766->1773|3827->1818|3867->1820|3924->1850|3979->1896|4030->1909|4091->1942|4224->2047|4241->2054|4278->2069|4407->2171|4423->2178|4450->2184|4543->2250|4559->2257|4593->2270|4622->2272|4638->2279|4672->2291|4765->2357|4781->2364|4817->2379|4910->2445|4926->2452|4954->2459|5047->2525|5063->2532|5091->2539|5184->2605|5200->2612|5229->2620|5558->2918|5615->2944|5664->2965|5766->3037|5798->3042|5962->3179|6075->3282|6116->3284|6162->3302|6232->3345|6250->3354|6301->3384|6351->3403|6392->3416|6530->3527|6548->3536|6590->3557|6641->3581|6725->3656|6765->3658|6814->3680|6829->3686|6921->3768|6961->3769|7014->3794|7220->3969|7269->3990|7484->4174|7530->4192|7567->4202|7651->4265|7761->4348|7848->4414|7959->4498|8052->4570|8651->5141|8670->5150|8720->5178|8930->5359|8950->5368|9000->5395|9215->5581|9235->5590|9288->5620|9495->5798|9515->5807|9564->5833|9765->6005|9785->6014|9831->6037|10029->6207|10049->6216|10094->6238|10292->6408|10312->6417|10357->6439|10419->6494|10465->6511|10757->6775|10816->6817|10857->6819|10907->6840|10993->6898|11019->6914|11047->6920|11200->7045|11226->7061|11269->7082|11331->7116|11437->7211|11478->7212|11540->7245|11631->7308|11704->7363|11746->7365|11777->7367|11804->7383|11855->7411|11887->7414|11946->7462|11987->7463|12018->7464|12058->7468|12112->7502|12126->7506|12166->7507|12228->7540|12359->7639|12413->7664|12558->7777|12604->7794|12801->7960
                  LINES: 30->1|33->3|34->4|35->5|36->6|37->7|38->8|39->9|41->14|41->14|43->14|44->15|44->15|44->15|44->15|45->17|45->17|47->17|48->18|48->18|48->18|48->18|49->12|49->12|50->2|51->10|52->11|53->12|55->16|56->19|58->21|58->21|58->21|59->22|59->22|59->22|60->23|76->39|76->39|76->39|77->40|77->40|77->40|78->41|79->42|79->42|79->42|80->43|80->43|80->43|81->44|81->44|81->44|81->44|81->44|81->44|82->45|82->45|82->45|83->46|83->46|83->46|84->47|84->47|84->47|85->48|85->48|85->48|90->53|91->54|92->55|97->60|98->61|102->65|102->65|102->65|104->67|104->67|104->67|104->67|106->69|107->70|109->72|109->72|109->72|111->74|111->74|111->74|112->75|112->75|112->75|112->75|113->76|115->78|116->79|119->82|121->84|121->84|121->84|122->85|122->85|123->86|123->86|136->99|136->99|136->99|138->101|138->101|138->101|140->103|140->103|140->103|142->105|142->105|142->105|144->107|144->107|144->107|146->109|146->109|146->109|148->111|148->111|148->111|149->112|150->113|157->120|157->120|157->120|158->121|159->122|159->122|159->122|160->123|160->123|160->123|161->124|161->124|161->124|162->125|162->125|162->125|162->125|162->125|162->125|162->125|162->125|162->125|162->125|162->125|162->125|163->126|163->126|163->126|164->127|165->128|166->129|169->132|170->133|176->139
                  -- GENERATED --
              */
          