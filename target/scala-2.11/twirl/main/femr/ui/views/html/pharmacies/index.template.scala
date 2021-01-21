
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


Seq[Any](format.raw/*2.1*/("""
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
                  DATE: Wed Jan 20 18:29:28 PST 2021
                  SOURCE: /Users/noel/Desktop/CPE/femr/femr/app/femr/ui/views/pharmacies/index.scala.html
                  HASH: d3fc0e1761a63a2766da84cc76835bfa5bea2267
                  MATRIX: 1037->1|1232->126|1294->182|1340->222|1412->124|1439->266|1467->269|1521->315|1560->317|1591->322|1646->351|1661->357|1718->405|1758->407|1795->417|1857->458|1893->464|1925->469|1963->477
                  LINES: 28->1|31->3|32->4|33->5|36->2|37->6|39->8|39->8|39->8|40->9|41->10|41->10|41->10|41->10|42->11|42->11|43->12|44->13|45->14
                  -- GENERATED --
              */
          