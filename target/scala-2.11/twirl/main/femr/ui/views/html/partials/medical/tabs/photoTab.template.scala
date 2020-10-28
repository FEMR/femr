
package femr.ui.views.html.partials.medical.tabs

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

object photoTab extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template1[List[femr.common.models.PhotoItem],play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(photos: List[femr.common.models.PhotoItem]):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*1.46*/("""

"""),format.raw/*3.1*/("""<div class="controlWrap hidden" id="photosControl">
    <div class="form-group">
        <label for="btnAddPhoto" class="control-label">Add Photo:</label>
        <div id="photoInputContainer">
            <input type="file" class="form-control" onchange="imageInputChange ( this )" placeholder="Choose Image" />
        </div>
    </div>
    <div id="patientImageList" class="row">
    """),_display_(/*11.6*/for(photoRec <- photos) yield /*11.29*/ {_display_(Seq[Any](format.raw/*11.31*/("""
        """),format.raw/*12.9*/("""<div class="col-xs-12 col-sm-6 col-md-4">
            <div class="thumbnail">
                <img src=""""),_display_(/*14.28*/photoRec/*14.36*/.getImageUrl),format.raw/*14.48*/("""">
                <div class="caption">
                    <div class="form-group">
                        <p name="photoDescription" >"""),_display_(/*17.54*/photoRec/*17.62*/.getImageDesc),format.raw/*17.75*/("""</p>
                        <div class="btn-group">
                            <button id="btnEditText" type="button" onclick="portraitEdit ( this )" class="btn btn-default btn-med">
                                <span class="glyphicon glyphicon-edit"></span> Edit Description
                            </button>
                        </div>
                        <div class="btn-group">
                            <button id="btnDeletePhoto" type="button" onclick="portraitDelete ( this )" class="btn btn-danger btn-med">
                                <span class="glyphicon glyphicon-trash"></span> Delete
                            </button>
                        </div>
                        <p>"""),_display_(/*28.29*/photoRec/*28.37*/.getImageDate),format.raw/*28.50*/("""</p>
                    </div>
                </div>
            </div>
                <!-- Data elements for server-side logic -->
            <div name="dataList" hidden="true">
                    <!-- Signals that user has requested to delete this photo from the database / server -->
                <input hidden="true" type="checkbox" name="deleteRequested" checked="false" value="false" />
                    <!-- Signals that the user has updated a description -->
                <input hidden="true" type="checkbox" name="hasUpdatedDesc" checked="false" value="false" />
                    <!-- Place holder for new images -->
                <input hidden="true" type="file" name="patientPhoto" />
                    <!-- text for POST -->
                <input hidden="true" type="text" name="imageDescText" value=""""),_display_(/*41.79*/photoRec/*41.87*/.getImageDesc),format.raw/*41.100*/("""" />
                    <!-- photo Id -->
                <input hidden="true" type="text" name="photoId" value=""""),_display_(/*43.73*/photoRec/*43.81*/.getId.toString),format.raw/*43.96*/("""" />
            </div>
        </div>
    """)))}),format.raw/*46.6*/("""

    """),format.raw/*48.5*/("""</div>
</div>"""))
      }
    }
  }

  def render(photos:List[femr.common.models.PhotoItem]): play.twirl.api.HtmlFormat.Appendable = apply(photos)

  def f:((List[femr.common.models.PhotoItem]) => play.twirl.api.HtmlFormat.Appendable) = (photos) => apply(photos)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  DATE: Tue Oct 27 21:59:18 PDT 2020
                  SOURCE: C:/Users/Noel/Documents/csc402/lemur-femr/femr/app/femr/ui/views/partials/medical/tabs/photoTab.scala.html
                  HASH: f9dde8de731511d0c4ab48fac5b7113936aa687c
                  MATRIX: 1009->1|1148->45|1178->49|1600->445|1639->468|1679->470|1716->480|1850->587|1867->595|1900->607|2069->749|2086->757|2120->770|2876->1499|2893->1507|2927->1520|3803->2369|3820->2377|3855->2390|3999->2507|4016->2515|4052->2530|4129->2577|4164->2585
                  LINES: 28->1|33->1|35->3|43->11|43->11|43->11|44->12|46->14|46->14|46->14|49->17|49->17|49->17|60->28|60->28|60->28|73->41|73->41|73->41|75->43|75->43|75->43|78->46|80->48
                  -- GENERATED --
              */
          