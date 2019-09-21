package leetcode;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class FlattenNestedListIterator {
	/*
	341. Flatten Nested List Iterator
Medium

Given a nested list of integers, implement an iterator to flatten it.

Each element is either an integer, or a list -- whose elements may also be integers or other lists.

Example 1:

Input: [[1,1],2,[1,1]]
Output: [1,1,2,1,1]
Explanation: By calling next repeatedly until hasNext returns false,
             the order of elements returned by next should be: [1,1,2,1,1].

Example 2:

Input: [1,[4,[6]]]
Output: [1,4,6]
Explanation: By calling next repeatedly until hasNext returns false,
             the order of elements returned by next should be: [1,4,6].


	 */
	public static void main(String[] args) {
		{
			List<NestedInteger> list = new LinkedList<>();
			list.add(new NestedIntegerImpl(1));

			List<NestedInteger> nestedList = new LinkedList<>();
			list.add(new NestedIntegerImpl(nestedList));

			nestedList.add(new NestedIntegerImpl(4));
			List<NestedInteger> nestedList2 = new LinkedList<>();
			nestedList.add(new NestedIntegerImpl(nestedList2));

			nestedList2.add(new NestedIntegerImpl(6));

			NestedIterator i = new FlattenNestedListIterator().new NestedIterator(list);
			while (i.hasNext()) System.out.println(i.next());
		}
		{
			List<NestedInteger> list = new LinkedList<>();

			List<NestedInteger> nestedList = new LinkedList<>();
			list.add(new NestedIntegerImpl(nestedList));

			NestedIterator i = new FlattenNestedListIterator().new NestedIterator(list);
			while (i.hasNext()) System.out.println(i.next());
		}

	}

	public class NestedIterator implements Iterator<Integer> {

		Stack<NestedInteger> stack = new Stack<>();

		public NestedIterator(List<NestedInteger> nestedList) {
			for (int i = nestedList.size() - 1; i >= 0; i--) {
				stack.add(nestedList.get(i));
			}
			rearrangeStack();
		}

		private void rearrangeStack() {
			if (stack.isEmpty()) return;
			while (!stack.peek().isInteger()) {
				List<NestedInteger> nestedList = stack.pop().getList();
				for (int i = nestedList.size() - 1; i >= 0; i--) {
					stack.add(nestedList.get(i));
				}
				if (stack.isEmpty()) return;
			}
		}

		@Override
		public Integer next() {
			NestedInteger item = stack.pop();
			rearrangeStack();
			return item.getInteger();
		}

		@Override
		public boolean hasNext() {
			return !stack.isEmpty();
		}
	}

	public interface NestedInteger {

		// @return true if this NestedInteger holds a single integer, rather than a nested list.
		public boolean isInteger();

		// @return the single integer that this NestedInteger holds, if it holds a single integer
		// Return null if this NestedInteger holds a nested list
		public Integer getInteger();

		// @return the nested list that this NestedInteger holds, if it holds a nested list
		// Return null if this NestedInteger holds a single integer
		public List<NestedInteger> getList();
	}

	static class NestedIntegerImpl implements NestedInteger {
		private final List<NestedInteger> list;
		private final Integer value;

		public NestedIntegerImpl(List<NestedInteger> list) {
			this.list = list;
			value = null;
		}

		public NestedIntegerImpl(Integer value) {
			list = null;
			this.value = value;
		}

		@Override
		public boolean isInteger() {
			return value != null;
		}

		@Override
		public Integer getInteger() {
			return value;
		}

		@Override
		public List<NestedInteger> getList() {
			return list;
		}
	}
}
