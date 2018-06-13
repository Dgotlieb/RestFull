<%@page import="main.ContactSender"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.regex.Pattern"%>
<%@ page import="main.User"%>
<%@ page import="main.DB_Connector"%>
<%@ page import="java.security.MessageDigest"%>
<%@ page import="java.nio.charset.StandardCharsets"%>
<%@ page import="java.math.BigInteger"%>

<% 
	
try {
		String name = request.getParameter("username");
		String password = request.getParameter("password");
		String email = request.getParameter("email");

		String issue = "";
		List<String> errors = new ArrayList<>();
		boolean isValidUser = true;

		if (!email.contains("@") || !email.contains(".")) {
			isValidUser = false;
		}

		if (!isValid(password, errors)) {
			String tempError = "";
			for (int i = 0; i < errors.size(); i++) {
				tempError += "\n" + errors.get(i);
			}
			out.println("Invalid password " + tempError);
			isValidUser = false;
		}

		if (isValidUser) {
			List<User> list = DB_Connector.readTable();

			for (int i = 0; i < list.size(); i++) {
				User tempUser = list.get(i);
				if (tempUser.getEmail().equals(email)) {
					isValidUser = false;
					issue = "Email already exist";
				}
				if (tempUser.getName().equals(name)) {
					isValidUser = false;
					issue += " User name already exist";
				}
			}
		}		
		response.sendRedirect("/Rest_Full/confirmation.jsp");		

		if (isValidUser) {			
				MessageDigest md5 = MessageDigest.getInstance("MD5");
				md5.update(StandardCharsets.UTF_8.encode(email));			
				String hashedLogin = String.format("%032x", new BigInteger(1, md5.digest()));
				
				ContactSender sender = new ContactSender("a@a.com","Aa123456");
				sender.sendMail("Registration success", "Validation code: "+hashedLogin, "a@a.com", email);				
			
		} else {
			out.println("Invalid login "+issue);
		}
	} catch (Exception e) {
		out.println(e.getMessage());
	}
%>

<%!public static boolean isValid(String passwordhere, List<String> errorList) {

		Pattern specailCharPatten = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
		Pattern UpperCasePatten = Pattern.compile("[A-Z ]");
		Pattern lowerCasePatten = Pattern.compile("[a-z ]");
		Pattern digitCasePatten = Pattern.compile("[0-9 ]");
		errorList.clear();

		boolean flag = true;

		if (passwordhere.length() < 8) {
			errorList.add("Password lenght must have alleast 8 character !!");
			flag = false;
		}
		if (!specailCharPatten.matcher(passwordhere).find()) {
			errorList.add("Password must have atleast one specail character !!");
			flag = false;
		}
		if (!UpperCasePatten.matcher(passwordhere).find()) {
			errorList.add("Password must have atleast one uppercase character !!");
			flag = false;
		}
		if (!lowerCasePatten.matcher(passwordhere).find()) {
			errorList.add("Password must have atleast one lowercase character !!");
			flag = false;
		}
		if (!digitCasePatten.matcher(passwordhere).find()) {
			errorList.add("Password must have atleast one digit character !!");
			flag = false;
		}

		return flag;
	}%>

