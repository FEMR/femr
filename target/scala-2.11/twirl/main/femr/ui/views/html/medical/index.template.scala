
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


Seq[Any](format.raw/*1.124*/("""

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
                  DATE: Tue Oct 27 21:59:18 PDT 2020
                  SOURCE: C:/Users/Noel/Documents/csc402/lemur-femr/femr/app/femr/ui/views/medical/index.scala.html
                  HASH: 03a70500c21f6049356107869010be77609a629b
                  MATRIX: 1034->1|1229->128|1288->182|1334->223|1408->123|1438->268|1468->273|1521->318|1560->320|1592->326|1648->356|1663->362|1717->407|1757->409|1795->420|1856->460|1893->467|1926->473|1965->482
                  LINES: 28->1|31->3|32->4|33->5|36->1|38->6|40->8|40->8|40->8|41->9|42->10|42->10|42->10|42->10|43->11|43->11|44->12|45->13|46->14
                  -- GENERATED --
              */
          