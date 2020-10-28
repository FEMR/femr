
package femr.ui.views.html.manager

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

object index extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template3[femr.common.dtos.CurrentUser,femr.ui.models.manager.IndexViewModelGet,AssetsFinder,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(currentUser: femr.common.dtos.CurrentUser, viewModelGet: femr.ui.models.manager.IndexViewModelGet, assets: AssetsFinder):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {
/*3.2*/import femr.ui.views.html.layouts.main
/*4.2*/import femr.ui.controllers.admin.routes.TripController
/*5.2*/import femr.ui.controllers.routes.HistoryController

def /*7.2*/additionalStyles/*7.18*/:play.twirl.api.HtmlFormat.Appendable = {_display_(

Seq[Any](format.raw/*7.22*/("""

    """),format.raw/*9.5*/("""<link rel="stylesheet" href=""""),_display_(/*9.35*/assets/*9.41*/.path("css/manager/manager.css")),format.raw/*9.73*/("""">
""")))};
Seq[Any](format.raw/*1.123*/("""

"""),format.raw/*6.1*/("""
"""),format.raw/*10.2*/("""

"""),_display_(/*12.2*/main("ManagerTab", currentUser, styles = additionalStyles, assets = assets)/*12.77*/ {_display_(Seq[Any](format.raw/*12.79*/("""
    """),format.raw/*13.5*/("""<div class="backgroundForWrap">


        <h3>Overview of patients checked in today: """),_display_(/*16.53*/viewModelGet/*16.65*/.getUserFriendlyDate),format.raw/*16.85*/(""" """),format.raw/*16.86*/("""</h3>

        """),_display_(/*18.10*/if(currentUser.getTripId != null)/*18.43*/ {_display_(Seq[Any](format.raw/*18.45*/("""
            """),format.raw/*19.13*/("""<p>Current Trip: """),_display_(/*19.31*/viewModelGet/*19.43*/.getUserFriendlyTrip),format.raw/*19.63*/("""</p>


        <div id="table-container center-panel-grid" >
            <div class="managerWrap">


                <table id="managersTable">
                    <thead>
                        <tr>
                            <th> Patient ID </th>
                            <th> Name </th>
                            <th> Patient Information </th>
                            <th> Chief Complaint </th>
                            <th> Triage Check-In Time </th>
                            <th> Medical Check-In Time </th>
                            <th> Pharmacy Check-In Time </th>
                            <th> Total Time Spent </th>
                        </tr>
                    </thead>
                    <tbody>

                        """),_display_(/*41.26*/for(encounterItem <- viewModelGet.getEncounter) yield /*41.73*/ {_display_(Seq[Any](format.raw/*41.75*/("""

                            """),format.raw/*43.29*/("""<tr class="form-history">
                                <td><a href=""""),_display_(/*44.47*/HistoryController/*44.64*/.indexEncounterGet(encounterItem.getId)),format.raw/*44.103*/("""">"""),_display_(/*44.106*/encounterItem/*44.119*/.getPatientItem.getId),format.raw/*44.140*/("""</a></td>
                                <td>"""),_display_(/*45.38*/encounterItem/*45.51*/.getPatientItem.getFirstName),format.raw/*45.79*/(""" """),_display_(/*45.81*/encounterItem/*45.94*/.getPatientItem.getLastName),format.raw/*45.121*/("""</td>
                                <td>
                                    """),_display_(/*47.38*/if(encounterItem.getPatientItem.getSex != null)/*47.85*/ {_display_(Seq[Any](format.raw/*47.87*/("""
                                        """),format.raw/*48.41*/("""<strong>Gender: </strong>
                                        """),_display_(/*49.42*/encounterItem/*49.55*/.getPatientItem.getSex),format.raw/*49.77*/(""" """),format.raw/*49.78*/("""<br>
                                    """)))}),format.raw/*50.38*/("""
                                    """),_display_(/*51.38*/if(encounterItem.getPatientItem.getAge() != null)/*51.87*/ {_display_(Seq[Any](format.raw/*51.89*/("""
                                        """),format.raw/*52.41*/("""<strong>Age: </strong>
                                        """),_display_(/*53.42*/encounterItem/*53.55*/.getPatientItem.getAge),format.raw/*53.77*/(""" """),format.raw/*53.78*/("""<br>
                                    """)))}),format.raw/*54.38*/("""
                                    """),_display_(/*55.38*/if(encounterItem.getPatientItem.getCity() != null)/*55.88*/ {_display_(Seq[Any](format.raw/*55.90*/("""
                                        """),format.raw/*56.41*/("""<strong>City: </strong>
                                        """),_display_(/*57.42*/encounterItem/*57.55*/.getPatientItem.getCity),format.raw/*57.78*/(""" """),format.raw/*57.79*/("""<br>
                                        """)))}),format.raw/*58.42*/("""
                                """),format.raw/*59.33*/("""</td>
                                <td>"""),_display_(/*60.38*/encounterItem/*60.51*/.getChiefComplaints),format.raw/*60.70*/(""" """),format.raw/*60.71*/("""</td>

                                """),_display_(/*62.34*/if(encounterItem.getTriageDateOfVisit != null)/*62.80*/ {_display_(Seq[Any](format.raw/*62.82*/("""
                                    """),format.raw/*63.37*/("""<td>"""),_display_(/*63.42*/encounterItem/*63.55*/.getTriageDateOfVisit),format.raw/*63.76*/("""</td>
                                """)))}),format.raw/*64.34*/("""
                                """),_display_(/*65.34*/if(encounterItem.getMedicalDateOfVisit != null)/*65.81*/ {_display_(Seq[Any](format.raw/*65.83*/("""
                                    """),format.raw/*66.37*/("""<td>"""),_display_(/*66.42*/encounterItem/*66.55*/.getMedicalDateOfVisit),format.raw/*66.77*/("""</td>
                                """)))}),format.raw/*67.34*/("""
                                """),_display_(/*68.34*/if(encounterItem.getPharmacyDateOfVisit != null)/*68.82*/ {_display_(Seq[Any](format.raw/*68.84*/("""
                                    """),format.raw/*69.37*/("""<td>"""),_display_(/*69.42*/encounterItem/*69.55*/.getPharmacyDateOfVisit),format.raw/*69.78*/("""</td>
                                """)))}),format.raw/*70.34*/("""
                                """),format.raw/*71.33*/("""<td>"""),_display_(/*71.38*/encounterItem/*71.51*/.getTurnAroundTime),format.raw/*71.69*/("""</td>
                            </tr>
                        """)))}),format.raw/*73.26*/("""
                    """),format.raw/*74.21*/("""</tbody>
                </table>
            </div>
        </div>
        """)))}/*78.11*/else/*78.16*/{_display_(Seq[Any](format.raw/*78.17*/("""
            """),format.raw/*79.13*/("""<h4>"""),_display_(/*79.18*/currentUser/*79.29*/.getFirstName),format.raw/*79.42*/(""" """),_display_(/*79.44*/currentUser/*79.55*/.getLastName),format.raw/*79.67*/("""
                """),format.raw/*80.17*/("""is not currently assigned to a trip. To manage trips please have an Admin visit the <a href=""""),_display_(/*80.111*/TripController/*80.125*/.manageGet()),format.raw/*80.137*/("""">Trip page</a></h4>
        """)))}),format.raw/*81.10*/("""
    """),format.raw/*82.5*/("""</div>
""")))}),format.raw/*83.2*/("""
"""))
      }
    }
  }

  def render(currentUser:femr.common.dtos.CurrentUser,viewModelGet:femr.ui.models.manager.IndexViewModelGet,assets:AssetsFinder): play.twirl.api.HtmlFormat.Appendable = apply(currentUser,viewModelGet,assets)

  def f:((femr.common.dtos.CurrentUser,femr.ui.models.manager.IndexViewModelGet,AssetsFinder) => play.twirl.api.HtmlFormat.Appendable) = (currentUser,viewModelGet,assets) => apply(currentUser,viewModelGet,assets)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  DATE: Tue Oct 27 21:59:18 PDT 2020
                  SOURCE: C:/Users/Noel/Documents/csc402/lemur-femr/femr/app/femr/ui/views/manager/index.scala.html
                  HASH: d0260a7ebcfaa0074e2d63c630a30ef609f8d8a9
                  MATRIX: 1040->1|1234->127|1280->168|1342->225|1406->281|1430->297|1510->301|1544->309|1600->339|1614->345|1666->377|1711->122|1741->278|1770->382|1801->387|1885->462|1925->464|1958->470|2074->559|2095->571|2136->591|2165->592|2210->610|2252->643|2292->645|2334->659|2379->677|2400->689|2441->709|3251->1492|3314->1539|3354->1541|3414->1573|3514->1646|3540->1663|3601->1702|3632->1705|3655->1718|3698->1739|3773->1787|3795->1800|3844->1828|3873->1830|3895->1843|3944->1870|4053->1952|4109->1999|4149->2001|4219->2043|4314->2111|4336->2124|4379->2146|4408->2147|4482->2190|4548->2229|4606->2278|4646->2280|4716->2322|4808->2387|4830->2400|4873->2422|4902->2423|4976->2466|5042->2505|5101->2555|5141->2557|5211->2599|5304->2665|5326->2678|5370->2701|5399->2702|5477->2749|5539->2783|5610->2827|5632->2840|5672->2859|5701->2860|5770->2902|5825->2948|5865->2950|5931->2988|5963->2993|5985->3006|6027->3027|6098->3067|6160->3102|6216->3149|6256->3151|6322->3189|6354->3194|6376->3207|6419->3229|6490->3269|6552->3304|6609->3352|6649->3354|6715->3392|6747->3397|6769->3410|6813->3433|6884->3473|6946->3507|6978->3512|7000->3525|7039->3543|7137->3610|7187->3632|7287->3714|7300->3719|7339->3720|7381->3734|7413->3739|7433->3750|7467->3763|7496->3765|7516->3776|7549->3788|7595->3806|7717->3900|7741->3914|7775->3926|7837->3957|7870->3963|7909->3972
                  LINES: 28->1|31->3|32->4|33->5|35->7|35->7|37->7|39->9|39->9|39->9|39->9|41->1|43->6|44->10|46->12|46->12|46->12|47->13|50->16|50->16|50->16|50->16|52->18|52->18|52->18|53->19|53->19|53->19|53->19|75->41|75->41|75->41|77->43|78->44|78->44|78->44|78->44|78->44|78->44|79->45|79->45|79->45|79->45|79->45|79->45|81->47|81->47|81->47|82->48|83->49|83->49|83->49|83->49|84->50|85->51|85->51|85->51|86->52|87->53|87->53|87->53|87->53|88->54|89->55|89->55|89->55|90->56|91->57|91->57|91->57|91->57|92->58|93->59|94->60|94->60|94->60|94->60|96->62|96->62|96->62|97->63|97->63|97->63|97->63|98->64|99->65|99->65|99->65|100->66|100->66|100->66|100->66|101->67|102->68|102->68|102->68|103->69|103->69|103->69|103->69|104->70|105->71|105->71|105->71|105->71|107->73|108->74|112->78|112->78|112->78|113->79|113->79|113->79|113->79|113->79|113->79|113->79|114->80|114->80|114->80|114->80|115->81|116->82|117->83
                  -- GENERATED --
              */
          