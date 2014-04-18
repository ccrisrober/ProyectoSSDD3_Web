<%@taglib prefix="t" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
                <!-- Main content -->
                <section class="content">
                                    
                    <!-- MAPA -->
                    <div class="box box-primary">
                        <div class="box-header">
                            <i class="fa fa-map-marker"></i>
                            <h3 class="box-title">
                                Mapa de porcentajes
                            </h3>
                        </div>
                        <div class="box-body">
                            <div id="world-map" style="height: 300px;">
                                <input type="hidden" id="datosTeruel" value="${datosTeruel}" />
                                <input type="hidden" id="datosSoria" value="${datosSoria}" />                
                            </div>
                        </div>
                    </div>
                </section>
