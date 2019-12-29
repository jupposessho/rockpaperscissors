package nicecactus.model

sealed trait Weapon

object Weapon {
  case object Rock extends Weapon
  case object Paper extends Weapon
  case object Scissors extends Weapon
}
