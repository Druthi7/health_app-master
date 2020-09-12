/**************************************************************************
 Name:          viewGroupUser.js
 Author:        Sharadruthi
 Description:   This part of the java Script code is called from 
                viewUsers.html  page. 
                Displays all the users present in a group.
                Removes any particular user from a group.

Modifications:
User           Date              Description
Sharadruthi    03/20/2020       View the all the users present in the group
Sharadruthi    03/30/2020       Delete the user from the html table as well as 
                                the database from a particular group.

****************************************************************************/

var url = document.location.href,
params = url.split('?')[1].split('&'),
data = {}, tmp;
for (var i = 0, l = params.length; i < l; i++) {
 tmp = params[i].split('=');
 data[tmp[0]] = tmp[1];
}
// the below code displays all the users present in the group.
function ViewAllUsers()
    {
            window.location.href="./addUserToGroup.html?groupKey="+data.groupKey;
    }

const rootRef=database.ref('GroupName').child(data.groupKey)

// the data is displayed in the table format.
rootRef.child("Users").on("child_added", snap =>{
    var key = snap.key;
    var UserName= snap.child("name").val();
    //var sleepChallenge = snap.child("sleepChallenge").val();
    console.log(key);
    $("#table_body").append("<tr><td class='c'><b>"+key+"</b></td>"+
                                "<td><b>"+UserName+"</b></td>"+
                                "<td><button onclick='remove_userFromGroup(this,\""+key+"\")' class='a' >Delete</button></td>"+
                                "</tr>");
  })


  // the below code is used to remove the user from the group
  function remove_userFromGroup(btn,key){
      console.log("entered");
      console.log(key)
      rootRef.child("Users").child(key).set(null).then(function(){
        var row = this.parentNode.parentNode;
        row.parentNode.removeChild(row);
      }.bind(btn))
      
  }
