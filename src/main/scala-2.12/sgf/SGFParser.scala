package sgf

import scala.util.parsing.combinator._
import sgf.SimpleTextParser._
import sgf.TextParser._

import fastparse.all._

/**
  * Author: Dryden Bouamalay
  * Date: 5/26/17
  */

//trait DebugStandardTokenParsers extends StandardTokenParsers {
//  class Wrap[+T](name:String,parser:Parser[T]) extends Parser[T] {
//    def apply(in: Input): ParseResult[T] = {
//      val first = in.first
//      val pos = in.pos
//      val offset = in.offset
//      val t = parser.apply(in)
//      println(name+".apply for token "+first+
//        " at position "+pos+" offset "+offset+" returns "+t)
//      t
//    }
//  }
//}


// TODO: Handle separate encodings and check carriage return
//trait SimpleTextParser extends RegexParsers {
//  override def skipWhitespace: Boolean = false
//}


// TODO: Handle separate encodings and check carriage return on soft line breaks
//trait TextParser extends RegexParsers
//with SimpleTextParser
//{
//  override def skipWhitespace: Boolean = false
//
//  def text: Parser[List[String]] = rep(
//    (softLineBreak | nonNewline | escapeChar) | anyChar
//  )
//
//  // TODO: ':' only needs an escape in the compose data type
////  def escapeChar: Parser[String] = (
////    """\\\\""".r ^^ (_ => "\\")
////      | """\\\]""".r ^^ (_ => "]")
////      | """\\\:""".r ^^ (_ => ":")
////      | """\\.""".r ^^ (s => s takeRight 1)
////    )
//
//  def nonNewline: Parser[String] = """\h""".r ^^ (_ => " ")
//  def softLineBreak: Parser[String] = """\\\n""".r ^^ (_ => " ")
//
////  def anyChar: Parser[String] = """[\s\S]""".r
//}

//trait DebugStandardTokenParsers extends RegexParsers {
//  class Wrap[+T](name:String,parser:Parser[T]) extends Parser[T] {
//    def apply(in: Input): ParseResult[T] = {
//      val first = in.first
//      val pos = in.pos
//      val offset = in.offset
//      val t = parser.apply(in)
//      println(name+".apply for token "+first+
//        " at position "+pos+" offset "+offset+" returns "+t)
//      t
//    }
//  }
//}

//class SGFParser {
//
//}

//override def skipWhitespace = false
//
//// EBNF definition for SGF files
//def collection: Parser[Any] = new Wrap("collection", rep1sep(gametree, whiteSpace | ""))
//def gametree: Parser[Any] = new Wrap("gametree", "(" ~ sequence <~ opt(whiteSpace) ~> repsep(gametree, whiteSpace | "") ~ ")")
//def sequence: Parser[Any] = new Wrap("sequence", rep1sep(node, whiteSpace | ""))
//def node: Parser[Any] = new Wrap("node", ";" ~> repsep(property, whiteSpace | ""))
//def property: Parser[Any] = new Wrap("property", propIndent ~ rep1sep(propValue, whiteSpace | ""))
//def propIndent: Parser[Any] = new Wrap("propIndent", rep1(ucLetter))
//def propValue: Parser[Any] = new Wrap("propValue", "[" ~ cValueType ~ "]")
//def cValueType: Parser[Any] = new Wrap("cValueType", valueType | compose)
//def valueType: Parser[Any] = new Wrap("valueType", (
//text
//| simpleText
//| number
//| real
//| double
//| color
//| point
//| move
//| stone
//| none
//))
//
//// TODO: Double check these regexes
//
//// Property value type definitions
//def ucLetter: Parser[String] = """[A-Z]""".r
//def digit: Parser[String] = """[0-9]""".r
//def none: Parser[String] = """""".r
//
//def number: Parser[Any] = opt("+" | "-") ~ rep1(digit)
//def real: Parser[Any] = number ~ opt("." ~ rep1(digit))
//
//def double: Parser[String] = """(1|2)""".r
//def color: Parser[String] = """(B|W)""".r
//
//// TODO: Handle separate encodings with CA
//def simpleText: Parser[List[String]] = rep(
//(replaceWhiteSpace | escapeWhiteSpace | escapeChar) | anyChar
//)
//
//// TODO: Handle separate encodings with CA
//def text: Parser[List[String]] = rep(
//(softLineBreak | nonNewline | escapeChar) | anyChar
//)
//
//def point = """[a-zA-Z][a-zA-Z]""".r
//def move = """[a-zA-Z][a-zA-Z]""".r
//def stone = point
//
//def compose = valueType ~ ":" ~ valueType
//
//
//// TODO: ':' only needs an escape in the compose data type
//def escapeChar: Parser[String] = (
//"""\\\\""".r ^^ (_ => "\\")
//| """\\\]""".r ^^ (_ => "]")
//| """\\\:""".r ^^ (_ => ":")
//| """\\.""".r ^^ (s => s takeRight 1)
//)
//
//def nonNewline: Parser[String] = """\h""".r ^^ (_ => " ")
//def softLineBreak: Parser[String] = """\\\n""".r ^^ (_ => " ")
//
//def replaceWhiteSpace: Parser[String] = whiteSpace ^^ (_ => " ")
//def escapeWhiteSpace: Parser[String] = """\\\s""".r ^^ (_ => " ")
//
//def anyChar: Parser[String] = """[\s\S]""".r
