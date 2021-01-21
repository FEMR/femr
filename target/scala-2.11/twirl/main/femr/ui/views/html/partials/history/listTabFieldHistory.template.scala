
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


Seq[Any](format.raw/*2.1*/("""


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
                  DATE: Wed Jan 20 18:29:29 PST 2021
                  SOURCE: /Users/noel/Desktop/CPE/femr/femr/app/femr/ui/views/partials/history/listTabFieldHistory.scala.html
                  HASH: 44ed5814dc7a031aeb47d7bd22c1a7332f8080eb
                  MATRIX: 1046->1|1233->95|1262->98|1400->209|1480->273|1520->275|1556->284|1605->306|1625->317|1681->352|1731->375|1751->386|1841->455|1891->478|1911->489|2005->561|2065->590|2096->594
                  LINES: 28->1|33->2|36->5|41->10|41->10|41->10|42->11|43->12|43->12|43->12|44->13|44->13|44->13|45->14|45->14|45->14|47->16|51->20
                  -- GENERATED --
              */
          