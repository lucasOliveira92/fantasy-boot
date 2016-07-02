$(document).ready(function () {
    $('#estatisticas-table').bootstrapTable({
        pagination: true,
        pageSize: 15,
        pageList: [15, 30, 45, 60, 80, 100]//list can be specified here
    });
});