<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>SAE - Intégration - Liste des tests</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="css/style.css" rel="stylesheet" type="text/css" />
</head>
<body>

<p class="titre1">SAE - Intégration - Liste des tests</p>
<p><br /></p>

<c:forEach var="categorie" items="${listeTests.categorie}">
   
   <p class="titre2"><c:out value="${categorie.nom}" /></p>
   
   <table border=0 cellpadding=3>
   
   <c:forEach var="casTest" items="${categorie.casTests.casTest}">
      <tr>
         <td>
            <c:choose>
            
	            <c:when test="${casTest.implemente==true}">
	               <a class="lienCas" href="test<c:out value="${casTest.id}" />.do">
		               <c:out value="${casTest.code}" />
		            </a>
	            </c:when>
            
               <c:otherwise>
                  <c:out value="${casTest.code}" />
               </c:otherwise>
            
            </c:choose>
            
         </td>
      </tr>
   </c:forEach>
   
   </table>
   
   
   <p><br /></p>

</c:forEach>



<p><br /><br /><br /><br /></p>
</body>
</html>
