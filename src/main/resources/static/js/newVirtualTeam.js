var novoBudget = parseInt($('#current-budget span').text());

$(document).on('click', '.clickable-row', function () {

    var current = parseInt($('#current_cost span').text());
    var valor = parseInt($(this).find('td').eq(3).text());
    if ($(this).hasClass("highlighted")) {
        $(this).removeClass('highlighted');
        if($(this).hasClass("listAllPlayersTr")){
            current -= valor;
        }
        else{
            current += valor;
        }
    }
    else {
        $(this).addClass('highlighted');
        if($(this).hasClass("listAllPlayersTr")){
            current += valor;
        }
        else{
            current -= valor;
        }
    }
    $('#current_cost span').text(current);
    /*
    else {
        $(this).addClass('highlighted').siblings().removeClass('highlighted');
    }
    */
});

function pickPlayer() {

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
            custoTotal += parseInt(e.cost);
            ids.push(e.id)
        }

        //caso positivo
        if (custoTotal <= novoBudget && gks <= 2 && defs <= 5 && meds <= 5 && avans <= 3) {
            console.log("trankz");
            var currentCost = parseInt($('#current_cost span').text());
            novoBudget = novoBudget - currentCost;

            $('#current_cost span').text("0");
            $('#current-budget span').text(novoBudget);

            $('#listAllPlayers tr.highlighted').each(function () {
                var copia = $(this).clone();
                copia.removeClass('highlighted');
                copia.removeClass('listAllPlayersTr');
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
            if (custoTotal > novoBudget) {
                aviso += "Not enough money. The current team costs " + custoTotal + " coins and you only have " + novoBudget + " to spend.\n";
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
            alert(aviso);
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

function removePlayer() {

    if($('#current_squad tr.highlighted').length > 0) {
        var escolhidos = [];

        $('#current_squad tr.highlighted').each(function () {
            var esc = {
                id: $(this).attr('id'),
                pos: $(this).find('td').eq(2).text(),
                cost: $(this).find('td').eq(3).text()
            };
            escolhidos.push(esc)
        });

        var equipa = $('#current_squad');
        var ids = [];
        var custoTotal = 0;
        var avans = parseInt(equipa.find('p.for').find('span').text());
        var defs = parseInt(equipa.find('p.def').find('span').text());
        var meds = parseInt(equipa.find('p.mid').find('span').text());
        var gks = parseInt(equipa.find('p.gk').find('span').text());

        for (var i = 0; i < escolhidos.length; i++) {
            var e = escolhidos[i];
            if (e.pos === "GK") {
                gks--;
            }
            else if (e.pos === "DEF") {
                defs--;
            }
            else if (e.pos === "MID") {
                meds--;
            }
            else if (e.pos === "FOR") {
                avans--;
            }
            custoTotal -= e.cost;
            ids.push(e.id)
        }

        equipa.find('p.gk').find('span').text(gks);
        equipa.find('p.def').find('span').text(defs);
        equipa.find('p.mid').find('span').text(meds);
        equipa.find('p.for').find('span').text(avans);


        var currentCost = parseInt($('#current_cost span').text());
        novoBudget = novoBudget - currentCost;
        $('#current_cost span').text("0");
        $('#current-budget span').text(novoBudget);

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

        $('#listGK tr.highlighted').each(function () {
            var copia = $(this).clone();
            copia.removeClass('highlighted');
            copia.addClass('listAllPlayersTr');
            $('#listAllPlayers tr').last().after(copia);
            $(this).remove();
        });

        $('#listDEF tr.highlighted').each(function () {
            var copia = $(this).clone();
            copia.removeClass('highlighted');
            $('#listAllPlayers tr').last().after(copia);
            $(this).remove();
        });

        $('#listMID tr.highlighted').each(function () {
            var copia = $(this).clone();
            copia.removeClass('highlighted');
            $('#listAllPlayers tr').last().after(copia);
            $(this).remove();
        });

        $('#listFOR tr.highlighted').each(function () {
            var copia = $(this).clone();
            copia.removeClass('highlighted');
            $('#listAllPlayers tr').last().after(copia);
            $(this).remove();
        });
    }
};

function submitTeam(){
    var equipa = [];
    var gks = [];
    var defs = [];
    var mids = [];
    var fors = [];
    var gkscount = 0;
    var defscount = 0;
    var midscount = 0;
    var forscount = 0;
    $('#listGK tr').each(function () {
        gkscount++;
        gks.push(($(this).attr('id')));
    });
    $('#listDEF tr').each(function () {
        defscount++;
        defs.push(($(this).attr('id')));
    });
    $('#listMID tr').each(function () {
        midscount++;
        mids.push(($(this).attr('id')));
    });
    $('#listFOR tr').each(function () {
        forscount++;
        fors.push(($(this).attr('id')));
    });

    for(var i=0;i<gks.length;i++){
        if(i!=0){
            equipa.push(gks[i]);
        }
    }
    for(var i=0;i<defs.length;i++){
        if(i!=0){
            equipa.push(defs[i]);
        }
    }for(var i=0;i<mids.length;i++){
        if(i!=0){
            equipa.push(mids[i]);
        }
    }for(var i=0;i<fors.length;i++){
        if(i!=0){
            equipa.push(fors[i]);
        }
    }

    user = $('.current-user-id').text();
    var teamName = $("input:text[name=name]").val();
    var search = {
        "user": user,
        "teamName": teamName,
        "equipa": equipa
    }

    $(function () {
        var token = $("input[name='_csrf']").val();
        var header = "X-CSRF-TOKEN";
        $(document).ajaxSend(function (e, xhr, options) {
            xhr.setRequestHeader(header, token);
        });
    });

    $.ajax({
        type: "POST",
        contentType: 'application/json; charset=utf-8',
        dataType: 'json',
        url: "/save/new/team",
        data: JSON.stringify(search), // Note it is important
        success: function (result) {
        }
    });

}