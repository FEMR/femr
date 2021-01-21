
package femr.ui.views.html.research

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

object index extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template3[femr.common.dtos.CurrentUser,femr.ui.models.research.FilterViewModel,AssetsFinder,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(currentUser: femr.common.dtos.CurrentUser, viewModel:femr.ui.models.research.FilterViewModel, assets: AssetsFinder):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {
/*3.2*/import femr.ui.views.html.layouts.main
/*4.2*/import femr.ui.controllers.routes.ResearchController
/*5.2*/import scala.collection.JavaConversions._
/*6.2*/import helper._

def /*10.6*/additionalStyles/*10.22*/:play.twirl.api.HtmlFormat.Appendable = {_display_(

Seq[Any](format.raw/*10.26*/("""
        """),format.raw/*11.9*/("""<link rel="stylesheet" href=""""),_display_(/*11.39*/assets/*11.45*/.path("css/research.css")),format.raw/*11.70*/("""">
    """)))};def /*13.6*/additionalScripts/*13.23*/:play.twirl.api.HtmlFormat.Appendable = {_display_(

Seq[Any](format.raw/*13.27*/("""
        """),format.raw/*14.9*/("""<script type="text/javascript" src=""""),_display_(/*14.46*/assets/*14.52*/.path("js/research/d3.min.js")),format.raw/*14.82*/(""""></script>
        <script type="text/javascript" src=""""),_display_(/*15.46*/assets/*15.52*/.path("js/research/d3.tip.js")),format.raw/*15.82*/(""""></script>
        <script type="text/javascript" src=""""),_display_(/*16.46*/assets/*16.52*/.path("js/research/saveSvgAsPng.js")),format.raw/*16.88*/(""""></script>
        <script type="text/javascript" src=""""),_display_(/*17.46*/assets/*17.52*/.path("js/research/filter-menu.js")),format.raw/*17.87*/(""""></script>
        <script type="text/javascript" src=""""),_display_(/*18.46*/assets/*18.52*/.path("js/research/bar-graph.js")),format.raw/*18.85*/(""""></script>
        <script type="text/javascript" src=""""),_display_(/*19.46*/assets/*19.52*/.path("js/research/pie-graph.js")),format.raw/*19.85*/(""""></script>
        <script type="text/javascript" src=""""),_display_(/*20.46*/assets/*20.52*/.path("js/research/line-graph.js")),format.raw/*20.86*/(""""></script>
        <script type="text/javascript" src=""""),_display_(/*21.46*/assets/*21.52*/.path("js/research/scatter-plot.js")),format.raw/*21.88*/(""""></script>
        <script type="text/javascript" src=""""),_display_(/*22.46*/assets/*22.52*/.path("js/research/stacked-bar.js")),format.raw/*22.87*/(""""></script>
        <script type="text/javascript" src=""""),_display_(/*23.46*/assets/*23.52*/.path("js/research/grouped-bar.js")),format.raw/*23.87*/(""""></script>
        <script type="text/javascript" src=""""),_display_(/*24.46*/assets/*24.52*/.path("js/research/table-chart.js")),format.raw/*24.87*/(""""></script>
        <script type="text/javascript" src=""""),_display_(/*25.46*/assets/*25.52*/.path("js/libraries/typeahead.bundle.min.js")),format.raw/*25.97*/(""""></script>
        <script type="text/javascript" src=""""),_display_(/*26.46*/assets/*26.52*/.path("js/research/research.js")),format.raw/*26.84*/(""""></script>
    """)))};
Seq[Any](format.raw/*2.1*/("""
"""),format.raw/*7.1*/("""


    """),format.raw/*12.6*/("""
    """),format.raw/*27.6*/("""

"""),_display_(/*29.2*/main("Research", currentUser, styles = additionalStyles, scripts = additionalScripts, assets = assets)/*29.104*/ {_display_(Seq[Any](format.raw/*29.106*/("""

        """),format.raw/*31.9*/("""<div class="row dropdown">
            <div class="col-sm-3 col-md-2 sidebar">
                """),_display_(/*33.18*/helper/*33.24*/.form(action = ResearchController.exportPost, 'id -> "graph-options")/*33.93*/ {_display_(Seq[Any](format.raw/*33.95*/("""
                    """),format.raw/*34.21*/("""<div id="filter-errors">

                    </div>
                    <ul class="nav nav-sidebar">
                        <li id="gdata1-menu" class="menu-item">
                            <a class="opt-link">Primary Dataset</a>
                            <span class="val"></span>
                            <span class="glyphicon glyphicon-chevron-right"></span>
                            <ul class="submenu">
                                <li class="title">Demographics</li>
                                <li><a data-dname1="age" href="#">Age</a></li>
                                <li><a data-dname1="gender" href="#">Gender</a></li>
                                <li><a data-dname1="height" href="#">Height</a></li>
                                <li><a data-dname1="weight" href="#">Weight</a></li>
                                <li><a data-dname1="pregnancyStatus" href="#">Pregnancy Status</a></li>
                                <li><a data-dname1="pregnancyTime" href="#">Weeks Pregnant</a></li>
                               <li class="title">Medication</li>
                                <li><a data-dname1="prescribedMeds" href="#">Prescribed Medications</a></li>
                                <li><a data-dname1="dispensedMeds" href="#">Dispensed Medications</a></li>
                                <li class="title">Vitals</li>
                                <li><a data-dname1="temperature" href="#">Temperature</a></li>
                                <li><a data-dname1="bloodPressureSystolic" href="#">Blood Pressure Systolic</a></li>
                                <li><a data-dname1="bloodPressureDiastolic" href="#">Blood Pressure Diastolic</a></li>
                                <li><a data-dname1="heartRate" href="#">Heart Rate</a></li>
                                <li><a data-dname1="respiratoryRate" href="#">Respirations</a></li>
                                <li><a data-dname1="oxygenSaturation" href="#">Oxygen Saturation</a></li>
                                <li><a data-dname1="glucose" href="#">Glucose</a></li>
                                """),format.raw/*63.35*/("""
                            """),format.raw/*64.29*/("""</ul>
                        </li>
                        <li id="gdata2-menu" class="menu-item">
                            <a class="opt-link">Secondary Dataset</a>
                            <span class="val"></span>
                            <span class="glyphicon glyphicon-chevron-right"></span>
                            <ul class="submenu">
                                <li><a class="clear" data-dname2="clear" href="#">None</a></li>
                                <li><a data-dname2="age" href="#">Age</a></li>
                                <li><a data-dname2="gender" href="#">Gender</a></li>
                                <li><a data-dname2="pregnancyStatus" href="#">Pregnancy Status</a></li>

                            </ul>
                            """),format.raw/*101.31*/("""
                        """),format.raw/*102.25*/("""</li>
                        <li id="gtype-menu" class="menu-item">
                            <a class="opt-link">Graph Type</a>
                            <span class="val"></span>
                            <span class="glyphicon glyphicon-chevron-right"></span>
                            <ul class="submenu">
                                <li><a data-gtype="bar" href="#">Bar Graph</a></li>
                                <li><a data-gtype="pie" href="#">Pie Graph</a></li>
                                <li><a data-gtype="line" href="#">Line Graph</a></li>
                                <li><a data-gtype="scatter" href="#">Scatterplot</a></li>
                                <li><a data-gtype="stacked-bar" href="#">Stacked Bar Graph</a></li>
                                <li><a data-gtype="grouped-bar" href="#">Grouped Bar Graph</a></li>
                                <li><a data-gtype="table" href="#">Table</a></li>
                            </ul>
                        </li>

                        <li id="gfilter-menu" class="menu-item">
                            <a class="opt-link">Filter Date (DD/MM/YYYY)</a>
                            <span class="val">
                                <span class="date">
                                    <span hidden class="start">"""),_display_(/*122.65*/viewModel/*122.74*/.getStartDate()),format.raw/*122.89*/("""</span><span hidden class="end">"""),_display_(/*122.122*/viewModel/*122.131*/.getEndDate()),format.raw/*122.144*/("""</span>
                                    <span id="start-up"></span> - <span  id="start-end"></span>
                                </span>
                            </span>
                            <span class="glyphicon glyphicon-chevron-right"></span>
                            <ul class="submenu">
                                <li>Start Date
                                    (DD/MM/YYYY)
                                    <input type="date" id="startDate" name="startDate" format="dd/MM/yyyy" onchange="mydate1()"  hidden value=""""),_display_(/*130.145*/viewModel/*130.154*/.getStartDate()),format.raw/*130.169*/("""" max=""""),_display_(/*130.177*/viewModel/*130.186*/.getEndDate()),format.raw/*130.199*/("""" />
                                    <input type="text" id="ndt"  onclick="mydate()"  format="dd/MM/yyyy" value=""""),_display_(/*131.114*/viewModel/*131.123*/.getStartDate()),format.raw/*131.138*/(""""  />
                                </li>
                                <li>End Date
                                    (DD/MM/YYYY)
                                    <input type="date" id="endDate" name="endDate" format="dd/MM/yyyy" onchange="mydate3()"  hidden value=""""),_display_(/*135.141*/viewModel/*135.150*/.getEndDate()),format.raw/*135.163*/("""" max=""""),_display_(/*135.171*/viewModel/*135.180*/.getEndDate()),format.raw/*135.193*/("""" />
                                    <input type="text" id="ndt2"  onclick="mydate2()"  format="dd/MM/yyyy" value=""""),_display_(/*136.116*/viewModel/*136.125*/.getEndDate()),format.raw/*136.138*/(""""  />
                                </li>
                            </ul>
                        </li>


                        <li class="controls">

                            <input type="hidden" id="primaryDataset" name="primaryDataset" value="" />
                            <input type="hidden" id="secondaryDataset" name="secondaryDataset" value="" />
                            <input type="hidden" id="graphType" name="graphType" value="" />


                            """),format.raw/*155.35*/("""
                                """),format.raw/*156.33*/("""<span class="medication">
                                <label>Filter Trip</label>
                                    <select id="MissionTripId" name="MissionTripId" class="medication">
                                        <option selected id="default" value="-1">-- Select Trip --</option> """),format.raw/*159.131*/("""
                                        """),_display_(/*160.42*/for(i <- 1 to viewModel.getMissionTrips.size) yield /*160.87*/ {_display_(Seq[Any](format.raw/*160.89*/("""
                                            """),_display_(/*161.46*/defining(viewModel.getMissionTrips.get(i - 1))/*161.92*/ { missionItem =>_display_(Seq[Any](format.raw/*161.109*/("""
                                                """),_display_(/*162.50*/for(tripIndex <- 1 to missionItem.getMissionTrips.size) yield /*162.105*/ {_display_(Seq[Any](format.raw/*162.107*/("""
                                                    """),_display_(/*163.54*/defining(missionItem.getMissionTrips.get(tripIndex - 1))/*163.110*/ { missionTrip =>_display_(Seq[Any](format.raw/*163.127*/("""
                                                        """),format.raw/*164.57*/("""<option value = """"),_display_(/*164.75*/missionTrip/*164.86*/.getId()),format.raw/*164.94*/(""""> """),_display_(/*164.98*/missionItem/*164.109*/.getMissionTrips.get(tripIndex - 1).getFriendlyTripTitle),format.raw/*164.165*/(""" """),format.raw/*164.166*/("""</option>
                                                    """)))}),format.raw/*165.54*/("""
                                                """)))}),format.raw/*166.50*/("""
                                            """)))}),format.raw/*167.46*/("""
                                        """)))}),format.raw/*168.42*/("""
                                    """),format.raw/*169.37*/("""</select>
                                </span>

                            <span class="filter">
                                <label>Filter Primary Dataset</label>
                                <input type="text" id="filterRangeStart" name="filterRangeStart" placeholder="Start" value="" /> &ndash;
                                <input type="text" id="filterRangeEnd" name="filterRangeEnd" placeholder="End" value="" />
                                <span id="filter-primary-clear" class="glyphicon glyphicon-remove"></span>
                            </span>



                            <span class="group">
                                <label for="groupPrimary"><input type="checkbox" id="groupPrimary" name="groupPrimary" value="1" />Group Primary</label>
                                <input type="text" name="groupFactor" id="groupFactor" value="10" />
                            </span>
                        </li>
                        <li class="submit">
                            <input id="clear-button" class="clear fButton pull-left" type="button" value="Clear" />
                            <input id="submit-button" class="submit fButton pull-right" type="submit" value="Apply" />
                        </li>
                        <li class="export">
                            <input id="export-button" class="export fButton" type="button" value="Export Data"/>
                        </li>
                    </ul>
                """)))}),format.raw/*194.18*/("""

            """),format.raw/*196.13*/("""</div>
            <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">

                <h1 class="page-header">Research</h1>

                <div class="chart-container">

                    <div class="graph-instructions">

                        <p>To view a graph, from the sidebar on the left:</p>
                        <ol>
                            <li>Choose Dataset(s)</li>
                            <li>Choose a Graph Type</li>
                            <li>Choose Filters</li>
                            <li>Click Apply</li>
                        </ol>

                        <p>To export data, click the "Export Data" button. The exported data will be de-identified and it will have all selected Datasets and Filters applied to it.</p>
                    </div>
                    <div class="graph-area">
                        <div class="graph-header">
                            <p id="average" class="stat"><strong>Average:</strong> <span class="val"></span></p>
                            <p id="range" class="stat"><strong>Range:</strong> <span class="val"></span></p>
                            <p id="totalPatients" class="stat"><strong>Total Patients:</strong> <span class="val"></span></p>
                            <p id="totalEncounters" class="stat"><strong>Total Encounters:</strong> <span class="val"></span></p>

                            <div class="save-image-cont">
                                <a id="save-button" class="fButton" href="#">Save As Image</a>
                                <div class="options">
                                    <div class="close"><span class="glyphicon glyphicon-remove"></span></div>
                                    <p>Choose Image Size</p>
                                    <a href="#" class="image-size-selection" data-imagesize="small">Small<br />700 x 350</a>
                                    <a href="#" class="image-size-selection" data-imagesize="medium">Medium<br />1000 x 500</a>
                                    <a href="#" class="image-size-selection" data-imagesize="large">Large<br />1200 x 600</a>
                                </div>
                            </div>
                        </div>
                        <svg id="legend"></svg>
                        <svg id="graph" class="chart">

                        </svg>
                        <img class="loading" src=""""),_display_(/*237.52*/assets/*237.58*/.path("img/graph-loader.gif")),format.raw/*237.87*/("""" alt="loading&hellip;" />
                    </div>
                </div>
                <div id="table-container" class="table-responsive">

                </div>

            </div>

        </div>
""")))}),format.raw/*247.2*/("""
"""))
      }
    }
  }

  def render(currentUser:femr.common.dtos.CurrentUser,viewModel:femr.ui.models.research.FilterViewModel,assets:AssetsFinder): play.twirl.api.HtmlFormat.Appendable = apply(currentUser,viewModel,assets)

  def f:((femr.common.dtos.CurrentUser,femr.ui.models.research.FilterViewModel,AssetsFinder) => play.twirl.api.HtmlFormat.Appendable) = (currentUser,viewModel,assets) => apply(currentUser,viewModel,assets)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  DATE: Wed Jan 20 18:29:25 PST 2021
                  SOURCE: /Users/noel/Desktop/CPE/femr/femr/app/femr/ui/views/research/index.scala.html
                  HASH: f0ce24e43e5d100cec7fa9d2580f5ff7cfd50af0
                  MATRIX: 1040->1|1229->120|1275->160|1335->214|1384->257|1413->281|1438->297|1519->301|1555->310|1612->340|1627->346|1673->371|1704->385|1730->402|1811->406|1847->415|1911->452|1926->458|1977->488|2061->545|2076->551|2127->581|2211->638|2226->644|2283->680|2367->737|2382->743|2438->778|2522->835|2537->841|2591->874|2675->931|2690->937|2744->970|2828->1027|2843->1033|2898->1067|2982->1124|2997->1130|3054->1166|3138->1223|3153->1229|3209->1264|3293->1321|3308->1327|3364->1362|3448->1419|3463->1425|3519->1460|3603->1517|3618->1523|3684->1568|3768->1625|3783->1631|3836->1663|3891->118|3918->273|3952->379|3984->1680|4013->1683|4125->1785|4166->1787|4203->1797|4326->1893|4341->1899|4419->1968|4459->1970|4508->1991|6654->4292|6711->4321|7524->7121|7578->7146|8921->8461|8940->8470|8977->8485|9039->8518|9059->8527|9095->8540|9677->9093|9697->9102|9735->9117|9772->9125|9792->9134|9828->9147|9975->9265|9995->9274|10033->9289|10340->9567|10360->9576|10396->9589|10433->9597|10453->9606|10489->9619|10638->9739|10658->9748|10694->9761|11213->10569|11275->10602|11602->10921|11672->10963|11734->11008|11775->11010|11849->11056|11905->11102|11962->11119|12040->11169|12113->11224|12155->11226|12237->11280|12304->11336|12361->11353|12447->11410|12493->11428|12514->11439|12544->11447|12576->11451|12598->11462|12677->11518|12708->11519|12803->11582|12885->11632|12963->11678|13037->11720|13103->11757|14620->13242|14663->13256|17123->15688|17139->15694|17190->15723|17427->15929
                  LINES: 28->1|31->3|32->4|33->5|34->6|36->10|36->10|38->10|39->11|39->11|39->11|39->11|40->13|40->13|42->13|43->14|43->14|43->14|43->14|44->15|44->15|44->15|45->16|45->16|45->16|46->17|46->17|46->17|47->18|47->18|47->18|48->19|48->19|48->19|49->20|49->20|49->20|50->21|50->21|50->21|51->22|51->22|51->22|52->23|52->23|52->23|53->24|53->24|53->24|54->25|54->25|54->25|55->26|55->26|55->26|57->2|58->7|61->12|62->27|64->29|64->29|64->29|66->31|68->33|68->33|68->33|68->33|69->34|96->63|97->64|110->101|111->102|131->122|131->122|131->122|131->122|131->122|131->122|139->130|139->130|139->130|139->130|139->130|139->130|140->131|140->131|140->131|144->135|144->135|144->135|144->135|144->135|144->135|145->136|145->136|145->136|158->155|159->156|162->159|163->160|163->160|163->160|164->161|164->161|164->161|165->162|165->162|165->162|166->163|166->163|166->163|167->164|167->164|167->164|167->164|167->164|167->164|167->164|167->164|168->165|169->166|170->167|171->168|172->169|197->194|199->196|240->237|240->237|240->237|250->247
                  -- GENERATED --
              */
          