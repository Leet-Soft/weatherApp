function bindEvents(){

    // Bind handlers for click events
  //  $('#login').on('click', loginClick);
    $('#register').on('click', registerClick);

}


function registerClick(e){

    $('#register-modal').modal('show');

}

