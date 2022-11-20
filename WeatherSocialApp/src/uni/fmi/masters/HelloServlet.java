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
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import uni.fmi.masters.entity.RequestEntity;
import uni.fmi.masters.entity.StatusEntity;
import uni.fmi.masters.entity.UserEntity;
import uni.fmi.masters.repo.JPARequestRepository;
import uni.fmi.masters.repo.JPAStatusRepository;
import uni.fmi.masters.repo.JPAUserRepository;

/**
 * Servlet implementation class HelloServlet
 */
@WebServlet("/HelloServlet")
public class HelloServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 
	JPAUserRepository userRepo = new JPAUserRepository();
	JPAStatusRepository statusRepo = new JPAStatusRepository();
	JPARequestRepository requestRepo = new JPARequestRepository();
	
	UserEntity user = null;	

	ArrayList<RequestEntity> requests = 
			new ArrayList<RequestEntity>();
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HelloServlet() {
        
    	
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
			
			StatusEntity status = new StatusEntity();
			
			status.setCity(city);
			status.setTemp(temp);
			status.setDescription(comment);
			status.setUser(user);
			
			statusRepo.insert(status);
					
			response.getWriter().append("completed");
		
			break;
			
		case "friends":
			goFriends(request, response);			
			
			break;
			
		case "search":
			String filter = request.getParameter("search-name");
			
			List<UserEntity> searchResult 
				= userRepo.searchByUsername(filter);
			
			request.setAttribute("searchResult", searchResult);
			
			goFriends(request, response);
			
			
			break;
		case "sendRequest":
			
			int id = Integer.parseInt(request.getParameter("userId"));
			
			RequestEntity newNotification = new RequestEntity();
			
			newNotification.setType("friendRequest");
			newNotification.setMessage("Add me pls");
			newNotification.setFromUser(user);
			newNotification.setToUser(userRepo.getById(id));
			
			JSONObject json = new JSONObject();
			
			if(requestRepo.insert(newNotification)) {				
				json.put("url", "inbox.jsp");
			}else {
				json.put("url", "error.jsp");
			}
			
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(json.toString());
			
			break;
			
		case "inbox":
			goToInbox(request,response);
			default:
				
				
		}
		
	
	}

	private void goToInbox(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("inbox.jsp")
			.forward(request, response);
		
	}

	private void goFriends(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("userFriends", requests);
		
		request.getRequestDispatcher("friends.jsp")
			.forward(request, response);
	}

	private void goHome(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("statuses", statusRepo.getAll());
		 
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
		
		user = userRepo.findUserByUsernameAndPassword(username, password);
				
		if(user != null) {
			
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
