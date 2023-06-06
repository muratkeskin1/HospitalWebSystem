    //docId al ->donorları sorgula -> bunları modalda göster
    import { initializeApp } from "https://www.gstatic.com/firebasejs/9.4.0/firebase-app.js";;
    import { getFirestore , collection, getDocs,doc,getDoc,query,where} from "https://www.gstatic.com/firebasejs/9.4.0/firebase-firestore.js";
    /* let donorIdNo=document.getElementById("donorIdNo")
    let donorBloodGroup=document.getElementById("donorBloodGroup")
    let complete=document.getElementById("complete")*/
    const firebaseConfig = {
    apiKey: "AIzaSyBWq2VoBEFzQnq7BOtgc0kX7YgcfQ1bjdk",
    authDomain: "blood-donation-app-bedf5.firebaseapp.com",
    databaseURL: "https://blood-donation-app-bedf5-default-rtdb.firebaseio.com",
    projectId: "blood-donation-app-bedf5",
    storageBucket: "blood-donation-app-bedf5.appspot.com",
    appId: "1:496697648178:android:a848a0eb1a564f72dd482b",
};
    const app = initializeApp(firebaseConfig);
    const db = getFirestore(app);
    console.log(db)
    let docId = "";
    const table=document.getElementById("tbody")
    $(document).ready( function (){
    $(".btn-outline-info").click(async function () {
        $("#tbody").empty();
        var $item = $(this).closest("tr");
        var $docid = $item.find("#docId").text();
        docId = $docid
        console.log($docid)
        const donors = doc(db, 'donors', docId);
        const q=query(collection(db,'donors'),where("applyId","==",$docid))
        const querySnapshot = await getDocs(q);
        setTimeout(function (){
        },2000)

        querySnapshot.forEach((doc) => {
            let row_2 = document.createElement('tr');
            let row_2_data_1 = document.createElement('td');
            row_2_data_1.innerHTML = doc.data().donorIdNo;
            let row_2_data_2 = document.createElement('td');
            row_2_data_2.innerHTML = doc.data().bloodGroup;
            let row_2_data_3 = document.createElement('td');
            row_2_data_3.innerHTML = doc.data().complete
            row_2.appendChild(row_2_data_1);
            row_2.appendChild(row_2_data_2);
            row_2.appendChild(row_2_data_3);
            table.appendChild(row_2);
            /*donorIdNo.innerHTML=doc.data().donorIdNo;
            donorBloodGroup.innerHTML=doc.data().bloodGroup;
            complete.innerHTML=doc.data().complete*/
            console.log(doc.id, " => ", doc.data());
        });

        /*
        const docSnap = await getDoc(donors)
        if (docSnap.exists()) {
          console.log("Document data:", docSnap.data());
        } else {
          // doc.data() will be undefined in this case
          console.log("No such document!");
        }
        console.log(donors.data)
  */

        /*
        db.collection('apply').doc($docid)
                .collection('donors').get().then(function(doc) {
          if (doc.exists) {
            console.log("Document data:", doc.data());
          } else {
            console.log("No such document!");
          }
        }).catch(function(error) {
          console.log("Error getting document:", error);
        });*/


        /*
        const mainRef=collection(db,'apply');
        const donors= getDocs(mainRef);
        console.log(donors)
        console.log(donors.data)
        Array.from(donors).forEach(async (apply)=>{
          let donorCollectionRef=collection(db,'apply/${docId}/donors');
          const groupDoc=getDocs(donorCollectionRef);
          Array.from(groupDoc).forEach((item)=> console.log(item.data()))
        })
  */


        /*
        let ref=getDocs(db,'apply',docId,'donors');
        const  docSnap = getDocs(ref);
        if (docSnap.exists()){
          console.log(docSnap.data)
        }
          const snapshot =  db.collection('users').document($docid).collection('donors').get();
          snapshot.forEach((doc) => {
            console.log(doc.id, '=>', doc.data());
          });*/
    })
})