<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Cadastro de produtos</title>
</head>
<body>
	<script>
		$("#releaseDate").datepicker({ dateFormat: 'dd/MM/yyyy'});
	</script>
	<form:form action="${spring:mvcUrl('PC#save').build()}" method="post" commandName="product" enctype="multipart/form-data">
		<div>
			<label for="title">Titulo</label> 
			<form:input path="title" id="title" />
			<form:errors path="title" />
		</div>
		<div>
			<label for="description">Descricao</label>
			<form:textarea path="description" rows="10" cols="20" id="description" />
			<form:errors path="description" />
		</div>
		<div>
			<label for="numberOfPages">Numero de paginas</label> 
			<form:input path="numberOfPages" id="numberOfPages" />
			<form:errors path="numberOfPages" />
		</div>
		<div>
			<label for="numberOfPages">Data Lancamento</label> 
			<form:input path="releaseDate" id="releaseDate" type="text"/>
			<form:errors path="releaseDate" />
		</div>
		<div>
		<c:forEach items="${types}" var="bookType" varStatus="status">
			<label for="price_${bookType}">${bookType}</label>
			<input type="text" name="prices[${status.index}].value" id="price_${bookType}" />
			<input type="hidden" name="prices[${status.index}].bookType" value="${bookType}" />
			<br>
		</c:forEach>
		</div>
		<div>
			<label for="summary">Sumario do Livro</label>
			<input type="file" name="summary" id="summary" />
			<form:errors path="summaryPath" />
		</div>
		<div>
			<input type="submit" value="Enviar">
		</div>
	</form:form>

</body>
</html>