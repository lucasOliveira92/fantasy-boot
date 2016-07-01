/**
 * Created by Pedro Machado on 30/06/2016.
 */


$(document).ready(function(){

    $('#user-header-toggle button').click(function(){
        var menu = $(this).parent().find('ul');
        if(menu.hasClass('open')){
            menu.removeClass('open');
        }
        else {
            menu.addClass('open');
        }
    });
});