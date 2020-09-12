/**************************************************************************
 Name:          addUsertoGroup
 Author:        Sharadruthi
 Description:   this part of the java Script code is called from 
                addUserToGroup html page.
      
Modifications:
User           Date              Description
Sharadruthi    03/20/2020       Display all the users present in the database.
Sharadruthi    03/24/2020       Delete the users present in the group
Sharadruthi    03/25/2020       Add users to a group using check box reference.

****************************************************************************/

var url = document.location.href,
params = url.split('?')[1].split('&'),
data = {}, tmp;
for (var i = 0, l = params.length; i < l; i++) {
 tmp = params[i].split('=');
 data[tmp[0]] = tmp[1];
}
database.ref('User1').once("value").then(function(snap){
// display the users present in the group
    snap.forEach(ch => {
        var userId = ch.key;
        var name = ch.child("FirstName").val();
        $("#table_body").append("<tr><td class='c'><b>"+userId+"</b></td>"+
                                "<td><b>"+name+"</b></td>"+
                                "<td><input type='checkbox' />&nbsp;</td>"+
                                "</tr>");                            
    });
 })
//code to remove the user from the group
  function l(btn,key){
      console.log("entered");
      console.log(key)
      rootRef.child(key).set(null).then(function(){
        var row = this.parentNode.parentNode;
        row.parentNode.removeChild(row);
      }.bind(btn))
      //var row = btn.parentNode.parentNode;
      //row.parentNode.removeChild(row);
  }

  function GetSelected() {
    //Reference the Table.
    var grid = document.getElementById("Table1");

    //Reference the CheckBoxes in Table.
    var checkBoxes = grid.getElementsByTagName("INPUT");
    var user_data={}
    
    //Loop through the CheckBoxes.
    for (var i = 0; i < checkBoxes.length; i++) {
        if (checkBoxes[i].checked) {
            var row = checkBoxes[i].parentNode.parentNode;
            user_data[row.cells[0].textContent]={"name":row.cells[1].textContent}
        }
    }//display users once added
    console.log(user_data);
    database.ref("GroupName").child(data.groupKey).child("Users").update(user_data,function(error) {
      if (error) {
          window.alert("error")
        } else {
          //window.alert("sucess")
          window.location.href='./viewUsers.html?groupKey='+data.groupKey;
          //window.open('./viewUsers.html?groupKey="+key+"')
        }
      }
      );
  
    
}
/*
//var rootRef1 = firebase.database().ref().child("GroupName");
 $("#table_body").on('click','.a', function(e){
    
    var $row = $(this).closest('tr');
    var $RId = $row.data('name');
         console.log($row.data('name'));
         console.log("user key", rootRef.key);
     
    var assetKey = rootRef.child(druthi).getKey();
    console.log(assetKey);
    rootRef.child(assetKey).remove()
    .then(function() {
        $row.remove();
      })
      .catch(function(error) {
        console.log('ERROR');
      });  
  });


  /*
    
       //console.log(rowId);
       console.log($row);

 })

       /*
       
    
    //it should remove the firebase object in here
    rootRef.child(assetKey).remove()
    //after firebase confirmation, remove table row
    .then(function() {
      $row.remove();
    })
    //Catch errors
    .catch(function(error) {
      console.log('ERROR');
    });  
});
 /*
  $("#table_body").on('click','.Delete', function(e){
      alert(1);
  }
  */
    



/*
 // var rootRef = firebase.database().ref().child("Assets");
  $("#table_body").on('click','.Delete', function(e){
     var key = snap.key;
     
      var $row = $(this).closest('tr'),
         rowId = $row.data(key);
      var assetKey = rootRef.child(key);
      //it should remove the firebase object in here
      rootRef.child(assetKey).remove()
      //after firebase confirmation, remove table row
      .then(function() {
        $row.remove();
      })
      //Catch errors
      .catch(function(error) {
        console.log('ERROR');
      });  
  });

/*
  rootRef.on("child_changed", snap =>{
    var key = snap.key;
    var stepChallenge = snap.child("stepChallenge").val();
    var sleepChallenge = snap.child("sleepChallenge").val();
    console.log(key);
    $("#table_body").append("<tr><td><b>"+key+"</b></td><td><b>"+stepChallenge+"</b></td><td><b>"+
                            sleepChallenge+"</b></td><td><table><td><button>View</button></td>"+
                            "<td><button>Modify</button></td><td><button>Delete</button></td></tr></table>");
  })
  */