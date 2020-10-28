
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


Seq[Any](format.raw/*1.193*/("""

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
                  DATE: Tue Oct 27 21:59:18 PDT 2020
                  SOURCE: C:/Users/Noel/Documents/csc402/lemur-femr/femr/app/femr/ui/views/partials/medical/dynamicField.scala.html
                  HASH: 79b8f4dd262a6e759000746520b72d3223cb374e
                  MATRIX: 1092->1|1379->192|1409->197|1449->229|1488->231|1520->237|1559->250|1583->254|1612->257|1636->261|1704->303|1729->308|1798->351|1822->355|1857->365|1897->397|1936->399|1973->410|2023->452|2061->453|2102->467|2252->590|2278->595|2357->646|2381->660|2422->662|2463->671|2493->673|2522->692|2563->694|2603->702|2802->874|2828->879|2915->938|2939->952|2980->954|3021->963|3051->965|3088->992|3129->994|3169->1002|3376->1182|3402->1187|3507->1264|3531->1278|3572->1280|3613->1289|3643->1291|3698->1336|3739->1338|3779->1346|4004->1544|4030->1549|4121->1612|4145->1626|4186->1628|4227->1637|4257->1639|4298->1670|4339->1672|4379->1680|4590->1864|4616->1869|4706->1931|4730->1945|4771->1947|4812->1956|4842->1958|4882->1988|4923->1990|4963->1998|5173->2181|5199->2186|5279->2238|5303->2252|5344->2254|5385->2263|5415->2265|5445->2285|5486->2287|5526->2295|5631->2382|5644->2387|5683->2388|5725->2402|5875->2525|5901->2530|5979->2580|6003->2594|6044->2596|6085->2605|6115->2607|6143->2625|6184->2627|6224->2635|6398->2782|6424->2787|6501->2836|6525->2850|6566->2852|6607->2861|6637->2863|6664->2880|6705->2882|6745->2890|6918->3036|6944->3041|7033->3102|7057->3116|7098->3118|7139->3127|7169->3129|7208->3158|7249->3160|7289->3168|7415->3263|7440->3271|7453->3276|7492->3277|7529->3287|7584->3315|7610->3320|7672->3355|7704->3366|7769->3403|7796->3408|7826->3410|7850->3424|7890->3425|7931->3434|7970->3443|8003->3449|8029->3458|8041->3463|8079->3464|8112->3471|8152->3502|8192->3504|8229->3514|8269->3527|8294->3531|8324->3534|8349->3538|8449->3611|8475->3616|8518->3632|8543->3636|8614->3680|8640->3685|8720->3738|8753->3749|8783->3751|8807->3765|8847->3766|8888->3775|8918->3776|8948->3778|8975->3783|9040->3818|9073->3821
                  LINES: 28->1|33->1|35->3|35->3|35->3|36->4|36->4|36->4|36->4|36->4|37->5|37->5|37->5|37->5|38->6|38->6|38->6|39->7|39->7|39->7|40->8|42->10|42->10|42->10|42->10|42->10|42->10|42->10|42->10|42->10|42->10|47->15|47->15|47->15|47->15|47->15|47->15|47->15|47->15|47->15|47->15|52->20|52->20|52->20|52->20|52->20|52->20|52->20|52->20|52->20|52->20|57->25|57->25|57->25|57->25|57->25|57->25|57->25|57->25|57->25|57->25|62->30|62->30|62->30|62->30|62->30|62->30|62->30|62->30|62->30|62->30|67->35|67->35|67->35|67->35|67->35|67->35|67->35|67->35|67->35|67->35|71->39|71->39|71->39|72->40|74->42|74->42|74->42|74->42|74->42|74->42|74->42|74->42|74->42|74->42|78->46|78->46|78->46|78->46|78->46|78->46|78->46|78->46|78->46|78->46|82->50|82->50|82->50|82->50|82->50|82->50|82->50|82->50|82->50|82->50|86->54|87->55|87->55|87->55|88->56|88->56|88->56|88->56|88->56|88->56|88->56|88->56|88->56|88->56|88->56|89->57|90->58|91->59|91->59|91->59|92->60|92->60|92->60|93->61|93->61|93->61|93->61|93->61|94->62|94->62|94->62|94->62|95->63|95->63|95->63|95->63|95->63|95->63|95->63|95->63|95->63|95->63|95->63|98->66|99->67
                  -- GENERATED --
              */
          