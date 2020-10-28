
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
Seq[Any](format.raw/*5.30*/("""

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
                  DATE: Tue Oct 27 21:59:18 PDT 2020
                  SOURCE: C:/Users/Noel/Documents/csc402/lemur-femr/femr/app/femr/ui/views/admin/users/edit.scala.html
                  HASH: 1aa1e830c32d17d95e1a644452c276b979cf3347
                  MATRIX: 1241->1|1583->275|1632->319|1672->354|1712->388|1762->432|1833->497|1881->539|1950->686|1975->702|2056->706|2093->716|2150->746|2165->752|2234->800|2303->842|2318->848|2367->876|2399->892|2425->909|2506->913|2543->923|2609->962|2624->968|2692->1014|2777->1072|2792->1078|2839->1104|2880->1129|2907->1147|2988->1151|3021->1158|3061->1182|3101->1184|3138->1194|3190->1219|3218->1226|3259->1237|3298->603|3320->616|3413->270|3444->596|3477->679|3510->885|3543->1122|3576->1244|3607->1249|3751->1383|3792->1385|3825->1391|3881->1421|3896->1427|4018->1539|4059->1541|4096->1551|4152->1580|4175->1594|4211->1609|4330->1701|4345->1707|4529->1870|4573->1887|4588->1893|4731->2015|4775->2032|4790->2038|4931->2158|4975->2175|4990->2181|5124->2294|5168->2310|5241->2356|5343->2448|5384->2450|5430->2468|5527->2547|5540->2552|5579->2553|5625->2571|5716->2631|5758->2645|6029->2889|6044->2895|6268->3098|6310->3113|6325->3119|6562->3335|6604->3349|7082->3800|7097->3806|7148->3848|7196->3858|7250->3884|7316->3923|7384->3982|7423->3983|7452->3985|7500->4023|7541->4039|7608->4079|7679->4141|7718->4142|7747->4144|7798->4185|7839->4201|7906->4241|7988->4313|8028->4314|8058->4316|8119->4367|8160->4383|8253->4444|8300->4462|8628->4762|8679->4796|8720->4798|8775->4824|8832->4853|8847->4858|8877->4866|8936->4893|8987->4915|9296->5196|9352->5235|9393->5237|9448->5263|9505->5292|9529->5306|9565->5320|9629->5352|9680->5374|9821->5487|9837->5493|9878->5524|9927->5534|9978->5556|10042->5592|10056->5596|10084->5602|10205->5695|10219->5699|10247->5705|10306->5735|10454->5855|10468->5859|10496->5865|10577->5914|10628->5936|10943->6220
                  LINES: 32->1|39->7|40->8|41->9|42->10|43->11|44->12|45->13|47->18|47->18|49->18|50->19|50->19|50->19|50->19|51->20|51->20|51->20|52->22|52->22|54->22|55->23|55->23|55->23|55->23|56->24|56->24|56->24|57->26|57->26|59->26|60->27|60->27|60->27|61->28|61->28|61->28|62->29|63->15|63->15|66->5|68->14|69->17|70->21|71->25|72->30|74->32|74->32|74->32|75->33|76->34|76->34|76->34|76->34|77->35|77->35|77->35|77->35|82->40|82->40|86->44|88->46|88->46|91->49|93->51|93->51|96->54|98->56|98->56|101->59|103->61|104->62|104->62|104->62|105->63|106->64|106->64|106->64|107->65|108->66|109->67|113->71|113->71|118->76|119->77|119->77|124->82|125->83|136->94|136->94|136->94|136->94|137->95|138->96|138->96|138->96|138->96|138->96|138->96|139->97|139->97|139->97|139->97|139->97|139->97|140->98|140->98|140->98|140->98|140->98|140->98|143->101|144->102|156->114|156->114|156->114|157->115|157->115|157->115|157->115|158->116|159->117|163->121|163->121|163->121|164->122|164->122|164->122|164->122|165->123|166->124|172->130|172->130|172->130|172->130|173->131|173->131|173->131|173->131|175->133|175->133|175->133|176->134|177->135|177->135|177->135|179->137|182->140|191->149
                  -- GENERATED --
              */
          