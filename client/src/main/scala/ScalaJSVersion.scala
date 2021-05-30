import org.scalajs.dom.experimental.RequestRedirect.error
import org.scalajs.dom.experimental.{Fetch, Headers, HttpMethod}
import org.scalajs.dom.{document, html}
import play.api.libs.json.{JsError, JsSuccess, Json}
import shared.Albums

import scala.concurrent.ExecutionContext.Implicits.global
import scala.scalajs.js
import scala.scalajs.js.Thenable.Implicits.thenable2future
import scala.scalajs.js.annotation.JSExportTopLevel

object ScalaJSVersion {
  val addAlbumRoute = document.getElementById("addAlbumRoute").asInstanceOf[html.Input].value
  val albumsRoute = document.getElementById("albumsRoute").asInstanceOf[html.Input].value
  val testArtist = document.getElementById("artist").asInstanceOf[html.Input].value
  def init(): Unit = {
    println("ScalaJS version: Add album")
  }

  @JSExportTopLevel("add")
  def add(): Unit = {
    val artist = document.getElementById("artist").asInstanceOf[html.Input].value
    val name = document.getElementById("name").asInstanceOf[html.Input].value
    val genre = document.getElementById("genre").asInstanceOf[html.Input].value

    val data = shared.Albums(artist, name, genre)

    val headers = new Headers()
    headers.set("Content-Type", "application/json")

    Fetch.fetch(addAlbumRoute
      , RequestInit(method = HttpMethod.POST,
      headers = headers, body = Json.toJson(data).toString))
      .flatMap(res => res.text())
      .map { data =>
        Json.fromJson[Boolean](Json.parse(data)) match {
          case JsSuccess(b, path) =>
            document.getElementById("submit-message").innerHTML = ""
          case JsError(_) =>
            document.getElementById("summit-message").innerHTML = "Failed"
        }}
//    )
//      .map {
//        data =>
//         JsSuccess(data)
//      }

//    getAlbums()
//    FetchJson.fetchPost(addAlbumRoute, csrfToken, data, (bool: Boolean) => {
//      if(bool) {
////        document.getElementById("create-message").innerHTML = ""
//        document.getElementById("artist").asInstanceOf[html.Input].value = ""
//        document.getElementById("name").asInstanceOf[html.Input].value = ""
//        document.getElementById("genre").asInstanceOf[html.Input].value = ""
//        getAlbums()
//      } else {
////        document.getElementById("create-message").innerHTML = "User Creation Failed"
//      }
//    }, e => {
//      println("Fetch error: " + e)
//    })
  }


}
