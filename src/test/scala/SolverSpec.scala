import model.Node
import org.scalatest.{FlatSpec, Matchers}
import serializers.{JsonSerializer, MySerializer}


class SolverSpec extends FlatSpec with Matchers with JsonSerializer {

  "A serializer" should "serialize test example" in {
    val examplePath = "test1.xlsx"

    val nodeList = List(
      Node(1,"A",List(
        Node(2,"AA",List(
          Node(3,"AA1",List()),
          Node(4,"AA2",List()))),
        Node(5,"AB",List()))),
      Node(6,"B",List()),
      Node(7,"C",List(
        Node(8,"CA",List(
          Node(9,"CA1",List()),
          Node(10,"CA2",List()))))),
      Node(11,"D",List(
        Node(12,"DA",List()))))

    MySerializer.sheetToNodes(IOControler.readXLSXFile(examplePath)) should be (nodeList)
  }

  "Json serializer" should "return case class in json format" in {
    val simpleExample = Node(1,"test",Nil)
    val notSimpleExample = Node(1,"test",List(Node(2,"test2",Nil), Node(3,"test3",List(Node(4,"test4",Nil)))))
    val listExample = List(Node(1,"test",Nil),Node(2,"test2",Nil),Node(3,"test3",Nil))

    toJson(simpleExample) should be ("""{"id":1,"name":"test","nodes":[]}""")
    toJson(notSimpleExample) should be ("""{"id":1,"name":"test","nodes":[{"id":2,"name":"test2","nodes":[]},{"id":3,"name":"test3","nodes":[{"id":4,"name":"test4","nodes":[]}]}]}""")
    toJson(listExample) should be ("""[{"id":1,"name":"test","nodes":[]},{"id":2,"name":"test2","nodes":[]},{"id":3,"name":"test3","nodes":[]}]""")
  }
}
