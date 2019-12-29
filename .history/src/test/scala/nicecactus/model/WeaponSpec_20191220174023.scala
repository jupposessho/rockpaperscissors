package nicecactus.model

import Weapon._
import org.scalatest.{ FlatSpec, Matchers }

class WeaponSpec extends FlatSpec with Matchers {

  "beats" should "return None for the same Weapon" in {
    Rock beats Rock shouldEqual None
  }
}
