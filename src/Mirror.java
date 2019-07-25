import java.awt.Rectangle;

public class Mirror implements GlobalConst{
	private int x, y, w, h;
	
	public Mirror(int x, int y) {
		this.x = x;
		this.y = y;
		
		w = gameVar.mirrorImage.getWidth(null);
		h = gameVar.mirrorImage.getHeight(null);
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getW() {
		return w;
	}
	
	public int getH() {
		return h;
	}
	
	public Rectangle getRect() {
		return new Rectangle(x, y, w, h);
	}
}