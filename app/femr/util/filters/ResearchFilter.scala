///*
//     fEMR - fast Electronic Medical Records
//     Copyright (C) 2014  Team fEMR
//
//     fEMR is free software: you can redistribute it and/or modify
//     it under the terms of the GNU General Public License as published by
//     the Free Software Foundation, either version 3 of the License, or
//     (at your option) any later version.
//
//     fEMR is distributed in the hope that it will be useful,
//     but WITHOUT ANY WARRANTY; without even the implied warranty of
//     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//     GNU General Public License for more details.
//
//     You should have received a copy of the GNU General Public License
//     along with fEMR.  If not, see <http://www.gnu.org/licenses/>. If
//     you have any questions, contact <info@teamfemr.org>.
//*/
//package femr.util.filters
//
//import femr.ui.controllers.routes
//import play.Play
//import play.api.mvc._
//import scala.concurrent.Future
//
////As of version 2.4, play framework recommends using Scala to implement HttpFilters.
//abstract class ResearchFilter extends Filter {
//
//  override def apply(f: (RequestHeader) => Future[Result])
//                    (rh: RequestHeader): Future[Result] = {
//
//    //get the research only setting from application.conf
//    val researchOnlySetting_String: String = Play.application.configuration.getString("settings.researchOnly")
//    //assume it's 0 for later use
//    var researchOnlySetting_Integer = 0
//
//    try {
//      //try to cast the string setting to an integer
//      researchOnlySetting_Integer = researchOnlySetting_String.toInt
//    } catch {
//      //if setting_string is not castable, set it to 0
//      case e: Exception => researchOnlySetting_Integer = 0
//    }
//
//    if (
//      //only allow a user to enter the research module. If the user does not have a researcher role,
//      //they will be stuck on the homepage.
//      researchOnlySetting_Integer == 1 &&
//        !rh.path.contains("/research") &&
//        !rh.path.contains("/assets") &&
//        !rh.path.contains("/admin") &&
//        !rh.path.contains("/login") &&
//        !rh.path.contains("/logout") &&
//        !rh.path.equals("/")
//    ) {
//
//      Future.successful(Results.Redirect(routes.ResearchController.indexGet()))
//    } else {
//
//      f(rh)
//    }
//  }
//}
