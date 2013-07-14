var initDatePickers = function() {
  $("#from").datepicker({
    format: "dd-mm-yyyy"
  });
  $("#to").datepicker({
    format: "dd-mm-yyyy"
  });
};

var initBookingForm = function() {
  var form = $("#bookingForm");
  form.on("submit", function(e) {
    e.preventDefault();
    var hasError = false;

    $(".control-group").removeClass("error");
    $(".help-block").hide();

    var nameCtrl = $("#nameCtrl");
    if(nameCtrl.find("input").val().length == 0){
      hasError = true;
      nameCtrl.addClass("error");
      nameCtrl.find(".help-block").show();
    }

    var emailCtrl = $("#emailCtrl");
    var emailVal = emailCtrl.find("input").val();
    if(emailVal.length < 3 || emailVal.indexOf("@") == -1){
      hasError = true;
      emailCtrl.addClass("error");
      emailCtrl.find(".help-block").show();
    }

    var peopleNbCtrl = $("#peopleNbCtrl");
    var peopleNbVal = peopleNbCtrl.find("input").val();
    if(peopleNbVal.length == 0 || !peopleNbVal.match(/^\d*$/) || parseInt(peopleNbVal) == 0) {
      hasError = true;
      peopleNbCtrl.addClass("error");
      peopleNbCtrl.find(".help-block").show();
    }

    var fromCtrl = $("#fromCtrl"),
      fromVal = $("#fromInput").val(),
      from = moment(fromVal, "DD-MM-YYYY"),
      toCtrl = $("#toCtrl"),
      toVal = $("#toInput").val(),
      to = moment(toVal, "DD-MM-YYYY");
    if(fromVal.length == 0 || toVal.length == 0 ||
      from.isBefore(moment().subtract("days", 1)) || to.isBefore(from) ||
      to.diff(from, "days") == 0) {
      hasError = true;
      fromCtrl.addClass("error");
      fromCtrl.find(".help-block").show();
      toCtrl.addClass("error");
    }

    if(!hasError) {
      $("#bookingSubmit").hide();
      $("#bookingSuccess").show();
      $.ajax({
        url: form.attr("action"),
        type: "POST",
        data: form.serialize()
      });
    }
  });

  $("#fromInput, #toInput").on("change", function() {
    var fromVal = $("#fromInput").val(),
        toVal = $("#toInput").val();
    if(fromVal.length > 0 && toVal.length > 0) {
      var from = moment(fromVal, "DD-MM-YYYY"),
          to = moment(toVal, "DD-MM-YYYY");
      var nbDays = to.diff(from, "days");
      $("#nbNightVal").val(nbDays);
    }
  });
};


var initContactForm = function() {
  var form = $("#contactForm");
  form.on("submit", function(e) {
    e.preventDefault();
    var hasError = false;

    $(".control-group").removeClass("error");
    $(".help-block").hide();

    var nameCtrl = $("#nameCtrl");
    if(nameCtrl.find("input").val().length == 0){
      hasError = true;
      nameCtrl.addClass("error");
      nameCtrl.find(".help-block").show();
    }

    var emailCtrl = $("#emailCtrl");
    var emailVal = emailCtrl.find("input").val();
    if(emailVal.length < 3 || emailVal.indexOf("@") == -1){
      hasError = true;
      emailCtrl.addClass("error");
      emailCtrl.find(".help-block").show();
    }

    var msgCtrl = $("#msgCtrl");
    if(msgCtrl.find("textarea").val().length == 0){
      hasError = true;
      msgCtrl.addClass("error");
      msgCtrl.find(".help-block").show();
    }

    if(!hasError) {
      $("#contactSubmit").hide();
      $("#contactSuccess").show();
      $.ajax({
        url: form.attr("action"),
        type: "POST",
        data: form.serialize()
      });
    }
  });
};


var initPagination = function() {
  $(".pagination .disabled a").on("click", function(e) {
    e.preventDefault();
  });
};


var initFeedbackForm = function() {
  var form = $("#form-feedback"),
      submitBtn = form.find(".submit"),
      title = form.find(".title"),
      name = form.find(".name"),
      msg = form.find(".msg");

  var updateBtn = function() {
    var hasError = false;
    if(form.find(".title").val().length == 0) { hasError = true; }
    if(form.find(".name").val().length == 0) { hasError = true; }
    if(form.find(".msg").val().length == 0) { hasError = true; }
    if(!hasError) {
      submitBtn.removeAttr("disabled").addClass("active");
    } else {
      submitBtn.attr("disabled", "disabled").removeClass("active");
    }
  };

  title.on("keydown", function() {
    setTimeout(updateBtn, 0);
  });
  name.on("keydown", function() {
    setTimeout(updateBtn, 0);
  });
  msg.on("keydown", function() {
    setTimeout(updateBtn, 0);
  });
};


var initEventsFeed = function() {
  var isMoving = false,
      controlsEnabled = false;
  var onPrevious = function() {
    controlsEnabled= false;
    previous();
  };
  var onNext = function() {
    controlsEnabled= false;
    next();
  };
  var enableControls = function() {
    if(!controlsEnabled) {
      controlsEnabled = true;
      $("#gc-event-previous").one("click", onPrevious);
      $("#gc-event-next").one("click", onNext);
    }
  };
  var disableControls = function() {
    controlsEnabled = false;
    $("#gc-event-previous").off("click", onPrevious);
    $("#gc-event-next").off("click", onNext);
  };

  var getEvents = function() {
    return $(".gc-event");
  };

  var next = function() {
    if(!isMoving) {
      disableControls();
      isMoving = true;
      var first = $(getEvents()[0]),
          height = first.outerHeight();
      first.clone().appendTo($("#gc-events"));

      first.animate({
        "margin-top": - height
      }, {
        duration: 2000,
        complete: function() {
          first.remove();
          enableControls();
          isMoving = false;
        }
      });
    }
  };

  var previous = function() {
    if(!isMoving) {
      disableControls();
      isMoving = true;
      var events = getEvents(),
          last = $(events[events.length - 1]),
          clone = last.clone(),
          height = last.outerHeight();
      clone.css("margin-top", -height).prependTo($("#gc-events"));

      clone.animate({
        "margin-top": 0
      }, {
        duration: 2000,
        complete: function() {
          last.remove();
          enableControls();
          isMoving = false;
        }
      });
    }
  };

  $("#gc-events-wrapper").hover(function() {
    $("#gc-event-previous").fadeIn("fast");
    $("#gc-event-next").fadeIn("fast");
  }, function() {
    $("#gc-event-previous").fadeOut("fast");
    $("#gc-event-next").fadeOut("fast");
  });

  var getTotalHeight = function() {
    var events = getEvents(),
        total = 0;
    for(var i=0; i<events.length; i++) {
      total += $(events[i]).outerHeight();
    }
    return total;
  };

  if(getTotalHeight() > $("#gc-events-wrapper").height()) {
    enableControls();
    setInterval(next, 7000);
  }
};


$(document).ready(function() {
  var path = window.location.pathname;

  if(path.match(/^\/reservation/)) {
    initDatePickers();
    initBookingForm();
  } else if(path.match(/\/admin/)) {
    initDatePickers();
  } else if(path.match(/\/contact/)) {
    initContactForm();
  } else if(path.match(/\/avis/)) {
    initPagination();
    initFeedbackForm();
  } else if(path.match(/\//)) {
    initEventsFeed();
  }

});