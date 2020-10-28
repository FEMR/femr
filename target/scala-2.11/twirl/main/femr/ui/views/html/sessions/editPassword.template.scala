
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
Seq[Any](format.raw/*1.193*/("""

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
                  DATE: Tue Oct 27 21:59:18 PDT 2020
                  SOURCE: C:/Users/Noel/Documents/csc402/lemur-femr/femr/app/femr/ui/views/sessions/editPassword.scala.html
                  HASH: 3d96dddcf3680ba7afb3d9bccd8866f0219cd589
                  MATRIX: 1159->1|1423->197|1469->238|1534->293|1547->299|1627->303|1659->309|1715->339|1729->345|1771->367|1798->375|1823->392|1903->396|1935->402|1998->439|2012->445|2058->471|2095->488|2122->506|2203->510|2232->513|2272->537|2312->539|2345->546|2377->569|2417->571|2454->581|2506->606|2534->613|2575->624|2608->627|2651->192|2681->372|2710->485|2739->630|2770->635|2826->682|2866->684|2899->690|2955->720|2970->726|3085->831|3126->833|3163->843|3202->855|3232->864|3261->866|3290->874|3419->1013|3457->1024|3496->1042|3533->1052|4155->1644|4188->1650|4227->1659
                  LINES: 30->1|33->3|34->4|36->5|36->5|38->5|39->6|39->6|39->6|39->6|40->8|40->8|42->8|43->9|43->9|43->9|43->9|44->11|44->11|46->11|47->12|47->12|47->12|48->13|48->13|48->13|49->14|49->14|49->14|50->15|51->16|53->1|55->7|56->10|57->17|59->19|59->19|59->19|60->20|61->21|61->21|61->21|61->21|62->22|62->22|62->22|62->22|62->22|64->24|65->25|65->25|66->26|78->38|79->39|80->40
                  -- GENERATED --
              */
          