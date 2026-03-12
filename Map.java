
/**
 * Author: Madison Pettipas
 * Email: mpettipas2024@my.fit.edu
 * Course: CSE2010
 * Section: E2
 * Description: Map ADT that hashes a given String, and stores its given index.
 *              Reduces the big O time of the heaps' change and cancel methods.
 *              Has an inner Node class, and methods a constructor, to create
 *              the hash, get, put, and remove.
 */
public class Map {
    Node[] map;
    
    /** Inner class to hold all information for each element. Has fields a
     * String, an int, and a Node and a constructor method. */
    public class Node {
        String key; //will store the name
        int value; //will store the index in the heap
        Node next; //for collisions
        
        public Node(String key, int value) {
            this.key = key;
            this.value = value;
            this.next = null;
        }
    }
    
    //constructor method
    public Map() {
        map = new Node[257];
    }
    
    //int method with String parameter; hashes the given String with mod 257
    public int hash(String key) {
        return (key.hashCode() % 257);
    }
    
    //int method with String parameter; returns the value of the given String/key
    public int get(String key) {
        int i = hash(key); //hash the key
        Node curr = map[i];
        
        while (curr!=null) { //if the index isnt empty
            if (curr.key.equals(key)) { 
                return curr.value;
            }
            curr = curr.next; //for collisions
        }
        return -1; //if not found
    }
    
    //void method with String and int parameters; enters a key and value pair into the map
    public void put(String key, int value) {
        int i = hash(key);
        Node curr = map[i];
        
        //makes sure any key is only inserted once, updates value if key found
        while (curr!=null) {
            if (curr.key.equals(key)) {
                curr.value = value;
                return;
            }
            curr = curr.next;
        }
        //set new Node to the front of the list (handles collisions) (O(1))
        Node head = new Node(key, value);
        head.next = map[i];
        map[i] = head;
    }
    
    //void method with String parameter; removes a given String/key from the map
    public void remove(String key) {
        int i = hash(key);
        Node curr = map[i];
        Node prev = null;
        
        while (curr!=null) {
            if (curr.key.equals(key)) {
                if (prev==null) {
                    map[i] = curr.next;
                } else prev.next = curr.next;
                return;
            }
            prev = curr;
            curr = curr.next;
        }
        
    }
}