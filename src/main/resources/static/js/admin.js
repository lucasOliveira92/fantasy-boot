function generateJornada() {
    alert("PASSEI AQUI");
    var jornada = $("#jornada-drop-down").val();

    window.open("/admin/generate/"+jornada,"_self");

    /*var search = {
        "jornada": jornada
    }
    $.ajax({
        type: "GET",
        contentType: 'application/json; charset=utf-8',
        dataType: 'json',
        url: "generate/"+jornada,
        data: JSON.stringify(search), // Note it is important
        success: function (result) {
            alert("FDP");
        }
    });*/

}