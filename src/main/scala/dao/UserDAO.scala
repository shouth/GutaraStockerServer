package dao

import scalikejdbc._
import dto.{UserGroup, ServiceUser, UserAndGroup}
import com.github.t3hnar.bcrypt._

trait UserDAO {
  val userRepository: UserRepository

  class UserRepository {
    def addUser(password: String):Unit = {
      DB localTx { implicit session =>
        val c = ServiceUser.column
        applyUpdate {
          insert.into(ServiceUser)
            .namedValues(c.password -> password.bcrypt)
        }
      }
    }

    def getUser(userId: Int): Option[ServiceUser] = {
      DB readOnly { implicit session =>
        val u = ServiceUser.syntax("u")
        withSQL {
          select.from(ServiceUser as u)
            .where.eq(u.userId, userId)
        }.map(ServiceUser(u)).single.apply()
      }
    }

    def getGroupsUserBelongs(userId: Int): Seq[UserGroup] = {
      DB readOnly { implicit session =>
        val ug = UserAndGroup.syntax("ug")
        val g = UserGroup.syntax("g")
        withSQL {
          select.from(UserAndGroup as ug)
            .innerJoin(UserGroup as g)
            .on(ug.groupId, g.groupId)
            .where.eq(ug.userId, userId)
        }.map(UserGroup(g)).list.apply()
      }
    }
  }
}
