
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

object authenticated extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template2[femr.common.dtos.CurrentUser,AssetsFinder,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(currentUser: femr.common.dtos.CurrentUser, assets: AssetsFinder):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {
/*3.2*/import femr.ui.views.partials.helpers.AuthenticatedPartialHelper
/*4.2*/import femr.ui.controllers.routes.HomeController
/*5.2*/import femr.ui.controllers.routes.SessionsController
/*6.2*/import femr.ui.controllers.routes.TriageController
/*7.2*/import femr.ui.controllers.routes.MedicalController
/*8.2*/import femr.ui.controllers.routes.PharmaciesController
/*9.2*/import femr.ui.controllers.manager.routes.ManagerController
/*10.2*/import femr.ui.controllers.superuser.routes.SuperuserController
/*11.2*/import femr.ui.controllers.admin.routes.AdminController
/*12.2*/import femr.ui.controllers.routes.ResearchController
/*13.2*/import femr.ui.controllers.routes.ReferenceController


Seq[Any](format.raw/*2.1*/("""
"""),format.raw/*14.1*/("""<div class="navigationLogo">
    <a href=""""),_display_(/*15.15*/HomeController/*15.29*/.index()),format.raw/*15.37*/("""">
        <img src=""""),_display_(/*16.20*/assets/*16.26*/.path("img/logo_color_wordless_sm.png")),format.raw/*16.65*/("""" />
    </a>
</div>


<div class="smallNavigationItemsWrap">
        <div class="hamburger">
            <div></div>
            <div></div>
            <div></div>
        </div>
</div>



<div class="navigationItemsWrap">
    <ul class="navigationItems">
        """),_display_(/*33.10*/if(AuthenticatedPartialHelper.showMedicalPersonnelMenu(currentUser.getRoles))/*33.87*/ {_display_(Seq[Any](format.raw/*33.89*/("""
            """),format.raw/*34.13*/("""<li><a href=""""),_display_(/*34.27*/TriageController/*34.43*/.indexGet()),format.raw/*34.54*/("""">Triage</a></li>
            <li><a href=""""),_display_(/*35.27*/MedicalController/*35.44*/.indexGet()),format.raw/*35.55*/("""">Medical</a></li>
            <li><a href=""""),_display_(/*36.27*/PharmaciesController/*36.47*/.indexGet()),format.raw/*36.58*/("""">Pharmacy</a></li>
        """)))}),format.raw/*37.10*/("""
        """),_display_(/*38.10*/if(AuthenticatedPartialHelper.showResearcherMenu(currentUser.getRoles))/*38.81*/ {_display_(Seq[Any](format.raw/*38.83*/("""
            """),format.raw/*39.13*/("""<li><a href=""""),_display_(/*39.27*/ResearchController/*39.45*/.indexGet()),format.raw/*39.56*/("""">Research</a></li>
        """)))}),format.raw/*40.10*/("""
        """),_display_(/*41.10*/if(AuthenticatedPartialHelper.showManagerMenu(currentUser.getRoles))/*41.78*/{_display_(Seq[Any](format.raw/*41.79*/("""
            """),format.raw/*42.13*/("""<li><a href=""""),_display_(/*42.27*/ManagerController/*42.44*/.indexGet()),format.raw/*42.55*/("""">Manager</a></li>
        """)))}),format.raw/*43.10*/("""
        """),_display_(/*44.10*/if(AuthenticatedPartialHelper.showAdminMenu(currentUser.getRoles) && !AuthenticatedPartialHelper.showSuperUserMenu(currentUser.getRoles))/*44.147*/ {_display_(Seq[Any](format.raw/*44.149*/("""
            """),format.raw/*45.13*/("""<li><a href=""""),_display_(/*45.27*/AdminController/*45.42*/.index()),format.raw/*45.50*/("""">Admin</a></li>
        """)))}),format.raw/*46.10*/("""
        """),_display_(/*47.10*/if(AuthenticatedPartialHelper.showSuperUserMenu(currentUser.getRoles))/*47.80*/ {_display_(Seq[Any](format.raw/*47.82*/("""
            """),format.raw/*48.13*/("""<li><a href=""""),_display_(/*48.27*/SuperuserController/*48.46*/.indexGet()),format.raw/*48.57*/("""">SuperUser</a></li>
        """)))}),format.raw/*49.10*/("""
        """),format.raw/*50.9*/("""<li><a href=""""),_display_(/*50.23*/ReferenceController/*50.42*/.indexGet()),format.raw/*50.53*/("""" class="glyphicon glyphicon-list-alt reference" target="_blank"><span>Reference</span></a></li>
    </ul>
    <p class="userStatus">


        """),_display_(/*55.10*/currentUser/*55.21*/.getFirstName),format.raw/*55.34*/(""" """),_display_(/*55.36*/currentUser/*55.47*/.getLastName),format.raw/*55.59*/(""" 
        """),format.raw/*56.9*/("""<a href=""""),_display_(/*56.19*/SessionsController/*56.37*/.delete()),format.raw/*56.46*/("""">
            <span class="glyphicon glyphicon-log-out"></span>
        </a>
    </p>
</div>
<input type="hidden" value=""""),_display_(/*61.30*/currentUser/*61.41*/.getTimeout2()),format.raw/*61.55*/("""" id='h_v' class='h_v'>
"""))
      }
    }
  }

  def render(currentUser:femr.common.dtos.CurrentUser,assets:AssetsFinder): play.twirl.api.HtmlFormat.Appendable = apply(currentUser,assets)

  def f:((femr.common.dtos.CurrentUser,AssetsFinder) => play.twirl.api.HtmlFormat.Appendable) = (currentUser,assets) => apply(currentUser,assets)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  DATE: Wed Jan 20 18:29:28 PST 2021
                  SOURCE: /Users/noel/Desktop/CPE/femr/femr/app/femr/ui/views/partials/authenticated.scala.html
                  HASH: a12e04a47ce623096f0fbf206d78cfa0cd6db410
                  MATRIX: 1008->1|1146->69|1218->135|1274->185|1334->239|1392->291|1451->344|1513->400|1581->461|1653->526|1717->583|1778->637|1860->67|1888->691|1958->734|1981->748|2010->756|2059->778|2074->784|2134->823|2428->1090|2514->1167|2554->1169|2595->1182|2636->1196|2661->1212|2693->1223|2764->1267|2790->1284|2822->1295|2894->1340|2923->1360|2955->1371|3015->1400|3052->1410|3132->1481|3172->1483|3213->1496|3254->1510|3281->1528|3313->1539|3373->1568|3410->1578|3487->1646|3526->1647|3567->1660|3608->1674|3634->1691|3666->1702|3725->1730|3762->1740|3909->1877|3950->1879|3991->1892|4032->1906|4056->1921|4085->1929|4142->1955|4179->1965|4258->2035|4298->2037|4339->2050|4380->2064|4408->2083|4440->2094|4501->2124|4537->2133|4578->2147|4606->2166|4638->2177|4810->2322|4830->2333|4864->2346|4893->2348|4913->2359|4946->2371|4983->2381|5020->2391|5047->2409|5077->2418|5227->2541|5247->2552|5282->2566
                  LINES: 28->1|31->3|32->4|33->5|34->6|35->7|36->8|37->9|38->10|39->11|40->12|41->13|44->2|45->14|46->15|46->15|46->15|47->16|47->16|47->16|64->33|64->33|64->33|65->34|65->34|65->34|65->34|66->35|66->35|66->35|67->36|67->36|67->36|68->37|69->38|69->38|69->38|70->39|70->39|70->39|70->39|71->40|72->41|72->41|72->41|73->42|73->42|73->42|73->42|74->43|75->44|75->44|75->44|76->45|76->45|76->45|76->45|77->46|78->47|78->47|78->47|79->48|79->48|79->48|79->48|80->49|81->50|81->50|81->50|81->50|86->55|86->55|86->55|86->55|86->55|86->55|87->56|87->56|87->56|87->56|92->61|92->61|92->61
                  -- GENERATED --
              */
          