
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


Seq[Any](format.raw/*7.1*/("""
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
                  DATE: Wed Jan 20 18:29:26 PST 2021
                  SOURCE: /Users/noel/Desktop/CPE/femr/femr/app/femr/ui/views/layouts/main.scala.html
                  HASH: 1764156731f9ce6d4200992a3a803acce91eb935
                  MATRIX: 1035->1|1405->301|1454->344|1524->299|1552->386|1580->387|1707->487|1733->492|1762->493|2032->736|2047->742|2092->766|2160->807|2175->813|2244->861|2312->902|2327->908|2388->948|2456->989|2471->995|2513->1016|2553->1029|2580->1035|2616->1044|2680->1081|2695->1087|2760->1131|2831->1175|2879->1202|2917->1213|2957->1232|2994->1242|3058->1279|3085->1285|3127->1300|3155->1307|3197->1322|3226->1330|3262->1339|3306->1356|3349->1378|3386->1388|3450->1425|3465->1431|3527->1472|3613->1531|3628->1537|3696->1583|3780->1640|3795->1646|3854->1684|3940->1743|3955->1749|4002->1775|4050->1796|4078->1803|4110->1808
                  LINES: 28->1|36->8|37->9|40->7|41->10|42->11|45->14|45->14|45->14|49->18|49->18|49->18|50->19|50->19|50->19|51->20|51->20|51->20|52->21|52->21|52->21|53->22|53->22|54->23|54->23|54->23|54->23|57->26|57->26|59->28|59->28|61->30|62->31|62->31|64->33|64->33|66->35|66->35|67->36|69->38|69->38|71->40|71->40|71->40|71->40|72->41|72->41|72->41|73->42|73->42|73->42|74->43|74->43|74->43|75->44|75->44|76->45
                  -- GENERATED --
              */
          