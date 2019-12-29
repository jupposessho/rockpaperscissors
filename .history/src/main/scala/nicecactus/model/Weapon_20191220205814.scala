package nicecactus.model

sealed abstract class Weapon(val move: Int) { self =>
  def beats(other: Weapon): Option[Boolean] = {
    if (self == other) {
      None
    } else {
      if ((self.even && other.even) || (!self.even && !other.even)) {
        if (self.move < other.move) Some(true)
        else Some(false)
      } else {
        if (self.move > other.move) Some(true)
        else Some(false)
      }
    }
  }

  val even = self.move % 2 == 0
}

object Weapon {
  case object Rock extends Weapon(1)
  case object Paper extends Weapon(2)
  case object Scissors extends Weapon(3)
}
