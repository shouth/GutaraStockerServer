package dao

import dto.{UserGroup, Item, UserAndGroup}
import scalikejdbc._

trait GroupDAO {
  val groupRepository: GroupRepository

  class GroupRepository {
    def add(groupName: String): Unit = {
      DB localTx { implicit session =>
        val c = UserGroup.column
        applyUpdate {
          insert.into(UserGroup)
            .namedValues(c.groupName -> groupName)
        }
      }
    }

    def addUserToGroup(userId: Int, groupId: Int): Unit = {
      DB localTx { implicit session =>
        val c = UserAndGroup.column
        applyExecute {
          insert.into(UserAndGroup)
            .namedValues(c.userId -> userId, c.groupId -> groupId)
        }
      }
    }
  }
}
