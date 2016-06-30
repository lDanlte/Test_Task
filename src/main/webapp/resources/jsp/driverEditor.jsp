<%-- 
    Document   : driverEdit
    Created on : 28.06.2016, 18:31:11
    Author     : Dante
--%>

<%@taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        
        <title>Редактирование</title>
        
        <link rel="stylesheet" type="text/css" href="<c:url value="/lib/bootstrap-3.3.5-dist/css/bootstrap.min.css" />" />
        <link rel="stylesheet" type="text/css" href="<c:url value="/lib/bootstrap-datepicker/css/bootstrap-datepicker.min.css" />" />
        <link rel="stylesheet" type="text/css" href="<c:url value="/css/style.css" />" />
        
    </head>
    
    <body>
        
        <c:set var="currentPolicy" value="${policyIds.iterator().next()}"></c:set>
        
        <div class="container">
            
            <div class="row">
                <h4>Выбран полис:
                    <select id="policySelect" data-selected="${currentPolicy}">
                        <c:forEach var="policyId" items="${policyIds}">
                            <option value="${policyId}">${policyId}</option>
                        </c:forEach>
                    </select>
                </h4>
            </div>
            
            <div class="row table-responsive">
                <table class="table">
                    <thead>
                        <tr>
                            <th>ФИО</th>
                            <th>Дата рождения</th>
                            <th>Возраст</th>
                            <th>Пол</th>
                            <th>Категории</th>
                            <th></th>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody id="driverTable" data-driver-id="">
                        <tr>
                            <td id="fio">Не выбрано</td>
                            <td id="birthday"></td>
                            <td id="age"></td>
                            <td id="sex"></td>
                            <td id="categories"></td>
                            <td><button type="button" class="btn btn-success" onclick="editDriver();">Редактировать</button></td>
                            <td><button type="button" class="btn btn-warning" onclick="deleteFromPolicy();">Удалить</button></td>
                        </tr>
                    </tbody>
                </table>
            </div>
                
                        
            <div class="row">
                
                <div id="custom-templates" class="col-sm-3">
                    <input id ="autocomplete" type="text" class="form-control" placeholder="Поиск по ФИО...">
                    <div id="notFound"></div>
                </div>
            </div>
            
                        
            <div class="row">
                
                <h4>Редактировние информации</h4>
                
                <form id="editForm" class="form-horizontal">
                    <div class="form-group">
                      <label for="editSurname" class="col-sm-2 control-label">Фамилия</label>
                      <div class="col-sm-4">
                          <input id="editSurname" class="form-control" type="text" placeholder="Фамилия">
                      </div>
                    </div>
                    <div class="form-group">
                      <label for="editName" class="col-sm-2 control-label">Имя</label>
                      <div class="col-sm-4">
                        <input id="editName" class="form-control" type="text" placeholder="Имя">
                      </div>
                    </div>
                    <div class="form-group">
                      <label for="editMiddleName" class="col-sm-2 control-label">Отчество</label>
                      <div class="col-sm-4">
                        <input id="editMiddleName"  class="form-control" type="text" placeholder="Отчество">
                      </div>
                    </div>
                    <div class="form-group">
                      <label for="editBirthDate" class="col-sm-2 control-label">Дата рождения</label>
                      <div class="col-sm-4">
                        <input id="editBirthDate" class="form-control datepicker" type="text" placeholder="Дата">
                      </div>
                    </div>
                    <div class="form-group">
                      <label class="col-sm-2 control-label" for="editAge">Возраст</label>
                      <div class="col-sm-4">
                          <input id="editAge" class="form-control" type="text"  placeholder="Возраст" disabled>
                      </div>
                    </div>
                    <div class="form-group">
                      <label for="male" class="col-sm-2 control-label">Пол</label>
                      <div class="col-sm-10">
                        <div class="checkbox">
                            <label> <input id="male" type="radio" name="sex"  value="m" checked>М</label>
                            <label> <input id="female" type="radio" name="sex"  value="f">Ж</label>
                        </div>
                          <div class="checkbox">
                            
                        </div>
                      </div>
                    </div>
                    
                     <div class="form-group">
                      <label for="inputPassword3" class="col-sm-2 control-label">Категории</label>
                      <div class="col-sm-2">
                          <select id="selectCategory" multiple="true" class="form-control">
                            <c:forEach var="category" items="${categories}">
                                <option value="${category.id}">${category.name}</option>
                            </c:forEach>
                        </select>
                      </div>
                    </div>
                    
                    <div class="form-group">
                      <div class="col-sm-offset-2 col-sm-10">
                          <button type="button" class="btn btn-primary" onclick="updateDriver();">Сохранить</button>
                      </div>
                    </div>
                  </form>
                
            </div>
            
                        
        </div>
                        
        <div id="localUrl" data-url="<c:url value="/" />"></div>
        
        
        <div class="modal fade" id="modalInfo">
            <div class="modal-dialog">
              <div class="modal-content">
                <div class="modal-header">
                  <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                  <h4 class="modal-title"></h4>
                </div>
                <div class="modal-body"></div>
                <div class="modal-footer">
                  <button type="button" class="btn jump-button" data-dismiss="modal">Закрыть</button>
                </div>
              </div>
            </div>
        </div>
        
        
        <script type="text/javascript" src="<c:url value="/lib/jquery/jquery-2.1.4.min.js" />"></script>
        <script type="text/javascript" src="<c:url value="/lib/bootstrap-3.3.5-dist/js/bootstrap.min.js" />"></script>
        <script type="text/javascript" src="<c:url value="/lib/bootstrap-datepicker/js/bootstrap-datepicker.min.js"/>"></script>
        <script type="text/javascript" src="<c:url value="/lib/bootstrap-datepicker/locales/bootstrap-datepicker.ru.min.js"/>"></script>
        <script type="text/javascript" src="<c:url value="/lib/bootstrap-3-typeahead/bootstrap3-typeahead.js"/>"></script>
        
        <script type="text/javascript" src="<c:url value="/js/index.js" />"></script>
        
    </body>
</html>
