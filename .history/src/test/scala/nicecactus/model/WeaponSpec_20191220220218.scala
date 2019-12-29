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
    List((1, Rock), (2, Paper), (3, Scissors)) foreach {
      case (move, weapon) =>
        s"create a $weapon from a valid move: $move" in {
          Weapon(move) shouldEqual Some(weapon)
        }
    }

    "return None" when {
      "move is invalid" in {
        Weapon(0) shouldEqual None
      }
    }
  }

  "numberOfWeapons" should {
    "return the number of available weapons: 3" in {
      numberOfWeapons shouldEqual 3
    }
  }
}
