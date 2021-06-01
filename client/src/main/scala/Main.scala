import org.scalajs.dom
import org.scalajs.dom.{document, html}

import scala.scalajs.js.annotation.{JSExport, JSExportTopLevel}
object Main {
  def main(args: Array[String]): Unit = {
    println("This is Scala.js")

    dom.document.getElementById("scalaJsShoutout").innerHTML = shared.SharedMessage.itWorks
  }

  def appendParagraph(target: dom.Node, text: String): Unit = {
    val p = document.createElement("p")
    val textNode = document.createTextNode(text)
    p.appendChild(textNode)
    target.appendChild(p)
  }

  @JSExportTopLevel("clickStuff")
  def click(): Unit = {
    println("Button clicked")
    appendParagraph(document.getElementById("scalaJsShoutout"), "Button clicked")
  }
}
