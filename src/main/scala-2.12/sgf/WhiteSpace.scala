package sgf

import fastparse.all._
import fastparse.WhitespaceApi

object WhiteSpace {
  val White = WhitespaceApi.Wrapper{
    NoTrace( CharIn(" \t\n\r\f").rep )
  }
}
