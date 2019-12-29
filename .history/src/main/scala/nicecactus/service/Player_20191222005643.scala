package nicecactus.service

import nicecactus.lib.IO
import nicecactus.model.Weapon
import Console._
import scala.util.Try

trait Player[F[_]] {
  def selectWeapon(): F[Option[Weapon]]
}

class HumanPlayer(val name: String) extends Player[IO] {

  override def selectWeapon(): IO[Option[Weapon]] =
    for {
      _ <- putStrLn(s"Dear $name, please select your weapon:")
      _ <- putStrLn("1 - Rock")
      _ <- putStrLn("2 - Paper")
      _ <- putStrLn("3 - Scissors")
      input <- getStrLn
      weapon = parseInt(input).flatMap(Weapon.apply).map { w =>
        putStrLn(s"$name selected $w")
        w
      }
    } yield weapon

  private def parseInt(s: String): Option[Int] = Try(s.toInt).toOption
}

class ComputerPlayer(name: String) extends Player[IO] {

  override def selectWeapon(): IO[Option[Weapon]] =
    nextInt(Weapon.numberOfWeapons).map(_ + 1).map(Weapon.apply)

  private def nextInt(upper: Int): IO[Int] =
    IO(() => scala.util.Random.nextInt(upper))
}
