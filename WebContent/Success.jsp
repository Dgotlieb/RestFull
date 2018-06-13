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
	Connection conn = null;

	try {
		String code = request.getParameter("code");
		String email = request.getParameter("email");
		String name = request.getParameter("name");
		String password = request.getParameter("password");

		MessageDigest md5 = MessageDigest.getInstance("MD5");
		md5.update(StandardCharsets.UTF_8.encode(email));
		String hashedLogin = String.format("%032x", new BigInteger(1, md5.digest()));

		if (code.equals(hashedLogin)) {

			List<User> list = DB_Connector.readTable();
			boolean isValidUser = true;

			for (int i = 0; i < list.size(); i++) {
				User tempUser = list.get(i);
				if (tempUser.getEmail().equals(email)) {
					isValidUser = false;
				}
				if (tempUser.getName().equals(name)) {
					isValidUser = false;
				}
			}

			if (isValidUser) {
				DB_Connector.insertNewUser(name, password, email);
			} else {
				out.println("Invalid credentials");
			}
		} else {
			out.println("Invalid credentials\nPlease make sure credentials matching registration");
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
%>