# Rock Paper Scissors game

This is an implementation of the Rock Paper Scissors game: [Description](https://en.wikipedia.org/wiki/Rock_paper_scissors)
I used the algorithm, based on parity:
"Rankings in rock paper scissors Spock lizard may be modeled by a comparison of the parity of the two choices. If it is the same (two odd-numbered moves or two even-numbered ones) then the lower number wins, while if they are different (one odd and one even) the higher wins."

Due to the library restrictions some boilerplate had to be implemented(/nicecactus.lib). That IO could be replace by any modern effect system like Cats IO, Monix Task or ZIO. A Monad instance could be used for replacing Program and State monad for replacing TestIO.

## To run the program

`sbt run`

## To run the tests

`sbt test`

## To extend the game

1. Declare a move N+1 (where N is the original total of moves) that beats all existing odd-numbered moves and loses to the others (for example, the rock (#1), scissors (#3), and lizard (#5) could fall into the German well (#6), while the paper (#2) covers it and Spock (#4) manipulates it).
1. Declare another move N+2 with the reverse property (such as a plant (#7) that grows through the paper (#2), poisons Spock (#4), and grows through the well (#6), while being damaged by the rock (#1), scissors (#3), and lizard (#5)).

The new weapons must be added in nicecactus.model.Weapon and the numberOfWeapons value should be increased by the number of the added weapons.

To display the new available weapons to the human player, the nicecactus.service.HumanPlayer::selectWeapon method should be extended.
