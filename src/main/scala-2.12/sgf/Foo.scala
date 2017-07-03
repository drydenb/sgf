package sgf

import fastparse.all._

/**
  * Author: Dryden Bouamalay
  * Date: 6/3/17
  */
object Foo {
  val plus = P( "+" )
  val num = P( CharIn('0' to '9').rep(1) ).!.map(_.toInt)
  val side = P( "(" ~ expr ~ ")" | num )
  val expr: P[Int] = P( side ~ plus ~ side ) map {
    case (l, r) => l + r
  }
}
