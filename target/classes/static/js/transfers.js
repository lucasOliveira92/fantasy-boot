$(document).ready(function () {

    
    $("#listAllPlayers tr").css('cursor', 'pointer');

    $('#listAllPlayers').bootstrapTable({
        pagination: true,
        pageSize: 15,
        pageList: [15, 30, 45, 60, 80, 100]//list can be specified here
    });
});

