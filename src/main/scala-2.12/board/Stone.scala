package board

import config.MAX_BOARD_COORD
import config.MIN_BOARD_COORD

/**
  * Created by Dryden Bouamalay on 3/25/17.
  */
class Stone(x: Int, y: Int) {
  override def toString: String = s"($x, $y)"
}
