package dto

import scalikejdbc._

final case class ServiceUser(userId: Int, password: String)

object ServiceUser extends SQLSyntaxSupport[ServiceUser] {
  def apply(e: ResultName[ServiceUser])(rs: WrappedResultSet): ServiceUser = ServiceUser(rs.get(e.userId), rs.get(e.password))
  def apply(p: SyntaxProvider[ServiceUser])(rs: WrappedResultSet): ServiceUser = apply(p.resultName)(rs)
}
