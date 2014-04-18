<%-- 
    Document   : insertar
    Created on : 13-mar-2014, 16:07:47
    Author     : Cristian
--%>
<%@taglib prefix="t" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- Main content -->
<section class="content">
    <t:choose>
        <t:when test="${not empty ok}">
            <div class="alert alert-success alert-dismissable">
                <i class="fa fa-check"></i>
                <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                <b>Alert!</b> ${ok}
            </div>
            <p>Actualiza  <a href=EmpleadoController?action=create>aquí</a> para insertar un nuevo empleado.</p>
            <p>Puedes ver todos los empleados <a href=EmpleadoController?action=list>aquí</a></p>
        </t:when>
        <t:otherwise>
            <t:if test="${not empty error}">
                <div class="alert alert-danger alert-dismissable">
                    <i class="fa fa-ban"></i>
                    <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                    <b>Error!</b> ${error}
                </div>
            </t:if>
            <form role="form" method="post" action=EmpleadoController id="regForm">
                <div class="box-body">
                    <input type="hidden" name="action" id="action" value="create" />
                    <input type="hidden" name="ac" id="ac" value="${asociatedchar}" />
                    <input type="hidden" name="ai" id="ai" value="${asociatedpos}" />

                    <div class="form-group">
                        <label for="userfield">Usuario</label>
                        <input type="text" class="form-control" id="userfield" name="userfield" placeholder="Enter ..."/>
                    </div>

                    <div class="form-group">
                        <label for="passwordfield">Password</label>
                        <input type="password" class="form-control" id="passwordfield" name="passwordfield" placeholder="Password">
                    </div>

                    <div class="form-group">
                        <label for="namefield">Nombre</label>
                        <input type="text" class="form-control" id="namefield" name="namefield" placeholder="Enter ..."/>
                    </div>

                    <div class="form-group">
                        <label for="surnamefield">Apellidos</label>
                        <input type="text" class="form-control" id="surnamefield" name="surnamefield" placeholder="Enter ..."/>
                    </div>

                    <div class="form-group">
                        <label for="dnifield">DNI</label>
                        <input type="text" class="form-control" id="dnifield" name="dnifield" placeholder="Enter ..."/>
                    </div>

                    <div class="form-group">
                        <label for="departamentofield">Departamento</label>
                        <input type="text" class="form-control" id="departamentofield" name="departamentofield" placeholder="Enter ..."/>
                    </div>

                    <div class="form-group">
                        <label for="sucursalfield">Sucursal</label>
                        <select class="form-control" id="sucursalfield" name="sucursalfield">
                            <option value="" selected>Elegir</option>
                            <option value="Soria">Soria</option>
                            <option value="Teruel">Teruel</option>
                        </select>
                    </div>

                </div><!-- /.box-body -->

                <div class="box-footer">
                    <button type="submit" class="btn btn-primary">Submit</button>
                </div>

            </form>   


        </t:otherwise>
    </t:choose>

</section><!-- /.content -->