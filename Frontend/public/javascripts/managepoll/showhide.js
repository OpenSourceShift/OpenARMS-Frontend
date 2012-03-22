$(document).ready(function() {
  $('div.instances').hide();  
  $('.questionwith').click(function() {
	  // jQuery noob at work
    $(this).next().next().next("div.instances").slideToggle('fast');
  });
  
  $("button").button();
});