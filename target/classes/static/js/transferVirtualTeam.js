
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
<<<<<<< HEAD
        if(posAll != posMy){
            console.log(posAll);
            console.log(posMy);
            alert("Positions doesn't match! Please select players from the same positions");
        }else{
            if(budget < (costMy-costAll)){
            alert("Your budget isn't enough! Select other players");
        }else{
                if(totalTransfers > 0){
                    alert("Transfer is OK!");
                    window.open("/team/transfers/"+idAll+"/"+idMy,"_self");
                }else{
                    alert("You don't have enough transfers!");
                }

            }
        }
    }
=======
        if(posAll!=posMy){
            $('.p-alerta').text("Please select players from the same positions");
            $('.alerta').fadeIn(1000).delay(4000).fadeOut('slow');
        }else{
            if(budget < (costMy-costAll)){
                $('.p-alerta').text("Your budget isn't enough! Select other players");
                $('.alerta').fadeIn(1000).delay(3000).fadeOut('slow');
            }else{
                $('.p-alerta').text("Transfer is OK!");
                $('.alerta').fadeIn(1000).delay(3000).fadeOut('slow');
                window.open("/team/transfers/"+idAll+"/"+idMy,"_self");}}}
>>>>>>> 8ed4004a4c9c45c31c9d89a026344373b10a7bc6
};

function recarregaAllPlayers(){
    var positionFilter = $('#positionFilter option:selected').attr('id');
    var realTeamFilter = $('#realTeamFilter option:selected').attr('id');
    var orderFilter = $('#orderFilter option:selected').attr('id');
    window.open("/team/transfers/"+realTeamFilter+"/"+positionFilter+"/"+orderFilter,"_self");
};


$(document).ready(function() {
    $('.alerta').hide();
});