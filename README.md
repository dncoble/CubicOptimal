# CubicOptimal
After a long time away, I am starting to work on this again. It's final goal is to be an optimal solver, but it's a winding path. I'm doing experimenting, and building out where I like. Expect very dynamic code; I'm changing a lot of things, including class names and project architecture. 

# The folders
each have a single letter name. Here's what they are and what they contain:

/c contains the basics of the puzzle, including Cube, and Scramble. Of course the goal of the project is to find a Scramble of minimum size. I guess I should mention that I'm following the half-turn metric: 180 degree turns are allowed, but not slice moves.

/h contains heuristics. a heuristic takes in a Cube and returns an estimation of how many moves it will take to solve that cube. All heuristics are complete, meaning that they never overestimate the actual amount of moves. This is a good property for search algorithms.

/s contains search algorithms. They take do the actual work of finding the optimal scramble.

/q contains coordinates. A coordinate is just a different way to chop up a cube mathematically. There are very few rules for what a coordinate can be, but to be useful here are three that the ones I use follow:
1. A coordinate's set of values is closed (graphing them, with moves as edges, one can reach every value from every other value)
2. A coordinate has some defined solved value, and when the coordinate value being solved is necessary (but not sufficient) for the whole cube to be solved.
3. A coordinate could return a vector value, but I prefer integer values. Bonus points if solved is integer 0.

There are also /main and /defunct. main contains two classes, each with a main method. One, Solver, is for developing the optimal solver. The other, Researcher, is for doing experiments. /defunct contains code that at one point I was using, but now don't need. Now that I have this git and am doing proper version control, I probably won't need it for too much longer.

Fair warning, my code contains a lot of comments, and not all of them are exactly professional. I've been making this over 2 years now (starting in high school) I think its funny, and rarely delete comments in case they say something important. Sometimes when code was moved to different classes I moved comments with them, and then they no longer make sense.


# Working coordinates and heuristics

The four coordinates most commonly used to describe a cube are orientation and permutation of the corner and edge pieces. CO and EO can't be rotated, however, so rotatable corner orientation and rotatable edge orientation contain added information from the permutation so that the coordinates can be rotated into sym coordinates.

CO: corner orientation \
CP: corner permutation \
EO: edge orientation \
EP: edge permutation \
RCO: rotatable corner orientation \REO: rotatable edge orientation \
Kociemba: coordinate used in Kociemba algorithm and Kociemba optimal solver.

PP = Predictive power

| Coordinates | Raw | Sym | Raw Size | Sym Size | avg. PP | max PP |
|----|---|---|----|---|---|---|
| CO |Yes| - |2187| - | 4.459 | 6 |
| CP |Yes|Yes|40321| 984 | 4.723 | 7 |
| EO |Yes| - |2048| - | 4.609 | 7 |
| EP |Too big|   |479001599| |
|RCO |Yes| Yes |153090|3393| 6.430 | 9 |
|REO |Too big|Yes|70963200|1482170| 8.247 | 11 |
| Kociemba| Not even close | No | 2217093120 | | | |

** avg. PP is for raw if available, else sym

See more in Coordinates Heuristics.md

# Current version: 0.3.1
Plan for versions:

0.2.1: Complete structure of how packages c, s, h, and q interact.

0.3.0: Restructure Cube class with permutations. Rotations x48 work.

0.3.1: Rotations x96 work.

0.3.2: Restructure coordinate calculations to be faster, including breaking 0 = solved.

0.3.3: Create power and lexicographic heuristics.

0.3.4: Implement SMA* search.

0.4.0: Coordinate class able to receive permutation input and perform operations on it.

0.5.0: Machine learning to generate heuristics.

See to-do.md for an unorganized list of things I plan on doing.

1.0.0 released when it's able to optimally solve cubes up to length 20. Current ability: 11.
