package dto

import scalikejdbc._

final case class Item(itemId: Int, itemName: String, belongingGroupId: Int)

object Item extends SQLSyntaxSupport[Item] {
  def apply(e: ResultName[Item])(rs: WrappedResultSet): Item = Item(rs.get(e.itemId), rs.get(e.itemName), rs.get(e.belongingGroupId))
  def apply(p: SyntaxProvider[Item])(rs: WrappedResultSet): Item = apply(p.resultName)(rs)
}