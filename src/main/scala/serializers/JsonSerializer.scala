package serializers

import net.liftweb.json.DefaultFormats
import net.liftweb.json.Serialization.write

trait JsonSerializer {
  implicit def toJson[R](data: R): String = {
    implicit val formats = DefaultFormats
    write(data)
  }
}
