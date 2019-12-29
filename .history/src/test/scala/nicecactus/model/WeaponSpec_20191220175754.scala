package nicecactus.model

import Weapon._
import org.scalatest.{ Matchers, WordSpecLike }

class WeaponSpec extends WordSpecLike with Matchers {

  "beats" should {

    val tieCases = List((Rock, Rock), (Paper, Paper), (Scissors, Scissors))

    "return None for the same Weapon" when {
      tieCases foreach {
        case (weapon, otherWeapon) =>
          s"$weapon beats $otherWeapon" in {
            weapon beats otherWeapon shouldEqual None
          }
      }
    }
  }
}
