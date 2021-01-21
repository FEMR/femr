
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

object edit extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template5[femr.common.dtos.CurrentUser,play.data.Form[femr.ui.models.admin.users.EditViewModel],java.util.List[_$1] forSome { 
   type _$1 >: _root_.scala.Nothing <: java.lang.String
},java.util.List[_$2] forSome { 
   type _$2 >: _root_.scala.Nothing <: java.lang.String
},AssetsFinder,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(currentUser: femr.common.dtos.CurrentUser,
        form: play.data.Form[femr.ui.models.admin.users.EditViewModel],
        availableRoles: java.util.List[_ <: java.lang.String],
        messages: java.util.List[_ <: java.lang.String],
        assets: AssetsFinder):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {
/*7.2*/import femr.common.models.MissionTripItem
/*8.2*/import views.html.helper.options
/*9.2*/import views.html.helper.select
/*10.2*/import views.html.helper.FieldConstructor
/*11.2*/import femr.ui.views.html.partials.admin.inputFieldConstructor
/*12.2*/import femr.ui.views.html.layouts.admin
/*13.2*/import femr.ui.controllers.admin.routes.UsersController

def /*18.6*/additionalStyles/*18.22*/:play.twirl.api.HtmlFormat.Appendable = {_display_(

Seq[Any](format.raw/*18.26*/("""
        """),format.raw/*19.9*/("""<link rel="stylesheet" href=""""),_display_(/*19.39*/assets/*19.45*/.path("css/libraries/jquery.dataTables.min.css")),format.raw/*19.93*/("""">
        <link rel="stylesheet" href=""""),_display_(/*20.39*/assets/*20.45*/.path("css/admin/users.css")),format.raw/*20.73*/("""">
    """)))};def /*22.6*/additionalScripts/*22.23*/:play.twirl.api.HtmlFormat.Appendable = {_display_(

Seq[Any](format.raw/*22.27*/("""
        """),format.raw/*23.9*/("""<script type = "text/javascript" src=""""),_display_(/*23.48*/assets/*23.54*/.path("js/libraries/jquery.dataTables.min.js")),format.raw/*23.100*/(""""></script>
        <script type="text/javascript" src=""""),_display_(/*24.46*/assets/*24.52*/.path("js/admin/users.js")),format.raw/*24.78*/(""""></script>
    """)))};def /*26.6*/additionalMessages/*26.24*/:play.twirl.api.HtmlFormat.Appendable = {_display_(

Seq[Any](format.raw/*26.28*/("""
    """),_display_(/*27.6*/for(message <- messages) yield /*27.30*/ {_display_(Seq[Any](format.raw/*27.32*/("""
        """),format.raw/*28.9*/("""<p class="adminMessage">"""),_display_(/*28.34*/message),format.raw/*28.41*/("""</p>
    """)))}),format.raw/*29.6*/("""
    """)))};implicit def /*15.6*/implicitField/*15.19*/ = {{
        FieldConstructor(inputFieldConstructor.f)
    }};
Seq[Any](format.raw/*6.1*/("""
"""),format.raw/*14.1*/("""
    """),format.raw/*17.6*/("""
    """),format.raw/*21.6*/("""
    """),format.raw/*25.6*/("""
    """),format.raw/*30.6*/("""

"""),_display_(/*32.2*/admin("Edit User", currentUser, styles = additionalStyles, scripts = additionalScripts, message = additionalMessages, assets = assets)/*32.136*/ {_display_(Seq[Any](format.raw/*32.138*/("""
    """),format.raw/*33.5*/("""<div class="container">
    """),_display_(/*34.6*/helper/*34.12*/.form(action = UsersController.editPost(Integer.parseInt(form("userId").getValue.get())), 'name -> "createForm")/*34.124*/ {_display_(Seq[Any](format.raw/*34.126*/("""
        """),format.raw/*35.9*/("""<input type="hidden" value=""""),_display_(/*35.38*/form("userId")/*35.52*/.getValue.get()),format.raw/*35.67*/("""" name="userId"/>


        <div class="editWrapBoth" id="editWrapLeft">

            """),_display_(/*40.14*/helper/*40.20*/.inputText(form("email"),
                'class -> "fInput",
                '_label -> "Email Address",
                'readonly -> "readonly"
            )),format.raw/*44.14*/("""

            """),_display_(/*46.14*/helper/*46.20*/.inputText(form("firstName"),
                'class -> "fInput",
                '_label -> "First Name"
            )),format.raw/*49.14*/("""

            """),_display_(/*51.14*/helper/*51.20*/.inputText(form("lastName"),
                'class -> "fInput",
                '_label -> "Last Name"
            )),format.raw/*54.14*/("""

            """),_display_(/*56.14*/helper/*56.20*/.inputText(form("notes"),
                'class -> "fInput",
                '_label -> "About"
            )),format.raw/*59.14*/("""

            """),format.raw/*61.13*/("""<label>Password Reset?*</label>
            """),_display_(/*62.14*/if(form("passwordReset").getValue != null && form("passwordReset").getValue.get() == "true")/*62.106*/ {_display_(Seq[Any](format.raw/*62.108*/("""
                """),format.raw/*63.17*/("""<input type="checkbox" name="passwordReset" checked="checked"/>
            """)))}/*64.15*/else/*64.20*/{_display_(Seq[Any](format.raw/*64.21*/("""
                """),format.raw/*65.17*/("""<input type="checkbox" name="passwordReset"/>
            """)))}),format.raw/*66.14*/("""
            """),format.raw/*67.13*/("""<p>*When checked, the user will be prompted to change their password on next login.</p>

            <label>Change User Password**</label>
            <p>**Changes the user's password when filled out. Proceed with caution.</p>
            """),_display_(/*71.14*/helper/*71.20*/.inputText(form("newPassword"),
                'class -> "fInput",
                'placeholder -> "New Password",
                'type -> "password",
                '_label -> null
            )),format.raw/*76.14*/("""
            """),_display_(/*77.14*/helper/*77.20*/.inputText(form("newPasswordVerify"),
                'class -> "fInput",
                'placeholder -> "Verify New Password",
                'type -> "password",
                '_label -> null
            )),format.raw/*82.14*/("""
            """),format.raw/*83.13*/("""<label>Trips</label>
            <p>Trips that this user has attended. Allows the user to view inventory related to that trip.</p>
            <table id="userTripTable">
                <thead>
                    <tr>
                        <th>Organization</th>
                        <th>Country</th>
                        <th>Date</th>
                    </tr>
                </thead>
                <tbody>
                    """),_display_(/*94.22*/helper/*94.28*/.repeat(form("missionTripItems"), min = 0)/*94.70*/ { trip =>_display_(Seq[Any](format.raw/*94.80*/("""
                        """),format.raw/*95.25*/("""<tr>
                            <td>"""),_display_(/*96.34*/if(form(trip.name.toString + ".teamName").getValue != null)/*96.93*/{_display_(Seq[Any](format.raw/*96.94*/(""" """),_display_(/*96.96*/form(trip.name.toString + ".teamName")/*96.134*/.getValue.get())))}),format.raw/*96.150*/("""</td>
                            <td>"""),_display_(/*97.34*/if(form(trip.name.toString + ".tripCountry").getValue != null)/*97.96*/{_display_(Seq[Any](format.raw/*97.97*/(""" """),_display_(/*97.99*/form(trip.name.toString + ".tripCountry")/*97.140*/.getValue.get())))}),format.raw/*97.156*/("""</td>
                            <td>"""),_display_(/*98.34*/if(form(trip.name.toString + ".friendlyTripStartDate").getValue != null)/*98.106*/{_display_(Seq[Any](format.raw/*98.107*/(""" """),_display_(/*98.109*/form(trip.name.toString + ".friendlyTripStartDate")/*98.160*/.getValue.get())))}),format.raw/*98.176*/("""</td>
                        </tr>

                    """)))}),format.raw/*101.22*/("""
                """),format.raw/*102.17*/("""</tbody>
            </table>

        </div>

        <div class="editWrapBoth" id="editWrapRight">
            <div id="roleWrap">
                <label>Roles</label>
                <span class="errors"></span>


                <div class="btn-group width-100">
                    """),_display_(/*114.22*/for(error <- form("roles").errors) yield /*114.56*/ {_display_(Seq[Any](format.raw/*114.58*/("""
                        """),format.raw/*115.25*/("""<p class="createUserError"> """),_display_(/*115.54*/error/*115.59*/.message),format.raw/*115.67*/("""</p>
                    """)))}),format.raw/*116.22*/("""
                    """),format.raw/*117.21*/("""<button type="button" class="btn btn-default dropdown-toggle width-100" data-toggle="dropdown">
                        Add Role <span class="caret"></span>
                    </button>
                    <ul class="dropdown-menu width-100" role="menu">
                    """),_display_(/*121.22*/for(role <- 1 to availableRoles.size()) yield /*121.61*/ {_display_(Seq[Any](format.raw/*121.63*/("""
                        """),format.raw/*122.25*/("""<li class="roleListItem"><a>"""),_display_(/*122.54*/availableRoles/*122.68*/.get(role - 1)),format.raw/*122.82*/("""</a></li>
                    """)))}),format.raw/*123.22*/("""
                    """),format.raw/*124.21*/("""</ul>
                </div>


                <ul class="list-group" id="currentRoles">

                """),_display_(/*130.18*/helper/*130.24*/.repeat(form("roles"), min = 1)/*130.55*/ { role =>_display_(Seq[Any](format.raw/*130.65*/("""
                    """),format.raw/*131.21*/("""<li class="list-group-item" value=""""),_display_(/*131.57*/role/*131.61*/.value),format.raw/*131.67*/("""">
                        <span class="badge roleBadge">X</span>
                        """),_display_(/*133.26*/role/*133.30*/.value),format.raw/*133.36*/("""
                            """),format.raw/*134.29*/("""<!-- stores hidden value for POST -->
                        <input type="text" class="hidden" name="roles[]" value=""""),_display_(/*135.82*/role/*135.86*/.value),format.raw/*135.92*/(""""/>
                    </li>
                """)))}),format.raw/*137.18*/("""


                """),format.raw/*140.17*/("""</ul>
            </div>
        </div>
    </div>
    <div class="btn-group pull-right">
        <button type="submit" class="btn btn-default" id="editUserSubmitBtn">Save</button>
        <a class="btn btn-default" id="cancelBtn" href="/admin/users">Cancel</a>
    </div>

""")))}),format.raw/*149.2*/("""

""")))}))
      }
    }
  }

  def render(currentUser:femr.common.dtos.CurrentUser,form:play.data.Form[femr.ui.models.admin.users.EditViewModel],availableRoles:java.util.List[_$1] forSome { 
   type _$1 >: _root_.scala.Nothing <: java.lang.String
},messages:java.util.List[_$2] forSome { 
   type _$2 >: _root_.scala.Nothing <: java.lang.String
},assets:AssetsFinder): play.twirl.api.HtmlFormat.Appendable = apply(currentUser,form,availableRoles,messages,assets)

  def f:((femr.common.dtos.CurrentUser,play.data.Form[femr.ui.models.admin.users.EditViewModel],java.util.List[_$1] forSome { 
   type _$1 >: _root_.scala.Nothing <: java.lang.String
},java.util.List[_$2] forSome { 
   type _$2 >: _root_.scala.Nothing <: java.lang.String
},AssetsFinder) => play.twirl.api.HtmlFormat.Appendable) = (currentUser,form,availableRoles,messages,assets) => apply(currentUser,form,availableRoles,messages,assets)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  DATE: Wed Jan 20 18:29:25 PST 2021
                  SOURCE: /Users/noel/Desktop/CPE/femr/femr/app/femr/ui/views/admin/users/edit.scala.html
                  HASH: 320be84e670c8d59005a69e1b4aaead26cbd2609
                  MATRIX: 1237->1|1575->269|1624->312|1664->346|1704->379|1754->422|1825->486|1873->527|1942->669|1967->685|2048->689|2084->698|2141->728|2156->734|2225->782|2293->823|2308->829|2357->857|2388->871|2414->888|2495->892|2531->901|2597->940|2612->946|2680->992|2764->1049|2779->1055|2826->1081|2866->1104|2893->1122|2974->1126|3006->1132|3046->1156|3086->1158|3122->1167|3174->1192|3202->1199|3242->1209|3280->589|3302->602|3392->267|3420->583|3452->663|3484->865|3516->1098|3548->1215|3577->1218|3721->1352|3762->1354|3794->1359|3849->1388|3864->1394|3986->1506|4027->1508|4063->1517|4119->1546|4142->1560|4178->1575|4292->1662|4307->1668|4487->1827|4529->1842|4544->1848|4684->1967|4726->1982|4741->1988|4879->2105|4921->2120|4936->2126|5067->2236|5109->2250|5181->2295|5283->2387|5324->2389|5369->2406|5465->2484|5478->2489|5517->2490|5562->2507|5652->2566|5693->2579|5960->2819|5975->2825|6194->3023|6235->3037|6250->3043|6482->3254|6523->3267|6990->3707|7005->3713|7056->3755|7104->3765|7157->3790|7222->3828|7290->3887|7329->3888|7358->3890|7406->3928|7447->3944|7513->3983|7584->4045|7623->4046|7652->4048|7703->4089|7744->4105|7810->4144|7892->4216|7932->4217|7962->4219|8023->4270|8064->4286|8154->4344|8200->4361|8516->4649|8567->4683|8608->4685|8662->4710|8719->4739|8734->4744|8764->4752|8822->4778|8872->4799|9177->5076|9233->5115|9274->5117|9328->5142|9385->5171|9409->5185|9445->5199|9508->5230|9558->5251|9693->5358|9709->5364|9750->5395|9799->5405|9849->5426|9913->5462|9927->5466|9955->5472|10074->5563|10088->5567|10116->5573|10174->5602|10321->5721|10335->5725|10363->5731|10442->5778|10490->5797|10796->6072
                  LINES: 32->1|39->7|40->8|41->9|42->10|43->11|44->12|45->13|47->18|47->18|49->18|50->19|50->19|50->19|50->19|51->20|51->20|51->20|52->22|52->22|54->22|55->23|55->23|55->23|55->23|56->24|56->24|56->24|57->26|57->26|59->26|60->27|60->27|60->27|61->28|61->28|61->28|62->29|63->15|63->15|66->6|67->14|68->17|69->21|70->25|71->30|73->32|73->32|73->32|74->33|75->34|75->34|75->34|75->34|76->35|76->35|76->35|76->35|81->40|81->40|85->44|87->46|87->46|90->49|92->51|92->51|95->54|97->56|97->56|100->59|102->61|103->62|103->62|103->62|104->63|105->64|105->64|105->64|106->65|107->66|108->67|112->71|112->71|117->76|118->77|118->77|123->82|124->83|135->94|135->94|135->94|135->94|136->95|137->96|137->96|137->96|137->96|137->96|137->96|138->97|138->97|138->97|138->97|138->97|138->97|139->98|139->98|139->98|139->98|139->98|139->98|142->101|143->102|155->114|155->114|155->114|156->115|156->115|156->115|156->115|157->116|158->117|162->121|162->121|162->121|163->122|163->122|163->122|163->122|164->123|165->124|171->130|171->130|171->130|171->130|172->131|172->131|172->131|172->131|174->133|174->133|174->133|175->134|176->135|176->135|176->135|178->137|181->140|190->149
                  -- GENERATED --
              */
          