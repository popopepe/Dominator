package web.proj;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import com.google.gson.JsonElement;

public class GameManager {

	public static final String ADMIN_PW = "dominator1";
	public static final String A_PLANTS = "plants";
	public static final String A_MAP_SIZE_X = "sizex";
	public static final String A_MAP_SIZE_Y = "sizey";
	public static final String A_PLAYER = "player";
	public static final String A_PLAYER_ID = "playerid";
	public static final String A_MIS_RANGE = "mismaxrange";
	public static final String A_GAME_CODE = "gamecode";
	public static final String A_WINNER = "winner";

	public static final int MAP_SIZE_X = 1500;
	public static final int MAP_SIZE_Y = 1500;
	public static final int NUM_OF_PLANT = 2;
	public static final int MIS_RANGE = Missile.MIS_MAX_RAGE;
	public static String[] colorSet = { "3", "4", "5", "6", "7", "8", "9", "a",
			"b", "c", "d", "e", "f" };

	// ���� ȸ��
	private int gameCode;
	private String winner;

	// �̱���
	private static GameManager manager = new GameManager();

	// plant
	private ArrayList<Plant> plants;
	private int numOfPlayer;

	// player
	private ArrayList<Player> players;

	// missile
	private ArrayList<Missile> miss;
	private int missCode;

	private GameManager() {
		plants = new ArrayList<Plant>();
		players = new ArrayList<Player>();
		miss = new ArrayList<Missile>();
		numOfPlayer = 0;
		missCode = 0;
		gameCode = 0;
		winner = "����";
	}

	public static GameManager getInstance() {
		return manager;
	}

	public void gameStart() {
		gameCode++;
		plants = new ArrayList<Plant>();
		players = new ArrayList<Player>();
		miss = new ArrayList<Missile>();
		numOfPlayer = 0;
		missCode = 0;
		creatPlant(NUM_OF_PLANT);
		System.out.println(gameCode + "st, game start!!");
	}

	public void creatPlant(int num) {
		int x = MAP_SIZE_X / 2;
		int y = MAP_SIZE_X / 2;

		// �׼��� �����.
		Plant sun = new Plant();
		sun.setId("plant0");
		sun.setOwn("");
		sun.setParent(null);
		sun.setR(64);
		sun.setRevAngle(0);
		sun.setRevR(0);
		sun.setRevSpd(0);
		sun.setType("sun");
		sun.setX(x);
		sun.setY(y);
		plants.add(sun); // 0���� sun�̴�.

		// �׼��� �������� num ���� �׼��� �����.
		for (int i = 1; i <= NUM_OF_PLANT; i++) {
			// �׼� ����
			Plant p = new Plant();
			p.setId("plant" + i);
			p.setOwn("");
			p.setParent(sun);
			p.setR(15);
			p.setRevAngle(((double) new Random().nextInt(100)) / 20);
			p.setRevR(new Random().nextInt(x - 500) + 300);
			p.setRevSpd((double) (new Random().nextInt(50) + 100) / 10000);
			p.setType("type" + (new Random().nextInt(13) + 1));
			p.setX(0);
			p.setY(0);
			plants.add(p);
			// ���� ����
			Plant s = new Plant();
			s.setId("plant" + (i * 1000));
			s.setOwn("empty");
			s.setParent(p);
			s.setR(10);
			s.setRevAngle(((double) new Random().nextInt(100)) / 20);
			s.setRevR(new Random().nextInt(70) + 100);
			s.setRevSpd((double) (new Random().nextInt(50) + 100) / 2500);
			s.setType("satellite");
			s.setX(0);
			s.setY(0);
			plants.add(s);
		}
	}

	public void revolutionPlant() {
		// SUN�� �������� �����Ƿ�
		for (int i = 1; i < plants.size(); i++) {
			Plant p = plants.get(i);

			p.setRevAngle(p.getRevAngle() + p.getRevSpd());

			p.setX(p.getParent().getX()
					+ (int) (p.getRevR() * Math.cos(p.getRevAngle())));
			p.setY(p.getParent().getY()
					+ (int) (p.getRevR() * Math.sin(p.getRevAngle())));
		}
	}

	public ArrayList<Plant> getPlants() {
		return plants;
	}

	public Player getEmptyPlant(String session, String id) {
		// �켱 ���ǿ� �� ������ �ִ��� �˻��ϰ�
		for (int i = 0; i < players.size(); i++) {
			if (players.get(i).getSession().equals(session))
				return players.get(i);
		}

		// ���ٸ� ��ȿ�� ���� ������ �÷��̾� ������ ��ȯ
		if (leavePlant() > 0) {
			numOfPlayer++;
			StringBuilder color = new StringBuilder();
			color.append("#");
			color.append(colorSet[new Random().nextInt(colorSet.length)]);
			color.append(colorSet[new Random().nextInt(colorSet.length)]);
			color.append(colorSet[new Random().nextInt(colorSet.length)]);
			color.append(colorSet[new Random().nextInt(colorSet.length)]);
			color.append(colorSet[new Random().nextInt(colorSet.length)]);
			color.append(colorSet[new Random().nextInt(colorSet.length)]);
			Player u = new Player();
			u.setId(id);
			u.setSession(session);
			u.setColor(color.toString());

			for (int i = 1; i < plants.size(); i++) {
				Plant p = plants.get(i);

				if (p.getOwn().equals("empty")) {
					p.setOwn(id);
					p.setOwnColor(color.toString());
					u.setP(p);
					players.add(u);
					System.out.println("###" + session + " :: " + id
							+ " is entered.");
					return u;
				}
			}
		}
		System.out.println("### member is full.");
		return null;
	}

