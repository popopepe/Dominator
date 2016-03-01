package web.proj;

public class Player implements Comparable<Player> {
	private String session;
	private String id;
	private Plant p;
	private String color;
	private int mis;
	private int point;

	public Player(String session, String id, Plant p, String color) {
		this.session = session;
		this.id = id;
		this.p = p;
		this.color = color;
		this.mis = 5;
		this.point = 0;
	}

	public Player() {
		this.mis = 5;
		this.point = 0;
	}

	public String getSession() {
		return session;
	}

	public void setSession(String session) {
		this.session = session;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Plant getP() {
		return p;
	}

	public void setP(Plant p) {
		this.p = p;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public int getMis() {
		return mis;
	}

	public void setMis(int mis) {
		this.mis = mis;
	}

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}

	public void addPoint() {
		this.point++;
	}

	public void addMis() {
		if (this.mis < 5)
			this.mis++;
	}

	public boolean useMis() {
		if (this.mis > 0) {
			this.mis--;
			return true;
		}
		return false;
	}

	@Override
	public int compareTo(Player o) {
		return o.point - this.point;
	}
}
