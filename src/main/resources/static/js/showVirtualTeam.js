var currentCaptain = -1;
var user;
var titulares = [];

$(document).on('click', '.clickable-row', function () {

    if ($(this).hasClass("highlighted")) {
        $(this).removeClass('highlighted');
        $('button.trade').fadeOut("500");
    }
    else {
        $(this).addClass('highlighted').siblings().removeClass('highlighted');
        $('button.trade').fadeIn("500");
    }
});

if ($('tr.highlighted').length < 0) {
    $('button.trade').fadeIn("500");
}
else {
    $('button.trade').hide();
};


function switchPlayers(id) {
    var p1 = $(".highlighted").find('td').eq(2).text();
    var p2 = $("#field #" + id).find('p').attr('id');

    if (!$(".highlighted").length) {
        alert("Select a player from the table first!");
        return;
    }
    if (($(".highlighted").find('td').eq(2).text() == 'GK') && (p1 != p2)) {
        alert("You can only trade GKs with GKs!");
        return;
    }

    removeFromField(id);
    addToField();

    $('button.trade').hide();
};


function addToField() {
    var row = $(".highlighted");
    var id = row.attr('id');
    var data = row.find('td');
    var name = data.eq(1).text();
    var pos = data.eq(2).text();
    var cost = data.eq(3).text();
    var points = data.eq(4).text();
    var img = data.eq(0).find("img").clone().attr("height", "50%").prop('outerHTML');


    row.attr("class", "");
    row.attr("style", "background-color: #666666");

    row.fadeOut(300, function () {
        $(this).remove();
    });

    $("<div class='titular' id=" + id + " style='overflow: hidden; width: 15%; height: 100%; position: relative; display: inline-block'>\
" + img + "\
<p style='line-height: 100%; font-weight: bold; font-size: 70%' id=" + pos + ">" + name + "</p>\
<h2 style='visibility: hidden;'>" + cost + "</h2>\
<h3 style='visibility: hidden;'>" + points + "</h3>\
<button type='button' class='btn btn-xs captain' onclick='makeCaptain(this.id)' style='position: absolute; font-weight: bold; left: 0; margin-left: 10%; bottom: 0;  width: 40%; overflow: hidden' id=" + id + ">C</button>\
<button type='button' class='btn btn-xs btn-default trade' id=" + id + " onclick='switchPlayers(this.id)' style='position: absolute; right: 0; margin-right: 10%; bottom: 0;  width: 40%; overflow: hidden'>\
<span class='glyphicon glyphicon-retweet'></span>\
</button>\
</div>\
").appendTo("#" + pos);


}

function removeFromField(id) {

    if (currentCaptain == id)
        currentCaptain = -1;
    var player = $("#field #" + id);
    var tablePlayer = $("#listPlayers #" + id);
    var id2 = player.attr('id');
    var name = player.find('p').text();
    var pos = player.find('p').attr('id');
    var cost = player.find('h2').text();
    var points = player.find('h3').text();
    var img = player.find('img').clone().attr("height", "20%").prop('outerHTML');

    //REMOVER DA LISTA DE TITULARES
    var index = titulares.indexOf(id);
    if (index > -1) {
        titulares.splice(index, 1);
    }

    $("<tr class='clickable-row' style='cursor:pointer;' id='id2'>\
    <td class='col-md-1 text-center'>" + img + "</td>\
        <td>" + name + "</td>\
    <td>" + pos + "</td>\
    <td>" + cost + "</td>\
    <td>" + points + "</td>\
    </tr>\
        ").appendTo("#listPlayers");

    tablePlayer.attr("class", "clickable-row");
    tablePlayer.attr("style", "");


    player.fadeOut(300, function () {
        $(this).remove();
    });

}
function makeCaptain(id) {
    currentCaptain = id;
    var player = $("#field #" + id);
    var name = player.find('p').text();
    var pos = player.find('p').attr('id');
    var cost = player.find('h2').text();
    var img = player.find('img').clone().attr("height", "50%").prop('outerHTML');


    $('button.captain').each(function () {
        if ($(this).attr('id') == id)
            $(this).addClass('cpt-selected')
        else
            $(this).removeClass('cpt-selected')
    });
}

//NAO ESQUECER DOS HEADERS (TEM NO VIRTUAL_TEAM_SHOW)
function updateStrategy() {
    console.log("entrei no botao");
    titulares = [];


    $('button.cpt-selected').each(function() {
        currentCaptain = $(this).attr('id');
    });

    user = $('.current-user-id').text();
    if (currentCaptain == -1) {
        $(".alerta").css("background-color", "red");
        $('.p-alerta').text("You must pick a captain for your team");
        $('.alerta').fadeIn(1000).delay(4000).fadeOut('slow');
        return;
    }
    //console.log(user);

    $(function () {
        var token = $("input[name='_csrf']").val();
        var header = "X-CSRF-TOKEN";
        $(document).ajaxSend(function (e, xhr, options) {
            xhr.setRequestHeader(header, token);
        });
    });


    $('.titular').each(function () {
        titulares.push(($(this).attr('id')));
    });

    var search = {
        "user": user,
        "capitao": currentCaptain,
        "titulares": titulares
    }
    console.log("antes do ajax");
    $.ajax({
        type: "POST",
        contentType: 'application/json; charset=utf-8',
        dataType: 'json',
        url: "/save/team",
        data: JSON.stringify(search),
        success: function (result) {
            console.log("cenas");
            $(".alerta").css("background-color", "green");
            $('.p-alerta').text("Updated successfully");
            $('.alerta').fadeIn(1000).delay(4000).fadeOut('slow');
        }
    });
    $(".alerta").css("background-color", "green");
    $('.p-alerta').text("updated successfully");
    $('.alerta').fadeIn(1000).delay(4000).fadeOut('slow');
}
