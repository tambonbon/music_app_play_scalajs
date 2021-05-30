package shared

import play.api.libs.json.Json

case class Albums(artist: String, name: String, genre: String)


object Albums {
  implicit val albumReads = Json.reads[Albums]
  implicit val albumWrites = Json.writes[Albums]
}
