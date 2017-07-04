package sgf

import java.io.FileReader
import fastparse.WhitespaceApi
//import fastparse.all._

/**
  * Author: Dryden Bouamalay
  * Date: 5/27/17
  */

object SGF extends Serializable {

  val White = WhitespaceApi.Wrapper{
    import fastparse.all._
    NoTrace( CharIn(" \t\n").rep )
  }

  import fastparse.noApi._
  import White._

  val collection = P( gametree.rep(1) )
  val gametree: P[Any] = P( "(" ~/ sequence ~ gametree.rep ~ ")" )
  val sequence = P( node.rep(1) )
  val node = P( ";" ~/ property.rep )
  val property = P( propIndent ~ propValue.rep(1) )
  val propIndent = P( ucLetter.rep(1) )
  val propValue = P( "[" ~/ cValueType ~ "]" )
  val cValueType = P( valueType | compose )

  // TODO: The following may not be sufficient enough for text values
  val simpleText = P( CharPred(_ != ']').rep )
  val text = P( CharPred(c => c.toString != "]" || c == "\\]").rep )

  val valueType = P(
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

  val ucLetter = P( CharIn('A' to 'Z') )
  val digit = P( CharIn('0' to '9') )
  val none = P( "" )

  val number = P( CharIn("+-").? ~ digit.rep(1) )
  val real = P( number ~ ".".? ~ digit.rep(1) ).log()

  val double = P( "1" | "2" )
  val color = P( "B" | "W" )

  val point = P( CharIn('a' to 'z').rep(exactly=2) )
  val move = point
  val stone = point

  val compose = P( valueType ~ ":" ~ valueType )

  def main(args: Array[String]): Unit = {
//    val fileString = scala.io.Source.fromFile("/Users/icarus/code/scala/go/src/main/resources/test.sgf").mkString
//      val fileString = scala.io.Source.fromFile("/Users/icarus/code/scala/go/src/main/resources/2015-05-01-8.sgf").mkString
      val fileString = scala.io.Source.fromFile("/Users/icarus/code/scala/go/src/main/resources/2015-05-27-13.sgf").mkString

    //    val Parsed.Failure(expected, idx, extra) = collection.parse(fileString)
//    println(expected)
//    println(idx)
//    println(extra.traced.trace)
    val Parsed.Success(value, successIndex) = collection.parse(fileString)
  }

}
