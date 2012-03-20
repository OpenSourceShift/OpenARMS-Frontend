jQuery(function($){
  var buttons = $('.tools').children();
  var descriptions = $('.description').children();
  var current = 0; // number of current description
  $(descriptions[current]).css('display', 'block');

  $.each(buttons, function(n){
    $(this).click(function(){
      $(descriptions[current]).css('display', 'none');
      current = n;
      $(descriptions[current]).css('display', 'block');
    });
  });
});
