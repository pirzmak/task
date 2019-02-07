import model.Node
import org.scalatest.{FlatSpec, Matchers}
import serializers.{JsonSerializer, MySerializer}

class MySerializerSpec extends FlatSpec with Matchers {

  "A serializer toNode" should "serialize empty cell list to empty list" in {
    MySerializer.toNode(Nil) should be (Nil)
  }

  "A serializer toNode" should "serialize simply cell list to model.Node" in {
    MySerializer.toNode(List((Some("A"),None,None,"1"))) should be (List(Node(1, "A", Nil)))
  }

  "A serializer toNode" should "serialize complicate cell list to model.Node" in {
    val list = List(
      (Some("A"),None,None,"1"),
      (Some("B"),None,None,"2"),
        (None,Some("BA"),None,"3"),
        (None,Some("BB"),None,"4"),
          (None,None,Some("BB1"),"5"),
          (None,None,Some("BB2"),"6"),
        (None,Some("BC"),None,"7"),
        (None,None,Some("BC1"),"8"),
      (Some("C"),None,None,"9"),
        (None,Some("CA"),None,"10"),
          (None,None,Some("CA1"),"11")
    )

    val nodeList = List(
      Node(1, "A", Nil),
      Node(2, "B", List(
        Node(3, "BA", Nil),
        Node(4, "BB", List(
          Node(5, "BB1", Nil),
          Node(6, "BB2", Nil)
        )),
        Node(7, "BC", List(
          Node(8, "BC1", Nil)
        )),
      )),
      Node(9, "C", List(
        Node(10, "CA", List(
          Node(11, "CA1", Nil),
        ))
      ))
    )

    MySerializer.toNode(list) should be (nodeList)
  }

  it should "throw IllegalArgumentException if an row data has different number than one Option with value" in {
    a [IllegalArgumentException] should be thrownBy {
      MySerializer.toNode(List((Some("A"),Some("A"),Some("A"),"1")))
    }
    a [IllegalArgumentException] should be thrownBy {
      MySerializer.toNode(List((Some("A"),None,Some("A"),"1")))
    }
    a [IllegalArgumentException] should be thrownBy {
      MySerializer.toNode(List((None,None,None,"1")))
    }
  }
}
