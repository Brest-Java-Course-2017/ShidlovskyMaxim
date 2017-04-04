var PRODUCER_URL = "http://localhost:8088/producer";
var PRODUCERS_URL = "http://localhost:8088/producers";
var PRODUCER_NAME = "http://localhost:8088/producer/name/";
var PRODUCER_CAR = "http://localhost:8088/producer/car/";
var PRODUCERS_AMOUNT = "http://localhost:8088/producers/amount";

$.dto = null;

$(document).ready(function () {
    findAll();
});

// Register listeners
$(document).on("click", "a", function() {
    var action = $(this).text();
    var selectedProducerId = $(this).data("id");
    if (action == "edit" || action != "delete") {
        $.each(dto, function (index) {
            if (dto[index].producerId == selectedProducerId) {
                $("#producerId").val(selectedProducerId);
                $("#name").val(dto[index].name);
                $("#country").val(dto[index].country);
            }
        });
    } else {
        deleteProducer(selectedProducerId);
    }
});

$('#btnSave').click(function () {
    if ($('#producerId').val() == '')
        addProducer();
    else
        updateProducer();
    return false;
});

$('#btnClean').click(function () {
    $("#producerId").val("");
    $("#name").val("");
    $("#country").val("");
});

$('#btnSearchById').click(function () {
    if ($('#searchByIdField').val() == '') {
        alert('The field is empty.');
    }
    else {
        getProducerById();
    }
});

$('#btnSearchByName').click(function () {
    if ($('#searchByNameField').val() == '') {
            alert('The field is empty.');
    }
    else {
        getProducerByName();
    }
});

$('#btnSearchByCar').click(function () {
    if ($('#carId').val() == '') {
        alert('The field is empty.');
    }
    else {
        getProducerByCar();
    }
});


function findAll() {
    console.log('findAll');
    $.ajax({
        type: 'GET',
        url: PRODUCERS_URL,
        dataType: "json", // data type of response
        success: renderList,
        error: function(jqXHR, textStatus, errorThrown) {
            console.log(jqXHR, textStatus, errorThrown);
            alert('findAll: ' + textStatus);
        }
    });
    getAmountOfAllProducers();
}

function getAmountOfAllProducers() {
    console.log('getAmountOfAllProducers');
    $.ajax({
        type: 'GET',
        url: PRODUCERS_AMOUNT,
        dataType: "json", // data type of response
        success: function (data, textStatus, jqXHR) {
            $("#amountOfAllProducers").val(data);
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.log(jqXHR, textStatus, errorThrown);
            alert('getAmountOfAllProducers: ' + textStatus);
        }
    });
}

function addProducer() {
    console.log('addProducer');
    $.ajax({
        type: 'POST',
        contentType: 'application/json',
        url: PRODUCER_URL,
        dataType: "json",
        data: formToJSON(),
        success: function (data, textStatus, jqXHR) {
            alert('Producer has been created successfully.');
            console.log('Producer has been created successfully.');
            findAll();
        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert('addProducer error: ' + errorThrown);
        }
    });
}

function updateProducer() {
    console.log('updateProducer');
    $.ajax({
        type: 'PUT',
        contentType: 'application/json',
        url: PRODUCER_URL,
        data: formToJSON(),
        success: function (data, textStatus, jqXHR) {
            alert('Producer has been updated successfully.');
            console.log('Producer has been updated successfully.');
            findAll();
        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert('updateProducer error: ' + errorThrown);
        }
    });
}

function deleteProducer(producerId) {
    console.log('deleteProducer');
    $.ajax({
        type: 'DELETE',
        contentType: 'application/json',
        url: PRODUCER_URL + '/' + producerId,
        success: function (data, textStatus, jqXHR) {
            alert('Producer has been deleted successfully.');
            findAll();
        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert('deleteProducer error: ' + errorThrown);
        }
    });
}

function getProducerById() {
    console.log('getProducerById');
        $.ajax({
            type: 'GET',
            url: PRODUCER_URL + '/' + $('#searchByIdField').val(),
            dataType: "json",
            success: renderList,
            error: function (jqXHR, textStatus, errorThrown) {
                      alert('getProducerById error: ' + errorThrown);
                   }
        });
}

function getProducerByName() {
    console.log('getProducerByName');
        $.ajax({
            type: 'GET',
            url: PRODUCER_NAME + $('#searchByNameField').val(),
            dataType: "json",
            success: renderList,
            error: function (jqXHR, textStatus, errorThrown) {
                      alert('getProducerByName error: ' + errorThrown);
                   }
        });
}

function getProducerByCar() {
    console.log('getProducerByCar');
        $.ajax({
            type: 'GET',
            url: PRODUCER_CAR + $('#carId').val(),
            dataType: "json",
            success: renderList,
            error: function (jqXHR, textStatus, errorThrown) {
                      alert('getProducerByCar error: ' + errorThrown);
                   }
        });
}

function renderList(data) {
    dto = data == null ? [] : (data instanceof Array ? data : [data]);
    $('#producerList tr').remove();
    $.each(dto, function (index, producer) {
        drawRow(producer);
    });
}

function drawRow(producer) {
    var row = $("<tr />")
    $("#producerList").append(row);
    row.append($("<td>" + '<a href="#" data-id="' + producer.producerId + '">'
        + producer.name + '</a></td>'));
    row.append($("<td>" + producer.country + "</td>"));
    row.append($("<td>" + producer.amountOfCars + "</td>"));
    row.append($("<td>" + '<a href="#" data-id="' + producer.producerId + '">edit</a></td>'));
    row.append($("<td>" + '<a href="#" data-id="' + producer.producerId + '">delete</a></td>'));
}

function formToJSON() {
    var producerId = $('#producerId').val();
    return JSON.stringify({
        "producerId": producerId == "" ? null : producerId,
        "name": $('#name').val(),
        "country": $('#country').val()
    });
}