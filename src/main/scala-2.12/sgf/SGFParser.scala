package sgf

import scala.util.parsing.combinator._

/**
  * Author: Dryden Bouamalay
  * Date: 5/26/17
  */


// TODO: Handle separate encodings and check carriage return
trait SimpleTextParser extends RegexParsers {
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


// TODO: Handle separate encodings and check carriage return on soft line breaks
trait TextParser extends RegexParsers {
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


class SGFParser extends RegexParsers
with TextParser with SimpleTextParser
{

  // EBNF definition for SGF files
  def collection: Parser[Any] = rep1(gametree)
  def gametree: Parser[Any] = "(" ~ sequence ~ rep(gametree) ~ ")"
  def sequence: Parser[Any] = rep1(node)
  def node: Parser[Any] = ";" ~ rep(property)
  def property: Parser[Any] = propIndent ~ propValue ~ rep(propValue)
  def propIndent: Parser[Any] = rep1(ucLetter)
  def propValue: Parser[Any] = "[" ~ cValueType ~ "]"
  def cValueType: Parser[Any] = valueType | compose
  def valueType: Parser[Any] = (
    none
      | number
      | real
      | double
      | color
      | simpleText
      | text
      | point
      | move
      | stone
    )

  // TODO: Double check these regexes

  // Property value type definitions
  def ucLetter: Parser[String] = """[A-Z]""".r
  def digit: Parser[String] = """[0-9]""".r
  def none: Parser[String] = """""".r

  def number: Parser[Any] = opt("+" | "-") ~ rep1(digit)
  def real: Parser[Any] = number ~ opt("." ~ rep1(digit))

  def double: Parser[String] = """(1|2)""".r
  def color: Parser[String] = """(B|W)""".r

  def point = ???
  def move = ???
  def stone = ???

  def compose = valueType ~ ":" ~ valueType
}
