from typing import List
from queue import Queue, LifoQueue

"""
前序遍历 preorder = [3,9,20,15,7]
中序遍历 inorder = [9,3,15,20,7]

    3
   / \
  9  20
    /  \
   15   7
"""


class TreeNode:
    def __init__(self, val=0, left=None, right=None):
        self.val = val
        self.left = left
        self.right = right


class ConstructBinaryTreeFromPreorderAndInorderTraversal105:
    def buildTree(self, preorder: List[int], inorder: List[int]) -> TreeNode or None:
        if len(preorder) == 0:
            return None
        root_val = preorder[0]
        root_val_index_of_in = inorder.index(root_val)
        left_inorder = inorder[:root_val_index_of_in]
        right_inorder = inorder[root_val_index_of_in + 1:]
        left_preorder = preorder[1:1 + root_val_index_of_in]
        right_preorder = preorder[1 + root_val_index_of_in:]
        root = TreeNode(root_val, left=self.buildTree(left_preorder, left_inorder),
                        right=self.buildTree(right_preorder, right_inorder))
        return root

    @classmethod
    def test(cls, preorder: List[int], inorder: List[int]):
        root = cls().buildTree(preorder, inorder)
        traverse(root)


class FlattenBinaryTreeToLinkedList114:
    def flatten(self, root: TreeNode) -> None:
        """
        Do not return anything, modify root in-place instead.
        """
        if not root:
            return
        if not root.left and not root.right:
            return
        left = root.left
        right = root.right
        root.right = left
        root.left = None
        self.flatten(root.right)
        node = root
        while node.right:
            node = node.right
        node.right = right
        self.flatten(right)

    def helper(self):
        pass


class BinaryTreeMaximumPathSum124:
    max_value = float('-inf')

    def maxPathSum(self, root: TreeNode) -> int:
        self._maxPathSum(root)
        return self.max_value

    def _maxPathSum(self, root: TreeNode) -> int:
        if not root:
            return 0
        left_max = self._maxPathSum(root.left)
        right_max = self._maxPathSum(root.right)

        left_path_val = left_max + root.val
        right_path_val = right_max + root.val
        all_path_val = left_max + right_max + root.val
        self.max_value = max(self.max_value, left_path_val, right_path_val, all_path_val, root.val)
        return max(left_path_val, right_path_val, root.val)


class InvertBinaryTree226:
    def invertTree(self, root: TreeNode) -> TreeNode:
        if not root:
            return root
        left = root.left
        root.left = root.right
        root.right = left
        self.invertTree(root.left)
        self.invertTree(root.right)
        return root


class LowestCommonAncestorOfABinaryTree236:
    def lowestCommonAncestor(self, root: 'TreeNode', p: 'TreeNode', q: 'TreeNode') -> 'TreeNode':
        p_path = []
        q_path = []
        self.dfs(root, p, p_path)
        self.dfs(root, q, q_path)
        node = None
        while p_path and q_path:
            a = p_path.pop(0)
            b = q_path.pop(0)
            if a.val == b.val:
                node = a
            else:
                break
        return node

    def dfs(self, root: TreeNode, p: TreeNode, res: List[TreeNode]):
        if not root:
            return False
        res.append(root)
        if root.val == p.val:
            return True
        left = self.dfs(root.left, p, res)
        if left:
            return True
        right = self.dfs(root.right, p, res)
        if right:
            return True
        res.pop()
        return False


def traverse(root: TreeNode) -> None:
    q = Queue()
    q.put(root)
    while not q.empty():
        sz = q.qsize()
        for i in range(sz):
            node: TreeNode or None = q.get()
            if node:
                print(node.val, end=' ')
            else:
                print("null", end=' ')
                continue
            q.put(node.left)
            q.put(node.right)
        print('')


class FindAllNumbersDisappearedInAnArray448:
    def findDisappearedNumbers(self, nums: List[int]) -> List[int]:
        tmp = [0] * (len(nums) + 1)
        for n in nums:
            tmp[n] += 1
        ret = []
        for i, _ in enumerate(tmp):
            if i == 0:
                continue
            if _ == 0:
                ret.append(i)
        return ret


