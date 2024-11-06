//Note: You have access to all of Java IO and Java Utility here. Make good use of them!
//No additional imports allowed.

import java.io.*;
import java.util.*;


/**************************************************
Random advice and tips:
(1) Don't forget to close your IO streams!
(2) Just because you _can_ implement something in
    the same number of lines as me doesn't mean
        that's the best or only solution. Do you!
(3) The class "TreeNode" is called "LinkedTree.TreeNode"
        from this class
**************************************************/


class Convert {
        /************************************************************************
        You may add additional PRIVATE methods. You may NOT add static class
        variables (this is for your own good... there is no "good" use of class
        variables here and a very high chance of losing points if you don't
        know how static "really" works... so don't do it!
        ************************************************************************/
       
       
        /********************************************************************************/
        /*                           Parent Pointer Format                              */
        /********************************************************************************/
        /**
     * Reads the file and converts it into an array of ParentPointer objects.
     * @param filename the name of the file to read
     * @return an array of ParentPointer objects
     * @throws IOException if an I/O error occurs
     */
        public static ParentPointer[] parentPointerFormat(String filename) throws IOException {
                BufferedReader reader = new BufferedReader (new FileReader (filename));

                //Read ID and child position
                String[] parentData = reader.readLine().split(" ");
                String[] childData = reader.readLine().split(" ");
                //Create an array to store parent pointer objects
                ParentPointer[] pointers = new ParentPointer [parentData.length];

                for (int i = 0; i< parentData.length; i++){
                        //Create ParentPointer objects
                        if(!parentData[i].equals("x") && !childData[i].equals("x")){
                                int parentId = Integer.parseInt(parentData[i]);
                                boolean isLeft = childData[i].equals("L");
                                pointers[i] = new ParentPointer(parentId, isLeft);
                        }else {
                                pointers[i] = null;
                        }
       
                }
                reader.close();
                return pointers;
        }
       
        /********************************************************************************/
        /*                                Linked Format                                 */
        /********************************************************************************/
        /**
     * Converts a file containing parent pointers into a LinkedTree.
     * @param filename the name of the file to read
     * @return a LinkedTree constructed from the file's data
     * @throws IOException if an I/O error occurs
     */
        @SuppressWarnings("unchecked")
        public static LinkedTree<Integer> treeLinkedFormat(String filename) throws IOException {
                 // Read parent pointers from file
    ParentPointer[] pointers = parentPointerFormat(filename);
   
    // Create a new linked tree
    LinkedTree<Integer> tree = new LinkedTree<>(null);
   
    // Map node IDs to their corresponding tree nodes
    HashMap<Integer, LinkedTree.TreeNode<Integer>> nodes = new HashMap<>();

    // Iterate over parent pointers
        LinkedTree.TreeNode<Integer> root = null;
    for (int i = 0; i < pointers.length; i++) {
        // Create a new tree node
        LinkedTree.TreeNode<Integer> node = new LinkedTree.TreeNode<>(null);
        nodes.put(i, node);
       
        // If the node has a parent, set the parent's child
        if (pointers[i] != null) {
            LinkedTree.TreeNode<Integer> parent = nodes.get(pointers[i].parentId);
            if (parent != null) {
                if (pointers[i].isLeft) {
                    parent.setLeft(node);
                } else {
                    parent.setRight(node);
                }
            }
        } else {
            // If the node is the root, set it as the tree's root
            root = node;
        }
    }

    return new LinkedTree<>(root);
}
       
        /********************************************************************************/
        /*                               Array Format                                   */
        /********************************************************************************/
       
    /**
     * Converts a file containing parent pointers into an array representing the tree.
     * @param filename the name of the file to read
     * @return an array representing the tree
     * @throws IOException if an I/O error occurs
     */
        @SuppressWarnings("unchecked")
        public static Integer[] treeArrayFormat(String filename) throws IOException {
                    // Convert the parent pointer tree to a linked tree
                        LinkedTree<Integer> tree = treeLinkedFormat(filename);
   
                        // Create a queue for level-order traversal
                        LinkedList<LinkedTree.TreeNode<Integer>> queue = new LinkedList<>();
                        queue.add(tree.getRoot()); // Enqueue the root node
                       
                        // Create a list to store node values
                        ArrayList<Integer> list = new ArrayList<>();
               
                        // Perform level-order traversal
                        while (!queue.isEmpty()) {
                                LinkedTree.TreeNode<Integer> node = queue.poll(); // Dequeue a node
                                if (node != null) {
                                        list.add(node.getData()); // Add node value to the list
                                        queue.add(node.getLeft()); // Enqueue left child
                                        queue.add(node.getRight()); // Enqueue right child
                                } else {
                                        list.add(null); // Add null for empty child
                                }
                        }
               
                        // Convert the list to an array
                        Integer[] array = new Integer[list.size()];
                        return list.toArray(array);
                }
       
