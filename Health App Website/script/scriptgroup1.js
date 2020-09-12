/**************************************************************************
 Name:          scriptgroup1.js
 Author:        Sharadruthi
 Description:   This part of the java Script code is called from groups.html
                Groups are added to the groups.html. The data of the group when a 
                group is added to a table is retrieved and displayed in the html 
                page. 
      
Modifications:
User           Date              Description
Sharadruthi    02/20/2020       View Groups into the HTML page
Sharadruthi    02/25/2020       Delete Group from the HTML page as well as from
                                database.
****************************************************************************/

const rootRef=database.ref('GroupName')

//Using the below code whenever a group is added to the database that group is also 
//added to the table in the html pages.

//snap.child("stepChallenge") and snap.child("sleepChallenge") are the data from the 
//database.
rootRef.on("child_added", snap =>{
    var key = snap.key;
    var stepChallenge = snap.child("stepChallenge").val();
    var sleepChallenge = snap.child("sleepChallenge").val();
    console.log(key);
    $("#table_body").append("<tr><td class='c'><b>"+key+"</b></td>"+
                                "<td><b>"+stepChallenge+"</b></td>"+
                                "<td><b>"+sleepChallenge+"</b></td>"+
                                "<td><button onclick=window.location.href='./viewUsers.html?groupKey="+key+"'>View</button>"+
                                //"<button class='a'>Modify</button>"+
                                "<button onclick='l(this,\""+key+"\")' class='a' >Delete</button></td>"+
                                "</tr>");
  })


  // The below code is used to delete the group from the html bages as well as the database.
  function l(btn,key){
      console.log("entered");
      console.log(key)
      rootRef.child(key).set(null).then(function(){
        var row = this.parentNode.parentNode;
        row.parentNode.removeChild(row);
      }.bind(btn))
  }
