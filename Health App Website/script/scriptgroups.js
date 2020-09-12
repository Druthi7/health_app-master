/**************************************************************************
 Name:          scriptgroups.js
 Author:        Sharadruthi
 Description:   This part of the java Script code is called from 
                Creategroups.html  page. 
                Input data such as group name, step challenge and sleep challege 
                is take from the client to create a group and stored in the database
      
Modifications:
User           Date              Description
Sharadruthi    03/01/2020       Data taken from the admin to create a group
Sharadruthi    03/02/2020       Create Store the group details in the database
                                suing add button.

****************************************************************************/
//data taken from the admin to create a group
const name = document.getElementById('name');
const stepChal = document.getElementById('stepChallenge');
const sleepChal = document.getElementById('SleepChallenge');
const rootRef=database.ref('GroupName')
//const rootRef=database.ref('User1')


// create a group in the database using add button 
addBtn.addEventListener('click', (e) =>{
  e.preventDefault();
  console.log(name.value)
  if (name.value != ""){
   //database.ref('/GroupName/'+ name.value).set({ 
  rootRef.child(name.value).set({
    //GroupName: name.value,
    name:name.value,
    stepChallenge:stepChal.value,
    sleepChallenge:sleepChal.value  
  });
    window.alert('Group added');
    window.location.href="./groups.html";
}
});













/*
//var ref = database.ref(GroupName)
rootRef.on('value', gotData, errData);
function gotData(data){
  //console.log(data.val());
  var GroupName = data.val();
  var keys = Object.keys(GroupName);
  console.log(keys);
  for (var i = 0; i<keys.length; i++){
    var k = keys[i];
    var StepChallenge = GroupName[k].stepChallenge;
    var sleepChallenge= GroupName[k].sleepChallenge;
    var gpdata = {k, StepChallenge, sleepChallenge};
    
    //console.log(k, StepChallenge,sleepChallenge)
    var list = document.createElement('ol')
    list.textContent = k
    //list.parent('gpnames');
  }
  console.log("Check");
  console.log(abc)

}
function errData(data){
  console.log('error!!');
  console.log(err);
}
*/








/*
rootRef.on('child_added', snapshot =>{
  console.log("Child added");
  //window.alert('Group added');
  //window.location.href="./groups.html";
});
//
rootRef.orderByChild("name").on('value', snapshot =>{
  console.log(snapshot.val());
});

var groupDataRef = firebase.database().ref("GroupName").orderByKey();
groupDataRef.once("value")
  .then(function(snapshot) {
    snapshot.forEach(function(childSnapshot) {
      var key = childSnapshot.key;
      var childData = childSnapshot.val();              // childData will be the actual contents of the child

      var name = childSnapshot.val().name;
      var stepChal = childSnapshot.val().stepChal;
      document.getElementById("name").innerHTML = name;
      document.getElementById("stepChal").innerHTML = stepChal;
  });
 });
}());
*/
