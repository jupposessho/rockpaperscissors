package nicecactus.model

import Weapon._
import org.scalatest.{ Matchers, WordSpecLike }

class WeaponSpec extends WordSpecLike with Matchers {

  "beats" should {

    val tieCases: List[Weapon] = List(Rock, Paper, Scissors)
    val trueCases: List[(Weapon, Weapon)] = List((Rock, Scissors), (Paper, Rock), (Scissors, Paper))

    val allCases: List[((Weapon, Weapon), Option[Boolean])] = tieCases.map(c => ((c, c), (None: Option[Boolean]))) :: trueCases.map(c => (c, Some(true))) :: Nil

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
