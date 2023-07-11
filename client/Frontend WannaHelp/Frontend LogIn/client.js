console.log("Hello Erik");
const form1 = document.forms["erik-form1"];
const resultofaction = document.querySelector('.result');
const statusofregistration = document.querySelector('.status');
var Created = false;

const API_URL_REGISTRATE = "http://localhost:9100/users/login";

form1.addEventListener('submit', (event) => {
    event.preventDefault();

    const formData = new FormData(form1);
    const username = formData.get('email');
    const password = formData.get('password');


    const login = {
   
        username,
        password
      };

      Created = false;
      console.log(login);

     fetch(API_URL_REGISTRATE, {
        method: 'POST',
        body: JSON.stringify(login),
        headers: {
            'content-type': 'application/json',
            'Authorization': 'Basic ' + btoa(username + ":" + password)
        }
      }).then(function(response) {
        console.log(response.status); // Will show you the status
        if (!response.ok) {
            statusofregistration.textContent = "";
           //throw new Error("HTTP status with The following Code" + response.status);
           statusofregistration.textContent = "THE FOLLOWING PROBLEM OCCURED:";
        }
        else if (response.ok) {
          Created = true;
          statusofregistration.textContent = "";
          statusofregistration.textContent = "LOGIN WAS SUCCESSFUL";
        }
        return response;})
    });


