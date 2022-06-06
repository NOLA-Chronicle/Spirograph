import java.util.*;
import java.io.*;
import java.awt.Color;
import java.awt.Graphics2D; 
import java.awt.image.BufferedImage;
import javax.imageio.IIOImage; 
import javax.imageio.ImageIO; 
import javax.imageio.ImageWriteParam; 
import javax.imageio.ImageWriter; 
import javax.imageio.plugins.jpeg.JPEGImageWriteParam; 
import javax.imageio.stream.ImageOutputStream;

class Arm{
	private static ArrayList<Arm> spirograph = new ArrayList<Arm>();
	private static int multiplier = 1;
	private static double DEFAULT_SPEED = 1;

	private Point prevPt = new Point();
	private Point endPt = new Point();
	private Point centerPt = new Point();
	private int length;
	private double speed;
	private double angle;

	public Arm(){
		this(50, DEFAULT_SPEED, 90.0);
	}

	public Arm(int length, double speed, double startAngle){
		this.length = length;
		this.speed = speed;
		angle = startAngle;
		spirograph.add(this);

		if(spirograph.size() > 1){
			centerPt = spirograph.get(spirograph.indexOf(this) - 1).getEndPt();
		}

		endPt.setX(multiplier * this.length * Math.cos(angle * Math.PI / 180) + this.centerPt.getX());
		endPt.setY(multiplier * this.length * Math.sin(angle * Math.PI / 180) + this.centerPt.getY());
	}

	public static void setMultiplier(int mult){
		multiplier = mult;
	}

	public static void setDefaultSpeed(double speed){
		DEFAULT_SPEED = speed;
	}

	public static void setLength(int index, Integer newLength){
		spirograph.get(index).length = newLength.intValue();
	}

	public static void setSpeed(int index, double newSpeed){
		spirograph.get(index).speed = newSpeed;
	}

	private Point getEndPt(){
		return endPt;
	}

	private Point getPrevPt(){
		return prevPt;
	}

	private double getSpeed(){
		return speed;
	}

	private int getLength(){
		return length;
	}

	public static void getPoints(String fileName){
		FileOutputStream outStream = null;
		PrintWriter out = null;

		final int MAX_TIME = getMinTime(); //set to -1 for endless. NO ESCAPE IMPLEMENTED YET!
		int timer = 0;
		String listOfPoints = "";
		
		System.out.println(MAX_TIME);

		try{
			System.out.println("Opening file- " + fileName);
			outStream = new FileOutputStream(fileName);
			out = new PrintWriter(outStream);

			while(timer != MAX_TIME){
				timer++;

				for(Arm arm:spirograph){
					arm.tick();
				}

				listOfPoints = listOfPoints + (spirograph.get(spirograph.size() - 1).getEndPt().toString() + ", ");
			}
			out.print(listOfPoints.substring(0, listOfPoints.length() - 2));
			

		} catch(IOException e){
			System.out.println("IOException in file!");
			System.out.println(e.getMessage());

		// }catch (Exception e){
		// 	System.out.println("Regular exception in file!");
		// 	System.out.println(e.getMessage());

		} finally {
			System.out.println("Closing file- " + fileName);
			out.close();
		}
	}

	public static BufferedImage makeImage() throws IOException{
		//image block size in pixels, 1 is 1px, use smaller values for 
 		//greater granularity 
		int PIX_SIZE = 1;
 		//image size in pixel blocks 
		int X = 350;
		int Y = X;

		BufferedImage bi = new BufferedImage( PIX_SIZE * X, PIX_SIZE * Y, BufferedImage.TYPE_3BYTE_BGR );
		Graphics2D g = (Graphics2D) bi.getGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, X, Y);

		g.setColor(Color.BLACK);

		final int MAX_TIME = getMinTime();
		int timer = 0;

		System.out.println(MAX_TIME);
		System.out.println("Image size: " + X + "x" + Y);

		while(timer != MAX_TIME){
			timer++;

			for(Arm arm:spirograph){
				arm.tick();
			}

			Arm drawPt = spirograph.get(spirograph.size() - 1);
			g.drawLine((int) Math.round(drawPt.getPrevPt().getX() + (X / 2)), (int) Math.round(drawPt.getPrevPt().getY() + (Y / 2)), (int) Math.round(drawPt.getEndPt().getX() + (X / 2)), (int) Math.round(drawPt.getEndPt().getY() + (Y / 2)));
		}

		g.dispose();
		System.out.println("Returning image...");
		return bi;
	}

	/** Saves jpeg to file * */ 
 	public static void saveToFile( BufferedImage img, File file ) throws IOException { 
 		ImageWriter writer = null; 
 		java.util.Iterator iter = ImageIO.getImageWritersByFormatName("jpg"); 
 		
 		if( iter.hasNext() ){ 
 			writer = (ImageWriter)iter.next(); 
 		} 
 		
 		ImageOutputStream ios = ImageIO.createImageOutputStream( file ); 
 		
 		writer.setOutput(ios); 
 		
 		ImageWriteParam param = new JPEGImageWriteParam( java.util.Locale.getDefault() ); 
 		
 		param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
 		param.setCompressionQuality(0.98f); 
 		writer.write(null, new IIOImage( img, null, null ), param); 
 	}//end method 

	private void tick(){
		prevPt.setX(endPt.getX());
		prevPt.setY(endPt.getY());

		angle += speed;
		angle %= 360;

		endPt.setX(multiplier * length * Math.cos(angle * Math.PI / 180) + centerPt.getX());
		endPt.setY(multiplier * length * Math.sin(angle * Math.PI / 180) + centerPt.getY());
	
	}

	//getMinTime functions
	private static int getMinTime(){
		ArrayList<Integer> revs = new ArrayList<Integer>();
		int arrayRevs[];

		for(Arm arm:spirograph){
			revs.add((int)Math.round(360 / Math.abs(arm.getSpeed())));
		}

		arrayRevs = new int[revs.size()];

		for(int i = 0; i < revs.size(); i++){
			arrayRevs[i] = (int)revs.get(i);
		}

		return lcm(arrayRevs) + 10;
	}

	private static int gcd(int a, int b){
		while (b > 0){
			int temp = b;
        	b = a % b; // % is remainder
        	a = temp;
        }
        return a;
    }

    private static int lcm(int a, int b){
    	return a * (b / gcd(a, b));
    }

    private static int lcm(int[] input){
    	int result = input[0];
    	
    	for(int i = 1; i < input.length; i++){
    		result = lcm(result, input[i]);
    	}

    	return result;
    }
}