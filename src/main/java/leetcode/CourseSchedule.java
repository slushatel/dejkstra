package leetcode;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CourseSchedule {
	/*
	207. Course Schedule
Medium

There are a total of n courses you have to take, labeled from 0 to n-1.

Some courses may have prerequisites, for example to take course 0 you have to first take course 1, which is expressed as a pair: [0,1]

Given the total number of courses and a list of prerequisite pairs, is it possible for you to finish all courses?

Example 1:

Input: 2, [[1,0]]
Output: true
Explanation: There are a total of 2 courses to take.
             To take course 1 you should have finished course 0. So it is possible.

Example 2:

Input: 2, [[1,0],[0,1]]
Output: false
Explanation: There are a total of 2 courses to take.
             To take course 1 you should have finished course 0, and to take course 0 you should
             also have finished course 1. So it is impossible.

Note:

    The input prerequisites is a graph represented by a list of edges, not adjacency matrices. Read more about how a graph is represented.
    You may assume that there are no duplicate edges in the input prerequisites.


	 */

	private Map<Integer, Set<Integer>> edges;
	boolean[] visited;

	public boolean canFinish(int numCourses, int[][] prerequisites) {
		visited = new boolean[numCourses];
		edges = new HashMap();
		for (int[] prerequisite : prerequisites) {
			edges.put(prerequisite[0], edges.computeIfAbsent(prerequisite[0], k->new HashSet<>()));
		}

		for (int i = 0; i < numCourses; i++) {
			if (visited[i]) continue;
			if (!dfs(i, new boolean[numCourses])) return false;
		}
		return true;
	}

	private boolean dfs(int startVertex, boolean[] marked) {
		if (marked[startVertex]) return false;
		visited[startVertex] = true;
		marked[startVertex] = true;

		if (edges.get(startVertex) == null) return true;
		for (Integer nextVertex : edges.get(startVertex)) {
			if (!dfs(nextVertex, marked)) return false;
		}

		marked[startVertex] = false;
		return true;
	}


}
