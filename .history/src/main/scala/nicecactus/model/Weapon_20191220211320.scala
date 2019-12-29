package nicecactus.model

sealed abstract class Weapon(move: Int) { self =>
  def beats(other: Weapon): Option[Boolean] = {
    if (self == other) {
      None
    } else {
      if (sameParity(other)) {
        Some(!other.lowerThan(self.move))
      } else {
        Some(other.lowerThan(self.move))
      }
    }
  }

  val even = self.move % 2 == 0

  def <(otherMove: Int) = self.move < otherMove

  private def sameParity(other: Weapon): Boolean = (self.even && other.even) || (!self.even && !other.even)
}

object Weapon {
  case object Rock extends Weapon(1)
  case object Paper extends Weapon(2)
  case object Scissors extends Weapon(3)
}
