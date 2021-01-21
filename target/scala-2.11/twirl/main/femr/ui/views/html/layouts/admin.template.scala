
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
Seq[Any](format.raw/*8.1*/("""
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
                  DATE: Wed Jan 20 18:29:26 PST 2021
                  SOURCE: /Users/noel/Desktop/CPE/femr/femr/app/femr/ui/views/layouts/admin.scala.html
                  HASH: 0fc51f659a2b4a0347f5d504101eb811dc72919f
                  MATRIX: 1036->2|1408->304|1455->344|1519->401|1587->462|1655->523|1727->588|1793->647|1861->759|1882->771|1963->775|1999->784|2065->823|2080->829|2127->855|2213->914|2228->920|2287->958|2335->979|2363->986|2392->998|2412->1009|2493->1013|2529->1022|2586->1052|2601->1058|2662->1098|2730->1139|2745->1145|2794->1173|2833->1185|2860->1191|2904->302|2932->702|2969->753|3001->992|3033->1197|3062->1200|3139->1268|3179->1270|3211->1275|3320->1357|3348->1376|3381->1388|3585->1565|3613->1584|3647->1597|3857->1780|3880->1794|3913->1806|4106->1972|4154->2011|4193->2012|4238->2029|4275->2039|4297->2052|4330->2064|4532->2235|4573->2248|4610->2258|4634->2273|4667->2285|4948->2539|4974->2544|5039->2582|5067->2589|5108->2602|5172->2639|5200->2646|5241->2659|5307->2695
                  LINES: 28->1|37->9|38->10|39->11|40->12|41->13|42->14|43->15|45->18|45->18|47->18|48->19|48->19|48->19|48->19|49->20|49->20|49->20|50->21|50->21|51->23|51->23|53->23|54->24|54->24|54->24|54->24|55->25|55->25|55->25|56->26|56->26|58->8|59->16|60->17|61->22|62->27|64->29|64->29|64->29|65->30|67->32|67->32|67->32|69->34|69->34|69->34|71->36|71->36|71->36|73->38|73->38|73->38|74->39|74->39|74->39|74->39|77->42|78->43|78->43|78->43|78->43|83->48|83->48|85->50|85->50|86->51|87->52|87->52|88->53|93->58
                  -- GENERATED --
              */
          