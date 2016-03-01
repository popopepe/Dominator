package web.proj;

public class Plant {

	// 고유값
	private String id;

	// 부모행성
	private Plant parent;

	// 좌표, 반지름
	private int x;
	private int y;
	private int r;

	// 공전
	private int revR;
	private double revSpd;
	private double revAngle;

	// 모양
	private String type;

	// 소유자
	private String own;
	private String ownColor;

	public Plant() {
	}

	public Plant(String id, Plant parent, int x, int y, int r, int revR,
			double revSpd, double revAngle, String type, String own,
			String ownColor) {
		this.id = id;
		this.parent = parent;
		this.x = x;
		this.y = y;
		this.r = r;
		this.revR = revR;
		this.revSpd = revSpd;
		this.revAngle = revAngle;
		this.type = type;
		this.own = own;
		this.ownColor = ownColor;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Plant getParent() {
		return parent;
	}

	public void setParent(Plant parent) {
		this.parent = parent;
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

	public int getR() {
		return r;
	}

	public void setR(int r) {
		this.r = r;
	}

	public int getRevR() {
		return revR;
	}

	public void setRevR(int revR) {
		this.revR = revR;
	}

	public double getRevSpd() {
		return revSpd;
	}

	public void setRevSpd(double revSpd) {
		this.revSpd = revSpd;
	}

	public double getRevAngle() {
		return revAngle;
	}

	public void setRevAngle(double revAngle) {
		this.revAngle = revAngle;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getOwn() {
		return own;
	}

	public void setOwn(String own) {
		this.own = own;
	}

	public String getOwnColor() {
		return ownColor;
	}

	public void setOwnColor(String ownColor) {
		this.ownColor = ownColor;
	}

}