class TrappingRainWater42:
    """
    FUCK
    总体思路：min(左边的最大值，右边的最大值) - 自己，就是当前格子能够储水的量
    """

    def test(self):
        height = [0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1]
        print(self.trap(height))

    def trap(self, height: List[int]) -> int:
        st = []
        ret = 0
        for i, _ in enumerate(height):
            while (st and height[i] > height[st[-1]]):
                print("height[%s] %s > height[st[-1]] %s " % (i, height[i], height[st[-1]]))
                top = st.pop()
                if not st:
                    print("st is empty break")
                    break
                distance = i - st[-1] - 1
                print("bound st[-1]: %s, i: %s, distance i - st[-1] - 1: %s" % (st[-1], i, distance))
                bound_height = min(height[i], height[st[-1]]) - height[top]
                print("bound_height, min(height[i]: %s, height[st[-1]]: %s) - height[top]: %s == %s" % (
                    height[i], height[st[-1]], height[top], bound_height))
                ret += bound_height * distance
            st.append(i)
            print("append height[%s] %s st %s" % (i, height[i], st))
        return ret


class LargestRectangleInHistogram84:
    """
    FUCK
    计算包裹住每一个矩形的轮廓矩形的面积，取其最大值
    1. 暴力循环，算每一个矩形对应的轮廓矩形的面积，会超时
    2. 单调递增栈，如果当前矩形高度小于上一个，则开始计算上一个矩形的轮廓矩形的面积，如果大于，则直接入栈，因为还不知道上一个矩形的右边界

    单调栈
    42, 739, 496, 316, 901, 402, 581
    在height最后append一个0，以免最后一个到了最后还没有出栈计算
    """

    def test(self):
        heights = [2, 1, 5, 6, 2, 3]
        print(self.largestRectangleArea(heights))

    def largestRectangleArea(self, heights: List[int]) -> int:
        print("heights ", heights)
        heights.append(0)
        st = []
        ret = 0
        for i, _ in enumerate(heights):
            print("i", i)
            while st and heights[i] < heights[st[-1]]:
                print("inner, i: %s, heights[i]: %s" % (i, heights[i]))
                left = st.pop()
                width = i if not st else i - st[-1] - 1  # 如果栈为空，说明左边没有比left矮的，所以左边界就是0！
                # 如果栈不为空，则此时的栈顶，就是轮廓矩形的左边界！！！
                # 理解单调栈再这个题目里的含义！栈顶的索引对应的高度是最高的！，即使中途有pop/push操作！！！
                height = heights[left]
                area = width * height
                print("%s area: %s" % (left, area))
                ret = max(ret, width * height)
            st.append(i)
            print("append i: %s, st: %s, st_height: %s" % (i, st, [heights[i] for i in st]))
        return ret


class MinStack155:
    """
    最小栈，用一个list来保存每一次push一个值时的最小值，所以每次pop的时候，直接pop这个list。list的最后一个值就是当前stack的最小值！
    """

    class Node:
        def __init__(self, val):
            self.val = val
            self.last = None
            self.next = None

    def __init__(self):
        """
        initialize your data structure here.
        """
        self.first: MinStack155.Node or None = None
        self.last: MinStack155.Node or None = None
        self.min_node: List = []

    def push(self, x: int) -> None:
        node = MinStack155.Node(x)
        if not self.min_node:
            self.min_node.append(node.val)
        elif node.val < self.min_node[-1]:
            self.min_node.append(node.val)
        else:
            tmp = self.min_node[-1]
            self.min_node.append(tmp)

        if self.first is None:
            self.first = node
            self.last = node
        else:
            self.last.next = node
            tmp = self.last
            self.last = node
            self.last.last = tmp

    def pop(self) -> None:
        if self.last is None:
            return None
        self.min_node.pop()
        if self.last.last is None:
            self.first = None
            self.last = None
        else:
            self.last = self.last.last
            self.last.next = None

    def top(self) -> int:
        if self.last:
            return self.last.val
        return None

    def getMin(self) -> int:
        if self.min_node:
            return self.min_node[-1]
        return None


