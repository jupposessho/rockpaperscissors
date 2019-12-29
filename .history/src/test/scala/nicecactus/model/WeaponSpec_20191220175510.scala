package nicecactus.model

import Weapon._
import org.scalatest.{ Matchers, WordSpecLike }

class WeaponSpec extends WordSpecLike with Matchers {

  "beats" should {

    val tieCases: List[(Weapon, Weapon)] = List((Rock, Rock), (Paper, Paper), (Scissors, Scissors))

    "return None for the same Weapon" when {
      tieCases foreach { (weapon: Weapon, otherWeapon: Weapon) =>
        s"weapon is $weapon" in {
          weapon beats otherWeapon shouldEqual None
        }
      }
    }

    // "return Some(true)" when {

    // }
  }
}
