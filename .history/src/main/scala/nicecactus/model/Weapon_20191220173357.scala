package nicecactus.model

abstract class Weapon(move: Int)

object Weapon {
  case object Rock extends Weapon(1)
  case object Paper extends Weapon(1)
  case object Scissors extends Weapon(1)
}