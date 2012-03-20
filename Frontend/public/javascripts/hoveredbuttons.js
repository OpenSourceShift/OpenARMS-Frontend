jQuery(function($){
  var hovered = $('#content').find('.button');
  $.each(hovered, function(){
    var $item = $(this);
    $item.hover(function(){
      $item.addClass('hovere-active');
    }, function(){
      $item.removeClass('hovere-active');
    });
  });
});
