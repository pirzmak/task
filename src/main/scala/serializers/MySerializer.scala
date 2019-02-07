package serializers

import model.Node
import org.apache.poi.ss.usermodel.{Cell, DataFormatter, Row, Sheet}
import scala.collection.JavaConverters._

object MySerializer {
  implicit val formatter: DataFormatter = new DataFormatter()

  def sheetToNodes(sheet: Sheet): List[Node] = toNode(sheet.asScala.map(formatRow).toList.tail)

  def toNode(row: List[(Option[String], Option[String], Option[String], String)]): List[Node] = {
    row match {
      case (Some(label), None, None, id) :: rest => Node(id.toInt, label, toNode(rest.takeWhile(_._1.isEmpty))) :: toNode(rest.dropWhile(_._1.isEmpty))
      case (None, Some(label), None, id) :: rest => Node(id.toInt, label, toNode(rest.takeWhile(_._2.isEmpty))) :: toNode(rest.dropWhile(_._2.isEmpty))
      case (None, None, Some(label), id) :: rest => Node(id.toInt, label, Nil) :: toNode(rest)
      case Nil => Nil
      case _ => throw new IllegalArgumentException()
    }
  }

  private def formatRow(row: Row)(implicit formatter: DataFormatter): (Option[String], Option[String], Option[String], String) = {
    (row.getCell(0), row.getCell(1), row.getCell(2), row.getCell(3).get)
  }

  private implicit def cellValueToString(cell: Cell)(implicit formatter: DataFormatter): Option[String] = formatter.formatCellValue(cell) match {
    case "" => None
    case s => Some(s)
  }
}
