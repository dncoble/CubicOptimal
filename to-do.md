right now i am:
* improving makeTable and making sym tables to get those up and working
* creating a better Cube class with permutation 

future work and notes:

make another cube class with simple boolean matrices 
and boolean multiplication. it has the potential to be faster.

implement SMA*
	make all search algorithms inherit

change everything possible from int, short -> byte
	table distances
	scrambles
	cubes
	nodes

makeTable right now either requires setCoord or to save cubes, but i think it can be done better by using fancy search stuff

test rotate works by rotating an identity cube, making sure it is still identity afterward.

experiment with garbage management, does that speed up performance?

are object files supposed to be saved as json?

clean up code
	can we do implements, inherits?
	rename all those badly named classes
	make Node classes private within their respective search algorithm

look up Simulated Annealling

do statistical analysis for tree nodes with heuristic to guess distance. if h(p) = n, how many h(c_k)=n+1, n-1, n? What does that tell you about true distance? What about at different depths of analysis?

remove incorrect tables --rotated CO and EO

UI

and if you do all that, you can look for optimizations
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
