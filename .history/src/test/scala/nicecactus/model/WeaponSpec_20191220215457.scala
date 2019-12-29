package nicecactus.model

import Weapon._
import org.scalatest.{ Matchers, WordSpecLike }

class WeaponSpec extends WordSpecLike with Matchers {

  "beats" should {

    val tieCases = List(Rock, Paper, Scissors)
    val trueCases = List((Rock, Scissors), (Paper, Rock), (Scissors, Paper))
    val allCases = tieCases.map(c => ((c, c), None)) ++
      trueCases.map(c => (c, Some(true))) ++
      trueCases.map(c => (c.swap, Some(false))) ++
      Nil

    "return the proper result" when {
      allCases foreach {
        case ((weapon, otherWeapon), result) =>
          s"$weapon beats $otherWeapon is $result" in {
            weapon beats otherWeapon shouldEqual result
          }
      }
    }
  }

  "apply" should {
    "create a proper instance" in {
      Weapon(1) shouldEqual Rock
    }
  }
}
