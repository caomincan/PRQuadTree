import java.util.ArrayList;
import java.util.LinkedList;

public class PRLeafNode<E extends Point2<?>> implements PRBinNode<E> {
    private static final int DEFAULT_SIZE=3;
	private ArrayList<E> var;
    
    public PRLeafNode(){
    	this.var = new ArrayList<E>(DEFAULT_SIZE);
    }
    
    public boolean setVar(E var){
    	if(this.var.size()< DEFAULT_SIZE){
    		 this.var.add(var);
    	     return true;
    	} else{
    		return false;
    	}
    }
    
    public ArrayList<E> getVar(){
    	return this.var;
    }
	@Override
	public boolean isLeaf() {
		return true;
	}

	@Override
	public String travesal(Integer x, Integer y,Integer scale, int level) {
		String result = "";
		 for(int i=0;i<level;i++)
		    	result +="  ";
		 result += "Node at "+x.toString()+", "+y.toString()+", "+scale.toString()+":\n";

		for(E elem : var){
			 for(int i=0;i<level+1;i++)
			    	result +="  ";
			result +=  elem.toString()+"\n";
			}
		return result;
	}

	@Override
	public int height (int level) {
		return level+1;
	}

	@Override
	public String duplicate() {
		if(var.size()>=2){
			String result ="";
			for(int i=0;i<var.size()-1;i++){
				for(int j=i+1;j<var.size();j++){
					if(var.get(i).equals(var.get(j))) result += var.get(i).toString()+" ";				
					}
			}
			return result;
		} else
		  return "";
	}

	@Override
	public void getAllPoint(PRBinNode<E> root, LinkedList<E> list) {
		for(E elem : var)
			list.add(elem);
	}

	@Override
	public PRBinNode<E> getNodeById(int id) {return this;}

	@Override
	public void setNodeById(PRBinNode<E> root, int id) {}
}
