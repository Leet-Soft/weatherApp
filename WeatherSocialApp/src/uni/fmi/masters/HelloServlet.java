package uni.fmi.masters;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uni.fmi.masters.entity.FriendsRequestsBean;
import uni.fmi.masters.entity.StatusBean;
import uni.fmi.masters.entity.UserEntity;
import uni.fmi.masters.repo.JPAUserRepository;

/**
 * Servlet implementation class HelloServlet
 */
@WebServlet("/HelloServlet")
public class HelloServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 
	JPAUserRepository userRepo = new JPAUserRepository();
    
	UserEntity user = null;
	
	ArrayList<StatusBean> statuses = new ArrayList<>();
	ArrayList<FriendsRequestsBean> requests = 
			new ArrayList<FriendsRequestsBean>();
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HelloServlet() {
        
    	
    }
    
    private void startingData() {
        this.statuses.add(new StatusBean(30, "Варна", "Време за море!"));
        statuses.add(new StatusBean(-68, "Аляска", "Малко захладня!"));
        statuses.add(new StatusBean(60, "Пловдив", "Айляк майна!"));
        
        UserEntity friend1 = 
        		new UserEntity("goshko", "hubaveca@abv.bg");
        UserEntity friend2 = 
        		new UserEntity("mariika", "sramejlivata@abv.bg");
        
        requests.add(new FriendsRequestsBean(friend1, user, "friends", true));
        
        requests.add(new FriendsRequestsBean(friend2, user, "friends", true));
              
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String action = request.getParameter("action");
		
		switch(action) {
		case "login":
			
			try {
				login(request, response);
			} catch (ServletException | IOException | SQLException e1) {
				e1.printStackTrace();
			}
			
			break;
			
		case "register":
			
			try {
				register(request, response);
			} catch (ServletException | IOException | SQLException e) {
				
				e.printStackTrace();
			}
			
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
			
		case "friends":
			goFriends(request, response);			
			
			break;
			default:
				
				
		}
		
	
	}

	private void goFriends(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("userFriends", requests);
		
		request.getRequestDispatcher("friends.jsp")
			.forward(request, response);
	}

	private void goHome(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("statuses", statuses);
		
		request.getRequestDispatcher("home.jsp")
			.forward(request, response);
	}

	private void register(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {
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
		
		user = new UserEntity(regUsername, regPassword, email);
		
		startingData();
				
		
		if(userRepo.createUser(user)) {
			request.getRequestDispatcher("index.html")
			.forward(request, response);
		}else {
			
			request.setAttribute("message", "Register not successfull");
			
			request.getRequestDispatcher("error.jsp")
			.forward(request, response);
		}
		

	}	

	private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
			
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		if(userRepo.findUserByUsernameAndPassword(username, password) != null) {
			
			request.setAttribute("loggedUser", user);
			request.getRequestDispatcher("profile.jsp")
				.forward(request, response);				
			
		}else {
			request.setAttribute("message", "Wrong username/password");
			request.getRequestDispatcher("error.jsp")
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
