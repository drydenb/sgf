package sgf

import scala.util.parsing.combinator._
import scala.util.matching.Regex

/**
  * Author: Dryden Bouamalay
  * Date: 5/26/17
  */
class ParserSGF extends RegexParsers {

  // TODO: Double check this grammar

  // EBNF definition for SGF files
  def collection: Parser[Any] = rep1(gametree)
  def gametree: Parser[Any] = "(" ~ sequence ~ rep(gametree) ~ ")"
  def sequence: Parser[Any] = rep1(node)
  def node: Parser[Any] = ";" ~ rep(property)
  def property: Parser[Any] = propIndent ~ propValue ~ rep(propValue)
  def propIndent: Parser[Any] = ucLetter ~ rep(ucLetter)
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
  def ucLetter: Regex = "[A-Z]".r
  def digit: Regex = "[0-9]".r
  def none: Regex = "".r

  def number: Parser[Any] = opt("+" | "-") ~ rep1(digit)
  def real: Parser[Any] = number ~ opt("." ~ rep1(digit))

  def double: Regex = "(1|2)".r
  def color: Regex = "(B|W)".r

  def simpleText = ???
  def text = ???

  def point = ???
  def move = ???
  def stone = ???

  def compose = valueType ~ ":" ~ valueType
}
