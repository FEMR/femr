
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


Seq[Any](format.raw/*1.107*/("""

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
                  DATE: Tue Oct 27 21:59:18 PDT 2020
                  SOURCE: C:/Users/Noel/Documents/csc402/lemur-femr/femr/app/femr/ui/views/partials/medical/tabs/hpi_fields.scala.html
                  HASH: a8813adbed44cf98e8cc0e8d9e09011cbf38ce3f
                  MATRIX: 1038->1|1216->111|1307->106|1337->173|1366->222|1394->224|1453->258|1480->277|1519->279|1551->285|1697->404|1769->467|1818->478|1864->496|1919->524|1933->529|1963->538|2086->634|2100->639|2130->648|2211->701|2247->715|2323->764|2337->769|2367->778|2439->822|2454->827|2485->836|2535->855|2576->869|2715->981|2791->1048|2844->1063|2890->1081|2945->1109|2963->1118|2993->1127|3120->1227|3138->1236|3168->1245|3249->1298|3285->1312|3361->1361|3379->1370|3409->1379|3481->1423|3500->1432|3531->1441|3581->1460|3618->1470|3795->1620|3869->1685|3920->1698|3966->1716|4021->1744|4037->1751|4067->1760|4192->1858|4208->1865|4238->1874|4319->1927|4355->1941|4431->1990|4447->1997|4477->2006|4549->2050|4566->2057|4597->2066|4647->2085|4684->2095|4730->2127|4767->2137|4830->2173|4942->2263|4979->2273|5161->2428|5236->2494|5288->2508|5334->2526|5389->2554|5406->2562|5436->2571|5562->2670|5579->2678|5609->2687|5690->2740|5726->2754|5802->2803|5819->2811|5849->2820|5921->2864|5939->2872|5970->2881|6020->2900|6057->2910|6196->3022|6272->3089|6325->3104|6371->3122|6426->3150|6444->3159|6474->3168|6601->3268|6619->3277|6649->3286|6730->3339|6766->3353|6842->3402|6860->3411|6890->3420|6962->3464|6981->3473|7012->3482|7062->3501|7099->3511|7240->3625|7316->3692|7369->3707|7415->3725|7470->3753|7488->3762|7518->3771|7645->3871|7663->3880|7693->3889|7774->3942|7810->3956|7886->4005|7904->4014|7934->4023|8006->4067|8025->4076|8056->4085|8106->4104|8143->4114|8282->4226|8358->4293|8411->4308|8457->4326|8512->4354|8530->4363|8560->4372|8687->4472|8705->4481|8735->4490|8816->4543|8852->4557|8928->4606|8946->4615|8976->4624|9048->4668|9067->4677|9098->4686|9148->4705|9185->4715|9270->4798|9312->4812|9415->4888|9501->4965|9565->4990|9611->5008|9666->5036|9694->5055|9724->5064|9861->5174|9889->5193|9919->5202|10000->5255|10036->5269|10124->5330|10152->5349|10182->5358|10250->5398|10279->5417|10310->5426|10367->5452|10404->5462|10445->5486|10457->5491|10495->5492|10529->5498|10643->5584|10720->5651|10774->5666|10817->5680|10873->5708|10892->5717|10923->5726|11047->5822|11066->5831|11097->5840|11179->5893|11216->5907|11292->5955|11311->5964|11342->5973|11409->6011|11429->6020|11461->6029|11515->6051|11549->6057|11589->6066|11619->6068
                  LINES: 28->1|31->3|34->1|36->4|37->5|38->6|40->8|40->8|40->8|41->9|45->13|45->13|45->13|46->14|46->14|46->14|46->14|47->15|47->15|47->15|47->15|47->15|48->16|48->16|48->16|48->16|48->16|48->16|49->17|52->20|56->24|56->24|56->24|57->25|57->25|57->25|57->25|58->26|58->26|58->26|58->26|58->26|59->27|59->27|59->27|59->27|59->27|59->27|60->28|61->29|68->36|68->36|68->36|69->37|69->37|69->37|69->37|70->38|70->38|70->38|70->38|70->38|71->39|71->39|71->39|71->39|71->39|71->39|72->40|73->41|75->43|76->44|77->45|77->45|78->46|86->54|86->54|86->54|87->55|87->55|87->55|87->55|88->56|88->56|88->56|88->56|88->56|89->57|89->57|89->57|89->57|89->57|89->57|90->58|91->59|95->63|95->63|95->63|96->64|96->64|96->64|96->64|97->65|97->65|97->65|97->65|97->65|98->66|98->66|98->66|98->66|98->66|98->66|99->67|100->68|104->72|104->72|104->72|105->73|105->73|105->73|105->73|106->74|106->74|106->74|106->74|106->74|107->75|107->75|107->75|107->75|107->75|107->75|108->76|109->77|113->81|113->81|113->81|114->82|114->82|114->82|114->82|115->83|115->83|115->83|115->83|115->83|116->84|116->84|116->84|116->84|116->84|116->84|117->85|118->86|121->89|122->90|123->91|123->91|123->91|124->92|124->92|124->92|124->92|125->93|125->93|125->93|125->93|125->93|126->94|126->94|126->94|126->94|126->94|126->94|127->95|128->96|131->99|131->99|131->99|132->100|134->102|134->102|134->102|135->103|135->103|135->103|135->103|136->104|136->104|136->104|136->104|136->104|137->105|137->105|137->105|137->105|137->105|137->105|138->106|139->107|140->108|141->109
                  -- GENERATED --
              */
          