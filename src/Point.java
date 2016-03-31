
public class Point implements Comparable<Point> {
	public Integer x;
	public Integer y;
	
	public Point() {}
	/**
	 * Constructor
	 * @param x
	 * @param y
	 */
	public Point(Integer x, Integer y){
		this.x = x;
		this.y = y;
	}
	/**
	 * create middle point
	 * @param xlo
	 * @param xhi
	 * @param ylo
	 * @param yhi
	 */
	public Point(Integer xlo,Integer xhi,Integer ylo,Integer yhi){
		this.x = (xlo+xhi)/2;
		this.y = (ylo+yhi)/2;
	}
	
	@Override
	public int compareTo(Point o) {
		if(this.x <= o.x && this.y>=o.y){
			return 1;
		} else if(this.x>o.x&&this.y>o.y){
			return 2;
		} else if(this.x<o.x && this.y<o.y){
			return 3;
		} else {
			return 4;
		} 
	}
	public boolean equals(Point o){
		if(this.x == o.x && this.y == o.y){
			return true;
		} else{
			return false;
		}
	}
	public String toString(){
		return "("+x.toString()+","+y.toString()+")";
	}

}
