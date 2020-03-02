package rockpaperscissors.model

import Weapon._
import org.scalatest.{ Matchers, WordSpecLike }

class WeaponSpec extends WordSpecLike with Matchers {

  "beats" should {

    val tieCases = Rock :: Paper :: Scissors :: Nil
    val trueCases = (Rock, Scissors) :: (Paper, Rock) :: (Scissors, Paper) :: Nil
    val allCases = tieCases.map(c => ((c, c), None)) ++
      trueCases.map(c => (c, Some(true))) ++
      trueCases.map(c => (c.swap, Some(false))) ++
      Nil

    "return the proper result" when {
      allCases foreach {
        case ((weapon, otherWeapon), result) =>
          s"$weapon beats $otherWeapon is $result" in {
            weapon beats otherWeapon shouldBe result
          }
      }
    }
  }

  "apply" should {
    (1, Rock) :: (2, Paper) :: (3, Scissors) :: Nil foreach {
      case (move, weapon) =>
        s"create a $weapon from a valid move: $move" in {
          Weapon(move) shouldBe Some(weapon)
        }
    }

    "return None" when {
      "move is invalid" in {
        Weapon(0) shouldBe None
      }
    }
  }

  "numberOfWeapons" should {
    "return the number of available weapons: 3" in {
      numberOfWeapons shouldBe 3
    }
  }
}
