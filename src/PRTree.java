import java.util.ArrayList;
import java.util.LinkedList;

public class PRTree<E extends Point2<?>> {
     private PRBinNode<E> head;
     private Integer xMin,xMax,yMin,yMax;
     
     public PRTree(Integer xmin,Integer xmax,Integer ymin,Integer ymax){
    	 this.xMin=xmin;
    	 this.xMax=xmax;
    	 this.yMin=ymin;
    	 this.yMax=ymax;
    	 this.head = null;
     }
     
     public PRBinNode<E> getRoot() { return head;}
     
     public boolean insert(E elem){
    	
    	if(elem.x<=xMin || elem.x>= xMax || elem.y<yMin || elem.y>yMax){
    		System.out.println("Insert Point is beyond boundary");
    		return false;
    	} else{
    		checkExist success = new checkExist();
    	    head = insertHelp(head,elem, xMin,xMax,yMin,yMax,success);
    	    return success.f;
    	}
     }
     /**
      * The PR Quad Tree Insert Help function will recursively insert element into tree.
      * The assumption for first augment root is that it will always be an internal node.
      * The procedure will be: if the child of root is null, then directly insert this element and done;
      * else check whether this child is leaf node, if it is still internal node continues calling the function with adjusted range.
      * If the child is leaf node, try insert it. It will return true if insert successful; otherwise, decomposition will happen.
      * @param root must be the internal node
      * @param elem which will be inserted into tree
      * @param xlo  x minimum value
      * @param xhi  x maximum value
      * @param ylo  y minimum value
      * @param yhi  y maximum value
      */
     @SuppressWarnings("unchecked")
	public PRBinNode<E> insertHelp(PRBinNode<E> root, E elem,
    		 Integer xlo,Integer xhi,Integer ylo,Integer yhi,checkExist sc){
    	 if(root == null){
    		 PRLeafNode<E> node = new PRLeafNode<E>();
    		 if(node.setVar(elem)) sc.f=true;
    		 return node;
    	 } else {
    		 // get center point
        	 Point origin = new Point(xlo,xhi,ylo,yhi);
        	 range r = new range();
        	 // root is leaf node
        	 if(root.isLeaf()){
        		 ArrayList<E> tmp = (ArrayList<E>) root.getVar();
        		 if( root.setVar(elem)) {
        			 sc.f=true; return root;
        		 }else {
        			 // split happen
        			 PRInternalNode<E> nintern = new PRInternalNode<E>();
        			 if(tmp != null){
        				 for(E var:tmp){
        				  	 int id = var.compareTo(origin);
        		        	 r.change(id, xlo, xhi, ylo, yhi);
        					 nintern.setNodeById(insertHelp(nintern.getNodeById(id),var,r.xmin,r.xmax,r.ymin,r.ymax,sc), id);
        				 }
        			 }
        			 int id = elem.compareTo(origin);
		        	 r.change(id, xlo, xhi, ylo, yhi);
		        	 // insert target element
		        	 nintern.setNodeById(insertHelp(nintern.getNodeById(id),elem,r.xmin,r.xmax,r.ymin,r.ymax,sc), id); 
		        	 return nintern;
        		 } // root.setVar false
        	 } else{
        		 // root is internal
        		 int id = elem.compareTo(origin);
	        	 r.change(id, xlo, xhi, ylo, yhi);
	        	 root.setNodeById(insertHelp(root.getNodeById(id),elem,r.xmin,r.xmax,r.ymin,r.ymax,sc), id); 
	        	 return root;
        	 }		 
    	 }
     }
     public E remove(Point elem){
    	 if(head == null) return null;
    	 checkExist success = new checkExist();
    	 head=removeHelp(head,elem,xMin,xMax,yMin,yMax,success); 	
    	 return success.result;
     }
     /**
      * The key property of any Quad tree data structure is its decomposition rule. 
      * The decomposition rule is what distinguishes the PR Quad tree of this project from the PR Quad tree of Module 15.3. 
      * The decomposition rule for this project is: A leaf node will split into four when 
      * (a) There are more than three points in the node, such that 
      * (b) not all of the point have the same x and y coordinates. 
      * It is also possible for a single deletion to cause a cascading series of node merges.
      * This help function use setNodeById to ensure when recursively delete one element it will change the node correctly
      * when travel to leaf node it will check value first, there is no target element to delete then there is no change and return false for checkExist object
      * If the number of element in leaf node is zero it will return null
      * It same with internal node, only change one internal node to leaf node when all four children of this internal node  either null or leaf node and total element is less than 4
      * 
      * @param root   the entry point node
      * @param elem   the target element to delete
      * @param xlo x minimum value
      * @param xhi x maximum value
      * @param ylo y minimum value
      * @param yhi y maximum value
      * @param sc     checkExist object to determine whether successful on remove
      * @return PRBinNode the result for setNodeById
      */
     public PRBinNode<E> removeHelp(PRBinNode<E> root, Point elem, 
    		 Integer xlo,Integer xhi,Integer ylo,Integer yhi,checkExist sc){
    	 if(root == null) return null;
    	 // The node is leaf node
    	 if(root.isLeaf()){
    		 ArrayList<E> tmp = ((PRLeafNode<E>)root).getVar();
    		 // remove all same point
    		 for(int i=0;i<tmp.size() ; i++){
    			 if(elem.equals(tmp.get(i))) {
    				 sc.result = tmp.get(i);
    				 tmp.remove(i); 
    				 sc.f=true;
    				 break;
    				 }
    		 }
    		 if(tmp.size()==0)  return null;
    	 } else{
    		 // The node is internal node
        	 // get center point and direction
        	 Point origin = new Point(xlo,xhi,ylo,yhi);
        	 int id = elem.compareTo(origin);
        	 range r = new range();
    		 r.change(id, xlo, xhi, ylo, yhi);
        	 // Recursively find element then delete 
    		 root.setNodeById(removeHelp(root.getNodeById(id),elem,r.xmin,r.xmax,r.ymin,r.ymax,sc), id); 
    		 // decomposition only happen there is no internal node and sum of element less then 4
    		 int sum = 0;
    		 boolean flag = true;
    		 for(int i=1;i<=4;i++){
    			 if(root.getNodeById(i)!=null){
    				 flag = flag & root.getNodeById(i).isLeaf();
    				if(flag) sum += ((PRLeafNode<E>)root.getNodeById(i)).getVar().size();
    				// once there is an internal node  decomposition procedure end
    				else break; 
    			 }
    		 }
    		 // only this situation will decomposition
    		 if(flag && sum <=3) {
    			 PRLeafNode<E> newnode = new PRLeafNode<E>();
    			 // get element by id
    			 for(int i=1;i<=4;i++){
        			 if(root.getNodeById(i)!=null){
        				ArrayList<E> tmp = ((PRLeafNode<E>)root.getNodeById(i)).getVar();
        				for(E var:tmp)
        					newnode.setVar(var);
        			 }
        		 }
    			 return newnode;
    		 }
    	 }
    	 return root;
     }
     /**
      * check whether there exist two same point
      * @return
      */
     public String duplicate(){
    	 if(head == null) return "";
    	 return head.duplicate();
     }
     public E search(Point elem){
    	 return searchHelp(head,elem,xMin,xMax,yMin,yMax);	
     }
     @SuppressWarnings("unchecked")
	public E searchHelp(PRBinNode<E> root, Point elem,
    		 Integer xlo,Integer xhi,Integer ylo,Integer yhi){
    	if(root == null) return null;
    	 if(root.isLeaf()){
    		 ArrayList<E> tmp = (ArrayList<E>) root.getVar();
    		 for(E var: tmp){
    			 if(elem.equals(var)) return var;
    		 }
    	 } else{
        	 Point origin = new Point(xlo,xhi,ylo,yhi);
        	 E result= null;
        	 int id = elem.compareTo(origin);
        	 range r = new range();
        	 r.change(id, xlo, xhi, ylo, yhi);
        	 result = searchHelp(root.getNodeById(id),elem,r.xmin,r.xmax,r.ymin,r.ymax);
        	 if(result != null) return result;
    	 }
		 return null;
     }
     public String regionSearch(Integer x,Integer y, Integer width,Integer height){
    	 region task = new region(x,y,width,height);
    	 region base = new region(xMin,yMin,xMax-xMin,yMax-yMin);
    	 
    	 return regionSearch(head,task,base);
     }
     @SuppressWarnings("unchecked")
	protected String regionSearch(PRBinNode<E> root,region task,region base){
    	 if(root == null) return "";
    	 String result = "";
    	 LinkedList<E> points = new LinkedList<E>();
    	 if(task.cover(base)){
    		 root.getAllPoint(root, points);
    		 for(E var:points)
    			 result += var.toString()+"\n";
    	 } else{ // task region didn't cover all base region
    		 if(root.isLeaf()){
    			 for(E var: (ArrayList<E>) root.getVar()){
    				 if(task.inside(var))
    					 result += var.toString()+"\n";
    			 }
    		 } else{
    			 region tmp = null;
    			 region ntask = null;
    		 for(int i=1;i<=4;i++){
    			 tmp = base.splitById(i);
    			 ntask=task.intersection(tmp);
    			 if(root.getNodeById(i)!=null  && !ntask.isEmpty())
    				 result += regionSearch(root.getNodeById(i),ntask,tmp);
    		 }
    		 } // internal node 
    	 } // not cover	 
    	 return result;
     }
     /**
      * dump PR Quad Tree
      */
     public String toString(){
    	 if(this.head != null){
            return this.head.travesal(xMin,yMin,(xMax-xMin),0);
    	 } else {
    		 return "null";
    	 }
     }
     
 
     // Internal Class
     protected class range{
    	 public Integer xmin,xmax,ymin,ymax;
    	 
