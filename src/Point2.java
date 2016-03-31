import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * { your description of the project here }
 */

/**
 * The class containing the main method, the entry point of the application.
 *
 * @author {your name here}
 * @version {put something here}
 */
public class Point2<K> extends Point
{
    private K key;
    
	public Point2(K key, String x, String y) {
		super(Integer.valueOf(x),Integer.valueOf(y));
		this.key=key;
		}

	public Point2(K key,Integer x, Integer y){
		super(x,y);
		this.key = key;	
	}
	
	public Point2(K key,Integer xlo,Integer xhi,Integer ylo,Integer yhi){
		super(xlo,xhi,ylo,yhi);
		this.key = key;
	}
    	
	public Integer getX() { return this.x;}
	public Integer getY() { return this.y;}
	public K key() {return this.key;}
	
	public String toString(){
		return "("+key.toString()+","+x.toString()+","+y.toString()+")";
	}
    /**
     * The entry point of the application.
     *
     * @param args
     *            The name of the command file passed in as a command line
     *            argument.
     */
    public static void main(String[] args)
    {
    	String line = null;
    	List<String> records = new ArrayList<String>();
    	
    	Integer xmin = 0;
    	Integer xmax = 1024;
    	Integer ymin = 0;
    	Integer ymax = 1024;
    	Database<String,Point2<String>> disc = new Database<String,Point2<String>>(xmin,xmax,ymin,ymax);
       
       try{
           // wrap a BufferedReader around FileReader
             BufferedReader bufferedReader = new BufferedReader(new FileReader(args[0])); 
        	    while ((line = bufferedReader.readLine()) != null)
        	        records.add(line);
        	    // close the BufferedReader when we're done
        	    bufferedReader.close();
        	    for(int i =0; i<records.size();i++)
                {
                	line = records.get(i); // Retrieve a line
                	String[] splited=line.split(" "); // remove ' ' to get refine the line
            		int count = 0;
            		ArrayList<String> refine = new ArrayList<String>();
            		for(int j =0; j<splited.length;j++)
            		{   // only keep words in refine array
            			if(!splited[j].equals("")) {refine.add(splited[j]);count++;}
            		}
            		if(count!=0)
            		{	
            		switch( refine.get(0))
            		{
            		      case "insert" :
            		    	  try{
            		    		  if(refine.get(1).matches("^[A-Za-z].*")&& refine.get(2).matches("-?\\d+")&& refine.get(3).matches("-?\\d+")){
            		    		  Point2<String> p = new Point2<String>(refine.get(1),refine.get(2),refine.get(3));
            		    		  disc.insert(refine.get(1),p);
            		    		  } else
            		    			  System.out.println("Insert Wrong Format:"+refine.get(1)+" ("+refine.get(2)+","+refine.get(3)+")");
            		    	  } catch(Exception e) {
            		    		  System.out.println("insert command invalid");
            		    	  }
            		    	  break;
            		     
            		      case "search": 
            		    	  try{
            		    		  if(refine.get(1).matches("-?\\d+")&& refine.get(2).matches("-?\\d+")){
            		    		     Point tmp = new Point(Integer.valueOf(refine.get(1)),Integer.valueOf(refine.get(2)));
            		    		     System.out.println(disc.search(tmp));
            		    		  } else
            		    			  System.out.println("Search wrong format: ("+refine.get(1)+","+refine.get(2)+")");	 
            		    	  } catch(Exception e) {}
            		    	  try{
            		    		  if(refine.get(1).matches("^[A-Za-z].*")){
            		    			  System.out.println(disc.search(refine.get(1)));
            		    		  }
            		    		  else{
            		    			  System.out.println("Search wrong format:"+refine.get(1));
            		    		  }
            		    	  } catch(Exception e) {}
            		    	  break;

            		      case "remove":
            		    	  try{
            		    		  if(refine.get(1).matches("-?\\d+")&& refine.get(2).matches("-?\\d+")){
            		    		     Point tmp = new Point(Integer.valueOf(refine.get(1)),Integer.valueOf(refine.get(2)));
            		    		     disc.remove(tmp);
            		    		  } else
            		    			  System.out.println("Remove wrong format: ("+refine.get(1)+","+refine.get(2)+")");	 
            		    	  } catch(Exception e) {}
            		    	  try{
            		    		  if(refine.get(1).matches("^[A-Za-z].*")){
            		    			  disc.remove(refine.get(1));
            		    		  }
            		    		  else{
            		    			  System.out.println("Remove wrong format:"+refine.get(1));
            		    		  }
            		    	  } catch(Exception e) {}
            		    	  break;
            		    	  
            		      case "regionsearch": 
                               try{
                            	   if(refine.get(1).matches("-?\\d+")&& refine.get(2).matches("-?\\d+")
                            			   && refine.get(3).matches("-?\\d+")&& refine.get(4).matches("-?\\d+")){
              		    		     String result=disc.regionsearch(Integer.valueOf(refine.get(1)),Integer.valueOf(refine.get(2)),
              		    		    		 Integer.valueOf(refine.get(3)),Integer.valueOf(refine.get(4)));
              		    		     System.out.println("Region search found:");
              		    		     System.out.println(result);
              		    		  } else
              		    			  System.out.println("Regionsearch wrong format");	 
              		    	  } catch(Exception e) {
              		    		  System.out.println("Regionsearch is invalid");
              		    	  }
                              break;
                              
            		      case "duplicate":  System.out.println(disc.duplicate()); break;
            		      case "dump":  disc.dump();break;
                         } // end switch case
            		}// if count !=0, means there exists meaningful words in one line     	  
               } // end for read records
        		
        } catch(Exception e){
        	System.out.println("Useage: Point2 inputfile");
        }    
    }// end main function   
}// end of class
