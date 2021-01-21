
package femr.ui.views.html.partials.medical

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

object dynamicField extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template8[java.lang.String,java.lang.String,java.lang.String,java.lang.Integer,java.lang.String,java.lang.String,Boolean,Integer,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(value: java.lang.String, name: java.lang.String, size: java.lang.String, order: java.lang.Integer, tipe: java.lang.String, placeholder: java.lang.String, isReadOnly: Boolean, index: Integer):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*2.1*/("""
"""),_display_(/*3.2*/if(size.toLowerCase == "medium")/*3.34*/ {_display_(Seq[Any](format.raw/*3.36*/("""
    """),format.raw/*4.5*/("""<label for=""""),_display_(/*4.18*/name),format.raw/*4.22*/("""">"""),_display_(/*4.25*/name),format.raw/*4.29*/("""</label>
    <input name="tabFieldItems["""),_display_(/*5.33*/index),format.raw/*5.38*/("""].name" class="hidden" type="text" value=""""),_display_(/*5.81*/name),format.raw/*5.85*/(""""/>
    """),_display_(/*6.6*/if(tipe.toLowerCase == "yes/no")/*6.38*/ {_display_(Seq[Any](format.raw/*6.40*/("""
        """),_display_(/*7.10*/if(name == "If no vaccinations, why not?")/*7.52*/{_display_(Seq[Any](format.raw/*7.53*/("""
            """),format.raw/*8.13*/("""<div class="radio-responses">
                <label class="radio-wrap">
                    <input name="tabFieldItems["""),_display_(/*10.49*/index),format.raw/*10.54*/("""].value" type="radio" value="Cost" class="rInput" """),_display_(/*10.105*/if(isReadOnly)/*10.119*/ {_display_(Seq[Any](format.raw/*10.121*/("""readonly""")))}),format.raw/*10.130*/(""" """),_display_(/*10.132*/if(value == "cost")/*10.151*/ {_display_(Seq[Any](format.raw/*10.153*/("""checked""")))}),format.raw/*10.161*/("""/>
                    Cost
                </label>
                <br />
                <label class="radio-wrap">
                    <input name="tabFieldItems["""),_display_(/*15.49*/index),format.raw/*15.54*/("""].value" type="radio" value="No transport" class="rInput" """),_display_(/*15.113*/if(isReadOnly)/*15.127*/ {_display_(Seq[Any](format.raw/*15.129*/("""readonly""")))}),format.raw/*15.138*/(""" """),_display_(/*15.140*/if(value == "No transport")/*15.167*/ {_display_(Seq[Any](format.raw/*15.169*/("""checked""")))}),format.raw/*15.177*/("""/>
                    No transport
                </label>
                <br />
                <label class="radio-wrap">
                    <input name="tabFieldItems["""),_display_(/*20.49*/index),format.raw/*20.54*/("""].value" type="radio" value="Distrust health care providers" class="rInput" """),_display_(/*20.131*/if(isReadOnly)/*20.145*/ {_display_(Seq[Any](format.raw/*20.147*/("""readonly""")))}),format.raw/*20.156*/(""" """),_display_(/*20.158*/if(value == "Distrust health care providers")/*20.203*/ {_display_(Seq[Any](format.raw/*20.205*/("""checked""")))}),format.raw/*20.213*/("""/>
                    Distrust health care providers
                </label>
                <br />
                <label class="radio-wrap">
                    <input name="tabFieldItems["""),_display_(/*25.49*/index),format.raw/*25.54*/("""].value" type="radio" value="Did not know how" class="rInput" """),_display_(/*25.117*/if(isReadOnly)/*25.131*/ {_display_(Seq[Any](format.raw/*25.133*/("""readonly""")))}),format.raw/*25.142*/(""" """),_display_(/*25.144*/if(value == "Did not know how")/*25.175*/ {_display_(Seq[Any](format.raw/*25.177*/("""checked""")))}),format.raw/*25.185*/("""/>
                    Did not know how
                </label>
                <br />
                <label class="radio-wrap">
                    <input name="tabFieldItems["""),_display_(/*30.49*/index),format.raw/*30.54*/("""].value" type="radio" value="Did not need it" class="rInput" """),_display_(/*30.116*/if(isReadOnly)/*30.130*/ {_display_(Seq[Any](format.raw/*30.132*/("""readonly""")))}),format.raw/*30.141*/(""" """),_display_(/*30.143*/if(value == "Did not need it")/*30.173*/ {_display_(Seq[Any](format.raw/*30.175*/("""checked""")))}),format.raw/*30.183*/("""/>
                    Did not need it
                </label>
                <br />
                <label class="radio-wrap">
                    <input name="tabFieldItems["""),_display_(/*35.49*/index),format.raw/*35.54*/("""].value" type="radio" value="Other" class="rInput" """),_display_(/*35.106*/if(isReadOnly)/*35.120*/ {_display_(Seq[Any](format.raw/*35.122*/("""readonly""")))}),format.raw/*35.131*/(""" """),_display_(/*35.133*/if(value == "Other")/*35.153*/ {_display_(Seq[Any](format.raw/*35.155*/("""checked""")))}),format.raw/*35.163*/("""/>
                    Other
                </label>
            </div>
        """)))}/*39.11*/else/*39.16*/{_display_(Seq[Any](format.raw/*39.17*/("""
            """),format.raw/*40.13*/("""<div class="radio-responses">
                <label class="radio-wrap">
                    <input name="tabFieldItems["""),_display_(/*42.49*/index),format.raw/*42.54*/("""].value" type="radio" value="yes" class="rInput" """),_display_(/*42.104*/if(isReadOnly)/*42.118*/ {_display_(Seq[Any](format.raw/*42.120*/("""readonly""")))}),format.raw/*42.129*/(""" """),_display_(/*42.131*/if(value == "yes")/*42.149*/ {_display_(Seq[Any](format.raw/*42.151*/("""checked""")))}),format.raw/*42.159*/("""/>
                    Yes
                </label>
                <label class="radio-wrap">
                    <input name="tabFieldItems["""),_display_(/*46.49*/index),format.raw/*46.54*/("""].value" type="radio" value="no" class="rInput" """),_display_(/*46.103*/if(isReadOnly)/*46.117*/ {_display_(Seq[Any](format.raw/*46.119*/("""readonly""")))}),format.raw/*46.128*/(""" """),_display_(/*46.130*/if(value == "no")/*46.147*/ {_display_(Seq[Any](format.raw/*46.149*/("""checked""")))}),format.raw/*46.157*/("""/>
                    No
                </label>
                <label class="radio-wrap">
                    <input name="tabFieldItems["""),_display_(/*50.49*/index),format.raw/*50.54*/("""].value" type="radio" value="not applicable" class="rInput" """),_display_(/*50.115*/if(isReadOnly)/*50.129*/ {_display_(Seq[Any](format.raw/*50.131*/("""readonly""")))}),format.raw/*50.140*/(""" """),_display_(/*50.142*/if(value == "not applicable")/*50.171*/ {_display_(Seq[Any](format.raw/*50.173*/("""checked""")))}),format.raw/*50.181*/("""/>
                    Not Applicable
                </label>
            </div>
        """)))}),format.raw/*54.10*/("""
    """)))}/*55.7*/else/*55.12*/{_display_(Seq[Any](format.raw/*55.13*/("""
        """),format.raw/*56.9*/("""<input name="tabFieldItems["""),_display_(/*56.37*/index),format.raw/*56.42*/("""].value" type="text" placeholder=""""),_display_(/*56.77*/placeholder),format.raw/*56.88*/("""" class="fInput customField" value=""""),_display_(/*56.125*/value),format.raw/*56.130*/("""""""),_display_(/*56.132*/if(isReadOnly)/*56.146*/{_display_(Seq[Any](format.raw/*56.147*/("""readonly""")))}),format.raw/*56.156*/("""/>
    """)))}),format.raw/*57.6*/("""
    """),format.raw/*58.5*/("""<br/>
""")))}/*59.3*/else/*59.8*/{_display_(Seq[Any](format.raw/*59.9*/("""
    """),_display_(/*60.6*/if(size.toLowerCase == "large")/*60.37*/ {_display_(Seq[Any](format.raw/*60.39*/("""
        """),format.raw/*61.9*/("""<label for=""""),_display_(/*61.22*/name),format.raw/*61.26*/("""">"""),_display_(/*61.29*/name),format.raw/*61.33*/("""</label>
        <input type="text" class="hidden" name="tabFieldItems["""),_display_(/*62.64*/index),format.raw/*62.69*/("""].name" value=""""),_display_(/*62.85*/name),format.raw/*62.89*/(""""/>
        <textarea name="tabFieldItems["""),_display_(/*63.40*/index),format.raw/*63.45*/("""].value" class="fTextArea customField" placeholder=""""),_display_(/*63.98*/placeholder),format.raw/*63.109*/("""""""),_display_(/*63.111*/if(isReadOnly)/*63.125*/{_display_(Seq[Any](format.raw/*63.126*/("""readonly""")))}),format.raw/*63.135*/(""" """),format.raw/*63.136*/(""">"""),_display_(/*63.138*/value),format.raw/*63.143*/("""</textarea>

        <br/>
    """)))}),format.raw/*66.6*/("""
""")))}),format.raw/*67.2*/("""
"""))
      }
    }
  }

  def render(value:java.lang.String,name:java.lang.String,size:java.lang.String,order:java.lang.Integer,tipe:java.lang.String,placeholder:java.lang.String,isReadOnly:Boolean,index:Integer): play.twirl.api.HtmlFormat.Appendable = apply(value,name,size,order,tipe,placeholder,isReadOnly,index)

  def f:((java.lang.String,java.lang.String,java.lang.String,java.lang.Integer,java.lang.String,java.lang.String,Boolean,Integer) => play.twirl.api.HtmlFormat.Appendable) = (value,name,size,order,tipe,placeholder,isReadOnly,index) => apply(value,name,size,order,tipe,placeholder,isReadOnly,index)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  DATE: Wed Jan 20 18:29:28 PST 2021
                  SOURCE: /Users/noel/Desktop/CPE/femr/femr/app/femr/ui/views/partials/medical/dynamicField.scala.html
                  HASH: 2eb932981fb443f6f56d8a9a7d815a9d14768ee9
                  MATRIX: 1092->1|1377->193|1404->195|1444->227|1483->229|1514->234|1553->247|1577->251|1606->254|1630->258|1697->299|1722->304|1791->347|1815->351|1849->360|1889->392|1928->394|1964->404|2014->446|2052->447|2092->460|2240->581|2266->586|2345->637|2369->651|2410->653|2451->662|2481->664|2510->683|2551->685|2591->693|2785->860|2811->865|2898->924|2922->938|2963->940|3004->949|3034->951|3071->978|3112->980|3152->988|3354->1163|3380->1168|3485->1245|3509->1259|3550->1261|3591->1270|3621->1272|3676->1317|3717->1319|3757->1327|3977->1520|4003->1525|4094->1588|4118->1602|4159->1604|4200->1613|4230->1615|4271->1646|4312->1648|4352->1656|4558->1835|4584->1840|4674->1902|4698->1916|4739->1918|4780->1927|4810->1929|4850->1959|4891->1961|4931->1969|5136->2147|5162->2152|5242->2204|5266->2218|5307->2220|5348->2229|5378->2231|5408->2251|5449->2253|5489->2261|5590->2344|5603->2349|5642->2350|5683->2363|5831->2484|5857->2489|5935->2539|5959->2553|6000->2555|6041->2564|6071->2566|6099->2584|6140->2586|6180->2594|6350->2737|6376->2742|6453->2791|6477->2805|6518->2807|6559->2816|6589->2818|6616->2835|6657->2837|6697->2845|6866->2987|6892->2992|6981->3053|7005->3067|7046->3069|7087->3078|7117->3080|7156->3109|7197->3111|7237->3119|7359->3210|7383->3217|7396->3222|7435->3223|7471->3232|7526->3260|7552->3265|7614->3300|7646->3311|7711->3348|7738->3353|7768->3355|7792->3369|7832->3370|7873->3379|7911->3387|7943->3392|7968->3400|7980->3405|8018->3406|8050->3412|8090->3443|8130->3445|8166->3454|8206->3467|8231->3471|8261->3474|8286->3478|8385->3550|8411->3555|8454->3571|8479->3575|8549->3618|8575->3623|8655->3676|8688->3687|8718->3689|8742->3703|8782->3704|8823->3713|8853->3714|8883->3716|8910->3721|8972->3753|9004->3755
                  LINES: 28->1|33->2|34->3|34->3|34->3|35->4|35->4|35->4|35->4|35->4|36->5|36->5|36->5|36->5|37->6|37->6|37->6|38->7|38->7|38->7|39->8|41->10|41->10|41->10|41->10|41->10|41->10|41->10|41->10|41->10|41->10|46->15|46->15|46->15|46->15|46->15|46->15|46->15|46->15|46->15|46->15|51->20|51->20|51->20|51->20|51->20|51->20|51->20|51->20|51->20|51->20|56->25|56->25|56->25|56->25|56->25|56->25|56->25|56->25|56->25|56->25|61->30|61->30|61->30|61->30|61->30|61->30|61->30|61->30|61->30|61->30|66->35|66->35|66->35|66->35|66->35|66->35|66->35|66->35|66->35|66->35|70->39|70->39|70->39|71->40|73->42|73->42|73->42|73->42|73->42|73->42|73->42|73->42|73->42|73->42|77->46|77->46|77->46|77->46|77->46|77->46|77->46|77->46|77->46|77->46|81->50|81->50|81->50|81->50|81->50|81->50|81->50|81->50|81->50|81->50|85->54|86->55|86->55|86->55|87->56|87->56|87->56|87->56|87->56|87->56|87->56|87->56|87->56|87->56|87->56|88->57|89->58|90->59|90->59|90->59|91->60|91->60|91->60|92->61|92->61|92->61|92->61|92->61|93->62|93->62|93->62|93->62|94->63|94->63|94->63|94->63|94->63|94->63|94->63|94->63|94->63|94->63|94->63|97->66|98->67
                  -- GENERATED --
              */
          