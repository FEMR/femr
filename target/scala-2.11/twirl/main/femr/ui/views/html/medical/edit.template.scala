
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
Seq[Any](format.raw/*4.1*/("""
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
                  DATE: Wed Jan 20 18:29:26 PST 2021
                  SOURCE: /Users/noel/Desktop/CPE/femr/femr/app/femr/ui/views/medical/edit.scala.html
                  HASH: 9e44f183edc5c10a6c38b48797042bbd4cdcc329
                  MATRIX: 1084->1|1345->192|1404->245|1463->298|1525->354|1571->394|1621->437|1679->488|1738->540|1796->591|1821->607|1902->611|1938->620|1995->650|2010->656|2063->688|2131->729|2146->735|2212->780|2280->821|2295->827|2356->867|2387->881|2413->898|2494->902|2531->912|2595->949|2610->955|2670->994|2754->1051|2769->1057|2835->1102|2919->1159|2934->1165|2985->1195|3069->1252|3084->1258|3151->1304|3235->1361|3250->1367|3314->1410|3398->1467|3413->1473|3472->1511|3527->190|3555->585|3587->875|3619->1528|3648->1531|3787->1660|3828->1662|3860->1667|3916->1696|3934->1705|3976->1726|4067->1879|4104->1889|4289->2052|4327->2063|4559->2268|4602->2302|4642->2304|4691->2325|4783->2399|4796->2404|4835->2405|4884->2426|4984->2495|5029->2512|5195->2651|5253->2688|5298->2705|5588->3020|5637->3041|5764->3141|5779->3147|5911->3269|5952->3271|6001->3292|6252->3516|6295->3550|6335->3552|6396->3585|6478->3636|6535->3666|6580->3702|6620->3704|6681->3737|6769->3794|6826->3851|6883->3881|6932->3914|6972->3916|7033->3950|7058->3966|7098->3968|7163->4005|7199->4014|7211->4017|7252->4037|7282->4040|7294->4043|7323->4051|7393->4090|7454->4120|7507->4145|7639->4250|7772->4361|7826->4388|8004->4544|8058->4571|8101->4605|8141->4607|8198->4637|8260->4678|8317->4704|8371->4731|8416->4767|8456->4769|8513->4799|8563->4828|8620->4854|8674->4881|8723->4914|8763->4916|8820->4986|8877->5016|8902->5032|8941->5033|9002->5066|9066->5103|9078->5106|9121->5128|9191->5171|9227->5186|9288->5219|9356->5255|9414->5281|9469->5307|9743->5549|9802->5579|9840->5589|9984->5711|10127->5826|10216->5892|10432->6077|10463->6080
                  LINES: 28->1|33->5|34->6|35->7|36->8|37->9|38->10|39->11|40->12|42->14|42->14|44->14|45->15|45->15|45->15|45->15|46->16|46->16|46->16|47->17|47->17|47->17|48->19|48->19|50->19|52->21|52->21|52->21|52->21|53->22|53->22|53->22|54->23|54->23|54->23|55->24|55->24|55->24|56->25|56->25|56->25|57->26|57->26|57->26|59->4|60->13|61->18|62->27|64->29|64->29|64->29|65->30|65->30|65->30|65->30|69->34|70->35|70->35|73->38|76->41|76->41|76->41|77->42|78->43|78->43|78->43|79->44|80->45|81->46|83->48|83->48|84->49|90->55|91->56|96->61|96->61|96->61|96->61|97->62|101->66|101->66|101->66|102->67|103->68|104->69|104->69|104->69|105->70|106->71|107->72|108->73|108->73|108->73|109->74|109->74|109->74|110->75|110->75|110->75|110->75|110->75|110->75|110->75|111->76|112->77|113->78|117->82|117->82|119->84|119->84|121->86|121->86|121->86|122->87|122->87|123->88|125->90|125->90|125->90|126->91|126->91|127->92|129->94|129->94|129->94|130->95|131->96|131->96|131->96|132->97|132->97|132->97|132->97|133->98|133->98|134->99|135->100|136->101|138->103|141->106|143->108|143->108|144->109|145->110|145->110|154->119|157->122
                  -- GENERATED --
              */
          