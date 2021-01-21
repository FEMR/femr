
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


Seq[Any](format.raw/*2.1*/("""
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
                  DATE: Wed Jan 20 18:29:28 PST 2021
                  SOURCE: /Users/noel/Desktop/CPE/femr/femr/app/femr/ui/views/partials/admin/inventory/inventoryMenu.scala.html
                  HASH: 8041cde38d384727c79eb75f6248c2898c3bdf63
                  MATRIX: 1017->1|1143->57|1210->118|1293->55|1320->173|1347->175|1382->202|1421->204|1452->209|1751->481|1774->495|1805->505|1868->551|1880->556|1918->557|1950->562|2027->612|2055->631|2111->666|2302->830|2330->849|2384->882|2612->1083|2636->1098|2678->1119|2718->1129
                  LINES: 28->1|31->3|32->4|35->2|36->5|37->6|37->6|37->6|38->7|44->13|44->13|44->13|47->16|47->16|47->16|48->17|49->18|49->18|49->18|51->20|51->20|51->20|55->24|55->24|55->24|56->25
                  -- GENERATED --
              */
          