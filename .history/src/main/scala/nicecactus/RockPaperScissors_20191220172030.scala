package nicecactus

import scala.util.Try
import scala.io.StdIn.readLine

object RockPaperScissors extends App {
  println("Welcome to the game! What is your name?")

  val name = readLine()

  var exec = true

  while (exec) {
    val num = scala.util.Random.nextInt(5) + 1

    println(s"Dear $name, please select your weapon from 1 to 3:")

    val guess = readLine().toInt

    if (guess == num) println("You guessed right, " + name + "!")
    else println("You guessed wrong, " + name + "! The number was: " + num)

    println("Do you want to continue, " + name + "?")

    readLine() match {
      case "y" => exec = true
      case "n" => exec = false
    }
  }
}
