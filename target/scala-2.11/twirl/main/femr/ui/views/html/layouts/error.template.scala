
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

object error extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template4[Html,Html,Html,AssetsFinder,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(styles: Html = Html(""), scripts: Html = Html(""), errorContent: Html = Html(""), assets: AssetsFinder):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*1.106*/("""

"""),format.raw/*3.1*/("""<!DOCTYPE html>
<html class="no-js" xmlns="http://www.w3.org/1999/html">
    <head>
        <title>fEMR - Error</title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <link rel="shortcut icon" type="image/png" href=""""),_display_(/*10.59*/assets/*10.65*/.path("img/favicon.png")),format.raw/*10.89*/("""">
        <link rel="stylesheet" href=""""),_display_(/*11.39*/assets/*11.45*/.path("css/femr.css")),format.raw/*11.66*/(""""/>
        """),_display_(/*12.10*/styles),format.raw/*12.16*/("""
        """),format.raw/*13.9*/("""<script type="text/javascript" src=""""),_display_(/*13.46*/assets/*13.52*/.path("js/libraries/modernizr-2.6.2.min.js")),format.raw/*13.96*/(""""></script>
    </head>
    <body>

        """),_display_(/*17.10*/errorContent),format.raw/*17.22*/("""


    """),format.raw/*20.5*/("""<script type="text/javascript" src=""""),_display_(/*20.42*/assets/*20.48*/.path("js/libraries/jquery-2.2.4.min.js")),format.raw/*20.89*/(""""></script>
        """),_display_(/*21.10*/scripts),format.raw/*21.17*/("""
    """),format.raw/*22.5*/("""</body>
</html>"""))
      }
    }
  }

  def render(styles:Html,scripts:Html,errorContent:Html,assets:AssetsFinder): play.twirl.api.HtmlFormat.Appendable = apply(styles,scripts,errorContent,assets)

  def f:((Html,Html,Html,AssetsFinder) => play.twirl.api.HtmlFormat.Appendable) = (styles,scripts,errorContent,assets) => apply(styles,scripts,errorContent,assets)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  DATE: Tue Oct 27 21:59:18 PDT 2020
                  SOURCE: C:/Users/Noel/Documents/csc402/lemur-femr/femr/app/femr/ui/views/layouts/error.scala.html
                  HASH: 92f54be3309e0c00de138900efcd7b2ca970cf0f
                  MATRIX: 985->1|1185->105|1215->109|1582->449|1597->455|1642->479|1711->521|1726->527|1768->548|1809->562|1836->568|1873->578|1937->615|1952->621|2017->665|2093->714|2126->726|2163->736|2227->773|2242->779|2304->820|2353->842|2381->849|2414->855
                  LINES: 28->1|33->1|35->3|42->10|42->10|42->10|43->11|43->11|43->11|44->12|44->12|45->13|45->13|45->13|45->13|49->17|49->17|52->20|52->20|52->20|52->20|53->21|53->21|54->22
                  -- GENERATED --
              */
          