    	 public range() { xmin = xmax = ymin =null; }
    	 
    	 public void change(int id,Integer xlo,Integer xhi,Integer ylo, Integer yhi){
    		 switch(id){
        	 case 1: xmin=xlo;xmax=(xlo+xhi)/2;ymin=(ylo+yhi)/2;ymax=yhi;break;
        	 case 2: xmin=(xlo+xhi)/2;xmax=xhi;ymin=(ylo+yhi)/2;ymax=yhi;break;
        	 case 3: xmin=xlo;xmax=(xlo+xhi)/2;ymin=ylo;ymax=(ylo+yhi)/2;break;
        	 case 4: xmin=(xlo+xhi)/2;xmax=xhi;ymin=ylo;ymax=(ylo+yhi)/2;break;
        	 default: break;
        	 }
    	 }
     }
     protected class region{
    	 private Integer x,y,w,h;
    	 
    	 public region(Integer x, Integer y,Integer w,Integer h){
    		 this.x=x;
    		 this.y=y;
    		 this.w=w;
    		 this.h=h;
    	 }
    	 
    	 public boolean cover(region r1){
    		 // determine whether this fully contains region r1
    		 if(x<=r1.x && y<=r1.y && (x+w) >=(r1.x+r1.w) && (y+h)>=(r1.y+r1.h))
    			 return true;
    		 else
    			 return false;
    	 }
    	 /**
    	  * Only split base region in quad
    	  * @param id
    	  * @return
    	  */
    	 public region splitById(int id){
    		 Integer tx,ty,tw=w/2,th=h/2;
    		 switch(id){
    		 case 1: tx=x; ty=y+th; break;
    		 case 2: tx=x+tw;ty=y+th;break;
    		 case 3: tx=x;ty=y;break;
    		 case 4: tx=x+tw;ty=y;break;
    		 default: tx=x;ty=y;
    		 }
    		 return new region(tx,ty,tw,th);
    	 }
    	 // get intersection
    	 public region intersection(region r){
    		 Integer tx,ty,tw,th;
    		 tw = Math.min(x+w, r.x+r.w)-Math.max(x, r.x);
    		 th = Math.min(y+h,r.y+r.h)-Math.max(y, r.y);
    		 if(tw < 0 || th <0){
    			 return new region(0,0,0,0);
    		 } else{
    			 tx = Math.min(x+w-tw, r.x+r.w-tw);
    			 ty = Math.min(y+h-th, r.y+r.h-th);
    		    return new region(tx,ty,tw,th);
    		 }
    	 }
    	 public boolean isEmpty() { if(w==0 || h==0) return true; else return false;}
    	 
    	 public boolean inside(Point p){
    		 if(p.x>=x && p.y >= y && p.x<x+w && p.y < y+h)
    			 return true;
    		 else 
    			 return false;
    	 }
    	 
    	 public String toString() { return x.toString()+","+y.toString()+","+w.toString()+","+h.toString();}
     }
     protected class checkExist{
    	 public boolean f;
    	 public E result;
    	 public checkExist() {f = false; result = null;}
     }
}
