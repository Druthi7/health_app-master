/**************************************************************************
 Name:          script.js
 Author:        Sharadruthi
 Description:   this part of the java Script code adds all the related firebase 
                dependencies needed for the project. It is used in all the HTML 
                pages so that we can add and fetch data from  the database.
      
Modifications:
User           Date              Description
Sharadruthi    02/15/2020       Add firebase dependencies
Sharadruthi    02/18/2020       log in functionality using email and password
Sharadruthi    02/19/2020       navigation to other pages

****************************************************************************/


//Firebase dependencies used for the project
var config = {
  apiKey: "AIzaSyBeluZixGyWRXf8ke0CwFUE0HDkmA08524",
  authDomain: "health-app-bc30e.firebaseapp.com",
  databaseURL: "https://health-app-bc30e.firebaseio.com",
  projectId: "health-app-bc30e",
  storageBucket: "http://health-app-bc30e.appspot.com/",
  messagingSenderId: "871137122836"
};
firebase.initializeApp(config);
var Auth = firebase.auth(); 
var database = firebase.database();


//log in functionality using email and password
function reg(){
  var data = {
    email: $('#email').val(),
    password: $('#password').val()
  };
  if (email != "" && password != "") {
  firebase.auth().signInWithEmailAndPassword(data.email, data.password)
          .then(function(authData) {
            auth = authData;
            console.log("Login Succes!");
            window.location.href="./home.html";

          })
          .catch(function(error) {
            console.log("Login Failed!", error);
           
          });
  console.log(data)
  return false;
}
}

/*
function storedata1(userId, name, email) {
  window.alert("check")
  console.log('Entered')
  firebase.database().ref('users/' + userId).set({
    username: 'druthi',
    email: 'MediaList'
  },function(error) {
  if (error) {
      window.alert("error")
    } else {
      window.alert("sucess")
    }
  }
  );
  return false;
}
*/
// below opens the groups page using the groups button
function groups(){
  window.location.href="./groups.html";
}

// below opens the users page using the users button
function users(){
  window.location.href="./users.html";
}
// below code is used to signout of the application
function signout(){
  window.location.href="./SignInpage.html";
}
// below opens the create groups page using the create groups page
function Creategroups(){
  window.location.href="./Creategroups.html";
}
/*
function Performancegroups(){
  window.location.href="./Performancegroups.html";
}*/
// the below code is used to open the creategroups page.
function GroupCreate(){
  window.location.href="./Creategroups.html";
}



  
      
      
  
  
      
  


