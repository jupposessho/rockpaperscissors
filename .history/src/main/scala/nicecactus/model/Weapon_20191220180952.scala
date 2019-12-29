package nicecactus.model

abstract class Weapon(move: Int) extends AnyVal { self =>
  def beats(other: Weapon): Option[Boolean] = {
    if (self == other) None
    else Some(true)
  }
}

object Weapon {
  case object Rock extends Weapon(1)
  case object Paper extends Weapon(2)
  case object Scissors extends Weapon(3)
}