package dto

import java.sql.Timestamp

import scalikejdbc._

final case class StockHistory(historyId: Int, itemId: Int, locationId: Int, belongingGroupId: Int, remain: Int, createdAt: Timestamp)

object StockHistory extends SQLSyntaxSupport[StockHistory] {
  def apply(e: ResultName[StockHistory])(rs: WrappedResultSet): StockHistory = StockHistory(rs.get(e.historyId), rs.get(e.itemId), rs.get(e.locationId), rs.get(e.belongingGroupId), rs.get(e.remain), rs.get(e.createdAt))
}