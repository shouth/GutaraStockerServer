package dto

import scalikejdbc._

final case class Location(locationId: Int, locationName: String, belongingGroupId: Int)

object Location extends SQLSyntaxSupport[Location] {
  def apply(e: ResultName[Location])(rs: WrappedResultSet): Location = Location(rs.get(e.locationId), rs.get(e.locationName), rs.get(e.belongingGroupId))
  def apply(p: SyntaxProvider[Location])(rs: WrappedResultSet): Location = apply(p.resultName)(rs)
}
