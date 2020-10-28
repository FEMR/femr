
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

object admin extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template9[String,femr.common.dtos.CurrentUser,Html,Html,Html,Html,Html,AssetsFinder,Html,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.3*/(title: String,
        currentUser: femr.common.dtos.CurrentUser,
        styles: Html = Html(""),
        scripts: Html = Html(""),
        message: Html = Html(""),
        outsideContainerTop: Html = Html(""),
        outsideContainerBottom: Html = Html(""), assets: AssetsFinder)(content: Html):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {
/*9.2*/import femr.ui.views.html.layouts.main
/*10.2*/import femr.ui.controllers.admin.routes.UsersController
/*11.2*/import femr.ui.controllers.admin.routes.ConfigureController
/*12.2*/import femr.ui.controllers.admin.routes.InventoryController
/*13.2*/import femr.ui.controllers.superuser.routes.SuperuserController
/*14.2*/import femr.ui.controllers.superuser.routes.TabController
/*15.2*/import femr.ui.controllers.admin.routes.TripController

def /*18.6*/adminScripts/*18.18*/:play.twirl.api.HtmlFormat.Appendable = {_display_(

Seq[Any](format.raw/*18.22*/("""
        """),format.raw/*19.9*/("""<script type = "text/javascript" src=""""),_display_(/*19.48*/assets/*19.54*/.path("js/admin/admin.js")),format.raw/*19.80*/(""""></script>
        <script type = "text/javascript" src=""""),_display_(/*20.48*/assets/*20.54*/.path("js/libraries/jquery-ui.min.js")),format.raw/*20.92*/(""""></script>
        """),_display_(/*21.10*/scripts),format.raw/*21.17*/("""
    """)))};def /*23.6*/adminStyles/*23.17*/:play.twirl.api.HtmlFormat.Appendable = {_display_(

Seq[Any](format.raw/*23.21*/("""
        """),format.raw/*24.9*/("""<link rel="stylesheet" href=""""),_display_(/*24.39*/assets/*24.45*/.path("css/libraries/jquery-ui.min.css")),format.raw/*24.85*/("""">
        <link rel="stylesheet" href=""""),_display_(/*25.39*/assets/*25.45*/.path("css/admin/admin.css")),format.raw/*25.73*/("""">
        """),_display_(/*26.10*/styles),format.raw/*26.16*/("""
    """)))};
Seq[Any](format.raw/*7.86*/("""

"""),format.raw/*16.1*/("""
        """),format.raw/*17.51*/("""
    """),format.raw/*22.6*/("""
    """),format.raw/*27.6*/("""

"""),_display_(/*29.2*/main(title, currentUser, adminStyles, adminScripts, assets = assets)/*29.70*/ {_display_(Seq[Any](format.raw/*29.72*/("""
    """),format.raw/*30.5*/("""<div id="admin-panels">
        <div id="admin-left-panel">
            <a href=""""),_display_(/*32.23*/ConfigureController/*32.42*/.manageGet()),format.raw/*32.54*/("""" class="fButton fOtherButton fAdminButton userBtns fAdminButtonLeftTextAlign"><span class="glyphicon glyphicon-cog"></span>
                Configure</a>
            <a href=""""),_display_(/*34.23*/InventoryController/*34.42*/.manageGet(0)),format.raw/*34.55*/("""" class="fButton fOtherButton fAdminButton userBtns fAdminButtonLeftTextAlign"><span class="glyphicon glyphicon-briefcase"></span>
                Inventory</a>
            <a href=""""),_display_(/*36.23*/TripController/*36.37*/.manageGet()),format.raw/*36.49*/("""" class="fButton userBtns fOtherButton fAdminButton fAdminButtonLeftTextAlign"><span class="glyphicon glyphicon-globe"></span>
                Trips</a>
            """),_display_(/*38.14*/if(currentUser.getEmail == "superuser")/*38.53*/{_display_(Seq[Any](format.raw/*38.54*/("""
                """),format.raw/*39.17*/("""<a href=""""),_display_(/*39.27*/TabController/*39.40*/.manageGet()),format.raw/*39.52*/("""" class="fButton userBtns fOtherButton fAdminButton fAdminButtonLeftTextAlign"><span class="glyphicon glyphicon-wrench"></span>
                    Tabs</a>

            """)))}),format.raw/*42.14*/("""
            """),format.raw/*43.13*/("""<a href=""""),_display_(/*43.23*/UsersController/*43.38*/.manageGet()),format.raw/*43.50*/("""" class="fButton fOtherButton fAdminButton userBtns fAdminButtonLeftTextAlign"><span class="glyphicon glyphicon-user"></span>
                Users</a>
        </div>
        <div id="admin-right-panel">
            <div id="title">
                <h2>"""),_display_(/*48.22*/title),format.raw/*48.27*/("""</h2>
            </div>
            """),_display_(/*50.14*/message),format.raw/*50.21*/("""
            """),format.raw/*51.13*/("""<div id="adminContent">
            """),_display_(/*52.14*/content),format.raw/*52.21*/("""
            """),format.raw/*53.13*/("""</div>

        </div>
    </div>

""")))}),format.raw/*58.2*/("""
"""))
      }
    }
  }

  def render(title:String,currentUser:femr.common.dtos.CurrentUser,styles:Html,scripts:Html,message:Html,outsideContainerTop:Html,outsideContainerBottom:Html,assets:AssetsFinder,content:Html): play.twirl.api.HtmlFormat.Appendable = apply(title,currentUser,styles,scripts,message,outsideContainerTop,outsideContainerBottom,assets)(content)

  def f:((String,femr.common.dtos.CurrentUser,Html,Html,Html,Html,Html,AssetsFinder) => (Html) => play.twirl.api.HtmlFormat.Appendable) = (title,currentUser,styles,scripts,message,outsideContainerTop,outsideContainerBottom,assets) => (content) => apply(title,currentUser,styles,scripts,message,outsideContainerTop,outsideContainerBottom,assets)(content)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  DATE: Tue Oct 27 21:59:18 PDT 2020
                  SOURCE: C:/Users/Noel/Documents/csc402/lemur-femr/femr/app/femr/ui/views/layouts/admin.scala.html
                  HASH: ea615f46c522a8e1ec1acb7fa39b770b9db73167
                  MATRIX: 1036->2|1414->312|1461->353|1525->411|1593->473|1661->535|1733->601|1799->661|1867->776|1888->788|1969->792|2006->802|2072->841|2087->847|2134->873|2221->933|2236->939|2295->977|2344->999|2372->1006|2402->1020|2422->1031|2503->1035|2540->1045|2597->1075|2612->1081|2673->1121|2742->1163|2757->1169|2806->1197|2846->1210|2873->1216|2919->307|2950->717|2988->769|3021->1013|3054->1223|3085->1228|3162->1296|3202->1298|3235->1304|3346->1388|3374->1407|3407->1419|3613->1598|3641->1617|3675->1630|3887->1815|3910->1829|3943->1841|4138->2009|4186->2048|4225->2049|4271->2067|4308->2077|4330->2090|4363->2102|4568->2276|4610->2290|4647->2300|4671->2315|4704->2327|4990->2586|5016->2591|5083->2631|5111->2638|5153->2652|5218->2690|5246->2697|5288->2711|5359->2752
                  LINES: 28->1|37->9|38->10|39->11|40->12|41->13|42->14|43->15|45->18|45->18|47->18|48->19|48->19|48->19|48->19|49->20|49->20|49->20|50->21|50->21|51->23|51->23|53->23|54->24|54->24|54->24|54->24|55->25|55->25|55->25|56->26|56->26|58->7|60->16|61->17|62->22|63->27|65->29|65->29|65->29|66->30|68->32|68->32|68->32|70->34|70->34|70->34|72->36|72->36|72->36|74->38|74->38|74->38|75->39|75->39|75->39|75->39|78->42|79->43|79->43|79->43|79->43|84->48|84->48|86->50|86->50|87->51|88->52|88->52|89->53|94->58
                  -- GENERATED --
              */
          