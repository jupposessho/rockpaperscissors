package rockpaperscissors

import scala.io.StdIn.readLine
import rockpaperscissors.lib.IO
import rockpaperscissors.lib.Program.syntax._

package object service {

  implicit val ConsoleIO = new Console[IO] {
    def putStrLn(line: String): IO[Unit] = IO(() => println(line))
    def getStrLn: IO[String] = IO(() => readLine())
  }

  implicit val RandomIO = new Random[IO] {
    def nextInt(upper: Int): IO[Int] = IO(() => scala.util.Random.nextInt(upper))
  }
}
