package dto

import scalikejdbc._

final case class UserAndGroup(relationId: Int, userId: Int, groupId: Int)

object UserAndGroup extends SQLSyntaxSupport[UserAndGroup] {
  def apply(e: ResultName[UserAndGroup])(rs: WrappedResultSet): UserAndGroup = UserAndGroup(rs.get(e.relationId), rs.get(e.userId), rs.get(e.groupId))
  def apply(p: SyntaxProvider[UserAndGroup])(rs: WrappedResultSet): UserAndGroup = apply(p.resultName)(rs)
}
