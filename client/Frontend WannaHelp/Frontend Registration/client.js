console.log("Hello Erik");
const form1 = document.forms["erik-form1"];
const resultofaction = document.querySelector('.result');
const statusofregistration = document.querySelector('.status');
var Created = false;

const API_URL_REGISTRATE = "http://localhost:9100/users";

form1.addEventListener('submit', (event) => {
    event.preventDefault();

    const formData = new FormData(form1);
    const email = formData.get('email');
    const username = formData.get('username');
    const password = formData.get('password');
    const role = formData.get('role');
    const fullName = formData.get('fullname');
    var dateOfBirth = formData.get('birthday');
    const address = formData.get('address');
  

    dateOfBirth = dateOfBirth.split("-").reverse().join(".");

    const location = {
        email,
        username,
        password,
        role,
        fullName,
        dateOfBirth,
        address
      };

      Created = false;
      console.log(location);

     fetch(API_URL_REGISTRATE, {
        method: 'POST',
        body: JSON.stringify(location),
        headers: {
            'content-type': 'application/json'
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
          statusofregistration.textContent = "YOUR REGISTERATION WAS SUCCESSFUL";
        }
        return response.json();})
        .then(result => {
        console.log(result);
        if(Created) {
          form1.reset();
          resultofaction.textContent = "Your New Username is " + JSON.stringify(result.username, undefined, 2);
        }
        else {
        resultofaction.textContent = JSON.stringify(result, undefined, 2);
        }
      })
    });


