
/**
 * Author: Madison Pettipas
 * Email: madisonpettipas@gmail.com
 * Description: Heap ADT that prioritizes larger values at the root. Simulates
 *              priority of buyers. Has an inner Node class, and 2 fields: a Node
 *              array and an int.
 * 
 */
public class MaxHeap {
    Node[] maxHeap;
    int size; //keeps track of how many Nodes are in the array
    Map map;
    
    /** Inner class to hold all information for each buyer. Has fields a double,
     * 2 Strings, and an int, and a constructor method. */
    public class Node {
        double price;
        String buyer, time;
        int quantity;
        public Node(String buyer, double price, String time, int quantity) {
            this.buyer = buyer;
            this.price = price;
            this.time = time;
            this.quantity = quantity;
        }
    }
    
    public MaxHeap() { //constructor method
        maxHeap = new Node[256];
        size = 0;
        map = new Map();
    }
    
    /** Void method with a Node parameter. Adds the Node to the heap, and upheaps
     * it until it is in its proper placement numerically. */
    public void insert(Node item) {
        maxHeap[size] = item;
        map.put(item.buyer, size);
        upHeap(size);
        size++;
    }
    
    /** Void method with an int parameter to keep track of the index. Upheaps the
     * value at the index of the parameter by swapping it with its parent if 
     * the parent's value is lower. Continues until the parent's value is higher or
     * the root is reached (index no longer greater than 0). */
    public void upHeap(int i) {
        while (i > 0) {
            int parent = (i-1)/2;
            if (comparePriority(maxHeap[i], maxHeap[parent])) {
                swap(i, parent);
                i = parent;
            } else break;
        }
    }
    
    /** Void method with an int parameter to keep track of the index. Downheaps the
     * value at the index of the parameter by swapping it with either its right or
     * left child if one of them have a higher value. Continues until either both 
     * children have lower values or the index reaches the array size. */
    public void downHeap(int i) {
        while ((i*2)+1 < size) {
            int leftChild = (i*2)+1;
            int rightChild = (i*2)+2;
            int max = i;
            if (comparePriority(maxHeap[leftChild], maxHeap[max])) {
                max = leftChild;
            } if (rightChild < size && 
                comparePriority(maxHeap[rightChild], maxHeap[max])) {
                max = rightChild;
            } if (max != i) {
                swap(i, max);
                i = max;
            } else break;
        }
    }
    
    /** Void method with 2 ints as parameters. Swaps the Nodes at the indexes
     * of the 2 given ints */
    public void swap(int first, int second) {
        Node temp = maxHeap[first];
        maxHeap[first] = maxHeap[second];
        maxHeap[second] = temp;
        
        map.put(maxHeap[first].buyer, first);
        map.put(maxHeap[second].buyer, second);
    }
    
    /** Boolean method with 2 Nodes as parameters. Returns true of the first Node
     * has a higher priority than the second. Priority is based first on which
     * has the higher price, and if equal, based on which has the earlier time */
    public boolean comparePriority(Node first, Node second) {
        if (first.price > second.price) return true;
        if ((first.price == second.price) && (first.time.compareTo(second.time) < 0)) {
            return true;
        }
        return false;
    }
    
    /** Node method with no parameters. If the heap is not empty, removes
     * the root/max Node from the heap and returns that Node. */
    public Node removeMax() {
        if (size == 0) return null;
        Node max = maxHeap[0];
        map.remove(max.buyer); 
        maxHeap[0] = maxHeap[size-1];
        size--;
        downHeap(0);
        return max;
    }
    
    /** Double method with no parameters. Returns the price of the root/max
     * Node, without removing or changing it */
    public double getMaxValue() {
        Node max = maxHeap[0];
        double maxValue = max.price;
        return maxValue;
    }
    
    /** Node method with no parameters. Returns the root/max Node of the heap
     * without removing or changing it. */
    public Node getMax() {
        return maxHeap[0];
    }
    
    /** Boolean method with parameters 2 Strings, a double, and an int.
     * Searches for a buy order, that matches the buyers name, using a map,
     * ensuring quicker search time. If found, the index is returned and 
     * the time, price, and quantity of the buyer are changed to the new values */
    public boolean change(String name, String time, double price, int quantity) {
        int i = map.get(name); //get index of buyer from map
        if (i == -1) return false; //not found
        maxHeap[i].time = time;
        maxHeap[i].price = price;
        maxHeap[i].quantity = quantity;
        upHeap(i); //make sure Node is still in heap order
        downHeap(i);
        return true;
    }
    
    /** Boolean method with String parameter. Searches for a buy order, that
     * matched the buyer's name, using a map, ensuring quicker search time.
     * If found, removes the Node using the removeAt helper method. */
    public boolean cancel(String name) {
        int i = map.get(name); //get index of buyer from map
        if (i == -1) return false;
        removeAt(i);
        return true;
    }
    
    /** Boolean method with an int parameter. If the heap is not empty,
     * removes the Node at the given index from the heap and the map */
    public boolean removeAt(int i) {
        if (size == 0) return false;
        Node toRemove = maxHeap[i];
        map.remove(toRemove.buyer);
        maxHeap[i] = maxHeap[size-1];
        size--;
        if (i < size) { //if it wasn't the last Node that was removed
            map.put(maxHeap[i].buyer, i);
            upHeap(i);
            downHeap(i);
        }
        return true;
    }
}
