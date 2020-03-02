package rockpaperscissors.service

import rockpaperscissors.model.Weapon
import rockpaperscissors.model.Weapon._
import TestIO._
import org.scalatest.{ Matchers, WordSpecLike }

class RockPaperScissorsSpec extends WordSpecLike with Matchers {

  val welcome = "Welcome to the game! What is your name?"
  val selectPlay = "What do you want to play?\n1 - Human vs Computer\n2 - Computer vs Computer\n"
  val tie = "It's a tie!"

  val name = "Joe"

  "the game" should {

    "be a tie" when {
      "human and computer select the same weapon" in {
        val testData = TestData(
          input = name :: "1" :: "3" :: "n" :: Nil,
          output = Nil,
          nums = 2 :: Nil
        )
        val expectedOutput =
          welcome ::
            selectPlay ::
            humanSelect() ::
            selected(Scissors) ::
            selected(Scissors, "Computer 2") ::
            tie ::
            continue() ::
            Nil

        runTest(testData, expectedOutput)
      }
    }

    "won by human player" when {
      "human's weapon beats computer's weapon" in {
        val testData = TestData(
          input = name :: "1" :: "1" :: "n" :: Nil,
          output = Nil,
          nums = 2 :: Nil
        )
        val expectedOutput =
          welcome ::
            selectPlay ::
            humanSelect() ::
            selected(Rock) ::
            selected(Scissors, "Computer 2") ::
            won() ::
            continue() ::
            Nil

        runTest(testData, expectedOutput)
      }
    }

    "won by computer" when {
      "computer's weapon beats human's weapon" in {
        val testData = TestData(
          input = name :: "1" :: "1" :: "n" :: Nil,
          output = Nil,
          nums = 1 :: Nil
        )
        val expectedOutput =
          welcome ::
            selectPlay ::
            humanSelect() ::
            selected(Rock) ::
            selected(Paper, "Computer 2") ::
            won("Computer 2") ::
            continue() ::
            Nil

        runTest(testData, expectedOutput)
      }
    }

    "ask if the player wants to play a new game" when {
      "invalid weapon selected" in {
        val testData = TestData(
          input = name :: "1" :: "99" :: "n" :: Nil,
          output = Nil,
          nums = 2 :: Nil
        )
        val expectedOutput =
          welcome ::
            selectPlay ::
            humanSelect() ::
            invalid() ::
            continue() ::
            Nil

        runTest(testData, expectedOutput)
      }
    }

    "enable to repeat the game" when {
      "invalid weapon selected by the human player" in {
        val testData = TestData(
          input = name :: "1" :: "99" :: "y" :: "1" :: "3" :: "n" :: Nil,
          output = Nil,
          nums = 2 :: 2 :: Nil
        )
        val expectedOutput =
          welcome ::
            selectPlay ::
            humanSelect() ::
            invalid() ::
            continue() ::
            selectPlay ::
            humanSelect() ::
            selected(Scissors) ::
            selected(Scissors, "Computer 2") ::
            tie ::
            continue() ::
            Nil

        runTest(testData, expectedOutput)
      }

      "invalid weapon selected by the computer" in {
        val testData = TestData(
          input = name :: "1" :: "3" :: "y" :: "1" :: "3" :: "n" :: Nil,
          output = Nil,
          nums = 99 :: 2 :: Nil
        )
        val expectedOutput =
          welcome ::
            selectPlay ::
            humanSelect() ::
            invalid("Computer 2") ::
            continue() ::
            selectPlay ::
            humanSelect() ::
            selected(Scissors) ::
            selected(Scissors, "Computer 2") ::
            tie ::
            continue() ::
            Nil

        runTest(testData, expectedOutput)
      }
    }

    "enable computer vs computer game" in {
      val testData = TestData(
        input = name :: "2" :: "n" :: Nil,
        output = Nil,
        nums = 2 :: 2 :: Nil
      )
      val expectedOutput =
        welcome ::
          selectPlay ::
          selected(Scissors, "Computer 1") ::
          selected(Scissors, "Computer 2") ::
          tie ::
          continue() ::
          Nil

      runTest(testData, expectedOutput)
    }

    "enable game after invalid game type" in {
      val testData = TestData(
        input = name :: "invalid" :: "2" :: "n" :: Nil,
        output = Nil,
        nums = 2 :: 2 :: Nil
      )
      val expectedOutput =
        welcome ::
          selectPlay ::
          selectPlay ::
          selected(Scissors, "Computer 1") ::
          selected(Scissors, "Computer 2") ::
          tie ::
          continue() ::
          Nil

      runTest(testData, expectedOutput)
    }

    "enable to continue game after invalid answer" in {
      val testData = TestData(
        input = name :: "2" :: "invalid" :: "n" :: Nil,
        output = Nil,
        nums = 2 :: 2 :: Nil
      )
      val expectedOutput =
        welcome ::
          selectPlay ::
          selected(Scissors, "Computer 1") ::
          selected(Scissors, "Computer 2") ::
          tie ::
          continue() ::
          continue() ::
          Nil

      runTest(testData, expectedOutput)
    }

    "enable to set player's name" in {
      val otherName = "John Doe"
      val testData = TestData(
        input = otherName :: "1" :: "3" :: "n" :: Nil,
        output = Nil,
        nums = 2 :: Nil
      )
      val expectedOutput =
        welcome ::
          selectPlay ::
          humanSelect(otherName) ::
          selected(Scissors, otherName) ::
          selected(Scissors, "Computer 2") ::
          tie ::
          continue(otherName) ::
          Nil

      runTest(testData, expectedOutput)
    }

    "enable to play different games" in {
      val testData = TestData(
        input = name :: "1" :: "3" :: "y" :: "2" :: "n" :: Nil,
        output = Nil,
        nums = 2 :: 1 :: 2 :: Nil
      )
      val expectedOutput =
        welcome ::
          selectPlay ::
          humanSelect() ::
          selected(Scissors) ::
          selected(Scissors, "Computer 2") ::
          tie ::
          continue() ::
          selectPlay ::
          selected(Paper, "Computer 1") ::
          selected(Scissors, "Computer 2") ::
          won("Computer 2") ::
          continue() ::
          Nil

      runTest(testData, expectedOutput)
    }
  }

  private def continue(name: String = name) =
    s"Do you want to continue, $name? (y/n)"

  private def invalid(name: String = name) =
    s"Invalid weapon for $name"

  private def won(name: String = name) =
    s"$name won!"

  private def humanSelect(name: String = name) =
    (s"Dear $name, please select your weapon:" ::
      "1 - Rock" ::
      "2 - Paper" ::
      "3 - Scissors" :: Nil).mkString("\n")

  private def selected(weapon: Weapon, name: String = name) =
    s"$name selected $weapon"

  private def runTest(testData: TestData, expectedOutput: List[String]) =
    RockPaperScissors()
      .eval(testData)
      .showResults shouldBe expectedOutput.mkString("\n")
}
