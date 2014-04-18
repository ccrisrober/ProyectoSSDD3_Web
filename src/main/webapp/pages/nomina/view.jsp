<%-- 
    Document   : update
    Created on : 23-mar-2014, 13:43:57
    Author     : Cristian
--%>
<%@taglib prefix="t" uri="http://java.sun.com/jsp/jstl/core" %>
                <!-- Main content -->
                <section class="content">
                    <t:choose>
                        <t:when test="${empty requestScope.nomina}">
                            ${requestScope.error}
                        </t:when>
                        <t:otherwise>
                            <t:if test="${not empty requestScope.error}">
                                <div class="alert alert-danger alert-dismissable">
                                    <i class="fa fa-ban"></i>
                                    <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                                    <b>Error!</b> ${requestScope.error}
                                </div>
                            </t:if>
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                            <h4 class="modal-title" id="myModalLabel">Insertar nómina</h4>
                        </div>
                        <div class="modal-body">
                            <div class="box-body">

                                <!-- Date mm/dd/yyyy -->
                                <div class="form-group">
                                    <label for="datefield">Fecha</label>
                                    <div class="input-group">
                                        <div class="input-group-addon">
                                            <i class="fa fa-calendar"></i>
                                        </div>
                                        <input type="text" id="date" name="datefield" class="form-control" placeholder="yyyy-mm-dd" value="${nomina.getFecha()}" data-inputmask="'alias': 'yyyy-mm-dd'" data-mask disabled/>
                                    </div><!-- /.input group -->
                                </div><!-- /.form group -->


                                <div class="form-group">
                                    <label for="salariofield">Salario</label>  <!-- Esto se puede autocompletar con ajax al rellenar el empleado -->
                                    <div class="input-group">
                                        <span class="input-group-addon"><i class="fa fa-dollar"></i></span>
                                        <input type="text" id="salario" name="salariofield" value="${nomina.getSalario()}" class="form-control" disabled>
                                    </div><!-- /.input group -->
                                </div><!-- /.form group -->

                                <div class="form-group">
                                    <label for="retencionfield">Retención</label>
                                    <div class="input-group">
                                        <span class="input-group-addon"><i class="fa fa-dollar"></i></span>
                                        <input type="text" id="retencion" name="retencionfield" value="${nomina.getRetencion()}" class="form-control" disabled>
                                    </div><!-- /.input group -->
                                </div><!-- /.form group -->

                            </div><!-- /.box-body -->
                        </div>
                        </t:otherwise>
                    </t:choose>

                </section><!-- /.content -->