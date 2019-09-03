package dao

import java.sql.Timestamp
import java.time.LocalDateTime

import dto.StockHistory
import scalikejdbc._

trait StockHistoryDAO {
  val stockRepository: StockRepository

  class StockRepository {
    def addUsageHistory(itemId: Int, locationId: Int, groupId: Int, remain: Int): Unit = {
      DB localTx { implicit session =>
        val c = StockHistory.column
        applyUpdate {
          insert.into(StockHistory)
            .namedValues(
              c.itemId -> itemId,
              c.locationId -> locationId,
              c.belongingGroupId -> groupId,
              c.remain -> remain,
              c.createdAt -> Timestamp.valueOf(LocalDateTime.now()))
        }
      }
    }

    def getLatestHistories(groupId: Int): Seq[StockHistory] = {
      DB readOnly { implicit session =>
        val sh1 = StockHistory.syntax("sh1")
        val sh2 = StockHistory.syntax("sh2")
        withSQL {
          select(sh1.*).from(StockHistory as sh1)
            .leftJoin(StockHistory as sh2).on(
              sqls
                .eq(sh1.itemId, sh2.itemId)
                .and.eq(sh1.locationId, sh2.locationId)
                .and.lt(sh1.createdAt, sh2.createdAt)
            )
            .where.isNull(sh2.createdAt)
        }.map(rs =>
          StockHistory(
            rs.get(1),
            rs.get(2),
            rs.get(3),
            rs.get(4),
            rs.get(5),
            rs.get(6))
        ).list.apply()
      }
    }
  }
}
