
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


Seq[Any](format.raw/*2.1*/("""
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
                  DATE: Wed Jan 20 18:29:28 PST 2021
                  SOURCE: /Users/noel/Desktop/CPE/femr/femr/app/femr/ui/views/partials/search.scala.html
                  HASH: 45dd97719dd18f8c73532ce8ff2a2a3af93cc71b
                  MATRIX: 966->1|1053->18|1132->16|1159->69|1186->70|1244->103|1256->107|1276->119|1290->125|1299->136|1323->152|1362->154|1402->168|1416->174|1479->229|1518->231|1563->248|1846->500|1874->519|1900->536|1940->538|1981->552|1996->558|2061->614|2101->616|2146->633|2429->885|2457->904|2483->921|2523->923|2564->937|2579->943|2644->999|2684->1001|2729->1018|3012->1270|3040->1289|3067->1307|3107->1309|3148->1323|3163->1329|3229->1386|3269->1388|3314->1405|3597->1657|3625->1676|3643->1685|3683->1687|3725->1704|3758->1850|3788->1853
                  LINES: 28->1|31->3|34->2|35->4|36->5|38->7|38->7|38->7|38->7|38->8|38->8|38->8|39->9|39->9|39->9|39->9|40->10|42->12|43->14|43->14|43->14|44->15|44->15|44->15|44->15|45->16|47->18|48->20|48->20|48->20|49->21|49->21|49->21|49->21|50->22|52->24|53->26|53->26|53->26|54->27|54->27|54->27|54->27|55->28|57->30|58->32|58->32|58->32|60->35|61->37|64->40
                  -- GENERATED --
              */
          