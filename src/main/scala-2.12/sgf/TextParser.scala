package sgf

import scala.util.parsing.combinator._

/**
  * Author: Dryden Bouamalay
  * Date: 5/29/17
  */
object TextParser extends RegexParsers {
  override def skipWhitespace: Boolean = false

  def text: Parser[List[String]] = rep(
    (softLineBreak | nonNewline | escapeChar) | anyChar
  )

  // TODO: ':' only needs an escape in the compose data type
  def escapeChar: Parser[String] = (
    """\\\\""".r ^^ (_ => "\\")
      | """\\\]""".r ^^ (_ => "]")
      | """\\\:""".r ^^ (_ => ":")
      | """\\.""".r ^^ (s => s takeRight 1)
    )

  def nonNewline: Parser[String] = """\h""".r ^^ (_ => " ")
  def softLineBreak: Parser[String] = """\\\n""".r ^^ (_ => " ")

  def anyChar: Parser[String] = """[\s\S]""".r
}
