
package femr.ui.views.html.partials.history

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

object listTabFieldHistory extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template2[java.lang.String,femr.util.DataStructure.Mapping.TabFieldMultiMap,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(tabFieldName:java.lang.String, tabFieldMap:femr.util.DataStructure.Mapping.TabFieldMultiMap):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*1.95*/("""



"""),format.raw/*5.1*/("""<tbody>
    <tr class="first">r
        <th>Date:</th>
        <th>Value:</th>
        <th>User:</th>
        """),_display_(/*10.10*/for(dateIndex <- 1 to tabFieldMap.getDateListChronological.size) yield /*10.74*/ {_display_(Seq[Any](format.raw/*10.76*/("""
        """),format.raw/*11.9*/("""<tr>
            <td>"""),_display_(/*12.18*/tabFieldMap/*12.29*/.getFormatedDateTime(dateIndex - 1)),format.raw/*12.64*/("""</td>
            <td>"""),_display_(/*13.18*/tabFieldMap/*13.29*/.get(tabFieldName, tabFieldMap.getDate(dateIndex - 1), null).getValue),format.raw/*13.98*/("""</td>
            <td>"""),_display_(/*14.18*/tabFieldMap/*14.29*/.get(tabFieldName, tabFieldMap.getDate(dateIndex - 1), null).getUserName),format.raw/*14.101*/("""</td>
        </tr>
        """)))}),format.raw/*16.10*/("""



"""),format.raw/*20.1*/("""</tbody>
"""))
      }
    }
  }

  def render(tabFieldName:java.lang.String,tabFieldMap:femr.util.DataStructure.Mapping.TabFieldMultiMap): play.twirl.api.HtmlFormat.Appendable = apply(tabFieldName,tabFieldMap)

  def f:((java.lang.String,femr.util.DataStructure.Mapping.TabFieldMultiMap) => play.twirl.api.HtmlFormat.Appendable) = (tabFieldName,tabFieldMap) => apply(tabFieldName,tabFieldMap)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  DATE: Tue Oct 27 21:59:18 PDT 2020
                  SOURCE: C:/Users/Noel/Documents/csc402/lemur-femr/femr/app/femr/ui/views/partials/history/listTabFieldHistory.scala.html
                  HASH: 92285b1e38c2434a1683c7b1ba82d7b7dcdcb70d
                  MATRIX: 1046->1|1234->94|1268->102|1411->218|1491->282|1531->284|1568->294|1618->317|1638->328|1694->363|1745->387|1765->398|1855->467|1906->491|1926->502|2020->574|2082->605|2117->613
                  LINES: 28->1|33->1|37->5|42->10|42->10|42->10|43->11|44->12|44->12|44->12|45->13|45->13|45->13|46->14|46->14|46->14|48->16|52->20
                  -- GENERATED --
              */
          