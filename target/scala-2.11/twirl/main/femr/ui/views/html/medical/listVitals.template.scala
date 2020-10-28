
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

object listVitals extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template3[femr.util.DataStructure.Mapping.VitalMultiMap,femr.ui.models.medical.EditViewModelGet,AssetsFinder,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(vitalMap: femr.util.DataStructure.Mapping.VitalMultiMap, viewModel: femr.ui.models.medical.EditViewModelGet, assets: AssetsFinder):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {
/*3.2*/import femr.ui.views.html.partials.helpers.outputHeightOrNA
/*4.2*/import femr.ui.views.html.partials.helpers.outputWeightOrNA
/*5.2*/import femr.ui.views.html.partials.helpers.outputTemperatureOrNA
/*6.2*/import femr.ui.views.html.partials.helpers.outputYesOrNA


Seq[Any](format.raw/*1.133*/("""

"""),format.raw/*7.1*/("""
"""),format.raw/*9.87*/("""

"""),format.raw/*11.1*/("""<link rel="stylesheet" href=""""),_display_(/*11.31*/assets/*11.37*/.path("css/libraries/jquery.tablescroll.css")),format.raw/*11.82*/("""">
<link rel="stylesheet" href=""""),_display_(/*12.31*/assets/*12.37*/.path("css/medical/listVitals.css")),format.raw/*12.72*/("""">

<script>window.jQuery || document.write ( '<script type="text/javascript" src="   """),_display_(/*14.84*/assets/*14.90*/.path("js/libraries/jquery-2.2.4.min.js")),format.raw/*14.131*/(""" """),format.raw/*14.132*/(""""><\/script>')</script>
<script type="text/javascript" src=""""),_display_(/*15.38*/assets/*15.44*/.path("js/libraries/jquery.tablescroll.js")),format.raw/*15.87*/(""""></script>
<script type="text/javascript" src=""""),_display_(/*16.38*/assets/*16.44*/.path("js/medical/listVitals.js")),format.raw/*16.77*/(""""></script>

<table id="vitalTable" cellspacing="0">
    <thead></thead>
    <tbody>
        <tr class="first">
            <th></th>
            """),_display_(/*23.14*/for(dateIndex <- 1 to vitalMap.getDateListChronological.size) yield /*23.75*/ {_display_(Seq[Any](format.raw/*23.77*/("""
                """),format.raw/*24.17*/("""<td>"""),_display_(/*24.22*/vitalMap/*24.30*/.getFormatedDateTime(dateIndex - 1)),format.raw/*24.65*/("""</td>
            """)))}),format.raw/*25.14*/("""
        """),format.raw/*26.9*/("""</tr>
        <tr id="bloodPressure">
            <th>BP</th>
            """),_display_(/*29.14*/for(dateIndex <- 1 to vitalMap.getDateListChronological.size) yield /*29.75*/ {_display_(Seq[Any](format.raw/*29.77*/("""
                """),format.raw/*30.17*/("""<td>
                    <span class="systolic">"""),_display_(/*31.45*/vitalMap/*31.53*/.get("bloodPressureSystolic", vitalMap.getDate(dateIndex - 1))),format.raw/*31.115*/("""</span>
                    / <span class="diastolic">"""),_display_(/*32.48*/vitalMap/*32.56*/.get("bloodPressureDiastolic", vitalMap.getDate(dateIndex - 1))),format.raw/*32.119*/("""</span>
                </td>
            """)))}),format.raw/*34.14*/("""
        """),format.raw/*35.9*/("""</tr>
        <tr id="heartRate">
            <th>HR</th>
            """),_display_(/*38.14*/for(dateIndex <- 1 to vitalMap.getDateListChronological.size) yield /*38.75*/ {_display_(Seq[Any](format.raw/*38.77*/("""
                """),format.raw/*39.17*/("""<td>"""),_display_(/*39.22*/vitalMap/*39.30*/.get("heartRate", vitalMap.getDate(dateIndex - 1))),format.raw/*39.80*/("""</td>
            """)))}),format.raw/*40.14*/("""
        """),format.raw/*41.9*/("""</tr>
        <tr id="temperature">
            <th>T</th>
            """),_display_(/*44.14*/for(dateIndex <- 1 to vitalMap.getDateListChronological.size) yield /*44.75*/ {_display_(Seq[Any](format.raw/*44.77*/("""
                """),format.raw/*45.17*/("""<td>"""),_display_(/*45.22*/if(viewModel.getSettings.isMetric)/*45.56*/ {_display_(Seq[Any](format.raw/*45.58*/("""
                    """),_display_(/*46.22*/outputTemperatureOrNA(vitalMap.get("temperatureCelsius", vitalMap.getDate(dateIndex - 1)), viewModel.getSettings.isMetric, "")),format.raw/*46.148*/("""
                    """),format.raw/*47.21*/("""/"""),_display_(/*47.23*/outputTemperatureOrNA(vitalMap.get("temperature", vitalMap.getDate(dateIndex - 1)), !viewModel.getSettings.isMetric, "")),format.raw/*47.143*/("""
                """)))}/*48.19*/else/*48.24*/{_display_(Seq[Any](format.raw/*48.25*/("""
                    """),_display_(/*49.22*/outputTemperatureOrNA(vitalMap.get("temperature", vitalMap.getDate(dateIndex - 1)), viewModel.getSettings.isMetric, "")),format.raw/*49.141*/("""
                    """),format.raw/*50.21*/("""/"""),_display_(/*50.23*/outputTemperatureOrNA(vitalMap.get("temperatureCelsius", vitalMap.getDate(dateIndex - 1)), !viewModel.getSettings.isMetric, "")),format.raw/*50.150*/("""
                """)))}),format.raw/*51.18*/("""
                """),format.raw/*52.17*/("""</td>
            """)))}),format.raw/*53.14*/("""
        """),format.raw/*54.9*/("""</tr>
        <tr id="respiratoryRate">
            <th>RR</th>
            """),_display_(/*57.14*/for(dateIndex <- 1 to vitalMap.getDateListChronological.size) yield /*57.75*/ {_display_(Seq[Any](format.raw/*57.77*/("""
                """),format.raw/*58.17*/("""<td>"""),_display_(/*58.22*/vitalMap/*58.30*/.get("respiratoryRate", vitalMap.getDate(dateIndex - 1))),format.raw/*58.86*/("""</td>
            """)))}),format.raw/*59.14*/("""
        """),format.raw/*60.9*/("""</tr>
        <tr id="glucose">
            <th>Gluc</th>
            """),_display_(/*63.14*/for(dateIndex <- 1 to vitalMap.getDateListChronological.size) yield /*63.75*/ {_display_(Seq[Any](format.raw/*63.77*/("""
                """),format.raw/*64.17*/("""<td>"""),_display_(/*64.22*/vitalMap/*64.30*/.get("glucose", vitalMap.getDate(dateIndex - 1))),format.raw/*64.78*/("""</td>
            """)))}),format.raw/*65.14*/("""
        """),format.raw/*66.9*/("""</tr>
        <tr id="oxygenSaturation">
            <th>SpO2</th>
            """),_display_(/*69.14*/for(dateIndex <- 1 to vitalMap.getDateListChronological.size) yield /*69.75*/ {_display_(Seq[Any](format.raw/*69.77*/("""
                """),format.raw/*70.17*/("""<td>"""),_display_(/*70.22*/vitalMap/*70.30*/.get("oxygenSaturation", vitalMap.getDate(dateIndex - 1))),format.raw/*70.87*/("""</td>
			""")))}),format.raw/*71.5*/("""
        """),format.raw/*72.9*/("""</tr>
        <tr>
            <th>WP</th> <!-- Sam Zanni -->
            """),_display_(/*75.14*/for(dateIndex <- 1 to vitalMap.getDateListChronological.size) yield /*75.75*/ {_display_(Seq[Any](format.raw/*75.77*/("""
                """),format.raw/*76.17*/("""<td>"""),_display_(/*76.22*/vitalMap/*76.30*/.get("weeksPregnant", vitalMap.getDate(dateIndex - 1))),format.raw/*76.84*/("""</td>
            """)))}),format.raw/*77.14*/("""
        """),format.raw/*78.9*/("""</tr>
        <tr id="height">
            <th>Ht</th>
            <!--- Change metric output here --->
            """),_display_(/*82.14*/for(dateIndex <- 1 to vitalMap.getDateListChronological.size) yield /*82.75*/ {_display_(Seq[Any](format.raw/*82.77*/("""
                """),format.raw/*83.17*/("""<td data-feet=""""),_display_(/*83.33*/vitalMap/*83.41*/.get("heightFeet", vitalMap.getDate(dateIndex - 1))),format.raw/*83.92*/(""""
                    data-inches=""""),_display_(/*84.35*/vitalMap/*84.43*/.get("heightInches", vitalMap.getDate(dateIndex - 1))),format.raw/*84.96*/("""">

                """),_display_(/*86.18*/if(viewModel.getSettings.isMetric)/*86.52*/ {_display_(Seq[Any](format.raw/*86.54*/("""
                    """),format.raw/*87.67*/("""
                    """),_display_(/*88.22*/outputHeightOrNA(
                        vitalMap.get("heightMeters", vitalMap.getDate(dateIndex - 1)),
                        vitalMap.get("heightCm", vitalMap.getDate(dateIndex - 1)),
                        viewModel.getSettings.isMetric,
                        ""
                    )),format.raw/*93.22*/("""
                    """),format.raw/*94.21*/("""/"""),_display_(/*94.23*/outputHeightOrNA(
                        vitalMap.get("heightFeet", vitalMap.getDate(dateIndex - 1)),
                        vitalMap.get("heightInches", vitalMap.getDate(dateIndex - 1)),
                        !viewModel.getSettings.isMetric,
                        ""
                    )),format.raw/*99.22*/("""
                """)))}/*100.19*/else/*100.24*/{_display_(Seq[Any](format.raw/*100.25*/("""
                    """),format.raw/*101.70*/("""
                    """),_display_(/*102.22*/outputHeightOrNA(
                        vitalMap.get("heightFeet", vitalMap.getDate(dateIndex - 1)),
                        vitalMap.get("heightInches", vitalMap.getDate(dateIndex - 1)),
                        viewModel.getSettings.isMetric,
                        ""
                    )),format.raw/*107.22*/(""" """),format.raw/*107.23*/("""/"""),_display_(/*107.25*/outputHeightOrNA(
                        vitalMap.get("heightMeters", vitalMap.getDate(dateIndex - 1)),
                        vitalMap.get("heightCm", vitalMap.getDate(dateIndex - 1)),
                        !viewModel.getSettings.isMetric,
                        ""
                    )),format.raw/*112.22*/("""
                """)))}),format.raw/*113.18*/("""
                """),format.raw/*114.17*/("""</td>
            """)))}),format.raw/*115.14*/("""
        """),format.raw/*116.9*/("""</tr>
        <tr id="weight">
            <th>Wt</th>
            """),_display_(/*119.14*/for(dateIndex <- 1 to vitalMap.getDateListChronological.size) yield /*119.75*/ {_display_(Seq[Any](format.raw/*119.77*/("""
                """),format.raw/*120.17*/("""<td data-weight=""""),_display_(/*120.35*/vitalMap/*120.43*/.get("weight", vitalMap.getDate(dateIndex - 1))),format.raw/*120.90*/("""">
                """),_display_(/*121.18*/if(viewModel.getSettings.isMetric)/*121.52*/ {_display_(Seq[Any](format.raw/*121.54*/("""
                    """),_display_(/*122.22*/outputWeightOrNA(vitalMap.get("weightKgs", vitalMap.getDate(dateIndex - 1)), viewModel.getSettings.isMetric, "")),format.raw/*122.134*/("""
                    """),format.raw/*123.21*/("""/"""),_display_(/*123.23*/outputWeightOrNA(vitalMap.get("weight", vitalMap.getDate(dateIndex - 1)), !viewModel.getSettings.isMetric, "")),format.raw/*123.133*/("""
                """)))}/*124.19*/else/*124.24*/{_display_(Seq[Any](format.raw/*124.25*/("""
                    """),_display_(/*125.22*/outputWeightOrNA(vitalMap.get("weight", vitalMap.getDate(dateIndex - 1)), viewModel.getSettings.isMetric, "")),format.raw/*125.131*/("""
                  """),format.raw/*126.19*/("""/"""),_display_(/*126.21*/outputWeightOrNA(vitalMap.get("weightKgs", vitalMap.getDate(dateIndex - 1)), !viewModel.getSettings.isMetric, "")),format.raw/*126.134*/("""

                """)))}),format.raw/*128.18*/("""
                """),format.raw/*129.17*/("""</td>
            """)))}),format.raw/*130.14*/("""
        """),format.raw/*131.9*/("""</tr>
        """),format.raw/*132.87*/("""
        """),format.raw/*133.9*/("""<tr id="smoker">
            <th>Smoking</th>
            """),_display_(/*135.14*/for(dateIndex <- 1 to vitalMap.getDateListChronological.size) yield /*135.75*/ {_display_(Seq[Any](format.raw/*135.77*/("""
                """),format.raw/*136.17*/("""<td> """),_display_(/*136.23*/outputYesOrNA(vitalMap.get("smoker", vitalMap.getDate(dateIndex - 1)))),format.raw/*136.93*/("""</td>
            """)))}),format.raw/*137.14*/("""
        """),format.raw/*138.9*/("""</tr>
        <tr id="diabetic">
            <th>Diabetes</th>
            """),_display_(/*141.14*/for(dateIndex <- 1 to vitalMap.getDateListChronological.size) yield /*141.75*/ {_display_(Seq[Any](format.raw/*141.77*/("""
                """),format.raw/*142.17*/("""<td> """),_display_(/*142.23*/outputYesOrNA(vitalMap.get("diabetic", vitalMap.getDate(dateIndex - 1)))),format.raw/*142.95*/("""</td>
            """)))}),format.raw/*143.14*/("""
        """),format.raw/*144.9*/("""</tr>
        <tr id="alcohol">
            <th>Alcohol</th>
            """),_display_(/*147.14*/for(dateIndex <- 1 to vitalMap.getDateListChronological.size) yield /*147.75*/ {_display_(Seq[Any](format.raw/*147.77*/("""
                """),format.raw/*148.17*/("""<td> """),_display_(/*148.23*/outputYesOrNA(vitalMap.get("alcohol", vitalMap.getDate(dateIndex - 1)))),format.raw/*148.94*/("""</td>
            """)))}),format.raw/*149.14*/("""
        """),format.raw/*150.9*/("""</tr>
    </tbody>
</table>"""))
      }
    }
  }

  def render(vitalMap:femr.util.DataStructure.Mapping.VitalMultiMap,viewModel:femr.ui.models.medical.EditViewModelGet,assets:AssetsFinder): play.twirl.api.HtmlFormat.Appendable = apply(vitalMap,viewModel,assets)

  def f:((femr.util.DataStructure.Mapping.VitalMultiMap,femr.ui.models.medical.EditViewModelGet,AssetsFinder) => play.twirl.api.HtmlFormat.Appendable) = (vitalMap,viewModel,assets) => apply(vitalMap,viewModel,assets)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  DATE: Tue Oct 27 21:59:18 PDT 2020
                  SOURCE: C:/Users/Noel/Documents/csc402/lemur-femr/femr/app/femr/ui/views/medical/listVitals.scala.html
                  HASH: c9c555213d5b2e21977f74225366bbca4c71667b
                  MATRIX: 1061->1|1265->137|1332->199|1399->261|1471->328|1558->132|1588->386|1617->565|1648->569|1705->599|1720->605|1786->650|1847->684|1862->690|1918->725|2034->814|2049->820|2112->861|2142->862|2231->924|2246->930|2310->973|2387->1023|2402->1029|2456->1062|2637->1216|2714->1277|2754->1279|2800->1297|2832->1302|2849->1310|2905->1345|2956->1365|2993->1375|3098->1453|3175->1514|3215->1516|3261->1534|3338->1584|3355->1592|3439->1654|3522->1710|3539->1718|3624->1781|3700->1826|3737->1836|3838->1910|3915->1971|3955->1973|4001->1991|4033->1996|4050->2004|4121->2054|4172->2074|4209->2084|4311->2159|4388->2220|4428->2222|4474->2240|4506->2245|4549->2279|4589->2281|4639->2304|4787->2430|4837->2452|4866->2454|5008->2574|5046->2594|5059->2599|5098->2600|5148->2623|5289->2742|5339->2764|5368->2766|5517->2893|5567->2912|5613->2930|5664->2950|5701->2960|5808->3040|5885->3101|5925->3103|5971->3121|6003->3126|6020->3134|6097->3190|6148->3210|6185->3220|6286->3294|6363->3355|6403->3357|6449->3375|6481->3380|6498->3388|6567->3436|6618->3456|6655->3466|6765->3549|6842->3610|6882->3612|6928->3630|6960->3635|6977->3643|7055->3700|7096->3711|7133->3721|7238->3799|7315->3860|7355->3862|7401->3880|7433->3885|7450->3893|7525->3947|7576->3967|7613->3977|7761->4098|7838->4159|7878->4161|7924->4179|7967->4195|7984->4203|8056->4254|8120->4291|8137->4299|8211->4352|8261->4375|8304->4409|8344->4411|8394->4479|8444->4502|8762->4799|8812->4821|8841->4823|9162->5123|9201->5143|9215->5148|9255->5149|9306->5220|9357->5243|9678->5542|9708->5543|9738->5545|10058->5843|10109->5862|10156->5880|10208->5900|10246->5910|10345->5981|10423->6042|10464->6044|10511->6062|10557->6080|10575->6088|10644->6135|10693->6156|10737->6190|10778->6192|10829->6215|10964->6327|11015->6349|11045->6351|11178->6461|11217->6481|11231->6486|11271->6487|11322->6510|11454->6619|11503->6639|11533->6641|11669->6754|11722->6775|11769->6793|11821->6813|11859->6823|11903->6916|11941->6926|12030->6987|12108->7048|12149->7050|12196->7068|12230->7074|12322->7144|12374->7164|12412->7174|12519->7253|12597->7314|12638->7316|12685->7334|12719->7340|12813->7412|12865->7432|12903->7442|13008->7519|13086->7580|13127->7582|13174->7600|13208->7606|13301->7677|13353->7697|13391->7707
                  LINES: 28->1|31->3|32->4|33->5|34->6|37->1|39->7|40->9|42->11|42->11|42->11|42->11|43->12|43->12|43->12|45->14|45->14|45->14|45->14|46->15|46->15|46->15|47->16|47->16|47->16|54->23|54->23|54->23|55->24|55->24|55->24|55->24|56->25|57->26|60->29|60->29|60->29|61->30|62->31|62->31|62->31|63->32|63->32|63->32|65->34|66->35|69->38|69->38|69->38|70->39|70->39|70->39|70->39|71->40|72->41|75->44|75->44|75->44|76->45|76->45|76->45|76->45|77->46|77->46|78->47|78->47|78->47|79->48|79->48|79->48|80->49|80->49|81->50|81->50|81->50|82->51|83->52|84->53|85->54|88->57|88->57|88->57|89->58|89->58|89->58|89->58|90->59|91->60|94->63|94->63|94->63|95->64|95->64|95->64|95->64|96->65|97->66|100->69|100->69|100->69|101->70|101->70|101->70|101->70|102->71|103->72|106->75|106->75|106->75|107->76|107->76|107->76|107->76|108->77|109->78|113->82|113->82|113->82|114->83|114->83|114->83|114->83|115->84|115->84|115->84|117->86|117->86|117->86|118->87|119->88|124->93|125->94|125->94|130->99|131->100|131->100|131->100|132->101|133->102|138->107|138->107|138->107|143->112|144->113|145->114|146->115|147->116|150->119|150->119|150->119|151->120|151->120|151->120|151->120|152->121|152->121|152->121|153->122|153->122|154->123|154->123|154->123|155->124|155->124|155->124|156->125|156->125|157->126|157->126|157->126|159->128|160->129|161->130|162->131|163->132|164->133|166->135|166->135|166->135|167->136|167->136|167->136|168->137|169->138|172->141|172->141|172->141|173->142|173->142|173->142|174->143|175->144|178->147|178->147|178->147|179->148|179->148|179->148|180->149|181->150
                  -- GENERATED --
              */
          