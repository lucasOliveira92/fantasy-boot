var pickPlayer=0;
$(document).on('click', '.clickable-row', function() {
    if($(this).hasClass("highlighted"))
        $(this).removeClass('highlighted');
    else
        $(this).addClass('highlighted').siblings().removeClass('highlighted');
});

function switchPlayers(id) {
    var p1 = $(".highlighted").find('td').eq(2).text();
    var p2 = $("#field #"+id).find('p').attr('id');

    if (!$(".highlighted").length) {
        alert("Select a player from the table first!");
        return;
    }
    if(($(".highlighted").find('td').eq(2).text() == 'GK') && (p1 != p2)){
        alert("You can only trade GKs with GKs!");
        return;
    }

    removeFromField(id);
    addToField();
};


function addToField() {
    var row = $(".highlighted");
    var id = row.attr('id');
    var data = row.find('td');
    var name = data.eq(1).text();
    var pos = data.eq(2).text();
    var cost = data.eq(3).text();
    var img = data.eq(0).find("img").clone().attr("height", "50%").prop('outerHTML');

    row.attr("class", "");
    row.attr("style", "background-color: #666666");

    row.fadeOut(300,function() {
        $(this).remove();
    });

    $("<div id="+id+" style='overflow: hidden; width: 15%; height: 100%; position: relative; display: inline-block'>\
"+img+"\
<p style='line-height: 100%; font-weight: bold; font-size: 100%' id="+ pos +">" + name + "</p>\
<h2 style='visibility: hidden;'>"+ cost +"</h2>\
<button type='button' class='btn btn-xs btn-default ' onclick='makeCaptain(this.id)' style='position: absolute; font-weight: bold;  background-color: darkgray; border: 2px solid #000000; left: 0; bottom: 0;  width: 30%; overflow: hidden' id="+id+">C</button>\
<button type='button' class='btn btn-xs btn-default' id="+id+" onclick='switchPlayers(this.id)' style='position: absolute; border: 2px solid #000000; right: 0; bottom: 0;  width: 70%; overflow: hidden'>\
<span class='glyphicon glyphicon-retweet'></span>\
</button>\
</div>\
").appendTo("#" + pos);



}

function removeFromField(id){
    var player = $("#field #"+id);
    var tablePlayer = $("#listPlayers #"+id);
    var id2 = player.attr('id');
    var name = player.find('p').text();
    var pos = player.find('p').attr('id');
    var cost = player.find('h2').text();
    var img = player.find('img').clone().attr("height", "20%").prop('outerHTML');

    $("<tr class='clickable-row' style='cursor:pointer;' id='id2'>\
    <td class='col-md-1 text-center'>"+img+"</td>\
        <td>"+ name +"</td>\
    <td>"+ pos +"</td>\
    <td>"+ cost +"</td>\
    </tr>\
        ").appendTo("#listPlayers");

    tablePlayer.attr("class","clickable-row");
    tablePlayer.attr("style","");


    player.fadeOut(300,function() {
        $(this).remove();
    });

}
function makeCaptain(id){
    var player =$("#field #"+id);
    var name = player.find('p').text();
    var pos = player.find('p').attr('id');
    var cost = player.find('h2').text();
    var img = player.find('img').clone().attr("height", "50%").prop('outerHTML');

    $('button.captain').each(function() {
        if($(this).attr('id') == id)
            $(this).addClass('cpt-selected')
        else
            $(this).removeClass('cpt-selected')
    });

    postJSONInfo();
}

//NAO ESQUECER DOS HEADERS (TEM NO VIRTUAL_TEAM_SHOW)
function postJSONInfo() {

    $(function () {
        var token = $("input[name='_csrf']").val();
        var header = "X-CSRF-TOKEN";
        $(document).ajaxSend(function(e, xhr, options) {
            xhr.setRequestHeader(header, token);
        });
    });
    

    var search = {
        "name": "TAUUU",
        "id" : 1
    }
    $.ajax({
        type: "POST",
        contentType: 'application/json; charset=utf-8',
        dataType: 'json',
        url: "/api/games",
        data: JSON.stringify(search), // Note it is important
        success: function (result) {
            console.log("WTF");
        }
    });
}
