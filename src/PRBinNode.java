import java.util.LinkedList;

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
	public String duplicate();
}
