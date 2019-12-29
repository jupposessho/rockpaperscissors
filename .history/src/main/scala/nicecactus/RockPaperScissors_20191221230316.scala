package nicecactus

import scala.util.Try
import scala.io.StdIn.readLine
import nicecactus.model.Weapon

object RockPaperScissors extends App {

  case class IO[A](unsafeRun: () => A) { self =>
    def map[B](f: A => B): IO[B] = IO(() => f(self.unsafeRun()))

    def flatMap[B](f: A => IO[B]): IO[B] = IO(() => f(self.unsafeRun()).unsafeRun())
  }
  object IO {
    def point[A](a: => A): IO[A] = IO(() => a)
  }

  def putStrLn(line: String): IO[Unit] = IO(() => println(line))
  def getStrLn: IO[String] = IO(() => readLine())
  def nextInt(upper: Int): IO[Int] = IO(() => scala.util.Random.nextInt(upper))

  println("Welcome to the game! What is your name?")

  val name = readLine()

  var exec = true
  val numberOfWeapons = Weapon.numberOfWeapons

  def parseInt(s: String): Option[Int] = Try(s.toInt).toOption

  def gameLoop(name: String): IO[Unit] =
    for {
      num   <- nextInt(5).map(_ + 1)
      _     <- putStrLn(s"Dear $name, please select your weapon:")
      input <- getStrLn
      _     <- printResults(input, num, name)
      cont  <- checkContinue(name)
      _     <- if (cont) gameLoop(name) else finish(())
    } yield ()

  def printResults(input: String, num: Int, name: String): IO[Unit] =
    parseInt(input).fold(
      putStrLn("You did not enter a number")
    )(i =>
      Weapon(guess).fold(
        putStrLn("Invalid weapon, try again!")
      ){ weapon =>
        opponent = Weapon(num).get // TODO fix
        putStrLn(s"Your opponent's weapon is $opponent")
        weapon.beats(opponent) match {
        case None =>
          putStrLn("It's a tie!")
        case Some(result) if result == true =>
          putStrLn("Congratulations!")
        case _ =>
          putStrLn("Sorry, maybe next time!")
        }
    )
    
  while (exec) {
    val num = scala.util.Random.nextInt(numberOfWeapons) + 1

    println(s"Dear $name, please select your weapon:")
    println("1. Rock")
    println("2. Paper")
    println("3. Scissors")

    val result = for {
      guess <- IO(parseInt(readLine()))
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
