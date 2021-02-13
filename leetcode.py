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
                width = i if not st else i - st[-1] - 1 # 如果栈为空，说明左边没有比left矮的，所以左边界就是0！
                # 如果栈不为空，则此时的栈顶，就是轮廓矩形的左边界！！！
                # 理解单调栈再这个题目里的含义！栈顶的索引对应的高度是最高的！，即使中途有pop/push操作！！！
                height = heights[left]
                area = width * height
                print("%s area: %s" % (left, area))
                ret = max(ret, width * height)
            st.append(i)
            print("append i: %s, st: %s, st_height: %s" % (i, st, [heights[i] for i in st]))
        return ret


if __name__ == '__main__':
    LargestRectangleInHistogram84().test()
