package nicecactus

import scala.util.Try
import nicecactus.model.Weapon
import nicecactus.lib.IO
import nicecactus.service.Console._
import nicecactus.service.{ ComputerPlayer, HumanPlayer }

object RockPaperScissors extends App {

  val numberOfWeapons = Weapon.numberOfWeapons

  def gameLoop(name: String): IO[Unit] =
    for {
      playeyer1 <- new HumanPlayer(name).selectWeapon()
      playeyer2 <- new ComputerPlayer("name").selectWeapon()
      _ <- printResults(playeyer1, playeyer2)
      cont <- checkContinue(name)
      _ <- if (cont) gameLoop(name) else IO.point(())
    } yield ()

  def printResults(player1: Option[Weapon], player2: Option[Weapon]): IO[Unit] = (player1, player2) match {
    case (None, _)                      => putStrLn("Invalid weapon for Player1")
    case (_, None)                      => putStrLn("Invalid weapon for Player2")
    case (Some(weapon1), Some(weapon2)) => result(weapon1, weapon2)
  }
  // parseInt(input).fold(
  //   putStrLn("Please enter a number")
  // )(
  //   i =>
  //     Weapon(i).fold(
  //       putStrLn("Invalid weapon, try again!")
  //     ) { weapon =>
  //       val opponent = Weapon(num).get // TODO fix
  //       for {
  //         _ <- putStrLn(s"You selected: $weapon")
  //         _ <- putStrLn(s"Your opponent's weapon is $opponent")
  //         _ <- result(weapon, opponent)
  //       } yield ()
  //     }
  // )

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
