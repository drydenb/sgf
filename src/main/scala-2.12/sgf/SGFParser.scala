package sgf

import scala.util.parsing.combinator._

/**
  * Author: Dryden Bouamalay
  * Date: 5/26/17
  */
class SGFParser extends RegexParsers {

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


  // TODO: Match escape characters and replace instances of \\n with " "
  //  Random notes:
  //
  //  scala> val test2: Regex = """\\\n""".r
  //  test2: scala.util.matching.Regex = \\\n
  //
  //  scala> "\\\n" match { case test2(_*) => "match!" }
  //  res14: String = match!
  //
  //  val thingy: Regex = "[\\\n\\]\\:]".r


  def escape = """[\]\\:]"""
  def simpleText = ???
  def text = ???

  def point = ???
  def move = ???
  def stone = ???

  def compose = valueType ~ ":" ~ valueType
}
