package nicecactus

import nicecactus.lib.ProgramIO
import nicecactus.service.RockPaperScissors
import nicecactus.lib.IO

object Main extends App {

  implicit val ProgramIOInstance = ProgramIO

  RockPaperScissors().unsafeRun()
}
