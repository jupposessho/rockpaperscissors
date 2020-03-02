package rockpaperscissors.model

sealed abstract class Weapon(move: Int) { self =>

  def beats(other: Weapon): Option[Boolean] = {
    if (self == other) {
      None
    } else {
      val otherLowerThanSelf = other < self.move
      if (sameParity(other)) {
        Some(!otherLowerThanSelf)
      } else {
        Some(otherLowerThanSelf)
      }
    }
  }

  val even = self.move % 2 == 0

  def <(otherMove: Int) =
    self.move < otherMove

  private def sameParity(other: Weapon): Boolean =
    (self.even && other.even) || (!self.even && !other.even)
}

object Weapon {

  case object Rock extends Weapon(1)
  case object Paper extends Weapon(2)
  case object Scissors extends Weapon(3)

  def apply(move: Int): Option[Weapon] = move match {
    case 1 => Some(Rock)
    case 2 => Some(Paper)
    case 3 => Some(Scissors)
    case _ => None
  }

  val numberOfWeapons = 3
}
