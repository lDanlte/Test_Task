var LOCAL_URL = $("#localUrl").data("url");

$(window).ready(function() {
    
    disableAll();
    
    $("#policySelect").change(function(){
        $("#policySelect").data("selected", $("#policySelect option:selected").val());
        clearTable();
        disableAll();
    });

    window.datepicker = $('.datepicker').datepicker({
        format: "dd.mm.yyyy",
        language: "ru"
    });
    datepicker.on("changeDate", function(e) {
        var age = getAge(e.date);
        $("#editAge").val(age);
    });
    
    
    $('#autocomplete').typeahead({
        
        //получение данных от сервера
        source: function (query, process) {
            return $.ajax({
                url: LOCAL_URL + 'policy/' + $("#policySelect").data("selected")
                       + '/drivers?q=' + query,
                method: "GET",
                dataType: "json",
                success: function (response) {
                    $("#notFound").html("");
                    return process(response);
                },
                error: function (response) {
                    process([]);
                    var notFoundHolder = $("#notFound");
                    notFoundHolder.html( "<div class='alert alert-danger alert-dismissible' role='alert'>" + 
                        "<button type='button' class='close' data-dismiss='alert' aria-label='Close'>" + 
                        "<span aria-hidden='true'>&times;</span></button>" +
                        "<strong>Не найдено</strong></div>"
                    );
                    
                }
            });
        },
        
        //формат вывода текста автоподсказми
        displayText: function (item) {
            if (item === "") {
                return item;
            }
            return item.fio + ' (' + stringifyDate(item.birthday) + ')';
        },
        
        autoSelect:false,
        delay: 100,
        minLength: 2,
        
        updater: function(item) {
            loadDriver(item.id);
            return "";
        }
        
    }).on('keyup', this, function (event) {
        if (event.keyCode === 13) {
            $('#autocomplete').typeahead("lookup");
        }
    });
    
});

//отправление новой информации о водителе на сервер
function updateDriver() {
    var id = $("#driverTable").data("driverId");
    if (id === "") {
        showMessage("Внимание", "Выбирите водителя.");
        return;
    }
    var editForm = $("#editForm");
    var fio =  editForm.find("#editSurname").val() + " " 
             + editForm.find("#editName").val() + " " 
             + editForm.find("#editMiddleName").val();
    var birthday = datepicker.val().split(".");
    var birthDate = {
        day: birthday[0],
        month: birthday[1],
        year: birthday[2]
    };
    var sex = (editForm.find("#male").prop("checked")) ? 0 : 1;
    var categories = [];
    editForm.find("#selectCategory option:selected").each(function(index, elem) {
        categories.push({
            id: $(elem).val()
        });
    });
    var policy = $("#policySelect").data("selected");
    var driver = {
        id: id,
        fio: fio,
        birthday: birthDate,
        sex: sex,
        policy: policy,
        categories: categories
    };

    $.ajax({
        url: LOCAL_URL + "drivers/" + id,
        method: "PUT",
        dataType: 'json',
        contentType: "application/json",
        data: JSON.stringify(driver),
        success: function (data) {
            setupTable(data);
            showMessage("Инфо", "Информация о водителе успешно обновлена.");
        },
        error: function (XMLHttp, textStatus, errorThrown) {
            var response = XMLHttp.responseJSON;
            if (response === undefined) {
                 showMessage("Внимание", "Произошла ошибка при обновлении информации о водителе.");
            } else {
                showMessage("Внимание", response.msg);
            }
        }
    });

}

//удаление водителя из полиса
function deleteFromPolicy() {
    var id = $("#driverTable").data("driverId");
    if (id === "") {
        showMessage("Внимание", "Выбирите водителя.");
        return;
    }

    $.ajax({
        url: LOCAL_URL + "policy/" + $("#policySelect").data("selected")
             + "/drivers/" + id,
        method: "DELETE",
        success: function() {
            clearTable();
            disableAll();
            showMessage("Инфо", "Удаление прошло успешно");
        },
        error:  function (XMLHttp, textStatus, errorThrown) {
            var response = XMLHttp.responseJSON;
            if (response === undefined) {
                 showMessage("Внимание", "Произошла ошибка при удалении водителя из полиса");
            } else {
                showMessage("Внимание", response.msg);
            }
        }

    });
}

