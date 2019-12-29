package nicecactus.service

trait PlayerAlg {
  def selectWeapon(): IO[Option[Weapon]]
}
