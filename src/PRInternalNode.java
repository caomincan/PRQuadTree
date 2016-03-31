import java.util.LinkedList;

public class PRInternalNode<E extends Point2<?>> implements PRBinNode<E> {
    
	private PRBinNode<E> NE,NW,SE,SW;
		
	public PRInternalNode() {
		this.NE=this.NW=this.SE=this.SW=null;
	}
	
	public void setNodeById(PRBinNode<E> root,int id){
		switch(id){
		case 1: NW = root;break;
		case 2: NE = root;break;
		case 3: SW = root;break;
		case 4: SE = root;break;
		default: System.out.println("The input direction is not right(1-4)");
		}
	}
	public PRBinNode<E> getNodeById(int id){
		switch(id){
		case 1: return this.NW;
		case 2: return this.NE;
		case 3: return this.SW;
		case 4: return this.SE;
		default: System.out.println("The input direction is not right(1-4)");
		         return null;
		}
	}
	@Override
	public boolean isLeaf() {
		return false;
	}

	@Override
	public String travesal(Integer x,Integer y, Integer scale,int level) {
		    String result = "";
		    for(int i=0;i<level;i++){
		    	result +="  ";
		    }
		    result += "Node at "+x.toString()+", "+y.toString()+", "+scale.toString()+": Internal\n";
		    if(SW != null){
		    	result+=SW.travesal(x,y,scale/2, level+1); 
		    } else{
		    	for(int i=0;i<level+1;i++)
			    	result +="  ";
				Integer tmpx = x;Integer tmpy = y; Integer tmps = scale/2;
				result+= "Node at "+tmpx.toString()+", "+tmpy.toString()+", "+tmps.toString()+": Empty\n";
		    }
		    if(SE != null){
		    	result+=SE.travesal(x+scale/2,y,scale/2,level+1); 
		    } else{
		    	for(int i=0;i<level+1;i++)
			    	result +="  ";
				Integer tmpx = x+scale/2;Integer tmpy = y; Integer tmps = scale/2;
				result+= "Node at "+tmpx.toString()+", "+tmpy.toString()+", "+tmps.toString()+": Empty\n";
		    }
		    if(NW != null){
				result+=NW.travesal(x,y+scale/2,scale/2,level+1);  
			} else {
				for(int i=0;i<level+1;i++)
			    	result +="  ";
				Integer tmpx = x;Integer tmpy = y+scale/2; Integer tmps = scale/2;
				result+= "Node at "+tmpx.toString()+", "+tmpy.toString()+", "+tmps.toString()+": Empty\n";
			}
			if(NE != null){ 
				result+=NE.travesal(x+scale/2,y+scale/2,scale/2, level+1); 
			} else{
				for(int i=0;i<level+1;i++)
			    	result +="  ";
				Integer tmpx = x+scale/2;Integer tmpy = y+scale/2; Integer tmps = scale/2;
				result+= "Node at "+tmpx.toString()+", "+tmpy.toString()+", "+tmps.toString()+": Empty\n";
			}	  
		    return result;
	}

	@Override
	public int height(int level) {
		int a,b,c,d;
		if(NW != null) a=NW.height(level+1); else a = level;
		if(NE != null) b=NE.height(level+1); else b = level;		
		if(SW != null) c=SW.height(level+1); else c = level;
		if(SE != null) d=SE.height(level+1); else d = level;
		return Math.max(Math.max(a, b),Math.max(c, d));
	}

	@Override
	public String duplicate() {
		String result = "";
		if(NW != null) result+=NW.duplicate(); 
		if(NE != null) result+=NE.duplicate(); 
		if(SW != null) result+=SW.duplicate(); 
		if(SE != null) result+=SE.duplicate(); 
		return result;
	}

	@Override
	public void getAllPoint(PRBinNode<E> root,LinkedList<E> list) {
		if(root==null) return;
		if(NW != null) NW.getAllPoint(NW, list); 
		if(NE != null) NE.getAllPoint(NE, list);
		if(SW != null) SW.getAllPoint(SW, list);
		if(SE != null) SE.getAllPoint(SE, list);
	}

	@Override
	public boolean setVar(E elem) {return false;}

	@Override
	public Object getVar() {return null;}
}
