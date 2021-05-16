# CubicOptimal0.2
After a long time away, I am starting to work on this again. It's final goal is to be an optimal solver, but it's a winding path. I'm doing experimenting, and building out where I like. Expect very dynamic code; I'm changing a lot of things, including class names and project architecture. 

#The folders
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