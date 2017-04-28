# OpenHex
Open-Source hexagonal java jME 3 library

No further information

## Usage
- 'Starting' the Game
```
new Game();
```
- Getting the Game
```
Game.get()
```
- Getting the Board
```
Game.get().getBoard()
```
- adding a Tile
```
board.addTile(new HexTile(new VectorAS(), new ResourceDescriptor()))
```
- adding a Unit
```
UnitFactory.createUnit(new Identifier(), UUID.randomUUID(), new ResourceDescriptor())
```
