package nicecactus.service

import scala.io.StdIn.readLine
import nicecactus.lib.IO

object Console {
  def putStrLn(line: String): IO[Unit] = IO(() => println(line))
  def getStrLn: IO[String] = IO(() => readLine())
}
