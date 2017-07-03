package sgf

import java.io.FileReader

import fastparse.WhitespaceApi
import fastparse.all._

/**
  * Author: Dryden Bouamalay
  * Date: 5/27/17
  */
object SGF {

//  val reader = new FileReader("/Users/icarus/code/scala/go/src/main/resources/2015-05-01-8.sgf")
//  val sgfParser = new SGFParser
////    parseAll(collection, reader) match {
////      case Success(result, _) => result
////      case NoSuccess(msg, _) => throw new RuntimeException("Parsing Failed:" + msg)
////    }
//  sgfParser.parseAll(sgfParser.collection, reader) match {
//    case sgfParser.Success(res, _) => println("Found value: <<"+ res +">>")
//    case sgfParser.NoSuccess(msg, _) => println("No match: <<"+ msg +">>")
//    case _ => println("I did not match")
//  }

  val White = WhitespaceApi.Wrapper{
    import fastparse.all._
    CharIn(" \n\t\r").rep
  }

  import White._
  val collection = P( gametree.rep(1) )
  val gametree: P[Any] = P( "(" ~/ sequence ~ gametree.rep ~ ")" )
  val sequence = P( node.rep(1) )
  val node = P( ";" ~/ property.rep )
  val property = P( propIndent ~ propValue.rep(1) ).log()
  val propIndent = P( ucLetter.rep(1) ).log()
  val propValue = P( "[" ~/ cValueType ~ "]" ).log()
  val cValueType = P( valueType | compose ).log()

  // TODO: Figure out handling escape characters and text / simpleText
  val escapeChar = P( "\\" ~ AnyChar.! )
  val escapeLineBreak = P( "\\" ~ "\n" | "\\" ~ "\r\n" | "\\" ~ "\r" )
  val text = P( (escapeLineBreak | escapeChar | CharIn('A' to 'Z', 'a' to 'z')).rep ).log()
  val simpleText = text

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
    val fileString = scala.io.Source.fromFile("/Users/icarus/code/scala/go/src/main/resources/test.sgf").mkString
    //    val fileString = scala.io.Source.fromFile("/Users/icarus/code/scala/go/src/main/resources/2015-05-01-8.sgf").mkString
    val Parsed.Failure(expected, idx, extra) = collection.parse(fileString)
    println(expected)
    println(idx)
    println(extra.traced.trace)
  }

}
