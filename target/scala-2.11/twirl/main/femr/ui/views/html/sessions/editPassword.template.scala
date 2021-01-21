
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

object editPassword extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template5[java.lang.String,java.lang.String,play.data.Form[femr.ui.models.sessions.CreateViewModel],java.util.List[_$1] forSome { 
   type _$1 >: _root_.scala.Nothing <: java.lang.String
},AssetsFinder,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(firstName: java.lang.String, lastName: java.lang.String, form: play.data.Form[femr.ui.models.sessions.CreateViewModel], messages: java.util.List[_ <: java.lang.String], assets: AssetsFinder):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {
/*3.2*/import femr.ui.views.html.layouts.main
/*4.2*/import femr.ui.controllers.routes.SessionsController

def /*5.2*/styles/*5.8*/:play.twirl.api.HtmlFormat.Appendable = {_display_(

Seq[Any](format.raw/*5.12*/("""
    """),format.raw/*6.5*/("""<link rel="stylesheet" href=""""),_display_(/*6.35*/assets/*6.41*/.path("css/login.css")),format.raw/*6.63*/("""">
""")))};def /*8.2*/additionalScripts/*8.19*/:play.twirl.api.HtmlFormat.Appendable = {_display_(

Seq[Any](format.raw/*8.23*/("""
    """),format.raw/*9.5*/("""<script type="text/javascript" src=""""),_display_(/*9.42*/assets/*9.48*/.path("js/admin/users.js")),format.raw/*9.74*/(""""></script>
""")))};def /*11.2*/additionalMessages/*11.20*/:play.twirl.api.HtmlFormat.Appendable = {_display_(

Seq[Any](format.raw/*11.24*/("""
"""),_display_(/*12.2*/for(message <- messages) yield /*12.26*/ {_display_(Seq[Any](format.raw/*12.28*/("""
    """),_display_(/*13.6*/if(!messages.isEmpty())/*13.29*/ {_display_(Seq[Any](format.raw/*13.31*/("""
        """),format.raw/*14.9*/("""<p class="adminMessage">"""),_display_(/*14.34*/message),format.raw/*14.41*/("""</p>
    """)))}),format.raw/*15.6*/("""
""")))}),format.raw/*16.2*/("""
""")))};
Seq[Any](format.raw/*2.1*/("""
"""),format.raw/*7.2*/("""
"""),format.raw/*10.2*/("""
"""),format.raw/*17.2*/("""

"""),_display_(/*19.2*/main("Login", styles = styles, assets = assets)/*19.49*/ {_display_(Seq[Any](format.raw/*19.51*/("""
    """),format.raw/*20.5*/("""<div class="container">
    """),_display_(/*21.6*/helper/*21.12*/.form(action = SessionsController.editPasswordPost(), 'class -> "form-horizontal", 'name -> "createForm")/*21.117*/ {_display_(Seq[Any](format.raw/*21.119*/("""
        """),format.raw/*22.9*/("""<h1>Hello, """),_display_(/*22.21*/firstName),format.raw/*22.30*/(""" """),_display_(/*22.32*/lastName),format.raw/*22.40*/(""", your password is older than 90 days.</h1>
        <h4>Please choose a new password:</h4>
        """),format.raw/*24.47*/("""
        """),_display_(/*25.10*/additionalMessages),format.raw/*25.28*/("""
        """),format.raw/*26.9*/("""<div id="passwordsWrap">
            <div>
                <label class="labelChangePassword" for="password1">Enter password:</label>
                <input type="password" class="fInput fInputChangePassword" name="newPassword"/>
            </div>
            <div>
                <label class="labelChangePassword" for="password2">Re-Enter password:</label>
                <input type="password" class="fInput fInputChangePassword" name="newPasswordVerify"/>
            </div>

            <button type="submit" class="fButton pull-right">Submit</button>
        </div>
    """)))}),format.raw/*38.6*/("""
    """),format.raw/*39.5*/("""</div>
""")))}),format.raw/*40.2*/("""
"""))
      }
    }
  }

  def render(firstName:java.lang.String,lastName:java.lang.String,form:play.data.Form[femr.ui.models.sessions.CreateViewModel],messages:java.util.List[_$1] forSome { 
   type _$1 >: _root_.scala.Nothing <: java.lang.String
},assets:AssetsFinder): play.twirl.api.HtmlFormat.Appendable = apply(firstName,lastName,form,messages,assets)

  def f:((java.lang.String,java.lang.String,play.data.Form[femr.ui.models.sessions.CreateViewModel],java.util.List[_$1] forSome { 
   type _$1 >: _root_.scala.Nothing <: java.lang.String
},AssetsFinder) => play.twirl.api.HtmlFormat.Appendable) = (firstName,lastName,form,messages,assets) => apply(firstName,lastName,form,messages,assets)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  DATE: Wed Jan 20 18:29:26 PST 2021
                  SOURCE: /Users/noel/Desktop/CPE/femr/femr/app/femr/ui/views/sessions/editPassword.scala.html
                  HASH: 0a84de603f184f3cef7b9133967eacec8f2b6ba2
                  MATRIX: 1157->1|1421->195|1467->235|1532->289|1545->295|1625->299|1656->304|1712->334|1726->340|1768->362|1794->368|1819->385|1899->389|1930->394|1993->431|2007->437|2053->463|2089->478|2116->496|2197->500|2225->502|2265->526|2305->528|2337->534|2369->557|2409->559|2445->568|2497->593|2525->600|2565->610|2597->612|2637->193|2664->366|2692->476|2720->614|2749->617|2805->664|2845->666|2877->671|2932->700|2947->706|3062->811|3103->813|3139->822|3178->834|3208->843|3237->845|3266->853|3393->990|3430->1000|3469->1018|3505->1027|4115->1607|4147->1612|4185->1620
                  LINES: 30->1|33->3|34->4|36->5|36->5|38->5|39->6|39->6|39->6|39->6|40->8|40->8|42->8|43->9|43->9|43->9|43->9|44->11|44->11|46->11|47->12|47->12|47->12|48->13|48->13|48->13|49->14|49->14|49->14|50->15|51->16|53->2|54->7|55->10|56->17|58->19|58->19|58->19|59->20|60->21|60->21|60->21|60->21|61->22|61->22|61->22|61->22|61->22|63->24|64->25|64->25|65->26|77->38|78->39|79->40
                  -- GENERATED --
              */
          