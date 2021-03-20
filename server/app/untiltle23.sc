import java.time.{Duration, LocalTime}
import java.time.format.DateTimeFormatter

import org.apache.commons.codec.binary.Base64.decodeBase64

import scala.util.matching.Regex

val decoded = new String(decodeBase64("Werner:werner")).split(":")

val list = List(
  1,2,3
)
list.+:(4)

val a = LocalTime.now()
val b = DateTimeFormatter.ofPattern("HH:mm")
val c = LocalTime.of(12, 20)
val d = LocalTime.parse("02:30", b)
val d = b.format(c)
//val c = LocalTime.parse("04:12", b)

val du1 = Duration.parse( "PT" + "3m 50s".replace( " " , "" ).toUpperCase())
val du2 = Duration.parse( "PT" + "4m 50s".replace( " " , "" ).toUpperCase())
du1.plus(du2)
du1.toMinutes
du1.toString
du1.toSecondsPart
val ba = "10:02:50"
//val du3 = Duration.parse("PT" + ba.replace(":","m").replaceFirst("$", "s").toUpperCase())
//val du4 = Duration.parse("PT" + "1:00:00".replaceFirst().replace(":","m").replaceFirst("$", "s").toUpperCase())
//du3.plus(du1)
val du4: Regex = ":".r
du4.findAllIn(ba).size
val du5 = du4.findAllIn(ba).size match {
  case 1 => Duration.parse("PT" + ba.replace(":","m").replaceFirst("$", "s").toUpperCase())
  case 2 => Duration.parse("PT" + ba.replaceFirst("():", "h").replace(":","m").replaceFirst("$", "s").toUpperCase())
}
case class Songs(id: Int, title: String, implicit val duration: Duration)
object Songs {
  //  val duration = LocalTime.now()
//  def apply(id: Int, title: String, duration: Duration): Songs = new Songs(id, title, duration)
  implicit def convertFromString(str: String): Duration = Duration.parse("PT" + str.replace(":","m").replaceFirst("$", "s").toUpperCase())
}
val song1 = Songs(1, "ABC", Songs.convertFromString("02:32"))
val a = DateTimeFormatter.ofPattern("HH:mm:ss")

val format: Regex = "(([0-9][0-9]|[0-9][0-9][0-9]):([0-9][0-9]):([0-9][0-9]))".r
format.matches("01:20")
