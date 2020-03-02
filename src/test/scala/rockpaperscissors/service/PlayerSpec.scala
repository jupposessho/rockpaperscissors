package rockpaperscissors.service

import rockpaperscissors.model.Weapon.Scissors
import TestIO._
import org.scalatest.{ Matchers, WordSpecLike }

class PlayerSpec extends WordSpecLike with Matchers {

  val testData = TestData(
    input = "3" :: Nil,
    output = Nil,
    nums = 2 :: Nil
  )

  "selectWeapon" should {

    "return random weapon" when {
      "computer player selected a valid weapon" in {
        val player = new ComputerPlayer("name")
        checkResult(player, testData) shouldBe Some(Scissors)
      }
    }

    "return None" when {

      "computer player selected invalid weapon" in {
        val player = new ComputerPlayer("name")
        checkResult(player, testData.copy(nums = 99 :: Nil)) shouldBe None
      }

      "human player selected invalid weapon" in {
        val player = new HumanPlayer("name")
        checkResult(player, testData.copy(input = "99" :: Nil)) shouldBe None
      }
    }

    "return selected weapon" when {
      "human player selected a valid weapon" in {
        val player = new HumanPlayer("name")
        checkResult(player, testData) shouldBe Some(Scissors)
      }
    }

    "print available weapons" in {
      val player = new HumanPlayer("name")
      val expectedOutput = "Dear name, please select your weapon:\n1 - Rock\n2 - Paper\n3 - Scissors"
      checkOutput(player, testData) shouldBe expectedOutput
    }
  }

  private def checkResult(player: Player[TestIO], testData: TestData) =
    player
      .selectWeapon()
      .run(testData)
      ._2

  private def checkOutput(player: Player[TestIO], testData: TestData) =
    player
      .selectWeapon()
      .eval(testData)
      .showResults
}
