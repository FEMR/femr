
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


Seq[Any](format.raw/*2.1*/("""
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
                  DATE: Wed Jan 20 18:29:26 PST 2021
                  SOURCE: /Users/noel/Desktop/CPE/femr/femr/app/femr/ui/views/medical/listVitals.scala.html
                  HASH: d9f749e16ef056d76b4c2a0955cdfb92a1bdc760
                  MATRIX: 1061->1|1265->135|1332->196|1399->257|1471->323|1556->133|1583->380|1611->557|1640->559|1697->589|1712->595|1778->640|1838->673|1853->679|1909->714|2023->801|2038->807|2101->848|2131->849|2219->910|2234->916|2298->959|2374->1008|2389->1014|2443->1047|2617->1194|2694->1255|2734->1257|2779->1274|2811->1279|2828->1287|2884->1322|2934->1341|2970->1350|3072->1425|3149->1486|3189->1488|3234->1505|3310->1554|3327->1562|3411->1624|3493->1679|3510->1687|3595->1750|3669->1793|3705->1802|3803->1873|3880->1934|3920->1936|3965->1953|3997->1958|4014->1966|4085->2016|4135->2035|4171->2044|4270->2116|4347->2177|4387->2179|4432->2196|4464->2201|4507->2235|4547->2237|4596->2259|4744->2385|4793->2406|4822->2408|4964->2528|5001->2547|5014->2552|5053->2553|5102->2575|5243->2694|5292->2715|5321->2717|5470->2844|5519->2862|5564->2879|5614->2898|5650->2907|5754->2984|5831->3045|5871->3047|5916->3064|5948->3069|5965->3077|6042->3133|6092->3152|6128->3161|6226->3232|6303->3293|6343->3295|6388->3312|6420->3317|6437->3325|6506->3373|6556->3392|6592->3401|6699->3481|6776->3542|6816->3544|6861->3561|6893->3566|6910->3574|6988->3631|7028->3641|7064->3650|7166->3725|7243->3786|7283->3788|7328->3805|7360->3810|7377->3818|7452->3872|7502->3891|7538->3900|7682->4017|7759->4078|7799->4080|7844->4097|7887->4113|7904->4121|7976->4172|8039->4208|8056->4216|8130->4269|8178->4290|8221->4324|8261->4326|8310->4393|8359->4415|8672->4707|8721->4728|8750->4730|9066->5025|9104->5044|9118->5049|9158->5050|9208->5120|9258->5142|9574->5436|9604->5437|9634->5439|9949->5732|9999->5750|10045->5767|10096->5786|10133->5795|10229->5863|10307->5924|10348->5926|10394->5943|10440->5961|10458->5969|10527->6016|10575->6036|10619->6070|10660->6072|10710->6094|10845->6206|10895->6227|10925->6229|11058->6339|11096->6358|11110->6363|11150->6364|11200->6386|11332->6495|11380->6514|11410->6516|11546->6629|11597->6648|11643->6665|11694->6684|11731->6693|11774->6785|11811->6794|11898->6853|11976->6914|12017->6916|12063->6933|12097->6939|12189->7009|12240->7028|12277->7037|12381->7113|12459->7174|12500->7176|12546->7193|12580->7199|12674->7271|12725->7290|12762->7299|12864->7373|12942->7434|12983->7436|13029->7453|13063->7459|13156->7530|13207->7549|13244->7558
                  LINES: 28->1|31->3|32->4|33->5|34->6|37->2|38->7|39->9|41->11|41->11|41->11|41->11|42->12|42->12|42->12|44->14|44->14|44->14|44->14|45->15|45->15|45->15|46->16|46->16|46->16|53->23|53->23|53->23|54->24|54->24|54->24|54->24|55->25|56->26|59->29|59->29|59->29|60->30|61->31|61->31|61->31|62->32|62->32|62->32|64->34|65->35|68->38|68->38|68->38|69->39|69->39|69->39|69->39|70->40|71->41|74->44|74->44|74->44|75->45|75->45|75->45|75->45|76->46|76->46|77->47|77->47|77->47|78->48|78->48|78->48|79->49|79->49|80->50|80->50|80->50|81->51|82->52|83->53|84->54|87->57|87->57|87->57|88->58|88->58|88->58|88->58|89->59|90->60|93->63|93->63|93->63|94->64|94->64|94->64|94->64|95->65|96->66|99->69|99->69|99->69|100->70|100->70|100->70|100->70|101->71|102->72|105->75|105->75|105->75|106->76|106->76|106->76|106->76|107->77|108->78|112->82|112->82|112->82|113->83|113->83|113->83|113->83|114->84|114->84|114->84|116->86|116->86|116->86|117->87|118->88|123->93|124->94|124->94|129->99|130->100|130->100|130->100|131->101|132->102|137->107|137->107|137->107|142->112|143->113|144->114|145->115|146->116|149->119|149->119|149->119|150->120|150->120|150->120|150->120|151->121|151->121|151->121|152->122|152->122|153->123|153->123|153->123|154->124|154->124|154->124|155->125|155->125|156->126|156->126|156->126|158->128|159->129|160->130|161->131|162->132|163->133|165->135|165->135|165->135|166->136|166->136|166->136|167->137|168->138|171->141|171->141|171->141|172->142|172->142|172->142|173->143|174->144|177->147|177->147|177->147|178->148|178->148|178->148|179->149|180->150
                  -- GENERATED --
              */
          