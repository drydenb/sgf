package sgf

import scala.util.parsing.combinator._

/**
  * Author: Dryden Bouamalay
  * Date: 5/27/17
  */
// TODO: Handle separate encodings and check carriage return
class SimpleTextParser extends RegexParsers {
  override def skipWhitespace: Boolean = false

  def simpleText: Parser[List[String]] = rep(
    (replaceWhiteSpace | escapeWhiteSpace | escapeChar) | anyChar
  )

  // TODO: ':' only needs an escape in the compose data type
  def escapeChar: Parser[String] = (
    """\\\\""".r ^^ (_ => "\\")
      | """\\\]""".r ^^ (_ => "]")
      | """\\\:""".r ^^ (_ => ":")
      | """\\.""".r ^^ (s => s takeRight 1)
    )

  def replaceWhiteSpace: Parser[String] = whiteSpace ^^ (_ => " ")
  def escapeWhiteSpace: Parser[String] = """\\\s""".r ^^ (_ => " ")

  def anyChar: Parser[String] = """[\s\S]""".r
}
