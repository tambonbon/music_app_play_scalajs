import org.scalajs.dom
object Main {
  def main(args: Array[String]): Unit = {
    println("This is Scala.js")
    if (dom.document.getElementById("scalaJsShoutout")!= null){
      dom.document.getElementById("scalaJsShoutout").textContent = "it works"
    }
  }
}