        public static <T> double arrayLoad(T[] tree) {
                int size = 0;
    // Count non-null elements
    for (T element : tree) {
        if (element != null) {
            size++; // Increment size for non-null elements
        } else {
            break; // Stop counting at the first null element
        }
    }
    // Calculate load factor
    return (double) size / tree.length;
        }
       
        /********************************************************************************/
        /*                             Merge Operations                                 */
        /* You must implement ONE of these options (each option contains three methods) */
        /* IMPORTANT: You may NOT edit/destroy any of the input trees in these methods! */
        /********************************************************************************/
       
       
        //Option 1: Merging using linked structures
        /**
     * Merges two LinkedTrees if they have compatible roots.
     * @param tree1 the first tree to merge
     * @param tree2 the second tree to merge
     * @return the merged tree
     * @throws UnsupportedOperationException if the trees are incompatible
     */
        /**@SuppressWarnings("unchecked")
        public static LinkedTree<Integer> merge(LinkedTree<Integer> tree1, LinkedTree<Integer> tree2) {
                // Check if roots are compatible (i.e., have the same value)
                if (tree1.getRoot() == null && tree2.getRoot() == null) {
                        return new LinkedTree<>(null);
                } else if (tree1.getRoot() == null || tree2.getRoot() == null) {
                        return tree1.getRoot() != null ? tree1 : tree2;
                }
                if (!tree1.getRoot().getData().equals(tree2.getRoot().getData())) {
                        throw new UnsupportedOperationException("Incompatible trees: Roots do not match");
                }
               
                // Create a merged tree starting from tree1's root
                LinkedTree<Integer> mergedTree = new LinkedTree<>(tree1.getRoot());
               
                // Use a recursive method to combine tree2's nodes into mergedTree
                try {
                        mergeNodes(mergedTree.getRoot(), tree2.getRoot());
                } catch (Exception e) {
                        throw new UnsupportedOperationException("Incompatible trees: " + e.getMessage());
                }
                return mergedTree;
        }**/
       

/**
 * Helper method to merge nodes recursively with exception handling.
 *
 * @param node1 the current node in the merged tree
 * @param node2 the current node in tree2
 * @throws Exception if nodes are incompatible
 */
/**private static void mergeNodes(LinkedTree.TreeNode<Integer> node1, LinkedTree.TreeNode<Integer> node2) throws Exception {
    if (node1 == null || node2 == null) {
        return;
    }
   
    // Attempt to add left and right children from node2 to node1 if not present
    if (node2.getLeft() != null) {
        if (node1.getLeft() == null) {
            node1.setLeft(node2.getLeft());
        } else if (!node1.getLeft().getData().equals(node2.getLeft().getData())) {
            throw new Exception("Conflict at left child of node " + node1.getData());
        } else {
            mergeNodes(node1.getLeft(), node2.getLeft());
        }
    }
    if (node2.getRight() != null) {
        if (node1.getRight() == null) {
            node1.setRight(node2.getRight());
        } else if (!node1.getRight().getData().equals(node2.getRight().getData())) {
            throw new Exception("Conflict at right child of node " + node1.getData());
        } else {
            mergeNodes(node1.getRight(), node2.getRight());
        }
    }
}**/
        /**
     * Checks if a LinkedTree contains duplicate values.
     * @param tree the tree to check
     * @return false if no duplicates found
     * @throws UnsupportedOperationException if a duplicate is detected
     */
        /*public static boolean containsDuplicates(LinkedTree<Integer> tree) {
                Set<Integer> nodeSet = new HashSet<>();
    try {
        checkDuplicates(tree.getRoot(), nodeSet);
    } catch (Exception e) {
        throw new UnsupportedOperationException("Duplicate detected: " + e.getMessage());
    }
    return false; // No duplicates found
}**/

/**
 * Helper method for recursive traversal and duplicate check.
 *
 * @param node the current node
 * @param nodeSet the set of unique node values
 * @throws Exception if duplicate value found
 */
/** private static void checkDuplicates(LinkedTree.TreeNode<Integer> node, Set<Integer> nodeSet) throws Exception {
        if (node == null) {
        return;
    }
    if (!nodeSet.add(node.getData())) {
        throw new Exception("Value " + node.getData() + " is duplicated.");
    }
    checkDuplicates(node.getLeft(), nodeSet);
    checkDuplicates(node.getRight(), nodeSet);
}
        /**
     * Converts a LinkedTree into an array of ParentPointer objects.
     * @param tree the tree to convert
     * @return an array of ParentPointer objects
     * @throws UnsupportedOperationException if the tree is null or empty
     */
        /**@SuppressWarnings("unchecked")
        public static ParentPointer[] toParentPointer(LinkedTree<Integer> tree) {
                if (tree == null) {
                        throw new NullPointerException("Tree cannot be null");
                }
                if (tree.getSize() == 0) {
                        throw new UnsupportedOperationException("Tree cannot be empty");
                }
               
                int size = tree.getSize();
                ParentPointer[] pointers = new ParentPointer[size];
                try {
                        toParentPointerHelper(tree.getRoot(), pointers, -1);
                } catch (Exception e) {
                        throw new UnsupportedOperationException("Failed to convert tree: " + e.getMessage());
                }
                return pointers;
        }
       
        /**
         * Helper method for toParentPointer.
         *
         * @param node the current node
         * @param pointers the parent pointer array
         * @param parentId the ID of the parent node
         */
        /**private static void toParentPointerHelper(LinkedTree.TreeNode<Integer> node, ParentPointer[] pointers, int parentId) {
                if (node == null) {
                        return;
                }
                pointers[node.getData()] = new ParentPointer(parentId);
                toParentPointerHelper(node.getLeft(), pointers, node.getData());
                toParentPointerHelper(node.getRight(), pointers, node.getData());
        }**/
       
       
        //Option 2: Merging using array structures
       
