package nicecactus

import scala.util.Try
import scala.io.StdIn.readLine
import nicecactus.model.Weapon

object RockPaperScissors extends App {
  println("Welcome to the game! What is your name?")

  val name = readLine()

  var exec = true

  while (exec) {
    val num = scala.util.Random.nextInt(3) + 1

    println(s"Dear $name, please select your weapon from 1 to 3:")

    val guess = readLine().toInt

    Weapon(guess) match {
      case None => println("Invalid weapon, try again!")
      case Some(weapon) =>
        val opponent = Weapon(num).get
        weapon.beats(opponent) match {
          case None =>
            println("It's a tie!")
          case Some(result) if result == true =>
            println("Congratulations!")
          case _ =>
            println("Sorry, maybe next time!")
        }
    }

    println("Do you want to continue, " + name + "?")

    readLine() match {
      case "y" => exec = true
      case "n" => exec = false
    }
  }
}
