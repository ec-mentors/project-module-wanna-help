console.log("Hello Erik");
const ratingStars = document.getElementsByClassName("rating");

const averageRating = document.querySelector('.averagerating');
const totalRatings = document.querySelector('.totalratings');
const email = document.querySelector('.email');
const username = document.querySelector('.username');
const userRole = document.querySelector('.role');
const fullName = document.querySelector('.fullname');
const dateOfBirth = document.querySelector('.dateofbirth');
const address = document.querySelector('.addreess');
const fiststar = document.querySelector('.first');
const secondstar = document.querySelector('.second');
const thirdtstar = document.querySelector('.third');
const fourthstar = document.querySelector('.fourth');
const fifthstar = document.querySelector('.fifth');


const API_URL_PROFILEORG= "http://localhost:9100/profile/helpSeeker/24";




    const profile = {
        email,
        username,
        userRole,
        fullName,
        dateOfBirth,
        address,
        averageRating,
        totalRatings
      };

      console.log(profile);

     fetch(API_URL_PROFILEORG, {
      method: 'GET',
      headers: new Headers({
        "Authorization": 'Basic '+btoa('organization69:organization69')
      }),}).then(function(response) {
        console.log(response.status); // Will show you the status
        if (!response.ok) {
            console.log("Problem");
        }
        else if (response.ok) {
   
          console.log("OK");
        }
        return response.json();})
        .then(result => {
        console.log(result);
        if(result.email != null) {
        const emailinfo = document.createElement('h3');
        emailinfo.textContent = " " + result.email;
        email.appendChild(emailinfo);
        }
        if(result.username != null) {
        const usernameinfo = document.createElement('h3');
        usernameinfo.textContent = " " + result.username;
        username.appendChild(usernameinfo);
        }
        if(result.userRole != null) {
        const userroleinfo = document.createElement('h3');
        userroleinfo.textContent = " " + result.userRole;
        userRole.appendChild(userroleinfo);
        }
        if(result.fullName != null) {
        const fullnameinfo = document.createElement('h3');
        fullnameinfo.textContent = " " + result.fullName;
        fullName.appendChild(fullnameinfo);
        }
        if(result.dateOfBirth != null) {
        const dateOfBirthinfo = document.createElement('h3');
        dateOfBirthinfo.textContent = " " + result.dateOfBirth;
        dateOfBirth.appendChild(dateOfBirthinfo);
        }
        if(result.address != null) {
        const addressinfo = document.createElement('h3');
        addressinfo.textContent = " " + result.address;
        address.appendChild(addressinfo);
        }
        if(result.averageRating != null) {
          const averagerating = document.createElement('h3');
          averagerating.textContent = " " + "3";
          averageRating.appendChild(averagerating);
          }
        if(result.totalRatings != null) {
            const totalratings = document.createElement('h3');
            totalratings.textContent = " " + "1";
            totalRatings.appendChild(totalratings);
            }

          const finalRating = Math.ceil(result.averageRating);
          console.log(finalRating);

          if(finalRating == 1) {
              fiststar.style.color= "orange";
          }
          if(finalRating == 2) {
              fiststar.style.color= "orange";
              secondstar.style.color = "orange";
          }
          if(finalRating == 0) {
            fiststar.style.color= "orange";
            secondstar.style.color = "orange";
            thirdtstar.style.color = "orange";
          }
          if(finalRating == 4) {
            fiststar.style.color= "orange";
            secondstar.style.color = "orange";
            thirdtstar.style.color = "orange";
            fourthstar.style.color = "orange";
          }
          if(finalRating == 5) {
            fiststar.style.color= "orange";
            secondstar.style.color = "orange";
            thirdtstar.style.color = "orange";
            fourthstar.style.color = "orange";
            fifthstar.style.color = "orange";
          }
        });

        
      
      
      
