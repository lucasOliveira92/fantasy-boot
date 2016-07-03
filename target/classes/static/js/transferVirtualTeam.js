
$(document).on('click', '.clickable-row', function() {
    if($(this).hasClass("highlighted"))
        $(this).removeClass('highlighted');
    else
        $(this).addClass('highlighted').siblings().removeClass('highlighted');
});

function switchPlayers(budget) {
    var idAll=null;
    var posAll=null;
    var idMy=null;
    var posMy=null;
    var costAll=null;
    var costMy=null;
    var totalTransfers=null;


    $('tr.highlighted').each(function(){
        if($(this).hasClass('allPlayers')) {
            idAll = $(this).attr('id');
            posAll = $(this).find('td').eq(3).text();
            costAll = $(this).find('td').eq(4).text();
        }
        if($(this).hasClass('myTeam')) {
            idMy = $(this).attr('id');
            posMy = $(this).find('td').eq(2).text();
            costMy = $(this).find('td').eq(3).text();
        }
    });
    
    $('p.total-transfers').each(function(){
        totalTransfers = $(this).attr('id');
    });

    if (idAll == null || idMy == null) {
        $(".alerta").css("background-color", "red");
        $('.p-alerta').text("Select a player from both tables first!");
        $('.alerta').fadeIn(1000).delay(4000).fadeOut('slow');
    }else{
        if(posAll!=posMy){
            $(".alerta").css("background-color", "red");
            $('.p-alerta').text("Please select players from the same positions");
            $('.alerta').fadeIn(1000).delay(4000).fadeOut('slow');
        }else{
            if(budget < (costMy-costAll)){
                $(".alerta").css("background-color", "red");
                $('.p-alerta').text("Your budget isn't enough! Select other players");
                $('.alerta').fadeIn(1000).delay(3000).fadeOut('slow');
            }else{
                if(totalTransfers > 0){
                    $(".alerta").css("background-color", "green");
                    $('.p-alerta').text("Transfer is OK!");
                    $('.alerta').fadeIn(1000).delay(3000).fadeOut('slow');

                    $(function () {
                        var token = $("input[name='_csrf']").val();
                        var header = "X-CSRF-TOKEN";
                        $(document).ajaxSend(function (e, xhr, options) {
                            xhr.setRequestHeader(header, token);
                        });
                    });

                    var search = {
                        "newId": idAll,
                        "cldId": idMy
                    }
                    $.ajax({
                        type: "POST",
                        contentType: 'application/json; charset=utf-8',
                        dataType: 'json',
                        url: "/team/transfers/"+idAll+"/"+idMy,
                        data: JSON.stringify(search), // Note it is important
                        success: function (result) {
                        }
                    });

                    setTimeout(function(){
                        location.reload(true);
                    }, 2000);

                    setTimeout(function(){
                        window.open("/team/transfers/"+idAll+"/"+idMy,"_self");
                    }, 2500);
                }
                else{
                    $(".alerta").css("background-color", "red");
                    $('.p-alerta').text("You don't have enough transfers!");
                    $('.alerta').fadeIn(1000).delay(3000).fadeOut('slow');
                }
            }
        }

    }
};

function recarregaAllPlayers(){
    var positionFilter = $('#positionFilter option:selected').attr('id');
    var realTeamFilter = $('#realTeamFilter option:selected').attr('id');
    var orderFilter = -1;
    window.open("/team/transfers/"+realTeamFilter+"/"+positionFilter+"/"+orderFilter,"_self");
};


$(document).ready(function() {
    $('.alerta').hide();
});

