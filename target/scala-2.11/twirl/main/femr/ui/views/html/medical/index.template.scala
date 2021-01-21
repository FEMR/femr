
package femr.ui.views.html.medical

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
/*3.2*/import femr.ui.controllers.routes.MedicalController
/*4.2*/import femr.ui.views.html.layouts.main
/*5.2*/import femr.ui.views.html.partials.shared._


Seq[Any](format.raw/*2.1*/("""
"""),format.raw/*6.1*/("""

"""),_display_(/*8.2*/main("Medical", currentUser, assets = assets)/*8.47*/ {_display_(Seq[Any](format.raw/*8.49*/("""
    """),format.raw/*9.5*/("""<div class="container">
    """),_display_(/*10.6*/helper/*10.12*/.form(action = MedicalController.indexPost())/*10.57*/ {_display_(Seq[Any](format.raw/*10.59*/("""
        """),_display_(/*11.10*/searchBox("Medical", patientId, message)),format.raw/*11.50*/("""
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
                  DATE: Wed Jan 20 18:29:26 PST 2021
                  SOURCE: /Users/noel/Desktop/CPE/femr/femr/app/femr/ui/views/medical/index.scala.html
                  HASH: 049d184350e791cfd19fc6470991062836e40779
                  MATRIX: 1034->1|1229->126|1288->179|1334->219|1406->124|1433->263|1461->266|1514->311|1553->313|1584->318|1639->347|1654->353|1708->398|1748->400|1785->410|1846->450|1882->456|1914->461|1952->469
                  LINES: 28->1|31->3|32->4|33->5|36->2|37->6|39->8|39->8|39->8|40->9|41->10|41->10|41->10|41->10|42->11|42->11|43->12|44->13|45->14
                  -- GENERATED --
              */
          