package rockpaperscissors

import rockpaperscissors.lib.ProgramIO
import rockpaperscissors.service.RockPaperScissors
import rockpaperscissors.lib.IO

object Main extends App {

  implicit val ProgramIOInstance = ProgramIO

  RockPaperScissors().unsafeRun()
}
