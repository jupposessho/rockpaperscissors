package nicecactus.model

sealed abstract class Weapon(move: Int) { self =>
  def beats(other: Weapon): Option[Boolean] = {
    if (self == other) None
    else Some(true)
  }

  private val even = self.move % 2 == 0
}

object Weapon {
  case object Rock extends Weapon(1)
  case object Paper extends Weapon(2)
  case object Scissors extends Weapon(3)
}
