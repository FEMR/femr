
package femr.ui.views.html.sessions

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

object create extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template3[Form[femr.ui.models.sessions.CreateViewModel],Integer,AssetsFinder,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(createForm: Form[femr.ui.models.sessions.CreateViewModel], errorMsg: Integer, assets: AssetsFinder):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {
/*3.2*/import femr.ui.views.html.layouts.main
/*4.2*/import femr.ui.controllers.routes.SessionsController

def /*5.6*/styles/*5.12*/:play.twirl.api.HtmlFormat.Appendable = {_display_(

Seq[Any](format.raw/*5.16*/("""
        """),format.raw/*6.9*/("""<link rel="stylesheet" href=""""),_display_(/*6.39*/assets/*6.45*/.path("css/login.css")),format.raw/*6.67*/("""">
    """)))};
Seq[Any](format.raw/*2.1*/("""
"""),format.raw/*5.1*/("""    """),format.raw/*7.6*/("""
"""),_display_(/*8.2*/main("Login", styles = styles, assets = assets)/*8.49*/ {_display_(Seq[Any](format.raw/*8.51*/("""
    """),_display_(/*9.6*/if(errorMsg.equals(1))/*9.28*/{_display_(Seq[Any](format.raw/*9.29*/("""
        """),format.raw/*10.9*/("""<div id="errormsg" style="color:red">
                <h1>Login Attempt Failed! Try again.</h1>
        </div>
    """)))}),format.raw/*13.6*/("""

    """),_display_(/*15.6*/helper/*15.12*/.form(action = SessionsController.createPost(), 'class -> "form-signin")/*15.84*/ {_display_(Seq[Any](format.raw/*15.86*/("""
        """),format.raw/*16.9*/("""<div id="login">
            <h1>Please sign in</h1>
            """),_display_(/*18.14*/if(createForm != null)/*18.36*/ {_display_(Seq[Any](format.raw/*18.38*/("""
                """),format.raw/*19.17*/("""<input type="text" name="email" value='"""),_display_(/*19.57*/createForm/*19.67*/.get().getEmail),format.raw/*19.82*/("""' />
            """)))}/*20.15*/else/*20.19*/{_display_(Seq[Any](format.raw/*20.20*/("""
                """),format.raw/*21.17*/("""<input type="text" name="email" placeholder="Email" />
            """)))}),format.raw/*22.14*/("""
            """),format.raw/*23.13*/("""<input type="password" name="password" placeholder="Password" />
            <input type="submit" value="Log in" />
            <img src=""""),_display_(/*25.24*/assets/*25.30*/.path("img/logo_color_sm.png")),format.raw/*25.60*/("""" />
        </div>
    """)))}),format.raw/*27.6*/("""
""")))}),format.raw/*28.2*/("""
"""))
      }
    }
  }

  def render(createForm:Form[femr.ui.models.sessions.CreateViewModel],errorMsg:Integer,assets:AssetsFinder): play.twirl.api.HtmlFormat.Appendable = apply(createForm,errorMsg,assets)

  def f:((Form[femr.ui.models.sessions.CreateViewModel],Integer,AssetsFinder) => play.twirl.api.HtmlFormat.Appendable) = (createForm,errorMsg,assets) => apply(createForm,errorMsg,assets)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  DATE: Wed Jan 20 18:29:26 PST 2021
                  SOURCE: /Users/noel/Desktop/CPE/femr/femr/app/femr/ui/views/sessions/create.scala.html
                  HASH: ff5d6bb051f55f39f3fa01c6218d3becd8dfc992
                  MATRIX: 1026->1|1199->104|1245->144|1310->202|1324->208|1404->212|1439->221|1495->251|1509->257|1551->279|1597->102|1624->197|1654->287|1681->289|1736->336|1775->338|1806->344|1836->366|1874->367|1910->376|2056->492|2089->499|2104->505|2185->577|2225->579|2261->588|2354->654|2385->676|2425->678|2470->695|2537->735|2556->745|2592->760|2629->779|2642->783|2681->784|2726->801|2825->869|2866->882|3032->1021|3047->1027|3098->1057|3153->1082|3185->1084
                  LINES: 28->1|31->3|32->4|34->5|34->5|36->5|37->6|37->6|37->6|37->6|39->2|40->5|40->7|41->8|41->8|41->8|42->9|42->9|42->9|43->10|46->13|48->15|48->15|48->15|48->15|49->16|51->18|51->18|51->18|52->19|52->19|52->19|52->19|53->20|53->20|53->20|54->21|55->22|56->23|58->25|58->25|58->25|60->27|61->28
                  -- GENERATED --
              */
          