package nicecactus.model

import Weapon._
import org.scalatest.{ Matchers, WordSpecLike }

class WeaponSpec extends WordSpecLike with Matchers {

  "beats" should {

    val tieCases = List((Rock, Rock), (Paper, Paper), (Scissors, Scissors))

    val allCases = tieCases map (c => (c, None))

    "return None for the same Weapon" when {
      allCases foreach {
        case (weapon, otherWeapon, result) =>
          s"$weapon beats $otherWeapon is $result" in {
            weapon beats otherWeapon shouldEqual None
          }
      }
    }
  }
}
