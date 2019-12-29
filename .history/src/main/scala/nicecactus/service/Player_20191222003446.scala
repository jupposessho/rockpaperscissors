package nicecactus.service

import nicecactus.lib.IO
import nicecactus.model.Weapon

trait PlayerAlg[F[_]] {
  def selectWeapon(): F[Option[Weapon]]
}

class HumanPlayer(name: String) extends PlayerAlg[IO] {
  override def selectWeapon(): F[Option[Weapon]] = ???
}
