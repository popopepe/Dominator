package web.proj;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class IngameServlet
 */
public class IngameServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private GameManager gm = GameManager.getInstance();
	
	public IngameServlet() {
		gm.gameStart();
		new MovePlant().start();
		new MoveMissile().start();
		new reloadMissile().start();
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String page = "/jsp/ingame.jsp";

		request.setAttribute(GameManager.A_WINNER, gm.getWinner());
		request.setAttribute(GameManager.A_GAME_CODE, gm.getGameCode());
		request.setAttribute(GameManager.A_PLANTS, gm.getPlants());
		request.setAttribute(GameManager.A_MAP_SIZE_X, GameManager.MAP_SIZE_X);
		request.setAttribute(GameManager.A_MAP_SIZE_Y, GameManager.MAP_SIZE_Y);
		request.setAttribute(GameManager.A_MIS_RANGE, GameManager.MIS_RANGE);
		request.getRequestDispatcher(page).forward(request, response);
	}

	class MovePlant extends Thread {

		@Override
		public void run() {
			while (true) {
				try {
					// 0.2sec, represent plants.
					sleep(200);
				} catch (InterruptedException e) {
				}
				gm.revolutionPlant();
			}
		}
	}

	class MoveMissile extends Thread {

		@Override
		public void run() {
			while (true) {
				try {
					// 0.2sec, represent missile.
					sleep(200);
				} catch (InterruptedException e) {
				}
				gm.flightMissile();
			}
		}
	}

	class reloadMissile extends Thread {
		@Override
		public void run() {
			while (true) {
				try {
					// 5sec, reload missile.
					sleep(5000);
				} catch (InterruptedException e) {
				}
				gm.addMissile();

				// if game is end, restart it.
				if (gm.isEndGame()) {
					gm.gameStart();
				}
			}
		}
	}
}