class DecodeString394:
    """
    示例 1：
    输入：s = "3[a]2[bc]"
    输出："aaabcbc"

    示例 2：
    输入：s = "3[a2[c]]"
    输出："accaccacc"

    示例 3：
    输入：s = "2[abc]3[cd]ef"
    输出："abcabccdcdcdef"

    示例 4：
    输入：s = "abc3[cd]xyz"
    输出："abccdcdcdxyz"
    """
    DIGIT = {'1', '2', '3', '4', '5', '6', '7', '8', '9', '0'}

    def __init__(self):
        s = None
        index = 0

    def decodeString(self, s: str) -> str:
        ret_stack = []
        num_stack = []
        ch_stack = []
        in_digit = False
        in_ch = False
        for ch in s:
            if ch in self.DIGIT:
                if in_digit:
                    num_stack[-1] = num_stack[-1] * 10 + int(ch)
                else:
                    num_stack.append(int(ch))
                    in_digit = True
            else:
                in_digit = False
                if ch == "[":
                    ch_stack.append(ch)
                elif ch == "]":
                    ch_set = []
                    while True:
                        tmp = ch_stack.pop()
                        if tmp == "[":
                            break
                        ch_set.append(tmp)
                    count = num_stack.pop()
                    ch_str = ''.join(ch_set[::-1])
                    if ch_stack:
                        ch_stack.append(count * ch_str)
                    else:
                        ret_stack.append(count * ch_str)
                else:
                    if ch_stack:
                        ch_stack.append(ch)
                    else:
                        ret_stack.append(ch)
        return ''.join(ret_stack)

    def decodeStringRecur(self, s: str) -> str:
        self.s = s
        self.index = 0
        return self.helper()

    def helper(self) -> str:
        if (self.index == len(self.s) or self.s[self.index] == ']'):
            return ''
        ret = ''
        if self.s[self.index] in self.DIGIT:
            count = self.get_digit()
            self.index += 1  # 跳过[
            string = self.helper()
            ret += count * string
            self.index += 1  # 跳过]
        else:
            ret = self.s[self.index]
            self.index += 1
            ret += self.helper()
        return ret + self.helper()

    def get_digit(self):
        ret = 0
        while self.s[self.index] != '[':
            ret = ret * 10 + int(self.s[self.index])
            self.index += 1
        return ret


class DailyTemperatures739:
    """
    单调栈，公式题目！
    """

    def dailyTemperatures(self, T: List[int]) -> List[int]:
        st = list()
        ret = [0] * len(T)
        for i, t in enumerate(T):
            while st and t > T[st[-1]]:
                index = st.pop()
                ret[index] = i - index
            st.append(i)
        return ret


class WeightedUnionFind:
    """
    算法4 1.5, 加权并查集
    """

    def __init__(self, N):
        self._count: int = N
        self._id: List[int] = [i for i in range(N)]
        self._sz: List[int] = [1 for _ in range(N)]

    def count(self) -> int:
        return self._count

    def connected(self, p: int, q: int) -> bool:
        return self.find(p) == self.find(q)

    def find(self, p: int) -> int:
        while p != self._id[p]:
            p = self._id[p]
        return p

    def union(self, p: int, q: int) -> None:
        """
        本质上是减少了树的的高度！然后就可以减少find的次数
        :param p:
        :param q:
        :return:
        """
        p_root = self.find(p)
        q_root = self.find(q)
        if p_root == q_root: return
        if self._sz[p_root] < self._sz[q_root]:
            self._id[p_root] = q_root
            self._sz[q_root] += self._sz[p_root]
        else:
            self._id[q_root] = p_root
            self._id[p_root] += self._sz[q_root]
        self._count -= 1


class UnionFind:
    """
    算法4 1.5, 并查集的基本算法
    """

    def __init__(self, N):
        self._count: int = N
        self._id: List = [i for i in range(N)]

    def count(self) -> int:
        return self._count

    def connected(self, p: int, q: int) -> bool:
        return self.find(p) == self.find(q)

    def find(self, p: int) -> int:
        while p != self._id[p]:
            p = self._id[p]
        return p

    def union(self, p: int, q: int) -> None:
        p_root = self.find(p)
        q_root = self.find(q)
        if p_root == q_root: return
        self._id[p_root] = q_root
        self._count -= 1

    # def find(self, p: int) -> int:
    #     """
    #     quick find
    #     """
    #     return self._id[p]
    #
    # def union(self, p: int, q: int) -> None:
    #     """
    #     使用quick find的union
    #     p和q连通，则id[p] == id[q]
    #     O(N),慢
    #     """
    #     p_id = self.find(p)
    #     q_id = self.find(q)
    #
    #     if p_id == q_id: return
    #
    #     for i in range(self._count):
    #         if self._id[i] == p_id:
    #             self._id[i] = q_id
    #             self._count -= 1


class FindCircleNum547:
    def findCircleNum(self, isConnected: List[List[int]]) -> int:
        count = len(isConnected)
        provinces = [i for i in range(count)]
        weights = [1] * count

        def find(p):
            while p != provinces[p]:
                p = provinces[p]
            return p

        def union(p, q, count):
            p_root = find(p)
            q_root = find(q)
            if p_root == q_root: return count
            if weights[p_root] > weights[q_root]:
                provinces[q_root] = p_root
                weights[p_root] += weights[q_root]
            else:
                provinces[p_root] = q_root
                weights[q_root] += weights[p_root]
            return count - 1

        for i in range(len(isConnected)):
            for j in range(len(isConnected[i])):
                if isConnected[i][j]:
                    count = union(i, j, count)

        return count


