
/**
 * Author: Madison Pettipas
 * Email: mpettipas2024@my.fit.edu
 * Course: CSE2010
 * Section: E2
 * Description: Heap ADT that prioritized smaller values at the root. Simulates
 *              priority of sellers. Has an inner Node class, and 2 fields: a Node
 *              array and an int.
 */
public class MinHeap {
    Node[] minHeap;
    int size; //keeps track of how many Nodes are in the array
    Map map; //added for extra
    
    /** Inner class to hold all information for each seller. Has fields a double,
     * 2 Strings, and an int, and a constructor method. */
    public class Node {
        double price;
        String seller, time;
        int quantity;
        public Node(String seller, double price, String time, int quantity) {
            this.seller = seller;
            this.price = price;
            this.time = time;
            this.quantity = quantity;
        }
    }
    
    //Constructor method
    public MinHeap() { 
        minHeap = new Node[256];
        size = 0;
        map = new Map(); //added for extra
    }
    
    /** Void method with a Node parameter. Adds the Node to the heap, and upheaps
     * it until it is in its proper placement numerically. */
    public void insert(Node item) {
        minHeap[size] = item;
        map.put(item.seller, size); //added for extra
        upHeap(size);
        size++;
    }
    
    /** Void method with an int parameter to keep track of the index. Upheaps the
     * value at the index of the parameter by swapping it with its parent if 
     * the parents value is higher. Continues until the parents value is lower or
     * the root is reached (index no longer greater than 0). */
    public void upHeap(int i) {
        while (i > 0) {
            int parent = (i-1)/2;
            if (comparePriority(minHeap[i], minHeap[parent])) {
                swap(i, parent);
                i = parent;
            } else break;
        }
    }
    
    /** Void method with an int parameter to keep track of the index. Downheaps the
     * value at the index of the parameter by swapping it with either its right or
     * left child if one of them have a lower value. Continues until either both 
     * children have higher values or the index reaches the array size. */
    public void downHeap(int i) {
        while ((i*2)+1 < size) {
            int leftChild = (i*2)+1;
            int rightChild = (i*2)+2;
            int min = i;
            if (comparePriority(minHeap[leftChild], minHeap[min])) {
                min = leftChild;
            } if (rightChild < size && 
                comparePriority(minHeap[rightChild], minHeap[min])) {
                min = rightChild;
            } if (min != i) {
                swap(i, min);
                i = min;
            } else break;
        }
    }
    
    /** Void method with 2 ints as parameters. Swaps the Nodes at the indexes
     * of the 2 given ints */
    public void swap(int first, int second) {
        Node temp = minHeap[first];
        minHeap[first] = minHeap[second];
        minHeap[second] = temp;
        //added for extra
        map.put(minHeap[first].seller, first);
        map.put(minHeap[second].seller, second);
    }
    
    /** Boolean method with 2 Nodes as parameters. Returns true of the first Node
     * has a higher priority than the second. Priority is based first on which
     * has the lowest price, and if equal, based on which has the earlier time */
    public boolean comparePriority(Node first, Node second) {
        if (first.price < second.price) return true;
        if ((first.price == second.price) && (first.time.compareTo(second.time) < 0)) {
            return true;
        }
        return false;
    }
    
    /** Node method with no parameters. If the heap is not empty, removes
     * the root/min Node from the heap and returns that Node. */
    public Node removeMin() {
        if (size == 0) return null;
        Node min = minHeap[0];
        map.remove(min.seller); //added for extra
        minHeap[0] = minHeap[size-1];
        size--;
        downHeap(0);
        return min;
    }
    
    /** Double method with no parameters. Returns the price of the root/min
     * Node, without removing or changing it */
    public double getMinValue() {
        Node min = minHeap[0];
        double minValue = min.price;
        return minValue;
    }
    
    /** Node method with no parameters. Returns the root/max Node of the heap
     * without removing or changing it. */
    public Node getMin() {
        return minHeap[0];
    }
    
    /** Boolean method with parameters 2 Strings, a double, and an int.
     * Searches for a sell order, that matches the sellers name, using a map,
     * ensuring quicker search time. If found, the index is returned and 
     * the time, price, and quantity of the seller are changed to the new values */
     //added for Extra
    public boolean change(String name, String time, double price, int quantity) {
        int i = map.get(name); //get index of seller from map
        if (i == -1) return false; //not found
        minHeap[i].time = time;
        minHeap[i].price = price;
        minHeap[i].quantity = quantity;
        upHeap(i); //make sure Node is still in heap order
        downHeap(i);
        return true;
    }
    
    /** Boolean method with String parameter. Searches for a sell order, that
     * matched the sellers name, using a map, ensuring quicker search time.
     * If found, removes the Node using the the removeAt helper method. */
     //added for extra
    public boolean cancel(String name) {
        int i = map.get(name); //get index of seller from map
        if (i == -1) return false;
        removeAt(i);
        return true;
    }
    
    /** Boolean method with an int parameter. If the heap is not empty,
     * removes the Node at the given index from the heap and the map */
     //added for Extra
    public boolean removeAt(int i) {
        if (size == 0) return false;
        Node toRemove = minHeap[i];
        map.remove(toRemove.seller);
        minHeap[i] = minHeap[size-1];
        size--;
        if (i < size) { //if it wasnt the last Node that was removed
            map.put(minHeap[i].seller, i);
            upHeap(i);
            downHeap(i);
        }
        return true;
    }
}