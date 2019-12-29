package nicecactus

import scala.util.Try
import scala.io.StdIn.readLine
import nicecactus.model.Weapon

object RockPaperScissors extends App {
  println("Welcome to the game! What is your name?")

  val name = readLine()

  var exec = true
  val numberOfWeapons = Weapon.numberOfWeapons

  while (exec) {
    val num = scala.util.Random.nextInt(numberOfWeapons) + 1

    println(s"Dear $name, please select your weapon from 1 to $numberOfWeapons:")

    val guess = readLine().toInt

    val result = for {
      weapon <- Weapon(guess)
      _ = println(s"Your selected weapon is $weapon")
      opponent <- Weapon(num)
      _ = println(s"Your opponent's weapon is $opponent")
      _ = weapon.beats(opponent) match {
        case None =>
          println("It's a tie!")
        case Some(result) if result == true =>
          println("Congratulations!")
        case _ =>
          println("Sorry, maybe next time!")
      }
    } yield ()

    if (result.isEmpty) println("Invalid weapon, try again!")

    println("Do you want to continue, " + name + "?")

    readLine() match {
      case "y" => exec = true
      case "n" => exec = false
    }
  }
}
