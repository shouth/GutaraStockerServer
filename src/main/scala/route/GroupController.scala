package route

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server._
import dao.{GroupDAO, ItemDAO, LocationDAO}

trait GroupController {
  this: GroupDAO with ItemDAO with LocationDAO =>

  object GroupRoute extends JsonSupport {
    val route: Route =
      pathPrefix("group") {
        concat (
          path("add") {
            post {
              formFields(Symbol("name")) { name =>
                groupRepository.add(name)
                complete(StatusCodes.OK)
              }
            }
          },
          path("register-member") {
            post {
              formFields(Symbol("user_id").as[Int], Symbol("group_id").as[Int]) { (userId, groupId) =>
                groupRepository.addUserToGroup(userId, groupId)
                complete(StatusCodes.OK)
              }
            }
          },
          path(IntNumber / "items") { groupId =>
            get {
              complete(itemRepository.getItemsOfGroup(groupId))
            }
          },
          path(IntNumber / "locations") { groupId =>
            get {
              complete(locationRepository.getLocationsOfGroup(groupId))
            }
          }
        )
      }
  }
}
