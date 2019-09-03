package route

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server._
import dao.StockHistoryDAO

trait StockHistoryController {
  this: StockHistoryDAO =>

  object StockHistoryRoute extends JsonSupport {
    val route: Route =
      pathPrefix("stock") {
        concat (
          path("record") {
            post {
              formFields(Symbol("item_id").as[Int], Symbol("location_id").as[Int], Symbol("group_id").as[Int], Symbol("remain").as[Int]) { (itemId, locationId, groupId, remain) =>
                stockRepository.addUsageHistory(itemId, locationId, groupId, remain)
                complete(StatusCodes.OK)
              }
            }
          },
          path("latest-histories" / IntNumber) { groupId =>
            complete(stockRepository.getLatestHistories(groupId))
          }
        )
      }
  }
}
