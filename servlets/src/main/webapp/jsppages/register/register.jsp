<%--suppress HtmlFormInputWithoutLabel --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form name="regform" method="post" accept-charset="UTF-8" >
    e-mail<input type="text" name="reg_email"> <br>
    password<input type="text" name="reg_password"> <br>
    Имя<input type="text" name="firstname"> <br>
    Фамилия<input  type="text" name="lastname"> <br>
    Ник<input  type="text" name="nickname"> <br>
    Дата рождения<input  type="date" name="dob"> <br>
    <input type="submit">

</form>


<a href="team_register">Team register</a>

</body>
</html>
