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

  val numberOfWeapons = Weapon.numberOfWeapons

  def parseInt(s: String): Option[Int] = Try(s.toInt).toOption

  def human(name: String): IO[Option[Weapon]] = {
    for {
      _ <- putStrLn(s"Dear $name, please select your weapon:")
      _ <- putStrLn("1 - Rock")
      _ <- putStrLn("2 - Paper")
      _ <- putStrLn("1 - Scissors")
      input <- getStrLn
      weapon = parseInt(input).flatMap(Weapon.apply)
    } yield weapon
  }

  def gameLoop(name: String): IO[Unit] =
    for {
      num <- nextInt(numberOfWeapons).map(_ + 1)
      _ <- putStrLn(s"Dear $name, please select your weapon:")
      _ <- putStrLn("1 - Rock")
      _ <- putStrLn("2 - Paper")
      _ <- putStrLn("1 - Scissors")
      input <- getStrLn
      _ <- printResults(input, num, name)
      cont <- checkContinue(name)
      _ <- if (cont) gameLoop(name) else IO.point(())
    } yield ()

  def printResults(
      input: String,
      num: Int,
      name: String
    ): IO[Unit] =
    parseInt(input).fold(
      putStrLn("Please enter a number")
    )(
      i =>
        Weapon(i).fold(
          putStrLn("Invalid weapon, try again!")
        ) { weapon =>
          val opponent = Weapon(num).get // TODO fix
          for {
            _ <- putStrLn(s"You selected: $weapon")
            _ <- putStrLn(s"Your opponent's weapon is $opponent")
            _ <- result(weapon, opponent)
          } yield ()
        }
    )

  def result(playerWeapon: Weapon, opponentWeapon: Weapon) = playerWeapon.beats(opponentWeapon) match {
    case None                           => putStrLn("It's a tie!")
    case Some(result) if result == true => putStrLn("Congratulations!")
    case _                              => putStrLn("Sorry, maybe next time!")
  }

  def checkContinue(name: String): IO[Boolean] =
    for {
      _ <- putStrLn("Do you want to continue, " + name + "? (y/n)")
      input <- getStrLn.map(_.toLowerCase)
      cont <- input match {
        case "y" => IO.point(true)
        case "n" => IO.point(false)
        case _   => checkContinue(name)
      }
    } yield cont

  val program = for {
    _ <- putStrLn("Welcome to the game! What is your name?")
    name <- getStrLn
    _ <- gameLoop(name)
  } yield ()

  program.unsafeRun()
}