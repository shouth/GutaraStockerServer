package route

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server._
import dao.LocationDAO

trait LocationController {
  this: LocationDAO =>

  object LocationRoute {
    val route: Route =
      pathPrefix("location") {
        path("add") {
          post {
            formFields(Symbol("name"), Symbol("group_id").as[Int]) { case (name, groupId) =>
              locationRepository.add(name, groupId)
              complete(StatusCodes.OK)
            }
          }
        }
      }
  }
}
