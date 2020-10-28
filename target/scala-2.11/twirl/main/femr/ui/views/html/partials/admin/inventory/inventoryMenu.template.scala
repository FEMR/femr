
package femr.ui.views.html.partials.admin.inventory

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

object inventoryMenu extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template1[femr.common.models.MissionTripItem,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(missionTripItem: femr.common.models.MissionTripItem):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {
/*3.2*/import femr.ui.controllers.admin.routes.InventoryController
/*4.2*/import femr.ui.controllers.admin.routes.TripController


Seq[Any](format.raw/*1.55*/("""

"""),format.raw/*5.1*/("""
"""),_display_(/*6.2*/if(missionTripItem == null)/*6.29*/ {_display_(Seq[Any](format.raw/*6.31*/("""
    """),format.raw/*7.5*/("""<div class="alert warning">

        <h3>Your account is not assigned to a trip</h3>

        <p>In order to manage your formulary, you must be assigned to a trip. Please assign yourself to a trip before using the inventory feature.</p>

        <a class="fButton" href=""""),_display_(/*13.35*/TripController/*13.49*/.manageGet),format.raw/*13.59*/("""">Manage Trip Users &raquo;</a>
    </div>

""")))}/*16.3*/else/*16.8*/{_display_(Seq[Any](format.raw/*16.9*/("""
    """),format.raw/*17.5*/("""<div id="inventoryOptionsWrap">
        <a href=""""),_display_(/*18.19*/InventoryController/*18.38*/.existingGet(missionTripItem.getId)),format.raw/*18.73*/("""" class="fButton fOtherButton fAdminButton medicationBtn"><span class="glyphicon glyphicon-plus-sign"></span>
            Existing Medication</a>
        <a href=""""),_display_(/*20.19*/InventoryController/*20.38*/.customGet(missionTripItem.getId)),format.raw/*20.71*/("""" class="fButton fOtherButton fAdminButton medicationBtn"><span class="glyphicon glyphicon-plus-sign"></span>
            Custom Medication</a>
    </div>

    <p>You are viewing the formulary for <b>"""),_display_(/*24.46*/missionTripItem/*24.61*/.getFriendlyTripTitle),format.raw/*24.82*/("""</b></p>
""")))}),format.raw/*25.2*/("""

"""))
      }
    }
  }

  def render(missionTripItem:femr.common.models.MissionTripItem): play.twirl.api.HtmlFormat.Appendable = apply(missionTripItem)

  def f:((femr.common.models.MissionTripItem) => play.twirl.api.HtmlFormat.Appendable) = (missionTripItem) => apply(missionTripItem)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  DATE: Tue Oct 27 21:59:18 PDT 2020
                  SOURCE: C:/Users/Noel/Documents/csc402/lemur-femr/femr/app/femr/ui/views/partials/admin/inventory/inventoryMenu.scala.html
                  HASH: 5a07f3e959373faf647bc892df6f912cbf8590bb
                  MATRIX: 1017->1|1143->59|1210->121|1294->54|1324->177|1352->180|1387->207|1426->209|1458->215|1763->493|1786->507|1817->517|1883->566|1895->571|1933->572|1966->578|2044->629|2072->648|2128->683|2321->849|2349->868|2403->901|2635->1106|2659->1121|2701->1142|2742->1153
                  LINES: 28->1|31->3|32->4|35->1|37->5|38->6|38->6|38->6|39->7|45->13|45->13|45->13|48->16|48->16|48->16|49->17|50->18|50->18|50->18|52->20|52->20|52->20|56->24|56->24|56->24|57->25
                  -- GENERATED --
              */
          