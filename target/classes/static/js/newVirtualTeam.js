$(document).on('click', '.clickable-row', function () {

    if ($(this).hasClass("highlighted")) {
        $(this).removeClass('highlighted');
    }
    else {
        $(this).addClass('highlighted').siblings().removeClass('highlighted');
    }
});

function pickPlayer(budget) {
    var idAll=null;
    var posAll=null;
    var costAll=null;
    var idMy=null;
    var posMy=null;
    var costMy=null;

    var escolhidos = [];


    $('tr.highlighted').each(function(){
        var esc = {
            id: $(this).attr('id'),
            pos: $(this).find('td').eq(2).text(),
            cost: $(this).find('td').eq(3).text()
        };
        escolhidos.push(esc)
    });

    var ids = []
    var custoTotal = 0;
    var avans = 0;
    var defs = 0;
    var meds = 0;
    var gks = 0;

    for(var i=0; i<escolhidos.length; i++){
        var e = escolhidos[i];
        if(e.pos === "GK"){
            gks++;
        }
        else if(e.pos === "DEF"){
            defs++;
        }
        else if(e.pos === "MID"){
            meds++;
        }
        else if(e.pos === "FOR"){
            avans++;
        }
        custoTotal += e.cost;
        ids.push(e.id)
    }

    //caso positivo
    if(custoTotal <= budget && gks <= 2 && defs <= 5 && meds <=5 && avans <= 3){
        console.log("trankz");
    }
    else {
        var aviso = "";
        if(custoTotal > budget){
            aviso += "Not enough money. The current team costs "+custoTotal+" coins and you only have "+budget+" to spend.\n";
        }
        if(gks > 2){
            aviso += "Too many goal keepers. You picked "+gks+", only 2 allowed.\n";
        }
        if(defs > 5){
            aviso += "Too many defenders. You picked "+defs+", only 5 allowed.\n";
        }
        if(meds > 5){
            aviso += "Too many midfielders. You picked "+meds+", only 5 allowed.\n";
        }
        if(avans > 3){
            aviso += "Too many forwarders. You picked "+avans+", only 3 allowed.\n";
        }
    }

    var table = $("#myTeam");
};