package sgf

import java.io.FileReader

/**
  * Author: Dryden Bouamalay
  * Date: 5/27/17
  */
object SGF extends TextParser {
  def main(args: Array[String]): Unit = {
    val reader = new FileReader("/Users/icarus/code/scala/go/src/main/resources/text_example.txt")
    val parsed: ParseResult[List[String]] = parseAll(text, reader)
    val result = parsed.get
    for (elem <- result) { print(elem) }
  }
}
