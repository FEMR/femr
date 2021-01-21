
package femr.ui.views.html.partials.medical.tabs

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

object hpi_fields extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template3[femr.common.models.TabItem,java.lang.Boolean,java.lang.String,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(hpiTab: femr.common.models.TabItem, isConsolidated: java.lang.Boolean, chiefComplaint: java.lang.String):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {
/*3.2*/import femr.ui.views.html.partials.medical.severityDropDown;


Seq[Any](format.raw/*2.1*/("""
"""),format.raw/*4.1*/("""
"""),format.raw/*5.48*/("""
"""),format.raw/*6.1*/("""<div class="hpiWraps hidden">

"""),_display_(/*8.2*/if(!isConsolidated)/*8.21*/ {_display_(Seq[Any](format.raw/*8.23*/("""
    """),format.raw/*9.5*/("""<div class="leftHPI">

        <div class="staticField">
            <label for="onset">Onset</label>
            """),_display_(/*13.14*/defining(hpiTab.getTabFieldItemByName(chiefComplaint, "onset"))/*13.77*/ { onset =>_display_(Seq[Any](format.raw/*13.88*/("""
                """),format.raw/*14.17*/("""<input name="tabFieldItems["""),_display_(/*14.45*/onset/*14.50*/.getIndex),format.raw/*14.59*/("""].name" type="text" class="hidden" value="onset"/>
                <input name="tabFieldItems["""),_display_(/*15.45*/onset/*15.50*/.getIndex),format.raw/*15.59*/("""].chiefComplaint" type="text" class="hidden" value=""""),_display_(/*15.112*/chiefComplaint),format.raw/*15.126*/(""""/>
                <input name="tabFieldItems["""),_display_(/*16.45*/onset/*16.50*/.getIndex),format.raw/*16.59*/("""].value" type="text" class="fInput" value=""""),_display_(/*16.103*/onset/*16.108*/.getValue),format.raw/*16.117*/("""" />
            """)))}),format.raw/*17.14*/("""


        """),format.raw/*20.9*/("""</div>

        <div class="staticField">
            <label for="radiation">Radiation</label>
            """),_display_(/*24.14*/defining(hpiTab.getTabFieldItemByName(chiefComplaint, "radiation"))/*24.81*/ { radiation =>_display_(Seq[Any](format.raw/*24.96*/("""
                """),format.raw/*25.17*/("""<input name="tabFieldItems["""),_display_(/*25.45*/radiation/*25.54*/.getIndex),format.raw/*25.63*/("""].name" type="text" class="hidden" value="radiation"/>
                <input name="tabFieldItems["""),_display_(/*26.45*/radiation/*26.54*/.getIndex),format.raw/*26.63*/("""].chiefComplaint" type="text" class="hidden" value=""""),_display_(/*26.116*/chiefComplaint),format.raw/*26.130*/(""""/>
                <input name="tabFieldItems["""),_display_(/*27.45*/radiation/*27.54*/.getIndex),format.raw/*27.63*/("""].value" type="text" class="fInput" value=""""),_display_(/*27.107*/radiation/*27.116*/.getValue),format.raw/*27.125*/("""" />
            """)))}),format.raw/*28.14*/("""
        """),format.raw/*29.9*/("""</div>

    </div>

    <div class="rightHPI">
        <div class="staticField">
            <label for="quality">Quality</label>
            """),_display_(/*36.14*/defining(hpiTab.getTabFieldItemByName(chiefComplaint, "quality"))/*36.79*/ { quality =>_display_(Seq[Any](format.raw/*36.92*/("""
                """),format.raw/*37.17*/("""<input name="tabFieldItems["""),_display_(/*37.45*/quality/*37.52*/.getIndex),format.raw/*37.61*/("""].name" type="text" class="hidden" value="quality"/>
                <input name="tabFieldItems["""),_display_(/*38.45*/quality/*38.52*/.getIndex),format.raw/*38.61*/("""].chiefComplaint" type="text" class="hidden" value=""""),_display_(/*38.114*/chiefComplaint),format.raw/*38.128*/(""""/>
                <input name="tabFieldItems["""),_display_(/*39.45*/quality/*39.52*/.getIndex),format.raw/*39.61*/("""].value" type="text" class="fInput" value=""""),_display_(/*39.105*/quality/*39.112*/.getValue),format.raw/*39.121*/("""" />
            """)))}),format.raw/*40.14*/("""
        """),format.raw/*41.9*/("""</div>

        """),format.raw/*43.23*/("""
        """),format.raw/*44.9*/("""<div class="staticField">
        """),_display_(/*45.10*/severityDropDown(chiefComplaint, hpiTab.getTabFieldItemByName(chiefComplaint, "severity"))),format.raw/*45.100*/("""
        """),format.raw/*46.9*/("""</div>


    </div>
    <div class="bottomHPI">

        <div class="staticField">
            <label for="provokes">Provokes</label>
            """),_display_(/*54.14*/defining(hpiTab.getTabFieldItemByName(chiefComplaint, "provokes"))/*54.80*/ { provokes =>_display_(Seq[Any](format.raw/*54.94*/("""
                """),format.raw/*55.17*/("""<input name="tabFieldItems["""),_display_(/*55.45*/provokes/*55.53*/.getIndex),format.raw/*55.62*/("""].name" type="text" class="hidden" value="provokes"/>
                <input name="tabFieldItems["""),_display_(/*56.45*/provokes/*56.53*/.getIndex),format.raw/*56.62*/("""].chiefComplaint" type="text" class="hidden" value=""""),_display_(/*56.115*/chiefComplaint),format.raw/*56.129*/(""""/>
                <input name="tabFieldItems["""),_display_(/*57.45*/provokes/*57.53*/.getIndex),format.raw/*57.62*/("""].value" type="text" class="fInput" value=""""),_display_(/*57.106*/provokes/*57.114*/.getValue),format.raw/*57.123*/("""" />
            """)))}),format.raw/*58.14*/("""
        """),format.raw/*59.9*/("""</div>

        <div class="staticField">
            <label for="palliates">Palliates</label>
            """),_display_(/*63.14*/defining(hpiTab.getTabFieldItemByName(chiefComplaint, "palliates"))/*63.81*/ { palliates =>_display_(Seq[Any](format.raw/*63.96*/("""
                """),format.raw/*64.17*/("""<input name="tabFieldItems["""),_display_(/*64.45*/palliates/*64.54*/.getIndex),format.raw/*64.63*/("""].name" type="text" class="hidden" value="palliates"/>
                <input name="tabFieldItems["""),_display_(/*65.45*/palliates/*65.54*/.getIndex),format.raw/*65.63*/("""].chiefComplaint" type="text" class="hidden" value=""""),_display_(/*65.116*/chiefComplaint),format.raw/*65.130*/(""""/>
                <input name="tabFieldItems["""),_display_(/*66.45*/palliates/*66.54*/.getIndex),format.raw/*66.63*/("""].value" type="text" class="fInput" value=""""),_display_(/*66.107*/palliates/*66.116*/.getValue),format.raw/*66.125*/("""" />
            """)))}),format.raw/*67.14*/("""
        """),format.raw/*68.9*/("""</div>

        <div class="staticField">
            <label for="timeOfDay">Time Of Day</label>
            """),_display_(/*72.14*/defining(hpiTab.getTabFieldItemByName(chiefComplaint, "timeOfDay"))/*72.81*/ { timeOfDay =>_display_(Seq[Any](format.raw/*72.96*/("""
                """),format.raw/*73.17*/("""<input name="tabFieldItems["""),_display_(/*73.45*/timeOfDay/*73.54*/.getIndex),format.raw/*73.63*/("""].name" type="text" class="hidden" value="timeOfDay"/>
                <input name="tabFieldItems["""),_display_(/*74.45*/timeOfDay/*74.54*/.getIndex),format.raw/*74.63*/("""].chiefComplaint" type="text" class="hidden" value=""""),_display_(/*74.116*/chiefComplaint),format.raw/*74.130*/(""""/>
                <input name="tabFieldItems["""),_display_(/*75.45*/timeOfDay/*75.54*/.getIndex),format.raw/*75.63*/("""].value" type="text" class="fInput" value=""""),_display_(/*75.107*/timeOfDay/*75.116*/.getValue),format.raw/*75.125*/("""" />
            """)))}),format.raw/*76.14*/("""
        """),format.raw/*77.9*/("""</div>

        <div class="staticField">
            <label for="narrative">Narrative</label>
            """),_display_(/*81.14*/defining(hpiTab.getTabFieldItemByName(chiefComplaint, "narrative"))/*81.81*/ { narrative =>_display_(Seq[Any](format.raw/*81.96*/("""
                """),format.raw/*82.17*/("""<input name="tabFieldItems["""),_display_(/*82.45*/narrative/*82.54*/.getIndex),format.raw/*82.63*/("""].name" type="text" class="hidden" value="narrative"/>
                <input name="tabFieldItems["""),_display_(/*83.45*/narrative/*83.54*/.getIndex),format.raw/*83.63*/("""].chiefComplaint" type="text" class="hidden" value=""""),_display_(/*83.116*/chiefComplaint),format.raw/*83.130*/(""""/>
                <input name="tabFieldItems["""),_display_(/*84.45*/narrative/*84.54*/.getIndex),format.raw/*84.63*/("""].value" type="text" class="fInput" value=""""),_display_(/*84.107*/narrative/*84.116*/.getValue),format.raw/*84.125*/("""" />
            """)))}),format.raw/*85.14*/("""
        """),format.raw/*86.9*/("""</div>

        <div class="staticField">
            """),format.raw/*89.39*/("""
            """),format.raw/*90.13*/("""<label for="physicalExamination">Physical Examination</label>
            """),_display_(/*91.14*/defining(hpiTab.getTabFieldItemByName(chiefComplaint, "physicalExamination"))/*91.91*/ { physicalExamination =>_display_(Seq[Any](format.raw/*91.116*/("""
                """),format.raw/*92.17*/("""<input name="tabFieldItems["""),_display_(/*92.45*/physicalExamination/*92.64*/.getIndex),format.raw/*92.73*/("""].name" type="text" class="hidden" value="physicalExamination"/>
                <input name="tabFieldItems["""),_display_(/*93.45*/physicalExamination/*93.64*/.getIndex),format.raw/*93.73*/("""].chiefComplaint" type="text" class="hidden" value=""""),_display_(/*93.126*/chiefComplaint),format.raw/*93.140*/(""""/>
                <textarea rows="3" name="tabFieldItems["""),_display_(/*94.57*/physicalExamination/*94.76*/.getIndex),format.raw/*94.85*/("""].value" class="form-control input-sm">"""),_display_(/*94.125*/physicalExamination/*94.144*/.getValue),format.raw/*94.153*/("""</textarea>
            """)))}),format.raw/*95.14*/("""
        """),format.raw/*96.9*/("""</div>
    </div>

""")))}/*99.3*/else/*99.8*/{_display_(Seq[Any](format.raw/*99.9*/("""
    """),format.raw/*100.5*/("""<div class="staticField">
        <label for="narrative">Narrative</label>
        """),_display_(/*102.10*/defining(hpiTab.getTabFieldItemByName(chiefComplaint, "narrative"))/*102.77*/ { narrative =>_display_(Seq[Any](format.raw/*102.92*/("""
            """),format.raw/*103.13*/("""<input name="tabFieldItems["""),_display_(/*103.41*/narrative/*103.50*/.getIndex),format.raw/*103.59*/("""].name" type="text" class="hidden" value="narrative"/>
            <input name="tabFieldItems["""),_display_(/*104.41*/narrative/*104.50*/.getIndex),format.raw/*104.59*/("""].chiefComplaint" type="text" class="hidden" value=""""),_display_(/*104.112*/chiefComplaint),format.raw/*104.126*/(""""/>
            <textarea name="tabFieldItems["""),_display_(/*105.44*/narrative/*105.53*/.getIndex),format.raw/*105.62*/("""].value" class="fNarrativeTextArea" >"""),_display_(/*105.100*/narrative/*105.109*/.getValue),format.raw/*105.118*/("""</textarea>
        """)))}),format.raw/*106.10*/("""
    """),format.raw/*107.5*/("""</div>
""")))}),format.raw/*108.2*/("""
"""),format.raw/*109.1*/("""</div>"""))
      }
    }
  }

  def render(hpiTab:femr.common.models.TabItem,isConsolidated:java.lang.Boolean,chiefComplaint:java.lang.String): play.twirl.api.HtmlFormat.Appendable = apply(hpiTab,isConsolidated,chiefComplaint)

  def f:((femr.common.models.TabItem,java.lang.Boolean,java.lang.String) => play.twirl.api.HtmlFormat.Appendable) = (hpiTab,isConsolidated,chiefComplaint) => apply(hpiTab,isConsolidated,chiefComplaint)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  DATE: Wed Jan 20 18:29:28 PST 2021
                  SOURCE: /Users/noel/Desktop/CPE/femr/femr/app/femr/ui/views/partials/medical/tabs/hpi_fields.scala.html
                  HASH: 53236f1f1483a43d50e2d47e7d1d9a9fee6dd2e6
                  MATRIX: 1038->1|1216->109|1305->107|1332->170|1360->218|1387->219|1444->251|1471->270|1510->272|1541->277|1683->392|1755->455|1804->466|1849->483|1904->511|1918->516|1948->525|2070->620|2084->625|2114->634|2195->687|2231->701|2306->749|2320->754|2350->763|2422->807|2437->812|2468->821|2517->839|2555->850|2690->958|2766->1025|2819->1040|2864->1057|2919->1085|2937->1094|2967->1103|3093->1202|3111->1211|3141->1220|3222->1273|3258->1287|3333->1335|3351->1344|3381->1353|3453->1397|3472->1406|3503->1415|3552->1433|3588->1442|3758->1585|3832->1650|3883->1663|3928->1680|3983->1708|3999->1715|4029->1724|4153->1821|4169->1828|4199->1837|4280->1890|4316->1904|4391->1952|4407->1959|4437->1968|4509->2012|4526->2019|4557->2028|4606->2046|4642->2055|4686->2085|4722->2094|4784->2129|4896->2219|4932->2228|5106->2375|5181->2441|5233->2455|5278->2472|5333->2500|5350->2508|5380->2517|5505->2615|5522->2623|5552->2632|5633->2685|5669->2699|5744->2747|5761->2755|5791->2764|5863->2808|5881->2816|5912->2825|5961->2843|5997->2852|6132->2960|6208->3027|6261->3042|6306->3059|6361->3087|6379->3096|6409->3105|6535->3204|6553->3213|6583->3222|6664->3275|6700->3289|6775->3337|6793->3346|6823->3355|6895->3399|6914->3408|6945->3417|6994->3435|7030->3444|7167->3554|7243->3621|7296->3636|7341->3653|7396->3681|7414->3690|7444->3699|7570->3798|7588->3807|7618->3816|7699->3869|7735->3883|7810->3931|7828->3940|7858->3949|7930->3993|7949->4002|7980->4011|8029->4029|8065->4038|8200->4146|8276->4213|8329->4228|8374->4245|8429->4273|8447->4282|8477->4291|8603->4390|8621->4399|8651->4408|8732->4461|8768->4475|8843->4523|8861->4532|8891->4541|8963->4585|8982->4594|9013->4603|9062->4621|9098->4630|9180->4710|9221->4723|9323->4798|9409->4875|9473->4900|9518->4917|9573->4945|9601->4964|9631->4973|9767->5082|9795->5101|9825->5110|9906->5163|9942->5177|10029->5237|10057->5256|10087->5265|10155->5305|10184->5324|10215->5333|10271->5358|10307->5367|10345->5388|10357->5393|10395->5394|10428->5399|10540->5483|10617->5550|10671->5565|10713->5578|10769->5606|10788->5615|10819->5624|10942->5719|10961->5728|10992->5737|11074->5790|11111->5804|11186->5851|11205->5860|11236->5869|11303->5907|11323->5916|11355->5925|11408->5946|11441->5951|11480->5959|11509->5960
                  LINES: 28->1|31->3|34->2|35->4|36->5|37->6|39->8|39->8|39->8|40->9|44->13|44->13|44->13|45->14|45->14|45->14|45->14|46->15|46->15|46->15|46->15|46->15|47->16|47->16|47->16|47->16|47->16|47->16|48->17|51->20|55->24|55->24|55->24|56->25|56->25|56->25|56->25|57->26|57->26|57->26|57->26|57->26|58->27|58->27|58->27|58->27|58->27|58->27|59->28|60->29|67->36|67->36|67->36|68->37|68->37|68->37|68->37|69->38|69->38|69->38|69->38|69->38|70->39|70->39|70->39|70->39|70->39|70->39|71->40|72->41|74->43|75->44|76->45|76->45|77->46|85->54|85->54|85->54|86->55|86->55|86->55|86->55|87->56|87->56|87->56|87->56|87->56|88->57|88->57|88->57|88->57|88->57|88->57|89->58|90->59|94->63|94->63|94->63|95->64|95->64|95->64|95->64|96->65|96->65|96->65|96->65|96->65|97->66|97->66|97->66|97->66|97->66|97->66|98->67|99->68|103->72|103->72|103->72|104->73|104->73|104->73|104->73|105->74|105->74|105->74|105->74|105->74|106->75|106->75|106->75|106->75|106->75|106->75|107->76|108->77|112->81|112->81|112->81|113->82|113->82|113->82|113->82|114->83|114->83|114->83|114->83|114->83|115->84|115->84|115->84|115->84|115->84|115->84|116->85|117->86|120->89|121->90|122->91|122->91|122->91|123->92|123->92|123->92|123->92|124->93|124->93|124->93|124->93|124->93|125->94|125->94|125->94|125->94|125->94|125->94|126->95|127->96|130->99|130->99|130->99|131->100|133->102|133->102|133->102|134->103|134->103|134->103|134->103|135->104|135->104|135->104|135->104|135->104|136->105|136->105|136->105|136->105|136->105|136->105|137->106|138->107|139->108|140->109
                  -- GENERATED --
              */
          