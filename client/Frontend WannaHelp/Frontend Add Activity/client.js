console.log("Hello Erik");
const form1 = document.forms["erik-form1"];
const resultofaction = document.querySelector('.result');
const statusofregistration = document.querySelector('.status');
var Created = false;

const API_URL_ADDACTIVITY = "http://localhost:9100/activities/add";

form1.addEventListener('submit', (event) => {
    event.preventDefault();

    const formData = new FormData(form1);
    const title = formData.get('title');
    const description = formData.get('description');
    const recommendedSkills = formData.get('recommendedSkills');
    var startDate = formData.get('startDate');
    var endDate = formData.get('endDate');


    startDate = startDate.split("-").reverse().join(".");
    endDate = endDate.split("-").reverse().join(".");

    const activity = {
        title,
        description,
        recommendedSkills,
        startDate,
        endDate
      };

      Created = false;
      console.log(activity);

     fetch(API_URL_ADDACTIVITY, {
        method: 'POST',
        body: JSON.stringify(activity),
        headers: {
            'content-type': 'application/json',         
            'Authorization': 'Basic ' + btoa("organization69" + ":" + "organization69")
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
          statusofregistration.textContent = "ADDING ACTIVITY WAS SUCCESSFUL";
        }
        return response.json();})
        .then(result => {
        console.log(result);
        if(Created) {
          form1.reset();
          resultofaction.textContent = "Your new Activity " + JSON.stringify(result.title, undefined, 2) + " was created.";
        }
        else {
        resultofaction.textContent = JSON.stringify(result, undefined, 2);
        }
      })
    });


