package nicecactus.service

import nicecactus.lib.IO
import nicecactus.model.Weapon

trait PlayerAlg[F[_]] {
  def selectWeapon(): F[Option[Weapon]]
}

class HumanPlayer(name: String) extends PlayerAlg[IO] {
  override def selectWeapon(): IO[Option[Weapon]] = ???
}

class ComputerPlayer(name: String) extends PlayerAlg[IO] {
  override def selectWeapon(): IO[Option[Weapon]] = nextInt(numberOfWeapons).map(_ + 1).map(Weapon.apply)
}
