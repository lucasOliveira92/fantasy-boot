var pickPlayer=0;
$(document).on('click', '.clickable-row', function() {
    if($(this).hasClass("highlighted"))
        $(this).removeClass('highlighted');
    else
        $(this).addClass('highlighted').siblings().removeClass('highlighted');
});

function switchPlayers(id) {

    if (!$(".highlighted").length) {
        alert("Select a player from the table first!");
        return;
    }
    if(($(".highlighted").find('td').eq(1).text() == 'GK') != $("#GK #"+id).length){
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
    var img = data.eq(0).find("img").clone().attr("height", 50).attr("width", 50).prop('outerHTML');
    row.attr("class", "");
    row.attr("style", "background-color: #666666");

    row.fadeOut(300,function() {
        $(this).remove();
    });

    $("<div id="+id+" style='overflow: hidden; border: 2px solid #000000; background-color: #C0C0C0; width: 15%; height: 100%; position: relative; display: inline-block'>\
<p style='line-height: 100%; font-size: 70%' id="+ pos +">" + name + "</p>\
<h2 style='visibility: hidden;'>"+ cost +"</h2>\
<button type='button' class='btn btn-xs btn-default' id="+id+" onclick='switchPlayers(this.id)' style='position: absolute; right: 0; bottom: 0;  width: 100%; overflow: hidden'>\
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

    alert(id2);
    alert(name);
    alert(pos)

    $("<tr class='clickable-row' style='cursor:pointer;' id='id2'>\
    <td class='col-md-1 text-center'><img src='http://i.imgur.com/m7ZcOFv.png' height='20%' /></td>\
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