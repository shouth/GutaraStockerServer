package dao

import scalikejdbc._
import dto.Item

trait ItemDAO {
  val itemRepository: ItemRepository

  class ItemRepository {
    def add(itemName: String, belongingGroupId: Int): Unit = {
      DB localTx { implicit session =>
        val c = Item.column
        applyUpdate {
          insert.into(Item)
            .namedValues(c.itemName -> itemName, c.belongingGroupId -> belongingGroupId)
        }
      }
    }

    def getItemsOfGroup(belongingGroupId: Int): Seq[Item] = {
      DB readOnly { implicit session =>
        val i = Item.syntax("i")
        withSQL {
          select.from(Item as i)
            .where.eq(i.belongingGroupId, belongingGroupId)
        }.map(Item(i)).list.apply()
      }
    }
  }
}
