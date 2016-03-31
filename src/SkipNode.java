import java.lang.reflect.Array;

public class SkipNode<K extends Comparable<K>,V> {
	private KVPair<K,V> element; // store KVpair as value
	private int maxlevel;  // maximum level,at least 1
	private int curlevel;  // current used level <= maximum level, at least 1
	private SkipNode<K,V>[] successor; //same level next node, non-deterministic array
	
	
	// constructor
	SkipNode() { maxlevel=curlevel=0; successor = null;}
	@SuppressWarnings("unchecked")
	SkipNode(KVPair<K,V> element, int maxlevel)
	{
		this.element = element;
		this.maxlevel = maxlevel;	
		this.curlevel = 1;
		// according max level assign array size
	    this.successor = (SkipNode<K,V>[]) Array.newInstance(SkipNode.class,maxlevel);
	    for(int i = 0;i< maxlevel;i++)
	    	this.setSuc(null, i);
	}
	
    // access member
	public KVPair<K,V> getEle() { return element;}
	public int getCurlevel()  { return curlevel;}
	public int getMaxlevel()  { return maxlevel;}
	public SkipNode<K,V> getSuc(int lev) {return successor[lev];}
	// set member
	public void setSuc(SkipNode<K,V> successor,int lev) { this.successor[lev] = successor;}
    public void setEle(KVPair<K,V> element) { this.element = element;}
    public void setCurLev(int lev) { this.curlevel=lev;}
	@SuppressWarnings("unchecked")
	/**
	 * Adjust node successor size due to max level change
	 * current level not necessary equals to max level
	 * @param newmaxlev: new max level could increase or decrease
	 */
	public void adjustLev(int newmaxlev)
	{
		// only adjust it when change
		if(newmaxlev != maxlevel)
		{
			// define new successor array
			SkipNode<K,V>[] tmp = (SkipNode<K,V>[]) Array.newInstance(SkipNode.class,newmaxlev);
			// copy existing value in previous array
		    for(int i=0;i< Math.min(maxlevel, newmaxlev);i++)
			     tmp[i] = this.successor[i];
		    // new level larger than previous assign null for rest part
		    if(newmaxlev>maxlevel)
		    {
			    for(int i=maxlevel;i<newmaxlev;i++)
				    tmp[i]= null;
		    }
		    // update value
		     maxlevel = newmaxlev;
		     successor = tmp;
		}
	}
    public String toString()
    {
    	String ele,tmp;
    	if(element == null)
    		 ele = new String("null");
    	else
    		ele = element.toString();
    	tmp = " Depth: "+Integer.toString(curlevel)+" ID: "+Integer.toString(this.hashCode()%1000)+" ";
    	for(int i = 0; i<maxlevel;i++)
    		if (successor[i] != null)
    		tmp +=" Level "+Integer.toString(i)+":  "+ Integer.toString(successor[i].hashCode()%1000);
    		else
    			tmp +=" Level "+Integer.toString(i)+": null";
    	return tmp+"  "+ele+"\n";
    }
    public String toString(int k)
    {
    	String ele,tmp;
    	if(element == null)
    		 return null;
    	else
    		ele =element.value().toString();
    	tmp = "Node has depth: "+Integer.toString(curlevel)+", Value ";
    	return tmp+ele+"\n";
    }
	
}
