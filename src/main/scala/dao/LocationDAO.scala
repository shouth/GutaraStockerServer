package dao

import scalikejdbc._
import dto.Location

trait LocationDAO {
  val locationRepository: LocationRepository

  class LocationRepository {
    def add(locationName: String, belongingGroupId: Int): Unit ={
      DB localTx { implicit session =>
        val c = Location.column
        applyUpdate {
          insert.into(Location)
            .namedValues(c.locationName -> locationName, c.belongingGroupId -> belongingGroupId)
        }
      }
    }

    def getLocationsOfGroup(belongingGroupId: Int): Seq[Location] = {
      DB readOnly { implicit session =>
        val l = Location.syntax("l")
        withSQL {
          select.from(Location as l)
            .where.eq(l.belongingGroupId, belongingGroupId)
        }.map(Location(l)).list.apply()
      }
    }
  }
}
