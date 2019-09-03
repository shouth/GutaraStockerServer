package dto

import scalikejdbc._

final case class UserGroup(groupId: Int, groupName: String)

object UserGroup extends SQLSyntaxSupport[UserGroup] {
  def apply(e: ResultName[UserGroup])(rs: WrappedResultSet): UserGroup = UserGroup(rs.get(e.groupId), rs.get(e.groupName))
  def apply(p: SyntaxProvider[UserGroup])(rs: WrappedResultSet): UserGroup = apply(p.resultName)(rs)
}
