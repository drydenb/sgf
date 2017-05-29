//package board
//
///**
//  * Created by Dryden Bouamalay on 3/21/17.
//  */
//class Board extends Serializable {
//
//  def placeStone(x: Int, y: Int): Unit = {
//
//    def inBounds(c: Int): Boolean = {
//      c >= MIN_BOARD_COORD && c <= MAX_BOARD_COORD
//    }
//
//    if ( !(inBounds(x) && inBounds(y)) ) {
//      throw new IllegalArgumentException(
//        s"Must place stone within board boundaries: " +
//          s"[$MIN_BOARD_COORD, $MAX_BOARD_COORD]")
//    }
//
//    val stone = new Stone(x, y)
//
//  }
//
//}
