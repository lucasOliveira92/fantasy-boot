$(document).on('click', '.clickable-row', function () {

    var current = parseInt($('#current_cost span').text());
    var valor = parseInt($(this).find('td').eq(3).text());
    if ($(this).hasClass("highlighted")) {
        $(this).removeClass('highlighted');
        current -= valor;
    }
    else {
        $(this).addClass('highlighted');
        current += valor;
    }
    $('#current_cost span').text(current);
    /*
    else {
        $(this).addClass('highlighted').siblings().removeClass('highlighted');
    }
    */
});

function pickPlayer(budget) {

    if($('#listAllPlayers tr.highlighted').length > 0) {
        var escolhidos = [];

        $('#listAllPlayers tr.highlighted').each(function () {
            var esc = {
                id: $(this).attr('id'),
                pos: $(this).find('td').eq(2).text(),
                cost: $(this).find('td').eq(3).text()
            };
            escolhidos.push(esc)
        });

        var equipa = $('#current_squad');
        var ids = []
        var custoTotal = 0;
        var avans = parseInt(equipa.find('p.for').find('span').text());
        var defs = parseInt(equipa.find('p.def').find('span').text());
        var meds = parseInt(equipa.find('p.mid').find('span').text());
        var gks = parseInt(equipa.find('p.gk').find('span').text());

        for (var i = 0; i < escolhidos.length; i++) {
            var e = escolhidos[i];
            if (e.pos === "GK") {
                gks++;
            }
            else if (e.pos === "DEF") {
                defs++;
            }
            else if (e.pos === "MID") {
                meds++;
            }
            else if (e.pos === "FOR") {
                avans++;
            }
            custoTotal += e.cost;
            ids.push(e.id)
        }

        //caso positivo
        if (custoTotal <= budget && gks <= 2 && defs <= 5 && meds <= 5 && avans <= 3) {
            console.log("trankz");
            $('#current_cost span').text("0");

            $('#listAllPlayers tr.highlighted').each(function () {
                var copia = $(this).clone();
                copia.removeClass('highlighted');
                var poz = copia.find('td').eq(2).text();
                if (poz === "GK") {
                    $('#listGK tr').last().after(copia);
                }
                else if (poz === "DEF") {
                    $('#listDEF tr').last().after(copia);
                }
                else if (poz === "MID") {
                    $('#listMID tr').last().after(copia);
                }
                else if (poz === "FOR") {
                    $('#listFOR tr').last().after(copia);
                }
                $(this).remove();
            });

            equipa.find('p.gk').find('span').text(gks);
            equipa.find('p.def').find('span').text(defs);
            equipa.find('p.mid').find('span').text(meds);
            equipa.find('p.for').find('span').text(avans);
        }
        else {
            var aviso = "";
            if (custoTotal > budget) {
                aviso += "Not enough money. The current team costs " + custoTotal + " coins and you only have " + budget + " to spend.\n";
            }
            if (gks > 2) {
                aviso += "Too many goal keepers. You picked " + gks + ", only 2 allowed.\n";
            }
            if (defs > 5) {
                aviso += "Too many defenders. You picked " + defs + ", only 5 allowed.\n";
            }
            if (meds > 5) {
                aviso += "Too many midfielders. You picked " + meds + ", only 5 allowed.\n";
            }
            if (avans > 3) {
                aviso += "Too many forwarders. You picked " + avans + ", only 3 allowed.\n";
            }

            console.log(aviso);
        }
    }
};

$('#toggle-buttons button').click(function(){
    var id = $(this).attr('id');
    var tabela = $('table#listAllPlayers');
    tabela.find('tr').show();
    switch(id){
        case "toggle_all":

            break;
        case "toggle_gk":
            tabela.find('tr').not(':first').each(function(){
                if($(this).find('td').eq(2).text() != "GK") {
                    $(this).hide();
                }
            });
            break;
        case "toggle_def":
            tabela.find('tr').not(':first').each(function(){
                if($(this).find('td').eq(2).text() != "DEF") {
                    $(this).hide();
                }
            });
            break;
        case "toggle_mid":
            tabela.find('tr').not(':first').each(function(){
                if($(this).find('td').eq(2).text() != "MID") {
                    $(this).hide();
                }
            });
            break;
        case "toggle_for":
            tabela.find('tr').not(':first').each(function(){
                if($(this).find('td').eq(2).text() != "FOR") {
                    $(this).hide();
                }
            });
            break;
        default:
            console.log("deu porcaria");
    }
});