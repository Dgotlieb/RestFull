<%@ page language="java" contentType="text/html; charset=windows-1255"
    pageEncoding="windows-1255"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1255">
<title>Email validation</title>
</head>
    <body>
        <form method="post" action="Success.jsp">
            <center>
            <table border="1" cellpadding="5" cellspacing="2">
                <thead>
                    <tr>
                        <th colspan="2">Enter the validation code that was sent to your email</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>Email</td>
                        <td><input type="text" name="email" required/></td>
                    </tr>
                     <tr>
                        <td>User name</td>
                        <td><input type="text" name="name" required/></td>
                    </tr>   
                    <tr>
                        <td>Code</td>
                        <td><input type="text" name="code" required/></td>
                    </tr>                   
                     <tr>
                        <td>Password</td>
                        <td><input type="password" name="password" required/></td>
                    </tr>
                    <tr>
                        <td colspan="2" align="center"><input type="submit" value="Confirm" />
                            &nbsp;&nbsp;
                            <input type="reset" value="Reset" />
                        </td>                        
                    </tr>                    
                </tbody>
            </table>
            </center>
        </form>
    </body>
</html>