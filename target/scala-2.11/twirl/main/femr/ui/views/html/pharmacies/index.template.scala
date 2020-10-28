
package femr.ui.views.html.pharmacies

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

object index extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template4[femr.common.dtos.CurrentUser,java.lang.String,java.lang.Integer,AssetsFinder,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(currentUser: femr.common.dtos.CurrentUser, message: java.lang.String, patientId: java.lang.Integer, assets: AssetsFinder):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {
/*3.2*/import femr.ui.controllers.routes.PharmaciesController
/*4.2*/import femr.ui.views.html.layouts.main
/*5.2*/import femr.ui.views.html.partials.shared._


Seq[Any](format.raw/*1.124*/("""

"""),format.raw/*6.1*/("""

"""),_display_(/*8.2*/main("Pharmacy", currentUser, assets = assets)/*8.48*/ {_display_(Seq[Any](format.raw/*8.50*/("""
    """),format.raw/*9.5*/("""<div class="container">
    """),_display_(/*10.6*/helper/*10.12*/.form(action = PharmaciesController.indexPost())/*10.60*/ {_display_(Seq[Any](format.raw/*10.62*/("""
        """),_display_(/*11.10*/searchBox("Pharmacy", patientId, message)),format.raw/*11.51*/("""
    """)))}),format.raw/*12.6*/("""
    """),format.raw/*13.5*/("""</div>
""")))}),format.raw/*14.2*/("""
"""))
      }
    }
  }

  def render(currentUser:femr.common.dtos.CurrentUser,message:java.lang.String,patientId:java.lang.Integer,assets:AssetsFinder): play.twirl.api.HtmlFormat.Appendable = apply(currentUser,message,patientId,assets)

  def f:((femr.common.dtos.CurrentUser,java.lang.String,java.lang.Integer,AssetsFinder) => play.twirl.api.HtmlFormat.Appendable) = (currentUser,message,patientId,assets) => apply(currentUser,message,patientId,assets)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  DATE: Tue Oct 27 21:59:18 PDT 2020
                  SOURCE: C:/Users/Noel/Documents/csc402/lemur-femr/femr/app/femr/ui/views/pharmacies/index.scala.html
                  HASH: fac8136e211cd0b3d411d17e417b75d2e837fd4a
                  MATRIX: 1037->1|1232->128|1294->185|1340->226|1414->123|1444->271|1474->276|1528->322|1567->324|1599->330|1655->360|1670->366|1727->414|1767->416|1805->427|1867->468|1904->475|1937->481|1976->490
                  LINES: 28->1|31->3|32->4|33->5|36->1|38->6|40->8|40->8|40->8|41->9|42->10|42->10|42->10|42->10|43->11|43->11|44->12|45->13|46->14
                  -- GENERATED --
              */
          