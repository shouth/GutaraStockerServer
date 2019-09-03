package route

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server._
import dao.ItemDAO

trait ItemController {
  this: ItemDAO =>

  object ItemRoute {
    val route: Route =
      pathPrefix("item") {
        path("add") {
          formFields(Symbol("name"), Symbol("group_id").as[Int]) { (itemName, belongingGroupId) =>
            itemRepository.add(itemName, belongingGroupId)
            complete(StatusCodes.OK)
          }
        }
      }
  }
}
