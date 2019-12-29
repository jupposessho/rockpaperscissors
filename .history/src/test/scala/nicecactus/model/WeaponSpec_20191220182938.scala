package nicecactus.model

import Weapon._
import org.scalatest.{ Matchers, WordSpecLike }

class WeaponSpec extends WordSpecLike with Matchers {

  "beats" should {

    val tieCases = List(Rock, Paper, Scissors).map(c => ((c, c), None))
    val trueCases = List((Rock, Scissors), (Paper, Rock), (Scissors, Paper)).map(c => (c, Some(true)))
    val falseCases = trueCases.map(c => (c.swap, Some(false)))
    val allCases = tieCases ++ trueCases ++ falseCases ++ Nil

    "return None for the same Weapon" when {
      allCases foreach {
        case ((weapon, otherWeapon), result) =>
          s"$weapon beats $otherWeapon is $result" in {
            weapon beats otherWeapon shouldEqual result
          }
      }
    }
  }
}
