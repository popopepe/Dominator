package web.proj;

public class Missile {

	public static final int MIS_SPD = 40;
	public static final int MIS_MAX_RAGE = 80;

	private int code;
	private double spdX;
	private double spdY;
	private int range;
	private int x;
	private int y;
	private String id;
	private String color;
	private double deg;
	private boolean crash;

	public Missile() {
		this.crash = false;
	}

	public Missile(int code, double spdX, double spdY, int range, int x, int y,
			String id, String color, double deg) {
		this.code = code;
		this.spdX = spdX;
		this.spdY = spdY;
		this.range = range;
		this.x = x;
		this.y = y;
		this.id = id;
		this.color = color;
		this.deg = deg;
		this.crash = false;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public double getSpdX() {
		return spdX;
	}

	public void setSpdX(double spdX) {
		this.spdX = spdX;
	}

	public double getSpdY() {
		return spdY;
	}

	public void setSpdY(double spdY) {
		this.spdY = spdY;
	}

	public int getRange() {
		return range;
	}

	public void setRange(int range) {
		this.range = range;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public double getDeg() {
		return deg;
	}

	public void setDeg(double deg) {
		this.deg = deg;
	}

	public boolean isCrash() {
		return crash;
	}

	public void setCrash(boolean crash) {
		this.crash = crash;
	}

}