	// start - target ������ �޴´�
	public Missile creatMissile(Player player, double x1, double y1, double x2,
			double y2) {
		if (player == null)
			return null;
		if (!player.useMis())
			return null;
		double disX = x2 - x1;
		double disY = y2 - y1;
		double r = Math.sqrt(Math.pow(disX, 2) + Math.pow(disY, 2));

		Missile m = new Missile();
		m.setCode(missCode++);
		m.setSpdX(disX / r);
		m.setSpdY(disY / r);
		m.setId(player.getId());
		m.setX((int) x1);
		m.setY((int) y1);
		m.setRange(0);
		m.setColor(player.getColor());

		double deg = Math.atan(Math.abs(disY) / Math.abs(disX)) * 180 / Math.PI;
		if (disX >= 0 && disY >= 0) {
			// 1��и�, ���ν� : 1��и�
			deg = deg;
		} else if (disX < 0 && disY >= 0) {
			// 2��и�, ���ν� : 2��и�
			deg = 180 - deg;
		} else if (disX >= 0 && disY < 0) {
			// 3��и�, ���ν� : 4��и�
			deg = 360 - deg;
		} else {
			// 4��и�, ���ν� : 3��и�
			deg = 180 + deg;
		}
		m.setDeg(deg);

		miss.add(m);
		return m;
	}

	public void flightMissile() {
		for (int i = miss.size() - 1; i >= 0; i--) {
			Missile m = miss.get(i);
			if (m.isCrash())
				miss.remove(i);
			else {
				if (m.getId() != null && m.getId().length() > 0) {
					m.setX((int) (m.getSpdX() * Missile.MIS_SPD + (double) m.getX()));
					m.setY((int) (m.getSpdY() * Missile.MIS_SPD + (double) m.getY()));
					m.setRange(m.getRange() + 1);

					// �浹����!!
					// �༺, �̻��� ������ �˻��ϸ�
					// ������ �̻���, �༺�� �ε�ġ�� ����.
					// �浹�� crash�� true�� �Ǹ� ������������ ����Ʈ�� �����ش�.
					for (int j = 0; j < plants.size(); j++) {
						Plant p = plants.get(j);
						if (!m.isCrash() && p.getOwn() != m.getId()) {
							// �̻����� ���� �ε�ġ�� �ʾҰ�, �����ְ� ���� ������
							// �Ÿ����. �༺��ǥ-�༺������ �� �������� �Ǵ��Ѵ�.
							double r = Math.sqrt(Math.pow(m.getX() - p.getX(),
									2) + Math.pow(m.getY() - p.getY(), 2));
							if (p.getR() + 10 > r) {
								// �Ÿ� r�� ������ +3 ���� ������ ����!!
								m.setCrash(true);
								if (p.getType().equals("satellite")) {
									// ������ ������
									deletePlayer(p.getOwn(), m.getId());
									p.setOwn("destroyed");
									p.setOwnColor("#f00");
								}
								break;
							}
						}
					}
					for (int j = 0; j < miss.size(); j++) {
						Missile tm = miss.get(j);
						if (!m.isCrash() && !tm.isCrash()
								&& tm.getId() != m.getId()) {
							// �� �̻����� ���� �ε�ġ�� �ʾҰ�,
							// ���̻��ϵ� ���� �ε�ġ�� �ʾҰ�, �����ְ� ���� ������
							// �Ÿ����
							double r = Math.sqrt(Math.pow(m.getX() - tm.getX(),
									2) + Math.pow(m.getY() - tm.getY(), 2));
							if (10 > r) {
								// �̻����� �Ÿ� r�� 10 ���� ������ ����!!
								m.setCrash(true);
								tm.setCrash(true);
								break;
							}
						}
					}
				}
				if (m.getRange() > Missile.MIS_MAX_RAGE)
					miss.remove(i);
			}
		}
	}

	private void deletePlayer(String own, String misUser) {
		for (int i = players.size() - 1; i >= 0; i--) {
			// �ش�Ǵ� ������ �ִ°�� ��-��
			Player u = players.get(i);
			if (u.getId() == own) {
				players.remove(i);
				// ������ �����Ѱ��̹Ƿ� ����Ʈ�߰�
				addPoint(misUser);
				return;
			}
		}
	}

	private void addPoint(String id) {
		for (int i = 0; i < players.size(); i++) {
			Player u = players.get(i);
			if (u.getId() == id)
				u.addPoint();
		}
	}

	public ArrayList<Missile> getMiss() {
		return miss;
	}

	public ArrayList<Player> getPlayerList() {
		Collections.sort(players);
		return players;
	}

	public void addMissile() {
		for (int i = 0; i < players.size(); i++) {
			players.get(i).addMis();
		}
	}

	public boolean isEndGame() {
		System.out.println("whole member : " + numOfPlayer + "entered member" + players.size()
				+ "leaves member: " + leavePlant());
		if (numOfPlayer > 1 && players.size() == 1) {
			// ������ �÷��̾ 2�� �̻��̰�, �÷��̾ �ϳ� ���Ҵٸ� �¸�
			winner = players.get(0).getId();
			return true;
		}
		if (leavePlant() == 0 && players.size() <= 1) {
			winner = "���º�";
			return true;
		}
		return false;
	}

	public int getGameCode() {
		return gameCode;
	}

	public String getWinner() {
		return winner;
	}

	// ���� �༺�� ��ȸ
	public int leavePlant() {
		int num = 0;
		for (int i = 0; i < plants.size(); i++) {
			if (plants.get(i).getType().equals("satellite")
					&& plants.get(i).getOwn().equals("empty")) {
				num++;
			}
		}
		return num;
	}
}
