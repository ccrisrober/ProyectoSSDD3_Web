<%-- 
    Document   : update
    Created on : 23-mar-2014, 13:43:57
    Author     : Cristian
--%>
<%@taglib prefix="t" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- Main content -->
<section class="content">
    <t:choose>
        <t:when test="${not empty requestScope.ok}">
            <div class="alert alert-success alert-dismissable">
                <i class="fa fa-check"></i>
                <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                <b>Alert!</b> ${requestScope.ok}
            </div>
            <p>Pincha <a href="EmpleadoController?action=profile&id=${id_empleado}">aquí</a> para volver atrás.</p>
        </t:when>
        <t:otherwise>
            <t:if test="${not empty requestScope.error}">
                <div class="alert alert-danger alert-dismissable">
                    <i class="fa fa-ban"></i>
                    <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                    <b>Error!</b> ${requestScope.error}
                </div>
            </t:if>
            <form role="form" method="post" id="newNomina" action="NominaController">
                <input type="hidden" name="action" id="action" value="update" />
                <div class="box-body">

                    <input type="hidden" name="id_empfield" value="${id_empleado}"/>
                    <input type="hidden" name="id_nomfield" value="${id_nomina}"/>

                    <!-- Date mm/dd/yyyy -->
                    <div class="form-group">
                        <label for="datefield">Fecha</label>
                        <div class="input-group">
                            <div class="input-group-addon">
                                <i class="fa fa-calendar"></i>
                            </div>
                            <input type="text" id="date" name="datefield" class="form-control" placeholder="yyyy-mm-dd" value="${date}" data-inputmask="'alias': 'yyyy-mm-dd'" data-mask/>
                        </div><!-- /.input group -->
                    </div><!-- /.form group -->


                    <div class="form-group">
                        <label for="salariofield">Salario</label>  <!-- Esto se puede autocompletar con ajax al rellenar el empleado -->
                        <div class="input-group">
                            <span class="input-group-addon"><i class="fa fa-dollar"></i></span>
                            <input type="text" id="salario" name="salariofield" value="${salario}" class="form-control">
                        </div><!-- /.input group -->
                    </div><!-- /.form group -->

                    <div class="form-group">
                        <label for="retencionfield">Retención</label>
                        <div class="input-group">
                            <span class="input-group-addon"><i class="fa fa-dollar"></i></span>
                            <input type="text" id="retencion" name="retencionfield" value="${retencion}" class="form-control">
                        </div><!-- /.input group -->
                    </div><!-- /.form group -->

                </div><!-- /.box-body -->
                <div class="box-footer">
                    <button type="submit" class="btn btn-primary">Submit</button>
                </div>
            </form>
        </t:otherwise>
    </t:choose>

</section><!-- /.content -->