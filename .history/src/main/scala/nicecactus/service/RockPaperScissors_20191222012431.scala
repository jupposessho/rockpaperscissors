package nicecactus.service

import nicecactus.model.Weapon
import nicecactus.lib.IO
import nicecactus.service.Console._
import nicecactus.service.{ ComputerPlayer, HumanPlayer, Player }

object RockPaperScissors {

  private def gameLoop(
      name: String,
      player1: Player[IO],
      player2: Player[IO]
    ): IO[Unit] =
    for {
      p1 <- player1.selectWeapon()
      p2 <- player2.selectWeapon()
    //   _ <- printResults(p1, p2)
    (p1, p2) match {
      case (None, _) => putStrLn("Invalid weapon for Player1")
      case (_, None) => putStrLn("Invalid weapon for Player2")
      case (Some(weapon1), Some(weapon2)) =>
        for {
          _ <- putStrLn(s"$player1.name selected $weapon1")
          _ <- putStrLn(s"$player2.name selected $weapon2")
          _ <- result(weapon1, weapon2)
        } yield ()
    }
      cont <- checkContinue(name)
      _ <- if (cont) gameLoop(name, player1, player2) else IO.point(())
    } yield ()

  private def printResults(player1: Option[Weapon], player2: Option[Weapon]): IO[Unit] =
    (player1, player2) match {
      case (None, _) => putStrLn("Invalid weapon for Player1")
      case (_, None) => putStrLn("Invalid weapon for Player2")
      case (Some(weapon1), Some(weapon2)) =>
        for {
          _ <- putStrLn(s"$player1.name selected $weapon1")
          _ <- putStrLn(s"$player2.name selected $weapon2")
          _ <- result(weapon1, weapon2)
        } yield ()
    }

  private def result(playerWeapon: Weapon, opponentWeapon: Weapon) =
    playerWeapon.beats(opponentWeapon) match {
      case None                           => putStrLn("It's a tie!")
      case Some(result) if result == true => putStrLn("Congratulations!")
      case _                              => putStrLn("Sorry, maybe next time!")
    }

  private def checkContinue(name: String): IO[Boolean] =
    for {
      _ <- putStrLn("Do you want to continue, " + name + "? (y/n)")
      input <- getStrLn.map(_.toLowerCase)
      cont <- input match {
        case "y" => IO.point(true)
        case "n" => IO.point(false)
        case _   => checkContinue(name)
      }
    } yield cont

  def start =
    for {
      _ <- putStrLn("Welcome to the game! What is your name?")
      name <- getStrLn
      _ <- gameLoop(name, new HumanPlayer(name), new ComputerPlayer("name"))
    } yield ()
}
