import serializers.{JsonSerializer, MySerializer}

object Solver extends JsonSerializer {
  def solve(in: String, out: String) = {
    val sheet = IOControler.readXLSXFile(in)

    val nodes = MySerializer.sheetToNodes(sheet)
    IOControler.writeToFile("",out,nodes)
  }
}
