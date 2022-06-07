working on:
1. add ability to time:
   1. cost of a node expansion.
   2. cost of moves and rotations.
2. Clean TableBuilder (don't remove it yet)
3. optimize coordinates, allow longs and heuristics/tables to accept longs

current bugs:
1. invert does not work, must add invert + move to TableBuilder?

after 0.3.0:
2. Clean TableBuilder (don't remove it yet)
3. optimize: CP and EP use ArrayList.indexOf and .add -- probably what is slowing this down a lot.

future work and notes:

make another cube class with simple boolean matrices 
and boolean multiplication, it may be faster.

implement SMA*
	make all search algorithms inherit

change everything possible from int, short -> byte
	table distances
	scrambles
	cubes
	nodes

experiment with garbage management, does that speed up performance?

are object files supposed to be saved as json?

look up Simulated Annealling

do statistical analysis for tree nodes with heuristic to guess distance. if h(p) = n, how many h(c_k)=n+1, n-1, n? What does that tell you about true distance? What about at different depths of analysis?

remove incorrect tables --rotated CO and EO

UI


| Table | Size |
|---|---|
| COTable | 33 kb |
| CPTable | 591 kb |
| EOTable | 31 kb |


ML generated coordinates:
what I want is something that iterates to produce more complecated coordinates each time. then it can make multiple at each iteration and choose the best based on some criterion function.
my ideal: a function is created from the coordinate basis. the values returned by the function are stored in a map to the distance values. the function is made more complicated. there are two problems with this:
1. i need to keep track of the size of this coordinate. the logic behind this would be pretty complicated unless i wanted to make a pretty restricted coordinate base.
2. i don't want to regenerate the coordinate each time. is there a way to have a rolling distance value just like the coordinate? now that i think of it, i just want to look at relaxing heuristics.
