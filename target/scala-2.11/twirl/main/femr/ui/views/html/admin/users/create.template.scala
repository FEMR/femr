
package femr.ui.views.html.admin.users

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

object create extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template5[femr.common.dtos.CurrentUser,play.data.Form[femr.ui.models.admin.users.CreateViewModel],java.util.List[_$1] forSome { 
   type _$1 >: _root_.scala.Nothing <: java.lang.String
},java.util.List[_$2] forSome { 
   type _$2 >: _root_.scala.Nothing <: java.lang.String
},AssetsFinder,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(currentUser: femr.common.dtos.CurrentUser,
        form: play.data.Form[femr.ui.models.admin.users.CreateViewModel],
        messages: java.util.List[_ <: java.lang.String],
        availableRoles: java.util.List[_ <: java.lang.String],
        assets: AssetsFinder):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {
/*7.2*/import views.html.helper.FieldConstructor
/*8.2*/import femr.ui.views.html.partials.admin.inputFieldConstructor
/*9.2*/import femr.ui.views.html.layouts.admin
/*10.2*/import femr.ui.controllers.admin.routes.UsersController

def /*15.6*/additionalStyles/*15.22*/:play.twirl.api.HtmlFormat.Appendable = {_display_(

Seq[Any](format.raw/*15.26*/("""
        """),format.raw/*16.9*/("""<link rel="stylesheet" href=""""),_display_(/*16.39*/assets/*16.45*/.path("css/admin/users.css")),format.raw/*16.73*/("""">
    """)))};def /*18.6*/additionalScripts/*18.23*/:play.twirl.api.HtmlFormat.Appendable = {_display_(

Seq[Any](format.raw/*18.27*/("""
        """),format.raw/*19.9*/("""<script type="text/javascript" src=""""),_display_(/*19.46*/assets/*19.52*/.path("js/admin/users.js")),format.raw/*19.78*/(""""></script>
    """)))};def /*21.6*/additionalMessages/*21.24*/:play.twirl.api.HtmlFormat.Appendable = {_display_(

Seq[Any](format.raw/*21.28*/("""
    """),_display_(/*22.6*/for(message <- messages) yield /*22.30*/ {_display_(Seq[Any](format.raw/*22.32*/("""
        """),format.raw/*23.9*/("""<p class="adminMessage">"""),_display_(/*23.34*/message),format.raw/*23.41*/("""</p>
    """)))}),format.raw/*24.6*/("""
    """)))};implicit def /*11.6*/implicitField/*11.19*/ = {{
        FieldConstructor(inputFieldConstructor.f)
    }};
Seq[Any](format.raw/*5.30*/("""

"""),format.raw/*11.1*/("""    """),format.raw/*13.6*/("""

    """),format.raw/*17.6*/("""
    """),format.raw/*20.6*/("""
    """),format.raw/*25.6*/("""

"""),_display_(/*27.2*/admin("Add User", currentUser, styles = additionalStyles, scripts = additionalScripts, message = additionalMessages, assets = assets)/*27.135*/ {_display_(Seq[Any](format.raw/*27.137*/("""
    """),format.raw/*28.5*/("""<div id="createWrap">
    """),_display_(/*29.6*/helper/*29.12*/.form(action = UsersController.createPost(), 'class -> "form-horizontal", 'name -> "createForm")/*29.108*/ {_display_(Seq[Any](format.raw/*29.110*/("""

        """),_display_(/*31.10*/helper/*31.16*/.inputText(form("email"),
            'class -> "fInput",
            'placeholder -> "Email Address",
            '_label -> "Email Address",
            '_isRequired -> true
        )),format.raw/*36.10*/("""

        """),_display_(/*38.10*/helper/*38.16*/.inputPassword(form("password"),
            'class -> "fInput",
            'placeholder -> "Password",
            '_label -> "Password",
            '_isRequired -> true
        )),format.raw/*43.10*/("""
"""),format.raw/*44.1*/("""<!-- added for FEMR-159 added by Vivek-->

        """),_display_(/*46.10*/helper/*46.16*/.inputPassword(form("passwordVerify"),
            'class -> "fInput",
            'placeholder -> "Verify Password",
            '_label -> null,
            'type -> "password"
        )),format.raw/*51.10*/("""

        """),_display_(/*53.10*/helper/*53.16*/.inputText(form("firstName"),
            'class -> "fInput",
            'placeholder -> "First Name",
            '_label -> "First Name",
            '_isRequired -> true
        )),format.raw/*58.10*/("""

        """),_display_(/*60.10*/helper/*60.16*/.inputText(form("lastName"),
            'class -> "fInput",
            'placeholder -> "Last Name",
            '_label -> "Last Name",
            '_isRequired -> false
        )),format.raw/*65.10*/("""

        """),_display_(/*67.10*/helper/*67.16*/.inputText(form("notes"),
            'class -> "fInput",
            'placeholder -> "About User",
            '_label -> "About User",
            '_isRequired -> false
        )),format.raw/*72.10*/("""

        """),format.raw/*74.9*/("""<div id="roleWrap">
            <label for="roles">Roles<span id="roles" class="red bold">*</span></label>
            <span class="errors"></span>
            """),_display_(/*77.14*/for(error <- form("roles").errors) yield /*77.48*/ {_display_(Seq[Any](format.raw/*77.50*/("""
                """),format.raw/*78.17*/("""<p class="createUserError"> """),_display_(/*78.46*/error/*78.51*/.message),format.raw/*78.59*/("""</p>
            """)))}),format.raw/*79.14*/("""

            """),format.raw/*81.13*/("""<br/>
            """),_display_(/*82.14*/for(role <- availableRoles) yield /*82.41*/ {_display_(Seq[Any](format.raw/*82.43*/("""
                """),format.raw/*83.17*/("""<label class="btn btn-default">
                    <input type="checkbox" name="roles[]" value=""""),_display_(/*84.67*/role),format.raw/*84.71*/(""""> """),_display_(/*84.75*/role),format.raw/*84.79*/("""
                """),format.raw/*85.17*/("""</label>
                <br/>
            """)))}),format.raw/*87.14*/("""
        """),format.raw/*88.9*/("""</div>
    </div>
    <button class="fButton fSubmitButton" id="addUserSubmitBtn" type="submit">Submit</button>
""")))}),format.raw/*91.2*/("""
""")))}))
      }
    }
  }

  def render(currentUser:femr.common.dtos.CurrentUser,form:play.data.Form[femr.ui.models.admin.users.CreateViewModel],messages:java.util.List[_$1] forSome { 
   type _$1 >: _root_.scala.Nothing <: java.lang.String
},availableRoles:java.util.List[_$2] forSome { 
   type _$2 >: _root_.scala.Nothing <: java.lang.String
},assets:AssetsFinder): play.twirl.api.HtmlFormat.Appendable = apply(currentUser,form,messages,availableRoles,assets)

  def f:((femr.common.dtos.CurrentUser,play.data.Form[femr.ui.models.admin.users.CreateViewModel],java.util.List[_$1] forSome { 
   type _$1 >: _root_.scala.Nothing <: java.lang.String
},java.util.List[_$2] forSome { 
   type _$2 >: _root_.scala.Nothing <: java.lang.String
},AssetsFinder) => play.twirl.api.HtmlFormat.Appendable) = (currentUser,form,messages,availableRoles,assets) => apply(currentUser,form,messages,availableRoles,assets)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  DATE: Tue Oct 27 21:59:17 PDT 2020
                  SOURCE: C:/Users/Noel/Documents/csc402/lemur-femr/femr/app/femr/ui/views/admin/users/create.scala.html
                  HASH: 0b6f35c2c0b45c08e24fce34ae6fd3326b7755ef
                  MATRIX: 1245->1|1589->277|1638->321|1708->386|1756->428|1825->575|1850->591|1931->595|1968->605|2025->635|2040->641|2089->669|2121->685|2147->702|2228->706|2265->716|2329->753|2344->759|2391->785|2432->810|2459->828|2540->832|2573->839|2613->863|2653->865|2690->875|2742->900|2770->907|2811->918|2850->490|2872->503|2965->272|2996->485|3027->566|3062->678|3095->803|3128->925|3159->930|3302->1063|3343->1065|3376->1071|3430->1099|3445->1105|3551->1201|3592->1203|3632->1216|3647->1222|3858->1412|3898->1425|3913->1431|4121->1618|4150->1620|4231->1674|4246->1680|4460->1873|4500->1886|4515->1892|4724->2080|4764->2093|4779->2099|4986->2285|5026->2298|5041->2304|5247->2489|5286->2501|5477->2665|5527->2699|5567->2701|5613->2719|5669->2748|5683->2753|5712->2761|5762->2780|5806->2796|5853->2816|5896->2843|5936->2845|5982->2863|6108->2962|6133->2966|6164->2970|6189->2974|6235->2992|6312->3038|6349->3048|6495->3164
                  LINES: 32->1|39->7|40->8|41->9|42->10|44->15|44->15|46->15|47->16|47->16|47->16|47->16|48->18|48->18|50->18|51->19|51->19|51->19|51->19|52->21|52->21|54->21|55->22|55->22|55->22|56->23|56->23|56->23|57->24|58->11|58->11|61->5|63->11|63->13|65->17|66->20|67->25|69->27|69->27|69->27|70->28|71->29|71->29|71->29|71->29|73->31|73->31|78->36|80->38|80->38|85->43|86->44|88->46|88->46|93->51|95->53|95->53|100->58|102->60|102->60|107->65|109->67|109->67|114->72|116->74|119->77|119->77|119->77|120->78|120->78|120->78|120->78|121->79|123->81|124->82|124->82|124->82|125->83|126->84|126->84|126->84|126->84|127->85|129->87|130->88|133->91
                  -- GENERATED --
              */
          