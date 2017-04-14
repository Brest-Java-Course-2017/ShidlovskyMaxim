var HOST = "http://localhost:8088";
var CAR_URL = "http://localhost:8088/car";
var CARS_URL = "http://localhost:8088/cars";
var CAR_PERIOD = "http://localhost:8088/car/period";
var CAR_MODEL = "http://localhost:8088/car/model/";
var CARS_AMOUNT = "http://localhost:8088/cars/amount";
var PRODUCER_URL = "http://localhost:8088/producer";

$.dto = null;

$(document).ready(function () {
    findAll();
});

// Register listeners
$('#btnSave').click(function () {
    if ($('#carId').val() == '')
        addCar();
    else
        updateCar();
    return false;
});

$('#btnClean').click(function () {
    $("#carId").val("");
    $("#producerName").val("");
    $("#model").val("");
    $("#releaseDate").val("");
    $("#amount").val("");
    $("#producerId").val("");
});

$('#btnFilter').click(function () {
    if ($('#from').val() == '' || $('#to').val() == '') {
        alert('The field is empty.');
    }
    else {
        getCarsForReleaseTimePeriod();
    }
});

$('#btnSearchById').click(function () {
    getCarById();
});

$('#btnSearchByModel').click(function () {
    getCarByModel();
});

$('#btnSearchByProducerId').click(function () {
    getCarsByProducerId();
});

    $(document).on("click", "a", function() {
    var action = $(this).text();
    var selectedCarId = $(this).data("id");

    if (action == "edit" || action != "delete") {
        $.each(dto, function (index) {
             if (dto[index].carId == selectedCarId) {
                $("#carId").val(selectedCarId);
                $("#producerName").val(dto[index].producerName);
                $("#model").val(dto[index].model);
                $("#releaseDate").val(dto[index].releaseDate);
                $("#amount").val(dto[index].amount);
                $("#producerId").val(dto[index].producerId);
             }
        });
    }
    else {
        deleteCar(selectedCarId);
    }
});

function findAll() {
    console.log('findAll');
    $.ajax({
        type: 'GET',
        url: CARS_URL,
        dataType: "json", // data type of response
        success: renderList,
        error: function(jqXHR, textStatus, errorThrown) {
            console.log(jqXHR, textStatus, errorThrown);
            alert('findAll: ' + textStatus);
        }
    });
    getAmountOfAllCars();
}

function getAmountOfAllCars() {
    console.log('getAmountOfAllCars');
    $.ajax({
        type: 'GET',
        url: CARS_AMOUNT,
        dataType: "json", // data type of response
        success: function (data, textStatus, jqXHR) {
            $("#amountOfAllCars").val(data);
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.log(jqXHR, textStatus, errorThrown);
            alert('getAmountOfAllCars: ' + textStatus);
        }
    });
}

function addCar() {
    console.log('addCar');
    $.ajax({
        type: 'POST',
        contentType: 'application/json',
        url: CAR_URL,
        dataType: "json",
        data: formToJSON(),
        success: function (data, textStatus, jqXHR) {
            alert('Car has been created successfully.');
            console.log('Car has been created successfully.');
            findAll();
        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert('addCar error: ' + errorThrown);
        }
    });
}

function updateCar() {
    console.log('updateCar');
    $.ajax({
        type: 'PUT',
        contentType: 'application/json',
        url: CAR_URL,
        data: formToJSON(),
        success: function (data, textStatus, jqXHR) {
            alert('Car has been updated successfully.');
            console.log('Car has been updated successfully.');
            findAll();
        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert('updateCar error: ' + errorThrown);
        }
    });
}

function deleteCar(carId) {
    console.log('deleteCar');
    $.ajax({
        type: 'DELETE',
        contentType: 'application/json',
        url: CAR_URL + '/' + carId,
        success: function (data, textStatus, jqXHR) {
            alert('Car has been deleted successfully.');
            findAll();
        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert('deleteCar error: ' + errorThrown);
        }
    });
}

function getCarsForReleaseTimePeriod() {
    console.log('getCarsForReleaseTimePeriod');
        $.ajax({
            type: 'GET',
            url: CAR_PERIOD + '?from=' + $('#from').val() + '&to=' + $('#to').val(),
            dataType: "json",
            success: renderList,
            error: function (jqXHR, textStatus, errorThrown) {
                alert('getCarsForReleaseTimePeriod error: ' + errorThrown);
            }
        });
}

function getCarById() {
    console.log('getCarById');
        $.ajax({
            type: 'GET',
            url: CAR_URL + '/' + $('#searchByIdField').val(),
            dataType: "json",
            success: renderList,
            error: function (jqXHR, textStatus, errorThrown) {
                      alert('getCarById error: ' + errorThrown);
                   }
        });
}

function getCarByModel() {
    console.log('getCarByModel');
        $.ajax({
            type: 'GET',
            url: CAR_MODEL + $('#searchByModelField').val(),
            dataType: "json",
            success: renderList,
            error: function (jqXHR, textStatus, errorThrown) {
                    alert('getCarByModel error: ' + errorThrown);
                    }
            });
}

function getCarsByProducerId() {
    console.log('getCarsByProducersId');
        $.ajax({
            type: 'GET',
            contentType: 'application/json',
            url: HOST + "/producer/" + $('#searchByProducerIdField').val() + "/cars",
            dataType: "json", // data type of response
            success: renderList,
            error: function(jqXHR, textStatus, errorThrown) {
                console.log(jqXHR, textStatus, errorThrown);
                alert('getCarsByProducersId: ' + textStatus);
            }
        });
}

function renderList(data) {
    dto = data == null ? [] : (data instanceof Array ? data : [data]);
    $('#carList tr').remove();
    $.each(dto, function (index, car) {
        drawRow(car);
    });
}

function drawRow(car) {
    var row = $("<tr />")
    $("#carList").append(row);
    row.append($("<td>" + car.producerName + "</td>"));
    row.append($("<td>" + '<a href="#" data-id="' + car.carId + '">' + car.model + '</a></td>'));
    row.append($("<td>" + car.releaseDate + "</td>"));
    row.append($("<td>" + car.amount + "</td>"));
    row.append($("<td>" + '<a href="#" data-id="' + car.carId + '">edit</a></td>'));
    row.append($("<td>" + '<a href="#" data-id="' + car.carId + '">delete</a></td>'));
    row.append($("<td style='display:none'>" + car.producerId + "</td>"));
}

function formToJSON() {
    var carId = $('#carId').val();
    var producerId = $('#producerId').val();

    if (producerId == "") {
                $.ajax({
                    async: false,
                    type: 'GET',
                    contentType: 'application/json',
                    url: PRODUCER_URL + "/name/" + $('#producerName').val(),
                    dataType: "json", // data type of response
                    success: function (data, textStatus, jqXHR) {
                                   producerId = data.producerId;
                             },
                    error: function(jqXHR, textStatus, errorThrown) {
                        console.log(jqXHR, textStatus, errorThrown);
                        alert('getCarsByProducersId: ' + textStatus);
                    }
                });
    }

    return JSON.stringify({
        "carId": carId == "" ? null : carId,
        "model": $('#model').val(),
        "releaseDate": $('#releaseDate').val(),
        "amount" : $('#amount').val(),
        "producerId" : producerId
    });
}