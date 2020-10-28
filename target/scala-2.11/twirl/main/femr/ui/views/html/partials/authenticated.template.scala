
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


Seq[Any](format.raw/*1.67*/("""

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
                  DATE: Tue Oct 27 21:59:18 PDT 2020
                  SOURCE: C:/Users/Noel/Documents/csc402/lemur-femr/femr/app/femr/ui/views/partials/authenticated.scala.html
                  HASH: 1d872475ae501c6b9801e563d1a2eec0a9daea29
                  MATRIX: 1008->1|1146->71|1218->138|1274->189|1334->244|1392->297|1451->351|1513->408|1581->470|1653->536|1717->594|1778->649|1861->66|1892->704|1963->748|1986->762|2015->770|2065->793|2080->799|2140->838|2451->1122|2537->1199|2577->1201|2619->1215|2660->1229|2685->1245|2717->1256|2789->1301|2815->1318|2847->1329|2920->1375|2949->1395|2981->1406|3042->1436|3080->1447|3160->1518|3200->1520|3242->1534|3283->1548|3310->1566|3342->1577|3403->1607|3441->1618|3518->1686|3557->1687|3599->1701|3640->1715|3666->1732|3698->1743|3758->1772|3796->1783|3943->1920|3984->1922|4026->1936|4067->1950|4091->1965|4120->1973|4178->2000|4216->2011|4295->2081|4335->2083|4377->2097|4418->2111|4446->2130|4478->2141|4540->2172|4577->2182|4618->2196|4646->2215|4678->2226|4855->2376|4875->2387|4909->2400|4938->2402|4958->2413|4991->2425|5029->2436|5066->2446|5093->2464|5123->2473|5278->2601|5298->2612|5333->2626
                  LINES: 28->1|31->3|32->4|33->5|34->6|35->7|36->8|37->9|38->10|39->11|40->12|41->13|44->1|46->14|47->15|47->15|47->15|48->16|48->16|48->16|65->33|65->33|65->33|66->34|66->34|66->34|66->34|67->35|67->35|67->35|68->36|68->36|68->36|69->37|70->38|70->38|70->38|71->39|71->39|71->39|71->39|72->40|73->41|73->41|73->41|74->42|74->42|74->42|74->42|75->43|76->44|76->44|76->44|77->45|77->45|77->45|77->45|78->46|79->47|79->47|79->47|80->48|80->48|80->48|80->48|81->49|82->50|82->50|82->50|82->50|87->55|87->55|87->55|87->55|87->55|87->55|88->56|88->56|88->56|88->56|93->61|93->61|93->61
                  -- GENERATED --
              */
          