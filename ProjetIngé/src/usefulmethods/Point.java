package usefulmethods;

public class Point {

	public Point(double x, double y){
		setX(x); setY(y);
	}
	
	public Point() {
		setX(0);setY(0);
	}

	double x_;
	double y_;
	
	public double getX() {
		return x_;
	}
	public void setX(double x) {
		this.x_ = x;
	}
	public double getY() {
		return y_;
	}
	public void setY(double y) {
		this.y_ = y;
	}
	public String toString(){
		return "(" + x_ + " ; " + y_ + ")";
		
	}
	public void set(double x, double y){
		setX(x);setY(y);
	}
	public boolean equals(Point p){
		if( (this.x_ == p.x_) && (this.y_ == p.y_) )
			return true;
		return false;
	}
}

