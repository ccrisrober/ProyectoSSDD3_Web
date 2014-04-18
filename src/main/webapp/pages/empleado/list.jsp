<%-- 
    Document   : listar
    Created on : 13-mar-2014, 16:18:52
    Author     : Cristian
--%>
<%@taglib prefix="t" uri="http://java.sun.com/jsp/jstl/core" %>
                <!-- Main content -->
                <section class="content">
                    ${ok}
                    ${error}
                    <div class="box">
                        <div class="box-header">
                            <h3 class="box-title">Lista de empleados</h3>                                    
                        </div><!-- /.box-header -->
                        <div class="box-body table-responsive">
                            <table id="tableempleados" class="table table-bordered table-striped">
                                <thead>
                                    <tr>
                                        <th style="display: none"></th>
                                        <th>Nombre</th>
                                        <th>Apellidos</th>
                                        <th>DNI</th>
                                        <th>Departamento</th>
                                        <th>Sucursal</th>
                                        <th>Editar</th>
                                        <th>Borrar</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <t:forEach items="${empleados}" var="item">
                                        <tr>
                                            <td style="display: none">${item.getId()}</td>
                                            <td><a href="EmpleadoController?action=profile&id=${item.getId()}">${item.getName()}</a></td>
                                            <td>${item.getSurname()}</td>
                                            <td>${item.getDni()}</td>
                                            <td>${item.getDepartamento()}</td>
                                            <td>${item.getSucursal()}</td>
                                            <td><a class="editar" href="EmpleadoController?action=edit&id=${item.getId()}">Editar</a></td>
                                            <td><a class="delete" href="EmpleadoController?action=delete&dni=${item.getDni()}&id=${item.getId()}">Borrar</a></td>
                                        </tr>
                                    </t:forEach>
                                </tbody>
                                <tfoot>
                                    <tr>
                                        <th style="display: none"></th>
                                        <th>Nombre</th>
                                        <th>Apellidos</th>
                                        <th>DNI</th>
                                        <th>Departamento</th>
                                        <th>Sucursal</th>
                                        <th>Editar</th>
                                        <th>Borrar</th>
                                    </tr>
                                </tfoot>
                            </table>

                        </div><!-- /.box-body -->
                    </div><!-- /.box -->

                    <a href="EmpleadoController?action=create" class="btn btn-primary">Create New</a>

                </section><!-- /.content -->