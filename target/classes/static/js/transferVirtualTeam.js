
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
        alert("Select a player from both tables first!");
    }else{
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
};

function recarregaAllPlayers(){
    var positionFilter = $('#positionFilter option:selected').attr('id');
    var realTeamFilter = $('#realTeamFilter option:selected').attr('id');
    var orderFilter = $('#orderFilter option:selected').attr('id');
    window.open("/team/transfers/"+realTeamFilter+"/"+positionFilter+"/"+orderFilter,"_self");
};
