import org.scalajs.dom
import org.scalajs.dom.{document, html}

import scala.scalajs.js.annotation.{JSExport, JSExportTopLevel}
object Main {
  def main(args: Array[String]): Unit = {
  }

  def appendParagraph(target: dom.Node, text: String): Unit = {
    val p = document.createElement("p")
    val textNode = document.createTextNode(text)
    p.appendChild(textNode)
    target.appendChild(p)
  }

  def click(): Unit = {
    println("Button clicked")
    appendParagraph(document.getElementById("scalaJsShoutout"), "Button clicked")
  }
}
