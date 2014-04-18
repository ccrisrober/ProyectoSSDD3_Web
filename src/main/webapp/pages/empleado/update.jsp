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
                            <p>Pincha <a href="EmpleadoController?action=list">aquí</a> para volver a la list de empleados.</p>
                        </t:when>
                        <t:otherwise>
                            <t:if test="${not empty error}">
                                <div class="alert alert-danger alert-dismissable">
                                    <i class="fa fa-ban"></i>
                                    <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                                    <b>Error!</b> ${error}
                                </div>
                            </t:if>
                        <form role="form" method="post" action="EmpleadoController" id="updForm">
                            <div class="box-body">
                                <input type="hidden" name="action" id="action" value="update" />
                                <input type="hidden" name="ac" id="ac" value="${requestScope.asociatedchar}" />
                                <input type="hidden" name="ai" id="ai" value="${requestScope.asociatedpos}" />
                                <input type="hidden" name="idfield" id="idfield" value="${id}" />

                                <div class="form-group">
                                    <label for="userfield">Usuario</label>
                                    <input type="text" class="form-control" id="userfield" name="userfield" value="${username}" placeholder="Enter ..."/>
                                </div>

                                <div class="form-group">
                                    <label for="passwordfield">Password</label>
                                    <input type="password" class="form-control" id="passwordfield" name="passwordfield" value="${password}" placeholder="Password">
                                </div>

                                <div class="form-group">
                                    <label for="namefield">Nombre</label>
                                    <input type="text" class="form-control" id="namefield" name="namefield" value="${name}" placeholder="Enter ..."/>
                                </div>

                                <div class="form-group">
                                    <label for="surnamefield">Apellidos</label>
                                    <input type="text" class="form-control" id="surnamefield" name="surnamefield" value="${surname}" placeholder="Enter ..."/>
                                </div>

                                <div class="form-group">
                                    <label for="dnifield">DNI</label>
                                    <input type="text" class="form-control" id="dnifield" name="dnifield" value="${dni}" placeholder="Enter ..."/>
                                </div>

                                <div class="form-group">
                                    <label for="departamentofield">Departamento</label>
                                    <input type="text" class="form-control" id="departamentofield" name="departamentofield" value="${departamento}" placeholder="Enter ..."/>
                                </div>

                                <input type="hidden" class="form-control" id="sucursalfield" name="sucursalfield" value="${sucursal}" />
                                
                            </div><!-- /.box-body -->

                            <div class="box-footer">
                                <button type="submit" class="btn btn-primary">Submit</button>
                            </div>

                        </form>   
                        </t:otherwise>
                    </t:choose>

                </section><!-- /.content -->