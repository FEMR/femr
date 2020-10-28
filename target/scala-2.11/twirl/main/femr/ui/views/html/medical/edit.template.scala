
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

object edit extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template4[femr.common.dtos.CurrentUser,femr.util.DataStructure.Mapping.VitalMultiMap,femr.ui.models.medical.EditViewModelGet,AssetsFinder,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(currentUser: femr.common.dtos.CurrentUser,
        vitals: femr.util.DataStructure.Mapping.VitalMultiMap,
        viewModel: femr.ui.models.medical.EditViewModelGet, assets: AssetsFinder):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {
/*5.2*/import femr.ui.controllers.routes.HistoryController
/*6.2*/import femr.ui.controllers.routes.MedicalController
/*7.2*/import femr.ui.controllers.routes.PharmaciesController
/*8.2*/import femr.ui.views.html.layouts.main
/*9.2*/import femr.ui.views.html.partials.search
/*10.2*/import femr.ui.views.html.partials.medical.tabs._
/*11.2*/import femr.ui.views.html.partials.patientOverview
/*12.2*/import femr.ui.views.html.medical.listVitals

def /*14.6*/additionalStyles/*14.22*/:play.twirl.api.HtmlFormat.Appendable = {_display_(

Seq[Any](format.raw/*14.26*/("""
        """),format.raw/*15.9*/("""<link rel="stylesheet" href=""""),_display_(/*15.39*/assets/*15.45*/.path("css/medical/medical.css")),format.raw/*15.77*/("""">
        <link rel="stylesheet" href=""""),_display_(/*16.39*/assets/*16.45*/.path("css/libraries/jquery.tablescroll.css")),format.raw/*16.90*/("""">
        <link rel="stylesheet" href=""""),_display_(/*17.39*/assets/*17.45*/.path("css/libraries/jquery-ui.min.css")),format.raw/*17.85*/("""">
    """)))};def /*19.6*/additionalScripts/*19.23*/:play.twirl.api.HtmlFormat.Appendable = {_display_(

Seq[Any](format.raw/*19.27*/("""

        """),format.raw/*21.9*/("""<script type="text/javascript" src=""""),_display_(/*21.46*/assets/*21.52*/.path("js/libraries/handlebars.min.js")),format.raw/*21.91*/(""""></script>
        <script type="text/javascript" src=""""),_display_(/*22.46*/assets/*22.52*/.path("js/libraries/typeahead.bundle.min.js")),format.raw/*22.97*/(""""></script>
        <script type="text/javascript" src=""""),_display_(/*23.46*/assets/*23.52*/.path("js/medical/medical.js")),format.raw/*23.82*/(""""></script>
        <script type="text/javascript" src=""""),_display_(/*24.46*/assets/*24.52*/.path("js/medical/medicalClientValidation.js")),format.raw/*24.98*/(""""></script>
        <script type="text/javascript" src=""""),_display_(/*25.46*/assets/*25.52*/.path("js/libraries/jquery.tablescroll.js")),format.raw/*25.95*/(""""></script>
        <script type="text/javascript" src=""""),_display_(/*26.46*/assets/*26.52*/.path("js/libraries/jquery-ui.min.js")),format.raw/*26.90*/(""""></script>
    """)))};
Seq[Any](format.raw/*3.82*/("""

"""),format.raw/*13.1*/("""
    """),format.raw/*18.6*/("""
    """),format.raw/*27.6*/("""

"""),_display_(/*29.2*/main("Medical", currentUser, scripts = additionalScripts, styles = additionalStyles, search = search("medical"), assets = assets)/*29.131*/ {_display_(Seq[Any](format.raw/*29.133*/("""
    """),format.raw/*30.5*/("""<input type="hidden" value=""""),_display_(/*30.34*/viewModel/*30.43*/.getPatientItem.getId),format.raw/*30.64*/("""" id="patientId"/>

    <div id="medicalContentWrap">

        """),format.raw/*34.99*/("""
        """),_display_(/*35.10*/patientOverview(viewModel.getPatientItem, viewModel.getPatientEncounterItem, viewModel.getSettings, "Medical - " + "Patient ID: " + viewModel.getPatientItem.getId)),format.raw/*35.173*/("""


        """),format.raw/*38.9*/("""<div id="mainWrap" class="backgroundForWrap">
            <div id="vitalsWrap">
                <!-- Alaa Serhan - Referenced in vitalClientValidation.js for validationg height inputs -->
                """),_display_(/*41.18*/if(viewModel.getSettings.isMetric)/*41.52*/ {_display_(Seq[Any](format.raw/*41.54*/("""
                    """),format.raw/*42.21*/("""<input type="hidden" id="vitalsUnits" value="metric" />
                """)))}/*43.19*/else/*43.24*/{_display_(Seq[Any](format.raw/*43.25*/("""
                    """),format.raw/*44.21*/("""<input type="hidden" id="vitalUnits" value="imp" />
                """)))}),format.raw/*45.18*/("""
                """),format.raw/*46.17*/("""<div id="vitalsPartial">
                        <!-- Repalaced when user clicks save in record new vitals dialog -->
                    """),_display_(/*48.22*/listVitals(vitals, viewModel, assets)),format.raw/*48.59*/("""
                """),format.raw/*49.17*/("""</div>

                <button type="button" id="newVitalsBtn" class="fButton fOtherButton">Record New Vitals</button>

                <div id="newVitalsDialog" title="Record New Vitals">
                    <div id="newVitalsPartial">
                        """),format.raw/*55.78*/("""
                    """),format.raw/*56.21*/("""</div>
                </div>
            </div>

            <div id="inputWrap">
                """),_display_(/*61.18*/helper/*61.24*/.form(action = MedicalController.editPost(int2Integer(viewModel.getPatientItem.getId)), 'enctype -> "multipart/form-data")/*61.146*/ {_display_(Seq[Any](format.raw/*61.148*/("""
                    """),format.raw/*62.21*/("""<div class="tabWrap">
                        <ul id="medicalTabs" class="tabrow">
                            <li id="hpi">HPI</li>
                            <li id="treatment">Treatment</li>
                            """),_display_(/*66.30*/if(viewModel.getSettings.isPmhTab)/*66.64*/ {_display_(Seq[Any](format.raw/*66.66*/("""
                                """),format.raw/*67.33*/("""<li id="pmh">PMH</li>
                            """)))}),format.raw/*68.30*/("""
                            """),_display_(/*69.30*/if(viewModel.getSettings.isPhotoTab)/*69.66*/ {_display_(Seq[Any](format.raw/*69.68*/("""
                                """),format.raw/*70.33*/("""<li id="photos">Photos</li>
                            """)))}),format.raw/*71.30*/("""
                            """),format.raw/*72.57*/("""
                            """),_display_(/*73.30*/for(tab <- viewModel.getTabItems) yield /*73.63*/ {_display_(Seq[Any](format.raw/*73.65*/("""
                                """),_display_(/*74.34*/if(tab.isCustom)/*74.50*/ {_display_(Seq[Any](format.raw/*74.52*/("""
                                    """),format.raw/*75.37*/("""<li id=""""),_display_(/*75.46*/tab/*75.49*/.getName.toLowerCase),format.raw/*75.69*/("""">"""),_display_(/*75.72*/tab/*75.75*/.getName),format.raw/*75.83*/("""</li>
                                """)))}),format.raw/*76.34*/("""
                            """)))}),format.raw/*77.30*/("""
                        """),format.raw/*78.25*/("""</ul>
                    </div>

                    <div id="tabContentWrap">
                        """),_display_(/*82.26*/hpiTab(viewModel.getTabItemByName("hpi"), viewModel.getSettings.isConsolidateHPI, viewModel.getChiefComplaints)),format.raw/*82.137*/("""

                        """),_display_(/*84.26*/treatmentTab(viewModel.getTabItemByName("treatment"), viewModel.getPrescriptionItems, viewModel.getMedicationAdministrationItems, viewModel.getProblemItems)),format.raw/*84.182*/("""

                        """),_display_(/*86.26*/if(viewModel.getSettings.isPmhTab)/*86.60*/ {_display_(Seq[Any](format.raw/*86.62*/("""
                            """),_display_(/*87.30*/pmhTab(viewModel.getTabItemByName("pmh"))),format.raw/*87.71*/("""
                        """)))}),format.raw/*88.26*/("""

                        """),_display_(/*90.26*/if(viewModel.getSettings.isPhotoTab)/*90.62*/ {_display_(Seq[Any](format.raw/*90.64*/("""
                            """),_display_(/*91.30*/photoTab(viewModel.getPhotos)),format.raw/*91.59*/("""
                        """)))}),format.raw/*92.26*/("""

                        """),_display_(/*94.26*/for(tab <- viewModel.getTabItems) yield /*94.59*/ {_display_(Seq[Any](format.raw/*94.61*/("""
                            """),format.raw/*95.70*/("""
                            """),_display_(/*96.30*/if(tab.isCustom)/*96.46*/{_display_(Seq[Any](format.raw/*96.47*/("""
                                """),format.raw/*97.33*/("""<div class="controlWrap hidden" id=""""),_display_(/*97.70*/tab/*97.73*/.getName.toLowerCase()),format.raw/*97.95*/("""Control">
                                """),_display_(/*98.34*/dynamicTab(tab)),format.raw/*98.49*/("""
                                """),format.raw/*99.33*/("""</div>
                            """)))}),format.raw/*100.30*/("""
                        """)))}),format.raw/*101.26*/("""

                        """),format.raw/*103.25*/("""<div class="historySubmitWrap">
                            <button type="submit" id="medicalSubmitBtn" class="fButton fRedButton fSubmitButton pull-right">
                                Submit Patient</button>
                            """)))}),format.raw/*106.30*/("""

                            """),format.raw/*108.29*/("""<a href=""""),_display_(/*108.39*/{HistoryController.indexPatientGet(
                                Integer.toString(viewModel.getPatientItem.getId)).url}),format.raw/*109.87*/("""" class="fButton fOtherButton pull-left" target="_blank">Patient History</a>
                            <a href=""""),_display_(/*110.39*/{PharmaciesController.editGet(viewModel.getPatientItem.getId).url}),format.raw/*110.105*/("""" class="fButton fOtherButton pull-left" target="_blank">View in Pharmacy</a>
                        </div>

                    </div>

            </div>
        </div>
    </div>

""")))}),format.raw/*119.2*/("""


"""),format.raw/*122.1*/("""<div id="portraitTemplate">
    <div class="col-xs-12 col-sm-6 col-md-4" hidden="true">
        <div class="thumbnail">
            <img src="">
            <div class="caption">
                <div class="form-group">
                    <p name="photoDescription" ></p>
                    <div class="btn-group">
                        <button id="btnEditText" type="button" onclick="portraitEdit ( this )" class="btn btn-default btn-med">
                            <span class="glyphicon glyphicon-edit"></span> Edit Description
                        </button>
                    </div>
                    <div class="btn-group">
                        <button id="btnDeletePhoto" type="button" onclick="portraitDelete ( this )" class="btn btn-danger btn-med">
                            <span class="glyphicon glyphicon-trash"></span> Delete
                        </button>
                    </div>
                </div>
            </div>
        </div>
            <!-- Data elements for server-side logic -->
        <div name="dataList" hidden="true">
                <!-- Signals that user has requested to delete this photo from the database / server -->
            <input hidden="true" type="checkbox" name="deleteRequested" checked="false" value="false" />
                <!-- Signals that the user has updated a description -->
            <input hidden="true" type="checkbox" name="hasUpdatedDesc" checked="false" value="false" />
                <!-- Place holder for new images -->
            <input hidden="true" type="file" name="patientPhoto" />
                <!-- text for POST -->
            <input hidden="true" type="text" name="imageDescText" value="" />
                <!-- photo Id -->
            <input hidden="true" type="text" name="photoId" value="" />
        </div>
    </div>
</div>
<div class="modal fade" id="modalNewImage" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">New photo</h4>
            </div>
            <div class="modal-body">
                <div class="form-group">
                    <div class="thumbnail">
                        <img id="modalImg" src="" />
                    </div>
                    <label for="modalTextEntry" class="control-label">Description:</label>
                    <textarea rows="8" id="modalTextEntry" name="modalTextEntryName" class="form-control input-med" ></textarea>

                </div>
            </div>
            <div class="modal-footer">
                <button type="button" id="modalCancelPortrait" class="btn btn-default" data-dismiss="modal">Close</button>
                <button type="button" id="modalSavePortrait" class="btn btn-primary">Save changes</button>
            </div>
        </div>
    </div>
</div>
"""))
      }
    }
  }

  def render(currentUser:femr.common.dtos.CurrentUser,vitals:femr.util.DataStructure.Mapping.VitalMultiMap,viewModel:femr.ui.models.medical.EditViewModelGet,assets:AssetsFinder): play.twirl.api.HtmlFormat.Appendable = apply(currentUser,vitals,viewModel,assets)

  def f:((femr.common.dtos.CurrentUser,femr.util.DataStructure.Mapping.VitalMultiMap,femr.ui.models.medical.EditViewModelGet,AssetsFinder) => play.twirl.api.HtmlFormat.Appendable) = (currentUser,vitals,viewModel,assets) => apply(currentUser,vitals,viewModel,assets)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  DATE: Tue Oct 27 21:59:18 PDT 2020
                  SOURCE: C:/Users/Noel/Documents/csc402/lemur-femr/femr/app/femr/ui/views/medical/edit.scala.html
                  HASH: eb4d38712029fcfc6168b5b8f2aa131a10d48ef4
                  MATRIX: 1084->1|1347->196|1406->250|1465->304|1527->361|1573->402|1623->446|1681->498|1740->551|1798->604|1823->620|1904->624|1941->634|1998->664|2013->670|2066->702|2135->744|2150->750|2216->795|2285->837|2300->843|2361->883|2393->899|2419->916|2500->920|2539->932|2603->969|2618->975|2678->1014|2763->1072|2778->1078|2844->1123|2929->1181|2944->1187|2995->1217|3080->1275|3095->1281|3162->1327|3247->1385|3262->1391|3326->1434|3411->1492|3426->1498|3485->1536|3542->191|3573->597|3606->892|3639->1554|3670->1559|3809->1688|3850->1690|3883->1696|3939->1725|3957->1734|3999->1755|4094->1912|4132->1923|4317->2086|4358->2100|4593->2308|4636->2342|4676->2344|4726->2366|4819->2441|4832->2446|4871->2447|4921->2469|5022->2539|5068->2557|5236->2698|5294->2735|5340->2753|5636->3074|5686->3096|5818->3201|5833->3207|5965->3329|6006->3331|6056->3353|6311->3581|6354->3615|6394->3617|6456->3651|6539->3703|6597->3734|6642->3770|6682->3772|6744->3806|6833->3864|6891->3922|6949->3953|6998->3986|7038->3988|7100->4023|7125->4039|7165->4041|7231->4079|7267->4088|7279->4091|7320->4111|7350->4114|7362->4117|7391->4125|7462->4165|7524->4196|7578->4222|7714->4331|7847->4442|7903->4471|8081->4627|8137->4656|8180->4690|8220->4692|8278->4723|8340->4764|8398->4791|8454->4820|8499->4856|8539->4858|8597->4889|8647->4918|8705->4945|8761->4974|8810->5007|8850->5009|8908->5080|8966->5111|8991->5127|9030->5128|9092->5162|9156->5199|9168->5202|9211->5224|9282->5268|9318->5283|9380->5317|9449->5354|9508->5381|9565->5409|9842->5654|9903->5686|9941->5696|10086->5819|10230->5935|10319->6001|10544->6195|10578->6201
                  LINES: 28->1|33->5|34->6|35->7|36->8|37->9|38->10|39->11|40->12|42->14|42->14|44->14|45->15|45->15|45->15|45->15|46->16|46->16|46->16|47->17|47->17|47->17|48->19|48->19|50->19|52->21|52->21|52->21|52->21|53->22|53->22|53->22|54->23|54->23|54->23|55->24|55->24|55->24|56->25|56->25|56->25|57->26|57->26|57->26|59->3|61->13|62->18|63->27|65->29|65->29|65->29|66->30|66->30|66->30|66->30|70->34|71->35|71->35|74->38|77->41|77->41|77->41|78->42|79->43|79->43|79->43|80->44|81->45|82->46|84->48|84->48|85->49|91->55|92->56|97->61|97->61|97->61|97->61|98->62|102->66|102->66|102->66|103->67|104->68|105->69|105->69|105->69|106->70|107->71|108->72|109->73|109->73|109->73|110->74|110->74|110->74|111->75|111->75|111->75|111->75|111->75|111->75|111->75|112->76|113->77|114->78|118->82|118->82|120->84|120->84|122->86|122->86|122->86|123->87|123->87|124->88|126->90|126->90|126->90|127->91|127->91|128->92|130->94|130->94|130->94|131->95|132->96|132->96|132->96|133->97|133->97|133->97|133->97|134->98|134->98|135->99|136->100|137->101|139->103|142->106|144->108|144->108|145->109|146->110|146->110|155->119|158->122
                  -- GENERATED --
              */
          