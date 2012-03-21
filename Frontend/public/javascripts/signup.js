jQuery(function($){
  var initD = $('.description').find('#initD');
  var $initD = $(initD);
  $initD.hover(function(){
    $initD.addClass('initD-hovered');
  }, function(){
    $initD.removeClass('initD-hovered');
  });

  var register = $('.description').find('#register');
  var $register = $(register);
  $initD.click(function(){
    $initD.css('display', 'none');
    $register.css('display', 'block');
  });

});
