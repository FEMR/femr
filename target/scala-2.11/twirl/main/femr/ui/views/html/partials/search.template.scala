
package femr.ui.views.html.partials

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

object search extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template1[String,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(page: String):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {
/*3.2*/import femr.ui.controllers.routes.SearchController


Seq[Any](format.raw/*1.16*/("""

"""),format.raw/*4.1*/("""
"""),format.raw/*5.1*/("""<div id="searchContainer">

    """),_display_(/*7.6*/page/*7.10*/.toLowerCase/*7.22*/ match/*7.28*/ {/*8.9*/case "triage" =>/*8.25*/ {_display_(Seq[Any](format.raw/*8.27*/("""
            """),_display_(/*9.14*/helper/*9.20*/.form(action = SearchController.handleSearch("triage"))/*9.75*/ {_display_(Seq[Any](format.raw/*9.77*/("""
                """),format.raw/*10.17*/("""<input type="text" class="fInput" id = "nameOrIdSearchForm" placeholder="ID, Name, or Phone #" name="patientSearchQuery"/>
                <button type="submit" id="searchBtn" class="fButton fRedButton fGlobalSearchButton">Search</button>
            """)))}),format.raw/*12.14*/("""
        """)))}/*14.9*/case "history" =>/*14.26*/ {_display_(Seq[Any](format.raw/*14.28*/("""
            """),_display_(/*15.14*/helper/*15.20*/.form(action = SearchController.handleSearch("history"))/*15.76*/ {_display_(Seq[Any](format.raw/*15.78*/("""
                """),format.raw/*16.17*/("""<input type="text" class="fInput" id = "nameOrIdSearchForm" placeholder="ID, Name, or Phone #" name="patientSearchQuery"/>
                <button type="submit" id="searchBtn" class="fButton fRedButton fGlobalSearchButton">Search</button>
            """)))}),format.raw/*18.14*/("""
        """)))}/*20.9*/case "medical" =>/*20.26*/ {_display_(Seq[Any](format.raw/*20.28*/("""
            """),_display_(/*21.14*/helper/*21.20*/.form(action = SearchController.handleSearch("medical"))/*21.76*/ {_display_(Seq[Any](format.raw/*21.78*/("""
                """),format.raw/*22.17*/("""<input type="text" class="fInput" id = "nameOrIdSearchForm" placeholder="ID, Name, or Phone #" name="patientSearchQuery"/>
                <button type="submit" id="searchBtn" class="fButton fRedButton fGlobalSearchButton">Search</button>
            """)))}),format.raw/*24.14*/("""
        """)))}/*26.9*/case "pharmacy" =>/*26.27*/ {_display_(Seq[Any](format.raw/*26.29*/("""
            """),_display_(/*27.14*/helper/*27.20*/.form(action = SearchController.handleSearch("pharmacy"))/*27.77*/ {_display_(Seq[Any](format.raw/*27.79*/("""
                """),format.raw/*28.17*/("""<input type="text" class="fInput" id = "nameOrIdSearchForm" placeholder="ID, Name, or Phone #" name="patientSearchQuery"/>
                <button type="submit" id="searchBtn" class="fButton fRedButton fGlobalSearchButton">Search</button>
            """)))}),format.raw/*30.14*/("""
        """)))}/*32.9*/case _ =>/*32.18*/ {_display_(Seq[Any](format.raw/*32.20*/("""

        """)))}}),format.raw/*35.6*/("""
    """),format.raw/*37.54*/("""


"""),format.raw/*40.1*/("""</div>"""))
      }
    }
  }

  def render(page:String): play.twirl.api.HtmlFormat.Appendable = apply(page)

  def f:((String) => play.twirl.api.HtmlFormat.Appendable) = (page) => apply(page)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  DATE: Tue Oct 27 21:59:18 PDT 2020
                  SOURCE: C:/Users/Noel/Documents/csc402/lemur-femr/femr/app/femr/ui/views/partials/search.scala.html
                  HASH: 41c6e35170fb7d42c0261d994c4dcabdf706e1f0
                  MATRIX: 966->1|1053->20|1133->15|1163->72|1191->74|1251->109|1263->113|1283->125|1297->131|1306->143|1330->159|1369->161|1410->176|1424->182|1487->237|1526->239|1572->257|1857->511|1886->532|1912->549|1952->551|1994->566|2009->572|2074->628|2114->630|2160->648|2445->902|2474->923|2500->940|2540->942|2582->957|2597->963|2662->1019|2702->1021|2748->1039|3033->1293|3062->1314|3089->1332|3129->1334|3171->1349|3186->1355|3252->1412|3292->1414|3338->1432|3623->1686|3652->1707|3670->1716|3710->1718|3754->1738|3788->1886|3821->1892
                  LINES: 28->1|31->3|34->1|36->4|37->5|39->7|39->7|39->7|39->7|39->8|39->8|39->8|40->9|40->9|40->9|40->9|41->10|43->12|44->14|44->14|44->14|45->15|45->15|45->15|45->15|46->16|48->18|49->20|49->20|49->20|50->21|50->21|50->21|50->21|51->22|53->24|54->26|54->26|54->26|55->27|55->27|55->27|55->27|56->28|58->30|59->32|59->32|59->32|61->35|62->37|65->40
                  -- GENERATED --
              */
          