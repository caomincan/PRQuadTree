import java.util.Random;

public class SkipList<K extends Comparable<K>,V> {
	
	private SkipNode<K,V> head;
	private SkipNode<K,V> tail;
	private SkipNode<K,V> current;
	private Random rnd; //Random number generator
	int maxlevel;
	int size;
	/**
	 * Class Object Constructor
	 */
	SkipList() 
	{
		maxlevel=1;
		size = 0;
		current= tail = new SkipNode<K,V>(null,maxlevel);
		head = new SkipNode<K,V>(null,maxlevel);
	    head.setSuc(tail, 0);
	    rnd = new Random();
	}
	/** clear
	 *  clear skip list as empty
	 */
	public void clear()
	{
		current= tail = new SkipNode<K,V>(null,maxlevel);
		maxlevel=1;
	    size = 0;
		head = new SkipNode<K,V>(null,maxlevel);
	    head.setSuc(tail, 0); 
	    rnd = new Random();
	}
	/** insert  
	 * insert KVPair data into list
	 * go through different levelList find insert position
	 * insert data into that position
	 * update  search path
	 * @param k Comparable key value
	 * @param e Stored element 
	 */
	public void insert(K k, V e)
	{
		KVPair<K,V> tmp = new KVPair<K, V>(k,e);
		// find position to insert
		current = Inserthelp(k);
		// get random level
		int lev = RandomLev();
		// create new node store insert position element
		SkipNode<K,V> newnode= new SkipNode<K,V>(current.getEle(),maxlevel);
		newnode.setCurLev(current.getCurlevel());
		// copy current node successor to new node
		for(int i=0;i<Math.min(lev, current.getCurlevel());i++)
		        newnode.setSuc(current.getSuc(i), i);
		// set current node successor to be new node
		for(int i=0;i<Math.min(lev, current.getCurlevel());i++)
		    current.setSuc(newnode,i);
		current.setCurLev(lev);
		current.setEle(tmp);
		// at tail position
		if(current == tail ) { tail = newnode;}
		size++;
		// update search head
		updateLev();
		// update list above level 0
		updateList();
	}
	/**
	 * Return the information about the rectangle(s), 
	 * if any that have the same k
	 * @param k comparable key
	 * @return
	 */
	public SkipNode<K,V> search(K k)
	{
		// Temp SkipNode
		SkipNode<K,V> x;
		// Insert help function not necessary find out equal key
		x = Inserthelp(k);
		if(x.getEle() != null)
		{
			// Confirm result's key equal k
			if(x.getEle().key().compareTo(k) == 0)
				return x;
			else
				return null;
		}
		else
			return null;
	}
	/**
	 *  Remove one node which key value equals k
	 * @param k
	 * @return The element of removed node
	 */
	public KVPair<K,V> remove(K k)
	{
		SkipNode<K,V> x,y; 
		KVPair<K,V> tmp;
		// find Node contain Key
		x= search(k);
		if( x != null)
		{
		   // get x's next node position
		   y = x.getSuc(0);
		   // temp store value
		   tmp = x.getEle();
		   // replace x's current level with x's next
		   x.setCurLev(y.getCurlevel());
		   // x's pointer will point to x next 2 node
		   for(int i=0;i<maxlevel;i++)
			   x.setSuc(y.getSuc(i), i);
		   // replace x value with x's next
		   x.setEle(y.getEle());
		   size--;
		   // Due to size change update
		   updateLev();
		   // Update each linked node
		   updateList();
		   return tmp;
		}
		else // not found
		  return null;
	}
	/**
	 *   random level generator
	 * @return random number
	 */
 	private int RandomLev()
	{
		return rnd.nextInt(maxlevel)+1;
	}
	/**
	 *  Update each node by used number of level
	 */
    private void updateList()
	{
		SkipNode<K,V> x,y;
		
		if(maxlevel>1)
		{
			for(int i= maxlevel-1;i>=1;i--)
			{
				x = head;
				// the first node except head
				y= x.getSuc(0);
				// j<= size could run at tail
				for(int j=0;j<= size;j++)
				{
					if(y.getCurlevel() > i)
					{	
						x.setSuc(y, i);
					    x = x.getSuc(i);
					}
					y = y.getSuc(0);
				}
			}
		}
	}
     /**
      * Insert help function. To find out the right insert position
      * @param k: Comparable key
      * @return
      */
	private SkipNode<K,V> Inserthelp(K k)
	{
		SkipNode<K,V> x,y;
		x= head;
		y =tail;
		// start from highest level to zero
		int i = maxlevel-1;
		// while x node is not the tail
		    while(x!= y)
			{
		    	// avoid null element raise pointer exception 
				if(x.getSuc(i).getEle() != null)
				{   
					// get current x's next node key value
					K key=x.getSuc(i).getEle().key();
				   
				 if(k.compareTo(key) > 0) 
					 // move to same level next element
					 x = x.getSuc(i);
				   
				 else if(k.compareTo(key)<0)
				 { 
					 // move to next level or return
					  i--;
					 // if at 0 level
					  if(i<0) return x.getSuc(0);
				 }
				 else 
					// key equal, already found end searching
					 return x.getSuc(i);
			    }
				else 
				{
					// at head position and head point to tail move next level 
					if(i>0)
						i--;
					// already at 0 level, which means list is empty
					else
						return x.getSuc(0);
				}
			 }
		return x;
	}
    /**
     *  Update skip list max level value 
     */
	private void updateLev( )
	{
		SkipNode<K,V> x;
		x= head;
		int tmpLev = maxlevel;
		// according to 2^n determine maximum level
		if(size > Math.pow(2, maxlevel))
		{	
		   tmpLev++;
		   head.adjustLev(tmpLev);
		   tail.adjustLev(tmpLev);
		   head.setSuc(tail, maxlevel);
		}
		else if (size < Math.pow(2, maxlevel) && maxlevel>1)
			tmpLev--;	
		// Only update each node pointer list when change
		if (tmpLev != maxlevel)
		{
			head.setCurLev(tmpLev);
			tail.setCurLev(tmpLev);
			maxlevel = tmpLev;
		   for(int i =0 ; i<= size;i++)
		   {
			   x.adjustLev(maxlevel);
			   x = x.getSuc(0);
		   }
		}
	}
	
	// print node
	public String print()
	{
		SkipNode<K,V> x;
	    x = head;
	    int count =0;
	    String result="";
		while(x != null)
		{  
		   if(x.toString(1)!=null){
			   result += x.toString(1);
			   count++;
		   }
		   if(x.getSuc(0)!= null) x = x.getSuc(0);
			else break;
		}
		result += "SkipList size is:" + String.valueOf(count)+"\n";
		return result;	
	}
}