
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
Seq[Any](format.raw/*2.1*/("""
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
                  DATE: Wed Jan 20 18:29:26 PST 2021
                  SOURCE: /Users/noel/Desktop/CPE/femr/femr/app/femr/ui/views/manager/index.scala.html
                  HASH: ca2620b6a94b645da388a28cb6d90fa8ad6acbe9
                  MATRIX: 1040->1|1234->125|1280->165|1342->221|1406->275|1430->291|1510->295|1542->301|1598->331|1612->337|1664->369|1706->123|1733->273|1761->373|1790->376|1874->451|1914->453|1946->458|2059->544|2080->556|2121->576|2150->577|2193->593|2235->626|2275->628|2316->641|2361->659|2382->671|2423->691|3211->1452|3274->1499|3314->1501|3372->1531|3471->1603|3497->1620|3558->1659|3589->1662|3612->1675|3655->1696|3729->1743|3751->1756|3800->1784|3829->1786|3851->1799|3900->1826|4007->1906|4063->1953|4103->1955|4172->1996|4266->2063|4288->2076|4331->2098|4360->2099|4433->2141|4498->2179|4556->2228|4596->2230|4665->2271|4756->2335|4778->2348|4821->2370|4850->2371|4923->2413|4988->2451|5047->2501|5087->2503|5156->2544|5248->2609|5270->2622|5314->2645|5343->2646|5420->2692|5481->2725|5551->2768|5573->2781|5613->2800|5642->2801|5709->2841|5764->2887|5804->2889|5869->2926|5901->2931|5923->2944|5965->2965|6035->3004|6096->3038|6152->3085|6192->3087|6257->3124|6289->3129|6311->3142|6354->3164|6424->3203|6485->3237|6542->3285|6582->3287|6647->3324|6679->3329|6701->3342|6745->3365|6815->3404|6876->3437|6908->3442|6930->3455|6969->3473|7065->3538|7114->3559|7210->3637|7223->3642|7262->3643|7303->3656|7335->3661|7355->3672|7389->3685|7418->3687|7438->3698|7471->3710|7516->3727|7638->3821|7662->3835|7696->3847|7757->3877|7789->3882|7827->3890
                  LINES: 28->1|31->3|32->4|33->5|35->7|35->7|37->7|39->9|39->9|39->9|39->9|41->2|42->6|43->10|45->12|45->12|45->12|46->13|49->16|49->16|49->16|49->16|51->18|51->18|51->18|52->19|52->19|52->19|52->19|74->41|74->41|74->41|76->43|77->44|77->44|77->44|77->44|77->44|77->44|78->45|78->45|78->45|78->45|78->45|78->45|80->47|80->47|80->47|81->48|82->49|82->49|82->49|82->49|83->50|84->51|84->51|84->51|85->52|86->53|86->53|86->53|86->53|87->54|88->55|88->55|88->55|89->56|90->57|90->57|90->57|90->57|91->58|92->59|93->60|93->60|93->60|93->60|95->62|95->62|95->62|96->63|96->63|96->63|96->63|97->64|98->65|98->65|98->65|99->66|99->66|99->66|99->66|100->67|101->68|101->68|101->68|102->69|102->69|102->69|102->69|103->70|104->71|104->71|104->71|104->71|106->73|107->74|111->78|111->78|111->78|112->79|112->79|112->79|112->79|112->79|112->79|112->79|113->80|113->80|113->80|113->80|114->81|115->82|116->83
                  -- GENERATED --
              */
          