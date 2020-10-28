
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
Seq[Any](format.raw/*1.230*/("""

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
                  DATE: Tue Oct 27 21:59:18 PDT 2020
                  SOURCE: C:/Users/Noel/Documents/csc402/lemur-femr/femr/app/femr/ui/views/history/indexPatient.scala.html
                  HASH: 6a14274a99bcfec908c28a78531a5cbe0d8636df
                  MATRIX: 1176->1|1477->234|1523->275|1572->319|1631->373|1693->430|1751->483|1803->530|1852->689|1877->705|1958->709|1991->715|2048->745|2063->751|2108->775|2136->783|2162->800|2243->804|2276->810|2340->847|2355->853|2406->883|2443->631|2456->636|2535->229|2566->567|2596->628|2626->684|2657->780|2686->897|2717->902|2856->1031|2897->1033|2930->1040|2977->1078|3017->1080|3054->1090|3802->1811|3863->1856|3903->1858|3961->1889|4016->1935|4067->1948|4129->1982|4263->2088|4280->2095|4317->2110|4447->2213|4463->2220|4490->2226|4584->2293|4600->2300|4634->2313|4663->2315|4679->2322|4713->2334|4807->2401|4823->2408|4859->2423|4953->2490|4969->2497|4997->2504|5091->2571|5107->2578|5135->2585|5229->2652|5245->2659|5274->2667|5608->2970|5666->2997|5716->3019|5823->3096|5856->3102|6024->3243|6137->3346|6178->3348|6226->3368|6296->3411|6314->3420|6365->3450|6417->3471|6459->3485|6599->3598|6617->3607|6659->3628|6712->3654|6796->3729|6836->3731|6886->3754|6901->3760|6993->3842|7033->3843|7087->3869|7295->4046|7345->4068|7563->4255|7611->4275|7648->4285|7732->4348|7843->4432|7930->4498|8042->4583|8135->4655|8747->5239|8766->5248|8816->5276|9028->5459|9048->5468|9098->5495|9315->5683|9335->5692|9388->5722|9597->5902|9617->5911|9666->5937|9869->6111|9889->6120|9935->6143|10135->6315|10155->6324|10200->6346|10400->6518|10420->6527|10465->6549|10528->6605|10575->6623|10874->6894|10933->6936|10974->6938|11025->6960|11112->7019|11138->7035|11166->7041|11320->7167|11346->7183|11389->7204|11452->7239|11558->7334|11599->7335|11662->7369|11753->7432|11826->7487|11868->7489|11899->7491|11926->7507|11977->7535|12009->7538|12068->7586|12109->7587|12140->7588|12180->7592|12235->7627|12249->7631|12289->7632|12352->7666|12484->7766|12539->7792|12687->7908|12734->7926|12937->8098
                  LINES: 30->1|33->3|34->4|35->5|36->6|37->7|38->8|39->9|41->14|41->14|43->14|44->15|44->15|44->15|44->15|45->17|45->17|47->17|48->18|48->18|48->18|48->18|49->12|49->12|50->1|52->10|53->11|54->12|56->16|57->19|59->21|59->21|59->21|60->22|60->22|60->22|61->23|77->39|77->39|77->39|78->40|78->40|78->40|79->41|80->42|80->42|80->42|81->43|81->43|81->43|82->44|82->44|82->44|82->44|82->44|82->44|83->45|83->45|83->45|84->46|84->46|84->46|85->47|85->47|85->47|86->48|86->48|86->48|91->53|92->54|93->55|98->60|99->61|103->65|103->65|103->65|105->67|105->67|105->67|105->67|107->69|108->70|110->72|110->72|110->72|112->74|112->74|112->74|113->75|113->75|113->75|113->75|114->76|116->78|117->79|120->82|122->84|122->84|122->84|123->85|123->85|124->86|124->86|137->99|137->99|137->99|139->101|139->101|139->101|141->103|141->103|141->103|143->105|143->105|143->105|145->107|145->107|145->107|147->109|147->109|147->109|149->111|149->111|149->111|150->112|151->113|158->120|158->120|158->120|159->121|160->122|160->122|160->122|161->123|161->123|161->123|162->124|162->124|162->124|163->125|163->125|163->125|163->125|163->125|163->125|163->125|163->125|163->125|163->125|163->125|163->125|164->126|164->126|164->126|165->127|166->128|167->129|170->132|171->133|177->139
                  -- GENERATED --
              */
          