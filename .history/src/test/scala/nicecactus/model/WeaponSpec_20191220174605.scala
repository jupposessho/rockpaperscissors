package nicecactus.model

import Weapon._
import org.scalatest.{ Matchers, WordSpecLike }

class WeaponSpec extends WordSpecLike with Matchers {

  "beats" should {
    "return None for the same Weapon" when {
      List(Rock, Paper, Scissors) foreach { w =>
        s"weapon is $w" in {
          w beats w shouldEqual None
        }
      }
    }
  }
}
