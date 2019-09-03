package route

import java.sql.Timestamp

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import dto.{UserGroup, Item, Location, StockHistory, ServiceUser}
import spray.json._

trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
  implicit object TimestampFormat extends RootJsonFormat[Timestamp] {
    override def write(obj: Timestamp): JsValue = JsNumber(obj.getTime)

    override def read(json: JsValue): Timestamp = json match {
      case JsNumber(bg) => new Timestamp(bg.longValue)
      case _ => deserializationError("Timestamp is needed to be Number")
    }
  }

  implicit val itemFormat = jsonFormat3(Item.apply)
  implicit val groupFormat = jsonFormat2(UserGroup.apply)
  implicit val locationFormat = jsonFormat3(Location.apply)
  implicit val stockHistoryFormat = jsonFormat6(StockHistory.apply)
  implicit val userFormat = jsonFormat2(ServiceUser.apply)
}
