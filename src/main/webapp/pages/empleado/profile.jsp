<%-- 
    Document   : profile
    Created on : 15-mar-2014, 13:09:01
    Author     : Cristian
--%>
<%@taglib prefix="t" uri="http://java.sun.com/jsp/jstl/core" %>

                <!-- Main content -->
                <section class="content">

                    <div class="box">
<t:choose>
    <t:when test="${not empty requestScope.error}">
                        <div class="box-header">
                            <h3 class="box-title">Error</h3>                                    
                        </div><!-- /.box-header -->
                        <div class="box-body">   
                            <p>${error}</p>
                        </div>
    </t:when>
    <t:when test="${not empty requestScope.empleado}">
                        <div class="box-header">
                            <h3 class="box-title">Datos del empleado ${empleado.getName()} ${empleado.getSurname()}</h3>                                    
                        </div><!-- /.box-header -->
                        <div class="box-body">    

                            <input type="hidden" class="form-control" id="idfield" name="idfield" value="${empleado.getId()}">
                            
                            <div class="form-group">
                                <label for="userfield">Dni</label>
                                <input type="text" class="form-control" id="dnifield_" name="dnifield_" value="${empleado.getDni()}" disabled />
                            </div>
                            
                            <div class="form-group">
                                <label for="userfield">Usuario</label>
                                <input type="text" class="form-control" id="userfield" name="userfield" value="${empleado.getUsername()}" disabled />
                            </div>

                            <div class="form-group">
                                <label for="namefield">Nombre</label>
                                <input type="text" class="form-control" id="namefield" name="namefield" value="${empleado.getName()}" disabled />
                            </div>

                            <div class="form-group">
                                <label for="surnamefield">Apellidos</label>
                                <input type="text" class="form-control" id="surnamefield" name="surnamefield" value="${empleado.getSurname()}" disabled />
                            </div>

                            <div class="form-group">
                                <label for="departamentfield">Departamento</label>
                                <input type="text" class="form-control" id="departamentfield" name="departamentfield" value="${empleado.getDepartamento()}" disabled />
                            </div>

                            <div class="form-group">
                                <label for="sucursalfield">Sucursal</label>
                                <input type="text" class="form-control" id="sucursalfield" name="sucursalfield" value="${empleado.getSucursal()}" disabled />
                            </div>

                        </div><!-- /.box-body -->
                    </div><!-- /.box -->


                    <div class="box">
                        <div class="box-header">
                            <h3 class="box-title">N髆inas</h3>                                    
                        </div><!-- /.box-header -->
                        <div class="box-body table-responsive">
                            <table id="nominas" class="table table-bordered table-striped">
                                <thead>
                                    <tr>
                                        <th style="display: none">ID</th>
                                        <th>Fecha</th>
                                        <th>Sueldo</th>
                                        <th>Porcentaje de retenci髇</th>
                                        <th>Modificar</th>
                                        <td>Borrar</td>
                                    </tr>
                                </thead>
                                <tbody>
                                <t:forEach items="${nominas}" var="item">
                                    <tr>
                                        <td style="display: none">${item.getId_nomina()}</td>
                                        <td><a href="NominaController?action=show&id_nom=${item.getId_nomina()}&dnifield=${empleado.getDni()}">${item.getFecha()}</a></td>
                                        <td>${item.getSalario()}</td>
                                        <td>${item.getRetencion()}</td>
                                        <td><a class="editar" href="NominaController?action=edit&id_emp=${item.getId_empleado()}&id_nom=${item.getId_nomina()}">Editar</a></td>
                                        <td><a class="borrar" href="NominaController?action=delete&id_emp=${item.getId_empleado()}&id_nom=${item.getId_nomina()}">Borrar</a></td>
                                    </tr>
                                </t:forEach>
                                </tbody>
                                <tfoot>
                                    <tr>
                                        <th style="display: none">ID</th>
                                        <th>Fecha</th>
                                        <th>Sueldo</th>
                                        <th>Porcentaje de retenci髇</th>
                                        <th>Modificar</th>
                                        <td>Borrar</td>
                                    </tr>
                                </tfoot>
                            </table>
                            
                        </div><!-- /.box-body -->
                    </div><!-- /.box -->
                    
                    
                    <div class="box">
                        <div class="box-header">
                            <h3 class="box-title">Otros datos</h3>                                    
                        </div><!-- /.box-header -->
                        <div class="box-body">        
                            <div class="form-group">
                                <label for="salariomedio">Salario medio</label>
                                <input type="text" class="form-control" id="salariomedio" name="salariomedio" value="${salariomedio} $" disabled />
                            </div>
                            
                            <div class="form-group">
                                <label for="retencionmedia">Retenci髇 media</label>
                                <input type="text" class="form-control" id="retencionmedia" name="retencionmedia" value="${retencionmedia} %" disabled />
                            </div>
                        </div><!-- /.box-body -->
                    </div><!-- /.box -->

                    <!-- Button trigger modal -->
                    <a class="btn btn-primary btn-lg" href="NominaController?action=create&id_emp=${empleado.getId()}"> Create New N贸mina </a>
                    
                    <a class="btn btn-warning btn-lg" target="_blank" href="NominaController?action=imprimir&id_emp=${empleado.getId()}"> Impresi贸n </a>
                   
                    <a class="btn btn-danger btn-lg" target="_blank" href="NominaController?action=imprimirHistorial&dni=${empleado.getDni()}"> Impresi贸n hist贸rico</a>
                   
    </t:when>
</t:choose>
                    </div>

                </section><!-- /.content -->
                