        //you may NOT edit/destroy either of the input trees
        public static Integer[] merge(Integer[] tree1, Integer[] tree2) {
                /// Determine max size needed for merged tree
                int maxSize = Math.max(tree1.length, tree2.length);
   
                // Initialize merged tree array
                Integer[] mergedTree = new Integer[maxSize];
       
                // Copy elements from tree1 to merged tree
                System.arraycopy(tree1, 0, mergedTree, 0, tree1.length);
       
                // Merge tree2 into merged tree, checking for compatibility
                for (int i = 0; i < tree2.length; i++) {
                        if (tree2[i] != null) {
                                if (mergedTree[i] == null) {
                                        // Add tree2 element if merged tree has null at this index
                                        mergedTree[i] = tree2[i];
                                } else if (!mergedTree[i].equals(tree2[i])) {
                                        // Throw exception if conflicting values at same index
                                        throw new UnsupportedOperationException("Incompatible trees: Conflicting values at index " + i);
                                }
                        }
                }
                return mergedTree;
        }
       
        public static boolean containsDuplicates(Integer[] tree) {
                  // Check if tree is null or empty
                  if (tree == null || tree.length == 0) {
                        throw new UnsupportedOperationException("Tree cannot be null or empty");
                }
       
                // Use a Set to track unique values in the tree
                Set<Integer> uniqueValues = new HashSet<>();
               
                // Iterate through each value in the tree
                for (Integer value : tree) {
                        if (value != null) {
                                // If a duplicate value is found, throw an exception
                                if (!uniqueValues.add(value)) {
                                        throw new UnsupportedOperationException("Duplicate detected: " + value);
                                }
                        }
                }
               
                // No duplicates found, return false
                return false;
        }
       
       
       
        public static ParentPointer[] toParentPointer(Integer[] tree) {
                 // Check if tree is null or empty
                 if (tree == null || tree.length == 0) {
                        throw new UnsupportedOperationException("Tree cannot be null or empty");
                }
       
                // Initialize an array to store ParentPointer objects for each node
                ParentPointer[] pointers = new ParentPointer[tree.length];
       
                // Iterate over each node in the tree array
                for (int i = 0; i < tree.length; i++) {
                        if (tree[i] != null) {
                                // Calculate the parent ID for the current node
                                int parentId = (i - 1) / 2;
                                // Determine if the current node is a left child of its parent
                                boolean isLeft = (i == 2 * parentId + 1);
                               
                                // Root node has no parent, so set parentId to -1
                                pointers[i] = (i == 0) ? new ParentPointer(-1) : new ParentPointer(parentId, isLeft);
                        } else {
                                // Null values represent absent nodes in the array-based tree
                                pointers[i] = null;
                        }
                }
                return pointers;
        }
       
        /********************************************************************************/
        /*                              Support Classes                                 */
        /*    You may NOT edit anything below this line except to add JavaDocs.         */
        /*    This class must remain as-is for grading.                                 */
        /********************************************************************************/
       
    /**
     * Represents a pointer to a parent node in a tree.
     */
        public static class ParentPointer {
                int parentId;
                boolean isLeft; //note: it doesn't matter what you set this to for root, it will be ignored
               
                public ParentPointer() {  }
                public ParentPointer(int parentId) { this.parentId = parentId; }
                public ParentPointer(int parentId, boolean isLeft) { this(parentId); this.isLeft = isLeft; }
               
                public String toString() { return parentId + ((parentId == -1) ? "" : (isLeft ? "L" : "R")); }
        }
        /**
     * Converts an array to a tree node.
     *
     * @param array the array to convert
     * @param index the current index in the array
     * @return the root node of the tree
     */
   /** private static <T> LinkedTree.TreeNode<T> arrayToTreeNode(T[] array, int index) {
        if (index >= array.length || array[index] == null) {
            return null;
        }
        LinkedTree.TreeNode<T> node = new LinkedTree.TreeNode<>(array[index]);
        node.setLeft(arrayToTreeNode(array, 2 * index + 1));
        node.setRight(arrayToTreeNode(array, 2 * index + 2));
        return node;
    }**/

        }
