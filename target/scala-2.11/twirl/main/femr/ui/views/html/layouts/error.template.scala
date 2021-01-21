
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


Seq[Any](format.raw/*2.1*/("""
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
                  DATE: Wed Jan 20 18:29:26 PST 2021
                  SOURCE: /Users/noel/Desktop/CPE/femr/femr/app/femr/ui/views/layouts/error.scala.html
                  HASH: 6186f1af1bbd3f8a79be4f81a39234b69e197375
                  MATRIX: 985->1|1183->106|1210->107|1570->440|1585->446|1630->470|1698->511|1713->517|1755->538|1795->551|1822->557|1858->566|1922->603|1937->609|2002->653|2074->698|2107->710|2141->717|2205->754|2220->760|2282->801|2330->822|2358->829|2390->834
                  LINES: 28->1|33->2|34->3|41->10|41->10|41->10|42->11|42->11|42->11|43->12|43->12|44->13|44->13|44->13|44->13|48->17|48->17|51->20|51->20|51->20|51->20|52->21|52->21|53->22
                  -- GENERATED --
              */
          