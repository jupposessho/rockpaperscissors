package rockpaperscissors.service

import rockpaperscissors.lib.Program
import rockpaperscissors.lib.Program.syntax._
import rockpaperscissors.model.Weapon
import Console._
import scala.util.Try

trait Player[F[_]] {
  def selectWeapon(): F[Option[Weapon]]
  def name(): String
}

class HumanPlayer[F[_]: Program: Console](name: String) extends Player[F] {

  override def selectWeapon(): F[Option[Weapon]] =
    for {
      _ <- Console[F].putStrLn(s"Dear $name, please select your weapon:")
      _ <- Console[F].putStrLn("1 - Rock")
      _ <- Console[F].putStrLn("2 - Paper")
      _ <- Console[F].putStrLn("3 - Scissors")
      input <- Console[F].getStrLn
      weapon = parseInt(input).flatMap(Weapon.apply)
    } yield weapon

  override def name(): String = name

  private def parseInt(i: String): Option[Int] = Try(i.toInt).toOption
}

class ComputerPlayer[F[_]: Program: Console: Random](name: String) extends Player[F] {

  override def selectWeapon(): F[Option[Weapon]] =
    nextInt(Weapon.numberOfWeapons)
      .map(_ + 1)
      .map(Weapon.apply)

  override def name(): String = name

  private def nextInt(upper: Int): F[Int] =
    Random[F].nextInt(upper)
}
