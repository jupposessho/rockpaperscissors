package nicecactus.service

trait PlayerAlg[F[_]] {
  def selectWeapon(): F[Option[Weapon]]
}
