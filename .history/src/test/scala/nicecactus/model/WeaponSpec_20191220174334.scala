package nicecactus.model

import Weapon._
import org.scalatest.{ Matchers, WordSpecLike }

class WeaponSpec extends WordSpecLike with Matchers {

  "beats" should {
    "return None for the same Weapon" when {
      "" in {
        Rock beats Rock shouldEqual None
      }
    }
  }
}
