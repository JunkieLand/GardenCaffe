var initDatePickers = function() {
  $("#eventDate").datepicker({
    format: "dd-mm-yyyy"
  });
};

$(document).ready(function() {
  initDatePickers();
});