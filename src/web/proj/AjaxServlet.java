package web.proj;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

/**
 * Servlet implementation class AjaxServlet
 */
public class AjaxServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private GameManager gm = GameManager.getInstance();

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		HttpSession ss = request.getSession();
		String action = (String) request.getParameter("action");
		Player player = (Player) ss.getAttribute(GameManager.A_PLAYER);

		String json = null;

		switch (action) {
		case "login":
			String playerId = request.getParameter("id");
			Player u = gm.getEmptyPlant(ss.getId(), playerId);
			if (u != null) {
				ss.setAttribute(GameManager.A_PLAYER_ID, u.getId());
				ss.setAttribute(GameManager.A_PLAYER, u);
			}
			json = new Gson().toJson(u);
			break;
		case "plant":
			json = new Gson().toJson(gm.getPlants());
			break;
		case "miss":
			json = new Gson().toJson(gm.getMiss());
			break;
		case "missAdd":
			json = new Gson().toJson(gm.creatMissile(player,
					Double.parseDouble(request.getParameter("x1")),
					Double.parseDouble(request.getParameter("y1")),
					Double.parseDouble(request.getParameter("x2")),
					Double.parseDouble(request.getParameter("y2"))));
			break;
		case "playerList":
			json = new Gson().toJson(gm.getPlayerList());
			break;
		case "playerDelete":
			ss.invalidate();
			// json엔 걍 암거나 넘겨요..
			json = new Gson().toJson(gm.getPlayerList());
			break;
		case "gameCode":
			// 게임코드 반환
			json = new Gson().toJson(gm.getGameCode());
			break;
		default:
			break;
		}
		if (json != null) {
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(json);
		}
	}
}
