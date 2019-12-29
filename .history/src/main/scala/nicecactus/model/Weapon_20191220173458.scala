package nicecactus.model

abstract class Weapon(move: Int) {
  def beat(other: Weapon): Option[Boolean] = ???
}

object Weapon {
  case object Rock extends Weapon(1)
  case object Paper extends Weapon(2)
  case object Scissors extends Weapon(3)
}
