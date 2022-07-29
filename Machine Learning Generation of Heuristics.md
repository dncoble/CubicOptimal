This file is for writing out my plans for the machine learning portion of this project, when I get to that point.

The main idea is to generate simple 'symbolic coorinates' which come directly from the cube. Symbolic coordinates should not be large (maybe 3-100 elements). We will use a genetic algorithm to iteratively produce better heuristics. A general coordinate can be made by a Cartesian product of symbolic coordinates. I imagine a notation looking like this:

$h(a \bigotimes b \bigotimes c)\bigoplus h(d \bigotimes e \bigotimes f)$

Where what's in parentheses is a coordinate composed of the symbolic coordinates a, b, c, etc. $h$ represents taking a heuristic of that coordinate, and the 
$\bigoplus$ operator is actually the $\max$ operator. I think that makes the sequence actually look like a gene.

## Genetic iteration
The population of total heuristics will always be maintained at 100. These heuristics will have a maximum coordinate size, which is the sum of the sizes of all either composite coordinates. But I don't think the amount of coordinates needs to be restricted. New candidate heuristics will be generated from random combinations from the population. Each composite coordinate can be thought of as a gene. There will be a small chance of mutation, where a symbolic coordinate will be added, lost, or changed (contingent on their being room in the heuristic's coordinate size)

'Health' of the heuristic will be calculated by generating a large, random amount of cubes and testing the heuristic . To keep things consistent, the library of cubes will be kept constant throughout the experiment. 

## Important points
1. We can find the size of any symbolic coordinate by simple expansion.
2. Each base heuristic is max(x96) rotated. No symmetry is expected but it would be an evolutionary disadvantage.
3. This is not a solving algorithm, and does not require any solver (IDA*, etc). The heuristics will be used in a solver after the genetic process.

## Coordinates
The key property of a coordinate is what might be called 'expandability,' or 'completeness.' A coordinate must be its own self-contained puzzle s.t. when the cube is solved, the coordinate is also solved. That is what guarantees that the coordinate's distance to solved is always less than or equal to the cube's distance. Let $c$ be the operation which cast's a cube permutation to its corresponding coordinate. Then we say 

$c(P_2)c(P_1) = c(P_2P_1)$

This says,
1. Casting to the coordinate before or after multiplication is equivalent.
2. The coordinate can be considered a separate puzzle, with c(P_2) as an operator on c(P_1).

Now, if $P$ is in a vector representation, then we can say that the coordinate operator is represented by the matrix $C$.

$CP_2CP_1 = CP_2P_1$

which describes a projection matrix. 

We want coordinates to be combinations of simple coordinates. The most simple projection matrix is rank-1 and projects the input onto one vector.

$C_{simple}=uu^T$.

and all projection matrices are a sum of rank-1 projections.

$C = \sum_i u_iu_i^T$

Therefore, we have a way of generating all coordinates from simple coordinates, which each are defined by a single vector. As $P$ is an element in the binary space 
$(0, 1)^{n*n}$

the most logical choice of field for operations would have $u \in (0,1)^{n*n}$, though any other field could be chosen.
