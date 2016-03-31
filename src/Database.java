public class Database<K extends Comparable<K>,E extends Point2<K>> {
         private SkipList<K,E> list;
         private PRTree<E> tree;
         
        public Database(Integer xmin,Integer xmax,Integer ymin,Integer ymax) { 
        	this.list = new SkipList<K,E>();
        	this.tree = new PRTree<E>(xmin,xmax,ymin,ymax);
        	}
        public SkipList<K,E> getList() { return list;}
        public PRTree<E> getTree()  { return tree;}
        
        public void insert(K k,E e){
        	
        	if(tree.insert(e)){
        	     list.insert(k, e);
        	     System.out.println("Point inserted: "+e.toString());
        	}
        	else
        		System.out.println("Insert Fail");
        }
        
        public void remove(K k){
        	KVPair<K,E> tmp =list.remove(k);
        	if(tmp != null){
        		    tree.remove(tmp.value());
        		    System.out.println("Point removed: "+tmp.value().toString());
        	}
        	else
        		System.out.println("Remove Fail: list tmp == null");
        	
        }
        
        public void remove(Point e){
        	E p = tree.remove(e);
        	if(p != null){
        		list.remove(p.key());
        		 System.out.println("Point removed: "+p.toString());
        	}
        	else
        		System.out.println("Remove Fail: tree p == null");
        }
        
        public void dump(){
        	System.out.println("SKipList:");
        	System.out.print(list.print());
        	System.out.println("Quadtree:");
        	System.out.print(tree.toString());
        }
        public String duplicate(){
        	return "Duplicate Points:\n"+tree.duplicate();
        }
        public String search(Point e){
        	String result = "Found :";
        	E tmp=tree.search(e);
        	if(tmp == null) 
        		return result+"null";
        	else
        	    return result+tmp.toString();
        }
        public String search(K k){
        	String result = "Found :";
        	SkipNode<K,E> tmp =list.search(k);
        	if(tmp == null) 
        		return result+"null";
        	else
        	    return result+tmp.getEle().value().toString();
        }
        
        public String regionsearch(Integer x,Integer y,Integer width,Integer height){
        	return tree.regionSearch(x, y, width, height);
        }
}
