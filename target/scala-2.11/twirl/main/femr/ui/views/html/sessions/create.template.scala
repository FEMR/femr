
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
Seq[Any](format.raw/*1.102*/("""

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
                  DATE: Tue Oct 27 21:59:18 PDT 2020
                  SOURCE: C:/Users/Noel/Documents/csc402/lemur-femr/femr/app/femr/ui/views/sessions/create.scala.html
                  HASH: eedb565872164246c626b40a3870604be5b9ee6d
                  MATRIX: 1026->1|1199->106|1245->147|1310->206|1324->212|1404->216|1440->226|1496->256|1510->262|1552->284|1601->101|1631->201|1661->293|1689->296|1744->343|1783->345|1815->352|1845->374|1883->375|1920->385|2069->504|2104->513|2119->519|2200->591|2240->593|2277->603|2372->671|2403->693|2443->695|2489->713|2556->753|2575->763|2611->778|2649->798|2662->802|2701->803|2747->821|2847->890|2889->904|3057->1045|3072->1051|3123->1081|3180->1108|3213->1111
                  LINES: 28->1|31->3|32->4|34->5|34->5|36->5|37->6|37->6|37->6|37->6|39->1|41->5|41->7|42->8|42->8|42->8|43->9|43->9|43->9|44->10|47->13|49->15|49->15|49->15|49->15|50->16|52->18|52->18|52->18|53->19|53->19|53->19|53->19|54->20|54->20|54->20|55->21|56->22|57->23|59->25|59->25|59->25|61->27|62->28
                  -- GENERATED --
              */
          