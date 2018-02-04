$(document).ready(function() {
	$( ".datepicker" ).datepicker({
		dateFormat: 'dd MM, yy',
		changeMonth: true,
		changeYear: true,
		onClose: function () { $(this).valid(); }
	});
});

$(document).ready(function() {
 $('body').click(function(event) {
   if ($(event.target).is(".datepicker")) {
     $(".datepicker").datepicker({showOn: 'focus'}).focus();
   }
 });
});