<%--suppress HtmlFormInputWithoutLabel --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form name="regform" method="post" accept-charset="UTF-8" >
    e-mail<input type="text" name="email"> <br>
    password<input type="text" name="password"> <br>
    Имя<input type="text" name="firstname"> <br>
    Фамилия<input  type="text" name="lastname"> <br>
    Ник<input  type="text" name="nickname"> <br>
    Дата рождения<input  type="date" name="dob"> <br>
    <input list="<PageType>">
    <datalist id="<PageType>">
        <option value="PERSON">
        <option value="TEAM">
    </datalist>
    <input type="submit">
</form>


</body>
</html>
