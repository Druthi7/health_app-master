/**************************************************************************
 Name:          scriptUser.js
 Author:        Sharadruthi
 Description:   This part of the java Script code is called from 
                users.html  page. 
                This code takes todays date and displays the user data for 
                that particular day.
                Use Delete for deleting the used from the database.
                Export the data to and Excel Sheet. 
     
Modifications:
User           Date              Description
Sharadruthi    03/03/2020       View the user data into HTML page
Sharadruthi    03/04/2020       Export the data to the excel sheet.
Sharadruthi    03/05/2020       Delete the user from the html table as well as 
                                the database.

****************************************************************************/




//the script in this page is used to display the user data for that particular day
// the code below is used only for the present day. If there is no data present for 
//that particular day the values shown will be null.
const rootRef=database.ref('User1')
//gets the date and stored as today. the date will be present days date.
var today = new Date();
var dd = String(today.getDate()).padStart(2, '0');
var mm = String(today.getMonth() + 1).padStart(2, '0'); //January is 0!
var yyyy = today.getFullYear();
today = yyyy +  mm + dd;
//document.write(today);
console.log(today);

//the data is fetched from the database and stored into local variables
rootRef.on("child_added", snap =>{
    var key = snap.key;
    var FirstName = snap.child("FirstName").val();
    var LastName = snap.child("LastName").val();
    console.log(key);
    var stepCount = snap.child(today).child("StepCount").val();
    var sleepCount = snap.child(today).child("SleepCount").val();
    var hyderation = snap.child(today).child("Hyderation").val();
    var fruits = snap.child(today).child("Fruits").val();
    var vegetables = snap.child(today).child("Vegetables").val();
    var calories = snap.child(today).child("Calories").val();
    console.log(stepCount);
    // the user data from the local variables is displayed into a table format.
    $("#table_body1").append("<tr><td><b>"+key+"</b></td><td><b>"+FirstName+"</b></td>"+
                                                        "<td><b>"+LastName+"</b></td>"+
                                                        "<td><b>"+stepCount+"</b></td>"+
                                                        "<td><b>"+sleepCount+"</b></td>"+
                                                        "<td><b>"+hyderation+"</b></td>"+
                                                        "<td><b>"+fruits+"</b></td>"+
                                                        "<td><b>"+vegetables+"</b></td>"+
                                                        "<td><b>"+calories+"</b></td>"+
                                                        "<td><button onclick='userDel(this,\""+key+"\")'>Delete</button></td></tr>");
                            
  })

  
   // below code is used to delete the user from the table as well as the databas.
  function userDel(btn,key){
    console.log("entered");
    console.log(key)
    rootRef.child(key).set(null).then(function(){
      var row = this.parentNode.parentNode;
      row.parentNode.removeChild(row);
    }.bind(btn))
}

// the below code is used to download the user data into an excel sheet.
function fnExcelReport()
{
    var tab_text="<table border='2px'><tr bgcolor='#87AFC6'>";
    var textRange; var j=0;
    tab = document.getElementById('headerTable'); // id of table

    for(j = 0 ; j < tab.rows.length ; j++) 
    {     
        tab_text=tab_text+tab.rows[j].innerHTML+"</tr>";
        //tab_text=tab_text+"</tr>";
    }

    tab_text=tab_text+"</table>";
    tab_text= tab_text.replace(/<A[^>]*>|<\/A>/g, "");//remove if u want links in your table
    tab_text= tab_text.replace(/<img[^>]*>/gi,""); // remove if u want images in your table
    tab_text= tab_text.replace(/<input[^>]*>|<\/input>/gi, ""); // reomves input params

    var ua = window.navigator.userAgent;
    var msie = ua.indexOf("MSIE "); 
    if (msie > 0 || !!navigator.userAgent.match(/Trident.*rv\:11\./))      // If Internet Explorer
    {
        txtArea1.document.open("txt/html","replace");
        txtArea1.document.write(tab_text);
        txtArea1.document.close();
        txtArea1.focus(); 
        sa=txtArea1.document.execCommand("SaveAs",true,"Say Thanks to Sumit.xls");
    }  
    else                 
        sa = window.open('data:application/vnd.ms-excel,' + encodeURIComponent(tab_text));  

    return (sa);
    

}
 