//получение информации и водителе по его id
function loadDriver(id) {
    $.ajax({
       url: LOCAL_URL + "drivers/" + id,
       method: "GET",
       dataType: "json",
       success: setupTable,
       error: function (XMLHttp, textStatus, errorThrown) {
            var response = XMLHttp.responseJSON;
            if (response === undefined) {
                 showMessage("Внимание", "Произошла ошибка при загрузке информации о водителе.");
            } else {
                showMessage("Внимание", response.msg);
            }
        }
    });
}

//получение возраста человека из его даты рождения
function getAge(birthDate) {
    var today = new Date();
    var age = today.getFullYear() - birthDate.getFullYear();
    var m = today.getMonth() - birthDate.getMonth();
    if (m < 0 || (m === 0 && today.getDate() < birthDate.getDate())) {
        age--;
    }
    return age;
}

//переводит все элементы формы в неактивное состояние
function disableAll() {
    var editForm = $("#editForm");

    editForm.find("input, select, button").each(function (index, elem) {
        elem = $(elem);
        elem.val("");
        elem.prop("disabled", true);
    });

}

//очистка значений в таблице
function clearTable() {
    $("#driverTable").data("driverId", "");
    $("#fio").html("Не выбрано");
    $("#birthday").html("");
    $("#age").html("");
    $("#sex").html("");
    $("#categories").html("");
}

//ставит все элементы формы в активное состояние и проецирует информацию о водителе в поля
function editDriver() {
    if ($("#driverTable").data("driverId") === "") {
        showMessage("Внимание", "Выбирите водителя");
        return;
    }
    var editForm = $("#editForm");

    editForm.find("input, select, button").each(function (index, elem) {
        $(elem).prop("disabled", false);
    });
    editForm.find("#editAge").prop("disabled", true);

    var fio = $("#fio").html().split(" ");
    editForm.find("#editSurname").val(fio[0]);
    editForm.find("#editName").val(fio[1]);
    editForm.find("#editMiddleName").val(fio[2]);
    datepicker.datepicker("setDate", getDate($("#birthday").html()));

    if ($("#sex").html() === "М") {
        editForm.find("#male").prop("checked", true);
    } else {
        editForm.find("#female").prop("checked", true);
    }

    var categories = $("#categories").html().split(", ");
    var options = editForm.find("#selectCategory option");
    for (var i = 0; i < categories.length; i++) {
        for (var j = 0; j < options.length; j++) {
            if ($(options[j]).html() === categories[i]) {
                $(options[j]).prop("selected", true);
                break;
            }
        }
    }
}

//возвращает дату, полученную из строки формата dd.MM.yyyy
function getDate(str) {
    var strs = str.split(".");
    return new Date(strs[2], strs[1] - 1, strs[0]);
}

//Полученные данные заносятся в таблицу
function setupTable(data) {
    disableAll();
    $("#driverTable").data("driverId", data.id);
    $("#fio").html(data.fio);
    $("#birthday").html(stringifyDate(data.birthday));
    $("#age").html(getAge(new Date(data.birthday.year, data.birthday.month - 1, data.birthday.day)));
    $("#sex").html(data.sex === 0 ? "М" : "Ж");
    var categories = "";
    for (var i = 0; i < data.categories.length; i++) {
        categories += data.categories[i].name;
        if (i < data.categories.length - 1) {
            categories += ", ";
        }
   }
   $("#categories").html(categories);
}

//получение строки из даты
function stringifyDate(date) {

    var dateNum = date.day,
        month = date.month,
        year = date.year;

    return ((dateNum < 10) ? "0" : "") + dateNum + "." + ((month < 10) ? "0" : "") + month + "." + year;
}


function showMessage(title, message) {
    var modal = $("#modalInfo");
    
    modal.find(".modal-body").html(message);
    modal.find(".modal-title").html(title);
    
    modal.modal();
}