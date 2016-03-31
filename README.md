# PRQuadTree
## Introduction
When we build a simple database system for storing,removing,and querying a collection of data by name or by position. The data structure could be a simple Skip List or Binary Tree. The list structure is efficient for finding records by key value. However, it is not good for finding records with spatial information. The one way to deal with spatial tasks is to us PR QuadTree. This simple project is complete by a combination with Skip List and PR QuadTree.
## Implementation
* Skip List

A skip list is a data structure that allows fast search within an ordered sequence of elements.

![A schematic picture of the skip list data structure from Wiki](/img/Skip_list.png)

Fig.1 A schematic picture of the skip list data structure from Wiki

The Skip List mainly hold the KVpair and insert, remove, search records by key value
```java
SkipList<K,V>{ 
 SkipNode<K,V> head;
 SKipNode<K,V> tail;
 SkipNode<K,V> current;
 int size;
 }
```
Each SkipNode holds
```java
SkipNode<K,V> {
	private KVPair<K,V> element; // store KVpair as value
	private int maxlevel;  // maximum level,at least 1
	private int curlevel;  // current used level <= maximum level, at least 1
	private SkipNode<K,V>[] successor; //same level next node, non-deterministic array
```
The method for Skip List will be
```java
	/* clear skip list as empty */
	public void clear()
	/* insert KVPair data into list */
	public void insert(K k, V e)
	/* remove one node which key value equals k */
	public KVPair<K,V> remove(K k)
	/* search date with key value k */
	public SkipNode<K,V> search(K k)
```
* PR QuadTree

he PR Quadtree is a full tree such that every node is either a leaf node, or else it is an internal node with four children.The key property of any Quadtree data structure is its decomposition rule. The decomposi- tion rule is what distinguishes the PR Quadtree of this project from the PR Quadtree of Module 15.3. The decomposition rule for this project is: A leaf node will split into four when (a) There are more than three points in the node, such that (b) not all of the point have the same x and y coordinates. Four sibling leaf nodes will merge together to form a single leaf node whenever deleting a point results in a set of points among the four leaves that does not violate the decomposition rule. It is possible for a single insertion to cause a cascading series of node splits. It is also possible for a single deletion to cause a cascading series of node merges.

![A example of PR QuadTree](/img/PRQuadTree.png)

Fig.2 A example of PR QuadTree

The interface for PR QuadTree base node will include basic methode for internal node and leaf node to implement.
```java
public interface  PRBinNode<E extends Point2<?>> {
    //Traversal for dump purpose
	public String travesal(Integer x,Integer y, Integer scale,int level);
	// Height of tree
	public int height(int level);
    // whether is leaf node	
	public boolean isLeaf();
	// get child
	public PRBinNode<E> getNodeById(int id);
	// set child
	public void setNodeById(PRBinNode<E> root,int id);
    // set value
	public boolean setVar(E elem);
	// get value
	public Object getVar();
	// get all points
	public void getAllPoint(PRBinNode<E> root,LinkedList<E> list);
}
```
The key point for insert, remove, search even region query is the base class Point implement Comparable, which method a.compareTo(b) will return the relative direction from a to b. It will determine which one of four children to go.

The element that stored in Leaf Node is Class E inherit from Point2, which is child class from Point. The Point2 includes key information so that the spatial information get connected to key.

* Database
It is a good idea to create a “Database” class and create one object of the class. This Database class will receive the commands to insert, delete, search, etc., and farm them out to the Skip List and/or PR Quadtree for actual implementation.

## Demonstration
You can write commands into input.txt then run the command in command prompt.
```
$javac Point2.java
java Point2 input.txt
```
The exmaple input and output should be.
```
insert r_r    1 20
insert rec    10 30
insert r_42   1 20
insert far  200 200
dump
duplicates
search 10 21
search r_r
regionsearch   0 0 25 25
remove r_r
remove 10 30
duplicates
dump
```
The output will be:
```
Point inserted: (r_r,1,20)
Point inserted: (rec,10,30)
Point inserted: (r_42,1,20)
Point inserted: (far,200,200)
SKipList:
Node has depth: 2, Value (far,200,200)
Node has depth: 1, Value (r_42,1,20)
Node has depth: 1, Value (r_r,1,20)
Node has depth: 1, Value (rec,10,30)
SkipList size is:4
Quadtree:
Node at 0, 0, 1024: Internal
  Node at 0, 0, 512: Internal
    Node at 0, 0, 256: Internal
      Node at 0, 0, 128:
        (r_r,1,20)
        (rec,10,30)
        (r_42,1,20)
      Node at 128, 0, 128: Empty
      Node at 0, 128, 128: Empty
      Node at 128, 128, 128:
        (far,200,200)
    Node at 256, 0, 256: Empty
    Node at 0, 256, 256: Empty
    Node at 256, 256, 256: Empty
  Node at 512, 0, 512: Empty
  Node at 0, 512, 512: Empty
  Node at 512, 512, 512: Empty
Found :null
Search wrong format:10
Found :(r_r,1,20)
Region search found:
(r_r,1,20)
(r_42,1,20)

Point removed: (r_r,1,20)
Point removed: (rec,10,30)
Remove wrong format:10
SKipList:
Node has depth: 2, Value (far,200,200)
Node has depth: 1, Value (r_42,1,20)
SkipList size is:2
Quadtree:
Node at 0, 0, 1024:
  (far,200,200)
  (r_42,1,20)
```