class NumIslands200:
    def numIslands(self, grid: List[List[str]]) -> int:
        count = 0
        new_grid = []
        for row in grid:
            tmp = []
            for col in row:
                if col == "1":
                    tmp.append(count)
                    count += 1
                else:
                    tmp.append(col)
            new_grid.append(tmp)
        if count == 0:
            return count

        islands = [i for i in range(count)]
        weights = [1] * count

        def find(p):
            while p != islands[p]:
                p = islands[p]
            return p

        def union(p, q, count):
            p_root = find(p)
            q_root = find(q)
            if p_root == q_root: return count
            if weights[p_root] > weights[q_root]:
                islands[q_root] = p_root
                weights[p_root] += weights[q_root]
            else:
                islands[p_root] = q_root
                weights[q_root] += weights[p_root]
            return count - 1

        rows = len(grid)
        cols = len(grid[0])
        for i in range(rows):
            for j in range(cols):
                if new_grid[i][j] != "0":
                    if j < cols - 1 and grid[i][j + 1] != "0":
                        count = union(new_grid[i][j], new_grid[i][j + 1], count)
                    if i < rows - 1 and grid[i + 1][j] != "0":
                        count = union(new_grid[i][j], new_grid[i + 1][j], count)
        return count


class MinSwapsCouples765:
    """
    并查集和BFS都可以
    如果N对(2Ng个)情侣中，有n个连通分量，一个连通分量的意思就是里面的情侣可以更换位置完成配对，不用其他人了
    2对情侣的连通分量，需要换1次位置
    3对情侣的连通分量，需要换2次位置
    4对情侣的连通分量，需要换3次位置
    X对情侣的连通分量，需要换X-1次位置
    那么总的换的次数就是1+2+3+...+X-1 = N-n 就是总的位置不对的情侣对数-连通分量的个数
    """

    def minSwapsCouples(self, row: List[int]) -> int:
        if not row:
            return 0
        couples = [[] for i in range(len(row) // 2)]
        # couples = []
        tmp = [i for i in range(len(row) // 2)]
        count = 0
        N = 0
        for i in range(0, len(row), 2):
            left = row[i] // 2
            right = row[i + 1] // 2
            if left == right:
                # 说明这对情侣不用换位子
                continue
            # 记录连通分量关系
            couples[left].append(right)
            couples[right].append(left)
            count += 1
            N += 1

        def find(p):
            while p != tmp[p]:
                p = tmp[p]
            return p

        def union(p, q):
            nonlocal count
            p_root = find(p)
            q_root = find(q)
            if p_root == q_root:
                return
            tmp[p_root] = q_root
            count -= 1

        for i, conn in enumerate(couples):
            for somebody in conn:
                union(i, somebody)

        return N - count


class calcEquation399:
    def calcEquation(self, equations: List[List[str]], values: List[float], queries: List[List[str]]) -> List[float]:
        equation_map = {tuple(e): v for e, v in zip(equations, values)}
        for e, v in zip(equations, values):
            equation_map[tuple(e[::-1])] = 1 / v


if __name__ == '__main__':
    # s = "3[a]2[bc]"
    # s = "3[a2[c]]"
    # s = "2[abc]3[cd]ef"
    # s = "abc3[cd]xyz"
    # print(DecodeString394().decodeStringRecur(s))
    # temperatures = [73, 74, 75, 71, 69, 72, 76, 73]
    # print(DailyTemperatures739().dailyTemperatures(temperatures))
    isConnected = [[1, 1, 0], [1, 1, 0], [0, 0, 1]]
    # print(FindCircleNum547().findCircleNum(isConnected))

    grid = [
        ["1", "1", "1", "1", "0"],
        ["1", "1", "0", "1", "0"],
        ["1", "1", "0", "0", "0"],
        ["0", "0", "0", "0", "0"]
    ]
    grid = [
        ["1", "1", "0", "0", "0"],
        ["1", "1", "0", "0", "0"],
        ["0", "0", "1", "0", "0"],
        ["0", "0", "0", "1", "1"]
    ]
    # print(NumIslands200().numIslands(grid))
    row = [0, 2, 1, 3]
    row = [3, 2, 0, 1]
    row = [5, 4, 2, 6, 3, 1, 0, 7]
    print(MinSwapsCouples765().minSwapsCouples(row))
