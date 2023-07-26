console.log("Hello Erik");
const button_addvol = document.querySelector('#button_addvol');


const API_URL_ADDVOL = "http://localhost:1204/registervolunteer";
const API_URL_ADDORG = "http://localhost:1204/registerorganization";
const API_URL_ADDACT = "http://localhost:1204/addactivity";
const API_URL_APPLYACT = "http://localhost:1204/activityapply/";
var Created = false;

var volunteerId = "";
var organizationId = "";
var activityId = "";

function positiveTest(selector) {
    document.getElementById(selector).style.boxShadow = "0 0 60px 30px #6eff3e";
    document.getElementById(selector).style.backgroundColor = "#6eff3e";
}

function negativeTest(selector) {
    document.getElementById(selector).style.boxShadow = "0 0 60px 30px red";
    document.getElementById(selector).style.backgroundColor = "red";
}

function registerVolunteer() {
    fetch(API_URL_ADDVOL, {
        method: 'GET',
        headers: {
            'content-type': 'application/json'
        }
      }).then(function(response) {
        console.log(response.status); // Will show you the HTTP status
        if (!response.ok) {
            console.log("Problem");
            negativeTest("firstTest");
        }
        else if (response.ok) {
        console.log("Perfect");
        Created = true;
        positiveTest("firstTest");
        }
        return response.json();})
        .then(result => {
            console.log(result);
            if(Created) {
              volunteerId = result.id;
              console.log("The Id of Volunteer: " + volunteerId);             
            }
            else {
              console.log(result);
            }
          });
        }
 

function registerOrganization() { 
    fetch(API_URL_ADDORG, {
        method: 'GET',
        headers: {
            'content-type': 'application/json'
        }
      }).then(function(response) {
        console.log(response.status); 
        if (!response.ok) {
            console.log("Problem");
            negativeTest("secondTest");
        }
        else if (response.ok) {
        console.log("Perfect");
        Created = true;
        positiveTest("secondTest");
        }
        return response.json();})
        .then(result => {
            console.log(result);
            if(Created) {
              organizationId = result.id;
              console.log("The Id of Organization: " + organizationId);             
            }
            else {
              console.log(result);
            }
          });
        }



function registerActivity() { 
    fetch(API_URL_ADDACT, {
        method: 'GET',
        headers: {
            'content-type': 'application/json'
        }
      }).then(function(response) {
        console.log(response.status); 
        if (!response.ok) {
            console.log("Problem");
            negativeTest("thirdTest");
        }
        else if (response.ok) {
        console.log("Perfect");
        Created = true;
        positiveTest("thirdTest");
        }
        return response.json();})
        .then(result => {
            console.log(result);
            if(Created) {
              activityId = result.id;
              console.log("The Id of Organization: " + activityId);             
            }
            else {
              console.log(result);
            }
          });
}

function applyActivity() {
    fetch(API_URL_APPLYACT + activityId, {
        method: 'PUT',
        headers: {
            'content-type': 'application/json'
        }
      }).then(function(response) {
        console.log(response.status); 
        if (!response.ok) {
            console.log("Problem");
            negativeTest("fourthTest");
        }
        else if (response.ok) {
        console.log("Perfect");
        Created = true;
        positiveTest("fourthTest");
        }
        });
}










