package uni.fmi.masters;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class HelloServlet
 */
@WebServlet("/HelloServlet")
public class HelloServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	UserBean user = null;
	
	ArrayList<StatusBean> statuses = new ArrayList<>();
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HelloServlet() {
        
        this.statuses.add(new StatusBean(30, "Варна", "Време за море!"));
        statuses.add(new StatusBean(-68, "Аляска", "Малко захладня!"));
        statuses.add(new StatusBean(60, "Пловдив", "Айляк майна!"));
        
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String action = request.getParameter("action");
		
		switch(action) {
		case "login":
			
			login(request, response);
			
			break;
			
		case "register":
			
			register(request, response);
			
			break;
			
		case "home":
			goHome(request, response);
			
			break;
			
		case "addComment":
			String city = request.getParameter("city");
			double temp = 
					Double.parseDouble(request.getParameter("temp"));
			String comment = request.getParameter("comment");
			
			statuses.add(new StatusBean(temp, city, comment));
			
			response.getWriter().append("completed");
		
			break;
			default:
				
				
		}
		
	
	}

	private void goHome(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("statuses", statuses);
		
		request.getRequestDispatcher("home.jsp")
			.forward(request, response);
	}

	private void register(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String regUsername = request.getParameter("register-user");
		String regPassword = request.getParameter("register-pass");
		String repeatPassword = 
				request.getParameter("confirm-register-pass");
		String email = request.getParameter("register-email");
		
		if(regUsername.length() == 0 || regPassword.length() == 0 
				|| 
				!regPassword.equals(repeatPassword)) {
			
			request.setAttribute("message", "wrong wrong wrong...");
			
			request.getRequestDispatcher("error.jsp")
				.forward(request, response);
			
			return;
		}
		
		user = new UserBean(regUsername, regPassword, email);
		
		request.getRequestDispatcher("index.html")
			.forward(request, response);
	}

	private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(user == null)
			return;
		
		String username = request.getParameter("usernamesdsd");
		String password = request.getParameter("password");
		
		if(username.equalsIgnoreCase(user.getUsername()) 
				&& password.equals(user.getPassword())) {
			
			request.setAttribute("loggedUser", user);
			request.getRequestDispatcher("profile.jsp")
				.forward(request, response);				
			
		}else {
			request.getRequestDispatcher("error.html")
				.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doDelete(req, resp);
	}
}
