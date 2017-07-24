package sgf

import scala.io.Source
import fastparse.noApi._
import fastparse.core.Parsed
import sgf.WhiteSpace.White._

sealed trait CValueType
sealed trait ValueType extends CValueType

class Collection(gametrees: Seq[GameTree]) {
  def getGameTrees = gametrees
  def getRootSequence: Seq[Node] = gametrees(0).sequence.nodes
  def printRootSequence: Unit = {
    for (node <- getRootSequence) {
      println(node)
    }
  }
  def getRootNode: Node = gametrees(0).sequence.nodes(0)
}

final case class GameTree(sequence: Sequence, gametrees: Seq[GameTree])
final case class Sequence(nodes: Seq[Node])
final case class Node(properties: Seq[Property])
final case class Property(
  propertyIdentifier: PropIdent,
  propertyValues: Seq[PropValue]
)
final case class PropIdent(ucLetters: String)
final case class PropValue(cValueType: String)
final case class Compose(a: ValueType, b: ValueType) extends CValueType
final case class Number(number: Int) extends ValueType
final case class Real(real: scala.Double) extends ValueType
final case class Double(double: Int) extends ValueType
final case class Color(color: Char) extends ValueType
final case class SimpleText(simpleText: String) extends ValueType
final case class Text(text: String) extends ValueType
final case class Point(firstCoord: Char, secondCoord: Char) extends ValueType
final case class None() extends ValueType

object SGF extends Serializable {

  def collection: P[Collection] = P( gametree.rep(1) ) map {
    trees => new Collection(trees)
  }
  def gametree: P[GameTree] =
    P( "(" ~/ sequence ~ gametree.rep ~ ")" ) map {
      case (seq: Sequence, gametrees: Seq[GameTree]) => {
        GameTree(seq, gametrees)
      }
    }
  def sequence: Parser[Sequence] = P( node.rep(1) ) map { Sequence(_) }
  def node: Parser[Node] = P( ";" ~/ property.rep ) map { Node(_) }
  def property: Parser[Property] = P( propIndent ~ propValue.rep(1) ) map {
    case (pId: PropIdent, pVals: Seq[PropValue]) => Property(pId, pVals)
  }
  def propIndent: Parser[PropIdent] = P( ucLetter.rep(1) ) map {
    ucChars => PropIdent(ucChars.mkString)
  }
  def propValue: Parser[PropValue] = P( "[" ~/ cValueType ~ "]" )
  def cValueType: P[PropValue] = P( valueType.! | compose.! ) map {
    PropValue(_)
  }

  // TODO: The following is not a precise specification for SimpleText and Text
  def simpleText: Parser[SimpleText] = P( CharPred(_ != ']').!.rep ) map {
    chars => SimpleText(chars.mkString)
  }
  def text: Parser[Text] =
    P( CharPred(c => c.toString != "]" || c.toString == "\\]").!.rep ) map {
      chars => Text(chars.mkString)
    }

  def valueType: Parser[ValueType] = P(
    text
      | simpleText
      | real
      | number
      | double
      | color
      | point
      | move
      | stone
      | none
  )

  def ucLetter: Parser[Char] = P( CharIn('A' to 'Z').! ) map { _.head }
  def digit: Parser[String] = P( CharIn('0' to '9').! )
  def none: Parser[None] = P( "" ) map { _ => None() }
  def number: Parser[Number] = P( CharIn("+-").?.! ~ digit.rep(1).! ) map {
    case (sign: String, digits: String) => {
      if (sign == "-") {
        Number(-Integer.parseInt(digits))
      }
      else {
        Number(Integer.parseInt(digits))
      }
    }
  }
  def real: Parser[Real] = P( number ~ ".".? ~ digit.rep(1).! ) map {
    case (number: Number, digits: String) => {
      Real(
        number.number +
        Integer.parseInt(digits) / Math.pow(10, digits.length)
      )
    }
  }
  def double: Parser[Double] = P( "1".! | "2".! ) map {
    number => Double(Integer.parseInt(number))
  }
  def color: Parser[Color] = P( "B".! | "W".! ) map {
    color => Color(color.head)
  }
  def point: Parser[Point] = P( CharIn('a' to 'z').rep(exactly=2).! ) map {
    point => Point(point(0), point(1))
  }
  def move = point
  def stone = point
  def compose: P[(PropValue, PropValue)] =
    P( valueType.! ~ ":" ~ valueType.! ).map {
      case (s1: String, s2: String) => (PropValue(s1), PropValue(s2))
    }

  def main(args: Array[String]): Unit = {
    val fileName = "test.sgf"
    val fileString = Source.fromResource(fileName).mkString
    val parseResult: Option[Collection] = SGF.collection.parse(fileString)
    match {
      case Parsed.Success(value, _) => Some(value)
      case Parsed.Failure(expected, failIndex, extra) => {
        println(
          s"Unsuccessful parse. expected: " +
            s"$expected, failIndex: $failIndex, extra: $extra"
        )
        scala.None
      }
    }
    val collection = parseResult.getOrElse(
      throw new RuntimeException("Unable to parse collection from SGF file")
    )
    println(collection.getGameTrees)
    println(collection.getRootSequence)
    collection.printRootSequence
    val rootNode: Node = collection.getRootNode
    println(s"The root node is: $rootNode")
  }

}
