
package femr.ui.views.html.layouts

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

object main extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template9[String,femr.common.dtos.CurrentUser,Html,Html,Html,Html,Html,AssetsFinder,Html,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(title: String, currentUser: femr.common.dtos.CurrentUser = null,
        styles: Html = Html(""), scripts: Html = Html(""),
        outsideContainerTop: Html = Html(""),
        outsideContainerBottom: Html = Html(""),
        search: Html = Html(""),
        assets: AssetsFinder)(content: Html):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {
/*8.2*/import femr.ui.views.html.partials.footer
/*9.2*/import femr.ui.views.html.partials.header


Seq[Any](format.raw/*6.45*/("""

"""),format.raw/*10.1*/("""
"""),format.raw/*11.1*/("""<!DOCTYPE html>
<html class="no-js" xmlns="http://www.w3.org/1999/html">
    <head>
        <title>"""),_display_(/*14.17*/title),format.raw/*14.22*/(""" """),format.raw/*14.23*/("""| fEMR - The free EMR</title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <link rel="shortcut icon" type="image/png" href=""""),_display_(/*18.59*/assets/*18.65*/.path("img/favicon.png")),format.raw/*18.89*/("""">
        <link rel="stylesheet" href=""""),_display_(/*19.39*/assets/*19.45*/.path("css/libraries/jquery.dataTables.min.css")),format.raw/*19.93*/("""">
        <link rel="stylesheet" href=""""),_display_(/*20.39*/assets/*20.45*/.path("css/libraries/bootstrap.min.css")),format.raw/*20.85*/("""">
        <link rel="stylesheet" href=""""),_display_(/*21.39*/assets/*21.45*/.path("css/femr.css")),format.raw/*21.66*/(""""/>
        """),_display_(/*22.10*/styles),format.raw/*22.16*/("""
        """),format.raw/*23.9*/("""<script type="text/javascript" src=""""),_display_(/*23.46*/assets/*23.52*/.path("js/libraries/modernizr-2.6.2.min.js")),format.raw/*23.96*/(""""></script>
    </head>
    <body>
        """),_display_(/*26.10*/header(currentUser, assets)),format.raw/*26.37*/("""

        """),_display_(/*28.10*/outsideContainerTop),format.raw/*28.29*/("""

        """),format.raw/*30.9*/("""<div class="container">
            """),_display_(/*31.14*/search),format.raw/*31.20*/("""

            """),_display_(/*33.14*/content),format.raw/*33.21*/("""

            """),_display_(/*35.14*/footer()),format.raw/*35.22*/("""
        """),format.raw/*36.9*/("""</div>

        """),_display_(/*38.10*/outsideContainerBottom),format.raw/*38.32*/("""

        """),format.raw/*40.9*/("""<script type="text/javascript" src=""""),_display_(/*40.46*/assets/*40.52*/.path("js/libraries/jquery-2.2.4.min.js")),format.raw/*40.93*/(""""></script>
        <script type = "text/javascript" src=""""),_display_(/*41.48*/assets/*41.54*/.path("js/libraries/jquery.dataTables.min.js")),format.raw/*41.100*/(""""></script>
        <script type="text/javascript" src=""""),_display_(/*42.46*/assets/*42.52*/.path("js/libraries/bootstrap.min.js")),format.raw/*42.90*/(""""></script>
        <script type = "text/javascript" src=""""),_display_(/*43.48*/assets/*43.54*/.path("js/shared/femr.js")),format.raw/*43.80*/(""""></script>
        """),_display_(/*44.10*/scripts),format.raw/*44.17*/("""
    """),format.raw/*45.5*/("""</body>
</html>
"""))
      }
    }
  }

  def render(title:String,currentUser:femr.common.dtos.CurrentUser,styles:Html,scripts:Html,outsideContainerTop:Html,outsideContainerBottom:Html,search:Html,assets:AssetsFinder,content:Html): play.twirl.api.HtmlFormat.Appendable = apply(title,currentUser,styles,scripts,outsideContainerTop,outsideContainerBottom,search,assets)(content)

  def f:((String,femr.common.dtos.CurrentUser,Html,Html,Html,Html,Html,AssetsFinder) => (Html) => play.twirl.api.HtmlFormat.Appendable) = (title,currentUser,styles,scripts,outsideContainerTop,outsideContainerBottom,search,assets) => (content) => apply(title,currentUser,styles,scripts,outsideContainerTop,outsideContainerBottom,search,assets)(content)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  DATE: Tue Oct 27 21:59:18 PDT 2020
                  SOURCE: C:/Users/Noel/Documents/csc402/lemur-femr/femr/app/femr/ui/views/layouts/main.scala.html
                  HASH: 83f68f3000e8530c3be65ee591652ed716e2956a
                  MATRIX: 1035->1|1410->308|1459->352|1530->303|1561->395|1590->397|1720->500|1746->505|1775->506|2049->753|2064->759|2109->783|2178->825|2193->831|2262->879|2331->921|2346->927|2407->967|2476->1009|2491->1015|2533->1036|2574->1050|2601->1056|2638->1066|2702->1103|2717->1109|2782->1153|2856->1200|2904->1227|2944->1240|2984->1259|3023->1271|3088->1309|3115->1315|3159->1332|3187->1339|3231->1356|3260->1364|3297->1374|3343->1393|3386->1415|3425->1427|3489->1464|3504->1470|3566->1511|3653->1571|3668->1577|3736->1623|3821->1681|3836->1687|3895->1725|3982->1785|3997->1791|4044->1817|4093->1839|4121->1846|4154->1852
                  LINES: 28->1|36->8|37->9|40->6|42->10|43->11|46->14|46->14|46->14|50->18|50->18|50->18|51->19|51->19|51->19|52->20|52->20|52->20|53->21|53->21|53->21|54->22|54->22|55->23|55->23|55->23|55->23|58->26|58->26|60->28|60->28|62->30|63->31|63->31|65->33|65->33|67->35|67->35|68->36|70->38|70->38|72->40|72->40|72->40|72->40|73->41|73->41|73->41|74->42|74->42|74->42|75->43|75->43|75->43|76->44|76->44|77->45
                  -- GENERATED --
              */
          