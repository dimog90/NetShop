<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="from" uri="http://www.springframework.org/tags/form" %>
<%@ page session="false" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>User page</title>

    <link href="webjars/bootstrap/4.3.1/css/bootstrap.min.css" rel="stylesheet">

</head>

<body>

<div class="container h-100">
    <div class="row h-100 justify-content-center align-items-center">
        <h3>Hello user ${user.username}!</h3>&nbsp
        <form action="/logout" method="post">
            <button style="width:100px;height:25px" type="submit">Log out</button>
        </form>
    </div>
</div>
<div class="container h-100">
    <div class="row h-100 justify-content-center align-items-center">
        <h3>Products</h3>
    </div>
</div>
<div class="container h-100">
    <div class="row h-100 justify-content-center align-items-center">
        <c:if test="${!empty productList}">
            <table style="" border="2">
                <tr>
                    <c:if test="${admin == true}">
                        <th>Id</th>
                    </c:if>
                    <th>Name product</th>
                    <th>Description</th>
                    <th>Price</th>
                    <th>Count</th>
                    <c:if test="${admin == false}">
                        <th>Buy</th>
                    </c:if>
                    <c:if test="${admin == true}">
                        <th>Remove</th>
                    </c:if>
                </tr>
                <tbody>
                <c:forEach items="${productList}" var="products">
                    <tr>
                        <c:if test="${admin == true}">
                            <td>${products.id}</td>
                        </c:if>
                        <td>${products.productname}</td>
                        <td>${products.description}</td>
                        <td>${products.price}</td>
                        <td>${products.count}</td>
                        <c:if test="${admin == false}">
                            <td><a href="<c:url value='/addToCart/${products.id}'/>">Add to cart</a></td>
                        </c:if>
                        <c:if test="${admin == true}">
                            <td><a href="<c:url value='/removeProduct/${products.id}'/>">Remove</a></td>
                        </c:if>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:if>
    </div>
</div>
<div class="container h-100">
    <div class="row h-100 justify-content-center align-items-center">
        <div id="pagination">
            <c:url value="/userPage" var="prev">
                <c:param name="page" value="${page-1}"/>
            </c:url>
            <c:if test="${page > 1}">
                <a href="<c:out value="${prev}"/>">Previous</a>
            </c:if>

            <c:forEach begin="1" end="${maxPages}" step="1" varStatus="i">
                <c:choose>
                    <c:when test="${page == i.index}">
                        <span>${i.index}</span>
                    </c:when>
                    <c:otherwise>
                        <c:url value="/userPage" var="url">
                            <c:param name="page" value="${i.index}"/>
                        </c:url>
                        <a href='<c:out value="${url}"/>'>${i.index}</a>
                    </c:otherwise>
                </c:choose>
            </c:forEach>

            <c:url value="/userPage" var="next">
                <c:param name="page" value="${page + 1}"/>
            </c:url>
            <c:if test="${page + 1 <= maxPages}">
                <a href='<c:out value="${next}" />'>Next</a>
            </c:if>
        </div>
    </div>
</div>
<div class="container h-100">
    <div class="row h-100 justify-content-center align-items-center">
        <c:if test="${admin == true}">
            Total cost: ${totalCostAdmin}
        </c:if>
    </div>
</div>
<div class="container h-100">
    <div class="row h-100 justify-content-center align-items-center">
        <h4>Filters:</h4>
    </div>
</div>
<div class="container h-100">
    <div class="row h-100 justify-content-center align-items-center">
        <form action="/userPage">
            <input type="text" placeholder="Name product" name="param"/>
            <input type="submit" value="Search"/>
        </form>
        &nbsp
        <form action="/userPage">
            <button name="param" type="submit" value="all">All products</button>
            <button name="param" type="submit" value="asc">ASC</button>
            <button name="param" type="submit" value="desc">DESC</button>
        </form>
    </div>
</div>
<c:if test="${admin == true}">
    <div class="container h-100">
        <div class="row h-100 justify-content-center align-items-center">
            <h4>Edit:</h4>
        </div>
    </div>
    <div class="container h-100">
        <div class="row h-100 justify-content-center align-items-center">
            <form method="post" action="/update">
                <input type="number" name="id" placeholder="id*(necessarily)"/>
                <input type="text" name="productname" placeholder="Product name"/>
                <input type="text" name="description" placeholder="Description"/>
                <input type="number" step="any" name="price" placeholder="Price"/>
                <input type="number" name="count" placeholder="Count"/>
                <button type="submit">Update product</button>
            </form>
        </div>
    </div>
    <br>
    <div class="container h-100">
        <div class="row h-100 justify-content-center align-items-center">
            <form method="post" action="/add">
                <input type="text" name="productname" placeholder="Product name"/>
                <input type="text" name="description" placeholder="Description"/>
                <input type="number" name="price" placeholder="Price"/>
                <input type="number" step="any" name="count" placeholder="Count"/>
                <button type="submit">Add product</button>
            </form>
        </div>
    </div>
</c:if>
<c:if test="${admin == false}">
    <div class="container h-100">
        <div class="row h-100 justify-content-center align-items-center">
            <h3>Cart</h3>
        </div>
    </div>
    <div class="container h-100">
        <div class="row h-100 justify-content-center align-items-center">
            <div>
                <c:if test="${!empty cart}">
                    <table border="1">
                        <tr>
                            <th>Name product</th>
                            <th>Description</th>
                            <th>Price</th>
                            <th>Remove</th>
                        </tr>
                        <tbody>
                        <c:forEach items="${cart}" var="cart">
                            <tr>
                                <td>${cart.productname}</td>
                                <td>${cart.description}</td>
                                <td>${cart.price}</td>
                                <td><a href="<c:url value='/removeFromCart/${cart.id}'/>">Remove from cart</a></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </c:if>
            </div>
        </div>
    </div>
    <br>
    <div class="container h-100">
        <div class="row h-100 justify-content-center align-items-center">
            Total cost: ${totalCost}&nbsp
            <form action="/buyCart" method="post">
                <button style="width:100px;height:25px" type="submit">Buy</button>
            </form>
        </div>
    </div>
</c:if>


<script src="webjars/jquery/3.3.1/dist/jquery.min.js"></script>
<script src="webjars/bootstrap/4.3.1/js/bootstrap.min.js"></script>
</body>
</html>