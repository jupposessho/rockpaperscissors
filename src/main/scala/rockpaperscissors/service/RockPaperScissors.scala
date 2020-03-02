package rockpaperscissors.service

import rockpaperscissors.model.Weapon
import rockpaperscissors.lib.{ IO, Program }
import rockpaperscissors.lib.Program.syntax._
import rockpaperscissors.service._
import rockpaperscissors.service.Console._
import rockpaperscissors.service.Random._

object RockPaperScissors {

  def apply[F[_]: Program: Console: Random]() =
    for {
      _ <- Console[F].putStrLn("Welcome to the game! What is your name?")
      name <- Console[F].getStrLn
      _ <- gameLoop(name)
    } yield ()

  private def gameLoop[F[_]: Program: Console: Random](name: String): F[Unit] =
    for {
      players <- selectPlayers(name)
      weapon1 <- players._1.selectWeapon()
      weapon2 <- players._2.selectWeapon()
      _ <- printResults(players._1.name, weapon1, players._2.name, weapon2)
      cont <- checkContinue(name)
      _ <- if (cont) gameLoop(name) else Program[F].finish(())
    } yield ()

  private def printResults[F[_]: Program: Console](
      player1Name: String,
      player1Weapon: Option[Weapon],
      player2Name: String,
      player2Weapon: Option[Weapon]
    ): F[Unit] =
    (player1Weapon, player2Weapon) match {
      case (None, _) => Console[F].putStrLn(s"Invalid weapon for $player1Name")
      case (_, None) => Console[F].putStrLn(s"Invalid weapon for $player2Name")
      case (Some(weapon1), Some(weapon2)) =>
        for {
          _ <- Console[F].putStrLn(s"$player1Name selected $weapon1")
          _ <- Console[F].putStrLn(s"$player2Name selected $weapon2")
          _ <- result(player1Name, weapon1, player2Name, weapon2)
        } yield ()
    }

  private def result[F[_]: Program: Console](
      player1Name: String,
      player1Weapon: Weapon,
      player2Name: String,
      player2Weapon: Weapon
    ) =
    player1Weapon.beats(player2Weapon) match {
      case None                           => Console[F].putStrLn("It's a tie!")
      case Some(result) if result == true => Console[F].putStrLn(s"$player1Name won!")
      case _                              => Console[F].putStrLn(s"$player2Name won!")
    }

  private def checkContinue[F[_]: Program: Console](name: String): F[Boolean] =
    for {
      _ <- Console[F].putStrLn(s"Do you want to continue, $name? (y/n)")
      input <- Console[F].getStrLn.map(_.toLowerCase)
      cont <- input match {
        case "y" => Program[F].finish(true)
        case "n" => Program[F].finish(false)
        case _   => checkContinue(name)
      }
    } yield cont

  private def selectPlayers[F[_]: Program: Console: Random](name: String): F[(Player[F], Player[F])] =
    for {
      _ <- Console[F].putStrLn("What do you want to play?\n1 - Human vs Computer\n2 - Computer vs Computer\n")
      input <- Console[F].getStrLn
      players <- input match {
        case "1" => Program[F].finish((new HumanPlayer(name), new ComputerPlayer("Computer 2")))
        case "2" => Program[F].finish((new ComputerPlayer("Computer 1"), new ComputerPlayer("Computer 2")))
        case _   => selectPlayers(name)
      }
    } yield players
}
