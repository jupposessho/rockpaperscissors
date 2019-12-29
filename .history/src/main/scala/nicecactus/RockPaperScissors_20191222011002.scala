package nicecactus

import scala.util.Try
import nicecactus.model.Weapon
import nicecactus.lib.IO
import nicecactus.service.Console._
import nicecactus.service.{ ComputerPlayer, HumanPlayer, Player }

object Main extends App {

  RockPaperScissors.start.unsafeRun()
}
