# OpenHex
Open-Source hexagonal java jME 3 library

## Original goal of this project

_Written by MisterCavespider_

The original goal for this project was to create a game library for games that featured hexagons.
Currently, the project is packed with all sorts of generic classes and interfaces.
The project was supposed to include entities (game objects) through an entity-component-system (ECS), full support for different style maps/boards (made up out of individual tiles), and support for AI/pathfinding.

The most notable features are the HexTerrainMesh, and the Vectors.

### HexTerrainMesh
The HexTerrainMesh is optimized to display an entire field hexagons.
It does this by iterating over the field, adding a hex tile or a wall where necessary. 
This process is not supposed to happen to frequently. Because of that, the Board object (data holder for tiles) is told to 'lock' itself, thereby not allowing any changes. It will then call the process once.
It uses different colors for walls and floors (blue and red, respectively), what could be used in a custom shader to optimize textures.

This means it can draw a lot of hexagons really fast.

### Vectors
I kinda went over the top with this.

Hexagons aligned in a raster use different axes than normal objects. Hexagons can either use Axial (2 axes) or Cube (3 axes) coordinates, might have a height variable, and can use either floats (Dynamic) or ints (Static). This resulted in 4 different vectors, using 2 different precisions, and 2 different dimension counts (Axial vectors are 3d, Cube vectors are 4d). I wanted to use 4 different classes for this, but I quickly learnt that Java generic parameters and reflection are not very fun to work with.

## Why this project is abandoned
This project simply started too big. Although the goal was to create a game **library**, the result was more a game **engine**, with classes that control logic and stuff.

I do want to come back and clean this project up one day. I would like to make it slimmer, featuring code that can be used _along side_ jME, not code that is _built on top_ of jME.
