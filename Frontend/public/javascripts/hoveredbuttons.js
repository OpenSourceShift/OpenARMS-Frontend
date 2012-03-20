jQuery(function($){
  var hovered = $('.tools').children();
  $.each(hovered, function(){
    var $item = $(this);
    $item.hover(function(){
      $item.addClass('hovere-active');
    }, function(){
      $item.removeClass('hovere-active');
    });
  });
});
