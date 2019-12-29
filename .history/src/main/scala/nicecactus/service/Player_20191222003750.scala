package nicecactus.service

import nicecactus.lib.IO
import nicecactus.model.Weapon

trait PlayerAlg[F[_]] {
  def selectWeapon(): F[Option[Weapon]]
}

class HumanPlayer(name: String) extends PlayerAlg[IO] {

  override def selectWeapon(): IO[Option[Weapon]] = ???

  def putStrLn(line: String): IO[Unit] = IO(() => println(line))
  def getStrLn: IO[String] = IO(() => readLine())
}

class ComputerPlayer(name: String) extends PlayerAlg[IO] {

  override def selectWeapon(): IO[Option[Weapon]] =
    nextInt(Weapon.numberOfWeapons).map(_ + 1).map(Weapon.apply)

  private def nextInt(upper: Int): IO[Int] =
    IO(() => scala.util.Random.nextInt(upper))
}
