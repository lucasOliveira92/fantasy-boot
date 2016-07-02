
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
        $('.p-alerta').text("Select a player from both tables first!");
        $('.alerta').fadeIn(1000).delay(4000).fadeOut('slow');
    }else{
        if(posAll!=posMy){
            $('.p-alerta').text("Please select players from the same positions");
            $('.alerta').fadeIn(1000).delay(4000).fadeOut('slow');
        }else{
            if(budget < (costMy-costAll)){
                $('.p-alerta').text("Your budget isn't enough! Select other players");
                $('.alerta').fadeIn(1000).delay(3000).fadeOut('slow');
            }else{
                if(totalTransfers > 0){
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


                }
                else{
                    $('.p-alerta').text("You don't have enough transfers!");
                    $('.alerta').fadeIn(1000).delay(3000).fadeOut('slow');
                }
            }
        }

    }
};

function recarregaAllPlayers(){
    var realTeamFilter = $('#realTeamFilter option:selected').attr('id');
    var orderFilter = $('#orderFilter option:selected').attr('id');
    window.open("/team/transfers/"+realTeamFilter+"/-1/"+orderFilter,"_self");
};

$(document).ready(function() {
    $('.alerta').hide();
});

$('#toggle-buttons button').click(function(){
    var id = $(this).attr('id');
    alert(id);
    var tabela = $('table#listAllPlayers');
    tabela.find('tr').show();
    switch(id){
        case "toggle_all":

            break;
        case "toggle_gk":
            tabela.find('tr').not(':first').each(function(){
                if($(this).find('td').eq(3).text() != "GK") {
                    $(this).hide();
                }
            });
            break;
        case "toggle_def":
            tabela.find('tr').not(':first').each(function(){
                if($(this).find('td').eq(3).text() != "DEF") {
                    $(this).hide();
                }
            });
            break;
        case "toggle_mid":
            tabela.find('tr').not(':first').each(function(){
                if($(this).find('td').eq(3).text() != "MID") {
                    $(this).hide();
                }
            });
            break;
        case "toggle_for":
            tabela.find('tr').not(':first').each(function(){
                if($(this).find('td').eq(3).text() != "FOR") {
                    $(this).hide();
                }
            });
            break;
        default:
            console.log("deu porcaria");
    }
});