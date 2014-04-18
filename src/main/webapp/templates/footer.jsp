<%@taglib prefix="t" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
            </aside><!-- /.right-side -->
        </div><!-- ./wrapper -->

        <!-- jQuery 2.0.2 -->
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/2.0.2/jquery.min.js"></script>
        <!-- Bootstrap -->
        <script src="assets/js/bootstrap.min.js" type="text/javascript"></script>
        <!-- AdminLTE App -->
        <script src="assets/js/AdminLTE/app.js" type="text/javascript"></script>

        <t:forEach items="${templatepage.getFooterInclude()}" var="item">
        <t:set var="file" value="${item}"/>

        <t:if test="${fn:contains(file, 'js')}">
           <script src="${item}" type="text/javascript"></script>
        </t:if>
                   
        </t:forEach> 
           
        <t:if test="${not empty templatepage.getScripts()}">
            <script>${templatepage.getScripts()}</script>
        </t:if>
    </body>
</html>