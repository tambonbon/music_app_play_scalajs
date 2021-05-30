import org.scalajs.dom
import shared.SharedMessage
object Main {
  def main(args: Array[String]): Unit = {
    println("This is Scala.js")
//    println(ScalaJSVersion.albumsRoute)
    println(ScalaJSVersion.addAlbumRoute)
    println(ScalaJSVersion.testArtist)
//    if (dom.document.getElementById("scalaJsShoutout")!= null){
//      dom.document.getElementById("scalaJsShoutout").textContent = SharedMessage.itWorks
//    }
  }
}
