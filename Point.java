

class Point{
	private double xPos;
	private double yPos;

	public Point(){
		xPos = 0;
		yPos = 0;
	}

	public Point(double x, double y){
		xPos = x;
		yPos = y;
	}

	public void setX(double newX){
		xPos = newX;
	}

	public void setY(double newY){
		yPos = newY;
	}

	public double getX(){
		return xPos;
	}

	public double getY(){
		return yPos;
	}

	@Override
	public String toString(){
		return (String.format("(%.4f, %.4f)", xPos, yPos));
	}
}