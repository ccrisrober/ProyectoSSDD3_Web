<%-- 
    Document   : list
    Created on : 15-mar-2014, 21:47:26
    Author     : Cristian
--%>
<%@taglib prefix="t" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
                <!-- Main content -->
                <section class="content">

                    <div class="box">
                        <div class="box-header">
                            <h3 class="box-title">Lista nóminas</h3>                                    
                        </div><!-- /.box-header -->
                        <div class="box-body table-responsive">
                            <table id="tableempleados" class="table table-bordered table-striped">
                                <thead>
                                    <tr>
                                        <th style="display: none"></th>
                                        <th>Mes</th>
                                        <th>Año</th>
                                        <th>Sueldo</th>
                                        <th>Porcentaje retención</th>
                                        <th>Sucursal</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <t:forEach items="${nominas}" var="item">
                                        <tr>
                                            <td style="display: none">${item.getId()}</td>
                                            <td><a href="NominaController?action=show&id_emp=${item.getIdEmp()}&id_nom=${item.getIdNom()}">Nómina</a></td>
                                            <t:set var="dateParts" value="${fn:split(item.getFecha(), '/')}" />
                                            <td>${dateParts[2]}</td>
                                            <td>${dateParts[1]}</td>
                                            <td>${item.getSalario()}</td>
                                            <td>${item.getRetencion()}</td>
                                        </tr>
                                    </t:forEach>
                                </tbody>
                                <tfoot>
                                    <tr>
                                        <th style="display: none"></th>
                                        <th>Mes</th>
                                        <th>Año</th>
                                        <th>Sueldo</th>
                                        <th>Porcentaje retención</th>
                                        <th>Sucursal</th>
                                    </tr>
                                </tfoot>
                            </table>
                           
                        </div><!-- /.box-body -->
                    </div><!-- /.box -->

                </section><!-- /.con