package models

import java.time.Duration

import controllers.CreateSongForm
import play.api.data.Form
import play.api.data.Forms.{localTime, mapping, text}
import play.api.libs.functional.syntax.{toFunctionalBuilderOps, unlift}
import play.api.libs.json.{Json, Reads, Writes, __}

import scala.util.matching.Regex

case class Songs(id: Int, title: String, duration: String) {
  val colon: Regex = ":".r
  def convertFromString = colon.findAllIn(duration).size match {
    case 1 => Duration.parse("PT" + duration.replace(":","m").replaceFirst("$", "s").toUpperCase())
    case 2 => Duration.parse("PT" + duration.replaceFirst("():", "h").replace(":","m").replaceFirst("$", "s").toUpperCase())
    case _ => None
  }
  def validationDuration(id: Int, title: String, duration: String): Option[Songs] = {
    val format: Regex = "(([0-9][0-9]|[0-9]):(([0-5][0-9])|[0-9]):([0-5][0-9]))|((([0-5][0-9])|[0-9]):([0-5][0-9]))".r

    if (format.matches(duration)) {
      Some(new Songs(id, title, duration))
    } else None
  }
}

object Songs {


  implicit val songWrites: Writes[Songs] = Writes { song =>
    Json.obj(
      "name" -> song.title,
      "duration" -> song.duration
    )
  }

  implicit val songReads: Reads[Songs] =  (
    (__ \ "id").read[Int] and
      (__ \ "title").read[String] and
      (__ \ "duration").read[String]
    )(Songs.apply _)

  val songsForm: Form[CreateSongForm] = Form (
    mapping(
      "title" -> text,
      "duration" -> text,
    )(CreateSongForm.apply)(CreateSongForm.unapply)
  )
}
case class SongNoID(name: String, duration: String) {
}
object SongNoID {
  implicit val songNoIDFormat = Json.format[SongNoID]
}
case class FullAlbum(artist: String, name: String, genre: String, songs: Seq[SongNoID])

object FullAlbum {
  implicit val fullAlbumWrite: Writes[FullAlbum] =
  (
    (__ \ "artist").write[String] and
      (__ \ "name").write[String] and
      (__ \ "genre").write[String] and
      (__ \ "songs").write[Seq[SongNoID]]
    )(unlift(FullAlbum.unapply _)
  )

  implicit val fullAlbumRead: Reads[FullAlbum] =
     (
      (__ \ "artist").read[String] and
      (__ \ "name").read[String] and
      (__ \ "genre").read[String] and
      (__ \ "songs").read[Seq[SongNoID]]
      )(FullAlbum.apply _)
}