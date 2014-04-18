<%@taglib prefix="t" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<t:if test="${templatepage.isCheckLogin()}">
    <t:if test="${empty sessionScope.user_active}">
        <t:redirect url="Login" />
    </t:if>
</t:if>
<jsp:include page="header.jsp" />
<jsp:include page="../pages/${templatepage.getFile()}" />
<jsp:include page="footer.jsp" />