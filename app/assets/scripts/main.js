var initDatePickers = function() {
  $("#from").datepicker({
    format: "dd-mm-yyyy"
  });
  $("#to").datepicker({
    format: "dd-mm-yyyy"
  });
};

$(document).ready(function() {
  initDatePickers();
});